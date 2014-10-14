/**
 * Текущий портфель
 */
(function() {

	var updateInfo = false;

	var _date = Ext.id();
	var _calcInfo = Ext.id();

	var info = new Ext.data.JsonStore({
	    autoDestroy : true,
	    autoLoad : false,
	    url : 'rest/ViewPortfolio.do',
	    // root : 'info',
	    fields : [ 'report_date', 'client', 'fund', 'security_code', 'short_name', 'batch',
	            'usd_funding', 'currency', 'quantity', 'avg_price', 'last_price', 'nkd', 'position',
	            'position_rep_date', 'revaluation' ],
	    listeners : App.ui.listenersJsonStore()
	});

	var futureSelect = new Ext.form.ComboBox({
	    width : 150,
	    fieldLabel : 'Тикер Блумберг',
	    valueField : 'id',
	    displayField : 'name',
	    store : new Ext.data.JsonStore({
	        autoDestroy : true,
	        url : 'rest/ViewPortfolio/Securities.do',
	        // root : 'info',
	        fields : [ 'id', 'name' ],
	        sortInfo : {
		        field : 'name'
	        }
	    }),
	    loadingText : 'Поиск...',
	    triggerAction : 'all',
	    minChars : 2,
	    typeAhead : false
	});

	function show() {
		var fd = Ext.getCmp(_date).getValue();

		if (fd == '') {
			App.ui.error('Необходимо выбрать дату!');
			return;
		}

		info.reload({
			params : {
			    date : App.util.Format.dateYMD(fd),
			    security : futureSelect.getValue()
			}
		});
	}

	function calcStart() {
		var fd = Ext.getCmp(_date).getValue();

		if (fd == '') {
			App.ui.error('Необходимо выбрать дату!');
			return;
		}

		Ext.Ajax.request({
		    url : 'rest/ViewPortfolio/Calculate.do',
		    params : {
		        date : App.util.Format.dateYMD(fd),
		        security : futureSelect.getValue()
		    },
		    timeout : 24 * 60* 60 * 1000,
		    success : function(xhr) {
			    var answer = Ext.decode(xhr.responseText);
			    if (answer.success) {
			    	Ext.getCmp(_calcInfo).setText(answer.text);
			    	//App.ui.message(answer.text);
			    }
		    },
		    failure : function() {
			    Ext.getCmp(_calcInfo).setText('Сервер недоступен');
		    }
		});
	}

	function calcStop() {
		var fd = Ext.getCmp(_date).getValue();

		if (fd == '') {
			App.ui.error('Необходимо выбрать дату!');
			return;
		}

		Ext.Ajax.request({
		    url : 'rest/ViewPortfolio/Calculate.do',
		    params : {
		        date : App.util.Format.dateYMD(fd),
		        security : futureSelect.getValue()
		    },
		    timeout : 24 * 60* 60 * 1000,
		    success : function(xhr) {
			    var answer = Ext.decode(xhr.responseText);
			    if (answer.success) {
			    	Ext.getCmp(_calcInfo).setText(answer.text);
			    	//App.ui.message(answer.text);
			    }
		    },
		    failure : function() {
			    Ext.getCmp(_calcInfo).setText('Сервер недоступен');
		    }
		});
	}

	function updateCalc() {
		if (!updateInfo)
			return;

		var un = Ext.getCmp('logout');
		if (un && un.hidden)
			return;

		Ext.Ajax.request({
		    url : 'rest/ViewPortfolio/Calculate/Progress.do',
		    timeout : 2000,
		    success : function(xhr) {
			    var msg;
			    var answer = Ext.decode(xhr.responseText);
			    if (answer.success) {
				    msg = Math.floor(100 * answer.value) + '% ' + answer.text;
			    } else {
				    msg = 'не запущено';
			    }
			    Ext.getCmp(_calcInfo).setText(msg);
		    },
		    failure : function() {
			    //App.ui.error('Сервер недоступен');
			    Ext.getCmp(_calcInfo).setText('Сервер недоступен');
		    }
		});
	}

	function exportXls() {
		var fd = Ext.getCmp(_date).getValue();

		if (fd == '') {
			App.ui.error('Необходимо выбрать дату!');
			return;
		}

		var url = 'rest/ViewPortfolio/Export.do?date=' + App.util.Format.dateYMD(fd) + '&security='
		        + futureSelect.getValue();
		window.open(url);
	}

	function deleteFinResAndRests() {
		var fd = Ext.getCmp(_date).getValue();
		var fy = new Date().add(Date.DAY, -1);
		var fs = futureSelect.getValue();

		if (!fd && fs) {
			App.ui.error('Необходимо выбрать дату!');
			return;
		}

		if (fd && fs) {
			var fn = futureSelect.getStore().getById(fs).data.name;
			var msg = 'Будут удалены остатки и финансовые результаты<br>с ' + fd.format('d.m.Y')
			        + ' по ' + fy.format('d.m.Y') + '<br>по инструменту: ' + fn + '.<br>Удалить?';
			Ext.MessageBox.confirm('Внимание', msg, function(btn) {
				if (btn == 'no') {
					return;
				}
				ajaxDeleteFRR(fd, fs);
			});
			return;
		}

		if (fd) {
			var msg = 'Будут удалены остатки и финансовые результаты<br>с ' + fd.format('d.m.Y')
			        + ' по ' + fy.format('d.m.Y') + '<br>по ВСЕМ ИНСТРУМЕНТАМ.<br>Удалить?';
			Ext.MessageBox.confirm('Внимание', msg, function(btn) {
				if (btn == 'no') {
					return;
				}

				var msg = 'Вы действительно хотите<br>'
				        + 'удалить остатки и финансовые результаты<br>ПО ВСЕМ ИНСТРУМЕНТАМ?';
				Ext.MessageBox.confirm('Внимание', msg, function(btn) {
					if (btn == 'no') {
						return;
					}
					ajaxDeleteFRR(fd, null);
				});

			});
			return;
		}

		var msg = 'Будут удалены ВСЕ остатки и финансовые результаты.<br>Удалить?';
		Ext.MessageBox.confirm('Внимание', msg, function(btn) {
			if (btn == 'no') {
				return;
			}

			var msg = 'Вы действительно хотите удалить<br>ВСЕ остатки и финансовые результаты?';
			Ext.MessageBox.confirm('Внимание', msg, function(btn) {
				if (btn == 'no') {
					return;
				}
				ajaxDeleteFRR(null, null);
			});
		});

	}
	function ajaxDeleteFRR(date, id_sec) {
		Ext.Ajax.request({
		    url : 'rest/ViewPortfolio/Delete.do',
		    params : {
		        date : App.util.Format.dateYMD(date),
		        security : futureSelect.getValue()
		    },
		    timeout : 2000,
		    success : function(xhr) {
		    	App.ui.message('Данные удалены.<br>Рекомендуется пересчитать портфель.');
		    },
		    failure : function() {
			    App.ui.error('Сервер недоступен');
		    }
		});
    }

	var intervalID = window.setInterval(updateCalc, 2000);

	return new Ext.grid.GridPanel({
	    id : 'ViewPortfolio-component',
	    title : 'Текущий потфель',
	    frame : true,
	    closable : true,
	    enableHdMenu : false,

	    listeners : {
	        destroy : function() {
		        updateInfo = false;
		        window.clearInterval(intervalID);
	        },
	        activate : function() {
		        updateInfo = true;
		        updateCalc();
	        },
	        deactivate : function() {
		        updateInfo = false;
	        }
	    },

	    tbar : {
	        xtype : 'panel',
	        border : false,
	        autoHeight : true,
	        layout : 'hbox',
	        padding : 5,

	        items : [ {
	            xtype : 'label',
	            style : 'font-weight: bold;',
	            margins : '2 5 0 5',
	            text : 'Дата:'
	        }, {
	            id : _date,
	            xtype : 'datefield',
	            name : 'fromDate',
	            margins : '2 5 0 5',
	            allowBlank : false,
	            format : 'd.m.Y'
	        }, {
	            xtype : 'label',
	            style : 'font-weight: bold;',
	            margins : '2 5 0 25',
	            text : 'Выбор тикера:'
	        }, futureSelect, {
	            xtype : 'button',
	            text : 'Х',
	            margins : '0 5',
	            width : 25,
	            handler : function() {
		            futureSelect.clearValue();
	            }
	        }, {
	            xtype : 'label',
	            style : 'font-weight: bold;',
	            margins : '2 5 0 25',
	            text : 'Расчёт портфеля:'
	        }, {
	            id : _calcInfo,
	            xtype : 'label',
	            // style : 'font-weight: bold;',
	            margins : '2 5 0 5',
	            text : 'не запущен'
	        } ],

	        buttonAlign : 'left',
	        buttons : [ {
	            text : 'Выбрать',
	            width : 100,
	            handler : show
	        }, ' ', {
	            text : 'Рассчитать',
	            width : 100,
	            handler : calcStart
	        }, {
	            text : 'Остановить',
	            width : 100,
	            handler : calcStop
	        }, ' ', {
	            text : 'Выгрузить в Excel',
	            width : 100,
	            handler : exportXls
	        }, ' ', {
	            text : 'Удалить остатки и финрез',
	            width : 120,
	            handler : deleteFinResAndRests
	        } ]
	    },

	    store : info,
	    columns : [ {
	        header : 'REPORT_DATE',
	        dataIndex : 'report_date',
	        renderer : App.util.Renderer.date()
	    }, {
	        header : 'CLIENT',
	        dataIndex : 'client'
	    }, {
	        header : 'FUND',
	        dataIndex : 'fund'
	    }, {
	        header : 'SECURITY_CODE',
	        dataIndex : 'security_code'
	    }, {
	        header : 'SHORT_NAME',
	        dataIndex : 'short_name'
	    }, {
	        header : 'BATCH',
	        dataIndex : 'batch',
	        align : 'right',
	        renderer : App.util.Renderer.number(0)
	    }, {
	        header : 'USD_FUNDING',
	        dataIndex : 'usd_funding'
	    }, {
	        header : 'CURRENCY',
	        dataIndex : 'currency'
	    }, {
	        header : 'QUANTITY',
	        dataIndex : 'quantity',
	        align : 'right',
	        renderer : App.util.Renderer.number(0)
	    }, {
	        header : 'AVG_PRICE',
	        dataIndex : 'avg_price',
	        align : 'right',
	        renderer : App.util.Renderer.number(6)
	    }, {
	        header : 'LAST_PRICE',
	        dataIndex : 'last_price',
	        align : 'right',
	        renderer : App.util.Renderer.number(6)
	    }, {
	        header : 'NKD',
	        dataIndex : 'nkd',
	        align : 'right',
	        renderer : App.util.Renderer.number(6)
	    }, {
	        header : 'POSITION',
	        dataIndex : 'position'
	    }, {
	        header : 'POSITION_REP_DATE',
	        dataIndex : 'position_rep_date',
	        align : 'right',
	        renderer : App.util.Renderer.number(6)
	    }, {
	        header : 'REVALUATION',
	        dataIndex : 'revaluation',
	        align : 'right',
	        renderer : App.util.Renderer.number(6)
	    } ],

	    viewConfig : {
	        forceFit : true,
	        emptyText : 'Записи не найдены'
	    }
	});
})();
