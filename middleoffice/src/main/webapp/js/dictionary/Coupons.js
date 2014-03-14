/**
 * Купоны (погашение)
 */
(function() {

	var _client = Ext.id();
	var _broker = Ext.id();
	var _security = Ext.id();
	var _operations = Ext.id();
	var _dateBegin = Ext.id();
	var _dateEnd = Ext.id();

	var info = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : false,
		autoSave : false,
		url : 'rest/Coupons.do',
		//root : 'info',
		fields : [ 'id', 'security_code', 'short_name', 'client', 'fund',
				'broker', 'account', 'currency', 'record_date', 'quantity',
				'coupon_per_share', 'receive_date', 'real_coupon_per_share',
				'status', 'estimate', 'real_coupons', 'extra_costs',
				'tax_value', 'country', 'oper' ],
		sortInfo : {
			field : 'security_code'
		},
		listeners : App.ui.listenersJsonStore()
	});

	var sm = new Ext.grid.RowSelectionModel({
		singleSelect : true
	});

	function reload() {
		info.reload({
			params : {
				clientId : Ext.getCmp(_client).getValue(),
				brokerId : Ext.getCmp(_broker).getValue(),
				securityId : App.Combo.getValueId(Ext.getCmp(_security)),
				// accountId : null,
				operationId : App.Combo.getValueId(Ext.getCmp(_operations)),
				dateBegin : App.util.Format.dateYMD(Ext.getCmp(_dateBegin)
						.getValue()),
				dateEnd : App.util.Format.dateYMD(Ext.getCmp(_dateBegin)
						.getValue())
			}
		});
	}

	function add() {
		menu.showModal(menu, 'dictionary/CouponsAdd');
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
		var id = sm.getSelected().data.id;
		Ext.Ajax.request({
			method : 'DELETE',
			url : 'rest/Coupons/' + id + '.do',
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Выполняется запрос',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					reload();
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

	function change() {
		if (sm.getCount() == 0) {
			App.ui.message('Необходимо выбрать запись для изменения!');
			return;
		}

		var id = sm.getSelected().data.id;
		menu.showModal(menu, 'dictionary/CouponsEdit', 'rest/Coupons/' + id
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
			displayField : 'name',
			valueField : 'id',
			store : new Ext.data.JsonStore({
				autoDestroy : true,
				url : 'rest/Coupons/Clients.do',
				//root : 'info',
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
			text : 'Брокер:'
		}, {
			id : _broker,
			xtype : 'combo',
			width : 100,
			displayField : 'name',
			valueField : 'id',
			store : new Ext.data.JsonStore({
				autoDestroy : true,
				url : 'rest/Coupons/Brokers.do',
				//root : 'info',
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
			margins : '2 5 0 15',
			text : 'Инструмент:'
		}, {
			id : _security,
			xtype : 'combo',
			width : 100,
			displayField : 'name',
			valueField : 'name',
			store : new Ext.data.JsonStore({
				autoDestroy : true,
				url : 'rest/Coupons/Bonds.do',
				//root : 'info',
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
			text : 'Операция:'
		}, {
			id : _operations,
			xtype : 'combo',
			width : 100,
			displayField : 'name',
			valueField : 'name',
			store : new Ext.data.JsonStore({
				autoDestroy : true,
				url : 'rest/Coupons/Operations.do',
				//root : 'info',
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
				Ext.getCmp(_operations).clearValue();
			}
		}, {
			xtype : 'label',
			style : 'font-weight: bold;',
			margins : '2 5 0 15',
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
		} ]
	});

	function updateField(id, column, newValue) {
		if ('record_date' == column) {
			newValue = newValue.format('d.m.Y');
		}

		Ext.Ajax.request({
			url : 'rest/Coupons/' + id + '.do',
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

	var editorAccount = new Ext.form.ComboBox({
		displayField : 'name',
		valueField : 'name',
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'rest/Coupons/Accounts.do',
			//root : 'info',
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
			url : 'rest/Coupons/Currencies.do',
			//root : 'info',
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
		id : 'Coupons-component',
		title : 'Купоны (погашение)',
		closable : true,
		frame : true,
		enableHdMenu : false,

		tbar : filter,

		store : info,
		selModel : sm,
		columns : [ new Ext.grid.RowNumberer(), {
			header : 'security_code',
			dataIndex : 'security_code'
		}, {
			header : 'short_name',
			dataIndex : 'short_name'
		}, {
			header : 'client',
			dataIndex : 'client'
		}, {
			header : 'fund',
			dataIndex : 'fund'
		}, {
			header : 'broker',
			dataIndex : 'broker'
		}, {
			header : 'account',
			dataIndex : 'account',
			editor : editorAccount
		}, {
			header : 'currency',
			dataIndex : 'currency',
			editor : editorCurrency
		}, {
			header : 'record_date',
			dataIndex : 'record_date',
			xtype : 'datecolumn',
			format : 'd.m.Y',
			editor : editorRecordDate
		}, {
			header : 'quantity',
			dataIndex : 'quantity',
			align : 'right',
			renderer : App.util.Renderer.number(0),
			editor : editorQuantity
		}, {
			header : 'coupon_per_share',
			dataIndex : 'coupon_per_share',
			align : 'right',
			renderer : App.util.Renderer.number(7),
			editor : editorDividend
		}, {
			header : 'receive_date',
			dataIndex : 'receive_date',
			renderer : App.util.Renderer.date()
		}, {
			header : 'real_coupon_per_share',
			dataIndex : 'real_coupon_per_share',
			align : 'right',
			renderer : App.util.Renderer.number(7)
		}, {
			header : 'status',
			dataIndex : 'status'
		}, {
			header : 'estimate',
			dataIndex : 'estimate',
			align : 'right',
			renderer : App.util.Renderer.number(2)
		}, {
			header : 'real_coupons',
			dataIndex : 'real_coupons',
			align : 'right',
			renderer : App.util.Renderer.number(2),
		}, {
			header : 'extra_costs',
			dataIndex : 'extra_costs',
			align : 'right',
			renderer : App.util.Renderer.number(),
			editor : editorDividend
		}, {
			header : 'tax_value',
			dataIndex : 'tax_value',
			align : 'right',
			renderer : App.util.Renderer.number()
		}, {
			header : 'country',
			dataIndex : 'country'
		}, {
			header : 'oper',
			dataIndex : 'oper'
		} ],
		viewConfig : {
			forceFit : true,
			emptyText : 'Записи не найдены'
		},

		listeners : {
			afteredit : function(e) {
				updateField(e.record.data.id, e.field, e.value);
			}
		},

		refresh : function() {
			reload();
		}
	});
})();
