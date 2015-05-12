/**
 * Список заданий
 */
(function() {

	var store = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : true,
		autoSave : false,
		url : 'JobberTasks',
		// root : 'info',
		fields : [ 'name', 'cron', 'parameter', 'description', 'enabled' ],
		// sortInfo : {
		// field : 'ticker'
		// },
		listeners : App.ui.listenersJsonStore()
	});

	var sm = new Ext.grid.RowSelectionModel({
		singleSelect : true
	});

	function start(btn) {
		if (sm.getCount() == 0) {
			App.ui.message('Необходимо выбрать задание.');
			return;
		}
		
		update('start');
	}


	function stop(btn) {
		if (sm.getCount() == 0) {
			App.ui.message('Необходимо выбрать задание.');
			return;
		}

		update('stop');
	}

	function update(action) {
		var data = sm.getSelected().data;

		if (data.cron == '-') {
			App.ui.message('Задание отключено.');
			return;
		}

		Ext.Ajax.request({
			url : 'JobberTasks.do',
			params : {
				action : action,
				task : data.parameter
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Сохранение',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					store.loadData(answer.item);
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	function exec() {
		if (sm.getCount() == 0) {
			App.ui.message('Необходимо выбрать задание.');
			return;
		}

		Ext.Ajax.request({
			url : 'JobberTasks/' + sm.getSelected().data.name,
			timeout : 20 * 60 * 1000, // 10 min
			waitMsg : 'Выполнение<br>' + sm.getSelected().data.description,
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					App.ui.message('Задача выполнена.');
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	return new Ext.grid.GridPanel({
		id : 'JobberTasks-component',
		title : 'Список заданий',
		closable : true,
		frame : true,
		enableHdMenu : false,

		tbar : [ {
			text : 'Включить',
			handler : start
		}, {
			text : 'Выключить',
			handler : stop
		}, ' ', {
			text : 'Выполнить',
			handler : exec
		} ],

		store : store,
		selModel : sm,
		columns : [ {
			header : 'enabled',
			dataIndex : 'enabled',
			align : 'center',
			width : 50,
			renderer : App.util.Renderer.bool(),
			sortable : true
		}, {
			header : 'name',
			dataIndex : 'name',
			width : 100,
			sortable : true
		}, {
			header : 'cron',
			dataIndex : 'cron',
			width : 150,
			sortable : true
		}, {
			header : 'description',
			dataIndex : 'description'
		} ],
		viewConfig : {
			forceFit : true,
			emptyText : 'Записи не найдены'
		}
	});
})();
