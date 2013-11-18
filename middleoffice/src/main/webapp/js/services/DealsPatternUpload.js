/**
 * Шаблон для загрузки сделок - Загрузить в базу
 */
(function() {

	var _upload = Ext.id();

	var panel = new Ext.FormPanel({
		fileUpload : true,
		border : false,
		baseCls : 'x-plain',
		padding : 5,
		width : 420,
		height : 100,
		labelWidth : 90,
		defaults : {
			width : 300
		},

		items : [ {
			id : _upload,
			xtype : 'fileuploadfield',
			fieldLabel : 'Файл загрузки',
			name : 'upload',
			buttonText : 'Выбрать файл'
		} ],

		buttons : [ {
			text : 'Загрузить',
			handler : submit
		}, {
			text : 'Отмена',
			handler : close
		} ],

		setWindow : function(window) {
			this.window = window;
			this.window.setTitle('Загрузить в базу');
		}
	});

	function close() {
		panel.window.close();
	}

	function submit(self) {
		if (!Ext.getCmp(_upload).getValue()) {
			App.ui.message('Выберите файл.');
			return;
		}

		panel.getForm().submit({
			clientValidation : false,
			url : 'rest/DealsPattern/Upload.do',
			waitMsg : 'Загрузка',
			timeout : 10 * 60 * 1000,
			success : submitSuccess,
			failure : submitFailure
		});
	}

	function submitSuccess(form, action) {
		panel.window.close();
		Ext.getCmp('DealsPattern-component').getStore().reload();
	}

	function submitFailure(form, action) {
		panel.window.close();
		Ext.getCmp('DealsPattern-component').getStore().reload();

		switch (action.failureType) {
		case Ext.form.Action.CONNECT_FAILURE:
			App.ui.error('Сервер недоступен');
			break;
		case Ext.form.Action.SERVER_INVALID:
			App.ui.error(action.result.msg);
			break;
		}
	}

	return panel;
})();
