/**
 * Компании и группы - Состав
 */
(function() {

	var groupId = 0;
	var baseUrl = 'rest/CompanyGroup/';

	var storeA = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : false,
		url : baseUrl + '/All.do',
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

	var storeP = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : false,
		url : baseUrl + '/Group.do',
		// root : 'infoCompany',
		fields : [ 'id', 'security_code', 'short_name' ],
		sortInfo : {
			field : 'security_code'
		},
		listeners : App.ui.listenersJsonStore()
	});

	var smP = new Ext.grid.CheckboxSelectionModel({
		singleSelect : false
	});

	function reload() {
		storeA.reload();
		storeP.reload();
	}

	function add(self) {
		if (smA.getCount() == 0) {
			App.ui.message('Необходимо выбрать организации.');
			return;
		}

		var ids = [];
		smA.each(function(item) {
			ids.push(item.data.id);
			return true;
		});

		Ext.Ajax.request({
			url : baseUrl + '/Staff.do',
			params : {
				action : 'ADD',
				ids : ids
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Добавляем компанию в группу',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					reload();
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	function del(self) {
		if (smP.getCount() == 0) {
			App.ui.message('Необходимо выбрать организации.');
			return;
		}

		var ids = [];
		smP.each(function(item) {
			ids.push(item.data.id);
			return true;
		});

		Ext.Ajax.request({
			url : baseUrl + '/Staff.do',
			params : {
				action : 'DEL',
				ids : ids
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Удаляем компанию из группы',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					reload();
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	return new Ext.Panel({
		id : 'CompanyGroupStaff-component',
		title : 'Редактируем группу: ',
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
			title : 'Все компании',

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
			title : 'Компании относящиеся к группе',

			tbar : [ {
				text : 'Удалить',
				handler : del
			} ],

			store : storeP,
			selModel : smP,
			columns : [ smP, {
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
			baseUrl = baseUrl + groupId;

			this.setTitle('Редактируем группу: ' + data.item.name);

			smA.clearSelections(true);
			smP.clearSelections(true);

			storeA.proxy.setUrl(baseUrl + '/All.do', true);
			storeP.proxy.setUrl(baseUrl + '/Group.do', true);

			reload();
		}
	});
})();
