/**
 * Журнал изменений справочника контактов
 */
(function() {
	var _startD = Ext.id();
	var _startT = Ext.id();
	var _stopD = Ext.id();
	var _stopT = Ext.id();

	var storeRes = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : false,
		url : 'rest/LogContacts.do',
		// root : 'answer',
		fields : [ 'number', 'name', 'nid', 'cid', 'value', 'gid', 'gname',
				'action', 'date' ],
		listeners : App.ui.listenersJsonStore()
	});

	function showLog() {
		var _start = App.util.Format.dateYMD(Ext.getCmp(_startD).getValue())
				+ 'T' + Ext.getCmp(_startT).getValue();
		var _stop = App.util.Format.dateYMD(Ext.getCmp(_stopD).getValue())
				+ 'T' + Ext.getCmp(_stopT).getValue();
		storeRes.reload({
			params : {
				start : _start,
				stop : _stop
			},
		});
	}

	return new Ext.Panel({
		id : 'ViewContacts-component',
		title : 'Журнал изменения контактов',
		closable : true,
		frame : true,
		layout : 'border',

		items : [ {
			region : 'north',
			xtype : 'panel',
			autoHeight : true,
			padding : 5,
			layout : 'hbox',

			buttonAlign : 'left',
			buttons : [ {
				text : 'Просмотр',
				handler : showLog
			} ],

			items : [ {
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
			} ]
		}, {
			region : 'center',
			xtype : 'grid',
			frame : true,
			enableHdMenu : false,

			store : storeRes,
			columns : [ {
				header : 'number',
				dataIndex : 'number',
				width : 20
			}, {
				header : 'name',
				dataIndex : 'name',
				width : 50
			}, {
				header : 'id',
				dataIndex : 'nid',
				width : 20
			}, {
				header : 'cid',
				dataIndex : 'cid',
				width : 20
			}, {
				header : 'value',
				dataIndex : 'value',
				width : 25
			}, {
				header : 'gid',
				dataIndex : 'gid',
				width : 20
			}, {
				header : 'gname',
				dataIndex : 'gname',
				width : 50
			}, {
				header : 'action',
				dataIndex : 'action',
				width : 100
			}, {
				header : 'date',
				dataIndex : 'date',
				renderer : App.util.Renderer.datetime()
			} ],
			viewConfig : {
				forceFit : true,
				emptyText : 'Записи не найдены'
			}
		} ]
	});
})();
