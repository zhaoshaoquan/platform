/*
Tree combo
Use with 'Ext.data.TreeStore'
If store root note has 'checked' property tree combo becomes multiselect combo (tree store must have records with 'checked' property)
Has event 'itemclick' that can be used to capture click
Options:
selectChildren - if set true and if store isn't multiselect, clicking on an non-leaf node selects all it's children
canSelectFolders - if set true and store isn't multiselect clicking on a folder selects that folder also as a value
Use:
single leaf node selector:
selectChildren: false
canSelectFolders: false
- this will select only leaf nodes and will not allow selecting non-leaf nodes
single node selector (can select leaf and non-leaf nodes)
selectChildren: false
canSelectFolders: true
- this will select single value either leaf or non-leaf
children selector:
selectChildren: true
canSelectFolders: true
- clicking on a node will select it's children and node, clicking on a leaf node will select only that node
This config:
selectChildren: true
canSelectFolders: false
- is invalid, you cannot select children without node
--Examples on: http://extjs.dariofilkovic.com/
*/
Ext.define('Ext.ux.TreeCombo',{
	extend: 'Ext.form.field.Picker',
	alias: 'widget.treecombo',
	tree: false,
	constructor: function(config){
		this.addEvents({"itemclick":true});
		this.listeners = config.listeners;
		this.callParent(arguments);
	},
	records: [],
	recursiveRecords: [],
	selectChildren: true,
	canSelectFolders: true,
	multiselect: false,
	displayField: 'text',
	valueField: 'id',
	recursivePush: function(node){
		var	me = this;
		me.recursiveRecords.push(node);
		node.eachChild(function(nodesingle){
			if(nodesingle.hasChildNodes() == true){
				me.recursivePush(nodesingle);
			}else me.recursiveRecords.push(nodesingle);
		});
	},
	recursiveUnPush: function(node){
		var	me = this;
		Ext.Array.remove(me.records, node);
		node.eachChild(function(nodesingle){
			if(nodesingle.hasChildNodes() == true){
				me.recursiveUnPush(nodesingle);
			}else Ext.Array.remove(me.records, nodesingle);
		});
	},
	afterLoadSetValue: false,
	setValue: function(valueInit){
		if(typeof valueInit == 'undefined') return;
		var	me = this,tree = this.tree,value = valueInit.split(',');
		inputEl = me.inputEl;
		if(tree.store.isLoading()){
			me.afterLoadSetValue = valueInit;
		}
		if(inputEl && me.emptyText && !Ext.isEmpty(value)){
			inputEl.removeCls(me.emptyCls);
		}
		if(tree == false) return false;
		var node = tree.getRootNode();
		if(node == null) return false;
		me.recursiveRecords = [];
		me.recursivePush(node);
		var valueFin = [];
		var idsFin = [];
		if(me.multiselect == true){
			Ext.each(me.recursiveRecords, function(record){
				record.set('checked', false);
			});
		}
		me.records = [];
		Ext.each(me.recursiveRecords, function(record){
			var data = record.get(me.valueField);
			Ext.each(value, function(val){
				if(data == val){
					valueFin.push(record.get(me.displayField));
					idsFin.push(data);
					if(me.multiselect == true) record.set('checked', true);
					me.records.push(record);
				}
			});
		});
		me.value = valueInit;
		me.setRawValue(valueFin.join(', '));
		me.checkChange();
		me.applyEmptyText();
		return me;
	},
	getValue: function(){
		return this.value;
	},
	getSubmitValue: function(){
		return this.value;
	},
	checkParentNodes: function(node){
		if(node == null) return;
		var	me = this,checkedAll = true,ids = [];
		Ext.each(me.records, function(value){
			ids.push(value.get(me.valueField));
		});
		node.eachChild(function(nodesingle){
			if(!Ext.Array.contains(ids, nodesingle.get(me.valueField))) checkedAll = false;
		});
		if(checkedAll == true && (me.tree.rootVisible || !node.isRoot())){
			me.records.push(node);
			me.checkParentNodes(node.parentNode);
		}else{
			Ext.Array.remove(me.records, node);
			me.checkParentNodes(node.parentNode);
		}
	},
	initComponent: function(){
		var	me = this;
		me.tree = Ext.create('Ext.tree.Panel',{
			alias: 'widget.assetstree',
			hidden: true,
			minHeight: 300,
			rootVisible: (typeof me.rootVisible != 'undefined') ? me.rootVisible : true,
			floating: true,
			useArrows: true,
			store: me.store,
			listeners:{
				load: function(store, records){
					if(me.afterLoadSetValue != false){
						me.setValue(me.afterLoadSetValue);
					}
				},
				itemclick: function(view, record, item, index, e, eOpts){
					var values = [];
					var node = me.tree.getRootNode().findChild('id', record.get(me.valueField), true);
					if(node == null){
						if(me.tree.getRootNode().get(me.valueField) == record.get(me.valueField)) node = me.tree.getRootNode();
						else return false;
					}
					if(me.multiselect == false) me.records = [];
					if(me.canSelectFolders == false && record.get('leaf') == false) return false;
					if(record.get('leaf') == true || me.selectChildren == false){
						if(me.multiselect == false) me.records.push(record);
						else{
							if(record.get('checked') == false) me.records.push(record);
							else Ext.Array.remove(me.records, record);
						}
					}else{		
						me.recursiveRecords = [];
						if(me.multiselect == false || record.get('checked') == false){
							me.recursivePush(node);
							Ext.each(me.recursiveRecords, function(value){
								if(!Ext.Array.contains(me.records, value)) me.records.push(value);
							});
						}else if(record.get('checked') == true){
							me.recursiveUnPush(node);
						}
					}
					if(me.canSelectFolders == true) me.checkParentNodes(node.parentNode);
					Ext.each(me.records, function(record){
						values.push(record.get(me.valueField));
					});
					me.setValue(values.join(','));
					me.fireEvent('itemclick', me, record, item, index, e, eOpts, me.records, values);
					if(me.multiselect == false) me.onTriggerClick();
				}
			}
		});
		if(me.tree.getRootNode().get('checked') != null) me.multiselect = true;
		this.createPicker = function(){
			var	me = this;
			return me.tree;
		};
		this.callParent(arguments);
	}
});