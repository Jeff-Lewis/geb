/**
 * Клиенты
 */
(function() {

	var store = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : true,
		autoSave : false,
		url : 'rest/Clients.do',
		// root : 'info',
		fields : [ 'id', 'name', 'countryName', 'countryCode', 'dateBegin', 'dateEnd', 'comment' ],
		sortInfo : {
			field : 'name'
		},
		listeners : App.ui.listenersJsonStore()
	});

	var sm = new Ext.grid.RowSelectionModel({
		singleSelect : true
	});

	function addItem(self) {
		showModal('dictionary/ClientsAdd');
	}

	function edtItem(self) {
		if (sm.getCount() == 0) {
			App.ui.message('Необходимо выбрать запись для изменения!');
			return;
		}

		var id = sm.getSelected().data.id;
		showModal('dictionary/ClientsEdit', 'rest/Clients/' + id + '.do');
	}

	function delItem(self) {
		if (sm.getCount() > 0) {
			App.ui.confirm('Удалить запись "' + sm.getSelected().data.name
					+ '"?', delItemAjax);
		} else {
			App.ui.message('Необходимо выбрать запись для удаления!');
		}
	}
	function delItemAjax() {
		var id = sm.getSelected().data.id;
		Ext.Ajax.request({
			method : 'DELETE',
			url : 'rest/Clients/' + id + '.do',
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Выполняется запрос',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					store.reload();
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
		id : 'Clients-component',
		title : 'Клиенты',
		frame : true,
		closable : true,
		enableHdMenu : false,

		tbar : [ {
			text : 'Добавить',
			handler : addItem
		}, {
			text : 'Изменить',
			handler : edtItem
		}, {
			text : 'Удалить',
			handler : delItem
		} ],

		store : store,
		selModel : sm,
		columns : [ new Ext.grid.RowNumberer({
			width : 30
		}), {
			header : 'Наименование',
			dataIndex : 'name',
			width : 150
		}, {
			header : 'Страна',
			dataIndex : 'countryName',
			width : 100
		}, {
			header : 'Код',
			dataIndex : 'countryCode',
			width : 50
		}, {
			header : 'Дата начала',
			dataIndex : 'dateBegin',
			renderer : App.util.Renderer.date(),
			width : 70
		}, {
			header : 'Дата окончания',
			dataIndex : 'dateEnd',
			renderer : App.util.Renderer.date(),
			width : 70
		}, {
			header : 'Комментарий',
			dataIndex : 'comment',
			width : 150
		} ],
		viewConfig : {
			forceFit : true,
			emptyText : 'Записи не найдены'
		}
	});
})();
