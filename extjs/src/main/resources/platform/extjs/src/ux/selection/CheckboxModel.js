Ext.define("Ext.ux.selection.CheckboxModel",{
	extend:"Ext.selection.CheckboxModel",
	constructor:function(config){
		var me = this;
		var config = config || {};
		var listeners = config.listeners || {};
		var currSelect = listeners.select || undefined;
		var currDeselect = listeners.deselect || undefined;
		var collection = config.collection || undefined;
		
		var aopSelect = function(cbm, record, index, eOpts ){
			var id = record.idProperty || "id";
			if(currSelect){
				currSelect(cbm,record,index,eOpts);
			}
			if(collection){
				collection.add(record.get(id),record);
			}
		};
		var aopDeselect = function(cbm, record, index, eOpts ){
			var id = record.idProperty || "id";
			if(currDeselect){
				currDeselect(cbm, record, index, eOpts);
			}
			if(collection){
				collection.removeAtKey(record.get(id));
			}
		};
		listeners.select = aopSelect;
		listeners.deselect = aopDeselect;
		if(config.listeners){
			Ext.apply(config.listeners, listeners);
		}else{
			config.listeners = listeners;
		}
        me.callParent([config]);
	}
});