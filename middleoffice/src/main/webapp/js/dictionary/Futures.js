/**
 * Фьючерсы
 */
(function() {

	var store = new Ext.data.GroupingStore({
		autoDestroy : true,
		autoLoad : false,
		autoSave : false,
		url : 'rest/Futures.do',
		reader : new Ext.data.JsonReader({
			// root : 'info',
			fields : [ 'futuresId', 'futures', 'coefId', 'coefficient', 'tradeSystem', 'comment' ]
		}),
		groupField : 'futures',
		sortInfo : {
			field : 'futures'
		},
		listeners : App.ui.listenersJsonStore()
	});

	var gv = new Ext.grid.GroupingView({
		forceFit : false,
		emptyText : 'Записи не найдены',
		startCollapsed : true,
		enableGrouping : true,
		hideGroupedColumn : true,
		showGroupName : false,
		groupTextTpl : '{text} ({[values.rs.length]})'
	});

	var sm = new Ext.grid.RowSelectionModel({
		singleSelect : true
	});

	function addItem(self) {
		showModal('dictionary/FuturesAdd');
	}

	function addCoItem(self) {
		if (sm.getCount() == 0) {
			App.ui.message('Необходимо выбрать запись для добавления!');
			return;
		}

		var data = sm.getSelected().data;
		showModal('dictionary/FuturesAdd', 'rest/Futures/' + data.futuresId + '.do');
	}

	function editItem(self) {
		if (sm.getCount() == 0) {
			App.ui.message('Необходимо выбрать запись для изменения!');
			return;
		}

		var data = sm.getSelected().data;
		switch (self.text) {
		case 'фьючерс':
			showModal('dictionary/FuturesEdit', 'rest/Futures/' + data.futuresId + '.do');
			break;
		case 'коэффициент':
			showModal('dictionary/FuturesEditCoefficients', 'rest/Futures/Coefficients/' + data.coefId + '.do');
			break;
		}
	}

	function delItem(btn) {
		if (sm.getCount() == 0) {
			App.ui.message('Необходимо выбрать запись для удаления!');
			return;
		}

		var data = sm.getSelected().data;

		if (btn.text == 'фьючерс' || store.query('futures', data.futures).getCount() == 1) {
			App.ui.confirm('Удалить фьючерс "' + data.futures
					+ '"<br>со всеми связанными коэффициентами?', delItemFuAjax);
			return;
		}

		if (btn.text == 'коэффициент') {
			App.ui.confirm('Удалить коэффициент "' + data.tradeSystem
					+ '"<br>для фьючерса "' + data.futures + '"?', delItemCoAjax);
			return;
		}
	}
	function delItemFuAjax() {
		var id = sm.getSelected().data.futuresId;
		Ext.Ajax.request({
			method : 'DELETE',
			url : 'rest/Futures/' + id + '.do',
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Выполняется запрос',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					store.reload();
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}
	function delItemCoAjax() {
		var id = sm.getSelected().data.coefId;
		Ext.Ajax.request({
			method : 'DELETE',
			url : 'rest/Futures/Coefficients/' + id + '.do',
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Выполняется запрос',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					store.reload();
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	return new Ext.grid.GridPanel({
		id : 'Futures-component',
		title : 'Фьючерсы',
		frame : true,
		closable : true,
		enableHdMenu : false,

		tbar : [ {
			text : 'Добавить',
			menu : [ {
				text : 'фьючерс',
				handler : addItem
			}, {
				text : 'коэффициент',
				handler : addCoItem
			} ]
		}, {
			text : 'Изменить',
			menu : [ {
				text : 'фьючерс',
				handler : editItem
			}, {
				text : 'коэффициент',
				handler : editItem
			} ]
		}, {
			text : 'Удалить',
			menu : [ {
				text : 'фьючерс',
				handler : delItem
			}, {
				text : 'коэффициент',
				handler : delItem
			} ]
		} ],

		store : store,
		view : gv,
		selModel : sm,
		columns : [ {
			header : 'futures',
			dataIndex : 'futures'
		}, {
			header : 'TradeSystem',
			dataIndex : 'tradeSystem',
			width : 150,
			sortable : true
		}, {
			header : 'Коэффициент',
			dataIndex : 'coefficient',
			align : 'right',
			renderer : App.util.Renderer.number(6),
			width : 150,
			sortable : true
		}, {
			header : 'Комментарий',
			dataIndex : 'comment',
			width : 350
		} ],

		listeners : {
			show : function(grid) {
				// grid.getView().refresh();
				setTimeout(function() {
					grid.getStore().reload();
				}, 0);
			}
		}
	});
})();
