/**
 * Список компаний - info
 */
(function() {

	var id_sec = 0;

	var _winFiles = Ext.id();

	var _bloomCode = Ext.id();
	var _adr = Ext.id();
	var _currency = Ext.id();
	var _group = Ext.id();
	var _koef_0 = Ext.id();
	var _koef_1 = Ext.id();
	var _period = Ext.id();
	var _eps = Ext.id();

	var exStore = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : false,
		url : 'rest/Companies/0/Exceptions.do',
		// root : 'infoEx',
		fields : [ 'exception', 'comment' ]
	});

	var exGrid = new Ext.grid.GridPanel({
		region : 'center',
		title : 'Исключения',
		frame : true,
		enableHdMenu : false,

		tbar : [ {
	        text : 'Добавить',
	        menu : [ {
	            text : 'по темпу роста <b>EPS</b>',
	            handler : addEpsGrowth
	        }, {
	            text : 'по темпу роста <b>BOOK_VAL_PER_SH</b>',
	            handler : addBvGrowth
	        }, '-', {
	            text : 'параметров по формулам',
	            handler : addVariableFormula
	        } ]
	    }, {
	        text : 'Удалить',
	        menu : [ {
	            text : 'по темпу роста <b>EPS</b>',
	            handler : delEpsGrowth
	        }, {
	            text : 'по темпу роста <b>BOOK_VAL_PER_SH</b>',
	            handler : delBvGrowth
	        }, '-', {
	            text : 'параметров по формулам',
	            handler : delVariableFormula
	        } ]
	    } ],

		store : exStore,
		columns : [ {
			header : 'Exception',
			dataIndex : 'exception'
		}, {
			header : 'Comment',
			dataIndex : 'comment'
		} ],

		viewConfig : {
			forceFit : true,
			stripeRows : true,
			emptyText : 'Записи не найдены'
		},

		listeners : {
			rowdblclick : function(grid, rowIndex, e) {
				var text = grid.getStore().getAt(rowIndex).data.comment;
				text = Ext.util.Format.htmlEncode(text);
				text = Ext.util.Format.nl2br(text);
				if (text) {
					App.ui.message(text);
				}
			}
		}
	});

	var quarters = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : false,
		url : 'rest/Companies/0/Quarters.do',
		// root : 'info1',
		fields : [ 'period', 'value', 'crnc', 'date', 'eqy_dps',
				'eqy_dvd_yld_ind', 'sales_rev_turn', 'prof_margin',
				'oper_margin', 'crnc' ]
	});

	var smQuarters = new Ext.grid.RowSelectionModel({
		singleSelect : true
	});

	function delQuarter() {
		if (smQuarters.getCount() == 0) {
			App.ui.message('Необходимо выбрать период.');
			return;
		}

		var period = smQuarters.getSelected().data.period;
		App.ui.confirm('Удалить квартальный период "' + period + '"?', ajaxDelQuarter);
    }
	function ajaxDelQuarter() {
		var period = smQuarters.getSelected().data.period;
		App.ui.message('Удаление квартального периода "' + period + '"');
    }

	var quarterGrid = new Ext.grid.GridPanel({
		title : 'Показатели по кварталам',
		frame : true,
		//autoHeight : true,
		height : 550,
		collapsible : true,
		collapsed : false,
		enableHdMenu : false,

		tbar : [ {
			text : 'Удалить',
			handler : delQuarter
	    } ],

	    store : quarters,
	    sm : smQuarters,
		columns : [ {
			header : 'Период',
			dataIndex : 'period'
		}, {
			header : 'Валюта (отчетности)'
		}, {
			header : 'IS_EPS'
		}, {
			header : 'IS_COMP_EPS_ADJUSTED'
		}, {
			header : 'SALES_REV_TURN'
		}, {
			header : 'EBITDA'
		}, {
			header : 'OPER_MARGIN'
		}, {
			header : 'PROF_MARGIN'
		}, {
			header : 'OPER_ROE'
		}/*, {
			header : 'ПЕРИОД',
			dataIndex : 'period'
		}, {
			header : 'EPS',
			dataIndex : 'value',
			align : 'right',
			renderer : App.util.Renderer.number(6)
		}, {
			header : 'EQY_DPS',
			dataIndex : 'eqy_dps',
			align : 'right',
			renderer : App.util.Renderer.number(6)
		}, {
			header : 'EQY_DVD_YLD_IND',
			dataIndex : 'eqy_dvd_yld_ind',
			align : 'right',
			renderer : App.util.Renderer.number(6)
		}, {
			header : 'SALES_REV_TURN',
			dataIndex : 'sales_rev_turn',
			align : 'right',
			renderer : App.util.Renderer.number(6)
		}, {
			header : 'PROF_MARGIN',
			dataIndex : 'prof_margin',
			align : 'right',
			renderer : App.util.Renderer.number(6)
		}, {
			header : 'OPER_MARGIN',
			dataIndex : 'oper_margin',
			align : 'right',
			renderer : App.util.Renderer.number(6)
		}, {
			header : 'Валюта',
			dataIndex : 'crnc'
		}*/ ],
		viewConfig : {
			forceFit : true,
			stripeRows : true,
			emptyText : 'Записи не найдены'
		}
	});

	var years = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : false,
		url : 'rest/Companies/0/Years.do',
		// root : 'info2',
		fields : [ 'period', 'value', 'crnc', 'date', 'eps_recon_flag',
				'eqy_dps', 'eqy_weighted_avg_px', 'eqy_weighted_avg_px_adr',
				'book_val_per_sh', 'oper_roe', 'r_ratio', 'crnc' ]
	});

	var smYears = new Ext.grid.RowSelectionModel({
		singleSelect : true
	});

	function delYears() {
		if (smYears.getCount() == 0) {
			App.ui.message('Необходимо выбрать период.');
			return;
		}

		var period = smYears.getSelected().data.period;
		App.ui.confirm('Удалить годовой период "' + period + '"?', ajaxDelYears);
    }
	function ajaxDelYears() {
		var period = smYears.getSelected().data.period;
		App.ui.message('Удаление годового периода "' + period + '"');
    }

	var yearsGrid = new Ext.grid.GridPanel({
		title : 'Показатели по годам',
		frame : true,
		//autoHeight : true,
		height : 550,
		collapsible : true,
		collapsed : false,
		enableHdMenu : false,

		tbar : [ {
			text : 'Удалить',
			handler : delYears
	    } ],

		store : years,
		sm : smYears,
		columns : [ {
			header : 'Период',
			dataIndex : 'period'
		}, {
			header : 'Валюта (отчетности)'
		}, {
			header : 'IS_EPS'
		}, {
			header : 'IS_COMP_EPS_ADJUSTED'
		}, {
			header : 'SALES_REV_TURN'
		}, {
			header : 'EBITDA'
		}, {
			header : 'OPER_MARGIN'
		}, {
			header : 'PROF_MARGIN'
		}, {
			header : 'OPER_ROE'
		}/*, {
			header : 'ПЕРИОД',
			dataIndex : 'period'
		}, {
			header : 'EPS',
			dataIndex : 'value',
			align : 'right',
			renderer : App.util.Renderer.number(6)
		}, {
			header : 'EPS_RECONTR_FLAG',
			dataIndex : 'eps_recon_flag',
			align : 'right'
		}, {
			header : 'EQY_DPS',
			dataIndex : 'eqy_dps',
			align : 'right',
			renderer : App.util.Renderer.number(6)
		}, {
			header : 'EQY_WEIGHTED_AVG_PX',
			dataIndex : 'eqy_weighted_avg_px',
			align : 'right',
			renderer : App.util.Renderer.number(6)
		}, {
			header : 'EQY_WEIGHTED_AVG_PX_ADR',
			dataIndex : 'eqy_weighted_avg_px_adr',
			align : 'right',
			renderer : App.util.Renderer.number(6)
		}, {
			header : 'BOOK_VAL_PER_SH',
			dataIndex : 'book_val_per_sh',
			align : 'right',
			renderer : App.util.Renderer.number(6)
		}, {
			header : 'OPER_ROE',
			dataIndex : 'oper_roe',
			align : 'right',
			renderer : App.util.Renderer.number(6)
		}, {
			header : 'RETENTION_RATIO',
			dataIndex : 'r_ratio',
			align : 'right',
			renderer : App.util.Renderer.number(6)
		}, {
			header : 'Валюта',
			dataIndex : 'crnc'
		}*/ ],
		viewConfig : {
			forceFit : true,
			stripeRows : true,
			emptyText : 'Записи не найдены'
		}
	});

	var files = new Ext.data.JsonStore({
		autoDestroy : true,
		autoLoad : false,
		url : 'rest/Companies/0/Files.do',
		// root : 'file',
		fields : [ 'id_doc', 'file_type', 'file_name', 'insert_date' ]
//id_doc		22
//file_type	"text/xml"
//file_name	"EclipseFormatter.xml"
//insert_date	"2014-03-27 10:09:41.3"
	});

	var smFiles = new Ext.grid.RowSelectionModel({
		singleSelect : true
	});

	var winFiles = null;

	function refresh(id_sec) {
		showPanel('company/CompaniesInfo', 'rest/Companies/' + id_sec + '.do');
	}

	function fileSubmit(self) {
		if (!Ext.getCmp(_winFiles).items.itemAt(0).getValue()) {
			App.ui.message('Выберите файл.');
			return;
		}

		Ext.getCmp(_winFiles).getForm().submit({
			url : 'rest/Companies/' + id_sec + '/Upload.do',
			waitMsg : 'Загрузка файла.',
			timeout : 10 * 60 * 1000,
			success : function(form, action) {
				winFiles.close();
				files.reload();
			},
			failure : function(form, action) {
				winFiles.close();
				files.reload();

				// switch (action.failureType) {
				// case Ext.form.Action.CONNECT_FAILURE:
				// App.ui.error('Сервер недоступен');
				// break;
				// case Ext.form.Action.SERVER_INVALID:
				// App.ui.error(action.result.msg);
				// break;
				// }
			}
		});
	}

	function fileAdd(self) {
		winFiles = new Ext.Window({
			title : 'Загрузить файл',
			width : 310,
			height : 100,
			plain : true,
			modal : true,
			border : false,
			layout : 'fit',

			items : [ {
				id : _winFiles,
				xtype : 'form',
				baseCls : 'x-plain',
				padding : 5,
				fileUpload : true,

				items : [ {
					hideLabel : true,
					xtype : 'fileuploadfield',
					name : 'upload',
					buttonText : 'Выбрать файл',
					width : 285
				} ]
			} ],

			buttons : [ {
				text : 'Загрузить',
				handler : fileSubmit
			}, {
				text : 'Отмена',
				handler : function() {
					winFiles.close();
				}
			} ]
		});
		winFiles.doLayout();
		winFiles.show();
	}

	function fileOpen(self) {
		if (smFiles.getCount() == 0) {
			App.ui.message('Выберите файл для сохранения.');
			return;
		}

		var id = smFiles.getSelected().data.id_doc;
		window.open('rest/Companies/' + id_sec + '/Files/' + id + '.do');
	}

	function fileDelete(self) {
		if (smFiles.getCount() == 0) {
			App.ui.message('Выберите файл для удаления.');
			return;
		}

		var name = smFiles.getSelected().data.file_name;
		App.ui.confirm('Удалить файл ' + name + '?', fileDeleteAjax);
	}
	function fileDeleteAjax() {
		var id = smFiles.getSelected().data.id_doc;
		Ext.Ajax.request({
			method : 'DELETE',
			url : 'rest/Companies/' + id_sec + '/Files/' + id + '.do',
			// timeout : 10 * 60 * 10000, // 10 min
			timeout : 1000000000,
			waitMsg : 'Удаление файла.',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					files.reload();
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	var fileGrid = new Ext.grid.GridPanel({
		title : 'Прикреплённые документы',
		frame : true,
		autoHeight : true,
		collapsible : true,
		collapsed : false,
		enableHdMenu : false,

		tbar : [ {
			text : 'Загрузить',
			handler : fileAdd
		}, {
			text : 'Сохранить',
			handler : fileOpen
		}, {
			text : 'Удалить',
			handler : fileDelete
		} ],

		store : files,
		selModel : smFiles,
		columns : [ new Ext.grid.RowNumberer({
			width : 30
		}), {
			header : 'Дата',
			dataIndex : 'insert_date',
			width : 50,
			renderer : App.util.Renderer.datetime()
		}, {
			header : 'Файл',
			dataIndex : 'file_name'
		} ],
		viewConfig : {
			forceFit : true,
			stripeRows : true,
			emptyText : 'Записи не найдены'
		}
	});

	function equityChange(self) {
		Ext.Ajax.request({
			url : 'rest/Companies/' + id_sec + '/EquityChange.do',
			params : {
				bloomCode : Ext.getCmp(_bloomCode).getValue(),
				adr : Ext.getCmp(_adr).getValue(),
				currency_calc : Ext.getCmp(_currency).getValue(),
				group : Ext.getCmp(_group).getValue(),
				koefZero : Ext.getCmp(_koef_0).getValue(),
				koefOne : Ext.getCmp(_koef_1).getValue(),
				period : Ext.getCmp(_period).getValue(),
				eps : Ext.getCmp(_eps).getValue()
			},
			timeout : 1000000000,
			waitMsg : 'Сохранение.',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					refresh(id_sec);
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	function calculateEPS(self) {
		Ext.Ajax.request({
			url : 'rest/Companies/' + id_sec + '/CalculateEps.do',
			timeout : 1000000000,
			waitMsg : 'Рачёт EPS.',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					refresh(id_sec);
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	function buildModel(self) {
		Ext.Ajax.request({
			url : 'rest/Companies/' + id_sec + '/BuildModel.do',
			timeout : 1000000000,
			waitMsg : 'Расчёт модели.',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					refresh(id_sec);
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	function delHistData(self) {
		Ext.Ajax.request({
			method : 'DELETE',
			url : 'rest/Companies/' + id_sec + '/HistData.do',
			timeout : 1000000000,
			waitMsg : 'Расчёт модели.',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					refresh(id_sec);
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
    }

	function executeBloom() {
		Ext.Ajax.request({
			url : 'rest/CompanyAdd/Bloom.do',
			params : {
				codes : Ext.getCmp(_bloomCode).getValue()
			},
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Загрузка Bloomberg...',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					App.ui.message('Данные загружены.');
					//data.loadData(answer.item);
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	var leftInfoForm = new Ext.form.FormPanel({
		region : 'west',
		title : 'Основная информация',
		width : 420,
		padding : 10,
		frame : true,
		border : true,
		labelWidth : 180,
		defaults : {
			labelStyle : 'color:#3764A0;',
			xtype : 'displayfield',
			cls : 'z-title',
			width : 200
		},

		items : [ {
			fieldLabel : 'ISIN',
			name : 'isin'
		}, {
			fieldLabel : 'Название компании',
			name : 'security_name'
		}, {
			id : _bloomCode,
			fieldLabel : 'Код Блумберг',
			xtype : 'textfield',
			name : 'security_code'
		}, {
			fieldLabel : 'Родной тикер',
			name : 'ticker'
		}, {
			id : _adr,
			fieldLabel : 'АДР',
			xtype : 'numberfield',
			decimalPrecision : 4,
			name : 'adr'
		}, {
			id : _currency,
			fieldLabel : 'Валюта расчёта',
			name : 'currency',
			xtype : 'combo',
			displayField : 'name',
			valueField : 'name',
			store : new Ext.data.JsonStore({
				autoDestroy : true,
				url : 'rest/Companies/Currencies.do',
				// root : 'info',
				fields : [ 'name' ],
				sortInfo : {
					field : 'name'
				}
			}),
			loadingText : 'Поиск...',
			minChars : 1,
			triggerAction : 'all'
		}, {
			fieldLabel : 'Сектор',
			name : 'indstry_grp'
		}, {
			id : _group,
			fieldLabel : 'Группа сводной',
			name : 'svod_grp',
			xtype : 'combo',
			displayField : 'name',
			valueField : 'name',
			store : new Ext.data.JsonStore({
				autoDestroy : true,
				url : 'rest/Companies/GroupSvod.do',
				// root : 'info',
				fields : [ 'name' ],
				sortInfo : {
					field : 'name'
				}
			}),
			minChars : 1,
			triggerAction : 'all'
		}, {
			id : _koef_0,
			fieldLabel : 'Koef Upside',
			xtype : 'numberfield',
			decimalPrecision : 6,
			name : 'koefUpside'
		}, {
			id : _koef_1,
			fieldLabel : 'Koef Upside н.м.',
			xtype : 'numberfield',
			decimalPrecision : 6,
			name : 'koefUpsideNM'
		}, {
			id : _period,
			fieldLabel : 'Периодичность отчётности',
			name : 'period',
			xtype : 'combo',
			displayField : 'name',
			valueField : 'name',
			store : new Ext.data.JsonStore({
				autoDestroy : true,
				url : 'rest/Companies/Period.do',
				// root : 'info',
				fields : [ 'name' ],
				sortInfo : {
					field : 'name'
				}
			}),
			loadingText : 'Поиск...',
			triggerAction : 'all',
			editable : false
		}, {
			id : _eps,
			fieldLabel : 'EPS',
			name : 'eps',
			xtype : 'combo',
			displayField : 'name',
			valueField : 'name',
			store : new Ext.data.JsonStore({
				autoDestroy : true,
				url : 'rest/Companies/Eps.do',
				// root : 'info',
				fields : [ 'name' ],
				sortInfo : {
					field : 'name'
				}
			}),
			loadingText : 'Поиск...',
			minChars : 2,
			triggerAction : 'all'
		}, {
			fieldLabel : 'Валюта торгов CRNCY',
			name : 'currencyTrade'
		}, {
			fieldLabel : 'Валюта отчетности EQY_FUND_CRNCY',
			name : 'currencyReport'
		}, {
			fieldLabel : 'PX_LAST',
			name : 'px_last'
		} ]
	});

	var rightInfoForm1 = new Ext.form.FormPanel({
		region : 'north',
		title : 'Текущий расчёт',
		padding : 10,
		frame : true,
		border : true,
		autoHeight : true,

		labelWidth : 70,
		defaults : {
			labelStyle : 'color:#3764A0;font-size:11px;',
			xtype : 'displayfield',
			cls : 'z-title',
			width : 150
		},

		items : [ {
			fieldLabel : 'g10',
			name : 'g10'
		}, {
			fieldLabel : 'g5',
			name : 'g5'
		}, {
			fieldLabel : 'b10',
			name : 'b10'
		}, {
			fieldLabel : 'b5',
			name : 'b5'
		}, {
			fieldLabel : 'PE10',
			name : 'pe10'
		}, {
			fieldLabel : 'PE5',
			name : 'pe5'
		}, {
			fieldLabel : 'PE current',
			name : 'peCurrent'
		} ]
	});

	var rightInfoForm2 = new Ext.form.FormPanel({
		region : 'north',
		title : 'Расчётные таргеты',
		padding : 10,
		frame : true,
		border : true,
		autoHeight : true,

		labelWidth : 70,
		defaults : {
			labelStyle : 'color:#3764A0;font-size:11px;',
			xtype : 'displayfield',
			cls : 'z-title',
			width : 150
		},

		items : [ {
			fieldLabel : 'По старой методике',
			name : 'methodOld'
		}, {
			fieldLabel : 'По новой методике',
			name : 'methodNew'
		}, {
			fieldLabel : 'CONSENSUS',
			name : 'consensus'
		}, {
			fieldLabel : 'ROE',
			name : 'roe'
		} ]
	});

	function addEpsGrowth(self) {
		showModal('company/CompaniesEpsAdd', 'rest/Companies/' + id_sec + '/id.do');
	}

	function delEpsGrowth() {
		Ext.Msg.show({
			title : 'Удаление исключения EPS',
			msg : 'Тип',
			prompt : true,
			minWidth : 300,
			buttons : {
				ok : 'Удалить',
				cancel : true
			},
			fn : deleteEpsGrowth
		});
	}
	function deleteEpsGrowth(btn, text) {
		if (btn != 'ok') {
			return;
		}

		if (!text) {
			App.ui.error('Тип не указан.');
			return;
		}

		Ext.Ajax.request({
			method : 'DELETE',
			url : 'rest/Companies/' + id_sec + '/eps.do?type=' + text,
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Удаление исключения.',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					App.ui.message('Исключение удалено.');
					exStore.reload();
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	function addBvGrowth(self) {
		showModal('company/CompaniesBvAdd', 'rest/Companies/' + id_sec + '/id.do');
	}

	function delBvGrowth() {
		Ext.Msg.show({
			title : 'Удаление исключения BOOK_VAL_PER_SH',
			msg : 'Тип',
			prompt : true,
			minWidth : 300,
			buttons : {
				ok : 'Удалить',
				cancel : true
			},
			fn : deleteBvGrowth
		});
	}
	function deleteBvGrowth(btn, text) {
		if (btn != 'ok') {
			return;
		}

		if (!text) {
			App.ui.error('Тип не указан.');
			return;
		}

		Ext.Ajax.request({
			method : 'DELETE',
			url : 'rest/Companies/' + id_sec + '/bv.do?type=' + text,
			timeout : 10 * 60 * 1000, // 10 min
			waitMsg : 'Удаление исключения.',
			success : function(xhr) {
				var answer = Ext.decode(xhr.responseText);
				if (answer.success) {
					App.ui.message('Исключение удалено.');
					exStore.reload();
				}
			},
			failure : function() {
				App.ui.error('Сервер недоступен');
			}
		});
	}

	function addVariableFormula(self) {
		showModal('company/CompaniesExpressionAdd', 'rest/Companies/' + id_sec + '/id.do');
	}

	function delVariableFormula(self) {
		var comboVariables = new Ext.form.ComboBox({
			fieldLabel : 'Переменная',
			width : 270,
			hiddenName : 'period',
			valueField : 'name',
			displayField : 'name',
			valueField : 'name',
			store : new Ext.data.JsonStore({
				autoDestroy : true,
				url : 'rest/Companies/Variables.do',
				// root : 'info',
				fields : [ 'name' ]
			}),
			allowBlank : true,
			emptyText : 'Выберите переменную',
			loadingText : 'Поиск...',
			triggerAction : 'all'
		});

		var window = new Ext.Window({
			title : 'Удаление исключения по формулам',
			plain : true,
			modal : true,
			width : 300,
			autoHeight : true,
			padding : 10,

			items : [ {
				xtype : 'label',
				cls : 'ext-mb-text',
				text : 'Переменная'
			}, comboVariables ],

			buttonAlign : 'center',
			buttons : [ {
				text : 'Удалить',
				handler : deleteVariable
			}, {
				text : 'Отмена',
				handler : closeVariable
			} ],
		});
		window.doLayout();
		window.show();

		function deleteVariable() {
			var text = comboVariables.getValue();

			if (!text) {
				App.ui.error('Переменная не указан.');
				return;
			}

			window.close();

			Ext.Ajax.request({
				method : 'DELETE',
				url : 'rest/Companies/' + id_sec + '/formula.do?variable=' + text,
				timeout : 10 * 60 * 1000, // 10 min
				waitMsg : 'Удаление исключения',
				success : function(xhr) {
					var answer = Ext.decode(xhr.responseText);
					if (answer.success) {
						App.ui.message('Исключение удалено.');
						exStore.reload();
					}
				},
				failure : function() {
					App.ui.error('Сервер недоступен');
				}
			});
		}

		function closeVariable() {
			window.close();
		}
	}

	return new Ext.Panel({
	    id : 'CompaniesInfo-component',
	    title : 'Компания',
	    frame : false,
	    baseCls : 'x-plain',
	    closable : true,
	    autoScroll : true,

	    tbar : [ {
	        text : 'Сохранить',
	        handler : equityChange
	    }, {
	        text : 'Выполнить',
	        menu : [ {
	            text : 'Посчитать EPS',
	            handler : calculateEPS
	        }, {
	            text : 'Построить модель',
	            handler : buildModel
	        }, {
	            text : 'Удалить исторические данные',
	            handler : delHistData
	        }, {
				text : 'Загрузить Bloomberg',
				handler : executeBloom
	        } ]
	    } ],

	    items : [ {
	        xtype : 'panel',
	        baseCls : 'x-plain',
	        height : 450,
	        layout : 'border',

	        items : [ leftInfoForm, {
	            region : 'center',
	            xtype : 'container',
	            width : 500,
	            layout : 'border',

	            items : [ {
	        		region : 'north',
	        		xtype : 'container',
	        		height : 230,
	        		layout : 'hbox',
	        		layoutConfig : {
	        			align : 'stretch'
	        		},

	        		items : [ rightInfoForm1, rightInfoForm2 ]
	        	}, exGrid ]
	        } ]
	    }, yearsGrid, quarterGrid, fileGrid ],

	    loadData : function(data) {
		    id_sec = data.item.id_sec;

		    this.setTitle('Компания: ' + data.item.security_name);

		    if (yearsGrid.collapsed) {
			    yearsGrid.expand(false);
		    }
		    if (quarterGrid.collapsed) {
			    quarterGrid.expand(false);
		    }
		    if (fileGrid.collapsed) {
			    fileGrid.expand(false);
		    }

		    var url;

		    url = 'rest/Companies/' + id_sec + '/Exceptions.do';
		    exStore.proxy.setUrl(url, true);
		    exStore.reload();

		    url = 'rest/Companies/' + id_sec + '/Years.do';
		    years.proxy.setUrl(url, true);
		    years.reload();

		    url = 'rest/Companies/' + id_sec + '/Quarters.do';
		    quarters.proxy.setUrl(url, true);
		    quarters.reload();

		    url = 'rest/Companies/' + id_sec + '/Files.do';
		    files.proxy.setUrl(url, true);
		    files.reload();

		    leftInfoForm.getForm().setValues(data.item);
		    rightInfoForm1.getForm().setValues(data.item);
		    rightInfoForm2.getForm().setValues(data.item);
	    },

	    refreshEx : function() {
		    exStore.reload();
	    }
	});
})();
