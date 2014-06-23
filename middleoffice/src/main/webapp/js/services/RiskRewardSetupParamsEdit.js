/**
 * Задание параметров отчёта RR - Изменить
 */
(function() {

	var idItem = 0;

	var _security = Ext.id();
	var _slip = Ext.id();
	var _riskTheor = Ext.id();
	var _riskPract = Ext.id();
	var _discount = Ext.id();
	var _dateBegin = Ext.id();
	var _dateEnd = Ext.id();

	var container = new Ext.FormPanel({
		border : false,
		baseCls : 'x-plain',
		padding : 20,
		width : 260,
		height : 430,
		labelAlign : 'top',
		defaults : {
			width : 200
		},

		items : [ {
			id : _security,
			xtype : 'textfield',
			fieldLabel : 'SECURITY_CODE',
			readOnly : true
		}, {
			id : _slip,
			xtype : 'numberfield',
			decimalPrecision : 6,
			fieldLabel : 'Слипыч'
		}, {
			id : _riskTheor,
			xtype : 'numberfield',
			decimalPrecision : 6,
			fieldLabel : 'Риск теоретической ликвидности'
		}, {
			id : _riskPract,
			xtype : 'numberfield',
			decimalPrecision : 6,
			fieldLabel : 'Риск практической ликвидности'
		}, {
			id : _discount,
			xtype : 'numberfield',
			decimalPrecision : 6,
			fieldLabel : 'Дисконт'
		}, {
			id : _dateBegin,
			xtype : 'datefield',
			format : 'd.m.Y',
			fieldLabel : 'Начало периода'
		}, {
			id : _dateEnd,
			xtype : 'datefield',
			format : 'd.m.Y',
			fieldLabel : 'Окончание периода'
		} ],

		buttons : [ {
			text : 'Сохранить',
			handler : save
		}, {
			text : 'Отмена',
			handler : close
		} ],

		loadData : function(data) {
			data = data.info;

			idItem = data.id;

			Ext.getCmp(_security).setValue(data.security_code);
			Ext.getCmp(_slip).setValue(data.slip);
			Ext.getCmp(_riskTheor).setValue(data.risk_theor);
			Ext.getCmp(_riskPract).setValue(data.risk_pract);
			Ext.getCmp(_discount).setValue(data.discount);
			Ext.getCmp(_dateBegin).setValue(data.date_begin);
			Ext.getCmp(_dateEnd).setValue(data.date_end);
		},
		setWindow : function(window) {
			this.window = window;
			this.window.setTitle('Изменить параметры отчёта RR');
		}
	});

	function save(self) {
		var db = Ext.getCmp(_dateBegin);
		if (!db.validate()) {
			App.ui.message('Необходимо заполнить дату!');
			return;
		}

		var de = Ext.getCmp(_dateEnd);

		Ext.Ajax.request({
			url : 'rest/RiskRewardSetupParams/' + idItem + '.do',
			params : {
				slip : Ext.getCmp(_slip).getValue(),
				riskTheor : Ext.getCmp(_riskTheor).getValue(),
				riskPract : Ext.getCmp(_riskPract).getValue(),
				discount : Ext.getCmp(_discount).getValue(),
				dateBegin : App.util.Format.dateYMD(db.getValue()),
				dateEnd : App.util.Format.dateYMD(de.getValue())
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Сохранение изменений',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					container.window.close();
					Ext.getCmp('RiskRewardSetupParams-component').refresh();
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	function close() {
		container.window.close();
	}

	return container;
})();
