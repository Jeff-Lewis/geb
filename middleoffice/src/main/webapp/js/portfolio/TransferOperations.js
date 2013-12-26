/**
 * Список перекидок
 */
(function() {

	var _date1 = Ext.id();
	var _date2 = Ext.id();
	var _ticker = Ext.id();

	var info = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : false,
		url : 'rest/TransferOperations.do',
		// root : 'info',
		fields : [ 'id', 'client', 'accout', 'security', 'currency',
				'transferQuantity', 'transferPrice', 'transferDate',
				'sourceFund', 'sourceBatch', 'sourceQuantity',
				'sourceOperation', 'destinationFund', 'destinationBatch',
				'destinationOperation', 'comment', 'funding', 'selected' ],
		listeners : App.ui.listenersJsonStore()
	});

	function reloadInfo() {
		var fd = Ext.getCmp(_date1).getValue();
		var td = Ext.getCmp(_date2).getValue();

		if ((fd == '') || (td == '')) {
			App.ui.error('Необходимо выбрать дату!');
			return;
		}

		info.reload({
			params : {
				dateBegin : App.util.Format.dateYMD(fd),
				dateEnd : App.util.Format.dateYMD(td),
				ticker : Ext.getCmp(_ticker).getValue()
			}
		});
	}

	function deleteSelected() {
		var ids = [];

		info.each(function(item) {
			if (item.data.selected)
				ids.push(item.data.id);
			return true;
		});

		if (ids.length == 0) {
			App.ui.message('Для удаления выберите позиции.');
			return;
		}

		Ext.Ajax.request({
			url : 'rest/TransferOperations/Del.do',
			params : {
				ids : ids
			},
			timeout : 60000,
			waitMsg : 'Выполняется удаление',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					reloadInfo();
				} else if (answer.code == 'login') {
					App.ui.sessionExpired();
				} else {
					App.ui.error(answer.message);
				}
			},
			failure : function(response, opts) {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	var sm = new Ext.grid.CellSelectionModel();

	function updateField(id, column, newValue) {
		var ids = [];

		info.each(function(item) {
			if (item.data.selected)
				ids.push(item.data.id);
			return true;
		});

		if (ids.length == 0) {
			ids.push(id);
		}

		Ext.Ajax.request({
			url : 'rest/TransferOperations/Set.do',
			params : {
				ids : ids,
				field : column,
				value : newValue
			},
			timeout : 60000,
			waitMsg : 'Выполняется изменение.',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					reloadInfo();
				} else if (answer.code == 'login') {
					App.ui.sessionExpired();
				} else {
					App.ui.error(answer.message);
				}
			},
			failure : function(response, opts) {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	var editorTransferQuantity = new Ext.form.NumberField({
		allowBlank : false,
		allowDecimals : false,
	});

	var editorTransferPrice = new Ext.form.NumberField({
		allowBlank : false,
		allowNegative : false,
		decimalPrecision : 6,
	});

	var editorDestinationFund = new Ext.form.ComboBox({
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'rest/TransferOperations/Funds.do',
			// root : 'info',
			fields : [ 'id', 'name' ],
			sortInfo : {
				field : 'name'
			}
		}),
		emptyText : 'Выберите',
		displayField : 'name',
		loadingText : 'Поиск...',
		triggerAction : 'all',
		editable : false,
	});

	var editorDestinationBatch = new Ext.form.NumberField({
		allowBlank : false,
		allowDecimals : false,
	});

	var editorComment = new Ext.form.TextField({
		allowBlank : false,
	});

	function exportExcel() {
		var fd = Ext.getCmp(_date1).getValue();
		var td = Ext.getCmp(_date2).getValue();

		if ((fd == '') || (td == '')) {
			App.ui.error('Необходимо выбрать дату!');
			return;
		}

		window.open('rest/TransferOperations/Export.do?' + 'dateBegin='
				+ App.util.Format.dateYMD(fd) + '&dateEnd='
				+ App.util.Format.dateYMD(td) + '&ticker='
				+ Ext.getCmp(_ticker).getValue());
	}

	var _checked;

	function selectCheck(This, checked) {
		Ext.Msg.wait(checked ? 'Установка меток' : 'Снятие меток',
				Ext.form.BasicForm.prototype.waitTitle);
		_checked = checked;
		setTimeout(function() {
			var checked = _checked;
			info.each(function(item) {
				item.data.selected = checked;
				// Ext.Msg.updateProgress();
				return true;
			});
			Ext.Msg.hide();
			Ext.getCmp('TransferOperations-component').getView().refresh();
		}, 0);
	}

	var filter = new Ext.Panel({
		layout : 'hbox',
		padding : 5,
		autoHeight : true,

		items : [ {
			xtype : 'label',
			style : 'font-weight: bold;',
			margins : '2 5 0 5',
			text : 'Дата с:'
		}, {
			id : _date1,
			xtype : 'datefield',
			allowBlank : false,
			format : 'd.m.Y'
		}, {
			xtype : 'label',
			style : 'font-weight: bold;',
			margins : '2 5 0 5',
			text : 'по:'
		}, {
			id : _date2,
			xtype : 'datefield',
			allowBlank : false,
			format : 'd.m.Y'
		}, {
			xtype : 'label',
			style : 'font-weight: bold;',
			margins : '2 5 0 25',
			text : 'Выбор тикера:'
		}, {
			id : _ticker,
			xtype : 'combo',
			width : 150,
			fieldLabel : 'Тикер Блумберг',
			valueField : 'id',
			displayField : 'name',
			store : new Ext.data.JsonStore({
				autoDestroy : true,
				url : 'rest/TransferOperations/Tickers.do',
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
				Ext.getCmp(_ticker).clearValue();
			}
		} ],

		buttonAlign : 'left',
		buttons : [ {
			xtype : 'label',
			width : 12,
			text : ''
		}, {
			xtype : 'checkbox',
			listeners : {
				check : selectCheck
			}
		}, {
			text : 'Удалить выбранные',
			handler : function() {
				App.ui.confirm('Удалить помеченные?', deleteSelected);
			}
		}, {
			xtype : 'label',
			width : 35,
			text : ''
		}, {
			text : 'Показать сделки',
			handler : reloadInfo
		}, {
			text : 'Выгрузить в Excel',
			handler : exportExcel
		} ]
	});

	return new Ext.grid.EditorGridPanel({
		id : 'TransferOperations-component',
		title : 'Список перекидок',
		frame : true,
		closable : true,
		enableHdMenu : false,

		tbar : filter,

		store : info,
		selModel : sm,
		clickToEdit : 1,
		columns : [ { /* header : 'X', */
			xtype : 'checkcolumn',
			dataIndex : 'selected',
			width : 16,
			sortable : false
		}, new Ext.grid.RowNumberer({
			width : 35
		}), {
			header : 'Client',
			dataIndex : 'client'
		}, {
			header : 'Accout',
			dataIndex : 'accout'
		}, {
			header : 'Security',
			dataIndex : 'security'
		}, {
			header : 'Currency',
			dataIndex : 'currency'
		}, {
			header : 'Funding',
			dataIndex : 'funding',
			width : 30,
			align : 'center',
			renderer : App.util.Renderer.bool()
		}, {
			header : 'TransferQuantity',
			dataIndex : 'transferQuantity',
			align : 'right',
			renderer : App.util.Renderer.number(0),
			editor : editorTransferQuantity
		}, {
			header : 'TransferPrice',
			dataIndex : 'transferPrice',
			align : 'right',
			renderer : App.util.Renderer.number(3),
			editor : editorTransferPrice
		}, {
			header : 'TransferDate',
			dataIndex : 'transferDate',
			renderer : App.util.Renderer.date(),
			width : 50,
		}, {
			header : 'SourceFund',
			dataIndex : 'sourceFund'
		}, {
			header : 'SourceBatch',
			dataIndex : 'sourceBatch'
		}, {
			header : 'SourceQuantity',
			dataIndex : 'sourceQuantity',
			align : 'right',
			renderer : App.util.Renderer.number(0),
		}, {
			header : 'SourceOperation',
			dataIndex : 'sourceOperation'
		}, {
			header : 'DestinationFund',
			dataIndex : 'destinationFund',
			editor : editorDestinationFund
		}, {
			header : 'DestinationBatch',
			dataIndex : 'destinationBatch',
			editor : editorDestinationBatch
		}, {
			header : 'DestinationOperation',
			dataIndex : 'destinationOperation'
		}, {
			header : 'Comment',
			dataIndex : 'comment',
			editor : editorComment
		} ],
		viewConfig : {
			forceFit : true,
			emptyText : 'Записи не найдены'
		},

		listeners : {
			rowdblclick : function(grid, rowIndex, e) {
				var sc = sm.getSelectedCell();

				switch (grid.getColumnModel().getDataIndex(sc[1])) {
				case 'transferQuantity':
				case 'transferPrice':
				case 'destinationFund':
				case 'destinationBatch':
				case 'comment':
					return;
				}

				var id = grid.getStore().getAt(rowIndex).data.id;
				menu.showTransferOperation(id);
			},
			afteredit : function(e) {
				updateField(e.record.data.id, e.field, e.value);
			}
		}
	});
})();
