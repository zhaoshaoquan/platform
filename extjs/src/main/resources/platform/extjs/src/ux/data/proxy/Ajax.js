Ext.define('Ext.ux.data.proxy.Ajax', {
    extend: 'Ext.data.proxy.Ajax',
	async:true,
    doRequest: function(operation, callback, scope) {
        var writer  = this.getWriter(),
            request = this.buildRequest(operation);  
        if (operation.allowWrite()) {
            request = writer.write(request);
        }
        Ext.apply(request, {
			async		  : this.async,
            binary        : this.binary,
            headers       : this.headers,
            timeout       : this.timeout,
            scope         : this,
            callback      : this.createRequestCallback(request, operation, callback, scope),
            method        : this.getMethod(request),
            disableCaching: false 
        });
        Ext.Ajax.request(request);
        return request;
    }
});