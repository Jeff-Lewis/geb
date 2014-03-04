/**
 * Купоны (погашение) - добавить
 */
(function() {

	var container;

	var _security = Ext.id();
	var _account = Ext.id();
	var _currency = Ext.id();
	var _record_date = Ext.id();
	var _receive_date = Ext.id();
	var _quantity = Ext.id();
	var _coupon_per_share = Ext.id();
	var _extra_costs_per_share = Ext.id();
	var _operations = Ext.id();

	function save(self) {
		Ext.Ajax.request({
			url : 'coupons/CouponsEdit-add.html',
			params : {
				securityId : Ext.getCmp(_security).getValue(),
				accountId : Ext.getCmp(_account).getValue(),
				currencyId : Ext.getCmp(_currency).getValue(),
				dateRecord : App.util.Format.dateYMD(Ext.getCmp(_record_date)
						.getValue()),
				dateReceive : App.util.Format.dateYMD(Ext.getCmp(_receive_date)
						.getValue()),
				quantity : Ext.getCmp(_quantity).getValue(),
				coupon : Ext.getCmp(_coupon_per_share).getValue(),
				extraCost : Ext.getCmp(_extra_costs_per_share).getValue(),
				operation : Ext.getCmp(_operations).getValue()
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Сохранение',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					container.window.close();
					Ext.getCmp('coupons-grid-component').refresh();
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

	container = new Ext.FormPanel({
		border : false,
		baseCls : 'x-plain',
		padding : 20,
		labelWidth : 160,
		width : 420,
		height : 330,
		defaults : {
			width : 200
		},

		items : [ {
			id : _security,
			xtype : 'combo',
			fieldLabel : 'Инструмент',
			displayField : 'name',
			valueField : 'id',
			store : new Ext.data.JsonStore({
				autoDestroy : true,
				url : 'utils/bonds-cb.html',
				root : 'info',
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
			valueField : 'id',
			store : new Ext.data.JsonStore({
				autoDestroy : true,
				url : 'dictionary/brokeraccounts/broker-accounts-cb.html',
				root : 'info',
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
			valueField : 'id',
			store : new Ext.data.JsonStore({
				autoDestroy : true,
				url : 'utils/choose-currency.html',
				root : 'info',
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
			id : _coupon_per_share,
			xtype : 'numberfield',
			decimalPrecision : 6,
			fieldLabel : 'Купон',
			emptyText : 'Заполните',
			allowBlank : false
		}, {
			id : _extra_costs_per_share,
			xtype : 'numberfield',
			decimalPrecision : 6,
			fieldLabel : 'Доп. затраты'
		}, {
			id : _operations,
			xtype : 'combo',
			fieldLabel : 'Операция',
			displayField : 'name',
			valueField : 'id',
			store : new Ext.data.JsonStore({
				autoDestroy : true,
				url : 'utils/CouponsOperations-cb.html',
				root : 'info',
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
		} ],

		buttons : [ {
			text : 'Добавить',
			handler : save

		}, {
			text : 'Отмена',
			handler : function() {
				container.window.close();
			}
		} ],

		setWindow : function(window) {
			this.window = window;
			this.window.setTitle('Добавление купона');
		}
	});

	return container;
})();
