/**
 * Дивиденды
 */
(function() {

	var _client = Ext.id();
	var _broker = Ext.id();
	var _security = Ext.id();
	var _dateBegin = Ext.id();
	var _dateEnd = Ext.id();

	function convertDate(v, rec) {
		v = Ext.util.Format.substr(v, 0, 10);
		v = Date.parseDate(v, 'Y-m-d');
	    return v;
    }

	var info = new Ext.data.JsonStore({
	    autoDestroy : true,
	    autoLoad : false,
	    autoSave : false,
	    url : 'rest/Dividends.do',
	    // root : 'info',
	    fields : [ 'id_sec', 'security_code', 'short_name', 'client', 'fund', 'broker', 'account',
	            'currency', {
	                name : 'record_date',
	                convert : convertDate
	            }, 'quantity', 'dividend_per_share', 'receive_date', 'status', 'estimate',
	            'real_dividend_per_share', 'real_dividends', 'extra_costs', 'tax_value', 'country' ],
	    sortInfo : {
		    field : 'security_code'
	    },
	    listeners : App.ui.listenersJsonStore()
	});

	var sm = new Ext.grid.RowSelectionModel({
		singleSelect : true
	});

	function reload() {
		var db = Ext.getCmp(_dateBegin).getValue();
		var de = Ext.getCmp(_dateEnd).getValue();
		info.reload({
			params : {
			    clientId : Ext.getCmp(_client).getValue(),
			    brokerId : Ext.getCmp(_broker).getValue(),
			    securityId : Ext.getCmp(_security).getValue(),
			    // accountId : null,
			    dateBegin : App.util.Format.dateYMD(db),
			    dateEnd : App.util.Format.dateYMD(de)
			}
		});
	}

	function exportExcel() {
		var db = Ext.getCmp(_dateBegin).getValue();
		var de = Ext.getCmp(_dateEnd).getValue();
		var p = Ext.urlEncode({
		    clientId : Ext.getCmp(_client).getValue(),
		    brokerId : Ext.getCmp(_broker).getValue(),
		    securityId : Ext.getCmp(_security).getValue(),
		    // accountId : null,
		    dateBegin : App.util.Format.dateYMD(db),
		    dateEnd : App.util.Format.dateYMD(de)
		});
		window.open('rest/Dividends/ExportXls.do?' + p);
	}

	function add() {
		showModal('dictionary/DividendsAdd');
	}

	function del() {
		if (sm.getCount() == 0) {
			App.ui.message('Необходимо выбрать запись для удаления!');
			return;
		}

		App.ui.confirm('Удалить запись для "'
				+ sm.getSelected().data.short_name + '"?', cbDel);
	}
	function cbDel() {
		var id = sm.getSelected().data.id_sec;
		Ext.Ajax.request({
			method : 'DELETE',
			url : 'rest/Dividends/' + id + '.do',
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Выполняется запрос',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					reload();
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	function change() {
		if (sm.getCount() == 0) {
			App.ui.message('Необходимо выбрать запись для изменения!');
			return;
		}

		var id = sm.getSelected().data.id_sec;
		showModal('dictionary/DividendsEdit', 'rest/Dividends/' + id
				+ '.do');
	}

	var filter = new Ext.Panel({
		layout : 'hbox',
		padding : 5,
		autoHeight : true,

		items : [ {
			xtype : 'label',
			style : 'font-weight: bold;',
			margins : '2 5 0 0',
			text : 'Клиент:'
		}, {
			id : _client,
			xtype : 'combo',
			width : 70,
			valueField : 'id',
			displayField : 'name',
			store : new Ext.data.JsonStore({
				autoDestroy : true,
				url : 'rest/Dividends/Clients.do',
				// root : 'info',
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
			margins : '2 5 0 25',
			text : 'Брокер:'
		}, {
			id : _broker,
			xtype : 'combo',
			width : 120,
			valueField : 'id',
			displayField : 'name',
			store : new Ext.data.JsonStore({
				autoDestroy : true,
				url : 'rest/Dividends/Brokers.do',
				// root : 'info',
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
				Ext.getCmp(_broker).clearValue();
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
				url : 'rest/Dividends/Equities.do',
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
			margins : '2 5 0 25',
			text : 'Дата с:'
		}, {
			id : _dateBegin,
			xtype : 'datefield',
			format : 'd.m.Y'
		}, {
			xtype : 'label',
			style : 'font-weight: bold;',
			margins : '2 5 0 5',
			text : 'по:'
		}, {
			id : _dateEnd,
			xtype : 'datefield',
			format : 'd.m.Y'
		} ],

		buttonAlign : 'left',
		buttons : [ {
			text : 'Показать',
			handler : reload
		}, ' ', {
			text : 'Добавить',
			handler : add
		}, {
			text : 'Удалить',
			handler : del
		}, {
			text : 'Изменить статус',
			handler : change
		}, ' ', {
			text : 'Выгрузить в Excel',
			handler : exportExcel
		} ]
	});

	function updateField(id, column, newValue) {
		if ('record_date' == column) {
			newValue = newValue.format('d.m.Y');
		}

		Ext.Ajax.request({
			url : 'rest/Dividends/' + id + '.do',
			params : {
				field : column,
				value : newValue
			},
			timeout : 60000,
			waitMsg : 'Выполняется изменение сделки',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					reload();
				}
			},
			failure : function(response, opts) {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	var editorAccount = new Ext.form.ComboBox({
		displayField : 'name',
		valueField : 'name',
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'rest/Dividends/BrokerAccounts.do',
			// root : 'info',
			fields : [ 'id', 'name' ],
			sortInfo : {
				field : 'name'
			}
		}),
		loadingText : 'Поиск...',
		minChars : 2,
		triggerAction : 'all',
		emptyText : 'Выберите',
		allowBlank : false
	});

	var editorCurrency = new Ext.form.ComboBox({
		displayField : 'name',
		valueField : 'name',
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'rest/Dividends/Currency.do',
			// root : 'info',
			fields : [ 'id', 'name' ],
			sortInfo : {
				field : 'name'
			}
		}),
		loadingText : 'Поиск...',
		minChars : 2,
		triggerAction : 'all',
		emptyText : 'Выберите',
		allowBlank : false
	});

	var editorRecordDate = new Ext.form.DateField({
		allowBlank : false,
		format : 'd.m.Y'
	});

	var editorQuantity = new Ext.form.NumberField({
		allowBlank : false,
		allowDecimals : false
	});

	var editorDividend = new Ext.form.NumberField({
		allowBlank : false,
		decimalPrecision : 6
	});

	return new Ext.grid.EditorGridPanel({
		id : 'Dividends-component',
		title : 'Дивиденды',
		closable : true,
		frame : true,
		enableHdMenu : false,

		tbar : filter,

		store : info,
		selModel : sm,
		columns : [ new Ext.grid.RowNumberer(), {
			header : 'security_code',
			dataIndex : 'security_code',
			sortable : true
		}, {
			header : 'short_name',
			dataIndex : 'short_name',
			sortable : true
		}, {
			header : 'client',
			dataIndex : 'client',
			sortable : true
		}, {
			header : 'fund',
			dataIndex : 'fund',
			sortable : true
		}, {
			header : 'broker',
			dataIndex : 'broker',
			sortable : true
		}, {
			header : 'account',
			dataIndex : 'account',
			sortable : true,
			editor : editorAccount
		}, {
			header : 'currency',
			dataIndex : 'currency',
			sortable : true,
			editor : editorCurrency
		}, {
			header : 'record_date',
			dataIndex : 'record_date',
			sortable : true,
			align : 'center',
			xtype : 'datecolumn',
			format : 'd.m.Y',
			editor : editorRecordDate
		}, {
			header : 'quantity',
			dataIndex : 'quantity',
			sortable : true,
			align : 'right',
			renderer : App.util.Renderer.number(0),
			editor : editorQuantity
		}, {
			header : 'dividend_per_share',
			dataIndex : 'dividend_per_share',
			sortable : true,
			align : 'right',
			renderer : App.util.Renderer.number(),
			editor : editorDividend
		}, {
			header : 'receive_date',
			dataIndex : 'receive_date',
			sortable : true,
			align : 'center',
			renderer : App.util.Renderer.date()
		}, {
			header : 'real_dividend_per_share',
			dataIndex : 'real_dividend_per_share',
			sortable : true,
			align : 'right',
			renderer : App.util.Renderer.number()
		}, {
			header : 'status',
			dataIndex : 'status',
			sortable : true
		}, {
			header : 'estimate',
			dataIndex : 'estimate',
			sortable : true,
			align : 'right',
			renderer : App.util.Renderer.number()
		}, {
			header : 'real_dividends',
			dataIndex : 'real_dividends',
			sortable : true,
			align : 'right',
			renderer : App.util.Renderer.number(),
		}, {
			header : 'extra_costs',
			dataIndex : 'extra_costs',
			sortable : true,
			align : 'right',
			renderer : App.util.Renderer.number(),
			editor : editorDividend
		}, {
			header : 'tax_value',
			dataIndex : 'tax_value',
			sortable : true,
			align : 'right',
			renderer : App.util.Renderer.number()
		}, {
			header : 'country',
			dataIndex : 'country',
			sortable : true
		} ],
		viewConfig : {
			forceFit : true,
			emptyText : 'Записи не найдены'
		},

		listeners : {
			afteredit : function(e) {
				updateField(e.record.data.id_sec, e.field, e.value);
			}
		},

		refresh : function() {
			reload();
		}
	});
})();
