/**
 * Добавление организаций в Portfolio
 */
(function() {

	var storeA = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : true,
		url : 'rest/ViewPortfolio/Securities.do',
		// root : 'sec',
		fields : [ 'id_sec', 'security_code' ],
		sortInfo : {
			field : 'security_code'
		},
		listeners : App.ui.listenersJsonStore()
	});

	var smA = new Ext.grid.CheckboxSelectionModel();

	var storeP = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : true,
		url : 'rest/ViewPortfolio/Portfolio.do',
		// root : 'secP',
		fields : [ 'id_sec', 'security_code' ],
		sortInfo : {
			field : 'security_code'
		},
	// listeners : App.ui.listenersJsonStore()
	});

	var smP = new Ext.grid.CheckboxSelectionModel();

	function reload() {
		// 'staticdatarequest/multi-request-form.html'
		storeA.reload(); // sec
		storeP.reload(); // secP
	}

	function add(self) {
		if (smA.getCount() == 0) {
			App.ui.message('Необходимо галочками выбрать организации!');
			return;
		}

		var ids = [];
		smA.each(function(item) {
			ids.push(item.data.id_sec);
			return true;
		});

		Ext.Ajax.request({
			url : 'rest/ViewPortfolio.do',
			params : {
				action : 'ADD',
				ids : ids
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Добавляем организации в портфель',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					reload();
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

	function del(self) {
		if (smP.getCount() == 0) {
			App.ui.message('Необходимо галочками выбрать организации!');
			return;
		}

		var ids = [];
		smP.each(function(item) {
			ids.push(item.data.id_sec);
			return true;
		});

		Ext.Ajax.request({
			url : 'rest/ViewPortfolio.do',
			params : {
				action : 'DEL',
				ids : ids
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Удаляем организации из портфеля',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					reload();
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

	return new Ext.Panel({
		id : 'ViewPortfolio-component',
		title : 'Добавляем в портфель',
		frame : true,
		closable : true,
		layout : 'hbox',
		layoutConfig : {
			align : 'stretch'
		},
		defaults : {
			frame : true,
			enableHdMenu : false,
			width : 350
		},

		items : [ {
			xtype : 'grid',
			title : 'НЕ В ПОРТФЕЛЕ',

			tbar : [ {
				text : 'Добавить',
				handler : add
			} ],

			store : storeA,
			selModel : smA,
			columns : [ new Ext.grid.RowNumberer({
				width : 30
			}), smA, {
				header : 'BLOOMBERG_CODE',
				dataIndex : 'security_code',
				width : 50
			} ],

			viewConfig : {
				forceFit : true,
				emptyText : 'Записи не найдены'
			}
		}, {
			xtype : 'container',
			width : 20
		}, {
			xtype : 'grid',
			title : 'В ПОРТФЕЛЕ',

			tbar : [ {
				text : 'Удалить',
				handler : del
			} ],

			store : storeP,
			selModel : smP,
			columns : [ new Ext.grid.RowNumberer({
				width : 30
			}), smP, {
				header : 'BLOOMBERG_CODE',
				dataIndex : 'security_code',
				width : 50
			} ],

			viewConfig : {
				forceFit : true,
				emptyText : 'Записи не найдены'
			}
		} ]
	});
})();
