/**
 * Праздники
 */
(function() {

	var info = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : true,
		autoSave : false,
		url : 'rest/Holidays.do',
		//root : 'info',
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

	function cnvtTime(v, rec) {
		return Ext.util.Format.substr(v, 0, 5);
	}

	var infoHR = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : true,
		autoSave : false,
		url : 'rest/Holidays/Time/rus.do',
		// root : 'infoHR',
		fields : [ 'country', 'day_week', {
			name : 'start',
			convert : cnvtTime
		}, {
			name : 'stop',
			convert : cnvtTime
		} ],
		sortInfo : {
			field : 'day_week'
		}
	});

	var infoHU = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : true,
		autoSave : false,
		url : 'rest/Holidays/Time/usa.do',
		// root : 'infoHU',
		fields : [ 'country', 'day_week', {
			name : 'start',
			convert : cnvtTime
		}, {
			name : 'stop',
			convert : cnvtTime
		} ],
		sortInfo : {
			field : 'day_week'
		}
	});

	var infoCO = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : true,
		autoSave : false,
		url : 'rest/Holidays/TimeOffset.do',
		//root : 'infoCO',
		fields : [ 'id', 'country', 'offset', {
			name : 'start',
			convert : cnvtTime
		}, {
			name : 'stop',
			convert : cnvtTime
		} ],
		sortInfo : {
			field : 'country'
		}
	});

	function addHoliday() {
		showModal('dictionary/HolidaysAdd');
	}

	function delHoliday() {
		if (sm.getCount() > 0) {
			var _data = sm.getSelected().data;
			App.ui.confirm('Удалить ' + _data.name + '(' + _data.date + ')?',
					delHolidayAjax);
		} else {
			App.ui.message('Необходимо выбрать запись  для удаления!');
		}
	}
	function delHolidayAjax() {
		var _data = sm.getSelected().data;
		Ext.Ajax.request({
			url : 'rest/Holidays/Delete.do',
			params : {
				country : _data.country,
				date : _data.date
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Выполняется запрос к Bloomberg',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					info.reload();
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	function setTime(_store) {
		var _v = [];
		_store.each(function(r) {
			if (r.dirty) {
				var d = r.data;
				_v.push(d.country + ';' + d.day_week + ';' + d.start + ';'
						+ d.stop);
				r.commit(true);
			}
			return true;
		});

		if (_v.length == 0) {
			return;
		}

		Ext.Ajax.request({
			url : 'rest/Holidays/Time.do',
			params : {
				values : _v,
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Установка расписания',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					_store.reload();
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	function setTimeOffset() {
		var _v = [];
		infoCO.each(function(r) {
			if (r.dirty) {
				var d = r.data;
				_v.push(d.id + ';' + d.country + ';' + d.offset + ';' + d.start
						+ ':00;' + d.stop + ':00');
				r.commit(true);
			}
			return true;
		});

		if (_v.length == 0) {
			return;
		}

		Ext.Ajax.request({
			url : 'rest/Holidays/TimeOffset.do',
			params : {
				values : _v
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Сохранение.',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					infoCO.reload();
				} else {
					e.record.data.offset = e.originalValue;
					e.grid.getView().refresh();
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

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
		hideTrigger : true
	};

	var gridHR = new Ext.grid.EditorGridPanel({
		frame : true,
		enableHdMenu : false,

		tbar : [ {
			text : 'Установить',
			handler : function() {
				setTime(infoHR);
			}
		} ],

		store : infoHR,
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
			afteredit : function() {
				setTime(infoHR);
			}
		}
	});

	var gridHU = new Ext.grid.EditorGridPanel({
		frame : true,
		enableHdMenu : false,

		tbar : [ {
			text : 'Установить',
			handler : function() {
				setTime(infoHU);
			}
		} ],

		store : infoHU,
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
			afteredit : function() {
				setTime(infoHU);
			}
		}
	});

	var gridCO = new Ext.grid.EditorGridPanel({
		frame : true,
		enableHdMenu : false,

		tbar : [ {
			text : 'Установить',
			handler : setTimeOffset
		} ],

		store : infoCO,
		columns : [ {
			header : 'Страна',
			dataIndex : 'country'
		}, {
			xtype : 'numbercolumn',
			header : 'Разница времени, ч',
			dataIndex : 'offset',
			format : '0',
			editor : {
				xtype : 'numberfield',
				allowDecimals : false,
				minValue : -23,
				maxValue : 23
			}
		}, {
			header : 'Начало торгов',
			dataIndex : 'start',
			renderer : App.util.Renderer.time('H:i'),
			editor : new Ext.form.TimeField(timeFieldCfg)
		}, {
			header : 'Окончание торгов',
			dataIndex : 'stop',
			renderer : App.util.Renderer.time('H:i'),
			editor : new Ext.form.TimeField(timeFieldCfg)
		} ],
		viewConfig : {
			forceFit : true,
			emptyText : 'Записи не найдены'
		},
		listeners : {
			afteredit : setTimeOffset
		}
	});

	function renderFlag(value, meta, record) {
		if ('1' == value) {
			meta.attr = 'style="background: url(images/vwicn082.gif) no-repeat center transparent;"';
		}
	}

	return new Ext.Panel({
		id : 'Holidays-component',
		title : 'Справочник праздников',
		frame : false,
		closable : true,
		layout : 'border',

		items : [ {
			region : 'center',
			xtype : 'grid',
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
				header : 'time_start',
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
				renderer : renderFlag
			}, {
				header : 'portfolio',
				dataIndex : 'portfolio',
				width : 30,
				renderer : renderFlag
			} ],
			viewConfig : {
				forceFit : true,
				emptyText : 'Записи не найдены'
			}
		}, {
			region : 'south',
			xtype : 'container',
			height : 220,
			layout : 'hbox',
			layoutConfig : {
				align : 'stretch'
			},
			defaults : {
				width : 350
			},
			items : [ gridHR, gridHU, gridCO ]
		} ],

		refresh : function() {
			info.reload();
		}
	});
})();
