/**
 * Свопы - добавить
 */
(function() {

	var _swap = Ext.id();
	var _security = Ext.id();

	var container = new Ext.FormPanel({
		border : false,
		baseCls : 'x-plain',
		padding : 20,
		labelWidth : 160,
		width : 420,
		height : 150,
		defaults : {
			width : 200
		},

		items : [ {
			id : _swap,
			xtype : 'textfield',
			fieldLabel : 'Своп'
		}, {
			id : _security,
			xtype : 'combo',
			fieldLabel : 'Инструмент',
			valueField : 'id',
			displayField : 'name',
			store : new Ext.data.JsonStore({
				autoDestroy : true,
				url : 'rest/Swaps/Equities.do',
				// root : 'info',
				fields : [ 'id', 'name' ],
				sortInfo : {
					field : 'name'
				}
			}),
			triggerAction : 'all',
			loadingText : 'Поиск...',
			minChars : 2
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
			this.window.setTitle('Добавление свопа');
		}
	});

	function save(b, e) {
		Ext.Ajax.request({
			url : 'rest/Swaps.do',
			params : {
				swap : Ext.getCmp(_swap).getValue(),
				security : Ext.getCmp(_security).getValue()
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Сохранение',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					container.window.close();
					Ext.getCmp('Swaps-component').getStore().reload();
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
