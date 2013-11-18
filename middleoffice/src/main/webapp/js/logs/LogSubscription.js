/**
 * Журнал подписки
 */
(function() {

	var store = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : true,
		autoSave : false,
		url : 'rest/LogSubscription.do',
		// root : 'info',
		fields : [ 'security_type', 'ticker', 'name', 'last', 'lastchange',
				'lastchangetime', 'attention' ],
		sortInfo : {
			field : 'ticker'
		},
		listeners : App.ui.listenersJsonStore()
	});

	return new Ext.grid.GridPanel({
		id : 'ViewSubscription-component',
		title : 'Журнал подписки',
		closable : true,
		frame : true,
		enableHdMenu : false,

		store : store,
		columns : [ {
			header : 'security_type',
			dataIndex : 'security_type',
			width : 20,
			sortable : true
		}, {
			header : 'ticker',
			dataIndex : 'ticker',
			width : 50,
			sortable : true
		}, {
			header : 'name',
			dataIndex : 'name',
			width : 60
		}, {
			header : 'last',
			dataIndex : 'last',
			align : 'right',
			renderer : App.util.Renderer.number(6),
			width : 50
		}, {
			header : 'lastchange',
			dataIndex : 'lastchange',
			align : 'right',
			renderer : App.util.Renderer.number(6),
			width : 50
		}, {
			header : 'lastchangetime',
			dataIndex : 'lastchangetime',
			renderer : App.util.Renderer.datetime('d-m-Y H:i:s'),
			width : 60
		}, {
			header : 'attention',
			dataIndex : 'attention',
			width : 200
		} ],
		viewConfig : {
			forceFit : true,
			emptyText : 'Записи не найдены'
		}
	});
})();
