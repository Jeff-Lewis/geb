/**
 * Заданные параметры риска
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
		url : 'rest/SecurityRiscs.do',
		// root : 'info',
		fields : [ 'id', 'client', 'fund', 'security_code', 'batch',
				'risk_ath', 'risk_avg', 'stop_loss', 'date_begin', 'date_end',
				'comment' ],
		sortInfo : {
			field : 'client'
		},
		listeners : App.ui.listenersJsonStore()
	});

	var sm = new Ext.grid.RowSelectionModel({
		singleSelect : true
	});

	function reload() {
		info.reload({
			params : {
				security : Ext.getCmp(_security).getValue(),
				client : Ext.getCmp(_client).getValue(),
				fund : Ext.getCmp(_fund).getValue(),
				batch : Ext.getCmp(_batch).getValue(),
				date : App.util.Format.dateYMD(Ext.getCmp(_date).getValue())
			}
		});
	}

	function edit(self) {
		if (sm.getCount() == 0) {
			App.ui.message('Необходимо выбрать запись для редактирования!');
			return;
		}

		var id = sm.getSelected().data.id;
		showModal('portfolio/SecurityRiscsEdit',
				'rest/SecurityRiscs/' + id + '.do');
	}

	function del(self) {
		if (sm.getCount() == 0) {
			App.ui.message('Необходимо выбрать запись для удаления!');
			return;
		}

		App.ui.confirm('Удалить запись?', cbDel);
	}
	function cbDel() {
		var id = sm.getSelected().data.id;
		Ext.Ajax.request({
			method : 'DELETE',
			url : 'rest/SecurityRiscs/' + id + '.do',
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
			displayField : 'name',
			valueField : 'id',
			store : new Ext.data.JsonStore({
				autoDestroy : true,
				url : 'rest/SecurityRiscs/Securities.do',
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
			margins : '2 5 0 15',
			text : 'Клиент:'
		}, {
			id : _client,
			xtype : 'combo',
			width : 70,
			displayField : 'name',
			valueField : 'id',
			store : new Ext.data.JsonStore({
				autoDestroy : true,
				url : 'rest/SecurityRiscs/Clients.do',
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
			margins : '2 5 0 15',
			text : 'Фонд:'
		}, {
			id : _fund,
			xtype : 'combo',
			width : 100,
			displayField : 'name',
			valueField : 'id',
			store : new Ext.data.JsonStore({
				autoDestroy : true,
				url : 'rest/SecurityRiscs/Funds.do',
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
			xtype : 'label',
			style : 'font-weight: bold;',
			margins : '2 5 0 15',
			text : 'Дата:'
		}, {
			id : _date,
			xtype : 'datefield',
			format : 'd.m.Y'
		} ],

		buttonAlign : 'left',
		buttons : [ {
			text : 'Показать',
			handler : reload
		}, ' ', {
			text : 'Изменить',
			handler : edit
		}, {
			text : 'Удалить',
			handler : del
		} ]
	});

	return new Ext.grid.GridPanel({
		id : 'SecurityRiscs-component',
		title : 'Заданные параметры риска',
		frame : true,
		closable : true,

		tbar : filter,

		store : info,
		selModel : sm,
		columns : [ new Ext.grid.RowNumberer({
			width : 30
		}), {
			header : 'client',
			dataIndex : 'client'
		}, {
			header : 'fund',
			dataIndex : 'fund'
		}, {
			header : 'security_code',
			dataIndex : 'security_code'
		}, {
			header : 'batch',
			dataIndex : 'batch'
		}, {
			header : 'risk_ath',
			dataIndex : 'risk_ath',
			align : 'right',
			renderer : App.util.Renderer.number(6)
		}, {
			header : 'risk_avg',
			dataIndex : 'risk_avg',
			align : 'right',
			renderer : App.util.Renderer.number(6)
		}, {
			header : 'stop_loss',
			dataIndex : 'stop_loss',
			align : 'right',
			renderer : App.util.Renderer.number(6)
		}, {
			header : 'date_begin',
			dataIndex : 'date_begin'
		}, {
			header : 'date_end',
			dataIndex : 'date_end'
		}, {
			header : 'comment',
			dataIndex : 'comment'
		}, ],
		viewConfig : {
			forceFit : true,
			emptyText : 'Записи не найдены'
		},
		refresh : function() {
			reload();
		}
	});
})();
