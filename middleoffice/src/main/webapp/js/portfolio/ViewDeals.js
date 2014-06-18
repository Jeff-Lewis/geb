/**
 * Список сделок
 */
(function() {

	var _date1 = Ext.id();
	var _date2 = Ext.id();
	var _ticker = Ext.id();

	var deals = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : false,
		url : 'rest/ViewDeals.do',
		// root : 'info',
		fields : [ 'selected', 'id', 'batch', 'tradeNum', 'secShortName',
				'operation', 'quantity', 'price', 'priceNKD', 'currency',
				'tradeDate', 'settleDate', 'tradeSystem', 'broker', 'account',
				'client', 'portfolio', 'funding', 'initiator' ],
		listeners : App.ui.listenersJsonStore()
	});

	function dealsLoad() {
		var fd = Ext.getCmp(_date1).getValue();
		var td = Ext.getCmp(_date2).getValue();

		if ((fd == '') || (td == '')) {
			App.ui.error('Необходимо выбрать дату!');
			return;
		}

		deals.reload({
			params : {
				dateBegin : App.util.Format.dateYMD(fd),
				dateEnd : App.util.Format.dateYMD(td),
				ticker : Ext.getCmp(_ticker).getValue()
			}
		});
	}

	function dealsDelete() {
		var ids = [];

		deals.each(function(item) {
			if (item.data.selected)
				ids.push(item.data.id);
			return true;
		});

		if (ids.length == 0) {
			App.ui.message('Для удаления выберите позиции.');
			return;
		}

		Ext.Ajax.request({
			url : 'rest/ViewDeals/Del.do',
			params : {
				deals : ids
			},
			timeout : 60000,
			waitMsg : 'Выполняется удаление',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					dealsLoad();
				} else if (answer.code == 'login') {
					App.ui.sessionExpired();
				} else {
					App.ui.error(answer.message);
				}
			},
			failure : function(response, opts) {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	var sm = new Ext.grid.CellSelectionModel();

	function updateField(id, column, newValue) {
		var ids = [];

		deals.each(function(item) {
			if (item.data.selected)
				ids.push(item.data.id);
			return true;
		});

		if (ids.length == 0) {
			ids.push(id);
		}

		Ext.Ajax.request({
			url : 'rest/ViewDeals/Set.do',
			params : {
				deals : ids,
				field : column,
				value : newValue
			},
			timeout : 60000,
			waitMsg : 'Выполняется изменение сделки',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					dealsLoad();
				} else if (answer.code == 'login') {
					App.ui.sessionExpired();
				} else {
					App.ui.error(answer.message);
				}
			},
			failure : function(response, opts) {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	var editorBatch = new Ext.form.NumberField({
		allowBlank : false,
		allowDecimals : false
	});

	var editorTradeNum = new Ext.form.TextField({
		allowBlank : false
	});

	var editorOperation = new Ext.form.ComboBox({
		store : [ 'buy', 'sell' ],
		emptyText : 'Выберите',
		displayField : 'name',
		loadingText : 'Поиск...',
		triggerAction : 'all',
		editable : false
	});

	var editorQuantity = new Ext.form.NumberField({
		allowBlank : false,
		allowDecimals : false
	});

	var editorPrice = new Ext.form.NumberField({
		allowBlank : false,
		allowNegative : false,
		decimalPrecision : 6
	});

	var editorInitiator = new Ext.form.ComboBox({
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'rest/ViewDeals/Initiator.do',
			// root : 'info',
			fields : [ 'name' ],
			sortInfo : {
				field : 'name'
			}
		}),
		emptyText : 'Выберите',
		displayField : 'name',
		loadingText : 'Поиск...',
		triggerAction : 'all',
		editable : false
	});

	var editorTradeSystem = new Ext.form.ComboBox({
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'rest/ViewDeals/TradeSystems.do',
			// root : 'info',
			fields : [ 'name' ],
			sortInfo : {
				field : 'name'
			}
		}),
		emptyText : 'Выберите',
		displayField : 'name',
		loadingText : 'Поиск...',
		triggerAction : 'all',
		editable : false
	});

	var editorAccount = new Ext.form.ComboBox({
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'rest/ViewDeals/Accounts.do',
			// root : 'info',
			fields : [ 'name' ],
			sortInfo : {
				field : 'name'
			}
		}),
		emptyText : 'Выберите',
		displayField : 'name',
		loadingText : 'Поиск...',
		triggerAction : 'all',
		editable : false
	});

	var editorPortfolio = new Ext.form.ComboBox({
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'rest/ViewDeals/Portfolio.do',
			// root : 'info',
			fields : [ 'name' ],
			sortInfo : {
				field : 'name'
			}
		}),
		emptyText : 'Выберите',
		displayField : 'name',
		loadingText : 'Поиск...',
		triggerAction : 'all',
		editable : false
	});

	function dealsExport() {
		var fd = Ext.getCmp(_date1).getValue();
		var td = Ext.getCmp(_date2).getValue();

		if ((fd == '') || (td == '')) {
			App.ui.error('Необходимо выбрать дату!');
			return;
		}

		window.open('rest/ViewDeals/Export.do?' + 'dateBegin='
				+ App.util.Format.dateYMD(fd) + '&dateEnd='
				+ App.util.Format.dateYMD(td) + '&ticker='
				+ Ext.getCmp(_ticker).getValue());
	}

	function selectCheck(This, checked) {
		Ext.Msg.wait(checked ? 'Установка меток' : 'Снятие меток',
				Ext.form.BasicForm.prototype.waitTitle);
		setTimeout(function() {
			deals.each(function(item) {
				item.data.selected = checked;
				// Ext.Msg.updateProgress();
				return true;
			});
			Ext.Msg.hide();
			Ext.getCmp('ViewDeals-component').getView().refresh();
		}, 0);
	}

	var filter = new Ext.Panel({
		layout : 'hbox',
		padding : 5,
		autoHeight : true,

		items : [ {
			xtype : 'label',
			style : 'font-weight: bold;',
			margins : '2 5 0 5',
			text : 'Дата с:'
		}, {
			id : _date1,
			xtype : 'datefield',
			allowBlank : false,
			format : 'd.m.Y'
		}, {
			xtype : 'label',
			style : 'font-weight: bold;',
			margins : '2 5 0 5',
			text : 'по:'
		}, {
			id : _date2,
			xtype : 'datefield',
			allowBlank : false,
			format : 'd.m.Y'
		}, {
			xtype : 'label',
			style : 'font-weight: bold;',
			margins : '2 5 0 25',
			text : 'Выбор тикера:'
		}, {
			id : _ticker,
			xtype : 'combo',
			width : 150,
			fieldLabel : 'Тикер Блумберг',
			valueField : 'id',
			displayField : 'name',
			store : new Ext.data.JsonStore({
				autoDestroy : true,
				url : 'rest/ViewDeals/Tickers.do',
				// root : 'info',
				fields : [ 'id', 'name' ],
				sortInfo : {
					field : 'name'
				}
			}),
			loadingText : 'Поиск...',
			triggerAction : 'all',
			minChars : 2,
			typeAhead : false
		}, {
			xtype : 'button',
			text : 'Х',
			margins : '0 5',
			width : 25,
			handler : function() {
				Ext.getCmp(_ticker).clearValue();
			}
		} ],

		buttonAlign : 'left',
		buttons : [ {
			xtype : 'label',
			width : 12,
			text : ''
		}, {
			xtype : 'checkbox',
			listeners : {
				check : selectCheck
			}
		}, {
			text : 'Удалить выбранные',
			handler : function() {
				App.ui.confirm('Удалить помеченные сделки?', dealsDelete);
			}
		}, {
			xtype : 'label',
			width : 35,
			text : ''
		}, {
			text : 'Показать сделки',
			handler : dealsLoad
		}, {
			text : 'Выгрузить в Excel',
			handler : dealsExport
		} ]
	});

	function clickFunding(col, grid, rowIndex, e) {
		var record = grid.getStore().getAt(rowIndex);
		var v = !record.data.Funding;
		record.data.Funding = v;
		record.commit();
		updateField(record.data.id, 'Funding', (v ? 1 : 0));
	}

	return new Ext.grid.EditorGridPanel({
		id : 'ViewDeals-component',
		title : 'Список сделок',
		frame : true,
		closable : true,
		enableHdMenu : false,

		tbar : filter,

		store : deals,
		selModel : sm,
		clickToEdit : 1,
		columns : [ { /* header : 'X', */
			xtype : 'checkcolumn',
			dataIndex : 'selected',
			width : 20,
			sortable : false
		}, new Ext.grid.RowNumberer({
			header : '#',
			width : 30
		}), {
			header : 'Id',
			dataIndex : 'id',
			sortable : true,
			width : 50,
			editable : false
		}, {
			header : 'Batch',
			dataIndex : 'batch',
			sortable : true,
			width : 50,
			editor : editorBatch
		}, {
			header : 'TradeNum',
			dataIndex : 'tradeNum',
			sortable : true,
			width : 60,
			editor : editorTradeNum
		}, {
			header : 'SecShortName',
			dataIndex : 'secShortName',
			sortable : true,
			width : 150,
			editable : false
		}, {
			header : 'Operation',
			dataIndex : 'operation',
			sortable : true,
			width : 60,
			align : 'center',
			editor : editorOperation
		}, {
			header : 'Quantity',
			dataIndex : 'quantity',
			sortable : true,
			width : 65,
			align : 'right',
			renderer : App.util.Renderer.number(0),
			editor : editorQuantity
		}, {
			header : 'Price',
			dataIndex : 'price',
			sortable : true,
			align : 'right',
			renderer : App.util.Renderer.number(6),
			width : 100,
			editor : editorPrice
		}, {
			header : 'PriceNKD',
			dataIndex : 'priceNKD',
			sortable : true,
			align : 'right',
			renderer : App.util.Renderer.number(6),
			width : 100,
			editable : false
		}, {
			header : 'Currency',
			dataIndex : 'currency',
			sortable : true,
			width : 60,
			align : 'center',
			editable : false
		}, {
			header : 'TradeDate',
			dataIndex : 'tradeDate',
			sortable : true,
			align : 'center',
			renderer : App.util.Renderer.date(),
			width : 70,
			editable : false
		}, {
			header : 'SettleDate',
			dataIndex : 'settleDate',
			sortable : true,
			align : 'center',
			renderer : App.util.Renderer.date(),
			width : 70,
			editable : false
		}, {
			header : 'TradeSystem',
			dataIndex : 'tradeSystem',
			sortable : true,
			width : 75,
			editor : editorTradeSystem
		}, {
			header : 'Broker',
			dataIndex : 'broker',
			sortable : true,
			width : 70,
			editable : false
		}, {
			header : 'Account',
			dataIndex : 'account',
			sortable : true,
			width : 80,
			editor : editorAccount
		}, {
			header : 'Client',
			dataIndex : 'client',
			sortable : true,
			width : 50
		}, {
			header : 'Portfolio',
			dataIndex : 'portfolio',
			sortable : true,
			width : 60,
			editor : editorPortfolio
		}, {
			header : 'Funding',
			dataIndex : 'funding',
			sortable : true,
			width : 30,
			align : 'center',
			renderer : App.util.Renderer.bool(),
			listeners : {
				click : clickFunding
			}
		}, {
			header : 'Initiator',
			dataIndex : 'initiator',
			sortable : true,
			width : 130,
			editor : editorInitiator
		} ],
		viewConfig : {
			//forceFit : true,
			emptyText : 'Записи не найдены'
		},

		listeners : {
			afteredit : function(e) {
				updateField(e.record.data.id, e.field, e.value);
			}
		}
	});
})();
