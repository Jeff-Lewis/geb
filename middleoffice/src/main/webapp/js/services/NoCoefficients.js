/**
 * Не хватает коэффициентов
 */
(function() {

	var info = new Ext.data.GroupingStore({
		autoDestroy : true,
		autoLoad : true,
		url : 'rest/NoCoefficients.do',
		reader : new Ext.data.JsonReader({
			// root : 'info',
		    fields : [ 'type', 'securityId', 'tradeSystemId', 'securityCode', 'tradeSystem' ],
		}),
		groupField : 'type',
		sortInfo : {
			field : 'type'
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

	return new Ext.grid.GridPanel({
	    id : 'NoCoefficients-component',
	    title : 'Не хватает коэффициентов',
	    closable : true,
	    frame : true,
	    enableHdMenu : false,

	    tbar : {
		    buttons : [ {
		        text : 'Обновить',
		        handler : function() {
			        info.reload();
		        }
		    } ]
	    },

	    store : info,
		view : gv,
	    columns : [ {
	        header : 'type',
	        dataIndex : 'type'
	    }, {
	        header : 'Security Code',
	        dataIndex : 'securityCode'
	    }, {
	        header : 'Trade System',
	        dataIndex : 'tradeSystem'
	    } ],
	    viewConfig : {
	        forceFit : true,
	        emptyText : 'Записи не найдены'
	    }
	});
})();
