/**
 * Брокерские счета - Изменение
 */
(function() {

	var idItem = 0;

	var _name = Ext.id();
	var _client = Ext.id();
	var _broker = Ext.id();
	var _comment = Ext.id();

	var container = new Ext.FormPanel({
		border : false,
		baseCls : 'x-plain',
		padding : 20,
		labelWidth : 160,
		width : 420,
		height : 210,

		items : [ {
			id : _name,
			xtype : 'textfield',
			fieldLabel : 'Наименование',
			width : 200,
			allowBlank : false,
			emptyText : 'Заполните'
		}, {
			id : _client,
			xtype : 'textfield',
			fieldLabel : 'Клиент',
			width : 200,
			readOnly : true
		}, {
			id : _broker,
			xtype : 'textfield',
			fieldLabel : 'Брокер',
			width : 200,
			readOnly : true
		}, {
			id : _comment,
			xtype : 'textfield',
			fieldLabel : 'Комментарий',
			width : 200
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
			this.window.setTitle('Редактирование брокерского счёта');
		},
		loadData : function(data) {
			idItem = data.item.id;
			Ext.getCmp(_name).setValue(data.item.name);
			Ext.getCmp(_client).setValue(data.item.client);
			Ext.getCmp(_broker).setValue(data.item.broker);
			Ext.getCmp(_comment).setValue(data.item.comment);
		}
	});

	function save(b, e) {
		Ext.Ajax.request({
			url : 'rest/BrokerAccounts/' + idItem + '.do',
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
					Ext.getCmp('BrokerAccounts-component').getStore().reload();
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
