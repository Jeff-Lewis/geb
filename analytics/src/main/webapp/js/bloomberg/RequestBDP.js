/**
 * BDP запрос
 */
(function() {

	var stCodes = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : true,
		url : 'rest/RequestBDP/Securities.do',
		// root : 'sec',
		fields : [ 'calculation_crncy', 'security_code', 'short_name',
				'all_flag', 'portfolio', 'wl_flag', 'pivot', 'new_flag' ],
		sortInfo : {
			field : 'short_name'
		},
		listeners : App.ui.listenersJsonStore()
	});

	var smCodes = new Ext.grid.CheckboxSelectionModel();

	var stParams = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : true,
		url : 'rest/RequestBDP/Params.do',
		// root : 'params',
		fields : [ 'name' ],
		sortInfo : {
			field : 'name'
		}
	});

	var smParams = new Ext.grid.CheckboxSelectionModel();

	var filter = new Ext.form.ComboBox({
		width : 100,
		displayField : 'name',
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'rest/RequestBDP/EquitiesFilter.do',
			// root : 'info',
			fields : [ 'name' ],
			sortInfo : {
				field : 'name'
			}
		}),
		editable : false,
		allowBlank : true,
		loadingText : 'Поиск...',
		triggerAction : 'all',
		listeners : {
			select : function(combo, record, index) {
				filterUpdate();
			}
		},
		value : 'Все'
	});

	var equities = new Ext.form.ComboBox({
		width : 150,
		displayField : 'name',
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'rest/RequestBDP/Equities.do',
			// root : 'info',
			fields : [ 'id', 'name' ],
			sortInfo : {
				field : 'name'
			}
		}),
		loadingText : 'Поиск...',
		minChars : 2,
		allowBlank : true,
		loadingText : 'Поиск...',
		triggerAction : 'all',
		listeners : {
			select : function(combo, record, index) {
				filterUpdate();
			}
		}
	});

	var fundamentals = new Ext.form.Checkbox({
		value : false,
		listeners : {
			check : function(checkbox, checked) {
				filterUpdate();
			}
		}
	});

	function filterUpdate() {
		stCodes.reload({
			params : {
				filter : filter.getValue(),
				equities : App.Combo.getValueId(equities),
				fundamentals : fundamentals.getValue() ? 5 : 4
			}
		});
	}

	var tbarFilter = [ 'Фильтр', filter, {
		text : 'X',
		handler : function() {
			filter.setValue(filter.originalValue);
			filterUpdate();
		}
	}, ' ', 'Компания', equities, {
		text : 'X',
		handler : function() {
			equities.setValue('');
			filterUpdate();
		}
	}, ' ', 'Fundamentals', fundamentals ];

	function clean(b, e) {
		smCodes.clearSelections();

		smParams.clearSelections();
	}

	function request(b, e) {
		if (smCodes.getCount() == 0) {
			App.ui.message('Необходимо галочками выбрать организации!');
			return;
		}

		if (smParams.getCount() == 0) {
			App.ui.message('Необходимо галочками выбрать параметры!');
			return;
		}

		var ids = [];
		smCodes.each(function(item) {
			ids.push(item.data.security_code);
			return true;
		});

		var idsp = [];
		smParams.each(function(item) {
			idsp.push(item.data.name);
			return true;
		});

		Ext.Ajax.request({
			url : 'rest/RequestBDP.do',
			params : {
				security : ids,
				params : idsp
			},
			// timeout : 10 * 60 * 10000, // 10 min
			timeout : 1000000000,
			waitMsg : 'Выполняется запрос к Bloomberg',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					App.ui.message('Данные загружены.');
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

	function requestDaily(b, e) {
		if (smCodes.getCount() == 0) {
			App.ui.message('Необходимо галочками выбрать организации!');
			return;
		}

		var ids = [];
		smCodes.each(function(item) {
			ids.push(item.data.security_code);
			return true;
		});

		Ext.Ajax.request({
			url : 'rest/RequestBDP.do',
			params : {
				security : ids
			},
			// timeout : 10 * 60 * 10000, // 10 min
			timeout : 1000000000,
			waitMsg : 'Выполняется запрос к Bloomberg',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					App.ui.message('Данные загружены.');
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

	return new Ext.Panel({
		id : 'RequestBDP-component',
		title : 'BDP запрос',
		frame : true,
		closable : true,
		layout : 'border',

		items : [ {
			region : 'north',
			autoHeight : true,
			layout : 'hbox',

			buttonAlign : 'left',
			buttons : [ {
				text : 'Очистить форму',
				handler : clean
			}, {
				text : 'Запрос данных',
				handler : request
			}, {
				text : 'Обновить ежедневные данные',
				handler : requestDaily
			} ]
		}, {
			region : 'west',
			width : 600,

			xtype : 'grid',
			frame : true,
			enableHdMenu : false,
			tbar : tbarFilter,
			store : stCodes,
			selModel : smCodes,
			columns : [ smCodes, {
				header : 'BLOOMBERG_CODE',
				dataIndex : 'security_code',
				width : 35
			}, {
				header : 'NAME',
				dataIndex : 'short_name',
				width : 65
			} ],
			viewConfig : {
				forceFit : true,
				emptyText : 'Записи не найдены'
			}
		}, {
			region : 'center',

			xtype : 'grid',
			frame : true,
			enableHdMenu : false,
			store : stParams,
			selModel : smParams,
			columns : [ smParams, {
				header : 'PARAMETER',
				dataIndex : 'name'
			} ],
			viewConfig : {
				forceFit : true,
				emptyText : 'Записи не найдены'
			}
		} ]
	});
})();
