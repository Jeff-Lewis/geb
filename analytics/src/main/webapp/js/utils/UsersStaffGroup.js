/**
 * Справочник пользователей - Группы - Состав
 */
(function() {

	var idGroup = 0;

	var users = new Ext.data.JsonStore({
	    autoDestroy : true,
	    autoLoad : false,
	    url : 'rest/DictGroups/0/Users.do',
	    fields : [ 'id', 'name' ],
	    sortInfo : {
		    field : 'name'
	    },
	    listeners : App.ui.listenersJsonStore()
	});

	var smU = new Ext.grid.CheckboxSelectionModel();

	var staff = new Ext.data.JsonStore({
	    autoDestroy : true,
	    autoLoad : false,
	    url : 'rest/DictGroups/0/Staff.do',
	    fields : [ 'id', 'name' ],
	    sortInfo : {
		    field : 'name'
	    },
	    listeners : App.ui.listenersJsonStore()
	});

	var smS = new Ext.grid.CheckboxSelectionModel();

	function add(self) {
		if (smU.getCount() == 0) {
			App.ui.message('Необходимо выбрать пользователя.');
			return;
		}

		var ids = [];
		smU.each(function(item) {
			ids.push(item.data.id);
			return true;
		});

		Ext.Ajax.request({
		    url : 'rest/DictGroups/' + idGroup + '/Staff.do',
		    params : {
		    	action : 'ADD',
			    ids : ids
		    },
		    timeout : 10 * 60 * 1000, // 10 min
		    waitMsg : 'Добавляем контакт в группу',
		    success : function(xhr) {
			    var answer = Ext.decode(xhr.responseText);
			    if (answer.success) {
				    staff.reload();
			    	users.reload();
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
			App.ui.message('Необходимо выбрать пользователя.');
			return;
		}

		var ids = [];
		smS.each(function(item) {
			ids.push(item.data.id);
			return true;
		});

		Ext.Ajax.request({
		    url : 'rest/DictGroups/' + idGroup + '/Staff.do',
		    params : {
		    	action : 'DEL',
			    ids : ids
		    },
		    timeout : 10 * 60 * 1000, // 10 min
		    waitMsg : 'Удаляем контакты из группы',
		    success : function(xhr) {
			    var answer = Ext.decode(xhr.responseText);
			    if (answer.success) {
				    staff.reload();
			    	users.reload();
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
	    id : 'Users-Staff-component',
	    title : 'Состав группы',
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
	        title : 'Пользователи',

	        tbar : [ {
	            text : 'Добавить',
	            handler : add
	        } ],

	        store : users,
	        selModel : smU,
	        columns : [ smU, new Ext.grid.RowNumberer({
		        width : 30
	        }), {
	            header : 'NAME',
	            dataIndex : 'name'
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
	        title : 'Пользователи в группе',

	        tbar : [ {
	            text : 'Удалить',
	            handler : del
	        } ],

	        store : staff,
	        selModel : smS,
	        columns : [ smS, new Ext.grid.RowNumberer({
		        width : 30
	        }), {
	            header : 'NAME',
	            dataIndex : 'name'
	        } ],
	        viewConfig : {
	            forceFit : true,
	            emptyText : 'Записи не найдены'
	        }
	    } ],

	    loadData : function(data) {
		    idGroup = data.item.id;
		    this.setTitle('Редактирование группы: ' + data.item.name);
		    users.proxy.setUrl('rest/DictGroups/' + idGroup + '/Users.do', true);
		    users.reload();
		    staff.proxy.setUrl('rest/DictGroups/' + idGroup + '/Staff.do', true);
		    staff.reload();
	    }
	});
})();
