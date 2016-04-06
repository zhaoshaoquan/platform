Ext.define("Ext.ux.form.field.SearchComboBox",{
	extend:"Ext.form.field.ComboBox",
	queryMode: 'remote',
	width:260,
	emptyText: '输入检索的内容进行匹配',
	initComponent:function(){
		var me = this;
		var listeners = me.listeners || {};
		var currExpand = listeners.expand || undefined;
		var currBeforequery = listeners.beforequery || undefined;
		var currSelect = listeners.select || undefined;
		var currSpecialKey = listeners.specialKey || undefined;
		var aopExpand = function(field, eOpts){
			field.store.load({
				callback: function(records, operation, success) {
					if(records.length > 0){
						field.expand();
					}else{
						field.collapse();
					}
				}
			});
			if(currExpand){
				currExpand(field, eOpts);
			}
		};
		var aopBeforequery = function( queryPlan, eOpts ){
			var value = this.getValue();
			if(value!=null){
				queryPlan.query=value;
			}
			if(currBeforequery){
				currBeforequery(queryPlan, eOpts);
			}
		};
		var aopSelect = function(combo,records, eOpts ){
			if(records.length>0){
				var value = records[0].get(combo.displayField);
				combo.setValue(value);
				combo.selectText();
			}
			if(currSelect){
				currSelect(combo,records, eOpts);
			}
		};
		var aopSpecialKey = function(field,e,eOpts){
			if(!field.isExpanded){
				if (e.getKey() == Ext.EventObject.DOWN) {
					field.expand();
				};
				if(e.getKey() == Ext.EventObject.ENTER){
					if(field.enter){
						field.enter();
					}
				}
			}
			if(currSpecialKey){
				currSpecialKey(field,e,eOpts);
			}
		};
		listeners.expand = aopExpand;
		listeners.beforequery = aopBeforequery;
		listeners.select = aopSelect;
		listeners.specialKey = aopSpecialKey;
		
		if(me.listeners){
			Ext.apply(me.listeners, listeners);
		}else{
			me.listeners = listeners;
		}
		me.pageSize=10;
		me.callParent(arguments);
	}
});