/**
 * Справочник контактов
 */
(function() {

	var contacts = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : true,
		url : 'rest/Contacts.do',
		// root : 'info',
		fields : [ 'id', 'name' ],
		sortInfo : {
			field : 'name'
		},
		listeners : App.ui.listenersJsonStore()
	});

	var smC = new Ext.grid.RowSelectionModel({
		singleSelect : true
	});

	function addContact(self) {
		Ext.Msg.prompt('Добавление контакта', 'Ф.И.О.', addContactCallback);
	}
	function addContactCallback(btn, text) {
		if (btn != 'ok') {
			return;
		}

		Ext.Ajax.request({
			url : 'rest/Contacts.do',
			params : {
				name : text
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Сохранение',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					contacts.reload();
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

	function renContact() {
		if (smC.getCount() == 0) {
			App.ui.message('Необходимо выбрать контакт.');
			return;
		}

		Ext.Msg.prompt('Переименование контакта', 'Ф.И.О.', renContactCallback,
				this, false, smC.getSelected().data.name);
	}
	function renContactCallback(btn, text) {
		if (btn != 'ok') {
			return;
		}

		var id = smC.getSelected().data.id;
		Ext.Ajax.request({
			url : 'rest/Contacts/' + id + '.do',
			params : {
				name : text
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Сохранение',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					contacts.reload();
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

	function edtContact(self) {
		if (smC.getCount() == 0) {
			App.ui.message('Необходимо выбрать контакт.');
			return;
		}

		var id = smC.getSelected().data.id;
		showPanel('utils/ContactsEdit', 'rest/Contacts/' + id + '.do');
	}

	function delContact(self) {
		if (smC.getCount() == 0) {
			App.ui.message('Необходимо выбрать контакт.');
			return;
		}

		App.ui.confirm('Удалить контакт ' + smC.getSelected().data.name + '?',
				delContactCallback);
	}
	function delContactCallback() {
		var id = smC.getSelected().data.id;
		Ext.Ajax.request({
			method : 'DELETE',
			url : 'rest/Contacts/' + id + '.do',
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Удаление',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					contacts.reload();
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

	var groups = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : true,
		url : 'rest/Groups.do',
		// root : 'info',
		fields : [ 'id', 'name' ],
		sortInfo : {
			field : 'name'
		},
		listeners : App.ui.listenersJsonStore()
	});

	var smG = new Ext.grid.RowSelectionModel({
		singleSelect : true
	});

	function addGroup(self) {
		Ext.Msg.prompt('Добавление группы', 'Название', addGroupCallback);
	}
	function addGroupCallback(btn, text) {
		if (btn != 'ok') {
			return;
		}

		Ext.Ajax.request({
			url : 'rest/Groups.do',
			params : {
				name : text
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Сохранение',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					groups.reload();
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

	function renGroup() {
		if (smG.getCount() == 0) {
			App.ui.message('Необходимо выбрать группу.');
			return;
		}

		Ext.Msg.prompt('Переименование группы', 'Название', renGroupCallback,
				this, false, smG.getSelected().data.name);
	}
	function renGroupCallback(btn, text) {
		if (btn != 'ok') {
			return;
		}

		var id = smG.getSelected().data.id;
		Ext.Ajax.request({
			url : 'rest/Groups/' + id + '.do',
			params : {
				name : text
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Сохранение',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					groups.reload();
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

	function edtGroup(self) {
		if (smG.getCount() == 0) {
			App.ui.message('Необходимо выбрать группу.');
			return;
		}

		var id = smG.getSelected().data.id;
		showPanel('utils/ContactsEditGroup', 'rest/Groups/' + id + '.do');
	}

	function delGroup(self) {
		if (smG.getCount() == 0) {
			App.ui.message('Необходимо выбрать группу.');
			return;
		}

		App.ui.confirm('Удалить группу ' + smG.getSelected().data.name + '?',
				delGroupCallback);
	}
	function delGroupCallback() {
		var id = smG.getSelected().data.id;
		Ext.Ajax.request({
			method : 'DELETE',
			url : 'rest/Groups/' + id + '.do',
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Удаление',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					groups.reload();
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

	return new Ext.Panel({
		id : 'Contacts-component',
		title : 'Справочник контактов',
		closable : true,
		frame : true,
		layout : 'hbox',
		layoutConfig : {
			align : 'stretch'
		},
		defaults : {
			width : 400,
			frame : true,
			enableHdMenu : false
		},

		items : [ {
			xtype : 'grid',
			title : 'Контакты',

			tbar : [ {
				text : 'Добавить',
				handler : addContact
			}, {
				text : 'Переименовать',
				handler : renContact
			}, {
				text : 'Сведения',
				handler : edtContact
			}, {
				text : 'Удалить',
				handler : delContact
			} ],

			store : contacts,
			selModel : smC,
			columns : [ new Ext.grid.RowNumberer({
				width : 30
			}), {
				header : 'Ф.И.О.',
				dataIndex : 'name'
			} ],
			viewConfig : {
				forceFit : true,
				emptyText : 'Записи не найдены'
			},
		}, {
			xtype : 'container',
			width : 20
		}, {
			xtype : 'grid',
			title : 'Группы',

			tbar : [ {
				text : 'Добавить',
				handler : addGroup
			}, {
				text : 'Переименовать',
				handler : renGroup
			}, {
				text : 'Состав',
				handler : edtGroup
			}, {
				text : 'Удалить',
				handler : delGroup
			} ],

			store : groups,
			selModel : smG,
			columns : [ new Ext.grid.RowNumberer({
				width : 30
			}), {
				header : 'Название',
				dataIndex : 'name'
			} ],
			viewConfig : {
				forceFit : true,
				emptyText : 'Записи не найдены'
			},
		} ]
	});
})();
