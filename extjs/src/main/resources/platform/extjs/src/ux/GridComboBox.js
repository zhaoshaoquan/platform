/**
 *  v1.2.4 2012-04-02
 *  author: Mac_J
 */
Ext.define('Ext.form.field.GridComboBox', {
	extend : 'Ext.form.field.Picker',
	requires : [ 'Ext.util.DelayedTask', 'Ext.EventObject', 'Ext.view.BoundList',
			'Ext.view.BoundListKeyNav', 'Ext.data.StoreManager', 'Ext.grid.View' ],
	alternateClassName : 'Ext.form.GridComboBox',
	alias : [ 'widget.gridcombobox', 'widget.gridcombo' ],
	triggerCls : Ext.baseCSSPrefix + 'form-arrow-trigger',
	multiSelect : false,
	delimiter : ',',
	displayField : 'text',
	triggerAction : 'all',
	labelAlign: 'right',
	allQuery : '',
	queryParam : 'query',
	queryMode : 'remote',
	queryCaching : true,
	pageSize : 0,
	autoSelect : true,
	typeAhead : false,
	typeAheadDelay : 250,
	selectOnTab : true,
	forceSelection : false,
	matchFieldWidth : false,
	pickerAlign: 'bl',
	defaultListConfig : {
		emptyText : '',
		loadingText : '读取中...',
		loadingHeight : 70,
		minWidth : 70,
		maxHeight : 300,
		shadow : 'sides'
	},
	// private
	ignoreSelection : 0,
	initComponent : function() {
		var me = this, isDefined = Ext.isDefined, store = me.store, transform = me.transform, transformSelect, isLocalMode;
		// <debug>
		if(!store && !transform){
			Ext.Error.raise('Either a valid store, or a HTML select to transform, must be configured on the combo.');
		}
		if(me.typeAhead && me.multiSelect){
			Ext.Error.raise('typeAhead and multiSelect are mutually exclusive options -- please remove one of them.');
		}
		if(me.typeAhead && !me.editable){
			Ext.Error.raise('If typeAhead is enabled the combo must be editable: true -- please change one of those settings.');
		}
		if(me.selectOnFocus && !me.editable){
			Ext.Error.raise('If selectOnFocus is enabled the combo must be editable: true -- please change one of those settings.');
		}
		// </debug>
		this.addEvents('beforequery', 'select');
		// Build store from 'transform' HTML select element's
		// options
		if (!store && transform) {
			transformSelect = Ext.getDom(transform);
			if (transformSelect) {
				store = Ext.Array.map(Ext.Array.from(transformSelect.options), function(
						option) {
					return [ option.value, option.text ];
				});
				if (!me.name) {
					me.name = transformSelect.name;
				}
				if (!('value' in me)) {
					me.value = transformSelect.value;
				}
			}
		}
		me.bindStore(store, true);
		store = me.store;
		if (store.autoCreated) {
			me.queryMode = 'local';
			me.valueField = me.displayField = 'field1';
			if (!store.expanded) {
				me.displayField = 'field2';
			}
		}
		if (!isDefined(me.valueField)) {
			me.valueField = me.displayField;
		}
		isLocalMode = me.queryMode === 'local';
		if (!isDefined(me.queryDelay)) {
			me.queryDelay = isLocalMode ? 10 : 500;
		}
		if (!isDefined(me.minChars)) {
			me.minChars = isLocalMode ? 0 : 4;
		}
		if (!me.displayTpl) {
			me.displayTpl = Ext.create('Ext.XTemplate', '<tpl for=".">'
					+ '{[typeof values === "string" ? values : values.' + me.displayField
					+ ']}' + '<tpl if="xindex < xcount">' + me.delimiter + '</tpl>'
					+ '</tpl>');
		} else if (Ext.isString(me.displayTpl)) {
			me.displayTpl = Ext.create('Ext.XTemplate', me.displayTpl);
		}
		me.callParent();
		me.doQueryTask = Ext.create('Ext.util.DelayedTask', me.doRawQuery, me);
		// store has already been loaded, setValue
		if (me.store.getCount() > 0) {
			me.setValue(me.value);
		}
		// render in place of 'transform' select
		if (transformSelect) {
			me.render(transformSelect.parentNode, transformSelect);
			Ext.removeNode(transformSelect);
			delete me.renderTo;
		}
	},
	beforeBlur : function() {
		var me = this;
		me.doQueryTask.cancel();
		if (me.forceSelection) {
			me.assertValue();
		} else {
			me.collapse();
		}
	},
	assertValue: function() {
		var me = this, value = me.getRawValue(), rec;
		rec = me.findRecordByDisplay(value);
		//only allow set single value for now.
		me.setValue(rec? [ rec.raw ]:[]);
		me.collapse();
	},
	onTypeAhead : function() {
		var me = this, df = me.displayField;
		var st = me.store, rv = me.getRawValue();
		var r = me.store.findRecord(df, rv);
		if (r) {
			var nv = r.get(df), ln = nv.length, ss = rv.length;
			if (ss !== 0 && ss !== ln) {
				me.setRawValue(nv);
				me.selectText(ss, nv.length);
			}
		}
	},
	// invoked when a different store is bound to this combo
	// than the original
	resetToDefault : function() {
	},
	bindStore : function(store, initial) {
		var me = this, oldStore = me.store;
		// this code directly accesses this.picker, bc invoking
		// getPicker
		// would create it when we may be preping to destroy it
		if (oldStore && !initial) {
			if (oldStore !== store && oldStore.autoDestroy) {
				oldStore.destroy();
			} else {
				oldStore.un({
					scope : me,
					load : me.onLoad,
					exception : me.collapse
				});
			}
			if (!store) {
				me.store = null;
				if (me.picker) {
					me.picker.bindStore(null);
				}
			}
		}
		if (store) {
			if (!initial) {
				me.resetToDefault();
			}
			me.store = Ext.data.StoreManager.lookup(store);
			me.store.on({
				scope : me,
				load : me.onLoad,
				exception : me.collapse
			});
			if (me.picker) {
				me.picker.bindStore(store);
			}
		}
	},
	onLoad : function() {
		var me = this, value = me.value;
		me.syncSelection();
	},
	/**
	 * @private Execute the query with the raw contents within
	 *		  the textfield.
	 */
	doRawQuery : function() {
		this.doQuery(this.getRawValue());
	},
	doQuery : function(queryString, forceAll) {
		queryString = queryString || '';
		// store in object and pass by reference in
		// 'beforequery'
		// so that client code can modify values.
		var me = this, qe = {
			query : queryString,
			forceAll : forceAll,
			combo : me,
			cancel : false
		}, store = me.store, isLocalMode = me.queryMode === 'local';

		if (me.fireEvent('beforequery', qe) === false || qe.cancel) {
			return false;
		}
		// get back out possibly modified values
		queryString = qe.query;
		forceAll = qe.forceAll;

		// query permitted to run
		if (forceAll || (queryString.length >= me.minChars)) {
			// expand before starting query so LoadMask can
			// position itself correctly
			me.expand();
			// make sure they aren't querying the same thing
			if (!me.queryCaching || me.lastQuery !== queryString) {
				me.lastQuery = queryString;
				store.clearFilter(!forceAll);
				if (isLocalMode) {
					if (!forceAll) {
						store.filter(me.displayField, queryString);
					}
				} else {
					store.load({
						params : me.getParams(queryString)
					});
				}
			}
			// Clear current selection if it does not match the
			// current value in the field
			if (me.getRawValue() !== me.getDisplayValue()) {
				me.ignoreSelection++;
				me.picker.getSelectionModel().deselectAll();
				me.ignoreSelection--;
			}

			if (me.typeAhead) {
				me.doTypeAhead();
			}
		}
		return true;
	},
	// private
	getParams : function(queryString) {
		var p = {}, pageSize = this.pageSize;
		p[this.queryParam] = queryString;
		if (pageSize) {
			p.start = 0;
			p.limit = pageSize;
		}
		return p;
	},
	doTypeAhead : function() {
		if (!this.typeAheadTask) {
			this.typeAheadTask = Ext.create('Ext.util.DelayedTask', this.onTypeAhead, this);
		}
		if (this.lastKey != Ext.EventObject.BACKSPACE
				&& this.lastKey != Ext.EventObject.DELETE) {
			this.typeAheadTask.delay(this.typeAheadDelay);
		}
	},
	onTriggerClick : function() {
		var me = this;
		if (!me.readOnly && !me.disabled) {
			if (me.isExpanded) {
				me.collapse();
			} else {
				me.onFocus({});
				if (me.triggerAction === 'all') {
					me.doQuery(me.allQuery, true);
				} else {
					me.doQuery(me.getRawValue());
				}
			}
			me.inputEl.focus();
		}
	},
	// store the last key and doQuery if relevant
	onKeyUp : function(e, t) {
		var me = this, key = e.getKey();

		if (!me.readOnly && !me.disabled && me.editable) {
			me.lastKey = key;
			me.doQueryTask.cancel();

			// perform query w/ any normal key or backspace or
			// delete
			if (!e.isSpecialKey() || key == e.BACKSPACE || key == e.DELETE) {
				if(me.getRawValue() == ''){
					me.clearValue();
					return;
				}
				me.doQueryTask.delay(me.queryDelay);
			}else if(key == e.ENTER) {
				this.doQuery(this.getRawValue(), true);
			}
		}
	},
	initEvents : function() {
		var me = this;
		me.callParent();
		// setup keyboard handling
		me.mon(me.inputEl, 'keyup', me.onKeyUp, me);
	},
	createPicker : function() {
		var me = this, menuCls = Ext.baseCSSPrefix + 'menu';
		var opts = Ext.apply({
			selModel : {
				mode : me.multiSelect ? 'SIMPLE' : 'SINGLE'
			},
			floating : true,
			hidden : true,
			ownerCt : me.ownerCt,
			cls : me.el.up('.' + menuCls) ? menuCls : '',
			store : me.store,
			multiSelect: me.multiSelect,
			displayField : me.displayField,
			focusOnToFront : false,
			pageSize : me.pageSize,
			gridCfg : me.gridCfg,
			owner : me
		}, me.listConfig, me.defaultListConfig);
		var pk = me.picker = new Ext.view.GridComboBoxList(opts);
		me.mon(pk, {
			itemclick : me.onItemClick,
			refresh : me.onListRefresh,
			scope : me
		});
		me.mon(pk.getSelectionModel(), {
			selectionChange : me.onListSelectionChange,
			scope : me
		});
		return pk;
	},
	onListRefresh : function() {
		this.alignPicker();
		this.syncSelection();
	},
	onItemClick : function(picker, record) {
		/*
		 * If we're doing single selection, the selection change
		 * events won't fire when clicking on the selected
		 * element. Detect it here.
		 */
		var me = this, lastSelection = me.lastSelection, valueField = me.valueField, selected;

		if (!me.multiSelect && lastSelection) {
			selected = lastSelection[0];
			if (record.get(valueField) === selected.get(valueField)) {
				me.collapse();
			}
		}
	},
	onListSelectionChange : function(list, selectedRecords) {
		var me = this;
		// Only react to selection if it is not called from
		// setValue, and if our list is
		// expanded (ignores changes to the selection model
		// triggered elsewhere)
		if (!me.ignoreSelection && me.isExpanded) {
			if (!me.multiSelect) {
				Ext.defer(me.collapse, 1, me);
			}
			me.setValue(selectedRecords, false);
			if (selectedRecords.length > 0) {
				me.fireEvent('select', me, selectedRecords);
			}
			me.inputEl.focus();
		}
	},
	/**
	 * @private Enables the key nav for the BoundList when it is
	 *		  expanded.
	 */
	onExpand : function() {
		var me = this, keyNav = me.listKeyNav;
		var selectOnTab = me.selectOnTab, picker = me.getPicker();

		// redo layout to make size right after reload store
		picker.grid.doLayout();
		
		// Handle BoundList navigation from the input field.
		// Insert a tab listener specially to enable
		// selectOnTab.
		if (keyNav) {
			keyNav.enable();
		} else {
			keyNav = me.listKeyNav = Ext.create('Ext.view.BoundListKeyNav', this.inputEl, {
				boundList : picker,
				forceKeyDown : true,
				home: function(e){
					return true;
				},
				end: function(e){
					return true;
				},
				tab: function(e) {
					if (selectOnTab) {
						this.selectHighlighted(e);
						me.triggerBlur();
					}
					// Tab key event is allowed to propagate to
					// field
					return true;
				}
			});
		}
		// While list is expanded, stop tab monitoring from
		// Ext.form.field.Trigger so it doesn't short-circuit
		// selectOnTab
		if (selectOnTab) {
			me.ignoreMonitorTab = true;
		}
		// Ext.defer(keyNav.enable, 1, keyNav); //wait a bit so
		// it doesn't react to the down arrow opening the picker
		me.inputEl.focus();
		me.syncSelection();
	},
	/**
	 * @private Disables the key nav for the BoundList when it
	 *		  is collapsed.
	 */
	onCollapse : function() {
		var me = this, keyNav = me.listKeyNav;
		if (keyNav) {
			keyNav.disable();
			me.ignoreMonitorTab = false;
		}
	},
	select : function(r) {
		this.setValue(r, true);
	},
	findRecord : function(field, value) {
		var ds = this.store, idx = ds.findExact(field, value);
		return idx !== -1 ? ds.getAt(idx) : false;
	},
	findRecordByValue : function(value) {
		return this.findRecord(this.valueField, value);
	},
	findRecordByDisplay : function(value) {
		return this.findRecord(this.displayField, value);
	},
	setValue : function(value, doSelect) {
		var me = this, txt = me.inputEl;
		me.value = value || {};
		if (me.store.loading)
			return me;
		me.setRawValue(me.getDisplayValue());
		if (txt && me.emptyText && !Ext.isEmpty(value))
			txt.removeCls(me.emptyCls);
		me.checkChange();
		if (doSelect)
			me.syncSelection();//
		me.applyEmptyText();
		return me;
	},
	getDisplayValue : function() {
		var me = this, dv = [];
		Ext.Object.each(me.value, function(k, v) {
			var a = v[me.displayField];
			if(a)
				dv.push(a);
		});
		return dv.join(',');
	},
	getValue : function() {
		return this.value || [];
	},
	//keys, spliter, doSelect
	setSubmitValue: function(keys, sp, ds){
		var me = this, v = {}, sp = sp || ',';
		if (keys) {
			Ext.Array.each(keys.split(sp), function(a) {
				var r = me.store.findRecord(me.valueField, a, 0, false, true, true);
				if(r)
					v[a] = r.data;
			});
		}
		me.setValue(v, ds);
	},
	getSubmitValue : function() {
		var me = this, sv = [];
		Ext.Object.each(me.value, function(k, v) {
			sv.push(v[me.valueField]);
		});
		return sv;
	},
	isEqual : function(v1, v2) {
		var fa = Ext.Array.from, i, len;
		v1 = fa(v1);
		v2 = fa(v2);
		len = v1.length;
		if (len !== v2.length) {
			return false;
		}
		for (i = 0; i < len; i++) {
			if (v2[i] !== v1[i]) {
				return false;
			}
		}
		return true;
	},
	clearValue : function() {
		this.setValue({});
	},
	syncSelection : function() {
		var me = this, pk = me.picker;
		if (pk && pk.grid) {
			var EA = Ext.Array, gd = pk.grid, st = gd.store;
			var cs = [];
			var sv = this.getSubmitValue();
			EA.each(st.data.items, function(r) {
				if (EA.contains(sv, r.data[me.valueField])) {
					cs.push(r);
				}
			});
			gd.getSelectionModel().select(cs, false, true);
		}
	}
});