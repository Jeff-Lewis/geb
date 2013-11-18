(function() {

	var container;

	var ids = 0;

	var _expression = Ext.id();
	var _comment = Ext.id();

	var variables = new Ext.data.JsonStore({
		autoDestroy : true,
		root : 'info',
		fields : [ 'var_user_name' ]
	});

	var smr = new Ext.grid.CheckboxSelectionModel({
		singleSelect : true
	});

	var sme = new Ext.grid.CheckboxSelectionModel({
		singleSelect : true,
		listeners : {
			rowselect : function(sme, idx, r) {
				var res = smr.getSelected().data.var_user_name;
				if (res != r.data.var_user_name) {
					var tf = Ext.getCmp(_expression);
					tf.setValue(tf.getValue() + r.data.var_user_name);
				}
			}
		}

	});

	function save(self) {
		if (smr.getCount() == 0) {
			App.ui.message('Выберите переменную результата.');
			return;
		}

		Ext.Ajax.request({
			url : 'organization/AddExpression.html',
			params : {
				id : ids,
				variable : smr.getSelected().data.var_user_name,
				expression : Ext.getCmp(_expression).getValue(),
				comment : Ext.getCmp(_comment).getValue()
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Сохранение нового исключения',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					container.window.close();
					menu.showSecurityInfo(ids);
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

	function btnHandler(btn) {
		var tf = Ext.getCmp(_expression);
		tf.setValue(tf.getValue() + btn.getText());
	}

	container = new Ext.FormPanel({
		width : 614,
		height : 420,
		padding : 10,
		border : false,
		baseCls : 'x-plain',
		hideLabels : true,
		layout : 'border',

		items : [ {
			region : 'north',
			height : 150,
			xtype : 'container',
			layout : 'hbox',
			layoutConfig : {
				align : 'stretch'
			},
			defaults : {
				width : 300,
				frame : true
			},

			items : [ {
				title : 'Выражение',
				layout : 'fit',

				items : {
					id : _expression,
					xtype : 'textarea',
					name : 'text'
				},

				minButtonWidth : 30,
				buttonAlign : 'left',
				buttons : [ {
					text : '+',
					handler : btnHandler
				}, {
					text : '-',
					handler : btnHandler
				}, {
					text : '*',
					handler : btnHandler
				}, {
					text : '/',
					handler : btnHandler
				} ]
			}, {
				title : 'Комментарий',
				layout : 'fit',
				items : {
					id : _comment,
					xtype : 'textarea',
					maxLength : 3000,
					// maxLengthText : ''
					name : 'comment'
				}
			} ]
		}, {
			region : 'center',
			xtype : 'container',
			layout : 'hbox',
			layoutConfig : {
				align : 'stretch'
			},
			defaults : {
				width : 300,
				frame : true,
				hideHeaders : true
			},

			items : [ {
				xtype : 'grid',
				title : 'Результат',

				store : variables,
				selModel : smr,
				columns : [ smr, {
					header : 'Переменная',
					dataIndex : 'var_user_name'
				} ],
				viewConfig : {
					forceFit : true,
					emptyText : 'Записи не найдены'
				}
			}, {
				xtype : 'grid',
				title : 'Слагаемые переменные',

				store : variables,
				selModel : sme,
				columns : [ sme, {
					header : 'Переменная',
					dataIndex : 'var_user_name'
				} ],
				viewConfig : {
					forceFit : true,
					emptyText : 'Записи не найдены'
				}
			} ]
		} ],

		buttons : [ {
			text : 'Сохранить',
			handler : save
		}, {
			text : 'Отмена',
			handler : function() {
				container.window.close();
			}
		} ],

		loadData : function(data) {
			variables.loadData(data);
			ids = data.id_sec;
		},
		setWindow : function(window) {
			this.window = window;
			this.window.setTitle('Построитель выражений');
		}
	});

	return container;
})();
