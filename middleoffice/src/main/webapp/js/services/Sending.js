/**
 * Рассылка E-mail и SMS
 */
(function() {

	var storeRes = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : false,
		fields : [ 'mail', 'status' ]
	});

	var receiver1 = new Ext.form.ComboBox({
		fieldLabel : 'Кому SMS',
		emptyText : 'Введите фамилию сотрудника',
		displayField : 'name',
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'rest/Sending/Phone.do',
			// root : 'answer',
			fields : [ 'name' ]
		}),
		minChars : 2,
		triggerAction : 'all',
		//typeAhead : false,
		loadingText : 'Поиск...',
		hideTrigger : true
	});

	var receiver2 = new Ext.form.ComboBox({
		fieldLabel : 'Кому E-Mail',
		emptyText : 'Введите E-Mail',
		displayField : 'name',
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'rest/Sending/Mail.do',
			// root : 'answer',
			fields : [ 'name' ]
		}),
		minChars : 2,
		triggerAction : 'all',
		//typeAhead : false,
		loadingText : 'Поиск...',
		hideTrigger : true
	});

	var patternSelect = new Ext.form.ComboBox({
		fieldLabel : 'Шаблон рассылки',
		mode : 'local',
		valueField : 'id',
		displayField : 'pattern',
		store : new Ext.data.ArrayStore({
			autoDestroy : true,
			fields : [ 'id', 'pattern' ],
			data : [ [ 0, 'Аналитики' ], [ 1, 'Трейдеры' ] ]
		}),
		allowBlank : false,
		emptyText : 'Выберите шаблон рассылки',
		loadingText : 'Поиск...',
		triggerAction : 'all',
		editable : false,
		listeners : {
			select : function(combo, record) {
				switch (combo.getValue()) {
				case 0:
					receiver1.setValue('news');
					receiver2.setValue('news_digr@prbb.ru');
					break;
				case 1:
					receiver1.setValue('');
					receiver2.setValue('');
					break;

				default:
					break;
				}
				Ext.Ajax.request({
					url : 'rest/Sending/' + combo.getValue() + '.do',
					timeout : 10 * 60 * 1000, // 10 min
					waitMsg : 'Сохранение',
					success : function(xhr) {
						var answer = Ext.decode(xhr.responseText);
						if (answer.success) {
							textarea.setValue(answer.item);
						}
					},
					failure : function() {
						App.ui.error('Сервер недоступен');
					}
				});
			}
		}
	});

	var textarea = new Ext.form.TextArea({
		fieldLabel : 'Текст рассылки',
		height : 200,
		editable : false,
		maxLength : 1500
	});

	function send() {
		Ext.Ajax.request({
			url : 'rest/Sending.do',
			params : {
				text : textarea.getValue(),
				recp : receiver1.getValue(),
				recm : receiver2.getValue()
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Отправка сообщения ...',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.length) {
					storeRes.loadData(answer);
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	return new Ext.Panel({
		id : 'Sending-component',
		title : 'Рассылка SMS и E-mail',
		closable : true,
		frame : true,
		layout : 'border',

		items : [ {
			region : 'west',
			xtype : 'form',
			padding : 10,
			width : 520,
			labelAlign : 'top',
			labelWidth : 160,
			defaults : {
				width : 500
			},

			items : [ receiver1, receiver2, patternSelect, textarea, {
				xtype : 'button',
				text : 'Отправить',
				handler : send
			}, {
				xtype : 'grid',
				title : 'Результаты рассылки',
				frame : true,
				enableHdMenu : false,
				height : 250,

				store : storeRes,
				columns : [ {
					header : 'Получатель',
					dataIndex : 'mail'
				}, {
					header : 'Статус',
					dataIndex : 'status',
					renderer : function(val) {
						return (val == '0') ? 'отправлено' : val;
					}
				} ],

				viewConfig : {
					forceFit : true,
					emptyText : 'Записи не найдены'
				}
			} ]
		}, {
			region : 'center',
			xtype : 'container'
		} ]
	});
})();
