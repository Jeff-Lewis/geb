/**
 * Dictionary
 */

(function() {

	var store = new Ext.data.GroupingStore( {
		autoDestroy : true,
		reader : new Ext.data.JsonReader( {
			root : 'info',
			fields : [ 'DateReport',
			           'CountContracts',
			           'Emitent',
			           'Emitent2',
			           'Course',
			           'Currency',
			           'PriceOpenPos',
			           'Seb',
			           'Price1Share',
			           'CountPosDate',
			           'ChangePos',
			           'FinRes',
			           'ATH_Reward',
			           'ATH_Value',
			           'All_Time_High',
			           'StoppLoss',
			           'Mkt_Val_Last',
			           'PL',
			           'PL_with_StopLoss',
			           'StoppLoss_Upside',
			           'Risk_Value',
			           'Risk' ]
		})
	});

	var periodSelect = new Ext.form.ComboBox( {
		fieldLabel : 'Дата отчёта',
		width : 150,
		hiddenName : 'codeDate',
		valueField : 'dated',
		store : new Ext.data.JsonStore({
			autoDestroy : true,
			url : 'portfolio/dates.html',
			root : 'dates',
			fields: [ 'dated' ]
		}),
		allowBlank : false,
		emptyText : 'Выберите дату отчёта',
		displayField : 'dated',
		loadingText : 'Поиск...',
		triggerAction : 'all',
		//multiSelect: true
		//editable : false
	});
	
	var cm = new Ext.grid.ColumnModel({
		columns : [
					{
						header : 'DateReport',
						dataIndex : 'DateReport',
						sortable : true,
						width : 20,
					},
		           {
						header : 'CountContracts',
						dataIndex : 'CountContracts',
						sortable : true,
						width : 20,
					},
					{
						header : 'Emitent',
						dataIndex : 'Emitent',
						sortable : true,
						width : 20,
					},
					{
						header : 'Emitent2',
						dataIndex : 'Emitent2',
						sortable : true,
						width : 20,
					},
					{
						header : 'Course',
						dataIndex : 'Course',
						sortable : true,
						width : 20,
					},

					{
						header : 'Currency',
						dataIndex : 'Currency',
						sortable : true,
						width : 20,
					},
					{
						header : 'PriceOpenPos',
						dataIndex : 'PriceOpenPos',
						sortable : true,
						width : 20,
					},
					{
						header : 'Seb',
						dataIndex : 'Seb',
						sortable : true,
						width : 20,
					},

					{
						header : 'Price1Share',
						dataIndex : 'Price1Share',
						sortable : true,
						width : 20,
					},
					
					{
						header : 'CountPosDate',
						dataIndex : 'CountPosDate',
						sortable : true,
						width : 20,
					},
					{
						header : 'ChangePos',
						dataIndex : 'ChangePos',
						sortable : true,
						width : 20,
					},
					{
						header : 'FinRes',
						dataIndex : 'FinRes',
						sortable : true,
						width : 20,
					},

					{
						header : 'ATH_Reward',
						dataIndex : 'ATH_Reward',
						sortable : true,
						width : 20,
					},
					{
						header : 'ATH_Value',
						dataIndex : 'ATH_Value',
						sortable : true,
						width : 20,
					},
					{
						header : 'All_Time_High',
						dataIndex : 'All_Time_High',
						sortable : true,
						width : 20,
					},
					
					
		           {
					header : 'StoppLoss',
					dataIndex : 'StoppLoss',
					sortable : true,
					width : 20,
				},
				{
					header : 'Mkt_Val_Last',
					dataIndex : 'Mkt_Val_Last',
					width : 20
				},
				{
					header : 'PL',
					dataIndex : 'PL',
					width : 20,
					sortable:true
				},
				{
					header : 'PL_with_StopLoss',
					dataIndex : 'PL_with_StopLoss',
					width : 20
				},
				{
					header : 'StoppLoss_Upside',
					dataIndex : 'StoppLoss_Upside',
					width : 20
				},
				{
					header : 'Risk_Value',
					dataIndex : 'Risk_Value',
					width : 20
				},
				{
					header : 'Risk',
					dataIndex : 'Risk',
					width : 20,
					editor : new Ext.form.NumberField({
					})
				}
		 ]
	});
	
	
	var grid  =  new Ext.grid.EditorGridPanel(
			{
				id : 'view-cur-portfolio-component',
				frame : true,
				closable : true,
				autoScroll : true,
				enableHdMenu : false,
				store : store,
				cm : cm,
				tbar : [
				periodSelect
				],
				viewConfig : {
					forceFit : true,
					emptyText : 'Записи не найдены'
				},
				listeners : {
					afteredit : function(e){
					if (cm.getDataIndex(e.column)=='Risk'){
						this.getStore().clearFilter();
						this.getStore().filter('DateReport', periodSelect.getValue(), true, false);

						Ext.Ajax.request( {                    	
                            url: 'portfolio/recalculate.html',
                            waitMsg: 'Пересчёт портфеля',
                            params : {
								security : e.record.get('Emitent2'),
								date : periodSelect.getValue(),
								risk : e.value 
								},
                            timeout: 30000,
                            success: function(form, action) {
                            	App.ui.message('Пересчёт портфеля с учётом риска завершен!', false,
                            			menu.showCurrentPortfolio);
                            failure: function(form, action) {
                                if (action.failureType == 'server') {
                                    var msg = Ext.decode(action.response.responseText);
                                    App.ui.error(msg.message.replace(/\n/g, '<br/>'));
                                } else if (action.failureType == 'connect') {
                                	App.ui.error('Сервер недоступен');
                                } else {
                                	App.ui.error('Неизвестная ошибка');
                                }
                            }
                            }
                        });        	   		

						
						
						}
				
					}
				
				},
				loadData : function(data) {
					this.setTitle('Текущий портфель');
					this.getStore().loadData(data);
				}
				
			});
	return grid;
})();