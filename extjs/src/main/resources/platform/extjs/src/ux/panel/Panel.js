Ext.define("Ext.ux.panel.Panel",{
	extend:"Ext.panel.Panel",
	bodyStyle:'background:#FFF',
	initComponent:function(){
		var me = this;
		me.on("resize",function( panel, width, height, oldWidth, oldHeight, eOpts ){
			for(var i = 0;i<me.items.length;i++){
				me.items.items[i].setWidth(me.getWidth()-2);
			} 
		});
		var constrainer = function(){
			for(var i = 0;i<me.items.length;i++){
				me.items.items[i].setWidth(me.getWidth()-2);
			}   
		}
		Ext.EventManager.onWindowResize(constrainer);
		me.on('destroy', function() { 
			Ext.EventManager.removeResizeListener(constrainer);
		});
		
		me.callParent(arguments);
	}
});