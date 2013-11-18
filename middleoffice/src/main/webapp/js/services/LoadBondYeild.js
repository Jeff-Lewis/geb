/**
 * Загрузка доходности облигаций
 */
(function() {

	var _dateStart = Ext.id();
	var _dateEnd = Ext.id();

	var storeSec = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : true,
		url : 'rest/LoadBondYeild/Securities.do',
		// root : 'info',
		fields : [ 'id_sec', 'security_code', 'short_name', 'type_id' ],
		sortInfo : {
			field : 'security_code'
		},
		listeners : App.ui.listenersJsonStore()
	});

	var filter = new Ext.form.ComboBox({
		width : 100,
		displayField : 'name',
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'rest/LoadBondYeild/Filter.do',
			// root : 'info',
			fields : [ 'name' ]
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
		value : 'Bond'
	});

	var security = new Ext.form.ComboBox({
		width : 150,
		displayField : 'name',
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'rest/LoadBondYeild/FilterSecurities.do',
			// root : 'info',
			fields : [ 'id', 'name' ],
			sortInfo : {
				field : 'name'
			}
		}),
		loadingText : 'Поиск...',
		triggerAction : 'all',
		minChars : 2,
		typeAhead : false,
		listeners : {
			select : function(combo, record, index) {
				combo.setValue(record.data.name);
				filterUpdate();
			}
		}
	});

	function filterUpdate() {
		storeSec.reload({
			params : {
				filter : filter.getValue(),
				security : App.Combo.getValueId(security)
			}
		});
	}

	var tbarFilter = [ 'Фильтр', filter, {
		text : 'X',
		handler : function() {
			filter.setValue(filter.originalValue);
			filterUpdate();
		}
	}, ' ', 'Инструмент', security, {
		text : 'X',
		handler : function() {
			security.setValue(security.originalValue);
			filterUpdate();
		}
	} ];

	var sm = new Ext.grid.CheckboxSelectionModel();

	var storeResult = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : false,
		// root : 'info',
		fields : [ 'date', 'value', 'params', 'security' ]
	});

	function loadData(self) {
		var ds = Ext.getCmp(_dateStart);
		var de = Ext.getCmp(_dateEnd);

		if (!ds.validate()) {
			ds.focus();
			App.ui.message('Необходимо заполнить дату!');
			return;
		}

		if (sm.getCount() == 0) {
			App.ui.message('Необходимо выбрать BLOOMBERG_CODE!');
			return;
		}

		var ids = [];
		sm.each(function(item) {
			ids.push(item.data.security_code);
			return true;
		});

		Ext.Ajax.request({
			url : 'rest/LoadBondYeild.do',
			params : {
				securities : ids,
				dateStart : App.util.Format.dateYMD(ds.getValue()),
				dateEnd : App.util.Format.dateYMD(de.getValue())
			},
			timeout : 10 * 60 * 10000, // 10 min
			waitMsg : 'Выполняется запрос к Bloomberg',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					storeResult.loadData(answer.item);
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
		id : 'LoadBondYeild-component',
		layout : 'border',
		frame : true,
		closable : true,
		title : 'Загрузка доходности облигаций',

		items : [ {
			region : 'north',
			margins : '5 0',
			autoHeight : true,
			layout : 'hbox',
			items : [ {
				xtype : 'label',
				style : 'font-weight: bold;',
				margins : '2 5 0 5',
				text : 'Начало периода:'
			}, {
				id : _dateStart,
				xtype : 'datefield',
				format : 'd.m.Y',
				allowBlank : false
			}, {
				xtype : 'label',
				style : 'font-weight: bold;',
				margins : '2 5 0 25',
				text : 'Конец периода:'
			}, {
				id : _dateEnd,
				xtype : 'datefield',
				format : 'd.m.Y',
				allowBlank : true
			} ],

			buttonAlign : 'left',
			buttons : [ {
				text : 'Запрос данных',
				handler : loadData
			} ]
		}, {
			region : 'west',
			width : 420,

			xtype : 'grid',
			frame : true,
			enableHdMenu : false,

			tbar : tbarFilter,

			store : storeSec,
			selModel : sm,
			columns : [ sm, new Ext.grid.RowNumberer(), {
				header : 'BLOOMBERG_CODE',
				dataIndex : 'security_code'
			}, {
				header : 'SHORT_NAME',
				dataIndex : 'short_name'
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

			store : storeResult,
			columns : [ new Ext.grid.RowNumberer({
				header : '#',
				width : 30
			}), {
				header : 'date',
				dataIndex : 'date',
				// align : 'center',
				renderer : App.util.Renderer.date(),
				width : 40,
				sortable : true
			}, {
				header : 'value',
				dataIndex : 'value',
				align : 'right',
				renderer : App.util.Renderer.number(2),
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
		} ]
	});
})();
