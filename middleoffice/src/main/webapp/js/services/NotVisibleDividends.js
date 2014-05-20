/**
 * Нет настроек для дивидендов
 */
(function() {

	var info = new Ext.data.JsonStore({
	    autoDestroy : true,
	    autoLoad : true,
	    url : 'rest/NotVisibleDividends.do',
	    // root : 'info',
	    fields : [ 'security_code', 'short_name', 'client', 'fund', 'broker', 'account', 'currency',
	            'record_date', 'quantity', 'dividend_per_share', 'receive_date' ],
	    listeners : App.ui.listenersJsonStore()
	});

	return new Ext.grid.GridPanel({
	    id : 'NotVisibleDividends-component',
	    title : 'Нет настроек для дивидендов',
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
	        header : 'SECURITY_CODE',
	        dataIndex : 'security_code'
	    }, {
	        header : 'SHORT_NAME',
	        dataIndex : 'short_name'
	    }, {
	        header : 'CLIENT',
	        dataIndex : 'client'
	    }, {
	        header : 'FUND',
	        dataIndex : 'fund'
	    }, {
	        header : 'BROKER',
	        dataIndex : 'broker'
	    }, {
	        header : 'ACCOUNT',
	        dataIndex : 'account'
	    }, {
	        header : 'CURRENCY',
	        dataIndex : 'currency'
	    }, {
	        header : 'RECORD_DATE',
	        dataIndex : 'record_date',
	        // align : 'center',
	        renderer : App.util.Renderer.date()
	    }, {
	        header : 'QUANTITY',
	        dataIndex : 'quantity',
	        align : 'right',
	        renderer : App.util.Renderer.number(0)
	    }, {
	        header : 'DIVIDEND_PER_SHARE',
	        dataIndex : 'dividend_per_share',
	        align : 'right',
	        renderer : App.util.Renderer.number(7)
	    }, {
	        header : 'RECEIVE_DATE',
	        dataIndex : 'receive_date',
	        // align : 'center',
	        renderer : App.util.Renderer.date()
	    } ],
	    viewConfig : {
	        forceFit : true,
	        emptyText : 'Записи не найдены'
	    }
	});
})();
