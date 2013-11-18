/**
 * Ввод нового инструмента - добавление
 */
(function() {

	var _code = Ext.id();
	var _type = Ext.id();

	var container = new Ext.FormPanel({
		border : false,
		baseCls : 'x-plain',
		padding : 20,
		labelWidth : 120,
		width : 360,
		height : 150,
		defaults : {
			width : 180,
			allowBlank : false
		},

		items : [ {
			id : _code,
			xtype : 'textfield',
			fieldLabel : 'Код БЛУМБЕРГ',
			emptyText : 'Заполните'
		}, {
			id : _type,
			xtype : 'combo',
			fieldLabel : 'Тип инструмента',
			emptyText : 'Выберите инструмент',
			mode : 'local',
			store : [ 'Акция', 'Индекс', 'Облигация', 'Фьючерс' ],
			editable : false,
			triggerAction : 'all'
		} ],

		buttons : [ {
			text : 'Добавить',
			handler : save
		}, {
			text : 'Отмена',
			handler : close
		} ],

		setWindow : function(window) {
			this.window = window;
			this.window.setTitle('Добавление инструмента');
		}
	});

	function save(b, e) {
		var _c = Ext.getCmp(_code);
		if (!_c.isValid()) {
			_c.focus();
			return;
		}

		var _t = Ext.getCmp(_type);
		if (!_t.isValid()) {
			_t.focus();
			return;
		}

		var s = Ext.getCmp('NewInstrument-component').getStore();
		var r = new s.recordType({
			code : _c.getValue(),
			type : _t.getValue()
		});
		s.add(r);

		container.window.close();
	}

	function close(b, e) {
		container.window.close();
	}

	return container;
})();
