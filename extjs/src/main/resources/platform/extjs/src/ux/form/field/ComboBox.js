Ext.define("Ext.ux.form.field.ComboBox",{
	extend:"Ext.form.field.ComboBox",
	getSubmitData: function() {
        var me = this,
            data = null,
            val;
        if (!me.disabled && me.submitValue && !me.isFileUpload()) {
            val = me.getSubmitValue();
            if (val !== null && val!="") {
                data = {};
                data[me.getName()] = val;
            }
        }
        return data;
    }
});