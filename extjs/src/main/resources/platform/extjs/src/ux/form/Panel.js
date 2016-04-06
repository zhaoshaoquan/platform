Ext.define("Ext.ux.form.Panel",{
	extend:"Ext.form.Panel",
	isValid:function(){
		var isValid = true;
		var validItems = function(items){
			Ext.Array.each(items,function(item,index){
				if(isValid){
					if(item.isValid && !item.isValid()){
						isValid = false;
						item.focus();
					}
					if(item.items && item.items.items && Ext.typeOf(item.items.items) == 'array' && item.items.items.length>0){
						validItems(item.items.items);
					}
				}
			});	
		}
		validItems(this.items.items);
		return isValid;
	},
	submit: function(options) {
		options.waitMsg = "正在处理中,请稍后";
		var currSuccess = options.success || undefined;
    	var currFailure = options.failure || undefined;
    	var mask = new Ext.LoadMask(Ext.getBody(), {msg:options.waitMsg});
    	var aopSuccess = function(form, action){
    		mask.hide();
    		var result = action.result;
	    	if(result.success){
	    		if(currSuccess){
					currSuccess(form, action);
				}
	    	}else{
	    		if(result.status==-2){
	    			var systemLogin = Ext.create('permission.view.SystemLogin');
	    		    systemLogin.show();
	    		}else{
	    			msg = result.message.statusText;
		    		Ext.ux.Msg.error(msg);
	    		}
	    	}
		};
		var aopFailure = function(form, action){
			var msg = "未知错误";
			mask.hide();
			switch (action.failureType) {
	            case Ext.form.action.Action.CLIENT_INVALID:
	            	msg = "Form fields may not be submitted with invalid values";
	                break;
	            case Ext.form.action.Action.CONNECT_FAILURE:
	            	msg = "提交数据失败";
	                break;
	            case Ext.form.action.Action.SERVER_INVALID:
	            	var responseText = action.response.responseText;
	            	var index = responseText.indexOf('loginForm');
	            	var result;
	            	if(index==-1){
	            		result = Ext.JSON.decode(responseText);
	            		msg = result.text || result.message.text;
	            		if(currFailure){
		    				currFailure(form, action,result);
		    			}
	            	}else{
	            		var systemLogin = Ext.create('permission.view.SystemLogin');
		    		    systemLogin.show();
	            	}
	            	
	    	}
			Ext.ux.Msg.error(msg);
		};
		options.success = aopSuccess;
		options.failure = aopFailure;
		mask.show();
        this.form.submit(options);
    },
	initComponent:function(){
		var me = this;
		
		var specialKey = function(field,e,eOpts){
			if (e.getKey() == Ext.EventObject.ENTER) {
				var isValid = true;
				if(!field.isValid()){
					field.focus();
				}else{
					if(me.isValid()) {
						if(me.submitBtn){
							me.submitBtn.focus();
						}
					 }
				}
			};
			if(field.old_specialKey){
				field.old_specialKey(field,e,eOpts);
			}
		};
		
		var addEnterEvent = function(items){
			Ext.Array.each(items,function(item,index){
				if(!Ext.isDefined(item.enterChange) || item.enterChange){
					if(item.listeners){
						if(!item.listeners.specialKey){
							item.listeners.specialKey = specialKey;
						}else{
							item.old_specialKey = item.listeners.specialKey;
							item.listeners.specialKey = specialKey;
						}
					}else{
						if(item.addListener){
							item.addListener("specialKey",specialKey);
						}else{
							item.listeners = {specialKey:specialKey};
						}
					}
					if(Ext.typeOf(item.items) == 'array' && item.items.length>0){
						addEnterEvent(item.items);
					}
				}
			});
		}
		addEnterEvent(me.items);
		
		me.callParent(arguments);
	}
});