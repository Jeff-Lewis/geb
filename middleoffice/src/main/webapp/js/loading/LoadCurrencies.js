/**
 * Загрузка курсов валют
 */
(function() {

	var _dateStart = Ext.id();
	var _dateEnd = Ext.id();

	var storeSec = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : true,
		url : 'rest/LoadCurrencies/Securities.do',
		// root : 'info',
		fields : [ 'id', 'code', 'iso', 'bloomberg_code' ],
		sortInfo : {
			field : 'code'
		},
		listeners : App.ui.listenersJsonStore()
	});

	var sm = new Ext.grid.CheckboxSelectionModel();

	var storeResult = new Ext.data.GroupingStore({
		autoDestroy : true,
		autoLoad : false,
		reader : new Ext.data.JsonReader({
			fields : [ 'security', 'date', 'value' ]
		}),
		groupField : 'security',
		sortInfo: {
		    field: 'date'
		},
		listeners : App.ui.listenersJsonStore()
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
			ids.push(item.data.bloomberg_code);
			return true;
		});

		Ext.Ajax.request({
			url : 'rest/LoadCurrencies.do',
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
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	return new Ext.Panel({
		id : 'LoadCurrencies-component',
		layout : 'border',
		frame : true,
		closable : true,
		title : 'Загрузка курсов валют',

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

			store : storeSec,
			selModel : sm,
			columns : [ sm, new Ext.grid.RowNumberer(), {
				header : 'BLOOMBERG_CODE',
				dataIndex : 'bloomberg_code'
			}, {
				header : 'SHORT_NAME',
				dataIndex : 'iso'
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
				hidden : true
			}, {
				header : 'DATE',
				dataIndex : 'date',
				align : 'center',
				renderer : App.util.Renderer.date(),
				width : 80,
				sortable : true
			}, {
				header : 'VALUE',
				dataIndex : 'value',
				align : 'right',
				renderer : App.util.Renderer.number(4),
				width : 80,
				sortable : true
			} ],
			view : new Ext.grid.GroupingView({
				//forceFit : true,
				emptyText : 'Записи не найдены',
				enableGrouping : true,
				hideGroupedColumn : false,
				showGroupName : false,
				groupTextTpl : '{text}'
			})
		} ]
	});
})();
