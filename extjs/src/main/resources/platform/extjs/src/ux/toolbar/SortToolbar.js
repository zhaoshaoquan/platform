Ext.define('Ext.ux.toolbar.SortToolbar',{
	extend:'Ext.toolbar.Toolbar',
	alias: 'widget.sortToolbar',
	store:null,
	items  : [{
        xtype: 'tbtext',
        text: '排序:',
        reorderable: false
    }, '-'],
    getSorters:function() {
        var sorters = [];
        Ext.each(this.query('button'), function(button) {
            sorters.push(button.sortData);
        }, this);
        return sorters;
    },
    doSort:function(){
		this.store.sort(this.getSorters());
    },
    changeSortDirection:function(button, changeDirection){
    	var sortData = button.sortData,
        iconCls  = button.iconCls;
	    if (sortData) {
	        if (changeDirection !== false) {
	            button.sortData.direction = Ext.String.toggle(button.sortData.direction, "ASC", "DESC");
	            button.setIconCls(Ext.String.toggle(iconCls, "sort-asc", "sort-desc"));
	        }
	        this.store.clearFilter();
	        this.doSort();
	    }
    },
    createSorterBtnConfig:function(config){
    	var $t = this;
    	config = config || {};
        Ext.applyIf(config, {
            listeners: {
                click: function(button, e) {
                	$t.changeSortDirection(button, true);
                }
            },
            iconCls: 'sort-' + config.sortData.direction.toLowerCase(),
            reorderable: true,
            xtype: 'button'
        });
        return config;
    },
    addSort:function(config){
    	this.add(this.createSorterBtnConfig({
	        text: config.text,
	        sortData: {
	            property: config.property,
	            direction: config.direction
	        }
	    }));
    },
	initComponent: function() {
		var $t = this;
		var reorderer = Ext.create('Ext.ux.BoxReorderer', {
	        listeners: {
	            scope: this,
	            Drop: function(r, c, button) {
	                $t.changeSortDirection(button, false);
	            }
	        }
	    });
		var droppable = Ext.create('Ext.ux.ToolbarDroppable', {
	        createItem: function(data) {
	            var header = data.header,
	                headerCt = header.ownerCt,
	                reorderer = headerCt.reorderer;
	            if (reorderer) {
	                reorderer.dropZone.invalidateDrop();
	            }
	            return createSorterButtonConfig({
	                text: header.text,
	                sortData: {
	                    property: header.dataIndex,
	                    direction: "ASC"
	                }
	            });
	        },
	        canDrop: function(dragSource, event, data) {
	            var sorters = $t.getSorters(),
	                header  = data.header,
	                length = sorters.length,
	                entryIndex = this.calculateEntryIndex(event),
	                targetItem = this.toolbar.getComponent(entryIndex),
	                i;
	            if (!header.dataIndex || (targetItem && targetItem.reorderable === false)) {
	                return false;
	            }

	            for (i = 0; i < length; i++) {
	                if (sorters[i].property == header.dataIndex) {
	                    return false;
	                }
	            }
	            return true;
	        },
	        afterLayout: this.doSort
	    });
		this.plugins = [reorderer,droppable];
		this.callParent(arguments);
	}
});