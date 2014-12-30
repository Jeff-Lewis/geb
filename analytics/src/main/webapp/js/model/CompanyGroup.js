/**
 * Компании и группы
 */
(function() {

	var info = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : true,
		url : 'rest/CompanyGroup.do',
		// root : 'info',
		fields : [ 'id', 'name' ],
		sortInfo : {
			field : 'name'
		},
		listeners : App.ui.listenersJsonStore()
	});

	var sm = new Ext.grid.RowSelectionModel({
		singleSelect : true
	});

	function add(self) {
		Ext.Msg.prompt('Добавление новой<br/>группы компаний', 'Название',
				addCallback);
	}
	function addCallback(btn, text) {
		if (btn != 'ok') {
			return;
		}

		Ext.Ajax.request({
			url : 'rest/CompanyGroup.do',
			params : {
				name : text
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Сохранение',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					info.reload();
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	function ren(self) {
		if (sm.getCount() == 0) {
			App.ui.message('Необходимо выбрать группу.');
			return;
		}

		Ext.Msg.prompt('Переименование<br/>группы компании', 'Название',
				renCallback, this, false, sm.getSelected().data.name);
	}
	function renCallback(btn, text) {
		if (btn != 'ok') {
			return;
		}

		var id = sm.getSelected().data.id;
		Ext.Ajax.request({
			url : 'rest/CompanyGroup/' + id + '.do',
			params : {
				name : text
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Переименование',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					info.reload();
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	function edit(self) {
		if (sm.getCount() == 0) {
			App.ui.message('Необходимо выбрать группу.');
			return;
		}

		var id = sm.getSelected().data.id;
		showPanel('model/CompanyGroupStaff', 'rest/CompanyGroup/' + id + '.do');
	}

	function del(self) {
		if (sm.getCount() == 0) {
			App.ui.message('Необходимо выбрать группу.');
			return;
		}

		App.ui.confirm('Удалить группу ' + sm.getSelected().data.name + '?',
				delCallback);
	}
	function delCallback() {
		var id = sm.getSelected().data.id;
		Ext.Ajax.request({
			method : 'DELETE',
			url : 'rest/CompanyGroup/' + id + '.do',
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Удаление',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					info.reload();
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	function requestDaily(b, e) {
		if (sm.getCount() == 0) {
			App.ui.message('Необходимо выбрать группу.');
			return;
		}

		App.ui.confirm('Обновить ежедневные данные<BR>для группы ' + sm.getSelected().data.name + '?',
				requestDailyCallback);
	}
	function requestDailyCallback() {
		var id = sm.getSelected().data.id;
		Ext.Ajax.request({
			url : 'rest/CompanyGroup/' + id + '/RequestBDP.do',
			// timeout : 10 * 60 * 10000, // 10 min
			timeout : 1000000000,
			waitMsg : 'Выполняется запрос к Bloomberg',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					App.ui.message('Данные загружены.');
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	function requestYearly(b, e) {
		if (sm.getCount() == 0) {
			App.ui.message('Необходимо выбрать группу.');
			return;
		}

		App.ui.confirm('Обновить годовые данные<BR>для группы ' + sm.getSelected().data.name + '?',
				requestYearlyCallback);
    }
	function requestYearlyCallback() {
		var id = sm.getSelected().data.id;
		Ext.Ajax.request({
			url : 'rest/CompanyGroup/' + id + '/RequestYearly.do',
			// timeout : 10 * 60 * 10000, // 10 min
			timeout : 1000000000,
			waitMsg : 'Выполняется запрос к Bloomberg',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					App.ui.message('Данные загружены.');
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	return new Ext.grid.GridPanel({
		id : 'CompanyGroup-component',
		title : 'Компании и группы',
		frame : true,
		closable : true,
		enableHdMenu : false,

		tbar : [ {
			text : 'Добавить',
			handler : add
		}, {
			text : 'Переименовать',
			handler : ren
		}, {
			text : 'Состав',
			handler : edit
		}, {
			text : 'Удалить',
			handler : del
		}, ' ', {
			text : 'Выполнить',
			menu : [ {
				text : 'Обновить ежедневные данные',
				handler : requestDaily
			}, {
				text : 'Обновить данные за последний год',
				handler : requestYearly
			} ]
		} ],

		store : info,
		selModel : sm,
		columns : [ new Ext.grid.RowNumberer({
			width : 30
		}), {
			header : 'Наименование',
			dataIndex : 'name'
		} ],
		viewConfig : {
			forceFit : true,
			emptyText : 'Записи не найдены'
		}
	});
})();
