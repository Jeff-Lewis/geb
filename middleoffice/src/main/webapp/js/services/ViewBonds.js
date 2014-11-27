/**
 * Редактирование облигаций
 */
(function() {

	var _nameTicker = Ext.id();

	var smS = new Ext.grid.CheckboxSelectionModel({
		singleSelect : true
	});

	var securities = new Ext.data.JsonStore({
	    autoDestroy : true,
	    autoLoad : true,
	    url : 'rest/ViewBonds/Securities.do',
	    // root : 'info',
	    fields : [ 'id_sec', 'security_code', 'type_id', 'short_name' ],
	    sortInfo : {
		    field : 'security_code'
	    },
	    listeners : App.ui.listenersJsonStore()
	});

	var smP = new Ext.grid.CheckboxSelectionModel({
		singleSelect : true
	});

	var portfolio = new Ext.data.JsonStore({
	    autoDestroy : true,
	    autoLoad : true,
	    url : 'rest/ViewBonds/Portfolio.do',
	    // root : 'info',
	    fields : [ 'id_sec', 'security', 'ticker', 'deal_name', 'date_insert' ],
	    sortInfo : {
		    field : 'ticker'
	    },
	    listeners : App.ui.listenersJsonStore()
	});

	function reload() {
		securities.reload();
		portfolio.reload();
	}

	function add(self) {
		if (smS.getCount() == 0) {
			App.ui.message('Необходимо выбрать котировку.');
			return;
		}

		var deal_name = Ext.getCmp(_nameTicker).getValue();
		if (!deal_name) {
			App.ui.message('Необходимо заполнить тикер.');
			return;
		}

		Ext.Ajax.request({
		    url : 'rest/ViewBonds/Add.do',
		    params : {
		        code : smS.getSelected().data.id_sec,
		        ticker : deal_name
		    },
		    timeout : 10 * 60 * 1000, // 10 min
		    waitMsg : 'Добавляем котировку в портфель',
		    success : function(xhr) {
			    var answer = Ext.decode(xhr.responseText);
			    if (answer.success) {
				    App.ui.message('Организации успешно добавлены в портфель!', false, reload);
			    }
		    },
		    failure : function() {
			    App.ui.error('Сервер недоступен');
		    }
		});
	}

	function del(self) {
		if (smP.getCount() == 0) {
			App.ui.message('Необходимо выбрать организации!');
			return;
		}

		Ext.Ajax.request({
		    url : 'rest/ViewBonds/Del.do',
		    params : {
		        code : smP.getSelected().data.id_sec,
		        ticker : smP.getSelected().data.deal_name
		    },
		    timeout : 10 * 60 * 1000, // 10 min
		    waitMsg : 'Удаление компании из портфеля',
		    success : function(xhr) {
			    var answer = Ext.decode(xhr.responseText);
			    if (answer.success) {
				    App.ui.message('Отмеченные компании удалены из портфеля!', false, reload);
			    }
		    },
		    failure : function() {
			    App.ui.error('Сервер недоступен');
		    }
		});
	}

	return new Ext.Panel({
	    id : 'ViewBonds-component',
	    title : 'Редактирование облигаций',
	    frame : true,
	    closable : true,
	    layout : 'border',

	    items : [ {
	        region : 'north',
	        autoHeight : true,
	        layout : 'hbox',
	        items : [ {
	            margins : '7 5 5 5',
	            xtype : 'label',
	            style : 'font-weight: bold;',
	            text : 'Название тикера по сделкам:'
	        }, {
	            margins : '5 15 5 5',
	            id : _nameTicker,
	            xtype : 'textfield',
	            allowBlank : false,
	            width : 150
	        } ],

	        buttonAlign : 'left',
	        buttons : [ {
	            width : 150,
	            text : 'Задать соответствие',
	            handler : add
	        }, {
	            width : 150,
	            text : 'Удалить соответствие',
	            handler : del
	        } ]
	    }, {
	        region : 'west',
	        width : 400,
	        xtype : 'grid',
	        title : 'Тикеры блумберга',
	        frame : true,
	        enableHdMenu : false,
	        margins : '5',

	        store : securities,
	        selModel : smS,
	        columns : [ smS, new Ext.grid.RowNumberer({
		        width : 30
	        }), {
	            header : 'BLOOMBERG_CODE',
	            dataIndex : 'security_code'
	        }, {
	            header : 'SHORT_NAME',
	            dataIndex : 'short_name'
	        } ],
	        viewConfig : {
	            forceFit : true,
	            emptyText : 'Записи не найдены'
	        }
	    }, {
	        region : 'center',
	        xtype : 'grid',
	        title : 'Тикеры в портфеле',
	        frame : true,
	        margins : '5',
	        enableHdMenu : false,

	        store : portfolio,
	        selModel : smP,
	        columns : [ smP, new Ext.grid.RowNumberer({
		        width : 30
	        }), {
	            header : 'TICKER',
	            dataIndex : 'ticker',
	            width : 30,
	            sortable : true
	        }, {
	            header : 'DEAL_NAME',
	            dataIndex : 'deal_name',
	            width : 50,
	            sortable : true
	        }, {
	            header : 'DATE_INSERT',
	            dataIndex : 'date_insert',
	            // align : 'center',
	            renderer : App.util.Renderer.date(),
	            width : 40,
	            sortable : true
	        } ],
	        viewConfig : {
	            forceFit : true,
	            emptyText : 'Записи не найдены'
	        }
	    } ]
	});
})();
