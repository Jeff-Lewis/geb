/**
 * Просмотр текущей модели - Детальная информация
 */
(function() {

	return new Ext.FormPanel({
		id : 'ViewModelInfo-component',
		frame : true,
		closable : true,
		autoScroll : true,

		header : false,
		labelWidth : 250,
		defaults : {
			style : {
				marginBottom : '5px'
			}
		},
		items : [ {
			xtype : 'form',
			defaults : {
				labelStyle : 'color:#3764A0;',
				xtype : 'displayfield',
				cls : 'z-title',
				width : 200,
				style : {
					padding : '5px'
				}
			},
			items : [ {
				fieldLabel : 'TargetPriceCons12M',
				name : 'targetPriceCons12M'
			}, {
				fieldLabel : 'TargetPriceDecCons',
				name : 'targetPriceDecCons'
			}, {
				fieldLabel : 'TargetPrice',
				name : 'targetPrice'
			} ]
		}, {
			xtype : 'form',
			frame : true,
			collapsible : true,
			autoScroll : true,
			title : 'MAIN INFORMATION',
			defaults : {
				xtype : 'displayfield'
			},
			items : [ {
				fieldLabel : 'BestPrice',
				name : 'bestPrice'
			}, {
				fieldLabel : 'r',
				name : 'r'
			}, {
				fieldLabel : 'teta',
				name : 'teta'
			}, {
				fieldLabel : 'PriceMedian',
				name : 'priceMedian'
			}, {
				fieldLabel : 'LastYearAvgWhtPrice',
				name : 'lastYearAvgWhtPrice'
			}, {
				fieldLabel : 'M1Q',
				name : 'M1Q'
			}, {
				fieldLabel : 'M2Q',
				name : 'M2Q'
			}, {
				fieldLabel : 'M3Q',
				name : 'M3Q'
			}, {
				fieldLabel : 'M4Q',
				name : 'M4Q'
			}, {
				fieldLabel : 'factEPS1Q',
				name : 'factEPS1Q'
			}, {
				fieldLabel : 'factEPS2Q',
				name : 'factEPS2Q'
			}, {
				fieldLabel : 'factEPS3Q',
				name : 'factEPS3Q'
			}, {
				fieldLabel : 'factEPS4Q',
				name : 'factEPS4Q'
			}, {
				fieldLabel : 'forecastEPS2Q',
				name : 'forecastEPS2Q'
			}, {
				fieldLabel : 'forecastEPS3Q',
				name : 'forecastEPS3Q'
			}, {
				fieldLabel : 'forecastEPS4Q',
				name : 'forecastEPS4Q'
			}, {
				fieldLabel : 'forecastEPS',
				name : 'forecastEPS'
			}, {
				fieldLabel : 'forecastEPS12M',
				name : 'forecastEPS12M'
			}, {
				fieldLabel : 'forecastEPS_NextYear',
				name : 'forecastEPS_NextYear'
			}, {
				fieldLabel : 'forecastEPS1QNext',
				name : 'forecastEPS1QNext'
			}, {
				fieldLabel : 'forecastEPS2QNext',
				name : 'forecastEPS2QNext'
			}, {
				fieldLabel : 'forecastEPS3QNext',
				name : 'forecastEPS3QNext'
			}, {
				fieldLabel : 'forecastEPS4QNext',
				name : 'forecastEPS4QNext'
			}, {
				fieldLabel : 'forecastEPScons',
				name : 'forecastEPScons'
			}, {
				fieldLabel : 'forecastEPScons12M',
				name : 'forecastEPScons12M'
			}, {
				fieldLabel : 'EPSttm',
				name : 'EPSttm'
			}, {
				fieldLabel : 'g5',
				name : 'g5'
			}, {
				fieldLabel : 'g10',
				name : 'g10'
			}, {
				fieldLabel : 'gk',
				name : 'gk'
			}, {
				fieldLabel : 'PE_5',
				name : 'pe_5'
			}, {
				fieldLabel : 'PE_10',
				name : 'pe_10'
			}, {
				fieldLabel : 'PE_current',
				name : 'pe_current'
			}, {
				fieldLabel : 'PE_ttm',
				name : 'pe_ttm'
			}, {
				fieldLabel : 'PE_cons',
				name : 'pe_cons'
			} ]
		} ],

		loadData : function(data) {
			this.setTitle("Детальная информация");

			// TODO заменить компонент
			var onclick = "showPanel('model/ViewModelPrice'," +
					" 'rest/ViewModel/" + data.item.id_sec + "/Price.do');";
			data.item['targetPriceCons12M'] = '<b><a onclick="' + onclick
					+ '" href="#">' + data.item['targetPriceCons12M']
					+ '</a></b>';

			if (data.item['factEPS3Q'] == null) {
				data.item.factEPS3Q = "0.00";
			}
			if (data.item['factEPS4Q'] == null) {
				data.item.factEPS4Q = "0.00";
			}

			if (data.item['forecastEPS2Q'] == null) {
				data.item.forecastEPS2Q = "0.00";
			}

			// Set values for the form
			this.getForm().setValues(data.item);
		}
	});

})();
