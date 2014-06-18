/**
 * Задание параметров отчёта RR
 */
(function() {

	var _date = Ext.id();
	var _securities = Ext.id();

	var info = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : false,
		url : 'rest/RiskRewardSetupParams.do',
		// root : 'info',
		fields : [ 'id', 'security_code', 'slip', 'risk_theor', 'risk_pract',
				'discount', 'date_begin', 'date_end', 'date_insert' ],
		listeners : App.ui.listenersJsonStore()
	});

	var sm = new Ext.grid.RowSelectionModel({
		singleSelect : true
	});

	function reload() {
		info.reload({
			params : {
				date : App.util.Format.dateYMD(Ext.getCmp(_date).getValue()),
				// security : Ext.getCmp(_securities).getValue()
				security : App.Combo.getValueId(Ext.getCmp(_securities))
			}
		});
	}

	function add() {
		showPanel('services/RiskRewardSetupParamsAdd');
	}

	function edt() {
		if (sm.getCount() == 0) {
			App.ui.message('Для изменения выберите позицию.');
			return;
		}

		var id = sm.getSelected().data.id;
		showModal('services/RiskRewardSetupParamsEdit',
				'rest/RiskRewardSetupParams/' + id + '.do');
	}

	function edtGroup() {
		showPanel('services/RiskRewardSetupParamsEditGroup');
	}

	function del() {
		if (sm.getCount() == 0) {
			App.ui.message('Для удаления выберите позицию.');
			return;
		}

		App.ui.confirm('Удалить запись для "'
				+ sm.getSelected().data.security_code + '"?', delCallback);
	}
	function delCallback() {
		var id = sm.getSelected().data.id;
		Ext.Ajax.request({
			method : 'DELETE',
			url : 'rest/RiskRewardSetupParams/' + id + '.do',
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Удаление записи',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					info.reload();
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

	return new Ext.Panel({
		id : 'RiskRewardSetupParams-component',
		title : 'Задание параметров отчёта RR',
		baseCls : 'x-plain',
		closable : true,
		border : true,
		layout : 'border',
		frame : true,

		items : [ {
			region : 'north',
			autoHeight : true,
			border : true,
			frame : true,
			layout : 'hbox',

			items : [ {
				xtype : 'label',
				style : 'font-weight: bold;',
				margins : '2 5 0 5',
				text : 'Дата:'
			}, {
				id : _date,
				xtype : 'datefield',
				format : 'd.m.Y'
			}, {
				xtype : 'button',
				text : 'X',
				handler : function() {
					var d = Ext.getCmp(_date);
					d.setValue(d.originalValue);
				}
			}, {
				xtype : 'label',
				style : 'font-weight: bold;',
				margins : '2 5 0 15',
				text : 'Инструмент:'
			}, {
				id : _securities,
				width : 150,
				xtype : 'combo',
				displayField : 'name',
				store : new Ext.data.JsonStore({
					autoDestroy : true,
					url : 'rest/RiskRewardSetupParams/Securities.do',
					// root : 'info',
					fields : [ 'id', 'name' ],
					sortInfo : {
						field : 'name'
					}
				}),
				loadingText : 'Поиск...',
				triggerAction : 'all',
				minChars : 2,
				typeAhead : false
			}, {
				xtype : 'button',
				text : 'X',
				handler : function() {
					var s = Ext.getCmp(_securities);
					s.setValue(s.originalValue);
				}
			} ],

			buttonAlign : 'left',
			buttons : [ {
				text : 'Показать',
				handler : reload
			} ]
		}, {
			region : 'center',
			xtype : 'grid',

			frame : true,
			enableHdMenu : false,

			tbar : [ {
				text : 'Добавить',
				handler : add
			}, {
				text : 'Изменить',
				handler : edt
			}, {
				text : 'Групповое изменение',
				handler : edtGroup
			}, {
				text : 'Удалить',
				handler : del
			} ],

			store : info,
			selModel : sm,
			columns : [ new Ext.grid.RowNumberer(), {
				header : 'SECURITY_CODE',
				dataIndex : 'security_code'
			}, {
				header : 'SLIP',
				dataIndex : 'slip',
				align : 'right',
				renderer : App.util.Renderer.number(6)
			}, {
				header : 'RISK_THEOR',
				dataIndex : 'risk_theor',
				align : 'right',
				renderer : App.util.Renderer.number(6)
			}, {
				header : 'RISK_PRACT',
				dataIndex : 'risk_pract',
				align : 'right',
				renderer : App.util.Renderer.number(6)
			}, {
				header : 'DISCOUNT',
				dataIndex : 'discount',
				align : 'right',
				renderer : App.util.Renderer.number(6)
			}, {
				header : 'DATE_BEGIN',
				dataIndex : 'date_begin',
			// renderer : App.util.Renderer.date()
			}, {
				header : 'DATE_END',
				dataIndex : 'date_end',
			// renderer : App.util.Renderer.date()
			}, {
				header : 'DATE_INSERT',
				dataIndex : 'date_insert',
			// renderer : App.util.Renderer.date()
			} ],
			viewConfig : {
				forceFit : true,
				emptyText : 'Записи не найдены'
			}
		} ],

		refresh : function() {
			reload();
		}
	});
})();
