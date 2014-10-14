/**
 * Загрузка ставки по купонам
 */
(function() {

	var storeSec = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : true,
		url : 'rest/LoadRateCoupon/Securities.do',
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
			url : 'rest/LoadRateCoupon/Filter.do',
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
			url : 'rest/LoadRateCoupon/FilterSecurities.do',
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

	var storeResult = new Ext.data.GroupingStore({
		autoDestroy : true,
		autoLoad : false,
		reader : new Ext.data.JsonReader({
			// root : 'info',
			fields : [ 'security', 'date', 'value' ],
		}),
		groupField : 'security'
	});

	function loadData(self) {
		if (sm.getCount() == 0) {
			App.ui.message('Необходимо выбрать BLOOMBERG_CODE!');
			return;
		}

		var ids = [];
		sm.each(function(item) {
			ids.push(item.data.id_sec + ';' + item.data.security_code);
			return true;
		});

		Ext.Ajax.request({
			url : 'rest/LoadRateCoupon.do',
			params : {
				securities : ids
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
		id : 'LoadRateCoupon-component',
		layout : 'border',
		frame : true,
		closable : true,
		title : 'Загрузка ставки по купонам',

		items : [ {
			region : 'north',
			height : 30,

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
				width : 30
			}), {
				header : 'security',
				dataIndex : 'security',
				hidden : true
			}, {
				header : 'Period End Date',
				dataIndex : 'date',
				align : 'center',
				renderer : App.util.Renderer.date(),
				width : 100
			}, {
				header : 'Coupon',
				dataIndex : 'value',
				align : 'right',
				renderer : App.util.Renderer.number(6),
				width : 100
			} ],
			view : new Ext.grid.GroupingView({
				// forceFit : true,
				emptyText : 'Записи не найдены',
				enableGrouping : true,
				hideGroupedColumn : true,
				showGroupName : false,
				startCollapsed : true,
				groupTextTpl : '{text} ({[values.rs.length]})'
			}),
		} ]
	});
})();
