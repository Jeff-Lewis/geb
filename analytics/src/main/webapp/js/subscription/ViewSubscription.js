/**
 * Subscription
 */
(function() {

	var stInfo = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : true,
		url : 'rest/ViewSubscription.do',
		// root : 'info',
		fields : [ 'id_subscr', 'subscription_name', 'subscription_comment',
				'subscription_status' ],
		listeners : App.ui.listenersJsonStore()
	});

	var smInfo = new Ext.grid.RowSelectionModel({
		singleSelect : true
	});

	function add(b, e) {
		menu.showModal(menu, 'subscription/ViewSubscriptionAdd');
	}

	function edit(b, e) {
		if (smInfo.getCount() == 0) {
			App.ui.message('Необходимо выбрать значение для редактирования.');
			return;
		}

		var data = smInfo.getSelected().data;
		menu.submitDataRequest(menu, 'subscription/ViewSubscriptionEdit',
				'subscription/Subscription-edit.html', {
					id : data.id_subscr,
					name : data.subscription_name
				});
	}

	function del(b, e) {
		if (smInfo.getCount() == 0) {
			App.ui.message('Необходимо выбрать значение для удаления.');
			return;
		}

		App.ui.confirm('Удалить подписку '
				+ smInfo.getSelected().data.subscription_name + '?',
				delCallback);
	}
	function delCallback() {
		var id = smInfo.getSelected().data.id_subscr;
		Ext.Ajax.request({
			method : 'DELETE',
			url : 'rest/ViewSubscription/' + id + '.do',
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Выполняется запрос.',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					stInfo.reload();
				} else if (answer.code == 'login') {
					App.ui.sessionExpired();
				} else {
					App.ui.error(answer.message);
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен.');
			}
		});
	}

	function subscrStart(b, e) {
		if (smInfo.getCount() == 0) {
			App.ui.message('Необходимо выбрать подписку.');
			return;
		}

		var id = smInfo.getSelected().data.id_subscr;
		Ext.Ajax.request({
			method : 'GET',
			url : 'rest/ViewSubscription/' + id + '/start.do',
			timeout : 10 * 60 * 10000, // 10 min
			waitMsg : 'Выполняется запрос.',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					stInfo.reload();
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

	function subscrStop(b, e) {
		if (smInfo.getCount() == 0) {
			App.ui.message('Необходимо выбрать подписку.');
			return;
		}

		var id = smInfo.getSelected().data.id_subscr;
		Ext.Ajax.request({
			method : 'GET',
			url : 'rest/ViewSubscription/' + id + '/stop.do',
			timeout : 10 * 60 * 10000, // 10 min
			waitMsg : 'Выполняется запрос.',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					stInfo.reload();
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
		id : 'ViewSubscription-component',
		title : 'Список подписок',
		frame : true,
		closable : true,

		tbar : [ {
			text : 'Добавить',
			handler : add
		}, {
			text : 'Изменить',
			handler : edit
		}, {
			text : 'Удалить',
			handler : del
		}, ' ', {
			text : 'Запустить',
			handler : subscrStart
		}, {
			text : 'Остановить',
			handler : subscrStop
		} ],

		store : stInfo,
		selModel : smInfo,
		columns : [ new Ext.grid.RowNumberer({
			width : 30
		}), {
			header : 'SUBSCRIPTION_NAME',
			dataIndex : 'subscription_name',
			width : 50
		}, {
			header : 'COMMENT',
			dataIndex : 'subscription_comment',
			width : 100
		}, {
			header : 'STATUS',
			dataIndex : 'subscription_status',
			width : 30
		} ],
		viewConfig : {
			forceFit : true,
			emptyText : 'Записи не найдены'
		}
	});
})();
