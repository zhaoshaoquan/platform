/**
 * @class Ext.selection.CheckboxModel2
 * @extends Ext.selection.CheckboxModel
 * @author Mac_J
 * @description
 */
Ext.define('Ext.selection.CheckboxModel2', {
	extend : 'Ext.selection.CheckboxModel',

	// override
	onRowMouseDown : function(view, record, item, index, e) {
		view.el.focus();
		var me = this, checker = e.getTarget('.' + Ext.baseCSSPrefix + 'grid-row-checker');

		// checkOnly set, but we didn't click on a checker.
		if (me.checkOnly && !checker) {
			return;
		}
		if (checker) {
			var mode = me.getSelectionMode();
			// dont change the mode if its single otherwise
			// we would get multiple selection
			if (mode !== 'SINGLE') {
				me.setSelectionMode('SIMPLE');
			}
			me.selectWithEvent(record, e);
			me.setSelectionMode(mode);
		} else {
			// Mac_J: here is the key
			e.ctrlKey = true;
			me.selectWithEvent(record, e);
		}
	}
});
