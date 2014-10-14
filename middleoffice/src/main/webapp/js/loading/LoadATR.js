/**
 * Загрузка ATR
 */
(function() {

	var _dateStart = Ext.id();
	var _dateEnd = Ext.id();
	var _TAPeriod = Ext.id();

	var selectMAType = new Ext.form.ComboBox({
		fieldLabel : 'MAType',
		width : 150,
		value : 'Exponential',
		valueField : 'name',
		displayField : 'name',
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'rest/LoadATR/MAType.do',
			// root : 'info',
			fields : [ 'id', 'name' ]
		}),
		allowBlank : false,
		loadingText : 'Поиск...',
		triggerAction : 'all',
		editable : false
	});

	var selectPeriod = new Ext.form.ComboBox({
		fieldLabel : 'Период',
		width : 150,
		value : 'DAILY',
		valueField : 'name',
		displayField : 'name',
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'rest/LoadATR/Period.do',
			// root : 'info',
			fields : [ 'id', 'name' ]
		}),
		allowBlank : false,
		loadingText : 'Поиск...',
		triggerAction : 'all',
		editable : false
	});

	var selectCalendar = new Ext.form.ComboBox({
		fieldLabel : 'Календарь',
		width : 150,
		value : 'CALENDAR',
		valueField : 'name',
		displayField : 'name',
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'rest/LoadATR/Calendar.do',
			// root : 'info',
			fields : [ 'id', 'name' ]
		}),
		allowBlank : false,
		loadingText : 'Поиск...',
		triggerAction : 'all',
		editable : false
	});

	var storeSec = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : true,
		url : 'rest/LoadATR/Securities.do',
		// root : 'info',
		fields : [ 'id_sec', 'security_code', 'short_name', 'type_id' ],
		sortInfo : {
			field : 'security_code'
		},
		listeners : App.ui.listenersJsonStore()
	});

	var filter = new Ext.form.ComboBox({
		width : 100,
		value : 'Все',
		displayField : 'name',
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'rest/LoadATR/Filter.do',
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
		}
	});

	var security = new Ext.form.ComboBox({
		width : 150,
		displayField : 'name',
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'rest/LoadATR/FilterSecurities.do',
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
		fields : [ 'security', 'date', 'value' ]
	});

	function loadData(self) {
		var ds = Ext.getCmp(_dateStart);
		if (!ds.validate()) {
			App.ui.message('Необходимо заполнить дату!');
			return;
		}

		var de = Ext.getCmp(_dateEnd);
		if (!de.validate()) {
			App.ui.message('Необходимо заполнить дату!');
			return;
		}

		var p = Ext.getCmp(_TAPeriod);
		if (!p.validate()) {
			App.ui.message('Необходимо заполнить TAPeriod!');
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
			url : 'rest/LoadATR.do',
			params : {
				securities : ids,
				typeMA : selectMAType.getValue(),
				periodTA : p.getValue(),
				period : selectPeriod.getValue(),
				calendar : selectCalendar.getValue(),
				dateStart : App.util.Format.dateYMD(ds.getValue()),
				dateEnd : App.util.Format.dateYMD(de.getValue())
			},
			timeout : 10 * 60 * 10000, // 10 min
			waitMsg : 'Выполняется запрос к Bloomberg',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					storeResult.loadData(answer.item);
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	return new Ext.Panel({
		id : 'LoadATR-component',
		layout : 'border',
		frame : true,
		closable : true,
		title : 'Загрузка ATR',

		items : [ {
			region : 'north',
			height : 125,
			layout : 'hbox',
			layoutConfig : {
				align : 'stretch'
			},

			items : [ {
				xtype : 'form',
				margins : '5',
				items : [ {
					id : _dateStart,
					xtype : 'datefield',
					fieldLabel : 'Начало периода',
					width : 100,
					format : 'd.m.Y',
					allowBlank : false
				}, {
					id : _TAPeriod,
					xtype : 'numberfield',
					fieldLabel : 'TAPeriod',
					width : 150,
					allowBlank : false,
					value : 7
				}, selectPeriod ]
			}, {
				xtype : 'container',
				width : 10
			}, {
				xtype : 'form',
				margins : '5',
				items : [ {
					id : _dateEnd,
					xtype : 'datefield',
					fieldLabel : 'Конец периода',
					width : 100,
					format : 'd.m.Y',
					allowBlank : false
				}, selectMAType, selectCalendar ]
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
				header : 'SECURITY',
				dataIndex : 'security',
				width : 150,
				sortable : true
			}, {
				header : 'DATE',
				dataIndex : 'date',
				// align : 'center',
				renderer : App.util.Renderer.date(),
				width : 100
			}, {
				header : 'VALUE',
				dataIndex : 'value',
				align : 'right',
				renderer : App.util.Renderer.number(6),
				width : 100
			} ],
			viewConfig : {
				// forceFit : true,
				emptyText : 'Записи не найдены'
			}
		} ]
	});
})();
