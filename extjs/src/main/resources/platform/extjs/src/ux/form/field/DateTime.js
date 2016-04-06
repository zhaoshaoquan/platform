Ext.define('Ext.ux.form.field.DateTime', {
    extend: 'Ext.form.field.Text',
    alias: 'widget.datetimefield',
    width:220,
    config:{},
    initComponent:function(){
    	var me = this;
    	var config = {errDealMode:1};
    	me.config = Ext.apply(config,me.config);
    	var configStr = Ext.JSON.encode(me.config);
    	var onclick = "WdatePicker("+configStr+")";
    	onclick = onclick.replace(/"/g,"'");
    	me.fieldSubTpl = [ // note: {id} here is really {inputId}, but {cmpId} is available
                   '<input id="{id}" type="{type}" {inputAttrTpl}',
                   ' size="1"', // allows inputs to fully respect CSS widths across all browsers
                   '<tpl if="name"> name="{name}"</tpl>',
                   '<tpl if="value"> value="{[Ext.util.Format.htmlEncode(values.value)]}"</tpl>',
                   '<tpl if="placeholder"> placeholder="{placeholder}"</tpl>',
                   '{%if (values.maxLength !== undefined){%} maxlength="{maxLength}"{%}%}',
                   '<tpl if="readOnly"> readonly="readonly"</tpl>',
                   '<tpl if="disabled"> disabled="disabled"</tpl>',
                   '<tpl if="tabIdx"> tabIndex="{tabIdx}"</tpl>',
                   '<tpl if="fieldStyle"> style="{fieldStyle}"</tpl>',
               ' class="{fieldCls} {typeCls} {editableCls} {inputCls} Wdate" autocomplete="off" onclick="'+onclick+'"/>',
               {
                   disableFormats: true
               }
           ];
    	me.callParent(arguments);
    }
});