/**
 * Справочник пользователей - Группы - Права
 */
(function() {

	var idGroup = 0;

	var objects = new Ext.data.JsonStore({
	    autoDestroy : true,
	    autoLoad : false,
	    url : 'rest/DictGroups/0/Objects.do',
	    fields : [ 'id', 'object', 'objectName', 'comment' ],
	    sortInfo : {
		    field : 'objectName'
	    },
	    listeners : App.ui.listenersJsonStore()
	});

	var smO = new Ext.grid.CheckboxSelectionModel();

	var staff = new Ext.data.JsonStore({
	    autoDestroy : true,
	    autoLoad : false,
	    url : 'rest/DictGroups/0/Permission.do',
	    fields : [ 'id', 'permission', 'object', 'objectName', 'comment' ],
	    sortInfo : {
		    field : 'objectName'
	    },
	    listeners : App.ui.listenersJsonStore()
	});

	var smS = new Ext.grid.CheckboxSelectionModel();

	function add(self) {
		if (smO.getCount() == 0) {
			App.ui.message('Необходимо выбрать объекты.');
			return;
		}

		if (smS.getCount() == 0) {
			App.ui.message('Необходимо выбрать права.');
			return;
		}

		var objects = [];
		smO.each(function(item) {
			objects.push(item.data.id);
			return true;
		});

		var ids = [];
		smS.each(function(item) {
			ids.push(item.data.id);
			return true;
		});

		return;
		Ext.Ajax.request({
		    url : 'rest/DictGroups/' + idGroup + '/Permission.do',
		    params : {
		    	action : 'ADD',
		    	objects : objects,
			    ids : ids
		    },
		    timeout : 10 * 60 * 1000, // 10 min
		    waitMsg : 'Добавляем контакт в группу',
		    success : function(xhr) {
			    var answer = Ext.decode(xhr.responseText);
			    if (answer.success) {
				    staff.reload();
			    	objects.reload();
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
			App.ui.message('Необходимо выбрать права.');
			return;
		}

		var ids = [];
		smS.each(function(item) {
			ids.push(item.data.id);
			return true;
		});

		return;
		Ext.Ajax.request({
		    url : 'rest/DictGroups/' + idGroup + '/Permission.do',
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
			    	objects.reload();
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
		id : 'Users-Permission-component',
	    title : 'Права группы',
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
	        title : 'Объекты',

	        tbar : [ {
	            text : 'Добавить',
	            handler : add
	        } ],

	        store : objects,
	        selModel : smO,
	        columns : [ smO, new Ext.grid.RowNumberer({
		        width : 30
	        }), {
	            header : 'Object',
	            dataIndex : 'objectName'
	        }, {
	            header : 'Comment',
	            dataIndex : 'comment'
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
	        title : 'Объекты в группе',

	        tbar : [ {
	            text : 'Удалить',
	            handler : del
	        } ],

	        store : staff,
	        selModel : smS,
	        columns : [ smS, new Ext.grid.RowNumberer({
		        width : 30
	        }), {
	            header : 'Object',
	            dataIndex : 'objectName'
	        }, {
	            header : 'Comment',
	            dataIndex : 'comment'
	        } ],
	        viewConfig : {
	            forceFit : true,
	            emptyText : 'Записи не найдены'
	        }
	    } ],

	    loadData : function(data) {
		    idGroup = data.item.id;
		    this.setTitle('Права группы: ' + data.item.name);
		    objects.proxy.setUrl('rest/DictGroups/' + idGroup + '/Objects.do', true);
		    objects.reload();
		    staff.proxy.setUrl('rest/DictGroups/' + idGroup + '/Permission.do', true);
		    staff.reload();
	    }
	});
})();
