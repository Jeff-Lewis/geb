/**
 * Изменение оценок брокеров
 */
(function() {

	var info = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : true,
		url : 'rest/BrokersEstimateChange.do',
		// root : 'info',
		fields : [ 'Security', 'Broker', 'targetChange', 'pcntChange',
				'Recommendation', 'DateInsert' ],
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
			dataIndex : 'Security'
		}, {
			header : 'Broker',
			dataIndex : 'Broker'
		}, {
			header : 'Target Change',
			dataIndex : 'targetChange'
		}, {
			header : 'Percent Change',
			dataIndex : 'pcntChange'
		}, {
			header : 'Recommendation',
			dataIndex : 'Recommendation'
		}, {
			header : 'Date Insert',
			dataIndex : 'DateInsert'
		} ],
		viewConfig : {
			forceFit : true,
			emptyText : 'Записи не найдены',
		}
	});
})();
