/**
 * Ввод нового инструмента
 */
(function() {
	// var GridRecord = Ext.data.Record.create([ 'code', 'type', 'state' ]);

	var data = new Ext.data.ArrayStore({
		autoDestroy : true,
		fields : [ 'code', 'type', 'state' ]
	});

	var sm = new Ext.grid.CheckboxSelectionModel();

	function add() {
		menu.showModal(menu, 'services/NewInstrumentAdd');
	}

	function del() {
		if (data.getCount() == 0) {
			App.ui.message('Список компаний пуст.');
			return;
		}

		switch (sm.getCount()) {
		case 0:
			App.ui.message('Выберите компании для удаления из списка.');
			break;

		case 1:
			data.remove(sm.getSelected());
			break;

		default:
			sm.lock();
			try {
				sm.each(function(r) {
					data.remove(r);
					return true;
				});
			} finally {
				sm.unlock();
			}
			break;
		}
	}

	function execute() {
		if (data.getCount() == 0) {
			App.ui.message('Список компаний пуст.');
			return;
		}

		var _recs = [];
		data.each(function(r) {
			_recs.push(r.data.type + ':' + r.data.code);
			return true;
		});

		Ext.Ajax.request({
			url : 'rest/NewInstrument.do',
			params : {
				instruments : _recs
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Обработка данных...',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					data.loadData(answer.info);
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
		id : 'NewInstrument-component',
		title : 'Ввод нового инструмента',
		frame : true,
		closable : true,
		enableHdMenu : false,

		tbar : [ {
			text : 'Добавить',
			handler : add
		}, {
			text : 'Удалить',
			handler : del
		}, {
			text : 'Загрузить',
			handler : execute
		} ],

		store : data,
		selModel : sm,
		columns : [ sm, {
			header : 'Код Блумберг',
			dataIndex : 'code',
			width : 100
		}, {
			header : 'Тип инструмента',
			dataIndex : 'type',
			width : 100
		}, {
			header : 'Состояние',
			dataIndex : 'state',
			width : 400
		} ],

		viewConfig : {
			forceFit : true,
			emptyText : 'Добавьте инструменты для загрузки'
		}
	});
})();
