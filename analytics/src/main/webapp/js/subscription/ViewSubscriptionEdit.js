/**
 * Subscription - edit
 */
(function() {

	var subscrId = 0;
	var subscrName = '';

	var storeSec = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : false,
		root : 'infoSec',
		fields : [ 'id_sec', 'security_code', 'security_name' ],
		listeners : App.ui.listenersJsonStore()
	});

	var smSec = new Ext.grid.CheckboxSelectionModel();

	var storeSub = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : false,
		root : 'info',
		fields : [ 'id_sec', 'security_code', 'security_name' ],
		listeners : App.ui.listenersJsonStore()
	});

	var smSub = new Ext.grid.CheckboxSelectionModel();

	function add(self) {
		if (smSec.getCount() == 0) {
			App.ui.message('Необходимо выбрать инструменты!');
			return;
		}

		var ids = [];
		smSec.each(function(item) {
			ids.push(item.data.id_sec);
			return true;
		});

		Ext.Ajax.request({
			url : 'subscription/Subscription-sec-add.html',
			params : {
				id : subscrId,
				security : ids
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Добавляем инструмент в подписку',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					storeSec.loadData(answer);
					storeSub.loadData(answer);
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
		if (smSub.getCount() == 0) {
			App.ui.message('Необходимо выбрать инструменты!');
			return;
		}

		var ids = [];
		smSub.each(function(item) {
			ids.push(item.data.id_sec);
			return true;
		});

		Ext.Ajax.request({
			url : 'subscription/Subscription-sec-del.html',
			params : {
				id : subscrId,
				security : ids
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Удаляем инструменты из подписки',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					storeSec.loadData(answer);
					storeSub.loadData(answer);
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

	var container = new Ext.Panel({
		id : 'ViewSubscriptionEdit-component',
		title : 'Подписка',
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

		tbar : [ {
			text : 'Добавить',
			handler : add
		}, {
			text : 'Удалить',
			handler : del
		} ],

		items : [ {
			xtype : 'grid',
			title : 'Инструменты для подписки',

			store : storeSec,
			selModel : smSec,
			columns : [ smSec, new Ext.grid.RowNumberer({
				width : 30
			}), {
				header : 'SECURITY_CODE',
				dataIndex : 'security_code',
				width : 50,
				sortable : true
			}, {
				header : 'SECURITY_NAME',
				dataIndex : 'security_name',
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
			title : 'Инструменты',

			store : storeSub,
			selModel : smSub,
			columns : [ smSub, new Ext.grid.RowNumberer({
				width : 30
			}), {
				header : 'SECURITY_CODE',
				dataIndex : 'security_code',
				width : 50,
				sortable : true
			}, {
				header : 'SECURITY_NAME',
				dataIndex : 'security_name',
				width : 50,
				sortable : true
			} ],
			viewConfig : {
				forceFit : true,
				emptyText : 'Записи не найдены'
			}
		} ],

		loadData : function(data) {
			subscrId = data.id;
			subscrName = data.name;

			this.setTitle('Подписка ' + subscrName);

			storeSec.loadData(data);
			storeSub.loadData(data);
		}
	});

	return container;
})();
