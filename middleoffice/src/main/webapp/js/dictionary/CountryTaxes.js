/**
 * Налоги по странам
 */
(function() {

	var info = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : true,
		autoSave : false,
		url : 'rest/CountryTaxes.do',
		// root : 'info',
		fields : [ 'id', 'security_type', 'name', 'alpha3', 'broker', 'value',
				'date_begin', 'date_end' ],
		sortInfo : {
			field : 'name'
		},
		listeners : App.ui.listenersJsonStore()
	});

	var sm = new Ext.grid.RowSelectionModel({
		singleSelect : true
	});

	function add() {
		menu.showModal(menu, 'dictionary/CountryTaxesAdd');
	}

	function edit() {
		if (sm.getCount() == 0) {
			App.ui.message('Необходимо выбрать запись для редактирования!');
			return;
		}

		var id = sm.getSelected().data.id;
		menu.showModal(menu, 'dictionary/CountryTaxesEdit',
				'rest/CountryTaxes/' + id + '.do');
	}

	function del() {
		if (sm.getCount() == 0) {
			App.ui.message('Необходимо выбрать запись для удаления!');
			return;
		}

		App.ui.confirm('Удалить запись для "' + sm.getSelected().data.name
				+ '"?', cbDel);
	}
	function cbDel() {
		var id = sm.getSelected().data.id;
		Ext.Ajax.request({
			method : 'DELETE',
			url : 'rest/CountryTaxes/' + id + '.do',
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
		id : 'CountryTaxes-component',
		title : 'Налоги по странам',
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
			header : 'Тип инструмента',
			dataIndex : 'security_type'
		}, {
			header : 'Страна',
			dataIndex : 'name'
		}, {
			header : 'Код',
			dataIndex : 'alpha3'
		}, {
			header : 'Брокер',
			dataIndex : 'broker'
		}, {
			header : 'Значение, %',
			dataIndex : 'value',
			align : 'right',
			renderer : App.util.Renderer.number()
		}, {
			header : 'Начало периода',
			dataIndex : 'date_begin',
			renderer : App.util.Renderer.date()
		}, {
			header : 'Конец периода',
			dataIndex : 'date_end',
			renderer : App.util.Renderer.date()
		} ],
		viewConfig : {
			forceFit : true,
			emptyText : 'Записи не найдены'
		}
	});
})();
