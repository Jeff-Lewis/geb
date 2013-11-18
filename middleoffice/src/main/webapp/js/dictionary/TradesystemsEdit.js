/**
 * Торговые системы - Изменение
 */
(function() {

	var idItem = 0;

	var _name = Ext.id();
	var _comment = Ext.id();

	var container = new Ext.FormPanel({
		border : false,
		baseCls : 'x-plain',
		padding : 20,
		labelWidth : 160,
		width : 420,
		height : 160,

		items : [ {
			id : _name,
			xtype : 'textfield',
			fieldLabel : 'Наименование',
			width : 200,
			allowBlank : false
		}, {
			id : _comment,
			xtype : 'textfield',
			fieldLabel : 'Комментарий',
			width : 200,
			allowBlank : false
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
			Ext.getCmp(_name).setValue(data.item.name);
			Ext.getCmp(_comment).setValue(data.item.comment);
		},
		setWindow : function(window) {
			this.window = window;
			this.window.setTitle('Редактирование торговой системы');
		}
	});

	function save(b, e) {
		Ext.Ajax.request({
			url : 'rest/Tradesystems/' + idItem + '.do',
			params : {
				name : Ext.getCmp(_name).getValue(),
				comment : Ext.getCmp(_comment).getValue()
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Сохранение изменений',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					container.window.close();
					Ext.getCmp('Tradesystems-component').getStore().reload();
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
