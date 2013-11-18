/**
 * Цена1 для RR
 */
(function() {

	var info = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : true,
		url : 'rest/RiskRewardPrice1.do',
		// root : 'info',
		fields : [ 'id', 'client', 'fund', 'security_code', 'batch', 'price',
				'date_begin', 'date_end' ],
		listeners : App.ui.listenersJsonStore()
	});

	var sm = new Ext.grid.RowSelectionModel({
		singleSelect : true
	});

	function add() {
		menu.showPane(menu, 'services/RiskRewardPrice1Add');
	}

	function edt() {
		if (sm.getCount() == 0) {
			App.ui.message('Для изменения выберите позицию.');
			return;
		}

		var id = sm.getSelected().data.id;
		menu.showModal(menu, 'services/RiskRewardPrice1Edit',
				'rest/RiskRewardPrice1/' + id + '.do');
	}

	function del() {
		if (sm.getCount() == 0) {
			App.ui.message('Для удаления выберите позицию.');
			return;
		}

		App.ui.confirm('Удалить запись для "'
				+ sm.getSelected().data.security_code + '"?', delCallback);
	}
	function delCallback() {
		var id = sm.getSelected().data.id;
		Ext.Ajax.request({
			method : 'DELETE',
			url : 'rest/RiskRewardPrice1/' + id + '.do',
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Удаление записи',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					info.reload();
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

	return new Ext.grid.GridPanel({
		id : 'RiskRewardPrice1-component',
		title : 'Цена1 для RR',
		closable : true,
		frame : true,
		enableHdMenu : false,

		tbar : [ {
			text : 'Добавить',
			handler : add
		}, {
			text : 'Изменить',
			handler : edt
		}, {
			text : 'Удалить',
			handler : del
		} ],

		store : info,
		selModel : sm,
		columns : [ new Ext.grid.RowNumberer(), {
			header : 'CLIENT',
			dataIndex : 'client'
		}, {
			header : 'FUND',
			dataIndex : 'fund'
		}, {
			header : 'SECURITY_CODE',
			dataIndex : 'security_code'
		}, {
			header : 'BATCH',
			dataIndex : 'batch'
		}, {
			header : 'PRICE',
			dataIndex : 'price',
			align : 'right',
			renderer : App.util.Renderer.number(12)
		}, {
			header : 'DATE_BEGIN',
			dataIndex : 'date_begin',
		// renderer : App.util.Renderer.date()
		}, {
			header : 'DATE_END',
			dataIndex : 'date_end',
		// renderer : App.util.Renderer.date()
		} ],
		viewConfig : {
			forceFit : true,
			emptyText : 'Записи не найдены'
		},

		refresh : function() {
			info.reload();
		}
	});
})();
