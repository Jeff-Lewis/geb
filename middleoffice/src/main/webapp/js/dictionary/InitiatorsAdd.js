/**
 * Инициаторы - Добавление
 */
(function() {

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
			text : 'Добавить',
			handler : save
		}, {
			text : 'Отмена',
			handler : close
		} ],

		setWindow : function(window) {
			this.window = window;
			this.window.setTitle('Добавление нового Инициатора');
		}
	});

	function save(b, e) {
		Ext.Ajax.request({
			url : 'rest/Initiators.do',
			params : {
				name : Ext.getCmp(_name).getValue(),
				comment : Ext.getCmp(_comment).getValue()
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Сохранение нового фонда',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					container.window.close();
					Ext.getCmp('Initiators-component').getStore().reload();
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
