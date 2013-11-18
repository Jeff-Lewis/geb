/**
 * Журнал отправки сообщений
 */
(function() {

	var _type = Ext.id();
	var _startD = Ext.id();
	var _startT = Ext.id();
	var _stopD = Ext.id();
	var _stopT = Ext.id();

	var info = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : false,
		url : 'rest/LogMessages.do',
		// root : 'info',
		fields : [ 'sl_id', 'name', 'value', 'text', 'status', 'date_insert' ],
		listeners : App.ui.listenersJsonStore()
	});

	function showLog() {
		var _start = App.util.Format.dateYMD(Ext.getCmp(_startD).getValue())
				+ 'T' + Ext.getCmp(_startT).getValue();
		var _stop = App.util.Format.dateYMD(Ext.getCmp(_stopD).getValue())
				+ 'T' + Ext.getCmp(_stopT).getValue();
		info.reload({
			params : {
				type : Ext.getCmp(_type).getValue(),
				start : _start,
				stop : _stop
			},
		});
	}

	return new Ext.Panel({
		id : 'ViewMessages-component',
		title : 'Журнал отправки сообщений',
		closable : true,
		frame : true,
		layout : 'border',

		items : [ {
			region : 'north',
			xtype : 'panel',
			border : false,
			autoHeight : true,
			padding : 5,
			layout : 'hbox',

			items : [ {
				xtype : 'label',
				style : 'font-weight: bold;',
				margins : '2 5 0 5',
				text : 'Тип сообщения:'
			}, {
				id : _type,
				xtype : 'combo',
				mode : 'local',
				width : 70,
				store : [ 'sms', 'email' ],
				allowBlank : false,
				emptyText : 'Тип',
				triggerAction : 'all',
				editable : false,
				value : 'sms'
			}, {
				xtype : 'label',
				style : 'font-weight: bold;',
				margins : '2 5 0 5',
				text : 'Дата/время старта:'
			}, {
				id : _startD,
				xtype : 'datefield',
				format : 'd.m.Y',
				allowBlank : false,
				width : 100,
				value : new Date()
			}, {
				id : _startT,
				xtype : 'timefield',
				allowBlank : false,
				width : 60,
				format : 'H:i',
				value : '00:00'
			}, {
				xtype : 'label',
				style : 'font-weight: bold;',
				margins : '2 5 0 5',
				text : 'Дата/время стопа:'
			}, {
				id : _stopD,
				xtype : 'datefield',
				format : 'd.m.Y',
				allowBlank : false,
				width : 100,
				value : new Date()
			}, {
				id : _stopT,
				xtype : 'timefield',
				allowBlank : false,
				width : 60,
				format : 'H:i',
				value : '23:59'
			} ],

			buttonAlign : 'left',
			buttons : [ {
				text : 'Просмотр',
				handler : showLog
			} ]
		}, {
			region : 'center',
			xtype : 'grid',
			frame : true,
			enableHdMenu : false,

			store : info,
			columns : [ {
				header : 'sl_id',
				dataIndex : 'sl_id',
				width : 20
			}, {
				header : 'name',
				dataIndex : 'name',
				width : 50
			}, {
				header : 'value',
				dataIndex : 'value',
				width : 25
			}, {
				header : 'text',
				dataIndex : 'text',
				width : 25
			}, {
				header : 'status',
				dataIndex : 'status',
				width : 15
			}, {
				header : 'date_insert',
				dataIndex : 'date_insert',
				width : 30,
				renderer : App.util.Renderer.datetime('d-m-Y H:i:s')
			} ],

			viewConfig : {
				forceFit : true,
				emptyText : 'Записи не найдены'
			},

			listeners : {
				rowdblclick : function(grid, rowIndex, e) {
					var text = grid.getStore().getAt(rowIndex).data.text;
					text = Ext.util.Format.htmlEncode(text);
					text = Ext.util.Format.nl2br(text);
					App.ui.message(text);
				}
			}
		} ]
	});
})();
