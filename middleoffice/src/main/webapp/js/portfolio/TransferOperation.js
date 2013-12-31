/**
 * Список перекидок - детализация
 */
(function() {

	var info = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : false,
		//url : 'portfolio/TransferOperations/{id}.do',
		//root : 'info',
		fields : [ 'id', 'batch', 'security', 'operation', 'quantity', 'price',
				'currency', 'tradeDate', 'tradeSystem', 'broker', 'account',
				'client', 'portfolio', 'funding' ],
		listeners : App.ui.listenersJsonStore()
	});

	return new Ext.grid.GridPanel({
		id : 'TransferOperation-component',
		title : 'Детализация перекидки',
		frame : true,
		closable : true,
		enableHdMenu : false,

		store : info,
		columns : [ new Ext.grid.RowNumberer({
			width : 35
		}), {
			header : 'Batch',
			dataIndex : 'batch'
		}, {
			header : 'Security',
			dataIndex : 'security'
		}, {
			header : 'Operation',
			dataIndex : 'operation'
		}, {
			header : 'Price',
			dataIndex : 'price',
			align : 'right',
			renderer : App.util.Renderer.number(3)
		}, {
			header : 'Currency',
			dataIndex : 'currency'
		}, {
			header : 'TradeDate',
			dataIndex : 'tradeDate',
			renderer : App.util.Renderer.date(),
			width : 50
		}, {
			header : 'TradeSystem',
			dataIndex : 'tradeSystem'
		}, {
			header : 'Broker',
			dataIndex : 'broker'
		}, {
			header : 'Account',
			dataIndex : 'account'
		}, {
			header : 'Client',
			dataIndex : 'client'
		}, {
			header : 'Portfolio',
			dataIndex : 'portfolio'
		}, {
			header : 'Funding',
			dataIndex : 'funding',
			width : 30,
			align : 'center',
			renderer : App.util.Renderer.bool()
		} ],
		viewConfig : {
			forceFit : true,
			emptyText : 'Записи не найдены'
		},

		loadData : function(data) {
			info.loadData(data.item);
		}
	});
})();
