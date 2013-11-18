/**
 * Текущий финрез
 */
(function() {

	var _dateBegin = Ext.id();
	var _dateEnd = Ext.id();
	var _client = Ext.id();
	var _fund = Ext.id();
	var _security = Ext.id();

	var info = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : false,
		url : 'rest/ViewDetailedFinrez.do',
		// root : 'info',
		fields : [ 'Client', 'Portfolio', 'Security_id', 'Security_code',
				'Batch', 'Deals_realised_profit_id', 'Deal_id', 'TradeNum',
				'Trade_date', 'Operation', 'Realised_profit',
				'Initial_quantity', 'Deal_quantity', 'Avg_price',
				'Avg_price_usd', 'Deal_price', 'Currency', 'Funding',
				'Date_insert', 'Account' ],
		listeners : App.ui.listenersJsonStore()
	});

	function reload() {
		var _db = App.util.Format.dateYMD(Ext.getCmp(_dateBegin).getValue());
		var _de = App.util.Format.dateYMD(Ext.getCmp(_dateEnd).getValue());
		var _c = Ext.getCmp(_client).getValue();
		var _f = Ext.getCmp(_fund).getValue();
		var _s = App.Combo.getValueId(Ext.getCmp(_security));

		info.reload({
			params : {
				dateBegin : _db,
				dateEnd : _de,
				client : _c,
				fund : _f,
				security : _s
			}
		});
	}

	function exportExcel() {
		var _db = App.util.Format.dateYMD(Ext.getCmp(_dateBegin).getValue());
		var _de = App.util.Format.dateYMD(Ext.getCmp(_dateEnd).getValue());
		var _c = Ext.getCmp(_client).getValue();
		var _f = Ext.getCmp(_fund).getValue();
		var _s = App.Combo.getValueId(Ext.getCmp(_security));

		var url = 'rest/ViewDetailedFinrez/Export.do?dateBegin=' + _db
				+ '&dateEnd=' + _de + '&client=' + _c + '&fund=' + _f
				+ '&security=' + _s;

		window.open(url);
	}

	var filter = new Ext.Panel({
		border : false,
		autoHeight : true,
		layout : 'hbox',
		padding : 5,

		items : [ {
			xtype : 'label',
			style : 'font-weight: bold;',
			margins : '2 5 0 5',
			text : 'Дата c:'
		}, {
			id : _dateBegin,
			xtype : 'datefield',
			margins : '2 5 0 5',
			format : 'd.m.Y'
		}, {
			xtype : 'label',
			style : 'font-weight: bold;',
			margins : '2 5 0 5',
			text : 'по:'
		}, {
			id : _dateEnd,
			xtype : 'datefield',
			margins : '2 5 0 5',
			format : 'd.m.Y'
		}, {
			xtype : 'label',
			style : 'font-weight: bold;',
			margins : '2 5 0 25',
			text : 'Клиент:'
		}, {
			id : _client,
			xtype : 'combo',
			width : 100,
			displayField : 'name',
			valueField : 'id',
			store : new Ext.data.JsonStore({
				autoDestroy : true,
				url : 'rest/ViewDetailedFinrez/Clients.do',
				// root : 'info',
				fields : [ 'id', 'name' ],
				sortInfo : {
					field : 'name'
				}
			}),
			loadingText : 'Поиск...',
			minChars : 2,
			editable : false,
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
			margins : '2 5 0 25',
			text : 'Фонд:'
		}, {
			id : _fund,
			xtype : 'combo',
			width : 100,
			displayField : 'name',
			valueField : 'id',
			store : new Ext.data.JsonStore({
				autoDestroy : true,
				url : 'rest/ViewDetailedFinrez/Funds.do',
				// root : 'info',
				fields : [ 'id', 'name' ],
				sortInfo : {
					field : 'name'
				}
			}),
			loadingText : 'Поиск...',
			minChars : 2,
			editable : false,
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
			margins : '2 5 0 25',
			text : 'Инструмент:'
		}, {
			id : _security,
			xtype : 'combo',
			width : 150,
			displayField : 'name',
			valueField : 'name',
			store : new Ext.data.JsonStore({
				autoDestroy : true,
				url : 'rest/ViewDetailedFinrez/Tickers.do',
				// root : 'info',
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
		} ],

		buttonAlign : 'left',
		buttons : [ {
			text : 'Выбрать',
			width : 100,
			handler : reload
		}, {
			text : 'Выгрузить в Excel',
			width : 100,
			handler : exportExcel
		} ]
	});

	return new Ext.grid.GridPanel({
		id : 'ViewDetailedFinrez-component',
		title : 'Текущий финрез',
		frame : true,
		closable : true,
		enableHdMenu : false,

		tbar : filter,

		store : info,
		columns : [ {
			header : 'Client',
			dataIndex : 'Client'
		}, {
			header : 'Portfolio',
			dataIndex : 'Portfolio'
		}, {
			header : 'Security_id',
			dataIndex : 'Security_id'
		}, {
			header : 'Security_code',
			dataIndex : 'Security_code'
		}, {
			header : 'Batch',
			dataIndex : 'Batch'
		}, {
			header : 'Deals_realised_profit_id',
			dataIndex : 'Deals_realised_profit_id'
		}, {
			header : 'Deal_id',
			dataIndex : 'Deal_id'
		}, {
			header : 'TradeNum',
			dataIndex : 'TradeNum'
		}, {
			header : 'Trade_date',
			dataIndex : 'Trade_date',
			align : 'center',
			renderer : App.util.Renderer.date()
		}, {
			header : 'Operation',
			dataIndex : 'Operation'
		}, {
			header : 'Realised_profit',
			dataIndex : 'Realised_profit'
		}, {
			header : 'Initial_quantity',
			dataIndex : 'Initial_quantity'
		}, {
			header : 'Deal_quantity',
			dataIndex : 'Deal_quantity'
		}, {
			header : 'Avg_price',
			dataIndex : 'Avg_price',
			align : 'right',
			renderer : App.util.Renderer.number()
		}, {
			header : 'Avg_price_usd',
			dataIndex : 'Avg_price_usd'
		}, {
			header : 'Deal_price',
			dataIndex : 'Deal_price'
		}, {
			header : 'Currency',
			dataIndex : 'Currency'
		}, {
			header : 'Funding',
			dataIndex : 'Funding'
		}, {
			header : 'Date_insert',
			dataIndex : 'Date_insert',
			align : 'center',
			renderer : App.util.Renderer.datetime()

		}, {
			header : 'Account',
			dataIndex : 'Account'
		} ],

		viewConfig : {
			forceFit : true,
			emptyText : 'Записи не найдены'
		}
	});
})();
