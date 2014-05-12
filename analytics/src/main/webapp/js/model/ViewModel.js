/**
 * Просмотр текущей модели
 */
(function() {

	var store = new Ext.data.GroupingStore({
		autoDestroy : true,
		autoLoad : true,
		url : 'rest/ViewModel.do',
		reader : new Ext.data.JsonReader({
			// root : 'info',
			fields : [ 'id_sec', 'company_short_name', 'industry_group',
					'currentPrice', 'targetPrice', 'targetPriceCons12M',
					'yearHigh', 'yearLow', 'pe_current', 'pe_5', 'pe_10',
					'dividendYield', 'bestPrice', 'deltaBstCur_pct',
					'currentUpside_pct', 'currency' ]
		}),
		sortInfo : {
			field : 'company_short_name'
		},
		listeners : App.ui.listenersJsonStore()
	});

	function rendererDetailPortfolioInfo(value, p, r) {
		var id = r.data.id_sec;
		var onclick = "menu.submitDataRequest(menu, 'model/ViewModelInfo',"
				+ " 'rest/ViewModel/" + id + "/Info.do');";
		return '<b><a onclick="' + onclick + '" href="#">' + value + '</a></b>';
	}

	var gv = new Ext.grid.GroupingView({
		forceFit : true,
		emptyText : 'Записи не найдены',
		enableGrouping : true,
		hideGroupedColumn : false,
		groupName : '',
		showGroupName : false,
		groupTextTpl : '{text} ({[values.rs.length]})'
	});

	return new Ext.grid.GridPanel({
		id : 'ViewModel-component',
		title : 'Текущая модель',
		frame : true,
		closable : true,
		enableHdMenu : false,

		tbar : [ {
			text : 'Обновить',
			handler : function() {
				store.reload();
			}
		}, {
			text : 'Группировать',
			menu : [ {
				text : 'по <b>Сектору</b>',
				handler : function(self) {
					gv.enableGrouping = true;
					gv.grid.store.groupBy('industry_group');
					gv.refresh();
				}
			}, {
				text : 'Не группировать',
				handler : function(self) {
					gv.enableGrouping = false;
					gv.grid.store.clearGrouping();
				}
			} ]
		} ],

		store : store,
		view : gv,
		columns : [ {
			header : 'company_short_name',
			dataIndex : 'company_short_name',
			renderer : rendererDetailPortfolioInfo
		}, {
			header : 'industry_group',
			dataIndex : 'industry_group'
		}, {
			header : 'CurrentPrice',
			dataIndex : 'currentPrice',
			align : 'right'
		}, {
			header : 'BestPrice',
			dataIndex : 'bestPrice',
			align : 'right'
		}, {
			header : 'DeltaBstCur_pct',
			dataIndex : 'deltaBstCur_pct',
			align : 'right'
		}, {
			header : 'CurrentUpside_pct',
			dataIndex : 'currentUpside_pct',
			align : 'right'
		}, {
			header : 'TargetPrice',
			dataIndex : 'targetPrice',
			align : 'right'
		}, {
			header : 'TargetPriceCons12M',
			dataIndex : 'targetPriceCons12M',
			align : 'right'
		}, {
			header : 'YearHigh',
			dataIndex : 'yearHigh',
			align : 'right'
		}, {
			header : 'YearLow',
			dataIndex : 'yearLow',
			align : 'right'
		}, {
			header : 'PE_current',
			dataIndex : 'pe_current',
			align : 'right'
		}, {
			header : 'PE_5',
			dataIndex : 'pe_5',
			align : 'right'
		}, {
			header : 'PE_10',
			dataIndex : 'pe_10',
			align : 'right'
		}, {
			header : 'DividendYield',
			dataIndex : 'dividendYield',
			align : 'right'
		}, {
			header : 'Currency',
			dataIndex : 'currency'
		} ]
	});
})();
