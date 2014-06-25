/**
 * Инициаторы
 */
(function() {

	var store = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : false,
		autoSave : false,
		url : 'rest/Initiators.do',
		// root : 'info',
		fields : [ 'id', 'name', 'comment' ],
		sortInfo : {
			field : 'name'
		},
		listeners : App.ui.listenersJsonStore()
	});

	var sm = new Ext.grid.RowSelectionModel({
		singleSelect : true
	});

	function addItem(self) {
		showModal('dictionary/InitiatorsAdd');
	}

	function edtItem(self) {
		if (sm.getCount() > 0) {
			var id = sm.getSelected().data.id;
			showModal('dictionary/InitiatorsEdit', 'rest/Initiators/' + id + '.do');
		} else {
			App.ui.message('Необходимо выбрать запись для редактирования!');
		}
	}

	function delItem(self) {
		if (sm.getCount() > 0) {
			App.ui.confirm('Удалить запись "' + sm.getSelected().data.name
					+ '"?', delFondAjax);
		} else {
			App.ui.message('Необходимо выбрать запись для удаления!');
		}
	}
	function delFondAjax() {
		var id = sm.getSelected().data.id;
		Ext.Ajax.request({
			method : 'DELETE',
			url : 'rest/Initiators/' + id + '.do',
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Выполняется запрос',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					store.reload();
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	return new Ext.grid.GridPanel({
		id : 'Initiators-component',
		title : 'Инициаторы',
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
			width : 50
		}, {
			header : 'Комментарий',
			dataIndex : 'comment',
			width : 50
		} ],
		viewConfig : {
			forceFit : true,
			emptyText : 'Записи не найдены'
		},
		listeners : {
			show : function(grid) {
				// grid.getView().refresh();
				setTimeout(function() {
					grid.getStore().reload();
				}, 0);
			}
		}
	});
})();
