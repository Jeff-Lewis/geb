/**
 * Subscription - add
 */
(function() {

	var _name = Ext.id();
	var _comment = Ext.id();

	var container = new Ext.form.FormPanel({
		width : 350,
		height : 150,
		border : false,
		baseCls : 'x-plain',
		padding : 10,
		labelWidth : 100,
		defaults : {
			width : 200,
			xtype : 'textfield'
		},

		items : [ {
			id : _name,
			fieldLabel : 'Наименование',
			allowBlank : false
		}, {
			id : _comment,
			fieldLabel : 'Комментарий'
		} ],

		buttonAlign : 'center',
		buttons : [ {
			text : 'Добавить',
			handler : save
		}, {
			text : 'Отмена',
			handler : close
		} ],

		setWindow : function(window) {
			this.window = window;
			this.window.setTitle('Добавление новой подписки');
		}
	});

	function save() {
		var _n = Ext.getCmp(_name);

		if (!_n.isValid()) {
			_n.focus();
			App.ui.error('Введите наименование.');
			return;
		}

		Ext.Ajax.request({
			url : 'rest/Subscription.do',
			params : {
				name : _n.getValue(),
				comment : Ext.getCmp(_comment).getValue()
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Сохранение новой подписки',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					container.window.close();
					var g = Ext.getCmp('ViewSubscription-component');
					g.getStore().reload();
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

	function close() {
		container.window.close();
	}

	return container;
})();
