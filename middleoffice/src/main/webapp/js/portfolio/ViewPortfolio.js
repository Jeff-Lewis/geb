/**
 * Текущий портфель
 */
(function() {

	var _date = Ext.id();

	var info = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : false,
		url : 'rest/ViewPortfolio.do',
		// root : 'info',
		fields : [ 'report_date', 'client', 'fund', 'security_code',
				'short_name', 'batch', 'usd_funding', 'currency', 'quantity',
				'avg_price', 'last_price', 'nkd', 'position',
				'position_rep_date', 'revaluation' ],
		listeners : App.ui.listenersJsonStore()
	});

	var futureSelect = new Ext.form.ComboBox({
		width : 150,
		fieldLabel : 'Тикер Блумберг',
		valueField : 'id',
		displayField : 'name',
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'rest/ViewPortfolio/Securities.do',
			// root : 'info',
			fields : [ 'id', 'name' ],
			sortInfo : {
				field : 'name'
			}
		}),
		loadingText : 'Поиск...',
		triggerAction : 'all',
		minChars : 2,
		typeAhead : false
	});

	function portfolioShow() {
		var fd = Ext.getCmp(_date).getValue();

		if (fd == '') {
			App.ui.error('Необходимо выбрать дату!');
			return;
		}

		info.reload({
			params : {
				date : App.util.Format.dateYMD(fd),
				security : futureSelect.getValue()
			}
		});
	}

	function portfolioCalc() {
		var fd = Ext.getCmp(_date).getValue();

		if (fd == '') {
			App.ui.error('Необходимо выбрать дату!');
			return;
		}

		Ext.Ajax.request({
			url : 'rest/ViewPortfolio/Calculate.do',
			params : {
				date : App.util.Format.dateYMD(fd),
				security : futureSelect.getValue()
			},
			timeout : 60 * 60 * 1000, // 60 min
			waitMsg : 'Выполняется расчет портфеля.',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					App.ui.message('Расчёт произведен!');
				} else if (answer.code == 'login') {
					App.ui.sessionExpired();
				} else {
					App.ui.error(answer.message);
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	function portfolioExport() {
		var fd = Ext.getCmp(_date).getValue();

		if (fd == '') {
			App.ui.error('Необходимо выбрать дату!');
			return;
		}

		var url = 'rest/ViewPortfolio/Export.do?date='
				+ App.util.Format.dateYMD(fd) + '&security='
				+ futureSelect.getValue();
		window.open(url);
	}

	return new Ext.grid.GridPanel({
		id : 'ViewPortfolio-component',
		title : 'Текущий потфель',
		frame : true,
		closable : true,
		enableHdMenu : false,

		tbar : {
			xtype : 'panel',
			border : false,
			autoHeight : true,
			layout : 'hbox',
			padding : 5,

			items : [ {
				xtype : 'label',
				style : 'font-weight: bold;',
				margins : '2 5 0 5',
				text : 'Дата:'
			}, {
				id : _date,
				xtype : 'datefield',
				name : 'fromDate',
				margins : '2 5 0 5',
				allowBlank : false,
				format : 'd.m.Y'
			}, {
				xtype : 'label',
				style : 'font-weight: bold;',
				margins : '2 5 0 25',
				text : 'Выбор тикера:'
			}, futureSelect, {
				xtype : 'button',
				text : 'Х',
				margins : '0 5',
				width : 25,
				handler : function() {
					futureSelect.clearValue();
				}
			} ],

			buttonAlign : 'left',
			buttons : [ {
				text : 'Выбрать',
				width : 100,
				handler : portfolioShow
			}, {
				text : 'Рассчитать',
				width : 100,
				handler : portfolioCalc
			}, {
				text : 'Выгрузить в Excel',
				width : 100,
				handler : portfolioExport
			} ]
		},

		store : info,
		columns : [ {
			header : 'REPORT_DATE',
			dataIndex : 'report_date',
			renderer : App.util.Renderer.date()
		}, {
			header : 'CLIENT',
			dataIndex : 'client'
		}, {
			header : 'FUND',
			dataIndex : 'fund'
		}, {
			header : 'SECURITY_CODE',
			dataIndex : 'security_code'
		}, {
			header : 'SHORT_NAME',
			dataIndex : 'short_name'
		}, {
			header : 'BATCH',
			dataIndex : 'batch',
			align : 'right',
			renderer : App.util.Renderer.number(0)
		}, {
			header : 'USD_FUNDING',
			dataIndex : 'usd_funding'
		}, {
			header : 'CURRENCY',
			dataIndex : 'currency'
		}, {
			header : 'QUANTITY',
			dataIndex : 'quantity',
			align : 'right',
			renderer : App.util.Renderer.number(0)
		}, {
			header : 'AVG_PRICE',
			dataIndex : 'avg_price',
			align : 'right',
			renderer : App.util.Renderer.number(6)
		}, {
			header : 'LAST_PRICE',
			dataIndex : 'last_price',
			align : 'right',
			renderer : App.util.Renderer.number(6)
		}, {
			header : 'NKD',
			dataIndex : 'nkd',
			align : 'right',
			renderer : App.util.Renderer.number(6)
		}, {
			header : 'POSITION',
			dataIndex : 'position'
		}, {
			header : 'POSITION_REP_DATE',
			dataIndex : 'position_rep_date',
			align : 'right',
			renderer : App.util.Renderer.number(6)
		}, {
			header : 'REVALUATION',
			dataIndex : 'revaluation',
			align : 'right',
			renderer : App.util.Renderer.number(6)
		} ],

		viewConfig : {
			forceFit : true,
			emptyText : 'Записи не найдены'
		}
	});
})();
