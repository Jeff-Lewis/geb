/**
 * Регистрация инструментов - добавить
 */
(function() {

	var _security = Ext.id();
	var _country = Ext.id();
	var _dateBegin = Ext.id();

	var container = new Ext.FormPanel({
		border : false,
		baseCls : 'x-plain',
		padding : 20,
		labelWidth : 160,
		width : 420,
		height : 180,
		defaults : {
			width : 200,
			emptyText : 'Заполните',
			allowBlank : false
		},

		items : [ {
			id : _security,
			xtype : 'combo',
			fieldLabel : 'Инструмент',
			displayField : 'name',
			valueField : 'name',
			store : new Ext.data.JsonStore({
				autoDestroy : true,
				url : 'rest/SecurityIncorporations/Securities.do',
				// root : 'info',
				fields : [ 'id', 'name' ],
				sortInfo : {
					field : 'name'
				}
			}),
			loadingText : 'Поиск...',
			minChars : 2,
			triggerAction : 'all'
		}, {
			id : _country,
			xtype : 'combo',
			fieldLabel : 'Страна',
			displayField : 'name',
			valueField : 'name',
			store : new Ext.data.JsonStore({
				autoDestroy : true,
				url : 'rest/SecurityIncorporations/Countries.do',
				// root : 'info',
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
			fieldLabel : 'Начало периода'
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
			this.window.setTitle('Добавление инструментов');
		}
	});

	function save(b, e) {
		Ext.Ajax.request({
			url : 'rest/SecurityIncorporations.do',
			params : {
				security : App.Combo.getValueId(Ext.getCmp(_security)),
				country : App.Combo.getValueId(Ext.getCmp(_country)),
				dateBegin : App.util.Format.dateYMD(Ext.getCmp(_dateBegin)
						.getValue())
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Сохранение',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					container.window.close();
					Ext.getCmp('SecurityIncorporations-component').getStore()
							.reload();
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
