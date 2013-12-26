/**
 * Праздники
 */
(function() {

	var ids = '';
	var idw = '';
	var idr = '';
	var uds = '';
	var udw = '';
	var udr = '';

	var _name = Ext.id();

	var info = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : false,
		autoSave : false,
		url : 'rest/Holidays.do',
		// root : 'info',
		fields : [ 'country', 'date', 'name', 'time_start', 'time_stop', 'sms',
				'portfolio' ],
		sortInfo : {
			field : 'date'
		},
		listeners : App.ui.listenersJsonStore()
	});

	var sm = new Ext.grid.RowSelectionModel({
		singleSelect : true
	});

	var infoRU = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : false,
		autoSave : false,
		url : 'rest/Holidays/RU.do',
		// root : 'infoHR',
		fields : [ 'country', 'day_week', 'start', 'stop' ],
		sortInfo : {
			field : 'day_week'
		},
		listeners : App.ui.listenersJsonStore()
	});

	var infoUS = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : false,
		autoSave : false,
		url : 'rest/Holidays/US.do',
		// root : 'infoHU',
		fields : [ 'country', 'day_week', 'start', 'stop' ],
		sortInfo : {
			field : 'day_week'
		},
		listeners : App.ui.listenersJsonStore()
	});

	function reload() {
		info.reload();
		infoRU.reload();
		infoUS.reload();
	}

	function addHoliday(self) {
		menu.showModal(menu, 'dictionary/HolidaysAdd');
	}

	function delHoliday(self) {
		if (sm.getCount() > 0) {
			App.ui.confirm('Удалить помеченные записи?', delHolidayAjax);
		} else {
			App.ui.message('Необходимо выбрать запись  для удаления!');
		}
	}
	function delHolidayAjax() {
		var _data = sm.getSelected().data;
		Ext.Ajax.request({
			url : 'rest/Holidays/Delete.do',
			params : {
				name : _data.country,
				date : _data.date
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Выполняется запрос к Bloomberg',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					App.ui.message('Контакт удален!');
					reload();
				} else if (answer.code == 'login') {
					App.ui.sessionExpired();
				} else {
					App.ui.error(answer.message);
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	function timeSetRus(self) {
		Ext.Ajax.request({
			url : 'rest/Holidays/SetTime.do',
			params : {
				country : 'rus',
				start : ids.substr(1),
				stop : idr.substr(1),
				date : idw.substr(1)
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Установка расписания',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					App.ui.message('Успешно!');
					reload();
				} else if (answer.code == 'login') {
					App.ui.sessionExpired();
				} else {
					App.ui.error(answer.message);
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	function timeSetUsa(self) {
		Ext.Ajax.request({
			url : 'rest/Holidays/SetTime.do',
			params : {
				country : 'usa',
				start : uds.substr(1),
				stop : udr.substr(1),
				date : udw.substr(1)
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Установка расписания',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					App.ui.message('Успешно!');
					reload();
				} else if (answer.code == 'login') {
					App.ui.sessionExpired();
				} else {
					App.ui.error(answer.message);
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	function timeOffset(self) {
		Ext.Ajax.request({
			url : 'rest/Holidays/SetTimeOffset.do',
			params : {
				value : Ext.getCmp(_name).getValue()
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Установка расписания',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					App.ui.message('Успешно!');
					reload();
				} else if (answer.code == 'login') {
					App.ui.sessionExpired();
				} else {
					App.ui.error(answer.message);
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	function rendererBool(v, m, r) {
		if ('1' == v) {
			m.attr = 'style="background: url(images/vwicn082.gif) no-repeat center transparent;"';
		}
	}

	var grid = new Ext.grid.GridPanel({
		region : 'center',
		frame : true,
		enableHdMenu : false,

		tbar : [ {
			text : 'Добавить праздник',
			handler : addHoliday
		}, {
			text : 'Удалить праздник',
			handler : delHoliday
		} ],

		store : info,
		selModel : sm,
		columns : [ new Ext.grid.RowNumberer({
			width : 30
		}), {
			header : 'country',
			dataIndex : 'country',
			width : 30
		}, {
			header : 'holiday_date',
			dataIndex : 'date',
			renderer : App.util.Renderer.date(),
			width : 50
		}, {
			header : 'holiday_time_start',
			dataIndex : 'time_start',
			renderer : App.util.Renderer.time('H:i'),
			width : 40
		}, {
			header : 'holiday_time_stop',
			dataIndex : 'time_stop',
			renderer : App.util.Renderer.time('H:i'),
			width : 40
		}, {
			header : 'holiday_name',
			dataIndex : 'name',
			width : 50
		}, {
			header : 'sms',
			dataIndex : 'sms',
			width : 30,
			renderer : rendererBool
		}, {
			header : 'portfolio',
			dataIndex : 'portfolio',
			width : 30,
			renderer : rendererBool
		} ],
		viewConfig : {
			forceFit : true,
			emptyText : 'Записи не найдены'
		}
	});

	function rendererWeek(value) {
		switch (value) {
		case 1:
			return 'Воскресенье';
		case 2:
			return 'Понедельник';
		case 3:
			return 'Вторник';
		case 4:
			return 'Среда';
		case 5:
			return 'Четверг';
		case 6:
			return 'Пятница';
		case 7:
			return 'Суббота';
		}
	}

	var timeFieldCfg = {
		maskRe : /[:\d]/,
		format : 'H:i',
		queryDelay : 10000
	};

	var gridHR = new Ext.grid.EditorGridPanel({
		frame : true,
		enableHdMenu : false,

		tbar : [ {
			text : 'Установить',
			handler : timeSetRus
		} ],

		store : infoRU,
		columns : [ {
			header : 'country',
			dataIndex : 'country',
			width : 30
		}, {
			header : 'start',
			dataIndex : 'start',
			renderer : App.util.Renderer.time('H:i'),
			width : 40,
			editor : new Ext.form.TimeField(timeFieldCfg)
		}, {
			header : 'stop',
			dataIndex : 'stop',
			renderer : App.util.Renderer.time('H:i'),
			width : 40,
			editor : new Ext.form.TimeField(timeFieldCfg)
		}, {
			header : 'day_week',
			dataIndex : 'day_week',
			width : 70,
			renderer : rendererWeek
		} ],
		viewConfig : {
			forceFit : true,
			emptyText : 'Записи не найдены'
		},
		listeners : {
			afteredit : function(e) {
				if ((e.field == 'start') || (e.field == 'stop')) {
					ids += ',' + e.record.get('start');
					idr += ',' + e.record.get('stop');
					idw += ',' + e.record.get('day_week');
				}
			}
		}
	});

	var gridHU = new Ext.grid.EditorGridPanel({
		frame : true,
		enableHdMenu : false,

		tbar : [ {
			text : 'Установить',
			handler : timeSetUsa
		} ],

		store : infoUS,
		columns : [ {
			header : 'country',
			dataIndex : 'country',
			width : 30
		}, {
			header : 'start',
			dataIndex : 'start',
			renderer : App.util.Renderer.time('H:i'),
			width : 40,
			editor : new Ext.form.TimeField(timeFieldCfg)
		}, {
			header : 'stop',
			dataIndex : 'stop',
			renderer : App.util.Renderer.time('H:i'),
			width : 40,
			editor : new Ext.form.TimeField(timeFieldCfg)
		}, {
			header : 'day_week',
			dataIndex : 'day_week',
			width : 70,
			renderer : rendererWeek
		} ],
		viewConfig : {
			forceFit : true,
			emptyText : 'Записи не найдены'
		},
		listeners : {
			afteredit : function(e) {
				if ((e.field == 'start') || (e.field == 'stop')) {
					uds += ',' + e.record.get('start');
					udr += ',' + e.record.get('stop');
					udw += ',' + e.record.get('day_week');
				}
			}
		}
	});

	return new Ext.Panel({
		id : 'Holidays-component',
		title : 'Справочник праздников',
		frame : false,
		closable : true,
		layout : 'border',

		items : [ {
			region : 'north',
			border : true,
			baseCls : 'x-plain',
			xtype : 'panel',
			autoHeight : true,
			padding : 5,
			layout : 'hbox',

			items : [ {
				margins : '2 5 0 0',
				xtype : 'label',
				style : 'font-weight: bold;',
				text : 'Смещение времени USA относительно Москвы:'
			}, {
				margins : '0 15 0 0',
				id : _name,
				xtype : 'numberfield',
				align : 'right',
				width : 30
			}, {
				xtype : 'button',
				text : 'Установить',
				handler : timeOffset
			} ]
		}, grid, {
			region : 'south',
			xtype : 'container',
			height : 220,
			layout : 'hbox',
			layoutConfig : {
				align : 'stretch'
			},
			defaults : {
				width : 400
			},
			items : [ gridHR, gridHU ]
		} ],
		loadData : function(data) {
			ids = '';
			idw = '';
			idr = '';
			uds = '';
			udw = '';
			udr = '';
			Ext.getCmp(_name).setValue(data.offset);
		},
		listeners : {
			show : function(grid) {
				setTimeout(reload, 0);
			}
		}
	});
})();
