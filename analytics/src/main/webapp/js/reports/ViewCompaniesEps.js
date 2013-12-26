/**
 * EPS по компаниям
 */
(function() {

	var info = new Ext.data.GroupingStore({
		autoDestroy : true,
		autoLoad : true,
		url : 'rest/ViewCompaniesEps.do',
		reader : new Ext.data.JsonReader({
			// root : 'info',
			fields : [ 'id_sec', 'sector', 'eps', 'related_security',
					'security_code' ],
		}),
		groupField : 'sector',
		sortInfo : {
			field : 'security_code'
		},
		listeners : App.ui.listenersJsonStore()
	});

	var gv = new Ext.grid.GroupingView({
		forceFit : true,
		emptyText : 'Записи не найдены',
		enableGrouping : true,
		hideGroupedColumn : true,
		showGroupName : false,
		groupTextTpl : '{text} ({[values.rs.length]})'
	});

	return new Ext.grid.GridPanel({
		id : 'ViewCompaniesEps-component',
		title : 'EPS по компаниям',
		frame : true,
		closable : true,
		enableHdMenu : false,

		store : info,
		view : gv,
		columns : [ {
			header : 'security_code',
			dataIndex : 'security_code',
			width : 100,
			sortable : true
		}, {
			header : 'related_security',
			dataIndex : 'related_security',
			width : 150,
			sortable : true
		}, {
			header : 'EPS',
			dataIndex : 'eps',
			width : 50
		}, {
			header : 'Сектор',
			dataIndex : 'sector',
			width : 100,
			renderer : function(value) {
				return value.substr(2);
			}
		} ]
	});
})();
