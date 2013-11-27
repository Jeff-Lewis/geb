/**
 * Просмотр текущей модели -
 */
(function() {

	var info = new Ext.data.JsonStore({
		autoDestroy : true,
		// root : 'info',
		fields : [ 'equity_fund_ticker', 'company_short_name', 'firm_name',
				'bloomberg_code', 'firm_rating', 'target_price', 'price_date',
				'price_period', 'TR' ],
		sortInfo : {
			field : 'firm_name'
		}
	});

	return new Ext.grid.GridPanel({
		id : 'ViewModelPrice-component',
		title : 'Расчёт по <br/><b>Target Price Cons 12m</b>',
		frame : true,
		closable : true,
		autoScroll : true,
		enableHdMenu : false,

		store : info,
		columns : [ {
			header : 'firm_name',
			dataIndex : 'firm_name',
			width : 40,
			css : 'color: #0099ff;'
		}, {
			header : 'bloomberg_code',
			dataIndex : 'bloomberg_code',
			width : 40
		}, {
			header : 'firm_rating',
			dataIndex : 'firm_rating',
			width : 40,
		}, {
			header : 'target_price',
			dataIndex : 'target_price',
			width : 40
		}, {
			header : 'price_date',
			dataIndex : 'price_date',
			width : 40
		}, {
			header : 'price_period',
			dataIndex : 'price_period',
			width : 40
		}, {
			header : 'TR',
			dataIndex : 'TR',
			width : 40
		} ],
		viewConfig : {
			forceFit : true,
			emptyText : 'Записи не найдены'
		},

		loadData : function(data) {
			info.loadData(data);
		}
	});
})();
