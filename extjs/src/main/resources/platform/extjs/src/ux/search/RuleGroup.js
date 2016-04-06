Ext.define("Ext.ux.search.RuleGroup",{
	extend:"Ext.panel.Panel",
	border:false,
	layout: {
        type: 'hbox'
    },
    padding:'2 0 0 10',
    defaults: { 
    	margin:'0 0 0 5'
    },
    type:"ruleGroup",
	initComponent:function(){
		var me = this;
		var columns = me.searchGroup.advancedSearch.getColumns();
		var operTypecoll = new Ext.util.MixedCollection();

		var propStore = Ext.create('Ext.data.Store', {
			fields: ['id', 'text','dataIndex'],
		    data : columns
		});
		var operationStore = Ext.create('Ext.data.Store', {
			fields: ["name","val"],
		    data : [
		        {name:'包含',val:"CN"},
		        {name:'不包含',val:"NC"},
		        {name:'等于',val:"EQ"},
		        {name:'不等于',val:"NE"},
		        {name:'小于',val:"LT"},
		        {name:'小于等于',val:"LE"},
		        {name:'大于',val:"GT"},
		        {name:'大于等于',val:"GE"},
		        {name:'开始于',val:"BW"},
		        {name:'不开始于',val:"BN"},
		        {name:'属于',val:"IN"},
		        {name:'不属于',val:"NI"},
		        {name:'结束于',val:"EW"},
		        {name:'不结束于',val:"EN"},
		        {name:'空值',val:"NU"},
		        {name:'非空值',val:"NN"}
		    ]
		});
		var operationCombo = Ext.create('Ext.form.field.ComboBox',{
	          editable:false,
	          queryMode: 'local',
	          displayField: 'name',
	          valueField: 'val',
	          store: operationStore,
	          value:"EQ",
	          listeners:{
	        	  change:function( combo, newValue, oldValue, eOpts ){
	        		  me.searchGroup.advancedSearch.buildResult();
	        	  }
	          }
		});
		operTypecoll.add("Ext.form.field.Text",Ext.create('Ext.data.Store', {
			fields: ["name","val"],
		    data : [
		        {name:'包含',val:"CN"},
		        {name:'不包含',val:"NC"},
		        {name:'等于',val:"EQ"},
		        {name:'不等于',val:"NE"},
		        {name:'小于',val:"LT"},
		        {name:'小于等于',val:"LE"},
		        {name:'大于',val:"GT"},
		        {name:'大于等于',val:"GE"},
		        {name:'开始于',val:"BW"},
		        {name:'不开始于',val:"BN"},
		        {name:'属于',val:"IN"},
		        {name:'不属于',val:"NI"},
		        {name:'结束于',val:"EW"},
		        {name:'不结束于',val:"EN"},
		        {name:'空值',val:"NU"},
		        {name:'非空值',val:"NN"}
		    ]
		}));
        operTypecoll.add("Ext.form.field.ComboBox",Ext.create('Ext.data.Store', {
        	fields: ["name","val"],
		    data : [
		        {name:'等于',val:"EQ"},
		        {name:'不等于',val:"NE"},
		        {name:'空值',val:"NU"},
		        {name:'非空值',val:"NN"}
		    ]
		}));
        operTypecoll.add("Ext.ux.form.field.DateTime",Ext.create('Ext.data.Store', {
			fields: ["name","val"],
		    data : [
	            {name:'包含',val:"BT"},
		        {name:'等于',val:"EQ"},
		        {name:'不等于',val:"NE"},
		        {name:'小于',val:"LT"},
		        {name:'小于等于',val:"LE"},
		        {name:'大于',val:"GT"},
		        {name:'大于等于',val:"GE"},
		        {name:'空值',val:"NU"},
		        {name:'非空值',val:"NN"}
		    ]
		}));
        operTypecoll.add("Ext.form.field.Date",Ext.create('Ext.data.Store', {
			fields: ["name","val"],
		    data : [
	            {name:'包含',val:"BT"},
		        {name:'等于',val:"EQ"},
		        {name:'不等于',val:"NE"},
		        {name:'小于',val:"LT"},
		        {name:'小于等于',val:"LE"},
		        {name:'大于',val:"GT"},
		        {name:'大于等于',val:"GE"},
		        {name:'空值',val:"NU"},
		        {name:'非空值',val:"NN"}
		    ]
		}));
        operTypecoll.add("Ext.form.field.Number",Ext.create('Ext.data.Store', {
			fields: ["name","val"],
		    data : [
		        {name:'等于',val:"EQ"},
		        {name:'不等于',val:"NE"},
		        {name:'小于',val:"LT"},
		        {name:'小于等于',val:"LE"},
		        {name:'大于',val:"GT"},
		        {name:'大于等于',val:"GE"}
		    ]
		}));
        
		var defaultType = Ext.create('Ext.form.field.Text',{
			allowBlank:false,
			listeners:{
					change:function( text, newValue, oldValue, eOpts ){
	        		  me.searchGroup.advancedSearch.buildResult();
					},
					blur:function( text, The, eOpts ){
						 me.searchGroup.advancedSearch.buildResult();
					},
					focus:function( text, The, eOpts ){
						 me.searchGroup.advancedSearch.buildResult();
					},
					specialkey:function(f,e){
			    		if(e.getKey()==Ext.EventObject.ENTER){
			    			me.searchGroup.advancedSearch.buildQuery();
			    		}
					}
				}
		  });
		
		var inputType;
		
		var processOperation = function(clazz){
			 var classname = clazz.$className;
			 var store = operTypecoll.getByKey(classname);
			 if(store){
				 operationStore = store;
				 operationCombo.bindStore(store);
				 operationCombo.reset();
			  }else{
				  var superclass = clazz.superclass;
				  if(superclass){
					  processOperation(superclass);
				  }
			  }
		};
		
		var buildInputType = function(propName){
	   		  var column = me.searchGroup.advancedSearch.columnMap.getByKey(propName);
	   		  if(column.queryConfig){
	   				if(column.queryConfig.inputType){
	   					inputType = column.queryConfig.inputType.cloneConfig();
	   				}
	   		  }else{
	   			  inputType = defaultType;
	   		  }
			 var clazz = Ext.getClass(inputType);
			 processOperation(clazz);
		};
		buildInputType(columns[0].dataIndex);
		
		
		var propCombobox = Ext.create('Ext.form.field.ComboBox',{
	          editable:false,
	          queryMode: 'local',
	          displayField: 'text',
	          valueField: 'dataIndex',
	          store: propStore,
	          value:columns[0].dataIndex,
	          listeners:{
	        	  change:function( combo, newValue, oldValue, eOpts ){
	        		  inputType.setVisible(false); 
	        		  buildInputType(newValue);
	        		  if(me.contains(inputType)){
	        			  inputType.setVisible(true); 
	        		  }else{
	        			  me.insert(2,inputType);
	        		  }
				
	        		  me.searchGroup.advancedSearch.buildResult();
	        	  }
	          }
		});
		
		me.items = [propCombobox,operationCombo,inputType,{
	    	  xtype:'button',
	    	  text:"删除",
	    	  handler:function(){
	    		  me.searchGroup.remove(me);
	    		  me.searchGroup.advancedSearch.buildResult();
	    	  }
	      }];
		
		me.getRule = function(){
			var propName = propCombobox.getValue();
			var opera = operationCombo.getValue();
			var value = inputType.getValue();
			if(Ext.typeOf(value)=='date'){
				value = Ext.Date.format(value,inputType.format);
			}
			if(value==null){
				value = "";
			}
			if(opera == 'BT' && value!=''){
				 if(value.indexOf(" ")!=-1){
					 var array = value.split(" ");
					 value = value + "," + array[0] + " 23:59:59";
				 }else{
					 value = value + " 00:00:00," + value + " 23:59:59";
				 }
			}
			if(opera == 'NU'){
				value = '';
			}
			var column = me.searchGroup.advancedSearch.columnMap.getByKey(propName);
			var rule = {field:propName,data:value,op:opera,dataIndex:propName};
			if(column.queryConfig){
				if(column.queryConfig.queryName){
					rule.field = column.queryConfig.queryName;
				}
			}
			return rule;
	    };
		
	    me.isValid = function(){
	    	//如果是空值和非空值类型直接返回true
	    	var opera = operationCombo.getValue();
	    	if(opera == 'NU' || opera == 'NN'){
	    		return true;
	    	}
	    	return inputType.isValid();
	    }
		me.callParent(arguments);
	}
});