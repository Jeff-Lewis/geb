/**
 * Справочник брокеров
 */
(function() {
	var info = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : true,
		url : 'rest/Brokers.do',
		// root : 'info',
		fields : [ 'id', 'full_name', 'rating', 'bloomberg_code',
				'cover_russian', 'short_name' ],
		sortInfo : {
			field : 'full_name'
		},
		listeners : App.ui.listenersJsonStore()
	});

	var sm = new Ext.grid.RowSelectionModel({
		singleSelect : true
	});

	function addItem(self) {
		showModal('utils/BrokersAdd');
	}

	function edtItem(self) {
		if (sm.getCount() == 0) {
			App.ui.message('Необходимо выбрать запись для редактирования!');
			return;
		}

		var id = sm.getSelected().data.id;
		showModal('utils/BrokersEdit', 'rest/Brokers/' + id + '.do');
	}

	function delItem(self) {
		if (sm.getCount() == 0) {
			App.ui.message('Необходимо выбрать запись для удаления.');
			return;
		}

		var _data = sm.getSelected().data;
		App.ui.confirm('Удалить запись "' + _data.full_name + '"?',
				confirmDelItem);
	}
	function confirmDelItem() {
		var _data = sm.getSelected().data;
		App.ui.confirm('Удаление брокера ' + _data.full_name + '<br/>'
				+ 'приведет к удалению всех прогнозов этого брокера.'
				+ '<br/>Продолжить?', ajaxDelItem);
	}
	function ajaxDelItem() {
		var id = sm.getSelected().data.id;
		Ext.Ajax.request({
			method : 'DELETE',
			url : 'rest/Brokers/' + id + '.do',
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Выполняется запрос',
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
		id : 'Brokers-component',
		title : 'Брокеры',
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

		store : info,
		selModel : sm,
		columns : [ new Ext.grid.RowNumberer({
			width : 30
		}), {
			header : 'Наименование',
			dataIndex : 'full_name',
			width : 150
		}, {
			header : 'Рейтинг',
			dataIndex : 'rating',
			align : 'center',
			width : 30
		}, {
			header : 'Код блумберг',
			dataIndex : 'bloomberg_code',
			align : 'center',
			width : 50
		}, {
			header : 'Расчет РЭ',
			dataIndex : 'cover_russian',
			align : 'center',
			width : 30,
			renderer : App.util.Renderer.bool()
		}, {
			header : 'Наименование краткое',
			dataIndex : 'short_name',
			width : 100
		} ],
		viewConfig : {
			forceFit : true,
			emptyText : 'Записи не найдены'
		}
	});
})();
