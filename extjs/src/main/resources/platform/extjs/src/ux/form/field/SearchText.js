Ext.define('Ext.ux.form.field.SearchText', {
    extend: 'Ext.form.field.Text',
    width: 260,
    initComponent:function(){
    	var me = this;
    	var listeners = me.listeners || {};
    	var currSpecialkey = listeners.specialkey || undefined;
    	var aopSpecialkey = function(f,e){
    		if(e.getKey()==Ext.EventObject.ENTER){
    			if(me.store){
    				me.store.loadPage(1);
    			}
    			f.selectText();
    		}
    		if(currSpecialkey){
    			currSpecialkey(f, e);
			}
    	};
    	
    	listeners.specialkey = aopSpecialkey;
    	var currRender = listeners.render || undefined;
    	var aopRender = function (f, p) {
    		Ext.tip.QuickTipManager.init();
    		Ext.tip.QuickTipManager.register({
    		    target: f.id,
    		    title: me.tipTitle || '搜索提示',
    		    text: me.tipText || me.emptyText,
    		    width: 200,
    		    dismissDelay: 10000
    		});
    		if(currRender){
    			currRender(f, e);
			}
		};
		listeners.render = aopRender;
		if(me.listeners){
    	   Ext.apply(me.listeners,listeners);
        }else{
        	me.listeners = listeners;
        }
    	me.callParent(arguments);
    }
});