/**
 * Расчёт EPS по компании
 */
(function() {

	var filter = new Ext.form.ComboBox({
		width : 100,
		displayField : 'name',
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'rest/BuildEPS/Filter.do',
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
			url : 'rest/BuildEPS/FilterEquities.do',
			// root : 'info',
			fields : [ 'id', 'name' ],
			sortInfo : {
				field : 'name'
			}
		}),
		loadingText : 'Поиск...',
		minChars : 2,
		triggerAction : 'all',
		listeners : {
			select : function(combo, record, index) {
				filterUpdate();
			}
		}
	});

	var companies = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : true,
		url : 'rest/BuildEPS/Equities.do',
		// root : 'info',
		fields : [ 'id_sec', 'security_code', 'short_name' ],
		sortInfo : {
			field : 'security_code'
		},
		listeners : App.ui.listenersJsonStore()
	});

	function filterUpdate() {
		companies.reload({
			params : {
				filter : filter.getValue(),
				equity : App.Combo.getValueId(equities),
				fundamentals : 1
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
			equities.setValue(equities.originalValue);
			filterUpdate();
		}
	} ];

	var result = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : false,
		// root : 'info',
		fields : [ 'security_code', 'bv_growth_rate', 'eps_growth_status',
				'eps_median_status', 'pb_median', 'pe_median_status',
				'periodical_eps_status', 'yearly_eps_status' ],
		sortInfo : {
			field : 'security_code'
		}
	});

	var sm = new Ext.grid.CheckboxSelectionModel();

	function calculate(self) {
		if (sm.getCount() == 0) {
			App.ui.confirm('Выполнить расчёт по всем компаниям?', calcAll);
		} else {
			App.ui.confirm('Выполнить расчёт по выбранным компаниям?',
					calcChecked);
		}
	}

	function calcChecked() {
		var ids = [];
		sm.each(function(item) {
			ids.push(item.data.id_sec);
			return true;
		});

		Ext.Ajax.request({
			url : 'rest/BuildEPS/CalculateEps.do',
			params : {
				ids : ids
			},
			timeout : 1000000000,
			waitMsg : 'Расчёт по выбранным компаниям',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					result.loadData(answer.item);
					App.ui.message('Расчёт произведен');
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

	function calcAll() {
		Ext.Ajax.request({
			url : 'rest/BuildEPS/CalculateEpsAll.do',
			timeout : 1000000000,
			waitMsg : 'Расчёт по всем компаниям',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					result.loadData(answer.item);
					App.ui.message('Расчёт произведен');
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
		id : 'BuildEPS-component',
		title : 'Расчёт EPS по компаниям',
		frame : false,
		closable : true,
		layout : 'border',

		items : [ {
			region : 'north',
			autoHeight : true,
			buttonAlign : 'left',
			buttons : [ {
				text : 'Рассчитать EPS',
				handler : calculate
			} ]
		}, {
			region : 'west',
			width : 500,
			xtype : 'grid',
			title : 'Компании',
			frame : true,
			enableHdMenu : false,

			tbar : tbarFilter,

			store : companies,
			selModel : sm,
			columns : [ sm, {
				header : 'security_code',
				dataIndex : 'security_code',
				width : 50
			}, {
				header : 'short_name',
				dataIndex : 'short_name',
				width : 50
			} ],

			viewConfig : {
				forceFit : true,
				emptyText : 'Записи не найдены'
			}
		}, {
			region : 'center',
			xtype : 'grid',
			title : 'Результат',
			frame : true,
			enableHdMenu : false,

			store : result,
			columns : [ {
				header : 'Security Code',
				dataIndex : 'security_code',
				width : 50
			}, {
				header : 'bv_growth_rate',
				dataIndex : 'bv_growth_rate',
				width : 20
			}, {
				header : 'eps_growth_status',
				dataIndex : 'eps_growth_status',
				width : 20
			}, {
				header : 'eps_median_status',
				dataIndex : 'eps_median_status',
				width : 20
			}, {
				header : 'pb_median',
				dataIndex : 'pb_median',
				width : 20
			}, {
				header : 'pe_median_status',
				dataIndex : 'pe_median_status',
				width : 20
			}, {
				header : 'periodical_eps_status',
				dataIndex : 'periodical_eps_status',
				width : 20
			}, {
				header : 'yearly_eps_status',
				dataIndex : 'yearly_eps_status',
				width : 20
			} ],

			viewConfig : {
				forceFit : true,
				emptyText : 'Расчёт не выполнялся'
			}
		} ]
	});
})();
