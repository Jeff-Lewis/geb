/**
 * Покрытие брокеров
 */
(function() {

	var info = new Ext.data.GroupingStore({
		autoDestroy : true,
		autoLoad : true,
		url : 'rest/BrokersCoverage.do',
		reader : new Ext.data.JsonReader({
			// root : 'info',
			fields : [ 'id_sec', 'security_code', 'short_name', 'pivot_group',
					'credit_Suisse', 'goldman_Sachs', 'jp_Morgan', 'ubs',
					'merrill_Lynch', 'morgan_Stanley', 'deutsche_Bank' ],
		}),
		groupField : 'pivot_group',
		sortInfo : {
			field : 'security_code'
		},
		listeners : App.ui.listenersJsonStore()
	});

	function changeCoverage(grid, rowIndex, columnIndex, e) {
		if (columnIndex < 3) {
			return;
		}
		var r = grid.getStore().getAt(rowIndex);
		var di = grid.getColumnModel().getDataIndex(columnIndex);
		var val = r.get(di) ? 0 : 1;

		Ext.Ajax.request({
			url : 'rest/BrokersCoverage/Change.do',
			params : {
				id : r.data.id_sec,
				broker : di,
				value : val
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Изменение данных.',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					r.set(di, val);
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	return new Ext.grid.GridPanel({
		id : 'BrokersCoverage-component',
		title : 'Покрытие брокеров',
		frame : true,
		closable : true,
		enableHdMenu : false,

		store : info,
		selModel : new Ext.grid.CellSelectionModel(),
		columns : [ {
			header : 'security_code',
			dataIndex : 'security_code'
		}, {
			header : 'short_name',
			dataIndex : 'short_name',
			width : 150
		}, {
			header : 'pivot_group',
			dataIndex : 'pivot_group',
			hidden : true,
			renderer : function(v) {
				return v.substr(2);
			}
		}, {
			header : 'Credit Suisse',
			dataIndex : 'credit_Suisse',
			align : 'center',
			width : 100,
			renderer : App.util.Renderer.bool()
		}, {
			header : 'Goldman Sachs',
			dataIndex : 'goldman_Sachs',
			align : 'center',
			width : 100,
			renderer : App.util.Renderer.bool()
		}, {
			header : 'JP Morgan',
			dataIndex : 'jp_Morgan',
			align : 'center',
			width : 100,
			renderer : App.util.Renderer.bool()
		}, {
			header : 'UBS',
			dataIndex : 'ubs',
			align : 'center',
			width : 100,
			renderer : App.util.Renderer.bool()
		}, {
			header : 'Merrill Lynch',
			dataIndex : 'merrill_Lynch',
			align : 'center',
			width : 100,
			renderer : App.util.Renderer.bool()
		}, {
			header : 'Morgan Stanley',
			dataIndex : 'morgan_Stanley',
			align : 'center',
			width : 100,
			renderer : App.util.Renderer.bool()
		}, {
			header : 'Deutsche Bank',
			dataIndex : 'deutsche_Bank',
			align : 'center',
			width : 100,
			renderer : App.util.Renderer.bool()
		} ],
		view : new Ext.grid.GroupingView({
			forceFit : true,
			emptyText : 'Записи не найдены',
			enableGrouping : true,
			hideGroupedColumn : false,
			showGroupName : false,
			groupTextTpl : '{text} ({[values.rs.length]})'
		}),
		listeners : {
			celldblclick : changeCoverage
		}
	});
})();
