/**
 * Список компаний - info - Построитель выражений
 */
(function() {

	var id_sec = 0;
	var urlBase = 'rest/Companies/';

	var _expression = Ext.id();
	var _comment = Ext.id();

	var variables = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : false,
		url : urlBase + '/Variables.do',
		fields : [ 'id', 'name' ]
	});

	var smr = new Ext.grid.CheckboxSelectionModel({
		singleSelect : true
	});

	var sme = new Ext.grid.RowSelectionModel({
		singleSelect : true,
		listeners : {
			rowselect : addVar
		}
	});

	function addVar(sm, idx, r) {
		if (smr.getCount() == 0) {
			return;
		}

		if (smr.getSelected().data.name == r.data.name) {
			return;
		}

		var tf = Ext.getCmp(_expression);
		tf.setValue(tf.getValue() + r.data.name);
	}

	function btnHandler(btn) {
		var tf = Ext.getCmp(_expression);
		tf.setValue(tf.getValue() + btn.getText());
	}

	var container = new Ext.FormPanel({
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
					xtype : 'textarea'
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
				}, {
					text : 'X',
					handler : function() {
						Ext.getCmp(_expression).setValue('');
					}
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
					dataIndex : 'name'
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
				columns : [ {
					header : 'Переменная',
					dataIndex : 'name'
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
			handler : close
		} ],

		loadData : function(data) {
			id_sec = data.item;
			urlBase = urlBase + id_sec;

			variables.proxy.setUrl(urlBase + '/Variables.do', true);
			variables.load();
		},
		setWindow : function(window) {
			this.window = window;
			this.window.setTitle('Построитель выражений');
		}
	});

	function save(self) {
		if (smr.getCount() == 0) {
			App.ui.message('Выберите переменную результата.');
			return;
		}

		Ext.Ajax.request({
			url : urlBase + '/formula.do',
			params : {
				variable : smr.getSelected().data.name,
				expression : Ext.getCmp(_expression).getValue(),
				comment : Ext.getCmp(_comment).getValue()
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Сохранение нового исключения',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					container.window.close();
					Ext.getCmp('CompaniesInfo-component').refreshEx();
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

	function close() {
		container.window.close();
	}

	return container;
})();
