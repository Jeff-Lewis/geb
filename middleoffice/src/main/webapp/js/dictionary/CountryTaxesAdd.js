/**
 * Налоги по странам - добавить
 */
(function() {

	var _security = Ext.id();
	var _country = Ext.id();
	var _broker = Ext.id();
	var _value = Ext.id();
	var _dateBegin = Ext.id();

	var container = new Ext.FormPanel({
		border : false,
		baseCls : 'x-plain',
		padding : 20,
		labelWidth : 160,
		width : 420,
		height : 220,
		defaults : {
			width : 200
		},

		items : [ {
			id : _security,
			xtype : 'combo',
			fieldLabel : 'Тип инструмента',
			valueField : 'id',
			displayField : 'name',
			allowBlank : false,
			emptyText : 'Выберите значение',
			store : new Ext.data.JsonStore({
				autoDestroy : true,
				url : 'rest/CountryTaxes/SecurityType.do',
				// root : 'info',
				fields : [ 'id', 'name' ],
				sortInfo : {
					field : 'name'
				}
			}),
			triggerAction : 'all',
			loadingText : 'Поиск...',
			minChars : 2
		}, {
			id : _country,
			xtype : 'combo',
			fieldLabel : 'Страна',
			valueField : 'id',
			displayField : 'name',
			allowBlank : false,
			emptyText : 'Выберите значение',
			store : new Ext.data.JsonStore({
				autoDestroy : true,
				url : 'rest/CountryTaxes/Countries.do',
				// root : 'info',
				fields : [ 'id', 'name' ],
				sortInfo : {
					field : 'name'
				}
			}),
			triggerAction : 'all',
			loadingText : 'Поиск...',
			minChars : 2
		}, {
			id : _broker,
			xtype : 'combo',
			fieldLabel : 'Брокер',
			valueField : 'id',
			displayField : 'name',
			allowBlank : false,
			emptyText : 'Выберите значение',
			store : new Ext.data.JsonStore({
				autoDestroy : true,
				url : 'rest/CountryTaxes/Brokers.do',
				// root : 'info',
				fields : [ 'id', 'name' ],
				sortInfo : {
					field : 'name'
				}
			}),
			triggerAction : 'all',
			loadingText : 'Поиск...',
			minChars : 2
		}, {
			id : _value,
			xtype : 'numberfield',
			decimalPrecision : 6,
			fieldLabel : 'Значение, %'
		}, {
			id : _dateBegin,
			xtype : 'datefield',
			format : 'd.m.Y',
			fieldLabel : 'Начало периода'
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
			this.window.setTitle('Добавление налога');
			Ext.getCmp(_value).focus();
		}
	});

	function save(b, e) {
		Ext.Ajax.request({
			url : 'rest/CountryTaxes.do',
			params : {
				securityType : Ext.getCmp(_security).getValue(),
				country : Ext.getCmp(_country).getValue(),
				broker : Ext.getCmp(_broker).getValue(),
				value : Ext.getCmp(_value).getValue(),
				dateBegin : App.util.Format.dateYMD(Ext.getCmp(_dateBegin)
						.getValue())
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Сохранение',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					container.window.close();
					Ext.getCmp('CountryTaxes-component').getStore().reload();
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
