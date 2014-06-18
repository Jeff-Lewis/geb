/**
 * Справочник пользователей
 */
(function() {

	var users = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : true,
		url : 'rest/DictUsers.do',
		// root : 'info',
		fields : [ 'id', 'login', 'name', 'email' ],
		sortInfo : {
			field : 'name'
		},
		listeners : App.ui.listenersJsonStore()
	});

	var smU = new Ext.grid.RowSelectionModel({
		singleSelect : true
	});

	function addUser() {
		showModal('utils/UsersAddUsers');
	}

	function renUser() {
		if (smU.getCount() == 0) {
			App.ui.message('Необходимо выбрать пользователя.');
			return;
		}

		var id = smU.getSelected().data.id;
		showModal('utils/UsersEditUsers', 'rest/DictUsers/' + id + '.do');
	}

	function infoUser() {
		if (smU.getCount() == 0) {
			App.ui.message('Необходимо выбрать пользователя.');
			return;
		}

		var id = smU.getSelected().data.id;
		showPanel('utils/UsersInfoUsers', 'rest/DictUsers/' + id + '.do');
	}

	function delUser() {
		if (smU.getCount() == 0) {
			App.ui.message('Необходимо выбрать пользователя.');
			return;
		}

		App.ui.confirm('Удалить пользователя ' + smU.getSelected().data.name + '?',
				delUserCallback);
	}
	function delUserCallback() {
		var id = smU.getSelected().data.id;
		Ext.Ajax.request({
			method : 'DELETE',
			url : 'rest/DictUsers/' + id + '.do',
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Удаление',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					users.reload();
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
		url : 'rest/DictGroups.do',
		// root : 'info',
		fields : [ 'id', 'name', 'comment' ],
		sortInfo : {
			field : 'name'
		},
		listeners : App.ui.listenersJsonStore()
	});

	var smG = new Ext.grid.RowSelectionModel({
		singleSelect : true
	});

	function addGroup() {
		showModal('utils/UsersAddGroups');
	}

	function renGroup() {
		if (smG.getCount() == 0) {
			App.ui.message('Необходимо выбрать группу.');
			return;
		}

		var id = smG.getSelected().data.id;
		showModal('utils/UsersEditGroups', 'rest/DictGroups/' + id + '.do');
	}

	function staffGroup() {
		if (smG.getCount() == 0) {
			App.ui.message('Необходимо выбрать группу.');
			return;
		}

		var id = smG.getSelected().data.id;
		showPanel('utils/UsersStaffGroup', 'rest/DictGroups/' + id + '.do');
	}

	function infoGroup() {
		if (smG.getCount() == 0) {
			App.ui.message('Необходимо выбрать группу.');
			return;
		}

		var id = smG.getSelected().data.id;
		showPanel('utils/UsersInfoGroup', 'rest/DictGroups/' + id + '.do');
    }

	function delGroup() {
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
			url : 'rest/DictGroups/' + id + '.do',
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

	var objects = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : true,
		url : 'rest/DictObjects.do',
		// root : 'info',
		fields : [ 'id', 'name', 'comment' ],
		sortInfo : {
			field : 'name'
		},
		listeners : App.ui.listenersJsonStore()
	});

	var smO = new Ext.grid.RowSelectionModel({
		singleSelect : true
	});

	function addObject() {
		showModal('utils/UsersAddObjects');
    }

	function edtObject() {
		if (smO.getCount() == 0) {
			App.ui.message('Необходимо выбрать объект.');
			return;
		}

		var id = smO.getSelected().data.id;
		showModal('utils/UsersEditObjects', 'rest/DictObjects/' + id + '.do');
    }

	function delObject() {
		if (smO.getCount() == 0) {
			App.ui.message('Необходимо выбрать объект.');
			return;
		}

		App.ui.confirm('Удалить объект ' + smO.getSelected().data.name + '?',
				delObjectCallback);
	}
	function delObjectCallback() {
		var id = smO.getSelected().data.id;
		Ext.Ajax.request({
			method : 'DELETE',
			url : 'rest/DictObjects/' + id + '.do',
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Удаление',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					objects.reload();
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
		id : 'Users-component',
		title : 'Справочник пользователей',
		closable : true,
		frame : true,
		layout : 'hbox',
		layoutConfig : {
			align : 'stretch'
		},
		defaults : {
			width : 350,
			frame : true,
			enableHdMenu : false
		},

		items : [ {
			xtype : 'grid',
			title : 'Пользователи',

			tbar : [ {
				text : 'Добавить',
				handler : addUser
			}, {
				text : 'Изменить',
				handler : renUser
			}, {
				text : 'Сведения',
				handler : infoUser
			}, {
				text : 'Удалить',
				handler : delUser
			} ],

			store : users,
			selModel : smU,
			columns : [ new Ext.grid.RowNumberer({
				width : 30
			}), {
				header : 'Логин',
				dataIndex : 'login',
				width : 50
			}, {
				header : 'Ф.И.О.',
				dataIndex : 'name',
				width : 100
			}, {
				header : 'E-Mail',
				dataIndex : 'email',
				width : 100
			} ],
			viewConfig : {
				forceFit : true,
				emptyText : 'Записи не найдены'
			},
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
				handler : staffGroup
			}, {
				text : 'Права',
				handler : infoGroup
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
				dataIndex : 'name',
				width : 100
			}, {
				header : 'Комментарий',
				dataIndex : 'comment',
				width : 100
			} ],
			viewConfig : {
				forceFit : true,
				emptyText : 'Записи не найдены'
			},
		}, {
			xtype : 'grid',
			title : 'Объекты',

			tbar : [ {
				text : 'Добавить',
				handler : addObject
			}, {
				text : 'Изменить',
				handler : edtObject
			}, {
				text : 'Удалить',
				handler : delObject
			} ],

			store : objects,
			selModel : smO,
			columns : [ new Ext.grid.RowNumberer({
				width : 30
			}), {
				header : 'Название',
				dataIndex : 'name',
				width : 100
			}, {
				header : 'Комментарий',
				dataIndex : 'comment',
				width : 100
			} ],
			viewConfig : {
				forceFit : true,
				emptyText : 'Записи не найдены'
			},
		} ],

		reloadUsers : function() {
	        users.reload();
        },

        reloadGroups : function() {
	        groups.reload();
        },

        reloadObjects : function() {
	        objects.reload();
        }
	});
})();
