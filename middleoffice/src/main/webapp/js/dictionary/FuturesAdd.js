/**
 * Фьючерсы - Добавить
 */
(function() {

	var futuresId = -1;

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
	        fieldLabel : 'Фьючерс'
	    }, {
	        id : _tradeSys,
	        xtype : 'combo',
	        fieldLabel : 'Торговая система',
	        valueField : 'id',
	        displayField : 'name',
	        forceSelection : true,
	        store : new Ext.data.JsonStore({
	            autoDestroy : true,
	            url : 'rest/Futures/Tradesystems.do',
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
	        fieldLabel : 'Коэффициент фьючерса',
	        decimalPrecision : 6
	    }, {
	        id : _comment,
	        xtype : 'textfield',
	        fieldLabel : 'Комментарий',
	        allowBlank : true
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
		    this.window.setTitle('Добавления нового фьючерса');
	    },
	    loadData : function(data) {
		    this.window.setTitle('Добавления нового коэффициента');

		    var item = data.item;
		    futuresId = item.futuresId;

		    var cmpName = Ext.getCmp(_name);
		    cmpName.setValue(item.futures);
		    cmpName.setReadOnly(true);

		    Ext.getCmp(_tradeSys).focus();
	    }
	});

	function save(b, e) {
		if (-1 != futuresId) {
			Ext.Ajax.request({
			    url : 'rest/Futures/Coefficients.do',
			    params : {
					futureId : futuresId,
					coef : Ext.getCmp(_coef).getValue(),
					comment : Ext.getCmp(_comment).getValue(),
					tradeSystemId : Ext.getCmp(_tradeSys).getValue()
			    },
			    timeout : 10 * 60 * 1000, // 10 min
			    waitMsg : 'Сохранение нового фьючерса',
			    success : function(xhr) {
				    var answer = Ext.decode(xhr.responseText);
				    if (answer.success) {
					    container.window.close();
					    Ext.getCmp('Futures-component').getStore().reload();
				    }
			    },
			    failure : function() {
				    App.ui.error('Сервер недоступен');
			    }
			});
		} else {
			Ext.Ajax.request({
			    url : 'rest/Futures.do',
			    params : {
			        name : Ext.getCmp(_name).getValue(),
					coef : Ext.getCmp(_coef).getValue(),
					comment : Ext.getCmp(_comment).getValue(),
					tradeSystemId : Ext.getCmp(_tradeSys).getValue()
			    },
			    timeout : 10 * 60 * 1000, // 10 min
			    waitMsg : 'Сохранение нового фьючерса',
			    success : function(xhr) {
				    var answer = Ext.decode(xhr.responseText);
				    if (answer.success) {
					    container.window.close();
					    Ext.getCmp('Futures-component').getStore().reload();
				    }
			    },
			    failure : function() {
				    App.ui.error('Сервер недоступен');
			    }
			});
		}
	}

	function close() {
		container.window.close();
	}

	return container;
})();
