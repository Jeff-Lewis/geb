/**
 * Компании и отчёты - Состав
 */
(function() {

	var groupId = 0;

	var storeA = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : false,
		url : 'rest/CompanyReports/Staff/All.do',
		// root : 'infoCompany',
		fields : [ 'id', 'security_code', 'short_name' ],
		sortInfo : {
			field : 'security_code'
		},
		listeners : App.ui.listenersJsonStore()
	});

	var smA = new Ext.grid.CheckboxSelectionModel({
		singleSelect : false
	});

	var storeS = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : false,
		url : 'rest/CompanyReports/Staff/Report.do',
		// root : 'infoCompany',
		fields : [ 'id', 'security_code', 'short_name' ],
		sortInfo : {
			field : 'security_code'
		}
	});

	var smS = new Ext.grid.CheckboxSelectionModel({
		singleSelect : false
	});

	function reload() {
		storeA.reload({
			params : {
				id : groupId
			}
		});
		storeS.reload({
			params : {
				id : groupId
			}
		});
	}

	function add(self) {
		if (smA.getCount() == 0) {
			App.ui.message('Необходимо выбрать компанию.');
			return;
		}

		var _ids = [];
		smA.each(function(item) {
			_ids.push(item.data.id);
			return true;
		});

		Ext.Ajax.request({
			url : 'rest/CompanyReports/Staff/' + groupId + '.do',
			params : {
				action : 'ADD',
				ids : _ids
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Добавляем компанию в отчёт',
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
		if (smS.getCount() == 0) {
			App.ui.message('Необходимо выбрать компанию.');
			return;
		}

		var _ids = [];
		smS.each(function(item) {
			_ids.push(item.data.id);
			return true;
		});

		Ext.Ajax.request({
			url : 'rest/CompanyReports/Staff/' + groupId + '.do',
			params : {
				action : 'DEL',
				ids : _ids
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Удаляем компанию из отчёта',
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
		id : 'CompanyReportsStaff-component',
		title : 'Редактируем отчёт: ',
		frame : true,
		closable : true,
		layout : 'hbox',
		layoutConfig : {
			align : 'stretch'
		},
		defaults : {
			width : 500,
			frame : true
		},

		items : [ {
			xtype : 'grid',
			title : 'Компании',

			tbar : [ {
				text : 'Добавить',
				handler : add
			} ],

			store : storeA,
			selModel : smA,
			columns : [ smA, new Ext.grid.RowNumberer({
				width : 30
			}), {
				header : 'BLOOMBERG_CODE',
				dataIndex : 'security_code',
				width : 50,
				sortable : true
			}, {
				header : 'NAME',
				dataIndex : 'short_name',
				width : 50,
				sortable : true
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
			title : 'Компании в отчёте',

			tbar : [ {
				text : 'Удалить',
				handler : del
			} ],

			store : storeS,
			selModel : smS,
			columns : [ smS, {
				header : 'BLOOMBERG_CODE',
				dataIndex : 'security_code',
				width : 50,
				sortable : true
			}, {
				header : 'NAME',
				dataIndex : 'short_name',
				width : 50,
				sortable : true
			} ],
			viewConfig : {
				forceFit : true,
				emptyText : 'Записи не найдены'
			}
		} ],

		loadData : function(data) {
			groupId = data.item.id;

			this.setTitle('Редактируем отчёт: ' + data.item.name);

			smA.clearSelections(true);
			smS.clearSelections(true);

			reload();
		}
	});
})();
