Ext.define('Ext.ux.data.TreeStore', {
    extend: 'Ext.data.TreeStore',
	defaultRootId:'0',
	getTotalCount : function() {
		return this.totalCount || 0;
    },
    buffered: false,
    clearOnPageLoad: true,
    clearRemovedOnLoad: true,
    pageSize:20,
    currentPage:1,
    nextPage: function(options) {
        this.loadPage(this.currentPage + 1, options);
    },
    previousPage: function(options) {
        this.loadPage(this.currentPage - 1, options);
    },
    loadPage: function(page, options) {
        var me = this;
        me.currentPage = page;
        // Copy options into a new object so as not to mutate passed in objects
        options = Ext.apply({
            page: page,
            start: (page - 1) * me.pageSize,
            limit: me.pageSize,
            addRecords: !me.clearOnPageLoad
        }, options);
        if (me.buffered) {
            options.limit = me.viewSize || me.defaultViewSize;
            return me.loadToPrefetch(options);
        }
        me.read(options);
    },
    load: function(options) {
    	var me = this;
    	options = options || {};

        if (typeof options == 'function') {
            options = {
                callback: options
            };
        }
        options.page = options.page || me.currentPage;
        options.start = (options.start !== undefined) ? options.start : (options.page - 1) * me.pageSize;
        options.limit = options.limit || me.pageSize;
        options.addRecords = options.addRecords || false;

        if (me.buffered) {
            options.limit = me.viewSize || me.defaultViewSize;
            return me.loadToPrefetch(options);
        }
    	return me.callParent([options]);
    },
    onProxyLoad: function(operation) {
        var me = this,
            resultSet = operation.getResultSet();
        if (me.isDestroyed) {
            return;
        }
        if (resultSet) {
            me.totalCount = resultSet.total;
        }
        return me.callParent([operation]);
    },
    constructor: function(config) {
    	var me = this;
		var config = config || {};
		var extraParams = config.params || {};
		
		var modelClass = me.model;
		var modelEntity = Ext.create(modelClass);
		var proxy = config.proxy || me.proxy || Ext.clone(modelEntity.proxy);
		
		if(proxy=='ajax'){
			proxy = {
				type: 'ajax',
		        idParam: modelEntity.idProperty,
		        api:{}
			}
		}
		
		me.readUrl = config.readUrl || me.readUrl || proxy.api.read;
		me.createUrl = config.createUrl || me.createUrl || proxy.api.create;
		me.updateUrl = config.updateUrl || me.updateUrl || proxy.api.update;
		me.destroyUrl = config.destroyUrl || me.destroyUrl || proxy.api.destroy;
		
		me.batchCreate = config.batchCreateUrl || me.batchCreate || proxy.api.read+'/batchCreate';
		me.batchUpdate = config.batchUpdateUrl || me.batchUpdate || proxy.api.read+'/batchUpdate';
		me.batchDestroy = config.batchDestroyUrl || me.batchDestroy || proxy.api.read+'/batchDelete';
		
		Ext.apply(proxy,{
		 	extraParams:extraParams,
			actionMethods: {read:'POST'},
			enablePaging: true,
			api: {
	        	read:me.readUrl,
				create:me.createUrl,
				update:me.updateUrl,
				destroy:me.destroyUrl
			},
			reader: {
			    type: 'json',
				root: 'rows',
				totalProperty: 'records',
				messageProperty:'statusText'
		    }
		}
	 );
		
		var listeners = {
	        exception: function(proxy, response, operation){
	        	var error = {};
	        	var responseText = response.responseText;
	        	var index = responseText.indexOf('loginForm');
	        	if(index==-1){
	        		error = Ext.JSON.decode(responseText);
	        	}
	        	if(index!=-1 || error.status==-2){
	        		permission.view.SystemLogin.show();
	        	}else{
	        		var msg = error.message.statusText;
	        		Ext.ux.Msg.error(msg);
	        	}
	        }
		 };
		 if(proxy.listeners){
			 Ext.apply(proxy.listeners,listeners);
		 }else{
			 proxy.listeners = listeners;
		 }
		config.proxy = proxy;	
        me.callParent([config]);
    },
    sync:function(options){
    	var me = this;
    	if(me.getNewRecords().length>1){
    		me.proxy.api.create = me.batchCreate;
		}else{
			me.proxy.api.create = me.createUrl;
		}
    	if(me.getUpdatedRecords().length>1){
    		me.proxy.api.update = me.batchUpdate;
    	}else{
    		me.proxy.api.update = me.updateUrl;
    	}
    	if(me.getRemovedRecords().length>1){
    		me.proxy.api.destroy = me.batchDestroy;
    	}else{
    		me.proxy.api.destroy = me.destroyUrl;
    	}
    	
    	var currSuccess = options.success || undefined;
    	var currFailure = options.failure || undefined;
    	
    	var aopSuccess = function(batch,options){
    		var operation = batch.operations[0];
    		var responseText = operation.response.responseText;
        	var index = responseText.indexOf('loginForm');
        	var result;
        	if(index==-1){
        		result = Ext.JSON.decode(responseText);
        	}
        	if(index!=-1 || result.status==-2){
//        		var systemLogin = Ext.create('permission.view.SystemLogin');
//    		    systemLogin.show();
        		permission.view.SystemLogin.show();
        	}else if(result.success && currSuccess){
				currSuccess(batch,options,result);
	    	}else{
        		msg = result.message.statusText;
	    		Ext.ux.Msg.error(code+":"+msg);
        	}
	    	
		};
		var aopFailure = function(batch,options){
			if(currFailure){
				currFailure(batch,options);
			}
		};
		options.success = aopSuccess;
		options.failure = aopFailure;
    	
    	return this.callParent([options]);
    }
});