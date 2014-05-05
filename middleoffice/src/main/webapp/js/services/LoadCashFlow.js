/**
 * Загрузка дат погашений
 */
(function() {

	var info = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : true,
		url : 'rest/LoadCashFlow/Securities.do',
		// root : 'info',
		fields : [ 'id', 'security_code', 'fst_settle_date', 'bond' ],
		sortInfo : {
			field : 'security_code'
		},
		listeners : App.ui.listenersJsonStore()
	});

	var sm = new Ext.grid.CheckboxSelectionModel();

	var storeResult = new Ext.data.GroupingStore({
		autoDestroy : true,
		autoLoad : false,
		reader : new Ext.data.JsonReader({
			// root : 'info',
			fields : [ 'security', 'date', 'value', 'value2' ],
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
			with (item.data) {
				ids.push(id + ';' + fst_settle_date + ';' + security_code);
			}
			return true;
		});

		Ext.Ajax.request({
			url : 'rest/LoadCashFlow.do',
			params : {
				securities : ids
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

	function loadDataNew(self) {
		if (sm.getCount() == 0) {
			App.ui.message('Необходимо выбрать BLOOMBERG_CODE!');
			return;
		}

		var ids = [];
		sm.each(function(item) {
			with (item.data) {
				ids.push(id + ';' + fst_settle_date + ';' + security_code);
			}
			return true;
		});

		Ext.Ajax.request({
			url : 'rest/LoadCashFlow/New.do',
			params : {
				securities : ids
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
		id : 'LoadCashFlow-component',
		layout : 'border',
		frame : true,
		closable : true,
		title : 'Загрузка дат погашений',

		items : [ {
			region : 'north',
			autoHeight : true,

			buttonAlign : 'left',
			buttons : [ {
				text : 'Запрос данных',
				handler : loadData
			}, {
				text : 'Запрос данных /новый',
				handler : loadDataNew
			} ]
		}, {
			region : 'west',
			width : 250,

			xtype : 'grid',
			frame : true,
			enableHdMenu : false,

			store : info,
			selModel : sm,
			columns : [ sm, new Ext.grid.RowNumberer(), {
				header : 'BOND',
				dataIndex : 'bond'
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
				header : 'Payment Date',
				dataIndex : 'date',
				align : 'center',
				renderer : App.util.Renderer.date(),
				width : 100
			}, {
				header : 'Coupon Amount',
				dataIndex : 'value',
				align : 'right',
				renderer : App.util.Renderer.number(6),
				width : 100
			}, {
				header : 'Principal Amount',
				dataIndex : 'value2',
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
