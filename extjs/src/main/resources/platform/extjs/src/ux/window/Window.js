Ext.define("Ext.ux.window.Window",{
	extend:"Ext.window.Window",
	constrain:true,
	bodyStyle:'background:#FFF',
	maximizable:true,
	listeners:{
		resize:function(win, width, height){
			for(var i = 0;i<win.items.length;i++){
				win.items.items[i].setWidth(width);
			}   
		}
	}
});