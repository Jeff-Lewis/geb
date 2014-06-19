/*<%@ page pageEncoding="utf-8" %>
 * 
 */

/**
 * Login
 */
function login() {
	var win = new Ext.Window({
		title : 'Проверка пользователя',
		layout : 'form',
		plain : true,
		modal : true,
		width : 340,
		height : 180,
		padding : 10,
		// border : false,
		labelAlign : 'top',
		defaults : {
			width : 300
		},
		items : [ {
			id : 'username',
			xtype : 'textfield',
			name : 'j_username',
			inputType : 'text',
			fieldLabel : 'Логин',
			allowBlank : false
		}, {
			id : 'password',
			xtype : 'textfield',
			name : 'j_password',
			inputType : 'password',
			fieldLabel : 'Пароль',
			allowBlank : false
		} ],
		buttons : [ {
			text : 'Логин',
			type : 'submit',
			handler : fnLogin
		}, {
			text : 'Восстановить пароль',
			disabled : true,
			handler : Ext.emptyFn
		} ]
	});
	win.doLayout();
	win.show();

	function fnLogin() {
		var un = Ext.getCmp('username');
		if (!un.isValid())
			return;
		un = un.getValue();

		var pw = Ext.getCmp('password');
		if (!pw.isValid())
			return;
		pw = pw.getValue();

		win.close();

		Ext.Ajax.request({
			url : 'j_spring_security_check',
			params : {
				j_username : un,
				j_password : pw,
				submit : 'Login'
			},
			success : fnSuccess,
			failure : fnFailure
		});

		function fnFailure(xhr, opts) {
			App.ui.error('Ошибка регистрации', xhr.statusText);
		}

		function fnSuccess(xhr, opts) {
			try {
				var res = Ext.decode(xhr.responseText);
				if (res && res.success) {
					switch (res.code) {
					case 'login_error':
						App.ui.error('Ошибка регистрации.', 'Логин и пароль недоступны.');
						return;
					case 'login_success':
						App.ui.message('Доступ разрешен.');
						return;
					}
				}
			} catch (error) {
			}
			App.ui.error('Ошибка при регистрации.', error);
		}
	}
}

/**
 * Logout
 */
function logout() {
	window.location = 'logout';
}
