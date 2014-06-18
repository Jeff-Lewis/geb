/**
 * Брокерские счета
 */
(function() {

	var accounts = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : false,
		autoSave : false,
		url : 'rest/BrokerAccounts.do',
		// root : 'info',
		fields : [ 'id', 'name', 'broker', 'client', 'comment' ],
		listeners : App.ui.listenersJsonStore()
	});

	var sm = new Ext.grid.RowSelectionModel({
		singleSelect : true
	});

	function addItem(self) {
		showModal('dictionary/BrokerAccountsAdd');
	}

	function edtItem(self) {
		if (sm.getCount() == 0) {
			App.ui.message('Необходимо выбрать запись для редактирования!');
			return;
		}
		var id = sm.getSelected().data.id;
		showModal('dictionary/BrokerAccountsEdit',
				'rest/BrokerAccounts/' + id + '.do');
	}

	function delItem(self) {
		if (sm.getCount() == 0) {
			App.ui.message('Необходимо выбрать запись для удаления!');
			return;
		}
		App.ui.confirm('Удалить запись "' + sm.getSelected().data.name + '"?',
				delItemAjax);
	}
	function delItemAjax() {
		var id = sm.getSelected().data.id;
		Ext.Ajax.request({
			method : 'DELETE',
			url : 'rest/BrokerAccounts/' + id + '.do',
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Выполняется запрос',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					accounts.reload();
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
		id : 'BrokerAccounts-component',
		title : 'Брокерские счета',
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

		store : accounts,
		selModel : sm,
		columns : [ new Ext.grid.RowNumberer({
			width : 35
		}), {
			header : 'Наименование',
			dataIndex : 'name',
			sortable : true,
			width : 50
		}, {
			header : 'Брокер',
			dataIndex : 'broker',
			sortable : true,
			width : 50
		}, {
			header : 'Клиент',
			dataIndex : 'client',
			sortable : true,
			width : 50
		}, {
			header : 'Комментарий',
			dataIndex : 'comment',
			width : 150
		} ],
		viewConfig : {
			autoFill : true,
			forceFit : true,
			emptyText : 'Записи не найдены'
		},
		listeners : {
			show : function(grid) {
				setTimeout(function() {
					accounts.reload();
				}, 0);
			}
		}
	});
})();
