/**
 * Брокерские счета - Добавление
 */
(function() {

	var _name = Ext.id();
	var _comment = Ext.id();

	var cbClient = new Ext.form.ComboBox({
		fieldLabel : 'Клиент',
		valueField : 'name',
		displayField : 'name',
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'rest/BrokerAccounts/Clients.do',
			// root : 'info',
			fields : [ 'id', 'name' ]
		}),
		allowBlank : false,
		editable : false,
		emptyText : 'Выберите клиента',
		loadingText : 'Поиск...',
		triggerAction : 'all'
	});

	var cbBroker = new Ext.form.ComboBox({
		fieldLabel : 'Брокер',
		valueField : 'name',
		displayField : 'name',
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'rest/BrokerAccounts/Brokers.do',
			// root : 'info',
			fields : [ 'id', 'name' ]
		}),
		allowBlank : false,
		emptyText : 'Выберите брокера',
		loadingText : 'Поиск...',
		minChars : 2,
		triggerAction : 'all'
	});

	var container = new Ext.FormPanel({
		border : false,
		baseCls : 'x-plain',
		padding : 20,
		labelWidth : 160,
		width : 420,
		height : 210,
		defaults : {
			width : 200
		},

		items : [ {
			id : _name,
			xtype : 'textfield',
			fieldLabel : 'Наименование',
			allowBlank : false,
			emptyText : 'Заполните'
		}, cbClient, cbBroker, {
			id : _comment,
			xtype : 'textfield',
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
			this.window.setTitle('Добавление брокерского счёта');
		}
	});

	function save(self) {
		Ext.Ajax.request({
			url : 'rest/BrokerAccounts.do',
			params : {
				name : Ext.getCmp(_name).getValue(),
				client : cbClient.getValue(),
				broker : cbBroker.getValue(),
				comment : Ext.getCmp(_comment).getValue()
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Сохранение...',
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

	function close() {
		container.window.close();
	}

	return container;
})();
