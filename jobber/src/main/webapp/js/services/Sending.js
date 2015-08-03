/**
 * Рассылка E-mail и SMS
 */
(function() {

	var storeRes = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : false,
		fields : [ 'mail', 'status' ]
	});

	var receiverSMS = new Ext.form.ComboBox({
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

	var receiverEMail = new Ext.form.ComboBox({
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
			data : [ [ 0, 'News' ], [ 1, 'Трейдеры' ], [ 2, 'Reading' ], [ 3, 'fullermoney' ] ]
		}),
		allowBlank : false,
		emptyText : 'Выберите шаблон рассылки',
		loadingText : 'Поиск...',
		triggerAction : 'all',
		editable : false,
		listeners : {
			select : function(combo, record) {
				var id = combo.getValue();

				switch (id) {
				case 0:
					receiverSMS.setValue('news');
					receiverEMail.setValue('news');
					break;
				case 1:
					receiverSMS.setValue('');
					receiverEMail.setValue('');
					break;
				case 2:
					receiverSMS.setValue('');
					receiverEMail.setValue('Reading');
					break;
				case 3:
					receiverSMS.setValue('');
					receiverEMail.setValue('');
					break;

				default:
					break;
				}

				Ext.Ajax.request({
					url : 'rest/Sending/' + id + '.do',
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

	var serviceSelect = new Ext.form.ComboBox({
		fieldLabel : 'Сервис СМС',
		mode : 'local',
		valueField : 'id',
		displayField : 'name',
		store : new Ext.data.ArrayStore({
			autoDestroy : true,
			fields : [ 'id', 'name' ],
			data : [ [ 0, 'mfms.ru' ], [ 1, 'sms.ru' ] ]
		}),
		allowBlank : false,
		loadingText : 'Поиск...',
		triggerAction : 'all',
		editable : false,
		value : 0
	});

	var textarea = new Ext.form.TextArea({
		fieldLabel : 'Текст рассылки',
		height : 200,
		//editable : false,
		//maxLength : 1500
	});

	function send() {
		var _subject = new String(patternSelect.getRawValue()).trim();
		var _text = new String(textarea.getValue()).trim();
		var _recp = new String(receiverSMS.getValue()).trim();
		var _recm = new String(receiverEMail.getValue()).trim();

		if (!_recp && !_recm) {
			App.ui.error('Не указан получатель.');
			return;
		}

		if (!_text) {
			App.ui.error('Сообщение пустое.');
			return;
		}

//		if (text.length > 144 * 8) {
//			App.ui.error('Сообщение привысило 8 СМС.<br>Пожалуйста, сократите сообщение.');
//			return;
//		}

		Ext.Ajax.request({
			url : 'rest/Sending.do',
			params : {
				subject : _subject,
				text : _text,
				recp : _recp,
				recm : _recm,
				service : serviceSelect.getValue()
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

			items : [ receiverSMS, receiverEMail, patternSelect, serviceSelect, textarea, {
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
