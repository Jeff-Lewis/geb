/**
 * Журнал действий пользователя
 */
(function() {

	var _startD = Ext.id();
	var _stopD = Ext.id();

	var info = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : false,
		url : 'rest/LogUserActions.do',
		// root : 'info',
		fields : [ 'id', 'userLogin', 'userName', 'userIp', 'userEmail', 'command', 'dateInsert' ],
		listeners : App.ui.listenersJsonStore()
	});

	function showLog() {
		var _start = App.util.Format.dateYMD(Ext.getCmp(_startD).getValue());
		var _stop = App.util.Format.dateYMD(Ext.getCmp(_stopD).getValue());
		info.reload({
			params : {
				begin : _start,
				end : _stop
			},
		});
	}

	return new Ext.Panel({
		id : 'LogUserActions-component',
		title : 'Журнал действий пользователя',
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
				text : 'Дата старта:'
			}, {
				id : _startD,
				xtype : 'datefield',
				format : 'd.m.Y',
				allowBlank : false,
				width : 100,
				value : new Date()
			}, {
				xtype : 'label',
				style : 'font-weight: bold;',
				margins : '2 5 0 5',
				text : 'Дата стопа:'
			}, {
				id : _stopD,
				xtype : 'datefield',
				format : 'd.m.Y',
				allowBlank : false,
				width : 100,
				value : new Date()
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
	            header : 'Login',
	            dataIndex : 'userLogin',
	            width : 50
	        }, {
	            header : 'Name',
	            dataIndex : 'userName',
	            width : 50
	        }, {
	            header : 'IP',
	            dataIndex : 'userIp',
	            width : 50
	        }, {
	            header : 'E-Mail',
	            dataIndex : 'userEmail',
	            width : 100
	        }, {
	            header : 'Command',
	            dataIndex : 'command',
	            width : 150
	        }, {
	            header : 'Date insert',
	            dataIndex : 'dateInsert',
	            width : 30,
	            renderer : App.util.Renderer.datetime()
	        } ],

			viewConfig : {
				forceFit : true,
				emptyText : 'Записи не найдены'
			}
		} ]
	});
})();
