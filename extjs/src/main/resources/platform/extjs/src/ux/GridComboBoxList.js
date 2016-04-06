/**
 *  v1.2.2 2012-05-07
 *  author: Mac_J
 */
Ext.define('Ext.view.GridComboBoxList', {
	extend : 'Ext.view.AbstractView',
	requires : [ 'Ext.PagingToolbar' ],
	alias : 'widget.gridcombolist',
	alternateClassName : 'Ext.GridComboBoxList',
	// 2012-05-07 Ext4.1下的Bug的解决
	renderTpl : [ '<div class="list-ct" style="border: 1px solid #99BBE8"></div>' ],
//	renderTpl : [ '<div class="list-ct" style="border: 1px solid #99BBE8"></div>' ],
	initComponent : function() {
		var me = this;
		// 2012-05-07 Ext4.1下的Bug的解决
		me.itemSelector = "div.list-ct";
		//me.itemSelector = ".";
		me.tpl = Ext.create('Ext.XTemplate');
		me.callParent();
		Ext.applyIf(me.renderSelectors, {
			listEl : '.list-ct'
		});
		me.gridCfg.border = false;
		me.gridCfg.store = me.store; 
		me.gridCfg.width = me.gridCfg.width || 200;
		me.gridCfg.maxHeight = me.gridCfg.maxHeight || 250;
		me.gridCfg.selModel = me.gridCfg.selModel || (me.multiSelect ? new Ext.selection.CheckboxModel({checkOnly: true}): null);
		
		me.gridCfg.bbar = me.gridCfg.bbar || Ext.create('Ext.PagingToolbar', {
			store : me.store,
			displayInfo : true//,
			//displayMsg : 'Displaying {0} - {1} of {2}',
			//emptyMsg : "No data to display"
		});
		me.grid = Ext.create('Ext.grid.Panel', me.gridCfg);
		me.grid.store.addListener({
			beforeload : function() {
				me.owner.loading = true;
			},
			load : function() {
				me.owner.loading = false;
			}
		});
		var sm = me.grid.getSelectionModel();
		sm.addListener('selectionchange', function(a, sl) {
			var cbx = me.owner;
			if (cbx.loading)
				return;
//			var sv = cbx.multiSelect? cbx.getValue():{};
//			var sv = {};
			// sv = {}
			var EA = Ext.Array, vf = cbx.valueField;
			// al = [ 'G', 'Y', 'B' ]
//			var al = EA.map(me.grid.store.data.items, function(r) {
//				return r.data[vf];
//			});
//			var cs = EA.map(sl, function(r) {
//				var d = r.data;
//				if(d){
//					var k = d[vf];
//					sv[k] = d;
//					return k;
//				}
//			});
			// cs = [ 'G' ]
//			var rl = EA.difference(al, cs);
//			EA.each(rl, function(r) {
//				delete sv[r];
//			});
			var sv = [];
			EA.each(sl,function(v,i){if(v && v.data)sv.push(v.data)});
			cbx.setValue(sv);
		});
		sm.addListener('select', function(m, r, i) {
			var cbx = me.owner;
			if (cbx.loading)
				return;
			if(!cbx.multiSelect)
				cbx.collapse();
		});
	},
	onRender : function() {
		this.callParent(arguments);
		this.grid.render(this.listEl);
	},
	bindStore : function(store, initial) {
		this.callParent(arguments);
		if(this.grid)
			this.grid.bindStore(store, initial);
	},
	onDestroy : function() {
		Ext.destroyMembers(this, 'grid', 'listEl');
		this.callParent();
	},
	highlightItem:function(item){
	}
});