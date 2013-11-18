/**
 * Сохраненные шаблоны
 */
(function() {

	var info = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : true,
		autoSave : false,
		url : 'rest/DealsPattern.do',
		// root : 'info',
		fields : [ 'id', 'file_name', 'file_type', 'date_insert' ],
		sortInfo : {
			field : 'date_insert'
		},
		listeners : App.ui.listenersJsonStore()
	});

	var sm = new Ext.grid.RowSelectionModel({
		singleSelect : true
	});

	function patternUpload() {
		menu.showModal(menu, 'services/DealsPatternUpload');
	}

	function patternDownload() {
		if (sm.getCount() == 0) {
			App.ui.message('Выберите файл для сохранения.');
			return;
		}

		var id = sm.getSelected().data.id;
		window.open('rest/DealsPattern/' + id + '.do');
	}

	function patternDelete() {
		if (sm.getCount() == 0) {
			App.ui.message('Выберите файл для удаления.');
		} else {
			App.ui.confirm('Удалить файл ' + sm.getSelected().data.file_name
					+ '?', patternDeleteAjax);
		}
	}

	function patternDeleteAjax() {
		var id = sm.getSelected().data.id;
		Ext.Ajax.request({
			method : 'DELETE',
			url : 'rest/DealsPattern/' + id + '.do',
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Удаление...',
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
		id : 'DealsPattern-component',
		title : 'Сохраненные шаблоны',
		frame : true,
		closable : true,
		enableHdMenu : false,

		tbar : [ {
			text : 'Загрузить',
			handler : patternUpload
		}, {
			text : 'Сохранить',
			handler : patternDownload
		}, {
			text : 'Удалить',
			handler : patternDelete
		} ],

		store : info,
		selModel : sm,
		columns : [ new Ext.grid.RowNumberer(), {
			header : 'Дата',
			dataIndex : 'date_insert',
			// align : 'center',
			renderer : App.util.Renderer.datetime(),
			width : 50
		}, {
			header : 'Файл',
			dataIndex : 'file_name'
		} ],
		viewConfig : {
			forceFit : true,
			emptyText : 'Записи не найдены'
		}
	});
})();
