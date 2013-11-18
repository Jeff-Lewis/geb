/**
 * BDP с override
 */
(function() {

	var _period = Ext.id();

	var stCodes = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : true,
		url : 'rest/RequestBDPovr/Securities.do',
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
		url : 'rest/RequestBDPovr/Params.do',
		// root : 'params',
		fields : [ 'code' ],
		sortInfo : {
			field : 'code'
		}
	});

	var smParams = new Ext.grid.CheckboxSelectionModel();

	var filter = new Ext.form.ComboBox({
		width : 100,
		displayField : 'name',
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'rest/RequestBDPovr/EquitiesFilter.do',
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
			url : 'rest/RequestBDPovr/Equities.do',
			// root : 'info',
			fields : [ 'id', 'name' ],
			sortInfo : {
				field : 'name'
			}
		}),
		loadingText : 'Поиск...',
		minChars : 2,
		typeAhead : false,
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

	var overSelect = new Ext.form.ComboBox({
		width : 150,
		displayField : 'code',
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'rest/RequestBDPovr/Override.do',
			// root : 'info',
			fields : [ 'code' ]
		}),
		editable : false,
		allowBlank : false,
		emptyText : 'Выберите override',
		loadingText : 'Поиск...',
		triggerAction : 'all',
	// value : 'CALENDAR'
	});

	function clean(b, e) {
		smCodes.clearSelections();

		smParams.clearSelections();

		overSelect.setValue(overSelect.originalValue);
		overSelect.clearInvalid();

		var p = Ext.getCmp(_period);
		p.setValue(p.originalValue);
		p.clearInvalid();
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
			ids.push(item.data.security_code + '|'
					+ item.data.calculation_crncy);
			return true;
		});

		var idsp = [];
		smParams.each(function(item) {
			idsp.push(item.data.code);
			return true;
		});

		Ext.Ajax.request({
			url : 'rest/RequestBDPovr.do',
			params : {
				security : ids,
				params : idsp,
				over : overSelect.getValue(),
				period : Ext.getCmp(_period).getValue()
			},
			timeout : 10 * 60 * 10000, // 10 min
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

	function requestQuarter(b, e) {
		if (smCodes.getCount() == 0) {
			App.ui.message('Необходимо галочками выбрать организации!');
			return;
		}

		if (smParams.getCount() == 0) {
			App.ui.message('Необходимо галочками выбрать параметры!');
			return;
		}

		var ids = [];
		var cur = [];
		smCodes.each(function(item) {
			ids.push(item.data.security_code + '|'
					+ item.data.calculation_crncy);
			cur.push(item.data.calculation_crncy);
			return true;
		});

		var idsp = [];
		smParams.each(function(item) {
			idsp.push(item.data.code);
			return true;
		});

		Ext.Ajax.request({
			url : 'rest/RequestBDPovr/Quarter.do',
			params : {
				security : ids,
				params : idsp,
				over : overSelect.getValue(),
				currency : cur
			},
			timeout : 10 * 60 * 10000, // 10 min
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
		id : 'RequestBDPoverride-component',
		title : 'BDP с override',
		frame : true,
		closable : true,
		layout : 'border',

		items : [ {
			region : 'north',
			autoHeight : true,
			layout : 'hbox',
			padding : 5,

			items : [ {
				margins : '2 5 0 0',
				xtype : 'label',
				style : 'font-weight: bold;',
				text : 'Параметр OVERRIDE:'
			}, overSelect, {
				margins : '2 5 0 25',
				xtype : 'label',
				style : 'font-weight: bold;',
				text : 'Период:'
			}, {
				id : _period,
				xtype : 'textfield',
				name : 'period',
				width : 100
			} ],

			buttonAlign : 'left',
			buttons : [ {
				text : 'Очистить форму',
				handler : clean
			}, {
				text : 'Запрос данных',
				handler : request
			}, {
				text : 'Все кварталы, полугодия, года',
				handler : requestQuarter
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
				dataIndex : 'code'
			} ],
			viewConfig : {
				forceFit : true,
				emptyText : 'Записи не найдены'
			}
		} ]
	});
})();
