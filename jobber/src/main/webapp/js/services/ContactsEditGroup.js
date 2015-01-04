/**
 * Состав группы
 */
(function() {

	var idGroup = 0;
	var baseUrl = 'rest/Groups/0';

	var _rbEMail = Ext.id();

	var addresses = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : false,
		url : 'rest/Groups/0/Addresses.do',
		// root : 'addresses',
		fields : [ 'name', 'cid', 'value', 'type_id' ],
		sortInfo : {
			field : 'name'
		},
		listeners : App.ui.listenersJsonStore()
	});

	var smA = new Ext.grid.CheckboxSelectionModel();

	var contacts = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : false,
		url : 'rest/Groups/0/Contacts.do',
		// root : 'contacts',
		fields : [ 'name', 'cid', 'value' ],
		sortInfo : {
			field : 'name'
		},
		listeners : App.ui.listenersJsonStore()
	});

	var smC = new Ext.grid.CheckboxSelectionModel();

	function reload() {
		addresses.reload({
			callback : function() {
				var b = Ext.getCmp(_rbEMail).getValue();
				addresses.filter('type_id', b ? 2 : 1);
			}
		});
		contacts.reload();
	}

	function add(self) {
		if (smA.getCount() == 0) {
			App.ui.message('Необходимо выбрать контакты.');
			return;
		}

		var ids = [];
		smA.each(function(item) {
			ids.push(item.data.cid);
			return true;
		});

		Ext.Ajax.request({
			url : baseUrl + '/Staff.do',
			params : {
				action : 'ADD',
				cids : ids
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Добавляем контакт в группу',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					reload();
					App.ui.message('Контакты успешно добавлены!');
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	function del(self) {
		if (smC.getCount() == 0) {
			App.ui.message('Необходимо выбрать контакты.');
			return;
		}

		var ids = [];
		smC.each(function(item) {
			ids.push(item.data.cid);
			return true;
		});

		Ext.Ajax.request({
			url : baseUrl + '/Staff.do',
			params : {
				action : 'DEL',
				cids : ids
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Удаляем контакты из группы',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					reload();
					App.ui.message('Контакты удалены из группы!');
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	function filterAddress(radio, checked) {
		if (checked) {
			addresses.filter('type_id', radio.inputValue);
		}
	}

	return new Ext.Panel({
		id : 'ContactsEditGroup-component',
		title : 'Редактирование группы',
		frame : true,
		closable : true,
		layout : 'hbox',
		layoutConfig : {
			align : 'stretch'
		},
		defaults : {
			width : 400,
			frame : true,
			enableHdMenu : false
		},

		items : [ {
			xtype : 'grid',
			title : 'Контакты',

			tbar : [ {
				text : 'Добавить',
				handler : add
			}, '->', {
				id : _rbEMail,
				xtype : 'radio',
				name : 'rgType',
				boxLabel : 'E-MAIL',
				inputValue : 2,
				width : 60,
				checked : true,
				listeners : {
					check : filterAddress
				}
			}, {
				xtype : 'radio',
				name : 'rgType',
				boxLabel : 'SMS',
				width : 60,
				inputValue : 1,
				listeners : {
					check : filterAddress
				}
			} ],

			store : addresses,
			selModel : smA,
			columns : [ smA, new Ext.grid.RowNumberer({
				width : 30
			}), {
				header : 'NAME',
				dataIndex : 'name',
				width : 50
			}, {
				header : 'VALUE',
				dataIndex : 'value'
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
			title : 'Контакты в группе',

			tbar : [ {
				text : 'Удалить',
				handler : del
			} ],

			store : contacts,
			selModel : smC,
			columns : [ smC, new Ext.grid.RowNumberer({
				width : 30
			}), {
				header : 'NAME',
				dataIndex : 'name',
				width : 50,
				sortable : true
			}, {
				header : 'VALUE',
				dataIndex : 'value'
			} ],
			viewConfig : {
				forceFit : true,
				emptyText : 'Записи не найдены'
			}
		} ],

		loadData : function(data) {
			idGroup = data.item.id;
			baseUrl = 'rest/Groups/' + idGroup;

			this.setTitle('Редактирование группы: ' + data.item.name);

			addresses.proxy.setUrl(baseUrl + '/Addresses.do', true);
			contacts.proxy.setUrl(baseUrl + '/Contacts.do', true);

			reload();
		}
	});
})();
