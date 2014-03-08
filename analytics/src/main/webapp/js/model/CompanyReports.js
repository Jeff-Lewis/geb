/**
 * Компании и отчёты
 */
(function() {

	var info = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : true,
		url : 'rest/CompanyReports.do',
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
		Ext.Msg.prompt('Добавить новый отчёт', 'Название', addCallback);
	}
	function addCallback(btn, text) {
		if (btn != 'ok') {
			return;
		}

		Ext.Ajax.request({
			url : 'rest/CompanyReports.do',
			params : {
				name : text
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Сохранение',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					info.reload();
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

	function ren(self) {
		if (sm.getCount() == 0) {
			App.ui.message('Необходимо выбрать отчёт.');
			return;
		}

		Ext.Msg.prompt('Переименовать отчёт', 'Название', renCallback, this,
				false, sm.getSelected().data.name);
	}
	function renCallback(btn, text) {
		if (btn != 'ok') {
			return;
		}

		var id = sm.getSelected().data.id;
		Ext.Ajax.request({
			url : 'rest/CompanyReports/' + id + '.do',
			params : {
				name : text
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Переименование',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					info.reload();
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

	function edit(self) {
		if (sm.getCount() == 0) {
			App.ui.message('Необходимо выбрать отчёт.');
			return;
		}

		var id = sm.getSelected().data.id;
		menu.submitDataRequest(menu, 'model/CompanyReportsStaff',
				'rest/CompanyReports/' + id + '.do');
	}

	function del(self) {
		if (sm.getCount() == 0) {
			App.ui.message('Необходимо выбрать отчёт.');
			return;
		}

		App.ui.confirm('Удалить отчёт ' + sm.getSelected().data.name + '?',
				delCallback);
	}
	function delCallback() {
		var id = sm.getSelected().data.id;
		Ext.Ajax.request({
			method : 'DELETE',
			url : 'rest/CompanyReports/' + id + '.do',
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Удаление',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					info.reload();
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
		id : 'CompanyReports-component',
		title : 'Компании и отчёты',
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
