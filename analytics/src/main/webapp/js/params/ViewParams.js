/**
 * Справочник параметров
 */
(function() {

	var store = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : true,
		url : 'rest/ViewParams.do',
		// root : 'info',
		fields : [ 'param_id', 'blm_id', 'code', 'name' ],
		sortInfo : {
			field : 'code'
		},
		listeners : App.ui.listenersJsonStore()
	});

	function rendererCode(value, p, r) {
		var id = r.data.blm_id;
		// TODO заменить компонент
		var onclick = "showPanel('params/ViewParamsInfo',"
				+ " 'rest/ViewParams/" + id + ".do');";
		return '<b><a onclick="' + onclick + '" href="#">' + value + '</a></b>';
	}

	return new Ext.grid.GridPanel({
		id : 'ViewParams-component',
		title : 'Bloomberg Params',
		frame : true,
		closable : true,
		autoScroll : true,
		enableHdMenu : false,

		store : store,
		columns : [ {
			header : 'param_id',
			dataIndex : 'param_id',
			sortable : true,
			width : 50,
			css : 'color: #0099ff;'
		}, {
			header : 'blm_id',
			dataIndex : 'blm_id',
			width : 50
		}, {
			header : 'code',
			dataIndex : 'code',
			sortable : true,
			width : 150,
			renderer : rendererCode
		}, {
			header : 'name',
			dataIndex : 'name',
			width : 200
		} ],
		viewConfig : {
			forceFit : true,
			emptyText : 'Записи не найдены'
		}
	});
})();
