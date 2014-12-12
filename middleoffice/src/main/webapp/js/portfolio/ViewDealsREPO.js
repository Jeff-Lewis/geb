/**
 * Сделки РЕПО
 */
(function() {

	var _dateBegin = Ext.id();
	var _dateEnd = Ext.id();
	var _security = Ext.id();

	var info = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : false,
		autoSave : false,
		url : 'rest/ViewDealsREPO.do',
		// root : 'info',
		fields : [ 'id', 'client', 'broker', 'account', 'security_code',
				'currency', 'deal_num', 'operation', 'deal_date', 'price1',
				'quantity', 'volume1', 'days', 'reverse_date', 'rate',
				'price2', 'volume2' ],
		// sortInfo : { field : 'name' },
		listeners : App.ui.listenersJsonStore()
	});

	function reload() {
		var db = Ext.getCmp(_dateBegin).getValue();
		var de = Ext.getCmp(_dateEnd).getValue();
		info.reload({
			params : {
				dateBegin : App.util.Format.dateYMD(db),
				dateEnd : App.util.Format.dateYMD(de),
				security : Ext.getCmp(_security).getValue()
			}
		});
	}

	var sm = new Ext.grid.RowSelectionModel({
		singleSelect : true
	});

	function edit() {
		if (sm.getCount() == 0) {
			App.ui.message('Необходимо выбрать запись для редактирования!');
			return;
		}

		var id = sm.getSelected().data.id;
		showModal('portfolio/ViewDealsREPOEdit',
				'rest/ViewDealsREPO/' + id + '.do');
	}

	function del() {
		if (sm.getCount() == 0) {
			App.ui.message('Необходимо выбрать запись для удаления!');
			return;
		}

		var name = sm.getSelected().data.deal_num;
		App.ui.confirm('Удалить запись для "' + name + '"?', cbDel);
	}
	function cbDel() {
		var id = sm.getSelected().data.id;
		Ext.Ajax.request({
			method : 'DELETE',
			url : 'rest/ViewDealsREPO/' + id + '.do',
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
			margins : '2 5 0 5',
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
				url : 'rest/ViewDealsREPO/Equities.do',
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
		id : 'ViewDealsREPO-component',
		title : 'Сделки РЕПО',
		closable : true,
		frame : true,
		enableHdMenu : false,

		tbar : filter,

		store : info,
		selModel : sm,
		columns : [ new Ext.grid.RowNumberer(), {
			header : 'client',
			dataIndex : 'client'
		}, {
			header : 'broker',
			dataIndex : 'broker'
		}, {
			header : 'account',
			dataIndex : 'account'
		}, {
			header : 'security_code',
			dataIndex : 'security_code'
		}, {
			header : 'currency',
			dataIndex : 'currency'
		}, {
			header : 'deal_num',
			dataIndex : 'deal_num'
		}, {
			header : 'operation',
			dataIndex : 'operation'
		}, {
			header : 'deal_date',
			dataIndex : 'deal_date',
			renderer : App.util.Renderer.datetime()
		}, {
			header : 'price1',
			dataIndex : 'price1',
			align : 'right',
			renderer : App.util.Renderer.number(6)
		}, {
			header : 'quantity',
			dataIndex : 'quantity',
			align : 'right',
			renderer : App.util.Renderer.number(0)
		}, {
			header : 'volume1',
			dataIndex : 'volume1',
			align : 'right',
			renderer : App.util.Renderer.number(6)
		}, {
			header : 'days',
			dataIndex : 'days',
			align : 'right',
			renderer : App.util.Renderer.number(0)
		}, {
			header : 'reverse_date',
			dataIndex : 'reverse_date',
			renderer : App.util.Renderer.date()
		}, {
			header : 'rate',
			dataIndex : 'rate',
			align : 'right',
			renderer : App.util.Renderer.number(6)
		}, {
			header : 'price2',
			dataIndex : 'price2',
			align : 'right',
			renderer : App.util.Renderer.number(6)
		}, {
			header : 'volume2',
			dataIndex : 'volume2',
			align : 'right',
			renderer : App.util.Renderer.number(6)
		} ],
		viewConfig : {
			forceFit : true,
			emptyText : 'Записи не найдены'
		},

		refresh : function() {
			reload();
		}
	});
})();
