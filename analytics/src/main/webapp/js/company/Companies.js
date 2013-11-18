/**
 * Список компаний
 */
(function() {

	var info = new Ext.data.GroupingStore({
		autoDestroy : true,
		autoLoad : true,
		url : 'rest/Companies.do',
		reader : new Ext.data.JsonReader({
			// root : 'info',
			fields : [ 'id_sec', 'id_isin', 'security_code', 'security_name',
					'currency', 'indstry_grp' ]
		}),
		groupField : 'indstry_grp',
		sortInfo : {
			field : 'security_code'
		},
		listeners : App.ui.listenersJsonStore()
	});

	var gv = new Ext.grid.GroupingView({
		forceFit : true,
		emptyText : 'Записи не найдены',
		enableGrouping : true,
		hideGroupedColumn : false,
		showGroupName : false,
		groupTextTpl : '{text} ({[values.rs.length]})'
	});

	function rSC(value, p, r) {
		var id = r.data.id_sec;
		var onclick = "menu.submitDataRequest(menu, 'company/CompaniesInfo',"
				+ " 'rest/Companies/" + id + ".do');";
		return '<b><a onclick="' + onclick + '" href="#">' + value + '</a></b>';
	}

	return new Ext.grid.GridPanel({
		id : 'Companies-component',
		title : 'Список компаний',
		frame : true,
		closable : true,
		enableHdMenu : false,

		tbar : [ {
			text : 'Обновить',
			handler : function() {
				info.reload();
			}
		}, {
			text : 'Группировать',
			menu : [ {
				text : 'по Сектору',
				handler : function() {
					gv.enableGrouping = true;
					gv.refresh();
				}
			}, {
				text : 'Не группировать',
				handler : function() {
					gv.enableGrouping = false;
					gv.refresh();
				}
			} ]
		} ],

		store : info,
		view : gv,
		columns : [ {
			header : 'ISIN',
			dataIndex : 'id_isin',
			sortable : true,
			width : 50,
			css : 'color: #0099ff;'
		}, {
			header : 'Название компании',
			dataIndex : 'security_name',
			width : 100
		}, {
			header : 'Код Блумберг',
			dataIndex : 'security_code',
			sortable : true,
			width : 100,
			renderer : rSC
		}, {
			header : 'Валюта расчёта',
			dataIndex : 'currency',
			width : 50
		}, {
			header : 'Сектор',
			dataIndex : 'indstry_grp',
			width : 100,
			renderer : function(value) {
				return value.substr(2);
			}
		} ],

		listeners : {
		// show : function(grid) {
		// info.reload();
		// }
		}
	});
})();
