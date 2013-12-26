/**
 * Сделки РЕПО - Изменить статус
 */
(function() {

	var itemId = 0;

	var _price1 = Ext.id();
	var _quantity = Ext.id();
	var _days = Ext.id();
	var _rate = Ext.id();

	var container = new Ext.FormPanel({
		border : false,
		baseCls : 'x-plain',
		padding : 20,
		labelWidth : 100,
		width : 360,
		height : 200,
		defaults : {
			width : 200
		},

		items : [ {
			id : _price1,
			xtype : 'numberfield',
			decimalPrecision : 6,
			fieldLabel : 'price1'
		}, {
			id : _quantity,
			xtype : 'numberfield',
			allowDecimals : false,
			fieldLabel : 'quantity'
		}, {
			id : _days,
			xtype : 'numberfield',
			allowDecimals : false,
			fieldLabel : 'days'
		}, {
			id : _rate,
			xtype : 'numberfield',
			decimalPrecision : 6,
			fieldLabel : 'rate'
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
			Ext.getCmp(_rate).setValue(data.item.rate);
			Ext.getCmp(_quantity).setValue(data.item.quantity);
			Ext.getCmp(_price1).setValue(data.item.price1);
			Ext.getCmp(_days).setValue(data.item.days);
		},
		setWindow : function(window) {
			this.window = window;
			this.window.setTitle('Изменить сделку РЕПО');
		}
	});

	function save(self) {
		Ext.Ajax.request({
			url : 'rest/ViewDealsREPO/' + itemId + '.do',
			params : {
				rate : Ext.getCmp(_rate).getValue(),
				quantity : Ext.getCmp(_quantity).getValue(),
				price : Ext.getCmp(_price1).getValue(),
				days : Ext.getCmp(_days).getValue()
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Сохранение',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					container.window.close();
					Ext.getCmp('ViewDealsREPO-component').refresh();
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
