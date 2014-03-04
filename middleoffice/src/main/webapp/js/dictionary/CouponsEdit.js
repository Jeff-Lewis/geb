/**
 * Купоны (погашение) - Изменить статус
 */
(function() {

	var itemId;

	var _receive_date = Ext.id();

	var container = new Ext.FormPanel({
		border : false,
		baseCls : 'x-plain',
		padding : 20,
		labelWidth : 160,
		width : 420,
		height : 120,
		defaults : {
			width : 200
		},

		items : [ {
			id : _receive_date,
			xtype : 'datefield',
			format : 'd.m.Y',
			fieldLabel : 'Дата получения'
		} ],

		buttons : [ {
			text : 'Добавить',
			handler : save

		}, {
			text : 'Отмена',
			handler : function() {
				container.window.close();
			}
		} ],

		loadData : function(data) {
			itemId = data.info.id;
			Ext.getCmp(_receive_date).setValue(data.info.receive_date);
		},
		setWindow : function(window) {
			this.window = window;
			this.window.setTitle('Изменить статус');
		}
	});

	function save(self) {
		Ext.Ajax.request({
			url : 'coupons/CouponsEdit-actual.html',
			params : {
				id : itemId,
				dateReceive : App.util.Format.dateYMD(Ext.getCmp(_receive_date)
						.getValue())
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Сохранение',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					container.window.close();
					Ext.getCmp('coupons-grid-component').refresh();
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

	return container;
})();
