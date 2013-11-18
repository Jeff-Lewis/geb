/**
 * Не хватает котировок
 */
(function() {

	var info = new Ext.data.JsonStore(
			{
				autoDestroy : true,
				autoLoad : true,
				url : 'rest/NotEnoughQuotations.do',
				// root : 'info',
				fields : [ 'id_sec', 'SecurityCode', 'SecurityType',
						'FirstTradeDate' ],
				listeners : App.ui.listenersJsonStore()
			});

	return new Ext.grid.GridPanel({
		id : 'NotEnoughQuotations-component',
		title : 'Не хватает котировок',
		closable : true,
		frame : true,
		enableHdMenu : false,

		tbar : {
			buttons : [ {
				text : 'Обновить',
				handler : function() {
					info.reload();
				}
			} ]
		},

		store : info,
		columns : [ {
			header : 'SecurityCode',
			dataIndex : 'SecurityCode'
		}, {
			header : 'SecurityType',
			dataIndex : 'SecurityType'
		}, {
			header : 'FirstTradeDate',
			dataIndex : 'FirstTradeDate',
			// align : 'center',
			renderer : App.util.Renderer.date(),
			width : 40
		} ],
		viewConfig : {
			forceFit : true,
			emptyText : 'Записи не найдены'
		}
	});
})();
