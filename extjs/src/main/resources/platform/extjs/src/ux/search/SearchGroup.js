Ext.define("Ext.ux.search.SearchGroup",{
	extend:"Ext.panel.Panel",
	border:false,
    subGroupCount:0,
    parentGroup:undefined,
    groupIndex:1,
    type:"searchGroup",
    getRules:function(){
    	var me = this;
    	var rules = [];
    	var items = me.items.items;
    	for(var i = 1;i<items.length;i++){
    		var itemPanel = items[i];
    		var type = itemPanel.type;
    		if(type=='ruleGroup'){
    			var rule = itemPanel.getRule();
    			if(rule.op=='BT' && rule.data==''){
    				
    			}else{
    				rules.push(rule);
    			}
    		}
    	}
    	return rules;
    },
    getGroupOp:function(){
    	return this.relateTypeCombo.getValue();
    },
    buildGroups:function(groups){
    	var me = this;
    	var items = me.items.items;
    	for(var i = 1;i<items.length;i++){
    		var itemPanel = items[i];
    		var type = itemPanel.type;
    		if(type=='searchGroup'){
    			itemPanel.buildGroups(groups);
    			var rules = itemPanel.getRules();
    			var groupOp = itemPanel.getGroupOp();
    			var groupItem = {groupOp:groupOp,rules:rules};
    			groups.push(groupItem);
    		}
    	}
    },
    isValid:function(){
    	var me = this;
    	var rules = [];
    	var items = me.items.items;
    	for(var i = 1;i<items.length;i++){
    		var itemPanel = items[i];
    		var type = itemPanel.type;
    		var valid = itemPanel.isValid();
    		if(!valid)return false;
    	}
    	return true;
    },
	initComponent:function(){
		var me = this;
		var columns = me.advancedSearch.getColumns();
		var paddingLeft = 0;
		var pGroup = me.parentGroup;
		var groupNumber = me.groupIndex;
		while(pGroup){
			paddingLeft+=10;
			groupNumber = pGroup.groupIndex + "-" + groupNumber;
			pGroup = pGroup.parentGroup;
		}
		me.padding = '0 0 0 '+paddingLeft;
		
	
		var relateTypeStore = Ext.create('Ext.data.Store', {
			fields: ['val', 'name'],
		    data : [
		        {'val':'AND','name':'并且'},
                {'val':'OR','name':'或者'}
		    ]
		});
		
		me.relateTypeCombo = Ext.create('Ext.form.field.ComboBox',{
			editable:false,
	          queryMode: 'local',
	          displayField: 'name',
	          valueField: 'val',
	          width:60,
	          store: relateTypeStore,
	          value:"AND",
	          listeners:{
	        	  change:function( combo, newValue, oldValue, eOpts ){
	        		  me.advancedSearch.buildResult();
	        	  }
	          }
		});
		
		var actionBar = Ext.create('Ext.panel.Panel', {
			border:false,
		    layout: {
		        type: 'hbox'
		    },
		    defaults: { 
		    	margin:'2 0 0 5'
		    },
		    items: [me.relateTypeCombo,{
		    	  xtype:'button',
		    	  text:"添加子组",
		    	  handler:function(){
		    		  me.subGroupCount = me.subGroupCount+1;
		    		  var groupPanel = Ext.create('Ext.ux.search.SearchGroup',{
	    				advancedSearch:me.advancedSearch,
	    				groupIndex:me.subGroupCount,
	    				parentGroup:me
	    			 });
		    		  me.insert(me.subGroupCount, groupPanel);
		    	  }
		      },{
		    	  xtype:'button',
		    	  text:"添加条件",
		    	  handler:function(){
		    		  var ruleGroup = Ext.create('Ext.ux.search.RuleGroup',{
	    				searchGroup:me
	    			  });
		    		  me.add(ruleGroup);
		    	  }
		      }]
		});
		if(groupNumber!=1){
			var delBtn= Ext.create("Ext.button.Button",{
				text:"删除子组",
				handler:function(){
					me.parentGroup.remove(me);
					me.parentGroup.subGroupCount = me.parentGroup.subGroupCount-1;
					me.advancedSearch.buildResult();
				}
			});
			actionBar.add(delBtn);
		}
		
		var ruleGroup = Ext.create('Ext.ux.search.RuleGroup',{
			searchGroup:me
		});
		
		me.items = [actionBar,ruleGroup];
		
		me.callParent(arguments);
	}
});