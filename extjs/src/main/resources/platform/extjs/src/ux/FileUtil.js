Ext.define('Ext.ux.FileUtil', {
    statics: {
    	download: function(config) {
    		var form = document.createElement('form');
			Ext.fly(form).set({
				method:'POST',
				action:config.action
			});
    		for(name in config.params){
    			var input = document.createElement('input');
    			Ext.fly(input).set({type:'hidden',name:name,value:config.params[name]});
    			form.appendChild(input);
    		}
    		form.submit();
        }
    }
});