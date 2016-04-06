Ext.define('Ext.ux.PageSizePager', {
    constructor : function(config) {
        if (config) {
            Ext.apply(this, config);
        }
    },
    init : function(pbar){
    	var me = this;
    	var idx = pbar.items.indexOf(pbar.child("#refresh"));
    	var store = pbar.store;
	     pbar.insert(idx + 1, Ext.create('Ext.form.field.ComboBox', {
	         displayField: 'pageSize',
	         width: 70,
	         regex:/^\d+$/,   
	         regexText:'只能输入数字',
	         labelWidth: 35,
	         store: Ext.create('Ext.data.Store', {
	     		fields: ['pageSize'],
		        data: [{pageSize:50},{"pageSize":100},{"pageSize":150},{"pageSize":200},{"pageSize":10000}]
		     }),
	         value:store.pageSize,
	         listeners:{
	        	 change:function(field,newValue,oldValue,eOpts){
	        		if(!field.isValid() || newValue>10000){
	        			field.setValue(oldValue);
	        		}
	        	 },
	        	 specialKey:function(field,e,eOpts){
					if(!field.isExpanded){
						if(e.getKey() == Ext.EventObject.ENTER){
							var pageSize = field.getValue();
							if(pageSize==null){
								pageSize = store.getTotalCount();
								field.setValue(pageSize);
							}
							if(pageSize!=store.pageSize){
			            		store.pageSize=pageSize;
			            		store.loadPage(1);
			            	}
						}
					}
				},
	             select:function(combo,records){
	            	var pageSize=records[0].data.pageSize;
	            	if(pageSize!=store.pageSize){
	            		store.pageSize=pageSize;
	            		store.loadPage(1);
	            	}
	             }
	         }
	     }));
	     
	     if(me.showSearch===false)return;
	     
	     var doAdvancedSearch  = function(){
	    	 if(me.showSearchCount!=1){
	    		 var advancedSearch = Ext.create('Ext.ux.search.AdvancedSearch',{
	  	     		grid:pbar.list
	  	     	});
	  	     	me.searchWin = Ext.create('Ext.ux.window.Window',{
	  	 			title:'高级检索',
	  	 			width: 640,
	  	 			maxWidth:640,
	  	 			maximizable:false,
	  	 			modal:false,
	  	 			items:[advancedSearch],
	  	 			listeners:{
	  	 				close:function( panel, eOpts ){
		  	 				me.showSearchCount = 0;
		  	 			}
	  	 			}
	  	 		});
	 	     	me.showSearchCount = 1;
	    	 }
	    	 me.searchWin.show();
	     };
	     
	     pbar.insert(idx + 2, {
            itemId: 'advancedSearch',
            tooltip: "高级搜索",
            text:"高级搜索",
            overflowText: "高级搜索",
            iconCls: "icon-serch-blue",
            handler: doAdvancedSearch
        });
    }
});
