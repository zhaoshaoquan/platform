Ext.define("Ext.ux.toolbar.Paging",{
	extend:"Ext.toolbar.Paging",
	initComponent:function(){
		var me = this;
		var listeners = me.listeners || {};
		var currChange = listeners.change || undefined;
		var collection = me.list.collection || undefined;
		var id = me.idProperty || "id";
		me.store = me.list.getStore();
		var aopChange = function(pagingtoolbar, pageData, eOpts ){
			if(currChange){
				currChange(pagingtoolbar, pageData, eOpts);
			}
			if(collection){
				var records = [];
				for(var i=0;i<me.getStore().getCount();i++){  
					var record = me.getStore().getAt(i);  
					if(collection.containsKey(record.get("id"))){
						records.push(record);
					}  
				}
				me.list.selModel.select(records);
			}
		};
		listeners.change = aopChange;
		if(me.listeners){
			Ext.apply(me.listeners, listeners);
		}else{
			me.listeners = listeners;
		}
		me.callParent(arguments);
	}
});