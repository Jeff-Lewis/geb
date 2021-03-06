/**
 * Добавить несколько компаний
 */
(function() {
	var GridRecord = Ext.data.Record.create([ 'code', 'state' ]);

	var data = new Ext.data.ArrayStore({
		autoDestroy : true,
		fields : [ 'code', 'state' ]
	});

	var sm = new Ext.grid.RowSelectionModel();

	function add() {
		Ext.MessageBox.prompt('Добавление', 'Код БЛУМБЕРГ', addText, this);
	}
	function addText(btn, text) {
		if ((btn == 'ok') && text) {
			data.add(new GridRecord({
				code : text,
				state : 'добавлено'
			}));
		}
	}

	function del() {
		if (data.getCount() == 0) {
			App.ui.message('Список компаний пуст.');
			return;
		}

		if (sm.getCount() == 0) {
			App.ui.message('Выберите компании для удаления из списка.');
			return;
		}

		if (sm.getCount() == 1) {
			var name = sm.getSelected().data.code;
			App.ui.confirm('Удалить компанию ' + name + '?', function() {
				data.remove(sm.getSelected());
			});
			return;
		}

		App.ui.confirm('Удалить выбранные компании?', function() {
			sm.lock();
			try {
				sm.each(function(r) {
					data.remove(r);
					return true;
				});
			} finally {
				sm.unlock();
			}
		});
	}

	function execute() {
		if (data.getCount() == 0) {
			App.ui.message('Список компаний пуст.');
			return;
		}

		var _codes = [];
		data.each(function(r) {
			_codes.push(r.data.code);
			return true;
		});

		Ext.Ajax.request({
			url : 'rest/CompanyAdd.do',
			params : {
				codes : _codes
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Обработка данных...',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					data.loadData(answer.item);
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	function executeBloom() {
		if (data.getCount() == 0) {
			App.ui.message('Список компаний пуст.');
			return;
		}

		var _codes = [];
		data.each(function(r) {
			_codes.push(r.data.code);
			return true;
		});

		Ext.Ajax.request({
			url : 'rest/CompanyAdd/Bloom.do',
			params : {
				codes : _codes
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Обработка данных...',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					data.loadData(answer.item);
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	return new Ext.grid.GridPanel({
		id : 'CompanyAdd-component',
		title : 'Добавление компаний',
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
		}, {
			text : 'Загрузить Bloomberg',
			handler : executeBloom
		} ],

		store : data,
		selModel : sm,
		columns : [ {
			header : 'Код Блумберг',
			dataIndex : 'code',
			width : 100
		}, {
			header : 'Состояние',
			dataIndex : 'state',
			width : 400
		} ],

		viewConfig : {
			forceFit : true,
			emptyText : 'Добавьте компании для загрузки'
		}
	});
})();
