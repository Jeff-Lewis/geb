/**
 * Верификация остатков
 */
(function() {

	var _security = Ext.id();
	var _client = Ext.id();
	var _fund = Ext.id();
	var _batch = Ext.id();
	var _date = Ext.id();

	var info = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : false,
		url : 'services/SecuritiesRests.html',
		root : 'info',
		fields : [ 'check_flag', 'id', 'security_code', 'rest_date', 'client',
				'fund', 'quantity', 'price', 'currency', 'batch', 'lock_rate',
				'price_usd', 'quantity_flag', 'nkd', 'nkd_usd', 'yield',
				'date_insert' ],
		listeners : App.ui.listenersJsonStore()
	});

	function reload() {
		info.reload({
			params : {
				securityId : Ext.getCmp(_security).getValue(),
				clientId : Ext.getCmp(_client).getValue(),
				batch : Ext.getCmp(_batch).getValue(),
				fundId : Ext.getCmp(_fund).getValue(),
				date : App.util.Format.dateYMD(Ext.getCmp(_date).getValue()),
			}
		});
	}

	function changeCheckFlag(grid, rowIndex, columnIndex, e) {
		var fieldName = grid.getColumnModel().getDataIndex(columnIndex);

		if ('check_flag' != fieldName) {
			return;
		}

		var r = grid.getStore().getAt(rowIndex);
		var val = r.get(fieldName) ? 0 : 1;

		Ext.Ajax.request({
			url : 'services/SecuritiesRests-set.html',
			params : {
				id : r.data.id,
				checkFlag : val
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Изменение данных.',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					r.set(fieldName, val);
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

	var filter = new Ext.Panel({
		layout : 'hbox',
		padding : 5,
		autoHeight : true,

		items : [ {
			xtype : 'label',
			style : 'font-weight: bold;',
			margins : '2 5 0 0',
			text : 'Инструмент:'
		}, {
			id : _security,
			xtype : 'combo',
			width : 150,
			valueField : 'id',
			displayField : 'name',
			store : new Ext.data.JsonStore({
				autoDestroy : true,
				url : 'utils/equities-cb.html',
				root : 'info',
				fields : [ 'id', 'name' ],
				sortInfo : {
					field : 'name'
				}
			}),
			loadingText : 'Поиск...',
			minChars : 2,
			triggerAction : 'all'
		}, {
			xtype : 'button',
			text : 'Х',
			margins : '0 5',
			width : 25,
			handler : function() {
				Ext.getCmp(_security).clearValue();
			}
		}, {
			xtype : 'label',
			style : 'font-weight: bold;',
			margins : '2 5 0 15',
			text : 'Клиент:'
		}, {
			id : _client,
			xtype : 'combo',
			width : 70,
			valueField : 'id',
			displayField : 'name',
			store : new Ext.data.JsonStore({
				autoDestroy : true,
				url : 'dictionary/clients/clients-cb.html',
				root : 'info',
				fields : [ 'id', 'name' ],
				sortInfo : {
					field : 'name'
				}
			}),
			loadingText : 'Поиск...',
			triggerAction : 'all'
		}, {
			xtype : 'button',
			text : 'Х',
			margins : '0 5',
			width : 25,
			handler : function() {
				Ext.getCmp(_client).clearValue();
			}
		}, {
			xtype : 'label',
			style : 'font-weight: bold;',
			margins : '2 5 0 15',
			text : 'Фонд:'
		}, {
			id : _fund,
			xtype : 'combo',
			width : 100,
			valueField : 'id',
			displayField : 'name',
			store : new Ext.data.JsonStore({
				autoDestroy : true,
				url : 'dictionary/funds/funds-cb.html',
				root : 'info',
				fields : [ 'id', 'name' ],
				sortInfo : {
					field : 'name'
				}
			}),
			loadingText : 'Поиск...',
			triggerAction : 'all'
		}, {
			xtype : 'button',
			text : 'Х',
			margins : '0 5',
			width : 25,
			handler : function() {
				Ext.getCmp(_fund).clearValue();
			}
		}, {
			xtype : 'label',
			style : 'font-weight: bold;',
			margins : '2 5 0 15',
			text : 'Партия:'
		}, {
			id : _batch,
			xtype : 'numberfield',
			allowDecimals : false
		}, {
			xtype : 'button',
			text : 'Х',
			margins : '0 5',
			width : 25,
			handler : function() {
				var c = Ext.getCmp(_batch);
				c.setValue(c.originalValue);
			}
		}, {
			xtype : 'label',
			style : 'font-weight: bold;',
			margins : '2 5 0 15',
			text : 'Дата:'
		}, {
			id : _date,
			xtype : 'datefield',
			format : 'd.m.Y'
		}, {
			xtype : 'button',
			text : 'Х',
			margins : '0 5',
			width : 25,
			handler : function() {
				var c = Ext.getCmp(_date);
				c.setValue(c.originalValue);
			}
		} ],

		buttonAlign : 'left',
		buttons : [ {
			text : 'Показать',
			handler : reload
		} ]
	});

	return new Ext.grid.GridPanel({
		id : 'SecuritiesRests-component',
		title : 'Верификация остатков',
		frame : true,
		closable : true,
		enableHdMenu : false,

		tbar : filter,

		store : info,
		columns : [ new Ext.grid.RowNumberer({
			width : 30
		}), {
			header : 'check_flag',
			dataIndex : 'check_flag',
			renderer : App.util.Renderer.bool()
		}, {
			header : 'security_code',
			dataIndex : 'security_code'
		}, {
			header : 'rest_date',
			dataIndex : 'rest_date',
			renderer : App.util.Renderer.date()
		}, {
			header : 'client',
			dataIndex : 'client'
		}, {
			header : 'fund',
			dataIndex : 'fund'
		}, {
			header : 'quantity',
			dataIndex : 'quantity',
			align : 'right',
			renderer : App.util.Renderer.number()
		}, {
			header : 'price',
			dataIndex : 'price',
			align : 'right',
			renderer : App.util.Renderer.number(6)
		}, {
			header : 'currency',
			dataIndex : 'currency'
		}, {
			header : 'batch',
			dataIndex : 'batch'
		}, {
			header : 'lock_rate',
			dataIndex : 'lock_rate'
		}, {
			header : 'price_usd',
			dataIndex : 'price_usd',
			align : 'right',
			renderer : App.util.Renderer.number(6)
		}, {
			header : 'quantity_flag',
			dataIndex : 'quantity_flag'
		}, {
			header : 'nkd',
			dataIndex : 'nkd',
			align : 'right',
			renderer : App.util.Renderer.number(6)
		}, {
			header : 'nkd_usd',
			dataIndex : 'nkd_usd',
			align : 'right',
			renderer : App.util.Renderer.number(6)
		}, {
			header : 'yield',
			dataIndex : 'yield',
			align : 'right',
			renderer : App.util.Renderer.number(6)
		}, {
			header : 'date_insert',
			dataIndex : 'date_insert',
			renderer : App.util.Renderer.datetime()
		} ],
		viewConfig : {
			forceFit : true,
			emptyText : 'Записи не найдены'
		},
		listeners : {
			celldblclick : changeCheckFlag
		},
		refresh : function() {
			reload();
		}
	});
})();
