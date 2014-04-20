/**
 * Справочник пользователей - Добавление
 */
(function() {

	var _name = Ext.id();
	var _comment = Ext.id();

	var container = new Ext.FormPanel({
		border : false,
		baseCls : 'x-plain',
		padding : 10,
		width : 340,
		height : 140,
		defaults : {
			xtype : 'textfield',
			width : 200,
			allowBlank : false
		},

		items : [ {
			id : _name,
			fieldLabel : 'Наименование'
		}, {
			id : _comment,
			fieldLabel : 'Комментарий'
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
			this.window.setTitle('Добавление объекта');
		}
	});

	function save(b, e) {
		Ext.Ajax.request({
			url : 'rest/DictObjects.do',
			params : {
				name : Ext.getCmp(_name).getValue(),
				comment : Ext.getCmp(_comment).getValue()
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Сохранение нового broker',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					container.window.close();
					Ext.getCmp('Users-component').reloadObjects();
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
