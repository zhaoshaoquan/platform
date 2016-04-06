Ext.define('demo.view.extjsDemoList',{
	extend: 'Ext.grid.Panel',
	forceFit: false,
	collapsible: false,
    autoScroll: true,
    loadMask: true,
    flex: 1,
    verticalScroller: {
        variableRowHeight: true
    },
    columns: [{
    	header: '',xtype: 'rownumberer',align:'center',width: 33
    },{
    	header: '姓名',dataIndex: 'zfxm',align:'center',width: 100
    },{
    	header: '罪犯编号',dataIndex: 'zfbh',align:'center',width: 100
    },{
    	header: '罪犯档案号',dataIndex: 'zfdah',align:'center',width: 80
    }],
	initComponent: function(){
		var me = this;
		var beforeload = function(s,o,e){
			if(me.getExtraParams)s.proxy.extraParams = me.getExtraParams();
		};
		var loadfun = function(th,records,success){
			if(success){
				var ids = [],selrecs = [];
				Ext.Array.each(me.getSelectionModel().getSelection(),function(record){
					ids.push(record.getId());
				});
				Ext.Array.each(records,function(record){
					if(Ext.Array.contains(ids,record.getId()))selrecs.push(record);
				});
				me.selModel.select(selrecs);
			}
		};
		if(me.store){
			me.store.addListener({'beforeload':beforeload,'load':loadfun});
		}else{
			me.store = Ext.create('demo.store.extjsDemo',{listeners:{beforeload:beforeload,load:loadfun}});
		}
		var pageSizePager = Ext.create('Ext.ux.PageSizePager');
		me.bbar = Ext.create('Ext.toolbar.Paging',{
			dock: 'bottom',
			displayInfo: true,
			list: me,
			plugins:[pageSizePager]
		});
		me.collection = new Ext.util.MixedCollection();
		me.selModel = Ext.create('Ext.ux.selection.CheckboxModel',{collection:me.collection});
		me.callParent(arguments);
	}
});