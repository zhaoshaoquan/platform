Ext.define('demo.view.extjsDemoIndex',{
	extend: 'Ext.ux.panel.Panel',
	border: false,
	layout: {
		type: 'vbox',
		align: 'stretch'
	},
	initComponent: function(){
		var me = this;
		
		var list = Ext.create('demo.view.extjsDemoList',{getExtraParams:function(){
			var extraParams = {};
			var val = searchField.getValue()==null?"":searchField.getValue();
			var rules = [];
			if(selectCheckbox.getValue()){
				var ids = list.collection.keys.join(",");
				rules.push({field:'id',data:ids,op:'in'});
			}
			extraParams.searchKey = val;
			extraParams.filters = Ext.JSON.encode({rules:rules});
			return extraParams;
		}});
		
		var searchField = Ext.create('Ext.ux.form.field.SearchText',{
			store:list.store,emptyText:'姓名|编号,多个逗号分隔'
		});
		
		var selectCheckbox = Ext.create('Ext.form.field.Checkbox',{
			boxLabel: '仅显示已选',
			listeners: {
				change: function(checkbox, newValue, oldValue, eOpts){
					list.store.loadPage(1);
				}
			}
		});
		var tbar = Ext.create('Ext.toolbar.Toolbar',{
			border: 0,
			items: [{
				xtype:'displayfield',
				value:'关键字:'
			},searchField,{
				text: '搜索',iconCls: 'icon-search',
				handler: function(button){
					list.store.loadPage(1);
				}
			},{
				text: '重置',iconCls: 'icon-reset',
				handler: function(button){
					searchField.setValue("");
					selectCheckbox.setValue(false);
					list.collection.clear();
					list.store.loadPage(1);
					list.getSelectionModel().deselectAll();
				}
			},selectCheckbox,{
				text: '添加',iconCls: 'icon-add',xtype: 'button',handler: function(b){
					
				}
			},{
				text: '修改',iconCls: 'icon-update',xtype: 'button',handler: function(b){
					
				}
			}]
		});
		me.items = [tbar,list];
		me.callParent(arguments);
	}
});