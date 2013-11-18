/**
 * Просмотр текущей модели
 */
(function() {

	var store = new Ext.data.GroupingStore({
		autoDestroy : true,
		autoLoad : true,
		url : 'rest/ViewModel/Current.do',
		reader : new Ext.data.JsonReader({
			// root : 'info',
			fields : [ 'id_sec', 'company_short_name', 'industry_group',
					'CurrentPrice', 'TargetPrice', 'TargetPriceCons12M',
					'YearHigh', 'YearLow', 'PE_current', 'PE_5', 'PE_10',
					'DividendYield', 'BestPrice', 'DeltaBstCur_pct',
					'CurrentUpside_pct', 'Currency' ]
		}),
		sortInfo : {
			field : 'company_short_name'
		},
		listeners : App.ui.listenersJsonStore()
	});

	function rendererDetailPortfolioInfo(value, p, r) {
		var id = r.data.id_sec;
		var onclick = "menu.submitDataRequest(menu, 'model/ViewModelInfo',"
				+ " 'rest/ViewModel/" + id + ".do');";
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
			sortable : true,
			width : 20,
			renderer : rendererDetailPortfolioInfo
		}, {
			header : 'industry_group',
			dataIndex : 'industry_group',
			width : 20
		}, {
			header : 'CurrentPrice',
			dataIndex : 'CurrentPrice',
			align : 'right',
			width : 20,
			sortable : true
		}, {
			header : 'BestPrice',
			dataIndex : 'BestPrice',
			align : 'right',
			width : 20
		}, {
			header : 'DeltaBstCur_pct',
			dataIndex : 'DeltaBstCur_pct',
			align : 'right',
			width : 20
		}, {
			header : 'CurrentUpside_pct',
			dataIndex : 'CurrentUpside_pct',
			align : 'right',
			width : 20
		}, {
			header : 'TargetPrice',
			dataIndex : 'TargetPrice',
			align : 'right',
			width : 20
		}, {
			header : 'TargetPriceCons12M',
			dataIndex : 'TargetPriceCons12M',
			align : 'right',
			width : 20
		}, {
			header : 'YearHigh',
			dataIndex : 'YearHigh',
			align : 'right',
			width : 20
		}, {
			header : 'YearLow',
			dataIndex : 'YearLow',
			align : 'right',
			width : 20
		}, {
			header : 'PE_current',
			dataIndex : 'PE_current',
			align : 'right',
			width : 20
		}, {
			header : 'PE_5',
			dataIndex : 'PE_5',
			align : 'right',
			width : 20
		}, {
			header : 'PE_10',
			dataIndex : 'PE_10',
			align : 'right',
			width : 20
		}, {
			header : 'DividendYield',
			dataIndex : 'DividendYield',
			align : 'right',
			width : 20
		}, {
			header : 'Currency',
			dataIndex : 'Currency',
			width : 20
		} ]
	});
})();
