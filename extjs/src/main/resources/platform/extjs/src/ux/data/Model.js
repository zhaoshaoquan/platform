Ext.define('Ext.ux.data.Model', {
    extend: 'Ext.data.Model',
  	constructor: function(data, id, raw, convertedData) {
  		var me = this;
  		if(me.proxy!='ajax'){
  			var proxy = me.proxy;
  	  		if(!proxy.isInit){
  	  			var writer =  Ext.create('Ext.ux.data.writer.Json',{
	  	  			writeAllFields: false,
		            expandData:true
  	  			});
  	  	  	   proxy.writer = proxy.writer || writer;
  	  	  	   proxy.idParam = proxy.idParam || me.idProperty;
  	  	       var listeners = {
  	  	            exception: function(proxy, response, operation){
  	  	            	var error = {};
  	  	        		error = Ext.JSON.decode(response.responseText);
  	  	            	if(error.status==-2){
  	  	            		var systemLogin = Ext.create('permission.view.SystemLogin');
  	  	        		    systemLogin.show();
  	  	            	}else{
  	  	            		var msg = error.message.statusText;
  	  	            		Ext.ux.Msg.error(msg);
  	  	            	}
  	  	            }
  	  	        };
  	  	       if(proxy.listeners){
  	  	    	   Ext.applyIf(proxy.listeners,listeners);
  	  	       }else{
  	  	    	   Ext.applyIf(proxy, {listeners:listeners});
  	  	       }
  	  	       proxy.isInit = true;
  	  		}
  		}
		me.callParent(arguments);
	}
});