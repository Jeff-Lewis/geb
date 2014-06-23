/**
 * Свопы
 */
(function() {

	var info = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : true,
		autoSave : false,
		url : 'rest/Swaps.do',
		// root : 'info',
		fields : [ 'id', 'swap', 'related_security' ],
		sortInfo : {
			field : 'swap'
		},
		listeners : App.ui.listenersJsonStore()
	});

	var sm = new Ext.grid.RowSelectionModel({
		singleSelect : true
	});

	function add() {
		showModal('dictionary/SwapsAdd');
	}

	function edit() {
		if (sm.getCount() == 0) {
			App.ui.message('Необходимо выбрать запись для редактирования!');
			return;
		}

		var swap = sm.getSelected().data.swap;
		Ext.Msg.prompt('Изменить своп', 'Своп', cbEdit, this, false, swap);
	}
	function cbEdit(btn, text) {
		if (btn != 'ok') {
			return;
		}

		var id = sm.getSelected().data.id;
		Ext.Ajax.request({
			url : 'rest/Swaps/' + id + '.do',
			params : {
				swap : text
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

	function del() {
		if (sm.getCount() == 0) {
			App.ui.message('Необходимо выбрать запись для удаления!');
			return;
		}

		App.ui.confirm('Удалить запись для "' + sm.getSelected().data.swap
				+ '"?', cbDel);
	}
	function cbDel() {
		var id = sm.getSelected().data.id;
		Ext.Ajax.request({
			method : 'DELETE',
			url : 'rest/Swaps/' + id + '.do',
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Выполняется запрос',
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

	return new Ext.grid.GridPanel({
		id : 'Swaps-component',
		title : 'Свопы',
		closable : true,
		frame : true,
		enableHdMenu : false,

		tbar : [ {
			text : 'Добавить',
			handler : add
		}, {
			text : 'Изменить',
			handler : edit
		}, {
			text : 'Удалить',
			handler : del
		} ],

		store : info,
		selModel : sm,
		columns : [ new Ext.grid.RowNumberer(), {
			header : 'swap',
			dataIndex : 'swap'
		}, {
			header : 'related_security',
			dataIndex : 'related_security'
		} ],
		viewConfig : {
			forceFit : true,
			emptyText : 'Записи не найдены'
		}
	});
})();
