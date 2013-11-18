/**
 * Dictionary
 */
(function() {

	var store = new Ext.data.JsonStore({
		autoDestroy : true,
		root : 'info',
		fields : [ 'equity_fund_ticker', 'company_short_name', 'firm_name',
				'bloomberg_code', 'firm_rating', 'target_price', 'price_date',
				'price_period', 'TR' ],
		sortInfo : {
			field : 'firm_name',
			direction : 'ASC'
		}
	});

	return new Ext.grid.GridPanel({
		id : 'view-target-price-cons12m-data-grid-component',
		frame : true,
		closable : true,
		autoScroll : true,
		enableHdMenu : false,
		store : store,
		columns : [ {
			header : 'firm_name',
			dataIndex : 'firm_name',
			sortable : true,
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
			this.setTitle('Расчёт по <br/><b>Target Price Cons 12m</b>');
			this.getStore().loadData(data);
		}
	});
})();
