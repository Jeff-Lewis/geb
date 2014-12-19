/**
 * Редактирование опционов
 */
(function() {

	var _nameTicker = Ext.id();

	var futureSelect = new Ext.form.ComboBox({
		width : 150,
		valueField : 'id',
		displayField : 'name',
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'rest/ViewOptions/Options.do',
			// root : 'info',
			fields : [ 'id', 'name' ],
			sortInfo : {
				field : 'name'
			}
		}),
		emptyText : 'Выберите опцион',
		loadingText : 'Поиск...',
		triggerAction : 'all',
		editable : false
	});

	var storeSec = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : true,
		url : 'rest/ViewOptions/Securities.do',
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
		url : 'rest/ViewOptions/Portfolio.do',
		// root : 'info',
		fields : [ 'id_sec', 'ticker', 'deal_name', 'name',
				'date_insert' ],
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

	var security = new Ext.form.ComboBox({
		width : 150,
		displayField : 'name',
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'rest/ViewOptions/FilterSecurities.do',
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
				filter : 'Option',
				security : App.Combo.getValueId(security)
			}
		});
	}

	var tbarFilter = [ 'Инструмент', security, {
		text : 'X',
		handler : function() {
			security.setValue(security.originalValue);
			filterUpdate();
		}
	} ];

	function addAccordance(self) {
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
			url : 'rest/ViewOptions/Add.do',
			params : {
				code : smSec.getSelected().data.id_sec,
				deal : tiker,
				futures : futureSelect.getValue()
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Проставляем соотвествие',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					App.ui.message('Соотвествие задано!', false, reload);
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	function delAccordance(self) {
		if (smP.getCount() == 0) {
			App.ui.message('Необходимо выбрать организации!');
			return;
		}

		var data = smP.getSelected().data;
		Ext.Ajax.request({
			url : 'rest/ViewOptions/Del.do',
			params : {
				code : data.id_sec,
				deal : data.deal_name
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Удаление компании из портфеля',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					App.ui.message('Отмеченные компании удалены!', false,
							reload);
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	return new Ext.Panel({
		id : 'ViewOptions-component',
		layout : 'border',
		frame : true,
		closable : true,
		title : 'Редактирование опционов',
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
				width : 150
			}, {
				margins : '7 5 5 5',
				xtype : 'label',
				style : 'font-weight: bold;',
				text : 'Наименование опциона:',
			}, futureSelect ],

			buttonAlign : 'left',
			buttons : [ {
				width : 150,
				text : 'Задать соответствие',
				handler : addAccordance
			}, {
				width : 150,
				text : 'Удалить соответствие',
				handler : delAccordance
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
				stripeRows : true,
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
			columns : [ smP, new Ext.grid.RowNumberer(), {
				header : 'TICKER',
				width : 50,
				dataIndex : 'ticker',
				sortable : true
			}, {
				header : 'DEAL_NAME',
				width : 30,
				dataIndex : 'deal_name',
				sortable : true
			}, {
				header : 'NAME',
				width : 30,
				dataIndex : 'name',
				sortable : true
			}, {
				header : 'DATE_INSERT',
				dataIndex : 'date_insert',
				// align : 'center',
				renderer : App.util.Renderer.datetime(),
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
