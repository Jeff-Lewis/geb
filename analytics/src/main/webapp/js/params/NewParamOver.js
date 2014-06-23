/**
 * Ввод нового параметра blm_datasource_ovr
 */
(function() {

	var _code = Ext.id();
	var _broker = Ext.id();

	var container = new Ext.FormPanel({
		width : 300,
		height : 150,
		padding : 10,
		border : false,
		baseCls : 'x-plain',
		labelWidth : 50,
		defaults : {
			width : 200,
			allowBlank : false
		},

		items : [ {
			id : _code,
			fieldLabel : 'CODE',
			xtype : 'textfield',
			name : 'code'
		}, {
			id : _broker,
			fieldLabel : 'BROKER',
			xtype : 'textfield',
			name : 'broker'
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
			this.window.setTitle('Ввод нового пареметра blm_datasource_ovr');
		}
	});

	function save(b, e) {
		Ext.Ajax.request({
			url : 'rest/NewParam/SaveOvr.do',
			params : {
				code : Ext.getCmp(_code).getValue(),
				broker : Ext.getCmp(_broker).getValue()
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Сохранение нового параметра',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					App.ui.message('Параметр успешно добавлен!');
					container.window.close();
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
