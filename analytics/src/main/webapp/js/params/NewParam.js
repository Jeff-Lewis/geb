/**
 * Ввод нового параметра
 */
(function() {

	var _blm_id = Ext.id();
	var _code = Ext.id();
	var _name = Ext.id();

	var container = new Ext.FormPanel({
		width : 300,
		height : 200,
		padding : 10,
		border : false,
		baseCls : 'x-plain',
		labelWidth : 50,
		defaults : {
			width : 200
		},

		items : [ {
			id : _code,
			fieldLabel : 'CODE',
			xtype : 'textfield',
			allowBlank : false
		}, {
			xtype : 'button',
			text : 'Проставить остальные значения',
			width : 250,
			handler : setup
		}, {
			id : _blm_id,
			fieldLabel : 'BLM_ID',
			xtype : 'textfield',
			readOnly : true
		}, {
			id : _name,
			fieldLabel : 'NAME',
			xtype : 'textfield',
			readOnly : true
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
			this.window.setTitle('Ввод нового пареметра');
		}
	});

	function setup() {
		Ext.Ajax.request({
			url : 'rest/NewParam/Setup.do',
			params : {
				code : Ext.getCmp(_code).getValue()
			},
			waitMsg : 'Запрос параметра',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					Ext.getCmp(_name).setValue(answer.item.NAME);
					Ext.getCmp(_blm_id).setValue(answer.item.BLM_ID);
				} else if (ret.code == 'login') {
					App.ui.sessionExpired();
				} else {
					App.ui.error(ret.message);
				}

			},
			failure : function(xhr) {
				App.ui.error(xhr.statusText, xhr.status);
			}
		});
	}

	function save(b, e) {
		Ext.Ajax.request({
			url : 'rest/NewParam/Save.do',
			params : {
				blm_id : Ext.getCmp(_blm_id).getValue(),
				code : Ext.getCmp(_code).getValue(),
				name : Ext.getCmp(_name).getValue()
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Сохранение нового параметра',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					App.ui.message('Параметр успешно добавлен!');
					container.window.close();
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
