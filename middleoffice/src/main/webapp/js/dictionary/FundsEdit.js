/**
 * фонды - Изменение
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
			fieldLabel : 'Наименование фонда',
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

		setWindow : function(window) {
			this.window = window;
			this.window.setTitle('Редактирование фонда');
		},
		loadData : function(data) {
			idItem = data.item.id;
			Ext.getCmp(_name).setValue(data.item.name);
			Ext.getCmp(_comment).setValue(data.item.comment);
		}
	});

	function save(self) {
		Ext.Ajax.request({
			url : 'rest/Funds/' + idItem + '.do',
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
					Ext.getCmp('Funds-component').getStore().reload();
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
