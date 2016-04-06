Ext.apply(Ext.form.field.VTypes,{
	//使用方法 在from中加
    //vtype: 'phone'
	phone:function(val,field){
		 var reg = /^((\d{3,4})*\d{7,8}(\d{3,4})*|13\d{9})$/;    
         if(!reg.test(val)) {    
             return false;    
         }    
         if(val.length > 11)  {
        	 return false; 
         }  
         return true;
//		 return /^((\d{3,4})*\d{7,8}(\d{3,4})*|13\d{9})$/ .test(val);
	},    
	phoneText:'请输入正确的手机号码',   
    //身份证号
    IDNo:function(val,field){
    	var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
        return reg.test(val);
    },
    IDNoText: '身份证号不合法',
    //中文验证
    chinese:function(val,field){
    	var reg = /^[\u4e00-\u9fa5]+$/;
		return reg.test(val);
    },
    chineseText:"请输入中文内容",
    //图片格式验证
    image:function(val,field){
    	var reg = new RegExp(/^.*\.(jpg|png|gif)$/ig);
    	return reg.test(val);
    },
    imageText:"文件格式不对，只支持jpg,png,gif",
    xml:function(val,field){
    	var reg = new RegExp(/^.*\.(xml)$/ig);
    	return reg.test(val);
    },
    xmlText:'文件格式不对，只支持XML格式',
    zip:function(val,field){
    	var reg = new RegExp(/^.*\.(zip)$/ig);
    	return reg.test(val);
    },
    zipText:'文件格式不对，只支持ZIP格式',
    xls:function(val,field){
    	var reg = new RegExp(/^.*\.(xls)$/ig);
    	return reg.test(val);
    },
    xlsText:'文件格式不对，只支持XLS格式',
    //联系电话验证
    telephone:function(val,field){
    	var reg = /((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/;
    	return reg.test(val);
    },
    telephoneText:'电话号码不合法',
    daterange: function(val, field) {
        var date = field.parseDate(val);

        if (!date) {
            return false;
        }
        if (field.startDateField && (!this.dateRangeMax || (date.getTime() != this.dateRangeMax.getTime()))) {
            var start = field.up('form').down('#' + field.startDateField);
            start.setMaxValue(date);
            start.validate();
            this.dateRangeMax = date;
        }
        else if (field.endDateField && (!this.dateRangeMin || (date.getTime() != this.dateRangeMin.getTime()))) {
            var end = field.up('form').down('#' + field.endDateField);
            end.setMinValue(date);
            end.validate();
            this.dateRangeMin = date;
        }
        /*
         * Always return true since we're only using this vtype to set the
         * min/max allowed values (these are tested for after the vtype test)
         */
        return true;
    },

    daterangeText: '开始日期必须小于结束日期'
});