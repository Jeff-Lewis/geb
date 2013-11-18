/**
 * Праздники
 */
(function() {

	var _date = Ext.id();
	var _name = Ext.id();
	var _sms = Ext.id();
	var _portfolio = Ext.id();
	var _time_start = Ext.id();
	var _time_stop = Ext.id();

	var countrySelect = new Ext.form.ComboBox({
		fieldLabel : 'Страна',
		width : 150,
		valueField : 'name',
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'rest/Holidays/Countries.do',
			// root : 'info',
			fields : [ 'id', 'name' ]
		}),
		allowBlank : false,
		emptyText : 'Выберите страну',
		displayField : 'name',
		loadingText : 'Поиск...',
		triggerAction : 'all',
		editable : false
	});

	var container = new Ext.FormPanel({
		border : false,
		baseCls : 'x-plain',
		padding : 20,
		labelWidth : 160,
		width : 420,
		height : 280,

		items : [ countrySelect, {
			id : _date,
			xtype : 'datefield',
			fieldLabel : 'Дата праздника',
			format : 'd.m.Y',
			allowBlank : false
		}, {
			id : _name,
			xtype : 'textfield',
			fieldLabel : 'Название праздника',
			width : 200,
			allowBlank : false
		}, {
			id : _time_start,
			xtype : 'textfield',
			fieldLabel : 'Время (начало)',
			width : 200,
			allowBlank : false,
			value : '00:00'
		}, {
			id : _time_stop,
			xtype : 'textfield',
			fieldLabel : 'Время (окончание)',
			width : 200,
			allowBlank : false,
			value : '23:59'
		}, {
			id : _sms,
			xtype : 'checkbox',
			fieldLabel : 'sms',
			inputValue : '1'
		}, {
			id : _portfolio,
			xtype : 'checkbox',
			fieldLabel : 'portfolio',
			inputValue : '1'
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
			this.window.setTitle('Добавление нового праздника');
		}
	});

	function save(b, e) {
		Ext.Ajax.request({
			url : 'rest/Holidays.do',
			params : {
				date : App.util.Format.dateYMD(Ext.getCmp(_date).getValue()),
				name : Ext.getCmp(_name).getValue(),
				times : Ext.getCmp(_time_start).getValue(),
				timee : Ext.getCmp(_time_stop).getValue(),
				country : countrySelect.getValue(),
				sms : Ext.getCmp(_sms).getValue(),
				portfolio : Ext.getCmp(_portfolio).getValue()
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Сохранение нового контакта',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					container.window.close();
					menu.submitDataRequest(menu, 'dictionary/Holidays',
							'rest/Holidays.do');
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
