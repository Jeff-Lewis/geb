/**
 * Редактирование акций
 */
(function() {

	var _nameTicker = Ext.id();

	var storeSec = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : true,
		url : 'rest/ViewShare/Securities.do',
		// root : 'info',
		fields : [ 'id_sec', 'security_code', 'short_name', 'type_id' ],
		sortInfo : {
			field : 'security_code'
		},
		listeners : App.ui.listenersJsonStore()
	});

	var smSec = new Ext.grid.CheckboxSelectionModel({
		singleSelect : true
	});

	var portfolio = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : true,
		url : 'rest/ViewShare/Portfolio.do',
		// root : 'info',
		fields : [ 'id_sec', 'deal_name', 'ticker', 'date_insert' ],
		sortInfo : {
			field : 'ticker'
		},
		listeners : App.ui.listenersJsonStore()
	});

	var smP = new Ext.grid.CheckboxSelectionModel({
		singleSelect : true
	});

	function reload() {
		filterUpdate();
		portfolio.reload();
	}

	var filter = new Ext.form.ComboBox({
		width : 100,
		displayField : 'name',
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'rest/ViewShare/Filter.do',
			// root : 'info',
			fields : [ 'id', 'name' ]
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
		value : 'Equity'
	});

	var security = new Ext.form.ComboBox({
		width : 150,
		displayField : 'name',
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'rest/ViewShare/FilterSecurities.do',
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

	function addCompany(self) {
		if (smSec.getCount() == 0) {
			App.ui.message('Необходимо выбрать котировку.');
			return;
		}

		var tiker = Ext.getCmp(_nameTicker).getValue();
		if (!tiker) {
			App.ui.message('Необходимо заполнить тикер.');
			return;
		}

		Ext.Ajax.request({
			url : 'rest/ViewShare/Add.do',
			params : {
				code : smSec.getSelected().data.id_sec,
				deal : tiker
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Добавляем котировку в портфель',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					App.ui.message('Организации успешно добавлены в портфель!',
							false, reload);
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

	function delCompany(self) {
		if (smP.getCount() == 0) {
			App.ui.message('Необходимо выбрать организации!');
			return;
		}

		var data = smP.getSelected().data;
		Ext.Ajax.request({
			url : 'rest/ViewShare/Del.do',
			params : {
				code : data.id_sec,
				deal : data.deal_name
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Удаление компании из портфеля',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					App.ui.message('Отмеченные компании удалены из портфеля!',
							false, reload);
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
		id : 'ViewShare-component',
		title : 'Редактирование акций',
		frame : true,
		closable : true,
		layout : 'border',

		items : [ {
			region : 'north',
			autoHeight : true,
			layout : 'hbox',
			items : [ {
				margins : '7 5 5 5',
				xtype : 'label',
				style : 'font-weight: bold;',
				text : 'Название тикера по сделкам:'
			}, {
				margins : '5 15 5 5',
				id : _nameTicker,
				xtype : 'textfield',
				allowBlank : false,
				width : 150
			} ],

			buttonAlign : 'left',
			buttons : [ {
				width : 150,
				text : 'Задать соответствие',
				handler : addCompany
			}, {
				width : 150,
				text : 'Удалить соответствие',
				handler : delCompany
			} ]
		}, {
			region : 'west',
			width : 420,
			xtype : 'grid',
			title : 'Тикеры блумберга',
			frame : true,
			enableHdMenu : false,

			tbar : tbarFilter,

			store : storeSec,
			selModel : smSec,
			columns : [ smSec, new Ext.grid.RowNumberer(), {
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
			title : 'Тикеры в портфеле',
			frame : true,
			enableHdMenu : false,

			store : portfolio,
			selModel : smP,
			columns : [ smP, new Ext.grid.RowNumberer({
				width : 30
			}), {
				header : 'TICKER',
				dataIndex : 'ticker',
				width : 30,
				sortable : true
			}, {
				header : 'DEAL_NAME',
				dataIndex : 'deal_name',
				width : 50,
				sortable : true
			}, {
				header : 'DATE_INSERT',
				dataIndex : 'date_insert',
				// align : 'center',
				renderer : App.util.Renderer.date(),
				width : 40,
				sortable : true
			} ],
			viewConfig : {
				forceFit : true,
				emptyText : 'Записи не найдены'
			}
		} ]
	});
})();
