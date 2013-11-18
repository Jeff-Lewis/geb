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
		fields : [ {
			name : 'selected',
			type : 'boolean'
		}, 'id', 'Batch', 'TradeNum', 'SecShortName', 'Operation', 'Quantity',
				'Price', 'PriceNKD', 'Currency', 'TradeDate', 'SettleDate',
				'TradeSystem', 'Broker', 'Account', 'Client', 'Portfolio', {
					name : 'Funding',
					type : 'boolean'
				} ],
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

	var _checked;

	function selectCheck(This, checked) {
		Ext.Msg.wait(checked ? 'Установка меток' : 'Снятие меток',
				Ext.form.BasicForm.prototype.waitTitle);
		_checked = checked;
		setTimeout(function() {
			var checked = _checked;
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
			displayField : 'name',
			store : new Ext.data.JsonStore({
				autoDestroy : true,
				url : 'rest/ViewDeals/Tickers.do',
				// root : 'info',
				fields : [ 'name' ],
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

	function renderFunding(value, meta, record) {
		if (value) {
			meta.attr = 'style="background: url(img/vwicn082.gif) no-repeat center transparent;"';
		} else {
			meta.attr = 'style="background: url(img/vwicn081.gif) no-repeat center transparent;"';
		}
	}

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
			width : 16,
			sortable : false
		}, new Ext.grid.RowNumberer({
			header : '#',
			sortable : false,
			width : 35
		}), {
			header : 'Id',
			dataIndex : 'id',
			sortable : true,
			width : 35,
			editable : false
		}, {
			header : 'Batch',
			dataIndex : 'Batch',
			sortable : true,
			width : 35,
			editor : editorBatch
		}, {
			header : 'TradeNum',
			dataIndex : 'TradeNum',
			sortable : true,
			width : 35,
			editor : editorTradeNum
		}, {
			header : 'SecShortName',
			dataIndex : 'SecShortName',
			sortable : true,
			width : 30,
			editable : false
		}, {
			header : 'Operation',
			dataIndex : 'Operation',
			sortable : true,
			width : 30,
			editor : editorOperation
		}, {
			header : 'Quantity',
			dataIndex : 'Quantity',
			sortable : true,
			width : 50,
			align : 'right',
			renderer : App.util.Renderer.number(0),
			editor : editorQuantity
		}, {
			header : 'Price',
			dataIndex : 'Price',
			sortable : true,
			align : 'right',
			renderer : App.util.Renderer.number(6),
			width : 60,
			editor : editorPrice
		}, {
			header : 'PriceNKD',
			dataIndex : 'PriceNKD',
			sortable : true,
			align : 'right',
			renderer : App.util.Renderer.number(6),
			width : 60,
			editable : false
		// editor : editorPrice
		}, {
			header : 'Currency',
			dataIndex : 'Currency',
			sortable : true,
			width : 30,
			editable : false
		}, {
			header : 'TradeDate',
			dataIndex : 'TradeDate',
			// align : 'center',
			renderer : App.util.Renderer.date(),
			width : 50,
			sortable : true,
			editable : false
		}, {
			header : 'SettleDate',
			dataIndex : 'SettleDate',
			// align : 'center',
			renderer : App.util.Renderer.date(),
			width : 50,
			sortable : true,
			editable : false
		}, {
			header : 'TradeSystem',
			dataIndex : 'TradeSystem',
			sortable : true,
			width : 50,
			editor : editorTradeSystem
		}, {
			header : 'Broker',
			dataIndex : 'Broker',
			sortable : true,
			width : 30,
			editable : false
		}, {
			header : 'Account',
			dataIndex : 'Account',
			sortable : true,
			width : 30,
			editor : editorAccount
		}, {
			header : 'Client',
			dataIndex : 'Client',
			sortable : true,
			width : 30
		}, {
			header : 'Portfolio',
			dataIndex : 'Portfolio',
			sortable : true,
			width : 30,
			editor : editorPortfolio
		}, {
			header : 'Funding',
			dataIndex : 'Funding',
			sortable : true,
			width : 30,
			align : 'center',
			renderer : renderFunding,
			listeners : {
				click : clickFunding
			}
		} ],
		viewConfig : {
			forceFit : true,
			emptyText : 'Записи не найдены'
		},

		listeners : {
			afteredit : function(e) {
				updateField(e.record.data.id, e.field, e.value);
			}
		}
	});
})();
