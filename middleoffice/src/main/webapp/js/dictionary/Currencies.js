/**
 * Валюты
 */
(function() {

	var info = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : true,
		autoSave : false,
		url : 'rest/Currencies.do',
		// root : 'info',
		fields : [ 'code', 'iso', 'name' ],
		sortInfo : {
			field : 'name'
		},
		listeners : App.ui.listenersJsonStore()
	});

	return new Ext.grid.GridPanel({
		id : 'Currencies-component',
		title : 'Валюты',
		closable : true,
		frame : true,
		enableHdMenu : false,

		store : info,
		columns : [ new Ext.grid.RowNumberer({
			width : 30
		}), {
			header : 'Код',
			dataIndex : 'code',
			align : 'center',
			width : 50
		}, {
			header : 'ISO',
			dataIndex : 'iso',
			align : 'center',
			width : 50
		}, {
			id : 'name',
			header : 'Валюта',
			dataIndex : 'name'
		} ],
		autoExpandColumn : 'name',
		viewConfig : {
			// autoFill : true,
			// forceFit : true,
			emptyText : 'Записи не найдены'
		}
	});
})();
