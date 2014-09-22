/**
 * Задать параметры риска
 */
(function() {

	var _FromDate = Ext.id();
	var _RiskAth = Ext.id();
	var _RiskAvg = Ext.id();
	var _StopLoss = Ext.id();
	var _Comment = Ext.id();

	var info = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : false,
		autoSave : false,
		url : 'rest/SetSecurityRiscs/Portfolio.do',
		// root : 'info',
		fields : [ 'id', 'dated', 'client', 'fund', 'security_code', 'batch',
				'quantity', 'avg_price', 'avg_price_usd', 'currency' ],
		// sortInfo : {
		// field : 'date_insert'
		// },
		listeners : App.ui.listenersJsonStore()
	});

	var sm = new Ext.grid.RowSelectionModel({
		singleSelect : true,
		listeners : {
			rowselect : updateFields
		}
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

	function SetSecurityRiscs() {
		if (sm.getCount() == 0) {
			App.ui.error('Выберите сделку.');
			return;
		}

		Ext.Ajax.request({
			url : 'rest/SetSecurityRiscs.do',
			params : {
				id : sm.getSelected().data.id,
				riskATH : Ext.getCmp(_RiskAth).getValue(),
				riskAVG : Ext.getCmp(_RiskAvg).getValue(),
				stopLoss : Ext.getCmp(_StopLoss).getValue(),
				comment : Ext.getCmp(_Comment).getValue()
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Выполняется запрос',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					App.ui.message('Параметры риска заданы.');
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	function updateFields(rsm, rowIndex, record) {
		Ext.Ajax.request({
			url : 'rest/SetSecurityRiscs/UpdateFields.do',
			params : {
				security : record.data.security_code,
				client : record.data.client,
				fund : record.data.fund,
				batch : record.data.batch,
				date : record.data.dated
			},
			timeout : 10 * 1000, // 10 sec
			//waitMsg : 'Выполняется запрос',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					Ext.getCmp(_RiskAth).setValue(answer.item.risk_ath);
					Ext.getCmp(_RiskAvg).setValue(answer.item.risk_avg);
					Ext.getCmp(_StopLoss).setValue(answer.item.stop_loss);
				} else {
					Ext.getCmp(_RiskAth).reset();
					Ext.getCmp(_RiskAvg).reset();
					Ext.getCmp(_StopLoss).reset();
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	 return new Ext.Panel({
		id : 'SetSecurityRiscs-component',
		title : 'Задать параметры риска',
		baseCls : 'x-plain',
		closable : true,
		border : true,
		layout : 'border',
		frame : true,

		items : [ {
			region : 'center',
			xtype : 'grid',
			title : 'Портфель',
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
				dataIndex : 'client',
				sortable : true
			}, {
				header : 'fund',
				dataIndex : 'fund',
				sortable : true
			}, {
				header : 'security_code',
				dataIndex : 'security_code',
				sortable : true
			}, {
				header : 'batch',
				dataIndex : 'batch',
				sortable : true
			}, {
				header : 'quantity',
				dataIndex : 'quantity',
				sortable : true,
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
				dataIndex : 'currency',
				sortable : true
			} ],
			viewConfig : {
				forceFit : true,
				emptyText : 'Записи не найдены'
			}
		}, {
			region : 'south',
			height : 200,
			title : 'Риск',
			border : true,
			frame : true,
			layout : 'border',

			buttonAlign : 'left',
			buttons : [ {
				text : 'Задать',
				handler : SetSecurityRiscs
			} ],

			items : [ {
				region : 'west',
				width : 300,
				baseCls : 'x-plain',
				padding : 10,
				xtype : 'form',
				labelWidth : 120,
				defaults : {
					width : 150
				},

				items : [ {
					id : _RiskAth,
					fieldLabel : 'Значение риска (ATH), %',
					xtype : 'numberfield',
					decimalPrecision : 6
				}, {
					id : _RiskAvg,
					fieldLabel : 'Значение риска (AVG), %',
					xtype : 'numberfield',
					decimalPrecision : 6
				}, {
					id : _StopLoss,
					fieldLabel : 'Цена (StopLoss)',
					xtype : 'numberfield',
					decimalPrecision : 6
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
