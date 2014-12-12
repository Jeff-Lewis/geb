/**
 * Опционы - Добавить
 */
(function() {

	var optionsId = -1;

	var _name = Ext.id();

	var container = new Ext.FormPanel({
	    border : false,
	    baseCls : 'x-plain',
	    padding : 20,
	    labelWidth : 160,
	    width : 420,
	    height : 120,
	    defaults : {
	        width : 200,
	        allowBlank : false
	    },

	    items : [ {
	        id : _name,
	        xtype : 'textfield',
	        fieldLabel : 'Опцион'
	    } ],

	    buttons : [ {
	        text : 'Сохранить',
	        handler : save
	    }, {
	        text : 'Отмена',
	        handler : close
	    } ],

	    setWindow : function(window) {
		    this.window = window;
		    this.window.setTitle('Изменение опциона');
	    },
	    loadData : function(data) {
		    var item = data.item;
		    optionsId = item.optionsId;

		    Ext.getCmp(_name).setValue(item.options);
	    }
	});

	function save(b, e) {
		Ext.Ajax.request({
		    url : 'rest/Options/' + optionsId + '.do',
		    params : {
			    name : Ext.getCmp(_name).getValue()
		    },
		    timeout : 10 * 60 * 1000, // 10 min
		    waitMsg : 'Сохранение нового опциона',
		    success : function(xhr) {
			    var answer = Ext.decode(xhr.responseText);
			    if (answer.success) {
				    container.window.close();
				    Ext.getCmp('Options-component').getStore().reload();
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
