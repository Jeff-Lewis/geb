/**
 * Перекидка ЦБ между фондами
 */
(function() {

	var _FromDate = Ext.id();
	var _Portfolio = Ext.id();
	var _Quantity = Ext.id();
	var _Price = Ext.id();
	var _Batch = Ext.id();
	var _Comment = Ext.id();

	var info = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : false,
		autoSave : false,
		url : 'rest/DealsTransfer/Portfolio.do',
		// root : 'info',
		fields : [ 'id', 'dated', 'client', 'fund', 'security_code', 'batch',
				'quantity', 'avg_price', 'avg_price_usd', 'currency' ],
		// sortInfo : {
		// field : 'date_insert'
		// },
		listeners : App.ui.listenersJsonStore()
	});

	function loadInfo() {
		var fd = Ext.getCmp(_FromDate).getValue();

		if (fd == '') {
			App.ui.error('Необходимо выбрать дату!');
			return;
		}

		info.reload({
			params : {
				date : App.util.Format.dateYMD(fd)
			}
		});
	}

	var sm = new Ext.grid.RowSelectionModel({
		singleSelect : true
	});

	function TransferDeals() {
		if (sm.getCount() == 0) {
			App.ui.error('Выберите сделку.');
			return;
		}

		Ext.Ajax.request({
			url : 'rest/DealsTransfer.do',
			params : {
				portfolioId : sm.getSelected().data.id,
				quantity : Ext.getCmp(_Quantity).getValue(),
				price : Ext.getCmp(_Price).getValue(),
				fundId : Ext.getCmp(_Portfolio).getValue(),
				batch : Ext.getCmp(_Batch).getValue(),
				comment : Ext.getCmp(_Comment).getValue()
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Выполняется запрос',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					App.ui.message('Перекидка выполнена.');
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	return new Ext.Panel({
		id : 'DealsTransfer-component',
		title : 'Перекидка ЦБ между фондами',
		baseCls : 'x-plain',
		closable : true,
		border : true,
		layout : 'border',
		frame : true,

		items : [ {
			region : 'center',
			xtype : 'grid',
			title : 'Откуда',
			frame : true,
			enableHdMenu : false,

			tbar : [ {
				id : _FromDate,
				xtype : 'datefield',
				format : 'd.m.Y',
				allowBlank : false,
				width : 100,
				value : new Date()
			}, {
				text : 'Выбрать',
				handler : loadInfo
			} ],

			store : info,
			selModel : sm,
			columns : [ new Ext.grid.RowNumberer(), {
				header : 'dated',
				dataIndex : 'dated',
				renderer : App.util.Renderer.date()
			}, {
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
				header : 'quantity',
				dataIndex : 'quantity',
				align : 'right',
				renderer : App.util.Renderer.number(0)
			}, {
				header : 'avg_price',
				dataIndex : 'avg_price',
				align : 'right',
				renderer : App.util.Renderer.number()
			}, {
				header : 'avg_price_usd',
				dataIndex : 'avg_price_usd',
				align : 'right',
				renderer : App.util.Renderer.number()
			}, {
				header : 'currency',
				dataIndex : 'currency'
			} ],
			viewConfig : {
				forceFit : true,
				emptyText : 'Записи не найдены'
			},
		}, {
			region : 'south',
			height : 220,
			title : 'Куда',
			border : true,
			frame : true,
			layout : 'border',

			buttonAlign : 'left',
			buttons : [ {
				text : 'Выполнить',
				handler : TransferDeals
			} ],

			items : [ {
				region : 'west',
				width : 300,
				baseCls : 'x-plain',
				padding : 10,
				xtype : 'form',
				labelWidth : 120,
				defaults : {
					emptyText : 'Заполните',
					width : 150
				},

				items : [ {
					id : _Portfolio,
					fieldLabel : 'Фонд',
					xtype : 'combo',
					valueField : 'id',
					displayField : 'name',
					store : new Ext.data.JsonStore({
						autoDestroy : true,
						url : 'rest/DealsTransfer/Funds.do',
						// root : 'info',
						fields : [ 'id', 'name' ],
						sortInfo : {
							field : 'name'
						}
					}),
					emptyText : 'Выберите из списка',
					loadingText : 'Поиск...',
					triggerAction : 'all',
					editable : false
				}, {
					id : _Quantity,
					fieldLabel : 'Количество',
					xtype : 'numberfield',
					allowDecimals : false
				}, {
					id : _Price,
					fieldLabel : 'Цена',
					xtype : 'numberfield',
					decimalPrecision : 6
				}, {
					id : _Batch,
					fieldLabel : 'Партия',
					xtype : 'numberfield',
					allowDecimals : false
				} ]
			}, {
				region : 'center',
				layout : 'form',
				baseCls : 'x-plain',
				padding : 10,
				labelAlign : 'top',
				items : {
					id : _Comment,
					fieldLabel : 'Комментарий',
					emptyText : 'Заполните',
					xtype : 'textarea',
					width : 500,
					height : 80
				}
			} ]
		} ]
	});
})();
