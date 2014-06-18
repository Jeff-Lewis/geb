/**
 * Регистрация инструментов
 */
(function() {

	var info = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : true,
		autoSave : false,
		url : 'rest/SecurityIncorporations.do',
		// root : 'info',
		fields : [ 'sip', 'security_code', 'short_name', 'country',
				'country_code', 'security_incorporation_period', 'tax_value',
				'tax_period' ],
		sortInfo : {
			field : 'security_code'
		},
		listeners : App.ui.listenersJsonStore()
	});

	var sm = new Ext.grid.RowSelectionModel({
		singleSelect : true
	});

	function add() {
		showModal('dictionary/SecurityIncorporationsAdd');
	}

	function edit() {
		if (sm.getCount() == 0) {
			App.ui.message('Необходимо выбрать запись для редактирования!');
			return;
		}

		var id = sm.getSelected().data.sip;
		showModal('dictionary/SecurityIncorporationsEdit',
				'rest/SecurityIncorporations/' + id + '.do');
	}

	function del() {
		if (sm.getCount() == 0) {
			App.ui.message('Необходимо выбрать запись для удаления!');
			return;
		}

		var d = sm.getSelected().data;
		App.ui.confirm('Удалить запись для "' + d.security_code + ' '
				+ d.short_name + '"?', cbDel);
	}
	function cbDel() {
		var id = sm.getSelected().data.sip;
		Ext.Ajax.request({
			method : 'DELETE',
			url : 'rest/SecurityIncorporations/' + id + '.do',
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Выполняется запрос',
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

	return new Ext.grid.GridPanel({
		id : 'SecurityIncorporations-component',
		title : 'Регистрация инструментов',
		closable : true,
		frame : true,
		enableHdMenu : false,

		tbar : [ {
			text : 'Добавить',
			handler : add
		}, {
			text : 'Изменить',
			handler : edit
		}, {
			text : 'Удалить',
			handler : del
		} ],

		store : info,
		selModel : sm,
		columns : [ new Ext.grid.RowNumberer(), {
			header : 'security_code',
			dataIndex : 'security_code'
		}, {
			header : 'short_name',
			dataIndex : 'short_name'
		}, {
			header : 'country',
			dataIndex : 'country'
		}, {
			header : 'security_incorporation_period',
			dataIndex : 'security_incorporation_period'
		}, {
			header : 'tax_value',
			dataIndex : 'tax_value',
			align : 'right',
			renderer : App.util.Renderer.number()
		}, {
			header : 'tax_period',
			dataIndex : 'tax_period'
		// renderer : App.util.Renderer.date()
		} ],
		viewConfig : {
			forceFit : true,
			emptyText : 'Записи не найдены'
		}
	});
})();
