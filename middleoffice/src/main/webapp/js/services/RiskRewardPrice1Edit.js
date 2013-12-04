/**
 * Цена1 для RR - редактировать
 */
(function() {

	var idItem = 0;

	var cbClient = new Ext.form.ComboBox({
		fieldLabel : 'CLIENT',
		// valueField : 'id',
		displayField : 'name',
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'rest/RiskRewardPrice1/Clients.do',
			// root : 'info',
			fields : [ 'id', 'name' ]
		}),
		emptyText : 'Выберите',
		loadingText : 'Поиск...',
		triggerAction : 'all'
	});

	var cbFund = new Ext.form.ComboBox({
		fieldLabel : 'FUND',
		// valueField : 'id',
		displayField : 'name',
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'rest/RiskRewardPrice1/Funds.do',
			// root : 'info',
			fields : [ 'id', 'name' ]
		}),
		emptyText : 'Выберите',
		loadingText : 'Поиск...',
		triggerAction : 'all'
	});

	var _batch = Ext.id();
	var _price = Ext.id();
	var _dateBegin = Ext.id();
	var _dateEnd = Ext.id();

	var container = new Ext.FormPanel({
		border : false,
		baseCls : 'x-plain',
		padding : 20,
		labelWidth : 160,
		width : 420,
		height : 260,
		defaults : {
			width : 200
		},

		items : [ cbClient, cbFund, {
			id : _batch,
			xtype : 'numberfield',
			fieldLabel : 'BATCH',
			allowDecimals : false,
			emptyText : 'Заполните',
			allowBlank : false
		}, {
			id : _price,
			xtype : 'numberfield',
			fieldLabel : 'PRICE',
			decimalPrecision : 12,
			emptyText : 'Заполните',
			allowBlank : false
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
		} ],

		buttons : [ {
			text : 'Сохранить',
			handler : save
		}, {
			text : 'Отмена',
			handler : close
		} ],

		loadData : function(data) {
			data = data.item;

			idItem = data.id;

			cbClient.setValue(data.client);
			setTimeout(function() {
				cbClient.getStore().load({
					callback : function(r, options, success) {
						if (success) {
							cbClient.setValue(data.client);
						}
					}
				});
			}, 1);

			cbFund.setValue(data.fund);
			setTimeout(function() {
				cbFund.getStore().load({
					callback : function(r, options, success) {
						if (success) {
							cbFund.setValue(data.fund);
						}
					}
				});
			}, 2);

			Ext.getCmp(_batch).setValue(data.batch);
			Ext.getCmp(_price).setValue(data.price);
			// Ext.getCmp(_dateBegin).setValue(App.util.Renderer.date()(data.date_begin));
			// Ext.getCmp(_dateEnd).setValue(App.util.Renderer.date()(data.date_end));
			Ext.getCmp(_dateBegin).setValue(data.date_begin);
			Ext.getCmp(_dateEnd).setValue(data.date_end);
		},
		setWindow : function(window) {
			this.window = window;
			this.window.setTitle('Редактирование Цена1 для RR');
		}
	});

	function save(self) {
		Ext.Ajax.request({
			url : 'rest/RiskRewardPrice1/' + idItem + '.do',
			params : {
				clientId : App.Combo.getValueId(cbClient),
				fundId : App.Combo.getValueId(cbFund),
				batch : Ext.getCmp(_batch).getValue(),
				price : Ext.getCmp(_price).getValue(),
				dateBegin : App.util.Format.dateYMD(Ext.getCmp(_dateBegin)
						.getValue()),
				dateEnd : App.util.Format.dateYMD(Ext.getCmp(_dateEnd)
						.getValue())
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Сохранение изменений',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					container.window.close();
					Ext.getCmp('RiskRewardPrice1-component').refresh();
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

	function close() {
		container.window.close();
	}

	return container;
})();
