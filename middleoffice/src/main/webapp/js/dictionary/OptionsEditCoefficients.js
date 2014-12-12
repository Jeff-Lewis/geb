/**
 * Опционы - Добавить
 */
(function() {

	var optionsId = -1;
	var coefId = -1;

	var _name = Ext.id();
	var _tradeSys = Ext.id();
	var _comment = Ext.id();
	var _coef = Ext.id();

	var container = new Ext.FormPanel({
	    border : false,
	    baseCls : 'x-plain',
	    padding : 20,
	    labelWidth : 160,
	    width : 420,
	    height : 200,
	    defaults : {
	        width : 200,
	        allowBlank : false
	    },

	    items : [ {
	        id : _name,
	        xtype : 'textfield',
	        fieldLabel : 'Опцион'
	    }, {
	        id : _tradeSys,
	        xtype : 'combo',
	        fieldLabel : 'Торговая система',
	        valueField : 'id',
	        displayField : 'name',
	        forceSelection : true,
	        store : new Ext.data.JsonStore({
	            autoDestroy : true,
	            url : 'rest/Options/Tradesystems.do',
	            // root : 'info',
	            fields : [ 'id', 'name' ],
	            sortInfo : {
		            field : 'name'
	            }
	        }),
	        loadingText : 'Поиск...',
	        minChars : 2,
	        triggerAction : 'all',
	        emptyText : 'Заполните'
	    }, {
	        id : _coef,
	        xtype : 'numberfield',
	        fieldLabel : 'Коэффициент опциона',
	        decimalPrecision : 6
	    }, {
	        id : _comment,
	        xtype : 'textfield',
	        fieldLabel : 'Комментарий',
	        allowBlank : true
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
		    this.window.setTitle('Изменение коэфициента');
	    },
	    loadData : function(data) {
		    var item = data.item;
		    optionsId = item.optionsId;
		    coefId = item.coefId;

		    Ext.getCmp(_name).setValue(item.options).setReadOnly(true);
		    Ext.getCmp(_tradeSys).setValue(item.tradeSystemId).setRawValue(item.tradeSystem);
		    Ext.getCmp(_coef).setValue(item.coefficient);
		    Ext.getCmp(_comment).setValue(item.comment);
	    }
	});

	function save(b, e) {
		Ext.Ajax.request({
		    url : 'rest/Options/Coefficients/' + coefId + '.do',
		    params : {
		        futureId : optionsId,
		        comment : Ext.getCmp(_comment).getValue(),
		        tradeSystemId : Ext.getCmp(_tradeSys).getValue(),
		        coef : Ext.getCmp(_coef).getValue()
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
