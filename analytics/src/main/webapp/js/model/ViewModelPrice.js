/**
 * Просмотр текущей модели -
 */
(function() {

	var info = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : false,
		// root : 'item',
		fields : [ 'equity_fund_ticker', 'company_short_name', 'firm_name',
				'bloomberg_code', 'firm_rating', 'target_price', 'price_date',
				'price_period', 'tr' ],
		sortInfo : {
			field : 'firm_name'
		}
	});

	return new Ext.grid.GridPanel({
		id : 'ViewModelPrice-component',
		title : 'Расчёт по <b>TargetPriceCons12m</b>',
		frame : true,
		closable : true,
		enableHdMenu : false,

		store : info,
		columns : [ {
			header : 'firm_name',
			dataIndex : 'firm_name',
			css : 'color: #0099ff;'
		}, {
			header : 'bloomberg_code',
			dataIndex : 'bloomberg_code'
		}, {
			header : 'firm_rating',
			dataIndex : 'firm_rating'
		}, {
			header : 'target_price',
			dataIndex : 'target_price'
		}, {
			header : 'price_date',
			dataIndex : 'price_date',
			renderer : App.util.Renderer.date()
		}, {
			header : 'price_period',
			dataIndex : 'price_period'
		}, {
			header : 'TR',
			dataIndex : 'tr'
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
