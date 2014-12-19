/*<%@ page pageEncoding="utf-8" %>
 * 
 */

/**
 * Header panel
 */
var panelHeader = {
	region : 'north',
	xtype : 'container',
	height : 84,
	layout : 'hbox',
	layoutConfig : {
		align : 'stretch'
	},
	items : [ {
		xtype : 'container',
		width : 300,
		style : {
			background : 'url(images/bank_v_life.png) center no-repeat'
		}
	}, {
		width : 521,
		border : false,
		unstyled : true,
		contentEl : 'intro-panel',
		buttonAlign : 'center',
        		buttons : [ {
            id : 'username',
            xtype : 'textfield',
            inputType : 'text',
            emptyText : 'Логин',
            allowBlank : false,
            listeners : {
	            specialkey : function(field, e) {
		            if (e.getKey() == e.ENTER && field.isValid()) {
		            	Ext.getCmp('password').focus();
		            }
	            }
            }
        }, {
			id : 'password',
			xtype : 'textfield',
			inputType : 'password',
			//emptyText : 'Пароль',
			allowBlank : false,
            listeners : {
	            specialkey : function(field, e) {
		            if (e.getKey() == e.ENTER && field.isValid()) {
		            	loginSubmit();
		            }
	            }
            }
		}, {
			id : 'login',
			text : 'Регистрация',
			handler : loginSubmit
		}, {
			id : 'login_restore',
			text : 'Восстановить пароль',
			disabled : true,
			handler : Ext.emptyFn
		}, {
			id : 'username_label',
			xtype : 'label',
			hidden : true,
			//<% if (null == request.getUserPrincipal()) { %>
			text : 'Пользователь: Anonymous',
			//<% } else { %>
			text : 'Пользователь: <%= request.getUserPrincipal().getName() %>',
			//<% } %>
		}, {
			id : 'logout',
			text : 'Выход',
			hidden : true,
			handler : function() {
				window.location = 'logout';
			}
		} ]
	}, {
		xtype : 'container',
		width : '100%',
		style : {
			background : 'url(images/summer_bg.png) right repeat-x'
		}
	} ]
};

function loginVisible(visible) {
	Ext.getCmp('username').setVisible(visible).reset();
	Ext.getCmp('password').setVisible(visible).reset();
	Ext.getCmp('login').setVisible(visible);
	Ext.getCmp('login_restore').setVisible(visible);
	Ext.getCmp('username_label').setVisible(!visible);
	Ext.getCmp('logout').setVisible(!visible);
	var un = Ext.util.Cookies.get('user');
	if (un) {
		Ext.getCmp('username').setValue(un);
	}
}

function loginSubmit() {
	var un = Ext.getCmp('username');
	if (!un.isValid())
		return;
	un = un.getValue();

	var pw = Ext.getCmp('password');
	if (!pw.isValid())
		return;
	pw = pw.getValue();

	Ext.util.Cookies.clear('user');
	Ext.getCmp('username').reset();
	Ext.getCmp('password').reset();

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
		App.ui.error('Ошибка при регистрации.', xhr.statusText);
	}

	function fnSuccess(xhr, opts) {
		try {
			var res = Ext.decode(xhr.responseText);
			if (res && res.success) {
				switch (res.code) {
				case 'login_error':
					App.ui.error('Ошибка регистрации.',
							'Логин и пароль недоступны.');
					return;
				case 'login_success':
					var expires = new Date().add(Date.DAY, 7);
					Ext.util.Cookies.set('user', res.user, expires);
					Ext.getCmp('username_label').setText('Пользователь: ' + res.user);
					loginVisible(false);
					App.ui.message('Доступ разрешен.');
					return;
				}
			}
		} catch (error) {
			App.ui.error('Ошибка при регистрации.', error);
		}
	}
}
