/**
 * multi request param form
 */

(function() {

	var _security = Ext.id();
	var _params = Ext.id();
	var _dateStart = Ext.id();
	var _dateEnd = Ext.id();

	var _rgName = Ext.id();
	var accTypeRadioGroup = new Ext.form.RadioGroup({
		xtype : 'radiogroup',
		width : 700,
		items : [ {
			boxLabel : 'все',
			name : _rgName,
			checked : true,
			inputValue : 1
		}, {
			boxLabel : 'портфель',
			name : _rgName,
			inputValue : 2
		}, {
			boxLabel : 'watchlist',
			name : _rgName,
			inputValue : 3
		}, {
			boxLabel : 'сводная',
			name : _rgName,
			inputValue : 4
		} ],
		listeners : {
			change : function(self, radio) {
				grid.getStore().clearFilter();
				if (radio.inputValue == 1) {
					grid.getStore().filter('all_flag', '1', true, false);
				}
				if (radio.inputValue == 2) {
					grid.getStore().filter('port_flag', '1', true, false);
				}
				if (radio.inputValue == 3) {
					grid.getStore().filter('wl_flag', '1', true, false);
				}
				if (radio.inputValue == 4) {
					grid.getStore().filter('pivot', '1', true, false);
				}
			}
		}
	});

	var periodSelect = new Ext.form.ComboBox({
		fieldLabel : 'Период',
		width : 150,
		hiddenName : 'period',
		valueField : 'name',
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'dictionary/Period.html',
			root : 'info',
			fields : [ 'name' ]
		}),
		allowBlank : true,
		emptyText : 'Выберите период',
		displayField : 'name',
		loadingText : 'Поиск...',
		triggerAction : 'all'
	});

	var calendarSelect = new Ext.form.ComboBox({
		fieldLabel : 'Календарь',
		width : 150,
		hiddenName : 'calendar',
		valueField : 'name',
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'dictionary/calendar.html',
			root : 'info',
			fields : [ 'name' ]
		}),
		allowBlank : true,
		emptyText : 'Выберите календарь',
		displayField : 'name',
		loadingText : 'Поиск...',
		triggerAction : 'all'
	});

	var sectorSelect = new Ext.form.ComboBox({
		fieldLabel : 'Cектор',
		width : 150,
		hiddenName : 'sectorCode',
		valueField : 'sector',
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'organization/sector.html',
			root : 'infoSector',
			fields : [ 'sector' ]
		}),
		allowBlank : true,
		emptyText : 'Выберите сектор',
		displayField : 'sector',
		loadingText : 'Поиск...',
		triggerAction : 'all',
		listeners : {
			select : function(combo, record) {
				combo.secId = record.data.sector;
				Ext.Ajax.request({
					url : 'staticdatarequest/multi-request-form.html',
					params : {
						fSec : combo.secId
					},
					timeout : 10 * 60 * 1000, // 10 min
					waitMsg : 'Фильтруем по сектору',
					success : function(xhr) {
						var answer = Ext.decode(xhr.responseText);
						if (answer.success) {
							container.loadData(answer);
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
		}
	});

	var storeGrid = new Ext.data.JsonStore({
		autoDestroy : true,
		root : 'info',
		fields : [ 'date', 'value', 'params', 'security' ]
	});

	var store = new Ext.data.JsonStore({
		autoDestroy : true,
		root : 'sec',
		fields : [ 'crncy', 'security_code', 'short_name', 'all_flag',
		           'port_flag', 'wl_flag', 'pivot' ]
	});

	var storePar = new Ext.data.JsonStore({
		autoDestroy : true,
		root : 'params',
		fields : [ 'param_id', 'code' ]
	});

	var resultGrid = new Ext.grid.GridPanel({
		frame : true,
		height : 390,
		autoScroll : true,
		store : storeGrid,
		columns : [ new Ext.grid.RowNumberer({
			width : 30
		}), {
			header : 'date',
			dataIndex : 'date',
			width : 40,
			format : 'd.m.Y',
			sortable : true
		}, {
			header : 'value',
			dataIndex : 'value',
			width : 70,
			sortable : true
		}, {
			header : 'params',
			dataIndex : 'params',
			width : 50,
			sortable : true
		}, {
			header : 'security',
			dataIndex : 'security',
			width : 150,
			sortable : true
		} ],
		viewConfig : {
			forceFit : true,
			emptyText : 'Записи не найдены'
		}
	});

	var sm = new Ext.grid.CheckboxSelectionModel();

	var grid = new Ext.grid.GridPanel({
		height : 280,
		autoScroll : true,
		store : store,
		selModel : sm,
		columns : [ new Ext.grid.RowNumberer({
			width : 30
		}), sm, {
			header : 'BLOOMBERG_CODE',
			width : 35,
			dataIndex : 'security_code',
			sortable : true
		}, {
			header : 'NAME',
			width : 50,
			dataIndex : 'short_name',
			sortable : true
		} ],
		viewConfig : {
			forceFit : true,
			emptyText : 'Записи не найдены'
		}
	});

	var smPar = new Ext.grid.CheckboxSelectionModel();

	var gridParam = new Ext.grid.GridPanel({
		height : 280,
		autoScroll : true,
		store : storePar,
		selModel : smPar,
		columns : [ new Ext.grid.RowNumberer({
			width : 30
		}), smPar, {
			header : 'PARAMETER',
			width : 50,
			dataIndex : 'code',
			sortable : true
		} ],
		viewConfig : {
			forceFit : true,
			emptyText : 'Записи не найдены'
		}
	});

	function dataSelect(self) {
		if (sm.getCount() == 0) {
			App.ui.message('Необходимо галочками выбрать организации!');
			return;
		}

		if (smPar.getCount() == 0) {
			App.ui.message('Необходимо галочками выбрать параметры!');
			return;
		}

		var ids = [];
		var idc = [];
		sm.each(function(item) {
			ids.push(item.data.security_code + '|' + item.data.crncy);
			idc.push(item.data.crncy);
		});

		var idsp = [];
		smPar.each(function(item) {
			idsp.push(item.data.code);
			return true;
		});

		Ext.Ajax.request({
			url : 'bloomberg/multi-data-request.html',
			params : {
				security : ids,
				params : idsp,
				dateStart : App.util.Format.dateYMD(Ext.getCmp(_dateStart).getValue()),
				dateEnd : App.util.Format.dateYMD(Ext.getCmp(_dateEnd).getValue()),
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
					container.loadData(answer);
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

	var container = new Ext.Panel({
		id : 'view-multi-request-data-grid-component',
		title : 'Мультизапрос',
		frame : true,
		closable : true,
		layout : 'absolute',

		items : [ {
			xtype : 'fieldset',
			height : 40,
			x : 0,
			y : 0,
			border : false,
			layout : 'column',
			autoHeight : true,
			items : [ {
				id : _dateStart,
				xtype : 'datefield',
				format : 'd.m.Y',
				fieldLabel : 'DateStart',
				name : 'DATESTART',
				allowBlank : false
			}, {
				id : _dateEnd,
				xtype : 'datefield',
				format : 'd.m.Y',
				fieldLabel : 'DateEnd',
				name : 'DATEEND',
				allowBlank : false
			}, {
				xtype : 'fieldset',
				border : false,
				width : 300,
				items : periodSelect
			}, {
				xtype : 'fieldset',
				border : false,
				width : 300,
				items : sectorSelect
			}, {
				xtype : 'fieldset',
				border : false,
				width : 300,
				items : calendarSelect
			}, {
				xtype : 'button',
				text : 'Запрос данных',
				handler : dataSelect
			}, {
				xtype : 'button',
				text : 'Очистить форму',
				handler : function(self) {
					menu.showMultiHistorParamsData();
				}
			} ]
		}, {
			xtype : 'fieldset',
			width : 800,
			border : false,
			x : 0,
			y : 60,
			items : accTypeRadioGroup
		}, {
			xtype : 'fieldset',
			height : 300,
			width : 390,
			border : false,
			x : 0,
			y : 80,
			items : grid
		}, {
			xtype : 'fieldset',
			height : 300,
			width : 420,
			border : false,
			x : 450,
			y : 80,
			items : gridParam
		}, {
			xtype : 'fieldset',
			height : 350,
			width : 920,
			border : true,
			x : 0,
			y : 395,
			items : resultGrid
		} ],

		loadData : function(data) {
			storeGrid.loadData(data); // info
			store.loadData(data); // sec
			storePar.loadData(data); // params
		}
	});

	return container;
})();
