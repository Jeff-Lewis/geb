/**
 * Справочник пользователей - Права пользователя
 */
(function() {

	var idItem = 0;

	var info = new Ext.data.JsonStore({
	    autoDestroy : true,
	    autoLoad : false,
	    url : 'rest/DictUsers/0/Info.do',
	    // root : 'info',
	    fields : [ 'object', 'permission' ],
	    sortInfo : {
		    field : 'object'
	    },
	    listeners : App.ui.listenersJsonStore()
	});

	var sm = new Ext.grid.RowSelectionModel({
		singleSelect : true
	});

	return new Ext.grid.GridPanel({
	    id : 'Users-Info-component',
	    title : 'Права пользователя',
	    frame : true,
	    closable : true,
	    enableHdMenu : false,

	    store : info,
	    selModel : sm,
	    columns : [ new Ext.grid.RowNumberer({
		    width : 30
	    }), {
	        header : 'Объект',
	        dataIndex : 'object',
	        width : 50
	    }, {
	        header : 'Права',
	        dataIndex : 'permission',
	        width : 100
	    } ],
	    viewConfig : {
	        forceFit : true,
	        emptyText : 'Записи не найдены'
	    },

	    loadData : function(data) {
		    idItem = data.item.id;
		    this.setTitle('Права пользователя: ' + data.item.name);
		    info.proxy.setUrl('rest/DictUsers/' + idItem + '/Info.do', true);
		    info.reload();
	    }
	});
})();
