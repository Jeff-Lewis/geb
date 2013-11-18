/**
 * BDH запрос
 */
(function() {

	var _dateStart = Ext.id();
	var _dateEnd = Ext.id();

	var stCodes = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : true,
		url : 'rest/RequestBDH/Securities.do',
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
		url : 'rest/RequestBDH/Params.do',
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
			url : 'rest/RequestBDH/EquitiesFilter.do',
			// root : 'info',
			fields : [ 'name' ],
			sortInfo : {
				field : 'name'
			}
		}),
		editable : false,
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
			url : 'rest/RequestBDH/Equities.do',
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

	var periodSelect = new Ext.form.ComboBox({
		width : 150,
		displayField : 'name',
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'rest/RequestBDH/Period.do',
			// root : 'info',
			fields : [ 'name' ]
		}),
		editable : false,
		allowBlank : true,
		emptyText : 'Выберите период',
		loadingText : 'Поиск...',
		triggerAction : 'all',
		value : 'QUARTERLY'
	});

	var calendarSelect = new Ext.form.ComboBox({
		width : 150,
		displayField : 'name',
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'rest/RequestBDH/Calendar.do',
			// root : 'info',
			fields : [ 'name' ]
		}),
		editable : false,
		allowBlank : true,
		emptyText : 'Выберите календарь',
		loadingText : 'Поиск...',
		triggerAction : 'all',
		value : 'CALENDAR'
	});

	function clean(b, e) {
		smCodes.clearSelections();

		smParams.clearSelections();

		var ds = Ext.getCmp(_dateStart);
		ds.setValue(ds.originalValue);
		ds.clearInvalid();

		var de = Ext.getCmp(_dateEnd);
		de.setValue(de.originalValue);
		de.clearInvalid();

		periodSelect.setValue(periodSelect.originalValue);

		calendarSelect.setValue(calendarSelect.originalValue);
	}

	function request(b, e) {
		var ds = Ext.getCmp(_dateStart);
		var de = Ext.getCmp(_dateEnd);

		var vds = ds.validate();
		var vde = de.validate();

		if (!vds || !vde) {
			App.ui.message('Необходимо заполнить дату!');
			return;
		}

		if (smCodes.getCount() == 0) {
			App.ui.message('Необходимо галочками выбрать организации!');
			return;
		}

		if (smParams.getCount() == 0) {
			App.ui.message('Необходимо галочками выбрать параметры!');
			return;
		}

		var ids = [];
		var idc = [];
		smCodes.each(function(item) {
			ids.push(item.data.security_code + '|'
					+ item.data.calculation_crncy);
			idc.push(item.data.calculation_crncy);
			return true;
		});

		var idsp = [];
		smParams.each(function(item) {
			idsp.push(item.data.name);
			return true;
		});

		Ext.Ajax.request({
			url : 'rest/RequestBDH.do',
			params : {
				security : ids,
				params : idsp,
				dateStart : ds.getValue().format('Ymd'),
				dateEnd : de.getValue().format('Ymd'),
				period : periodSelect.getValue(),
				calendar : calendarSelect.getValue(),
				currency : idc
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
		id : 'RequestBDH-component',
		title : 'BDH запрос',
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
				text : 'DateStart:'
			}, {
				id : _dateStart,
				xtype : 'datefield',
				format : 'd.m.Y',
				allowBlank : false
			}, {
				margins : '2 5 0 25',
				xtype : 'label',
				style : 'font-weight: bold;',
				text : 'DateEnd:'
			}, {
				id : _dateEnd,
				xtype : 'datefield',
				format : 'd.m.Y',
				allowBlank : false
			}, {
				margins : '2 5 0 25',
				xtype : 'label',
				style : 'font-weight: bold;',
				text : 'Период:'
			}, periodSelect, {
				margins : '2 5 0 25',
				xtype : 'label',
				style : 'font-weight: bold;',
				text : 'Календарь:'
			}, calendarSelect ],

			buttonAlign : 'left',
			buttons : [ {
				text : 'Очистить форму',
				handler : clean
			}, {
				text : 'Запрос данных',
				handler : request
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
