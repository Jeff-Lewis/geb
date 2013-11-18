/**
 * Наименование фьючерсов - Добавить
 */
(function() {

	var _name = Ext.id();
	var _comment = Ext.id();
	var _coef = Ext.id();

	var container = new Ext.FormPanel({
		border : false,
		baseCls : 'x-plain',
		padding : 20,
		labelWidth : 160,
		width : 420,
		height : 180,

		items : [ {
			id : _name,
			xtype : 'textfield',
			fieldLabel : 'Наименование фьючерса',
			width : 200,
			allowBlank : false
		}, {
			id : _comment,
			xtype : 'textfield',
			fieldLabel : 'Комментарий',
			width : 200,
			allowBlank : false
		}, {
			id : _coef,
			xtype : 'numberfield',
			fieldLabel : 'Коэффициент фьючерса',
			decimalPrecision : 3,
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
			this.window.setTitle('Добавления нового фьючерса');
		}
	});

	function save(b, e) {
		Ext.Ajax.request({
			url : 'rest/Futures.do',
			params : {
				name : Ext.getCmp(_name).getValue(),
				comment : Ext.getCmp(_comment).getValue(),
				coef : Ext.getCmp(_coef).getValue()
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Сохранение нового фьючерса',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					container.window.close();
					Ext.getCmp('Futures-component').getStore().reload();
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
