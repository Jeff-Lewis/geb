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

	function showHolidays() {
		menu.submitDataRequest(menu, 'dictionary/Holidays', 'rest/Holidays.do');
	}

	var store = new Ext.data.JsonStore(
			{
				autoDestroy : true,
				autoLoad : false,
				autoSave : false,
				root : 'info',
				fields : [ 'country', 'holiday_date', 'holiday_name',
						'holiday_time_start', 'holiday_time_stop', 'sms',
						'portfolio' ],
				sortInfo : {
					field : 'holiday_date'
				}
			});

	var sm = new Ext.grid.RowSelectionModel({
		singleSelect : true
	});

	var storeRU = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : false,
		autoSave : false,
		root : 'infoHR',
		fields : [ 'country', {
			name : 'day_week',
			type : 'int'
		}, 'start', 'stop' ],
		sortInfo : {
			field : 'day_week'
		}
	});

	var storeHU = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : false,
		autoSave : false,
		root : 'infoHU',
		fields : [ 'country', {
			name : 'day_week',
			type : 'int'
		}, 'start', 'stop' ],
		sortInfo : {
			field : 'day_week'
		}
	});

	var pics = {
		p82 : 'style="background: url(img/vwicn082.gif) no-repeat center transparent;"'
	};

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
				date : _data.holiday_date
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Выполняется запрос к Bloomberg',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					App.ui.message('Контакт удален!');
					showHolidays();
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
					showHolidays();
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
					showHolidays();
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
					showHolidays();
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

		store : store,
		selModel : sm,
		columns : [ new Ext.grid.RowNumberer({
			width : 30
		}), {
			header : 'country',
			dataIndex : 'country',
			width : 30
		}, {
			header : 'holiday_date',
			dataIndex : 'holiday_date',
			renderer : App.util.Renderer.date(),
			width : 50
		}, {
			header : 'holiday_time_start',
			dataIndex : 'holiday_time_start',
			renderer : App.util.Renderer.time('H:i'),
			width : 40
		}, {
			header : 'holiday_time_stop',
			dataIndex : 'holiday_time_stop',
			renderer : App.util.Renderer.time('H:i'),
			width : 40
		}, {
			header : 'holiday_name',
			dataIndex : 'holiday_name',
			width : 50
		}, {
			header : 'sms',
			dataIndex : 'sms',
			width : 30,
			renderer : function(value, meta, record) {
				if (value == '1') {
					meta.attr = pics.p82;
				}
			}
		}, {
			header : 'portfolio',
			dataIndex : 'portfolio',
			width : 30,
			renderer : function(value, meta, record) {
				if (value == '1') {
					meta.attr = pics.p82;
				}
			}
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

		store : storeRU,
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

		store : storeHU,
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
		frame : false,
		closable : true,
		layout : 'border',
		title : 'Справочник праздников',
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
			// store.loadData(data);
			// storeRU.loadData(data);
			// storeHU.loadData(data);
		}
	});
})();
