/**
 * Изменение оценок брокеров
 */
(function() {

	var info = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : true,
		url : 'rest/BrokersEstimateChange.do',
		// root : 'info',
		fields : [ 'security', 'broker', 'targetChange', 'pcntChange',
				'recommendation', 'dateInsert' ],
		listeners : App.ui.listenersJsonStore()
	});

	return new Ext.grid.GridPanel({
		id : 'BrokersEstimateChange-component',
		title : 'Изменение оценок брокеров',
		frame : true,
		closable : true,
		enableHdMenu : false,

		store : info,
		columns : [ {
			header : 'Security',
			dataIndex : 'security'
		}, {
			header : 'Broker',
			dataIndex : 'broker'
		}, {
			header : 'Target Change',
			dataIndex : 'targetChange'
		}, {
			header : 'Percent Change',
			dataIndex : 'pcntChange'
		}, {
			header : 'Recommendation',
			dataIndex : 'recommendation'
		}, {
			header : 'Date Insert',
			dataIndex : 'dateInsert'
		} ],
		viewConfig : {
			forceFit : true,
			emptyText : 'Записи не найдены',
		}
	});
})();
