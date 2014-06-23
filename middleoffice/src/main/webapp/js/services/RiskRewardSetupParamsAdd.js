/**
 * Задание параметров отчёта RR - Добавление
 */
(function() {

	var _slip = Ext.id();
	var _riskTheor = Ext.id();
	var _riskPract = Ext.id();
	var _discount = Ext.id();
	var _dateBegin = Ext.id();

	var sm = new Ext.grid.CheckboxSelectionModel();

	function add() {
		var db = Ext.getCmp(_dateBegin);
		if (!db.validate()) {
			App.ui.message('Необходимо заполнить дату!');
			return;
		}

		if (sm.getCount() == 0) {
			App.ui.message('Необходимо выбрать BLOOMBERG_CODE!');
			return;
		}

		var ids = [];
		sm.each(function(item) {
			ids.push(item.data.id_sec);
			return true;
		});

		Ext.Ajax.request({
			url : 'rest/RiskRewardSetupParams/Add.do',
			params : {
				securities : ids,
				slip : Ext.getCmp(_slip).getValue(),
				riskTheor : Ext.getCmp(_riskTheor).getValue(),
				riskPract : Ext.getCmp(_riskPract).getValue(),
				discount : Ext.getCmp(_discount).getValue(),
				dateBegin : App.util.Format.dateYMD(db.getValue())
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Сохранение изменений',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					Ext.getCmp('RiskRewardSetupParams-component').refresh();
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	var storeSec = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : true,
		url : 'rest/RiskRewardSetupParams/Add/Securities.do',
		// root : 'info',
		fields : [ 'id_sec', 'security_code', 'short_name', 'type_id' ],
		sortInfo : {
			field : 'security_code'
		},
		listeners : App.ui.listenersJsonStore()
	});

	var filter = new Ext.form.ComboBox({
		width : 100,
		displayField : 'name',
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'rest/RiskRewardSetupParams/Add/Filter.do',
			// root : 'info',
			fields : [ 'name' ]
		}),
		editable : false,
		allowBlank : true,
		loadingText : 'Поиск...',
		triggerAction : 'all',
		listeners : {
			select : function(combo, record, index) {
				filterUpdate();
			}
		},
		value : 'Все'
	});

	var security = new Ext.form.ComboBox({
		width : 150,
		displayField : 'name',
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'rest/RiskRewardSetupParams/Add/FilterSecurities.do',
			// root : 'info',
			fields : [ 'id', 'name' ],
			sortInfo : {
				field : 'name'
			}
		}),
		loadingText : 'Поиск...',
		triggerAction : 'all',
		minChars : 2,
		typeAhead : false,
		listeners : {
			select : function(combo, record, index) {
				combo.setValue(record.data.name);
				filterUpdate();
			}
		}
	});

	function filterUpdate() {
		storeSec.reload({
			params : {
				filter : filter.getValue(),
				security : App.Combo.getValueId(security)
			}
		});
	}

	var tbarFilter = [ 'Фильтр', filter, {
		text : 'X',
		handler : function() {
			filter.setValue(filter.originalValue);
			filterUpdate();
		}
	}, ' ', 'Инструмент', security, {
		text : 'X',
		handler : function() {
			security.setValue(security.originalValue);
			filterUpdate();
		}
	} ];

	return new Ext.Panel({
		id : 'RiskRewardSetupParamsAdd-component',
		layout : 'border',
		frame : true,
		closable : true,
		title : 'Добавление параметров отчёта RR',

		items : [ {
			region : 'west',
			xtype : 'form',
			padding : 20,
			width : 250,
			labelAlign : 'top',
			defaults : {
				width : 200
			},

			items : [ {
				id : _slip,
				xtype : 'numberfield',
				fieldLabel : 'Слипыч'
			}, {
				id : _riskTheor,
				xtype : 'numberfield',
				fieldLabel : 'Риск теоретической ликвидности'
			}, {
				id : _riskPract,
				xtype : 'numberfield',
				fieldLabel : 'Риск практической ликвидности'
			}, {
				id : _discount,
				xtype : 'numberfield',
				fieldLabel : 'Дисконт'
			}, {
				id : _dateBegin,
				xtype : 'datefield',
				format : 'd.m.Y',
				fieldLabel : 'Начало периода'
			}, {
				xtype : 'button',
				text : 'Добавить',
				handler : add
			} ]
		}, {
			region : 'center',
			xtype : 'grid',
			frame : true,
			enableHdMenu : false,

			tbar : tbarFilter,

			store : storeSec,
			selModel : sm,
			columns : [ sm, new Ext.grid.RowNumberer(), {
				header : 'BLOOMBERG_CODE',
				dataIndex : 'security_code'
			}, {
				header : 'SHORT_NAME',
				dataIndex : 'short_name'
			} ],
			viewConfig : {
				forceFit : true,
				emptyText : 'Записи не найдены'
			}
		} ]
	});
})();
