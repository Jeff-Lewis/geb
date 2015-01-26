/**
 * Агенты БЛУМБЕРГа
 */
(function() {

	var stInfo = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : true,
		url : 'Agents',
		// root : 'info',
		fields : [ 'host', 'time', 'status' ],
		listeners : App.ui.listenersJsonStore()
	});

	var smInfo = new Ext.grid.RowSelectionModel({
		singleSelect : true
	});

	return new Ext.grid.GridPanel({
		id : 'ViewAgents-component',
		title : 'Агенты БЛУМБЕРГа',
		frame : true,
		closable : true,
		enableHdMenu : false,

		tbar : [ {
			text : 'Обновить',
			handler : function() {
	            stInfo.reload();
            }
		} ],

		store : stInfo,
		selModel : smInfo,
		columns : [ new Ext.grid.RowNumberer({
			width : 30
		}), {
			header : 'Agent IP',
			dataIndex : 'host',
			width : 100
		}, {
			header : 'Last active time',
			dataIndex : 'time',
			width : 100
		}, {
			header : 'Status',
			dataIndex : 'status'
		} ],
		viewConfig : {
			forceFit : true,
			emptyText : 'Записи не найдены'
		}
	});
})();
