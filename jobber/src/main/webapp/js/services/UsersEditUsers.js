/**
 * Справочник пользователей - Изменение
 */
(function() {

	var idItem = 0;

	var _login = Ext.id();
	var _password = Ext.id();
	var _name = Ext.id();
	var _email = Ext.id();

	var container = new Ext.FormPanel({
		border : false,
		baseCls : 'x-plain',
		padding : 10,
		labelWidth : 70,
		width : 310,
		height : 190,
		defaults : {
			xtype : 'textfield',
			width : 200,
			allowBlank : false
		},

		items : [ {
			id : _login,
			fieldLabel : 'Логин'
		}, {
			id : _password,
			fieldLabel : 'Пароль'
		}, {
			id : _name,
			fieldLabel : 'Ф.И.О.'
		}, {
			id : _email,
			fieldLabel : 'E-Mail'
		} ],

		buttons : [ {
			text : 'Изменить',
			handler : save
		}, {
			text : 'Отмена',
			handler : close
		} ],

		setWindow : function(window) {
			this.window = window;
			this.window.setTitle('Изменение пользователя');
		},
		loadData : function(data) {
			idItem = data.item.id;
			Ext.getCmp(_login).setValue(data.item.login);
			Ext.getCmp(_password).setValue(data.item.password);
			Ext.getCmp(_name).setValue(data.item.name);
			Ext.getCmp(_email).setValue(data.item.email);
		}
	});

	function save(b, e) {
		Ext.Ajax.request({
			url : 'rest/DictUsers/' + idItem + '.do',
			params : {
				login : Ext.getCmp(_login).getValue(),
				password : Ext.getCmp(_password).getValue(),
				name : Ext.getCmp(_name).getValue(),
				email : Ext.getCmp(_email).getValue()
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Сохранение изменений',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					container.window.close();
					Ext.getCmp('Users-component').reloadUsers();
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
