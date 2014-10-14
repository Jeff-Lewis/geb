/**
 * Сортировка клиентов
 */
(function() {

	function convertDate(v, rec) {
		v = Ext.util.Format.substr(v, 0, 10);
		v = Date.parseDate(v, 'Y-m-d');
		return v;
	}

	var clientsS = new Ext.data.JsonStore({
	    autoDestroy : true,
	    autoLoad : true,
	    url : 'rest/ClientSort/Selected.do',
	    fields : [ 'id', 'sort', 'client', 'name', 'tablename', {
	        name : 'dateBegin',
	        convert : convertDate
	    } ],
	    // sortInfo : {
	    // field : 'name'
	    // },
	    listeners : App.ui.listenersJsonStore()
	});

	var smCS = new Ext.grid.RowSelectionModel({
		singleSelect : true
	});

	var clientsU = new Ext.data.JsonStore({
	    autoDestroy : true,
	    autoLoad : true,
	    url : 'rest/ClientSort/Unselected.do',
	    fields : [ 'id', 'sort', 'client', 'name', 'tablename', {
	        name : 'dateBegin',
	        convert : convertDate
	    } ],
	    // sortInfo : {
	    // field : 'name'
	    // },
	    listeners : App.ui.listenersJsonStore()
	});

	var smCU = new Ext.grid.RowSelectionModel({
		singleSelect : true
	});

	function handlerAction(sm, flag) {
		if (sm.getCount() == 0) {
			App.ui.message('Необходимо выбрать клиента.');
			return;
		}

		var id = sm.getSelected().data.id;
		Ext.Ajax.request({
		    url : 'rest/ClientSort/Action.do',
		    params : {
		        id : id,
		        flag : flag
		    },
		    timeout : 60 * 1000, // 1 min
		    waitMsg : 'Сохранение',
		    success : function(xhr) {
			    var answer = Ext.decode(xhr.responseText);
			    if (answer.success) {
				    switch (flag) {
				    case 1:
				    case 2:
					    clientsU.reload();
				    }
				    clientsS.reload();
			    }
		    },
		    failure : function() {
			    App.ui.error('Сервер недоступен');
		    }
		});
	}

	var editorDate = {
	    xtype : 'datefield',
	    allowBlank : false,
	    format : 'd.m.Y',
	    maxValue : new Date()
	};

	function updateField(id, column, newValue) {
		var dateBegin = App.util.Format.dateYMD(newValue);

		Ext.Ajax.request({
		    url : 'rest/ClientSort/SetDate.do',
		    params : {
		        id : id,
		        dateBegin : dateBegin
		    },
		    timeout : 60 * 1000, // 1 min
		    waitMsg : 'Сохранение',
		    success : function(xhr) {
			    var answer = Ext.decode(xhr.responseText);
			    if (answer.success) {
				    switch (flag) {
				    case 1:
				    case 2:
					    clientsU.reload();
				    }
				    clientsS.reload();
			    }
		    },
		    failure : function() {
			    App.ui.error('Сервер недоступен');
		    }
		});
	}

	return new Ext.Panel({
	    id : 'ClientSort-component',
	    title : 'Сортировка клиентов',
	    closable : true,
	    frame : true,
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
	        xtype : 'editorgrid',
	        title : 'Выбранные',

	        tbar : [ {
	            text : 'Выше',
	            handler : function() {
		            handlerAction(smCS, 3);
	            }
	        }, {
	            text : 'Ниже',
	            handler : function() {
		            handlerAction(smCS, 4);
	            }
	        }, ' ', {
	            text : 'Удалить',
	            handler : function() {
		            handlerAction(smCS, 2);
	            }
	        } ],

	        store : clientsS,
	        selModel : smCS,
	        columns : [ new Ext.grid.RowNumberer({
		        width : 30
	        }), {
	            header : 'Клиент',
	            dataIndex : 'name',
	            editable : false
	        }, {
	            header : 'Таблица',
	            dataIndex : 'tablename',
	            editable : false
	        }, {
	            header : 'Начальная дата',
	            dataIndex : 'dateBegin',
	            align : 'center',
	            xtype : 'datecolumn',
	            format : 'd.m.Y',
	            editor : editorDate
	        } ],
	        viewConfig : {
	            forceFit : true,
	            emptyText : 'Записи не найдены'
	        },

	        listeners : {
		        afteredit : function(e) {
			        updateField(e.record.data.id, e.field, e.value);
		        }
	        }
	    }, {
	        xtype : 'container',
	        width : 20
	    }, {
	        xtype : 'editorgrid',
	        title : 'Не выбранные',

	        tbar : [ {
	            text : 'Добавить',
	            handler : function() {
		            handlerAction(smCU, 1);
	            }
	        } ],

	        store : clientsU,
	        selModel : smCU,
	        columns : [ new Ext.grid.RowNumberer({
		        width : 30
	        }), {
	            header : 'Клиент',
	            dataIndex : 'name',
	            editable : false
	        }, {
	            header : 'Таблица',
	            dataIndex : 'tablename',
	            editable : false
	        }, {
	            header : 'Начальная дата',
	            dataIndex : 'dateBegin',
	            align : 'center',
	            xtype : 'datecolumn',
	            format : 'd.m.Y',
	            editor : editorDate
	        } ],
	        viewConfig : {
	            forceFit : true,
	            emptyText : 'Записи не найдены'
	        },

	        listeners : {
		        afteredit : function(e) {
			        updateField(e.record.data.id, e.field, e.value);
		        }
	        }
	    } ]
	});
})();
