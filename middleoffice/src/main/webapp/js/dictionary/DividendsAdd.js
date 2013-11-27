/**
 * Дивиденды - добавить
 */
(function() {

	var _security = Ext.id();
	var _account = Ext.id();
	var _currency = Ext.id();
	var _record_date = Ext.id();
	var _receive_date = Ext.id();
	var _quantity = Ext.id();
	var _dividend_per_share = Ext.id();
	var _extra_costs_per_share = Ext.id();

	var container = new Ext.FormPanel({
		border : false,
		baseCls : 'x-plain',
		padding : 20,
		labelWidth : 160,
		width : 420,
		height : 310,
		defaults : {
			width : 200
		},

		items : [ {
			id : _security,
			xtype : 'combo',
			fieldLabel : 'Инструмент',
			displayField : 'name',
			valueField : 'name',
			store : new Ext.data.JsonStore({
				autoDestroy : true,
				url : 'rest/Dividends/Equities.do',
				// root : 'info',
				fields : [ 'id', 'name' ],
				sortInfo : {
					field : 'name'
				}
			}),
			loadingText : 'Поиск...',
			minChars : 2,
			triggerAction : 'all',
			emptyText : 'Заполните',
			allowBlank : false
		}, {
			id : _account,
			xtype : 'combo',
			fieldLabel : 'Счёт',
			displayField : 'name',
			valueField : 'name',
			store : new Ext.data.JsonStore({
				autoDestroy : true,
				url : 'rest/Dividends/BrokerAccounts.do',
				// root : 'info',
				fields : [ 'id', 'name' ],
				sortInfo : {
					field : 'name'
				}
			}),
			loadingText : 'Поиск...',
			minChars : 2,
			triggerAction : 'all',
			emptyText : 'Заполните',
			allowBlank : false
		}, {
			id : _currency,
			xtype : 'combo',
			fieldLabel : 'Валюта',
			displayField : 'name',
			valueField : 'name',
			store : new Ext.data.JsonStore({
				autoDestroy : true,
				url : 'rest/Dividends/Currency.do',
				// root : 'info',
				fields : [ 'id', 'name' ],
				sortInfo : {
					field : 'name'
				}
			}),
			loadingText : 'Поиск...',
			minChars : 2,
			triggerAction : 'all',
			emptyText : 'Заполните',
			allowBlank : false
		}, {
			id : _record_date,
			xtype : 'datefield',
			format : 'd.m.Y',
			fieldLabel : 'Дата закрытия реестра',
			emptyText : 'Заполните',
			allowBlank : false
		}, {
			id : _receive_date,
			xtype : 'datefield',
			format : 'd.m.Y',
			fieldLabel : 'Дата получения'
		}, {
			id : _quantity,
			xtype : 'numberfield',
			allowDecimals : false,
			fieldLabel : 'Количество',
			emptyText : 'Заполните',
			allowBlank : false
		}, {
			id : _dividend_per_share,
			xtype : 'numberfield',
			decimalPrecision : 6,
			fieldLabel : 'Дивиденды',
			emptyText : 'Заполните',
			allowBlank : false
		}, {
			id : _extra_costs_per_share,
			xtype : 'numberfield',
			decimalPrecision : 6,
			fieldLabel : 'Доп. затраты'
		} ],

		buttons : [ {
			text : 'Добавить',
			handler : save
		}, {
			text : 'Отмена',
			handler : close
		} ],

		setWindow : function(window) {
			this.window = window;
			this.window.setTitle('Добавление дивиденд');
		}
	});

	function save(b, e) {
		var rrd = Ext.getCmp(_record_date).getValue();
		var rcd = Ext.getCmp(_receive_date).getValue();
		Ext.Ajax.request({
			url : 'rest/Dividends/Add.do',
			params : {
				securityId : App.Combo.getValueId(Ext.getCmp(_security)),
				accountId : App.Combo.getValueId(Ext.getCmp(_account)),
				currencyId : App.Combo.getValueId(Ext.getCmp(_currency)),
				dateRecord : App.util.Format.dateYMD(rrd),
				dateReceive : App.util.Format.dateYMD(rcd),
				quantity : Ext.getCmp(_quantity).getValue(),
				dividend : Ext.getCmp(_dividend_per_share).getValue(),
				extraCost : Ext.getCmp(_extra_costs_per_share).getValue()
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Сохранение',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					container.window.close();
					Ext.getCmp('Dividends-component').refresh();
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

	function close(b, e) {
		container.window.close();
	}

	return container;
})();