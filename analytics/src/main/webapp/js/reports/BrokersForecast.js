/**
 * Прогнозы по брокерам
 */
(function() {

	var _date = Ext.id();
	var _broker = Ext.id();
	var _equity = Ext.id();

	var info = new Ext.data.GroupingStore({
		autoDestroy : true,
		autoLoad : true,
		url : 'rest/BrokersForecast.do',
		reader : new Ext.data.JsonReader({
			// root : 'info',
			fields : [ 'id_sec', 'security_code', 'short_name', 'pivot_group',
					'broker', 'eps1Q', 'eps2Q', 'eps3Q', 'eps4Q', 'eps1CY',
					'eps2CY', 'targetConsensus12m', 'targetConsensus',
					'recommendation', 'period', 'target_date', 'currency',
					'date_insert' ],
		}),
		groupField : 'pivot_group',
		sortInfo : {
			field : 'security_code'
		},
		listeners : App.ui.listenersJsonStore()
	});

	function reload() {
		info.reload({
			params : {
				date : Ext.getCmp(_date).getValue(),
				broker : Ext.getCmp(_broker).getValue(),
				equity : Ext.getCmp(_equity).getValue()
			}
		});
	}

	return new Ext.grid.GridPanel({
		id : 'BrokersForecast-component',
		title : 'Прогнозы по брокерам',
		frame : true,
		closable : true,
		enableHdMenu : false,

		tbar : {
			xtype : 'panel',
			autoHeight : true,
			layout : 'hbox',

			items : [ {
				xtype : 'label',
				style : 'font-weight: bold;',
				margins : '2 5 0 5',
				text : 'Дата загрузки:'
			}, {
				id : _date,
				xtype : 'combo',
				width : 150,
				valueField : 'value',
				displayField : 'display',
				store : new Ext.data.JsonStore({
					autoDestroy : true,
					url : 'rest/BrokersForecast/Dates.do',
					// root : 'info',
					fields : [ 'date', 'value', 'display' ],
					sortInfo : {
						field : 'date',
						direction : 'DESC'
					}
				}),
				triggerAction : 'all',
				editable : false
			}, {
				xtype : 'button',
				text : 'X',
				width : 25,
				handler : function() {
					Ext.getCmp(_date).clearValue();
				}
			}, {
				xtype : 'label',
				style : 'font-weight: bold;',
				margins : '2 5 0 5',
				text : 'Брокер:'
			}, {
				id : _broker,
				xtype : 'combo',
				width : 100,
				valueField : 'id',
				displayField : 'name',
				store : new Ext.data.JsonStore({
					autoDestroy : true,
					url : 'rest/BrokersForecast/Brokers.do',
					// root : 'info',
					fields : [ 'id', 'name' ],
					sortInfo : {
						field : 'name'
					}
				}),
				loadingText : 'Поиск...',
				triggerAction : 'all',
				minChars : 1
			}, {
				xtype : 'button',
				text : 'X',
				width : 25,
				handler : function() {
					Ext.getCmp(_broker).clearValue();
				}
			}, {
				xtype : 'label',
				style : 'font-weight: bold;',
				margins : '2 5 0 5',
				text : 'Компания:'
			}, {
				id : _equity,
				xtype : 'combo',
				width : 150,
				valueField : 'id',
				displayField : 'name',
				store : new Ext.data.JsonStore({
					autoDestroy : true,
					url : 'rest/BrokersForecast/Equities.do',
					// root : 'info',
					fields : [ 'id', 'name' ],
					sortInfo : {
						field : 'name'
					}
				}),
				loadingText : 'Поиск...',
				triggerAction : 'all',
				minChars : 2
			}, {
				xtype : 'button',
				text : 'X',
				width : 25,
				handler : function() {
					Ext.getCmp(_equity).clearValue();
				}
			} ],

			buttonAlign : 'left',
			buttons : [ {
				text : 'Показать',
				handler : reload
			} ]
		},

		store : info,
		columns : [ {
			header : 'security_code',
			dataIndex : 'security_code'
		}, {
			header : 'short_name',
			dataIndex : 'short_name',
			width : 90
		}, {
			header : 'pivot_group',
			dataIndex : 'pivot_group',
			hidden : true,
			renderer : function(v) {
				return v.substr(2);
			}
		}, {
			header : 'broker',
			dataIndex : 'broker'
		}, {
			header : 'EPS1Q',
			dataIndex : 'eps1Q',
			align : 'right',
			width : 60,
			renderer : App.util.Renderer.number()
		}, {
			header : 'EPS2Q',
			dataIndex : 'eps2Q',
			align : 'right',
			width : 60,
			renderer : App.util.Renderer.number()
		}, {
			header : 'EPS3Q',
			dataIndex : 'eps3Q',
			align : 'right',
			width : 60,
			renderer : App.util.Renderer.number()
		}, {
			header : 'EPS4Q',
			dataIndex : 'eps4Q',
			align : 'right',
			width : 60,
			renderer : App.util.Renderer.number()
		}, {
			header : 'EPS1CY',
			dataIndex : 'eps1CY',
			align : 'right',
			width : 70,
			renderer : App.util.Renderer.number()
		}, {
			header : 'EPS2CY',
			dataIndex : 'eps2CY',
			align : 'right',
			width : 70,
			renderer : App.util.Renderer.number()
		}, {
			header : 'TargetConsensus12m',
			dataIndex : 'targetConsensus12m',
			align : 'right',
			renderer : App.util.Renderer.number()
		}, {
			header : 'TargetConsensus',
			dataIndex : 'targetConsensus',
			align : 'right',
			renderer : App.util.Renderer.number()
		}, {
			header : 'recommendation',
			dataIndex : 'recommendation'
		}, {
			header : 'period',
			dataIndex : 'period',
			width : 50
		}, {
			header : 'target_date',
			dataIndex : 'target_date',
			width : 70,
			renderer : App.util.Renderer.date()
		}, {
			header : 'currency',
			dataIndex : 'currency',
			width : 60
		}, {
			header : 'date_insert',
			dataIndex : 'date_insert',
			width : 92,
			renderer : App.util.Renderer.datetime()
		} ],
		view : new Ext.grid.GroupingView({
			forceFit : true,
			emptyText : 'Записи не найдены',
			enableGrouping : true,
			hideGroupedColumn : false,
			showGroupName : false,
			groupTextTpl : '{text} ({[values.rs.length]})'
		})
	});
})();
