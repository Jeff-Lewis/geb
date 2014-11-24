/**
 * Цена1 для RR - добавить
 */
(function() {

	var _FromDate = Ext.id();
	var _price = Ext.id();

	var info = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : false,
		autoSave : false,
		url : 'rest/RiskRewardPrice1/PortfolioShowTransfer.do',
		// root : 'info',
		fields : [ 'id', 'dated', 'client', 'fund', 'security_code', 'batch',
				'quantity', 'avg_price', 'avg_price_usd', 'currency' ],
		// sortInfo : {
		// field : 'date_insert'
		// },
		listeners : App.ui.listenersJsonStore()
	});

	var sm = new Ext.grid.RowSelectionModel({
		singleSelect : true
	});

	var clientSelect = new Ext.form.ComboBox({
	    width : 150,
	    fieldLabel : 'Клиент',
	    valueField : 'id',
	    displayField : 'name',
	    store : new Ext.data.JsonStore({
	        autoDestroy : true,
	        url : 'rest/ViewPortfolio/Clients.do',
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
	});

	function loadInfo() {
		var fd = Ext.getCmp(_FromDate);

		if (!fd.isValid()) {
			App.ui.error('Необходимо выбрать дату!');
			return;
		}

		info.reload({
			params : {
				date : App.util.Format.dateYMD(fd.getValue()),
				client : clientSelect.getValue()
			}
		});
	}

	function save(self) {
		if (sm.getCount() == 0) {
			App.ui.error('Выберите сделку.');
			return;
		}

		var p = Ext.getCmp(_price);
		if (!p.isValid()) {
			App.ui.error('Заполните ' + p.fieldLabel);
			return;
		}

		Ext.Ajax.request({
			url : 'rest/RiskRewardPrice1.do',
			params : {
				portfolio : sm.getSelected().data.id,
				price : p.getValue()
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Сохранение',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					Ext.getCmp('RiskRewardPrice1-component').refresh();
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	return new Ext.Panel({
		title : 'Добавление Цена1 для RR',
		baseCls : 'x-plain',
		closable : true,
		border : true,
		layout : 'border',
		frame : true,

		items : [ {
			region : 'center',
			xtype : 'grid',
			title : 'Портфель',
			frame : true,
			enableHdMenu : false,

			tbar : [ {
				id : _FromDate,
				xtype : 'datefield',
				format : 'd.m.Y',
				allowBlank : false,
				width : 100,
				value : new Date()
			}, {
	            xtype : 'label',
	            style : 'font-weight: bold;',
	            margins : '2 5 0 25',
	            text : 'Клиент:'
	        }, clientSelect, {
	            xtype : 'button',
	            text : 'Х',
	            margins : '0 5',
	            width : 25,
	            handler : function() {
	            	clientSelect.clearValue();
	            }
	        }, {
				text : 'Выбрать',
				handler : loadInfo
			} ],

			store : info,
			selModel : sm,
			columns : [ new Ext.grid.RowNumberer(), {
				header : 'dated',
				dataIndex : 'dated',
				renderer : App.util.Renderer.date()
			}, {
				header : 'client',
				dataIndex : 'client'
			}, {
				header : 'fund',
				dataIndex : 'fund'
			}, {
				header : 'security_code',
				dataIndex : 'security_code'
			}, {
				header : 'batch',
				dataIndex : 'batch'
			}, {
				header : 'quantity',
				dataIndex : 'quantity',
				align : 'right',
				renderer : App.util.Renderer.number(0)
			}, {
				header : 'avg_price',
				dataIndex : 'avg_price',
				align : 'right',
				renderer : App.util.Renderer.number()
			}, {
				header : 'avg_price_usd',
				dataIndex : 'avg_price_usd',
				align : 'right',
				renderer : App.util.Renderer.number()
			}, {
				header : 'currency',
				dataIndex : 'currency'
			} ],
			viewConfig : {
				forceFit : true,
				emptyText : 'Записи не найдены'
			},
		}, {
			region : 'south',
			autoHeight : true,
			border : true,
			frame : true,
			layout : 'form',
			labelWidth : 120,
			defaults : {
				width : 150
			},

			buttonAlign : 'left',
			buttons : [ {
				text : 'Добавить',
				handler : save
			} ],

			items : [ {
				id : _price,
				xtype : 'numberfield',
				fieldLabel : 'PRICE',
				decimalPrecision : 12,
				emptyText : 'Заполните',
				allowBlank : false
			} ]
		} ]
	});
})();
