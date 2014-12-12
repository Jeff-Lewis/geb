/**
 * Не хватает коэффициентов
 */
(function() {

	var info = new Ext.data.JsonStore({
	    autoDestroy : true,
	    autoLoad : true,
	    url : 'rest/NoCoefficients.do',
	    // root : 'info',
	    fields : [ 'securityId', 'tradeSystemId', 'securityCode', 'tradeSystem' ],
	    listeners : App.ui.listenersJsonStore()
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
	    columns : [ {
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
