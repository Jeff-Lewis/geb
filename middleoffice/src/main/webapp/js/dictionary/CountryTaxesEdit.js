/**
 * Налоги по странам - редактировать
 */
(function() {

	var idItem = 0;

	var _security = Ext.id();
	var _country = Ext.id();
	var _broker = Ext.id();
	var _value = Ext.id();
	var _dateBegin = Ext.id();
	var _dateEnd = Ext.id();

	var container = new Ext.FormPanel({
		border : false,
		baseCls : 'x-plain',
		padding : 20,
		labelWidth : 160,
		width : 420,
		height : 240,
		defaults : {
			width : 200
		},

		items : [ {
			id : _security,
			xtype : 'textfield',
			fieldLabel : 'Тип инструмента',
			readOnly : true
		}, {
			id : _country,
			xtype : 'textfield',
			fieldLabel : 'Страна',
			readOnly : true
		}, {
			id : _broker,
			xtype : 'textfield',
			fieldLabel : 'Брокер',
			readOnly : true
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
		}, {
			id : _dateEnd,
			xtype : 'datefield',
			format : 'd.m.Y',
			fieldLabel : 'Конец периода'
		} ],

		buttons : [ {
			text : 'Сохранить',
			handler : save
		}, {
			text : 'Отмена',
			handler : close
		} ],

		loadData : function(data) {
			idItem = data.item.id;
			Ext.getCmp(_security).setValue(data.item.security_type);
			Ext.getCmp(_country).setValue(data.item.name);
			Ext.getCmp(_broker).setValue(data.item.broker);
			Ext.getCmp(_value).setValue(data.item.value);
			Ext.getCmp(_dateBegin).setValue(
					App.util.Renderer.date()(data.item.date_begin));
			Ext.getCmp(_dateEnd).setValue(
					App.util.Renderer.date()(data.item.date_end));
		},
		setWindow : function(window) {
			this.window = window;
			this.window.setTitle('Редактирование налога');
		}
	});

	function save(b, e) {
		Ext.Ajax.request({
			url : 'rest/CountryTaxes/' + idItem + '.do',
			params : {
				value : Ext.getCmp(_value).getValue(),
				dateBegin : App.util.Format.dateYMD(Ext.getCmp(_dateBegin)
						.getValue()),
				dateEnd : App.util.Format.dateYMD(Ext.getCmp(_dateEnd)
						.getValue())
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Сохранение изменений',
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
