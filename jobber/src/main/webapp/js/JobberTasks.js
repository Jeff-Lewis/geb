/**
 * Список заданий
 */
(function() {

	var store = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : true,
		autoSave : false,
		url : 'JobberTasks',
		// root : 'info',
		fields : [ 'name', 'cron', 'enabled', 'description' ],
		// sortInfo : {
		// field : 'ticker'
		// },
		listeners : App.ui.listenersJsonStore()
	});

	return new Ext.grid.GridPanel({
		id : 'JobberTasks-component',
		title : 'Список заданий',
		closable : true,
		frame : true,
		enableHdMenu : false,

		store : store,
		columns : [ {
			header : 'enabled',
			dataIndex : 'enabled',
			align : 'center',
			width : 50,
			renderer : App.util.Renderer.bool(),
			sortable : true
		}, {
			header : 'name',
			dataIndex : 'name',
			width : 100,
			sortable : true
		}, {
			header : 'cron',
			dataIndex : 'cron',
			width : 150,
			sortable : true
		}, {
			header : 'description',
			dataIndex : 'description'
		} ],
		viewConfig : {
			forceFit : true,
			emptyText : 'Записи не найдены'
		}
	});
})();
