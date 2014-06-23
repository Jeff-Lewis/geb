/**
 * Справочник пользователей - Изменение
 */
(function() {

	var idItem = 0;

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
			text : 'Изменить',
			handler : save
		}, {
			text : 'Отмена',
			handler : close
		} ],

		setWindow : function(window) {
			this.window = window;
			this.window.setTitle('Изменение объекта');
		},
		loadData : function(data) {
			idItem = data.item.id;
			Ext.getCmp(_name).setValue(data.item.name);
			Ext.getCmp(_comment).setValue(data.item.comment);
		}
	});

	function save(b, e) {
		Ext.Ajax.request({
			url : 'rest/DictObjects/' + idItem + '.do',
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
					Ext.getCmp('Users-component').reloadObjects();
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
