/**
 * Загрузка погашения купонов из файла
 */
(function() {

	var _resultText = Ext.id();
	var _winFiles = Ext.id();

	var errors = new Ext.data.ArrayStore({
		autoDestroy : true,
		autoLoad : false,
		fields : [ 'row', 'error' ]
	});

	function updateGrid(form, action) {
		var s = action.response.responseText;
		var p1 = s.indexOf('>');
		if (p1 > 0) {
			s = s.substr(p1 + 1, s.length - p1 - 7);
		}
		var answer = Ext.decode(s);
		if (answer) {
			if (answer.success) {
				Ext.getCmp(_resultText).setText(
						'Всего записей в файле: '+answer.item.totalRecords+
						'  Записей с ошибками: ' + answer.item.totalErrors						);
				errors.removeAll();
				Ext.each(answer.item.errors, function(r) {
					errors.add(new Ext.data.Record({
						row : r.row,
						error : r.error
					}));
				});
				App.ui.message('Операция выполнена успешно');
			} else if (answer.code == 'login') {
				App.ui.sessionExpired();
			} else {
				App.ui.error(answer.message);
			}
		}
	}

	function loadFile(self) {
		Ext.getCmp(_winFiles).getForm().submit({
			url : 'rest/CouponsLoading.do',
			waitMsg : 'Загрузка',
			timeout : 10 * 60 * 1000,
			success : updateGrid,
			failure : updateGrid
		});
	}

	return new Ext.Panel({
		id : 'CouponsLoading-component',
		title : 'Загрузка погашения купонов',
		closable : true,
		border : false,
		layout : 'border',
		frame : true,

		items : [ {
			region : 'north',
			xtype : 'form',
			id : _winFiles,
			autoHeight : true,
			padding : 5,
			fileUpload : true,
			items : [ {
				xtype : 'fileuploadfield',
				fieldLabel : 'Файл загрузки',
				width : 400,
				name : 'upload',
				buttonText : 'Выбрать файл'
			} ],
			buttonAlign : 'left',
			buttons : [ {
				text : 'Загрузить',
				handler : loadFile
			} ]
		}, {
			region : 'center',
			xtype : 'grid',
			frame : true,
			enableHdMenu : false,

			tbar : [ {
				id : _resultText,
				xtype : 'tbtext',
				text : 'Всего записей в файле: -  Записей с ошибками: -'
			} ],

			store : errors,
			columns : [ new Ext.grid.RowNumberer({
				header : '#',
				sortable : false
			}), {
				header : 'Строка',
				dataIndex : 'row',
				width : 30
			}, {
				header : 'Ошибка',
				dataIndex : 'error'
			} ],
			viewConfig : {
				forceFit : true,
				emptyText : 'Ошибок нет'
			}
		} ]
	});
})();
