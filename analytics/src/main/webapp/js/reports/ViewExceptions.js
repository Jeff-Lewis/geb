/**
 * Отчёт по исключениям
 */
(function() {

	var store = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : true,
		url : 'rest/ViewExceptions',
		// root : 'info',
		fields : [ 'sec_code', 'rs_code', 'exc', 'r_par' ],
		sortInfo : {
			field : 'sec_code'
		},
		listeners : App.ui.listenersJsonStore()
	});

	return new Ext.grid.GridPanel({
		id : 'ViewExceptions-component',
		title : 'Отчёт по исключениям',
		frame : true,
		closable : true,
		enableHdMenu : false,

		store : store,
		columns : [ {
			header : 'security_code',
			dataIndex : 'sec_code',
			width : 50,
			sortable : true
		}, {
			header : 'related_code',
			dataIndex : 'rs_code',
			width : 50,
			sortable : true
		}, {
			header : 'exc',
			dataIndex : 'exc',
			width : 200
		}, {
			header : 'related_parameter',
			dataIndex : 'r_par',
			width : 100
		} ],
		viewConfig : {
			forceFit : true,
			emptyText : 'Записи не найдены'
		}
	});
})();
