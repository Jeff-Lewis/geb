/**
 * Расчёт модели по компании
 */
(function() {

	var filter = new Ext.form.ComboBox({
		width : 100,
		displayField : 'name',
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'rest/BuildModel/Filter.do',
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
		valueField : 'id',
		displayField : 'name',
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'rest/BuildModel/FilterEquities.do',
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
		url : 'rest/BuildModel/Equities.do',
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
				equity : equities.getValue(),
				fundamentals : 2
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

	var result = new Ext.data.GroupingStore({
		autoDestroy : true,
		autoLoad : false,
		groupField : 'status',
		reader : new Ext.data.JsonReader({
			fields : [ 'security_code', 'status' ]
		}),
		sortInfo : {
			field : 'security_code'
		}
	});

	var sm = new Ext.grid.CheckboxSelectionModel();

	function calcModel() {
		if (sm.getCount() == 0) {
			App.ui.message('Необходимо выбрать организации!');
			return;
		}

		var ids = [];
		sm.each(function(item) {
			ids.push(item.data.id_sec);
			return true;
		});

		Ext.Ajax.request({
			url : 'rest/BuildModel/CalculateModel.do',
			params : {
				ids : ids
			},
			timeout : 1000000000,
			waitMsg : 'Выполняется расчёт модели',
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

	function calcConsolidated() {
		Ext.Ajax.request({
			url : 'rest/BuildModel/CalculateSvod.do',
			timeout : 1000000000,
			waitMsg : 'Выполняется расчёт модели',
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
		id : 'BuildModel-component',
		title : 'Расчёт модели по компаниям',
		frame : false,
		closable : true,
		layout : 'border',

		items : [ {
			region : 'north',
			autoHeight : true,
			buttonAlign : 'left',
			buttons : [ {
				text : 'Расчёт модели',
				handler : calcModel
			}, {
				text : 'Расчёт сводной',
				handler : calcConsolidated
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
				header : 'Status',
				dataIndex : 'status',
				width : 20
			} ],

			// viewConfig : {
			// forceFit : true,
			// emptyText : 'Записи не найдены'
			// }
			view : new Ext.grid.GroupingView({
				forceFit : true,
				emptyText : 'Расчёт не выполнялся',
				showGroupName : false,
				groupTextTpl : '{text} ({[values.rs.length]})'
			})
		} ]
	});
})();
