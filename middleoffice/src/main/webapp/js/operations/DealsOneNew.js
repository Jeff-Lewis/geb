/**
 * Загрузка единичной сделки
 */
(function() {

	var _tradenum = Ext.id();
	var _batch = Ext.id();
	var _tradeticker = Ext.id();
	var _tradedate = Ext.id();
	var _settleDate = Ext.id();
	var _tradeprice = Ext.id();
	var _quantity = Ext.id();

	var typeDeal = new Ext.form.ComboBox({
		fieldLabel : 'Тип инструмента',
		valueField : 'id',
		displayField : 'name',
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'rest/DealsOneNew/SecurityType.do',
			// root : 'info',
			fields : [ 'id', 'name' ]
		}),
		emptyText : 'Выберите из списка',
		loadingText : 'Поиск...',
		triggerAction : 'all',
		editable : false,
		listeners : {
			select : function(combo, record, index) {
				futureAlias.setDisabled('Future' != record.data.name);
			}
		}
	});

	var tradeOper = new Ext.form.ComboBox({
		fieldLabel : 'Купля/Продажа',
		valueField : 'tradeoper',
		displayField : 'tradeoper',
		mode : 'local',
		store : new Ext.data.ArrayStore({
			autoDestroy : true,
			fields : [ 'id', 'tradeoper' ],
			data : [ [ 0, 'buy' ], [ 1, 'sell' ] ]
		}),
		allowBlank : true,
		emptyText : 'Выберите из списка',
		loadingText : 'Поиск...',
		triggerAction : 'all',
		editable : false
	});

	var currency = new Ext.form.ComboBox({
		emptyText : 'Заполните',
		fieldLabel : 'Валюта',
		displayField : 'name',
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'rest/DealsOneNew/Currencies.do',
			// root : 'info',
			fields : [ 'name' ],
			sortInfo : {
				field : 'name'
			}
		}),
		loadingText : 'Поиск...',
		triggerAction : 'all',
		minChars : 1
	});

	var tradeSystem = new Ext.form.ComboBox({
		fieldLabel : 'Торговая система',
		valueField : 'name',
		displayField : 'name',
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'rest/DealsOneNew/TradeSystems.do',
			// root : 'info',
			fields : [ 'name' ],
			sortInfo : {
				field : 'name'
			}
		}),
		emptyText : 'Выберите из списка',
		loadingText : 'Поиск...',
		triggerAction : 'all',
		editable : false
	});

	var account = new Ext.form.ComboBox({
		fieldLabel : 'Счёт',
		valueField : 'name',
		displayField : 'name',
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'rest/DealsOneNew/Accounts.do',
			// root : 'info',
			fields : [ 'name' ],
			sortInfo : {
				field : 'name'
			}
		}),
		emptyText : 'Выберите из списка',
		loadingText : 'Поиск...',
		triggerAction : 'all',
		editable : false
	});

	var portfolio = new Ext.form.ComboBox({
		fieldLabel : 'Портфель',
		valueField : 'name',
		displayField : 'name',
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'rest/DealsOneNew/Portfolio.do',
			// root : 'info',
			fields : [ 'name' ],
			sortInfo : {
				field : 'name'
			}
		}),
		emptyText : 'Выберите из списка',
		loadingText : 'Поиск...',
		triggerAction : 'all',
		editable : false
	});

	var tickerSelect = new Ext.form.ComboBox({
		emptyText : 'Заполните',
		fieldLabel : 'Тикер Блумберг',
		displayField : 'name',
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'rest/DealsOneNew/Tickers.do',
			// root : 'info',
			fields : [ 'name' ],
			sortInfo : {
				field : 'name'
			}
		}),
		loadingText : 'Поиск...',
		triggerAction : 'all',
		minChars : 2
	});

	var futureAlias = new Ext.form.ComboBox({
		fieldLabel : 'Наименование фьючерса',
		valueField : 'name',
		displayField : 'name',
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'rest/DealsOneNew/Futures.do',
			// root : 'info',
			fields : [ 'id', 'name' ],
			sortInfo : {
				field : 'name'
			}
		}),
		emptyText : 'Выберите из списка',
		loadingText : 'Поиск...',
		triggerAction : 'all',
		editable : false,
		disabled : true
	});

	var container = new Ext.FormPanel(
			{
				border : false,
				baseCls : 'x-plain',
				padding : 10,
				labelWidth : 130,
				width : 320,
				height : 480,
				defaults : {
					width : 150
				},

				items : [ typeDeal, {
					id : _tradenum,
					xtype : 'textfield',
					fieldLabel : 'Номер сделки',
					allowBlank : false,
					emptyText : 'Заполните'
				}, {
					id : _batch,
					xtype : 'numberfield',
					allowDecimals : false,
					fieldLabel : 'Партия'
				}, {
					id : _tradeticker,
					xtype : 'textfield',
					fieldLabel : 'Тикер сделки',
					allowBlank : false,
					emptyText : 'Заполните'
				}, {
					id : _tradedate,
					xtype : 'datefield',
					format : 'd.m.Y',
					fieldLabel : 'Дата сделки',
					allowBlank : false,
					emptyText : 'Заполните'
				}, {
					id : _settleDate,
					xtype : 'datefield',
					format : 'd.m.Y',
					fieldLabel : 'Дата поставки'
				}, tradeOper, {
					id : _tradeprice,
					xtype : 'numberfield',
					decimalPrecision : 6,
					fieldLabel : 'Цена'
				}, {
					id : _quantity,
					xtype : 'numberfield',
					decimalPrecision : 6,
					fieldLabel : 'Количество'
				}, currency, tradeSystem, account, portfolio, tickerSelect,
						futureAlias ],

				buttons : [ {
					text : 'Загрузить',
					handler : save
				}, {
					text : 'Отмена',
					handler : close
				} ],

				setWindow : function(window) {
					this.window = window;
					this.window.setTitle('Загрузка единичной сделки');
				}
			});

	function save(b, e) {
		Ext.Ajax.request({
			url : 'rest/DealsOneNew.do',
			params : {
				tradenum : Ext.getCmp(_tradenum).getValue(),
				batch : Ext.getCmp(_batch).getValue(),
				tradeticker : Ext.getCmp(_tradeticker).getValue(),
				tradedate : App.util.Format.dateYMD(Ext.getCmp(_tradedate)
						.getValue()),
				settleDate : App.util.Format.dateYMD(Ext.getCmp(_settleDate)
						.getValue()),
				tradeoper : tradeOper.getValue(),
				tradeprice : Ext.getCmp(_tradeprice).getValue(),
				quantity : Ext.getCmp(_quantity).getValue(),
				currency : currency.getValue(),
				tradeSystem : tradeSystem.getValue(),
				account : account.getValue(),
				portfolio : portfolio.getValue(),
				tickerSelect : tickerSelect.getValue(),
				futures : futureAlias.getValue(),
				kindTicker : typeDeal.getValue()
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Импорт сделки',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					container.window.close();
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	function close(b, e) {
		container.window.close();
	}

	return container;
})();
