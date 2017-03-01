Ext.define('demo.store.extjsDemo',{
    extend: 'Ext.ux.data.Store',
	model: 'demo.model.extjsDemo',
	pageSize: 20,
	remoteSort: true,
	autoLoad: true,
	sorters: [{
        property: 'createDate',
        direction: 'DESC'
    }],
	constructor: function(config){
		var me = this;
		var config = config || {};
		me.readUrl = contextPath+'/demo/list';
		me.callParent([config]);
	}
});