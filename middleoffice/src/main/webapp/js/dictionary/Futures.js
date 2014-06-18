/**
 * Фьючерсы
 */
(function() {

	var store = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : false,
		autoSave : false,
		url : 'rest/Futures.do',
		// root : 'info',
		fields : [ 'id', 'name', 'coefficient', 'comment' ],
		sortInfo : {
			field : 'name'
		},
		listeners : App.ui.listenersJsonStore()
	});

	var sm = new Ext.grid.RowSelectionModel({
		singleSelect : true
	});

	function addItem(self) {
		showModal('dictionary/FuturesAdd');
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
			url : 'rest/Futures/' + id + '.do',
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Выполняется запрос к Bloomberg',
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
		id : 'Futures-component',
		title : 'Фьючерсы',
		frame : true,
		closable : true,
		enableHdMenu : false,

		tbar : [ {
			text : 'Добавить',
			handler : addItem
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
			width : 50,
			sortable : true
		}, {
			header : 'Коэффициент',
			dataIndex : 'coefficient',
			align : 'right',
			renderer : App.util.Renderer.number(3),
			width : 50,
			sortable : true
		}, {
			header : 'Комментарий',
			dataIndex : 'comment',
			sortable : true
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
