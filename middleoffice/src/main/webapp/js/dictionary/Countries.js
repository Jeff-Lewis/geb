/**
 * Страны
 */
(function() {

	var info = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : true,
		autoSave : false,
		url : 'rest/Countries.do',
		// root : 'info',
		fields : [ 'id', 'name', 'alpha2', 'alpha3', 'iso' ],
		sortInfo : {
			field : 'name'
		},
		listeners : App.ui.listenersJsonStore()
	});

	return new Ext.grid.GridPanel({
		id : 'Countries-component',
		title : 'Страны',
		closable : true,
		frame : true,
		enableHdMenu : false,

		store : info,
		columns : [ new Ext.grid.RowNumberer(), {
			header : 'Страна',
			dataIndex : 'name',
			width : 75
		}, {
			header : 'Код',
			dataIndex : 'alpha2',
			width : 25
		}, {
			header : 'Код',
			dataIndex : 'alpha3',
			width : 25
		}, {
			header : 'ISO',
			dataIndex : 'iso',
			width : 25
		} ],
		viewConfig : {
			forceFit : true,
			emptyText : 'Записи не найдены'
		}
	});
})();
