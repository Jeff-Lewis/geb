/**
 * Текущий финрез
 */
(function() {

	var _dateBegin = Ext.id();
	var _dateEnd = Ext.id();
	var _client = Ext.id();
	var _fund = Ext.id();
	var _security = Ext.id();
	var _initiator = Ext.id();

	var info = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : false,
		url : 'rest/ViewDetailedFinrez.do',
		// root : 'info',
		fields : [ 'client', 'portfolio', 'security_id', 'security_code',
				'batch', 'deals_realised_profit_id', 'deal_id', 'tradeNum',
				'trade_date', 'operation', 'realised_profit',
				'initial_quantity', 'deal_quantity', 'avg_price',
				'avg_price_usd', 'deal_price', 'currency', 'funding',
				'date_insert', 'account', 'initiator' ],
		listeners : App.ui.listenersJsonStore()
	});

	function getParams() {
		var _db = Ext.getCmp(_dateBegin).getValue();
		var _de = Ext.getCmp(_dateEnd).getValue();

		var params = {
		    dateBegin : App.util.Format.dateYMD(_db),
		    dateEnd : App.util.Format.dateYMD(_de),
		    client : Ext.getCmp(_client).getValue(),
		    fund : Ext.getCmp(_fund).getValue(),
		    security : Ext.getCmp(_security).getValue(),
		    initiator : Ext.getCmp(_initiator).getValue()
		};

		return params;
    }

	function reload() {
		info.reload({
			params : getParams()
		});
	}

	function exportExcel() {
		window.open('rest/ViewDetailedFinrez/Export.do?' + Ext.urlEncode(getParams()));
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
			valueField : 'id',
			displayField : 'name',
			store : new Ext.data.JsonStore({
				autoDestroy : true,
				url : 'rest/ViewDetailedFinrez/Securities.do',
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
		}, {
			xtype : 'label',
			style : 'font-weight: bold;',
			margins : '2 5 0 5',
			text : 'Инициатор:'
		}, {
			id : _initiator,
			xtype : 'combo',
			width : 100,
			fieldLabel : 'Инициатор',
			valueField : 'id',
			displayField : 'name',
			store : new Ext.data.JsonStore({
				autoDestroy : true,
				url : 'rest/ViewDetailedFinrez/Initiator.do',
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
				Ext.getCmp(_initiator).clearValue();
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
			dataIndex : 'client'
		}, {
			header : 'Portfolio',
			dataIndex : 'portfolio'
		}, {
			header : 'Security_id',
			dataIndex : 'security_id'
		}, {
			header : 'Security_code',
			dataIndex : 'security_code'
		}, {
			header : 'Batch',
			dataIndex : 'batch'
		}, {
			header : 'Deals_realised_profit_id',
			dataIndex : 'deals_realised_profit_id'
		}, {
			header : 'Deal_id',
			dataIndex : 'deal_id'
		}, {
			header : 'TradeNum',
			dataIndex : 'tradeNum'
		}, {
			header : 'Trade_date',
			dataIndex : 'trade_date',
			align : 'center',
			renderer : App.util.Renderer.date()
		}, {
			header : 'Operation',
			dataIndex : 'operation'
		}, {
			header : 'Realised_profit',
			dataIndex : 'realised_profit'
		}, {
			header : 'Initial_quantity',
			dataIndex : 'initial_quantity'
		}, {
			header : 'Deal_quantity',
			dataIndex : 'deal_quantity'
		}, {
			header : 'Avg_price',
			dataIndex : 'avg_price',
			align : 'right',
			renderer : App.util.Renderer.number()
		}, {
			header : 'Avg_price_usd',
			dataIndex : 'avg_price_usd'
		}, {
			header : 'Deal_price',
			dataIndex : 'deal_price'
		}, {
			header : 'Currency',
			dataIndex : 'currency'
		}, {
			header : 'Funding',
			dataIndex : 'funding',
			width : 30,
			align : 'center',
			renderer : App.util.Renderer.bool()
		}, {
			header : 'Date_insert',
			dataIndex : 'date_insert',
			align : 'center',
			renderer : App.util.Renderer.datetime()

		}, {
			header : 'Account',
			dataIndex : 'account'
		}, {
			header : 'Initiator',
			dataIndex : 'initiator'
		} ],

		viewConfig : {
			forceFit : true,
			emptyText : 'Записи не найдены'
		}
	});
})();
