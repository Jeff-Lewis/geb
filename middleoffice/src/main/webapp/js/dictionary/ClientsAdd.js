/**
 * Клиенты - Добавить
 */
(function() {

	var _name = Ext.id();
	var _country = Ext.id();
	var _dateBegin = Ext.id();
	var _dateEnd = Ext.id();
	var _comment = Ext.id();

	var container = new Ext.FormPanel({
		border : false,
		baseCls : 'x-plain',
		padding : 20,
		labelWidth : 160,
		width : 420,
		height : 240,

		items : [ {
			id : _name,
			xtype : 'textfield',
			fieldLabel : 'Наименование фонда',
			width : 200,
			allowBlank : false
		}, {
			id : _country,
			xtype : 'combo',
			fieldLabel : 'Страна',
			displayField : 'name',
			valueField : 'id',
			store : new Ext.data.JsonStore({
				autoDestroy : true,
				url : 'rest/Clients/Countries.do',
				//root : 'info',
				fields : [ 'id', 'name' ],
				sortInfo : {
					field : 'name'
				}
			}),
			loadingText : 'Поиск...',
			minChars : 2,
			triggerAction : 'all'
		}, {
			id : _dateBegin,
			xtype : 'datefield',
			format : 'd.m.Y',
			fieldLabel : 'Дата начала'
		}, {
			id : _dateEnd,
			xtype : 'datefield',
			format : 'd.m.Y',
			fieldLabel : 'Дата окончания'
		}, {
			id : _comment,
			xtype : 'textfield',
			fieldLabel : 'Комментарий',
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
			this.window.setTitle('Добавление нового фонда');
		}
	});

	function save(b, e) {
		Ext.Ajax.request({
			url : 'rest/Clients.do',
			params : {
				name : Ext.getCmp(_name).getValue(),
				country : Ext.getCmp(_country).getValue(),
				dateBegin : App.util.Format.dateYMD(Ext.getCmp(_dateBegin).getValue()),
				dateEnd : App.util.Format.dateYMD(Ext.getCmp(_dateEnd).getValue()),
				comment : Ext.getCmp(_comment).getValue()
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Сохранение нового фонда',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					container.window.close();
					Ext.getCmp('Clients-component').getStore().reload();
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
