Ext.define('Ext.ux.Msg', {
    singleton: true,
    constructor: function(config) {
    	this.msgList = new Array();
    	this.msgErrorList = new Array();
    	this.showing = false;
    	this.showErroring = false;
    },
    createBox:function(title,s){
//    	return '<div class="waring_body">'+
//	        '<div class="waring_title">'+title+'<div class="waring_close"><a href="#"></a></div></div>'+
//	        '<div class="waring_content">'+
//	             '<div class="waring_success"><div class="success_ico"></div><div class="content_text">'+s+'</div></div>'+
//	        '</div>'+
//        '</div>';
    	return '<div style="position: absolute;background: ef8f00;opacity: 0;z-index: 99999;height:24px;text-align: center;"><span style="color:#FFF;background:#68af02;heigth:24px;line-height:18px;border-radius:3px;padding: 3px 24px 3px;">'+s+'</span></div>';
    },
    createBoxError:function(title,s){
//    	return '<div class="waring_body">'+
//	        '<div class="waring_title">'+title+'<div class="waring_close"><a href="#"></a></div></div>'+
//	        '<div class="waring_content">'+
//	             '<div class="waring_error"><div class="error_ico"></div><div class="content_text">'+s+'</div></div>'+
//	        '</div>'+
//        '</div>';
    	return '<div style="position: absolute;background: red;opacity: 0;z-index: 99999;height:24px;text-align: center;"><span style="color:#FFF;background:red;heigth:24px;line-height:18px;border-radius:3px;padding: 3px 24px 3px;">'+s+'</span></div>';
    },
    show:function(msg){
    	this.msgList.push(msg);
    	this.showMsg();
    },
    showMsg:function(){
    	var me = this;
    	if(!me.showing){
    		var msg = this.msgList.pop();
    		if(msg){
    			me.showing = true;
    			var m = Ext.DomHelper.append(document.body, this.createBox('操作提示', msg), true);
            	m.alignTo(document.body,'t',[-100, 100]);
            	m.setOpacity(1);
                m.fadeIn({ duration: 2000}).fadeOut({ duration: 1000,remove: false,callback:function(){
                	me.showing = false;
                	me.showMsg();
                }});
    		}
    	}
    },
    showError:function(){
    	var me = this;
    	if(!me.showErroring){
    		var msg = this.msgErrorList.pop();
    		if(msg){
    			me.showErroring = true;
    			var m = Ext.DomHelper.append(document.body, this.createBoxError('操作提示', msg), true);
            	m.alignTo(document.body,'t',[-100, 100]);
            	m.setOpacity(1);
                m.fadeIn({ duration: 2000}).fadeOut({ duration: 1000,remove: true,callback:function(){
                	me.showErroring = false;
                	me.showError();
                }});
    		}
    	}
    },
    error:function(msg){
    	this.msgErrorList.push(msg);
    	this.showError();
    }
});