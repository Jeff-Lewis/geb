/**
 * Просмотр текущей модели - Детальная информация
 */
(function() {

	return {
	    xtype : 'panel',
	    id : 'ViewModelInfo-component',
	    title : 'Детальная информация',
	    layout : 'border',

	    defaults : {
		    labelWidth : 150
	    },
	    items : [ {
	        region : 'north',
	        height : 80,
	        xtype : 'form',
	        padding : 5,

	        defaults : {
	            xtype : 'displayfield',
	            labelStyle : 'color:#3764A0;',
	            cls : 'z-title'
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
	        region : 'center',
	        xtype : 'form',
	        title : 'MAIN INFORMATION',
	        frame : true,
	        collapsible : true,
	        autoScroll : true,

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

		    // TODO заменить компонент
		    var urlData = 'rest/ViewModel/' + data.item.id_sec + '/Price.do';
		    var onclick = "showPanel('model/ViewModelPrice', '" + urlData + "'); return false;";
		    data.item['targetPriceCons12M'] = '<b><a onclick="' + onclick + '" href="#">'
		            + data.item['targetPriceCons12M'] + '</a></b>';

		    if (!data.item['factEPS3Q']) {
			    data.item.factEPS3Q = "0.00";
		    }
		    if (!data.item['factEPS4Q']) {
			    data.item.factEPS4Q = "0.00";
		    }

		    if (!data.item['forecastEPS2Q']) {
			    data.item.forecastEPS2Q = "0.00";
		    }

		    // Set values for the form
		    cmp.items.get(0).getForm().setValues(data.item);
		    cmp.items.get(1).getForm().setValues(data.item);
	    }
	};
})();
