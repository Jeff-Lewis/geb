/**
 * Курсы валют
 */
(function() {

	var _date = Ext.id();
	var _currency = Ext.id();

	var info = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : false,
		autoSave : false,
		url : 'rest/CurrencyRate.do',
		// root : 'info',
		fields : [ 'dated', 'code', 'scale', 'iso', 'name', 'rate' ],
		sortInfo : {
			field : 'dated'
		},
		listeners : App.ui.listenersJsonStore()
	});

	function reload() {
		var d = Ext.getCmp(_date).getValue();
		info.reload({
			params : {
				dated : App.util.Format.dateYMD(d),
				iso : Ext.getCmp(_currency).getValue()
			}
		});
	}

	return new Ext.grid.GridPanel({
		id : 'CurrencyRate-component',
		title : 'Курсы валют',
		closable : true,
		frame : true,
		enableHdMenu : false,

		tbar : {
			xtype : 'panel',
			border : false,
			autoHeight : true,
			layout : 'hbox',
			padding : 5,

			items : [ {
				xtype : 'label',
				style : 'font-weight: bold;',
				margins : '2 5 0 5',
				text : 'Дата:'
			}, {
				id : _date,
				xtype : 'datefield',
				margins : '2 5 0 5',
				allowBlank : false,
				format : 'd.m.Y'
			}, {
				xtype : 'label',
				style : 'font-weight: bold;',
				margins : '2 5 0 25',
				text : 'Валюта:'
			}, {
				id : _currency,
				xtype : 'combo',
				width : 150,
				displayField : 'name',
				store : new Ext.data.JsonStore({
					autoDestroy : true,
					url : 'rest/CurrencyRate/Currencies.do',
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
				text : 'Х',
				margins : '0 5',
				width : 25,
				handler : function() {
					Ext.getCmp(_currency).clearValue();
				}
			} ],

			buttonAlign : 'left',
			buttons : [ {
				text : 'Выбрать',
				handler : reload
			} ]
		},

		store : info,
		columns : [ new Ext.grid.RowNumberer({
			width : 30
		}), {
			header : 'Дата',
			dataIndex : 'dated',
			align : 'center',
			renderer : App.util.Renderer.date(),
			width : 100
		}, {
			header : 'Код',
			dataIndex : 'code',
			align : 'center',
			width : 50
		}, {
			header : 'scale',
			dataIndex : 'scale',
			align : 'right',
			renderer : App.util.Renderer.number(0),
			width : 100
		}, {
			header : 'iso',
			dataIndex : 'iso',
			align : 'center',
			width : 50
		}, {
			header : 'Наименование',
			dataIndex : 'name',
			width : 250
		}, {
			header : 'Курс',
			dataIndex : 'rate',
			align : 'right',
			renderer : App.util.Renderer.number(6),
			width : 100
		} ],
		viewConfig : {
			emptyText : 'Записи не найдены'
		}
	});
})();
