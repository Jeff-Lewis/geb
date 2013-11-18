(function() {

	var _id = 0;
	var _full_name = Ext.id();
	var _rating = Ext.id();
	var _bloomberg_code = Ext.id();
	var _cover_russian = Ext.id();
	var _short_name = Ext.id();

	var container = new Ext.form.FormPanel({
		border : false,
		baseCls : 'x-plain',
		padding : 20,
		labelWidth : 160,
		width : 420,
		height : 260,
		defaults : {
			emptyText : 'Заполните',
			width : 200
		},

		items : [ {
			id : _full_name,
			fieldLabel : 'Наименование',
			xtype : 'textfield'
		}, {
			id : _rating,
			fieldLabel : 'Рейтинг',
			xtype : 'numberfield',
			allowDecimals : false
		}, {
			id : _bloomberg_code,
			fieldLabel : 'Код блумберг',
			xtype : 'textfield'
		}, {
			id : _cover_russian,
			fieldLabel : 'Расчет РЭ',
			xtype : 'checkbox'
		}, {
			id : _short_name,
			fieldLabel : 'Наименование краткое',
			xtype : 'textfield'
		} ],

		buttons : [ {
			text : 'Сохранить',
			handler : save
		}, {
			text : 'Отмена',
			handler : close
		} ],

		loadData : function(data) {
			_id = data.id;
			Ext.getCmp(_full_name).setValue(data.full_name);
			Ext.getCmp(_rating).setValue(data.rating);
			Ext.getCmp(_bloomberg_code).setValue(data.bloomberg_code);
			Ext.getCmp(_cover_russian).setValue(data.cover_russian);
			Ext.getCmp(_short_name).setValue(data.short_name);
		},
		setWindow : function(window) {
			this.window = window;
			this.window.setTitle('Редактирование брокера');
		}
	});

	function save(self) {
		Ext.Ajax.request({
			url : 'rest/Brokers/' + _id + '.do',
			params : {
				fullName : Ext.getCmp(_full_name).getValue(),
				rating : Ext.getCmp(_rating).getValue(),
				bloombergCode : Ext.getCmp(_bloomberg_code).getValue(),
				coverRussian : Ext.getCmp(_cover_russian).getValue() ? 1 : 0,
				shortName : Ext.getCmp(_short_name).getValue()
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Сохранение',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					container.window.close();
					Ext.getCmp('Brokers-component').getStore().reload();
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

	function close() {
		container.window.close();
	}

	return container;
})();
