Ext.define('extjs.ExportExcel',{
	filename: '',
	constructor: function(config){
		Ext.apply(this,config);
	},
	exp: function(list){
		//判断是否有数据，如果没有数据则不导出
		var records = list.getSelectionModel().getSelection();
		if(records.length<1){
			Ext.ux.Msg.error("请选择要导出的数据！");
			return;
		}
		
//		var columns = list.columnManager.headerCt.items.items;
		var columns = list.columns;
		var listWidth = list.getWidth();
		var newCol = [],dataIndex = [],renderer = [],datas = [];
		for(var i=0;i<columns.length;i++){
			var c = columns[i];
			if(c.isCheckerHd||c.xtype=='rownumberer'||c.xtype=='actioncolumn')continue;
			newCol.push(c);
		}
		var headers = buildHeader(newCol,[]);
		for(var i=0;i<records.length;i++){
			var obj = {};
			for(var j=0;j<dataIndex.length;j++){
				if(!renderer[j]){
					obj[dataIndex[j]] = records[i].get(dataIndex[j]);
				}else{
					obj[dataIndex[j]] = renderer[j].call(this,records[i].get(dataIndex[j]),null,records[i]);
				}
			}
			datas.push(obj);
		}
		if(Ext.isEmpty(document.ExcelDataForm)){
			var form = document.createElement('form');
			Ext.fly(form).set({
				method:'POST',
				name:'ExcelDataForm',
				action:contextPath+'/comm/exportExcel'
			});
			document.body.appendChild(form);
			var in1 = document.createElement('input');
			Ext.fly(in1).set({type:'hidden',name:'columns',value:Ext.encode(headers)});
			var in2 = document.createElement('input');
			Ext.fly(in2).set({type:'hidden',name:'data',value:Ext.encode(datas)});
			var in3 = document.createElement('input');
			Ext.fly(in3).set({type:'hidden',name:'filename',value:this.filename});
			form.appendChild(in1);
			form.appendChild(in2);
			form.appendChild(in3);
		}else{
			document.ExcelDataForm.columns.value = Ext.encode(headers);
			document.ExcelDataForm.data.value = Ext.encode(datas);
			document.ExcelDataForm.filename.value = this.filename;
		}
		document.ExcelDataForm.submit();
		
		//递归方法，该方法会遍历所有表头，重新生成一个新表头对象
		function buildHeader(ch,ar){
			for(var i=0;i<ch.length;i++){
				var obj = {};
				obj.text = ch[i].text;
				obj.dataIndex = ch[i].dataIndex;
				if(!Ext.isEmpty(ch[i].dataIndex)){
					renderer.push(ch[i].renderer);
					dataIndex.push(ch[i].dataIndex);
				}
				obj.width = Ext.isEmpty(ch[i].width)?((i+1<ch.length)?(ch[i+1].x-ch[i].x):(listWidth-ch[i].x)):ch[i].width;
				obj.align = ch[i].align;
				obj.hidden = ch[i].hidden;
				if(ch[i].isGroupHeader){
					obj.columns = buildHeader(ch[i].items.items,[]);
				}
				ar.push(obj);
			}
			return ar;
		}
	}
});