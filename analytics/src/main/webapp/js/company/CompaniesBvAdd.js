(function() {

	var ids = 0;

	var _type = Ext.id();
	var _start = Ext.id();
	var _end = Ext.id();

	var container = new Ext.FormPanel({
		width : 250,
		height : 170,
		padding : 10,
		border : false,
		baseCls : 'x-plain',
		labelWidth : 110,
		defaults : {
			width : 100
		},

		items : [ {
			id : _type,
			fieldLabel : 'Тип',
			xtype : 'textfield',
			allowBlank : false,
			name : 'type'
		}, {
			id : _start,
			fieldLabel : 'Начальный год',
			xtype : 'nubmerfield',
			name : 'start'
		}, {
			id : _end,
			fieldLabel : 'Конечный год',
			xtype : 'nubmerfield',
			name : 'end'
		} ],

		buttons : [ {
			text : 'Сохранить',
			handler : save
		}, {
			text : 'Отмена',
			handler : close
		} ],

		loadData : function(data) {
			ids = data.id_sec;
		},
		setWindow : function(window) {
			this.window = window;
			this.window.setTitle('Ввод исключения BOOK_VAL_PER_SH');
		}
	});

	function save(self) {
		Ext.Ajax.request({
			url : 'organization/add-bv-growth.html',
			params : {
				id : ids,
				type : Ext.getCmp(_type).getValue(),
				start : Ext.getCmp(_start).getValue(),
				end : Ext.getCmp(_end).getValue()
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Сохранение нового исключения',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					container.window.close();
					menu.showSecurityInfo(id_sec);
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
