/**
 * Нет соответствия
 */
(function() {

	var info = new Ext.data.JsonStore(
			{
				autoDestroy : true,
				autoLoad : true,
				autoSave : false,
				url : 'rest/NoConformity.do',
				// root : 'info',
				fields : [ 'id', 'tradeNum', 'tradeDate', 'secShortName',
						'operation' ],
				sortInfo : {
					field : 'tradeDate'
				},
				listeners : App.ui.listenersJsonStore()
			});

	var sm = new Ext.grid.CheckboxSelectionModel({
		checkOnly : true
	});

	function del() {
		if (sm.getCount() == 0) {
			App.ui.message('Для удаления выберите позиции.');
			return;
		}

		var ids = [];
		sm.each(function(item) {
			ids.push(item.data.id);
			return true;
		});

		Ext.Ajax.request({
			url : 'rest/NoConformity/Delete.do',
			params : {
				ids : ids
			},
			timeout : 60000,
			waitMsg : 'Выполняется удаление',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					info.reload();
				}
			},
			failure : function(response, opts) {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	return new Ext.grid.EditorGridPanel({
		id : 'NoConformity-component',
		title : 'Нет соответствия',
		frame : true,
		closable : true,
		enableHdMenu : false,

		tbar : {
			buttons : [ {
				text : 'Обновить',
				handler : function() {
					info.reload();
				}
			}, {
				text : 'Удалить',
				handler : del
			}, {
				text : 'Выгрузить в Excel',
				handler : function() {
					window.open('rest/NoConformity/ExportXls.do');
				}
			} ]
		},

		store : info,
		selModel : sm,
		clicksToEdit : 1,
		columns : [ sm, new Ext.grid.RowNumberer(), {
			header : 'TradeNum',
			dataIndex : 'tradeNum',
			sortable : true
		}, {
			header : 'Date',
			dataIndex : 'tradeDate',
			// align : 'center',
			renderer : App.util.Renderer.date(),
			sortable : true
		}, {
			header : 'SecShortName',
			dataIndex : 'secShortName',
			editor : new Ext.form.TextField({
				readOnly : true,
				selectOnFocus : true
			})
		}, {
			header : 'Operation',
			dataIndex : 'operation'
		} ],
		viewConfig : {
			forceFit : true,
			emptyText : 'Записи не найдены'
		}
	});
})();
