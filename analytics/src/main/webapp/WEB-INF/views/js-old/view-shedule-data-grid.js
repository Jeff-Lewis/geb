/**
 *  multi request param form  
 */

(function() {
	
	var _security = Ext.id(); 
	var _params = Ext.id(); 

	var store = new Ext.data.JsonStore({
		autoDestroy : true,
		root : 'sec',
		fields : [ 'id_sec', 'security_code' ]
	});

	var storePar = new Ext.data.JsonStore({
		autoDestroy : true,
		root : 'params',
		fields : [ 'param_id', 'code' ]
	});

	var sm = new Ext.grid.CheckboxSelectionModel();
	var smPar = new Ext.grid.CheckboxSelectionModel();
	
	
    var grid =new Ext.grid.GridPanel ({
        height:280,
        autoScroll: true,
        store: store,
        columns: [
            new Ext.grid.RowNumberer({width: 30}),
            sm,
            {
                header: 'BLOOMBERG_CODE',
                width: 50,
                dataIndex: 'security_code',
                sortable : true
            }
        ],
        viewConfig : {
            forceFit: true,
            emptyText: 'Записи не найдены'
        },
        sm : sm
    });

    
    var gridParam =new Ext.grid.GridPanel ({
   	 	height:280,
        autoScroll: true,
        store: storePar,
        columns: [
            new Ext.grid.RowNumberer({width: 30}),
            sm,
            {
                header: 'PARAMETER',
                width: 50,
                dataIndex: 'code',
           		sortable : true
            }
        ],
        viewConfig : {
            forceFit: true,
            emptyText: 'Записи не найдены'
        },
        sm : smPar
    });
    
    
    var statusView = new Ext.Panel({
        layout: 'table',
        defaults: {
            style: {
                padding: '5px'
            }
        },
        layoutConfig: {
            columns : 1
        },
        items: [
            {
                html: '<span style="color:#3764A0;font-weight:bold;font-size:11px;">FIELD_ID:</span>'
            },
           
            {
                xtype: 'displayfield',
                cls: 'z-title',
                name : 'field'
            }
        ]
    });

    
    
	var container = new Ext.FormPanel({
        id:'view-multi-request-data-grid-component',
		layout : 'absolute',
		frame : true,
		closable : true,
		title : 'MultiDataRequest<br/><b>',
		items : [ 
		         {
		        	 xtype : 'fieldset',
		        	 height:40,
		        	 x:0,
		        	 y:0,
		        	 border : false,
		        	 layout : 'column',
		        	 autoHeight : true,
		        	 items : [
								{
									xtype : 'button',
									text : 'Сформировать файл закачки по расписанию',
									handler : function(self) {
									var ids = '';
									var idsp= '';
									Ext.each(sm.getSelections(), function(item) {
										ids += ',' + item.data.security_code;
									});
									if (ids.length > 0)
										ids = ids.substr(1);

									Ext.each(smPar.getSelections(), function(item) {
										idsp += ',' + item.data.code;
									});
									if (idsp.length > 0)
										idsp = idsp.substr(1);
									
									if (ids == '') {
										App.ui.message('Необходимо галочками выбрать организации!');
									} else {
										if (idsp == '')
										{
											App.ui.message('Необходимо галочками выбрать параметры!');
										} else {
											
										Ext.Ajax.request( {
											url : 'shedule/create-bat.html',
											params : {
												security : ids,
												params: idsp
											},
											timeout : 10 * 60 * 1000, // 10 min
											waitMsg : 'Выполняется формирование файла',
											success : function(xhr) {
												var answer = Ext.decode(xhr.responseText);
												if (answer.success) {
													App.ui.message('Файл успешно сформирован!');
													container.loadData(answer);
										} else if (answer.code == 'login') {
											App.ui.sessionExpired();
										} else {
											App.ui.error(answer.message);
										}
									},
									failure : function() {
										App.ui.error('Сервер недоступен');
									}
										});
										} //else
								}//else
								}
								}
    	          ]
		         },
		         {
		        	 xtype : 'fieldset',
		        	 height:300,
		        	 width:300,
		        	 border : true,
		        	 x:0,
		        	 y:40,
		        	 items : grid
		         },		         
		        {
		        	 xtype : 'fieldset',
		        	 height:300,
		        	 width:420,
		        	 border : true,
		        	 x:315,
		        	 y:40,
		        	 items : gridParam
		         },
					{
		                xtype: 'displayfield',
			        	x:0,
			        	y:400,
//		                cls: 'z-title',
		                name : 'field'
					}

		         
		         ]
		         ,

		         	loadData : function(data) {
					this.setTitle("Формирование файла для запроса по расписанию");
					store.loadData(data);
					storePar.loadData(data);
					
					var inf = {};
					this.data = data;
					inf['field'] = data['bat'];
					 // Set values for the form
		            this.getForm().setValues(inf);
					
		}
	});


return container;

})();
