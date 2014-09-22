/**
 * Заданные параметры риска - редактировать
 */
(function() {

	var itemId = 0;

	var _fund = Ext.id();
	var _client = Ext.id();
	var _batch = Ext.id();
	var _riskAth = Ext.id();
	var _riskAvg = Ext.id();
	var _stopLoss = Ext.id();
	var _dateBegin = Ext.id();
	var _dateEnd = Ext.id();
	var _comment = Ext.id();

	var container = new Ext.FormPanel({
		border : false,
		baseCls : 'x-plain',
		padding : 20,
		labelWidth : 160,
		width : 420,
		height : 370,
		defaults : {
			width : 200
		},

		items : [ {
			id : _client,
			xtype : 'combo',
			fieldLabel : 'Клиент',
			displayField : 'name',
			valueField : 'id',
			store : new Ext.data.JsonStore({
				autoDestroy : true,
				url : 'rest/SecurityRiscs/Clients.do',
				root : 'info',
				fields : [ 'id', 'name' ],
				sortInfo : {
					field : 'name'
				}
			}),
			loadingText : 'Поиск...',
			triggerAction : 'all'
		}, {
			id : _fund,
			xtype : 'combo',
			fieldLabel : 'Фонд',
			displayField : 'name',
			valueField : 'id',
			store : new Ext.data.JsonStore({
				autoDestroy : true,
				url : 'rest/SecurityRiscs/Funds.do',
				root : 'info',
				fields : [ 'id', 'name' ],
				sortInfo : {
					field : 'name'
				}
			}),
			loadingText : 'Поиск...',
			triggerAction : 'all'
		}, {
			id : _batch,
			xtype : 'numberfield',
			fieldLabel : 'Партия',
			allowDecimals : false
		}, {
			id : _riskAth,
			xtype : 'numberfield',
			fieldLabel : 'Значение риска (ATH), %',
			decimalPrecision : 6
		}, {
			id : _riskAvg,
			xtype : 'numberfield',
			fieldLabel : 'Значение риска (AVG), %',
			decimalPrecision : 6
		}, {
			id : _stopLoss,
			fieldLabel : 'Цена (StopLoss)',
			xtype : 'numberfield',
			decimalPrecision : 6
		}, {
			id : _dateBegin,
			xtype : 'datefield',
			format : 'd.m.Y',
			fieldLabel : 'Начало периода'
		}, {
			id : _dateEnd,
			xtype : 'datefield',
			format : 'd.m.Y',
			fieldLabel : 'Конец периода'
		}, {
			id : _comment,
			xtype : 'textarea',
			fieldLabel : 'Комментарий'
		} ],

		buttons : [ {
			text : 'Сохранить',
			handler : save
		}, {
			text : 'Отмена',
			handler : close
		} ],

		loadData : function(data) {
			itemId = data.item.id;
			setTimeout(function() {
				var c = Ext.getCmp(_client);
				c.getStore().load(
						{
							callback : function(r, options, success) {
								if (success) {
									var idx = c.getStore().find('name',
											data.item.client);
									if (idx != -1) {
										c.setValue(r[idx].data.id);
									}
								}
							}
						});
			}, 0);

			setTimeout(function() {
				var f = Ext.getCmp(_fund);
				f.getStore().load(
						{
							callback : function(r, options, success) {
								if (success) {
									var idx = f.getStore().find('name',
											data.item.fund);
									if (idx != -1) {
										f.setValue(r[idx].data.id);
									}
								}
							}
						});
			}, 0);

			Ext.getCmp(_batch).setValue(data.item.batch);
			Ext.getCmp(_riskAth).setValue(data.item.risk_ath);
			Ext.getCmp(_riskAvg).setValue(data.item.risk_avg);
			Ext.getCmp(_stopLoss).setValue(data.item.stop_loss);
			Ext.getCmp(_dateBegin).setValue(data.item.date_begin);
			Ext.getCmp(_dateEnd).setValue(data.item.date_end);
			Ext.getCmp(_comment).setValue(data.item.comment);
		},
		setWindow : function(window) {
			this.window = window;
			this.window.setTitle('Редактирование параметров риска');
		}
	});

	function save(b, e) {
		var db = Ext.getCmp(_dateBegin).getValue();
		var de = Ext.getCmp(_dateEnd).getValue();
		Ext.Ajax.request({
			url : 'rest/SecurityRiscs/' + itemId + '.do',
			params : {
				client : Ext.getCmp(_client).getValue(),
				fund : Ext.getCmp(_fund).getValue(),
				batch : Ext.getCmp(_batch).getValue(),
				riskATH : Ext.getCmp(_riskAth).getValue(),
				riskAVG : Ext.getCmp(_riskAvg).getValue(),
				stopLoss : Ext.getCmp(_stopLoss).getValue(),
				dateBegin : App.util.Format.dateYMD(db),
				dateEnd : App.util.Format.dateYMD(de),
				comment : Ext.getCmp(_comment).getValue()
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Сохранение изменений',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					container.window.close();
					Ext.getCmp('SecurityRiscs-component').refresh();
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	function close(b, e) {
		container.window.close();
	}

	return container;
})();
