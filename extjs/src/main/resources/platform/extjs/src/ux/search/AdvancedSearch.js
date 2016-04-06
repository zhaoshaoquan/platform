Ext.define("Ext.ux.search.AdvancedSearch",{
	extend:"Ext.panel.Panel",
	border:false,
	buildResult:function(){
		var me = this;
		var groupOp = me.groupPanel.getGroupOp();
		var rootRules = me.groupPanel.getRules();
		var groups = [];
		me.groupPanel.buildGroups(groups);
		var prefixBracketsCount = groups.length + 1;
		var result = "";
		for(var i=0;i<prefixBracketsCount;i++){
			result+="(";
		}
		for(var i=0;i<groups.length;i++){
			var group = groups[i];
			var subRules = group.rules;
			for(var j=0;j<subRules.length;j++){
				var rule = subRules[j];
				var fieldName = me.columnMap.getByKey(rule.dataIndex).text;
				var opName = me.opMap.getByKey(rule.op);
				var groupOpName = me.relateTypeMap.getByKey(group.groupOp);
				if((groups.length==1  || i==groups.length-1) && j==0){
					result+= ' ' + fieldName + ' ' + opName + ' "' + rule.data +'"';
				}else{
					result+= ' ' + groupOpName + ' ' + fieldName + ' ' + opName + ' "' + rule.data +'"';
				}
			}
			result+=")";
		}
		for(var j=0;j<rootRules.length;j++){
			var rule = rootRules[j];
			var fieldName = me.columnMap.getByKey(rule.dataIndex).text;
			var opName = me.opMap.getByKey(rule.op);
			var groupOpName = me.relateTypeMap.getByKey(groupOp);
			if(groups.length==0 && j==0){
				result+=' ' + fieldName + ' ' + opName + ' "' + rule.data +'"';
			}else{
				result+=' ' + groupOpName + ' ' + fieldName + ' ' + opName + ' "' + rule.data +'"';
			}
		}
		result+=")";
		me.resultText.setText("查询条件：" + result);
		
	},
	buildQuery:function(){
		var me = this;
		me.buildResult();
		var valid = me.groupPanel.isValid();
		if(!valid){
			return;
		}
		var groupOp = me.groupPanel.getGroupOp();
		var rules = me.groupPanel.getRules();
		var groups = [];
		me.groupPanel.buildGroups(groups);
		me.filters = {groupOp:groupOp,rules:rules,groups:groups};
		me.grid.store.loadPage(1);
	},
	getColumns:function(){
		var newCols = [];
		//var columns = this.grid.columnManager.headerCt.items.items;
		var columns = this.grid.columns;
		this.columnMap = new Ext.util.MixedCollection();
		for(var i=0;i<columns.length;i++){
			if(columns[i].isCheckerHd)continue;
			if(columns[i].id.indexOf('rownumberer')!=-1)continue;
			if(columns[i].hidden==true)continue;
			if(columns[i].hiddenSearch==true)continue;
			newCols.push(columns[i]);
			this.columnMap.add(columns[i].dataIndex,columns[i]);
		}
		return newCols;
	},
	initComponent:function(){
		var me = this;
		me.items = [];
		
		opMap = new Ext.util.MixedCollection();
		opMap.add("CN","包含");
		opMap.add("NC","不包含");
		opMap.add("EQ","等于");
		opMap.add("NE","不等于");
		opMap.add("LT","小于");
		opMap.add("LE","小于等于");
		opMap.add("GT","大于");
		opMap.add("GE","大于等于");
		opMap.add("BW","开始于");
		opMap.add("BN","不开始于");
		opMap.add("IN","属于");
		opMap.add("NI","不属于");
		opMap.add("EW","结束于");
		opMap.add("NU","空值");
		opMap.add("NN","非空值");
		opMap.add("EN","不结束于");
		opMap.add("BT","包含");
		
		me.opMap = opMap;
		
		relateTypeMap = new Ext.util.MixedCollection();
		relateTypeMap.add("AND","并且");
		relateTypeMap.add("OR","或者");
		me.relateTypeMap = relateTypeMap;
		
		
		me.resultText = Ext.create('Ext.form.Label',{
			text:"查询条件：",
			padding:'10 0 5 5'
		});
		me.items.push(me.resultText);
		
		me.groupPanel = Ext.create('Ext.ux.search.SearchGroup',{
			advancedSearch:me
		});
		
		me.btnBar = Ext.create('Ext.panel.Panel', {
			border:false,
		    layout: {
		        type: 'hbox',
		        pack:"center"
		    },
		    padding:'5 10 10 0',
		    defaults: { 
		    	margin:'5 0 0 5'
		    },
		    items: [
		    {
		    	  xtype:'button',
		    	  text:"重　置",
		    	  handler:function(){
		    		 me.remove(me.groupPanel);
		    		 me.groupPanel = Ext.create('Ext.ux.search.SearchGroup',{
	    				advancedSearch:me
	    			 });
		    		 me.insert(1,me.groupPanel);
		    		 me.buildResult();
		    	  }
		      },{
		    	  xtype:'button',
		    	  text:"查　询",
		    	  handler:function(){
		    		me.buildQuery();
		    	  }
		      }]
		});
		var beforeload = function( store, operation, eOpts ){
			var extraParams = store.proxy.extraParams;
			var currFilters = me.filters;
			if(extraParams.filters){
				if(Ext.typeOf(extraParams.filters)=='string'){
					extraParams.filters =  Ext.JSON.decode(extraParams.filters);
				}
				if(extraParams.filters.rules){
					var rules = extraParams.filters.rules;
					for(var i=0;i<rules.length;i++){
						currFilters.rules.push(rules[i]);
					}
				}
			}
			extraParams.filters = Ext.JSON.encode(currFilters);
		};
	
		me.listeners = {
			afterrender:function( panel, eOpts ){
				me.add(me.groupPanel);
				me.add(me.btnBar);
				me.buildResult();
			},
			destroy:function(panel, eOpts ){
				me.grid.store.removeListener("beforeload",beforeload,me);
			}
	    }
		
		me.grid.store.addListener("beforeload",beforeload,me);
		
		me.callParent(arguments);
	}
});