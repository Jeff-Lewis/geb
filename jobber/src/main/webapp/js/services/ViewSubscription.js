/**
 * Subscription
 */
(function() {

	var stInfo = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : true,
		url : 'rest/ViewSubscription.do',
		// root : 'info',
		fields : [ 'id', 'name', 'comment', 'status' ],
		listeners : App.ui.listenersJsonStore()
	});

	var smInfo = new Ext.grid.RowSelectionModel({
		singleSelect : true
	});

	function add(b, e) {
		showModal('subscription/ViewSubscriptionAdd');
	}

	function edit(b, e) {
		if (smInfo.getCount() == 0) {
			App.ui.message('Необходимо выбрать значение для редактирования.');
			return;
		}

		var id = smInfo.getSelected().data.id;
		showPanel('subscription/ViewSubscriptionEdit', 'rest/ViewSubscription/' + id + '.do');
	}

	function del(b, e) {
		if (smInfo.getCount() == 0) {
			App.ui.message('Необходимо выбрать значение для удаления.');
			return;
		}

		var name = smInfo.getSelected().data.name;
		App.ui.confirm('Удалить подписку ' + name + '?', delCallback);
	}
	function delCallback() {
		var id = smInfo.getSelected().data.id;
		Ext.Ajax.request({
			method : 'DELETE',
			url : 'rest/ViewSubscription/' + id + '.do',
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Выполняется запрос.',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					stInfo.reload();
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен.');
			}
		});
	}

	function subscr(b, e) {
		if (smInfo.getCount() == 0) {
			App.ui.message('Необходимо выбрать подписку.');
			return;
		}

		var id = smInfo.getSelected().data.id;
		Ext.Ajax.request({
			url : 'rest/ViewSubscription/' + id + '/' + b.id + '.do',
			timeout : 10 * 60 * 10000, // 10 min
			waitMsg : 'Выполняется запрос.',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					stInfo.reload();
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	return new Ext.grid.GridPanel({
		id : 'ViewSubscription-component',
		title : 'Список подписок',
		frame : true,
		closable : true,
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
		}, ' ', ' ', ' ', {
			id : 'start',
			text : 'Запустить',
			handler : subscr
		}, {
			id : 'stop',
			text : 'Остановить',
			handler : subscr
		} ],

		store : stInfo,
		selModel : smInfo,
		columns : [ new Ext.grid.RowNumberer({
			width : 30
		}), {
			header : 'SUBSCRIPTION_NAME',
			dataIndex : 'name',
			width : 50
		}, {
			header : 'COMMENT',
			dataIndex : 'comment',
			width : 100
		}, {
			header : 'STATUS',
			dataIndex : 'status',
			width : 30
		} ],
		viewConfig : {
			forceFit : true,
			emptyText : 'Записи не найдены'
		}
	});
})();
