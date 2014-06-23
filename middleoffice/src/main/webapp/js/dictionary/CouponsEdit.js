/**
 * Купоны (погашение) - Изменить статус
 */
(function() {

	var itemId = 0;

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
			text : 'Сохранить',
			handler : save

		}, {
			text : 'Отмена',
			handler : close
		} ],

		loadData : function(data) {
			itemId = data.item.id;
			Ext.getCmp(_receive_date).setValue(data.item.receive_date);
		},
		setWindow : function(window) {
			this.window = window;
			this.window.setTitle('Изменить статус');
		}
	});

	function save(self) {
		Ext.Ajax.request({
			url : 'rest/Coupons/' + itemId + '.do',
			params : {
				field : 'ACTUAL',
				value : App.util.Format.dateYMD(Ext.getCmp(_receive_date)
						.getValue())
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Сохранение',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					container.window.close();
					Ext.getCmp('Coupons-component').refresh();
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
