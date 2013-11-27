/**
 * Справочник контактов Параметры контакта
 */
(function() {

	var idContact = 0;

	var info = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : false,
		url : 'rest/Contacts/Staff.do',
		// root : 'info',
		fields : [ 'cid', 'value' ]
	});

	function reload() {
		info.reload({
			params : {
				id : idContact
			}
		});
	}

	var sm = new Ext.grid.RowSelectionModel({
		singleSelect : true
	});

	function addContact() {
		var _name = Ext.id();
		var _sms = Ext.id();

		var dlg = null;
		dlg = new Ext.Window({
			title : 'Добавление значения',
			width : 350,
			height : 140,
			padding : 20,
			plain : true,
			modal : true,
			border : false,
			layout : 'form',
			labelWidth : 80,
			defaults : {
				width : 200
			},

			items : [ {
				id : _name,
				fieldLabel : 'Значение',
				xtype : 'textfield',
				allowBlank : false
			}, {
				fieldLabel : 'Тип',
				xtype : 'radiogroup',
				items : [ {
					boxLabel : 'MAIL',
					name : 'type',
					inputValue : 2,
					checked : true
				}, {
					id : _sms,
					boxLabel : 'SMS',
					name : 'type',
					inputValue : 1
				} ]
			} ],
			buttons : [ {
				text : 'Добавить',
				handler : function() {
					Ext.Ajax.request({
						url : 'rest/Contacts/Staff/' + idContact + '.do',
						params : {
							name : Ext.getCmp(_name).getValue(),
							type : Ext.getCmp(_sms).getValue() ? 1 : 2
						},
						timeout : 10 * 60 * 1000, // 10 min
						waitMsg : 'Сохранение нового значения контакта',
						success : function(xhr) {
							var answer = Ext.decode(xhr.responseText);
							if (answer.success) {
								reload();
							} else if (answer.code == 'login') {
								App.ui.sessionExpired();
							} else {
								App.ui.error(answer.message);
							}
							dlg.close();
						},
						failure : function() {
							App.ui.error('Сервер недоступен');
							dlg.close();
						}
					});
				}
			}, {
				text : 'Отмена',
				handler : function() {
					dlg.close();
				}
			} ]
		});
		dlg.doLayout();
		dlg.show();
	}

	function edtContact() {
		if (sm.getCount() == 0) {
			App.ui.message('Необходимо выбрать параметр.');
			return;
		}

		Ext.Msg.prompt('Изменить значение', 'Значение', edtContactCallback,
				this, false, sm.getSelected().data.value);
	}
	function edtContactCallback(btn, text) {
		if (btn != 'ok') {
			return;
		}

		Ext.Ajax.request({
			url : 'rest/Contacts/Staff/' + idContact + '/'
					+ sm.getSelected().data.cid + '.do',
			params : {
				name : text
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Сохранение',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					reload();
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

	function delContact() {
		if (sm.getCount() == 0) {
			App.ui.message('Необходимо выбрать параметр.');
			return;
		}
		App.ui.confirm('Удалить параметр?', delContactAjax);
	}
	function delContactAjax() {
		Ext.Ajax.request({
			method : 'DELETE',
			url : 'rest/Contacts/' + idContact + '/'
					+ sm.getSelected().data.cid + '.do',
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Удаление',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					App.ui.message('Контакт удален!');
					reload();
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

	return new Ext.grid.GridPanel({
		id : 'ContactsEdit-component',
		title : 'Редактирование контакта',
		frame : true,
		closable : true,
		enableHdMenu : false,

		tbar : [ {
			text : 'Добавить',
			handler : addContact
		}, {
			text : 'Изменить',
			handler : edtContact
		}, {
			text : 'Удалить',
			handler : delContact
		} ],

		store : info,
		selModel : sm,
		columns : [ new Ext.grid.RowNumberer({
			width : 30
		}), {
			header : 'CID',
			width : 20,
			dataIndex : 'cid'
		}, {
			header : 'Value',
			dataIndex : 'value'
		} ],
		viewConfig : {
			forceFit : true,
			emptyText : 'Записи не найдены'
		},

		loadData : function(data) {
			idContact = data.item.id;
			this.setTitle('Редактирование контакта: ' + data.item.name);
			reload();
		}
	});
})();
