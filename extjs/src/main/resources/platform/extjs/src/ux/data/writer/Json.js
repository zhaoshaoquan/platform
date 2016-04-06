Ext.define('Ext.ux.data.writer.Json', {
    extend: 'Ext.data.writer.Json',
    getExpandedData: function(data) {
        var dataLength = data.length,
            i = 0,
            item,
            prop,
            nameParts,
            j,
            tempObj,
            toObject = function(name, value) {
                var o = {};
                o[name] = value;
                return o;
            };
        for (; i < dataLength; i++) {
            item = data[i];
            for (prop in item) {
                if (item.hasOwnProperty(prop)) {
                    nameParts = prop.split('.');
                    j = nameParts.length - 1;
                    if (j > 0) {
                        tempObj = item[prop];
                        if(tempObj != "undefined" && tempObj!=''){
                        	 for (; j > 0; j--) {
                                 tempObj = toObject(nameParts[j], tempObj);
                             }
                             item[nameParts[0]] = item[nameParts[0]] || {};
                             Ext.Object.merge(item[nameParts[0]], tempObj);
                        }
                        delete item[prop];
                    }
                }
            }
        }
        return data;
    }
});