/**
 * Organization Info view
 */

(function() {

	var statusView = new Ext.Panel(
			{
				layout : 'table',
				defaults : {
					style : {
						padding : '5px'
					}
				},
				layoutConfig : {
					columns : 2
				},
				items : [
						{
							html : '<span style="color:#3764A0;font-weight:bold;font-size:11px;">TargetPriceCons12M:</span>'
						},
						{
							xtype : 'displayfield',
							cls : 'z-title',
							name : 'TargetPriceCons12M'
						},
						{
							html : '<span style="color:#3764A0;font-weight:bold;font-size:11px;">TargetPriceDecCons:</span>'
						},
						{
							xtype : 'displayfield',
							cls : 'z-title',
							name : 'TargetPriceDecCons'
						},
						{
							html : '<span style="color:#3764A0;font-weight:bold;font-size:11px;">TargetPrice:</span>'
						}, {
							xtype : 'displayfield',
							cls : 'z-title',
							name : 'TargetPrice'
						} ]
			});

	var container = new Ext.FormPanel(
			{
				id : 'ViewModelInfo-component',
				frame : true,
				closable : true,
				header : false,
				autoScroll : true,
				labelWidth : 250,
				defaults : {
					style : {
						marginBottom : '5px'
					}
				},
				items : [ statusView, {
					xtype : 'panel',
					frame : true,
					collapsible : true,
					title : 'MAIN INFORMATION',
					items : [ {
						xtype : 'fieldset',
						autoHeight : true,
						items : [ {
							xtype : 'displayfield',
							fieldLabel : 'BestPrice',
							name : 'BestPrice',
							width : 400,
							readOnly : true
						}, {
							xtype : 'displayfield',
							fieldLabel : 'r',
							name : 'r',
							width : 400,
							readOnly : true
						},

						{
							xtype : 'displayfield',
							fieldLabel : 'teta',
							name : 'teta',
							width : 400,
							readOnly : true
						}, {
							xtype : 'displayfield',
							fieldLabel : 'PriceMedian',
							name : 'PriceMedian',
							width : 400,
							readOnly : true
						}, {
							xtype : 'displayfield',
							fieldLabel : 'LastYearAvgWhtPrice',
							name : 'LastYearAvgWhtPrice',
							width : 400,
							readOnly : true
						},

						{
							xtype : 'displayfield',
							fieldLabel : 'M1Q',
							name : 'M1Q',
							width : 400,
							readOnly : true
						}, {
							xtype : 'displayfield',
							fieldLabel : 'M2Q',
							name : 'M2Q',
							width : 400,
							readOnly : true
						}, {
							xtype : 'displayfield',
							fieldLabel : 'M3Q',
							name : 'M3Q',
							width : 400,
							readOnly : true
						}, {
							xtype : 'displayfield',
							fieldLabel : 'M4Q',
							name : 'M4Q',
							width : 400,
							readOnly : true
						},

						{
							xtype : 'displayfield',
							fieldLabel : 'factEPS1Q',
							name : 'factEPS1Q',
							width : 400,
							readOnly : true
						},

						{
							xtype : 'displayfield',
							fieldLabel : 'factEPS2Q',
							name : 'factEPS2Q',
							width : 400,
							readOnly : true
						},

						{
							xtype : 'displayfield',
							fieldLabel : 'factEPS3Q',
							name : 'factEPS3Q',
							width : 400,
							readOnly : true
						},

						{
							xtype : 'displayfield',
							fieldLabel : 'factEPS4Q',
							name : 'factEPS4Q',
							width : 400,
							readOnly : true
						},

						{
							xtype : 'displayfield',
							fieldLabel : 'forecastEPS2Q',
							name : 'forecastEPS2Q',
							width : 400,
							readOnly : true
						},

						{
							xtype : 'displayfield',
							fieldLabel : 'forecastEPS3Q',
							name : 'forecastEPS3Q',
							width : 400,
							readOnly : true
						}, {
							xtype : 'displayfield',
							fieldLabel : 'forecastEPS4Q',
							name : 'forecastEPS4Q',
							width : 400,
							readOnly : true
						}, {
							xtype : 'displayfield',
							fieldLabel : 'forecastEPS',
							name : 'forecastEPS',
							width : 400,
							readOnly : true
						}, {
							xtype : 'displayfield',
							fieldLabel : 'forecastEPS12M',
							name : 'forecastEPS12M',
							width : 400,
							readOnly : true
						},

						{
							xtype : 'displayfield',
							fieldLabel : 'forecastEPS_NextYear',
							name : 'forecastEPS_NextYear',
							width : 400,
							readOnly : true
						}, {
							xtype : 'displayfield',
							fieldLabel : 'forecastEPS1QNext',
							name : 'forecastEPS1QNext',
							width : 400,
							readOnly : true
						}, {
							xtype : 'displayfield',
							fieldLabel : 'forecastEPS2QNext',
							name : 'forecastEPS2QNext',
							width : 400,
							readOnly : true
						},

						{
							xtype : 'displayfield',
							fieldLabel : 'forecastEPS3QNext',
							name : 'forecastEPS3QNext',
							width : 400,
							readOnly : true
						}, {
							xtype : 'displayfield',
							fieldLabel : 'forecastEPS4QNext',
							name : 'forecastEPS4QNext',
							width : 400,
							readOnly : true
						}, {
							xtype : 'displayfield',
							fieldLabel : 'forecastEPScons',
							name : 'forecastEPScons',
							width : 400,
							readOnly : true
						}, {
							xtype : 'displayfield',
							fieldLabel : 'forecastEPScons12M',
							name : 'forecastEPScons12M',
							width : 400,
							readOnly : true
						},

						{
							xtype : 'displayfield',
							fieldLabel : 'EPSttm',
							name : 'EPSttm',
							width : 400,
							readOnly : true
						},

						{
							xtype : 'displayfield',
							fieldLabel : 'g5',
							name : 'g5',
							width : 400,
							readOnly : true
						}, {
							xtype : 'displayfield',
							fieldLabel : 'g10',
							name : 'g10',
							width : 400,
							readOnly : true
						},

						{
							xtype : 'displayfield',
							fieldLabel : 'gk',
							name : 'gk',
							width : 400,
							readOnly : true
						}, {
							xtype : 'displayfield',
							fieldLabel : 'PE_5',
							name : 'PE_5',
							width : 400,
							readOnly : true
						}, {
							xtype : 'displayfield',
							fieldLabel : 'PE_10',
							name : 'PE_10',
							width : 400,
							readOnly : true
						}, {
							xtype : 'displayfield',
							fieldLabel : 'PE_current',
							name : 'PE_current',
							width : 400,
							readOnly : true
						},

						{
							xtype : 'displayfield',
							fieldLabel : 'PE_ttm',
							name : 'PE_ttm',
							width : 400,
							readOnly : true
						}, {
							xtype : 'displayfield',
							fieldLabel : 'PE_cons',
							name : 'PE_cons',
							width : 400,
							readOnly : true
						}

						]
					} ]
				} ],

				loadData : function(data) {
					this.setTitle("Детальная информация");

					this.data = data;
					var inf = {};
					// Ext.apply(inf, data['info']);
					// inf['TargetPriceCons12M'] =
					// data['info'][0]['TargetPriceCons12M'];

					inf['TargetPriceCons12M'] = '<b><a onclick=\"menu.submitDataRequest(menu, \'model/ViewModelPrice\', \'portfolio/info-target-cons12m.html\', { id : '
							+ data['info'][0]['id_sec']
							+ '});\" href="#">'
							+ data['info'][0]['TargetPriceCons12M']
							+ '</a></b>';

					inf['TargetPriceDecCons'] = data['info'][0]['TargetPriceDecCons'];
					inf['TargetPrice'] = data['info'][0]['TargetPrice'];
					inf['BestPrice'] = data['info'][0]['BestPrice'];
					inf['r'] = data['info'][0]['r'];
					inf['teta'] = data['info'][0]['teta'];
					inf['PriceMedian'] = data['info'][0]['PriceMedian'];
					inf['LastYearAvgWhtPrice'] = data['info'][0]['LastYearAvgWhtPrice'];
					inf['M1Q'] = data['info'][0]['M1Q'];
					inf['M2Q'] = data['info'][0]['M2Q'];
					inf['M3Q'] = data['info'][0]['M3Q'];
					inf['M4Q'] = data['info'][0]['M4Q'];

					inf['factEPS1Q'] = data['info'][0]['factEPS1Q'];
					inf['factEPS2Q'] = data['info'][0]['factEPS2Q'];

					if (data['info'][0]['factEPS3Q'] == null) {
						inf['factEPS3Q'] = "0.00";
					} else
						inf['factEPS3Q'] = data['info'][0]['factEPS3Q'];

					if (data['info'][0]['factEPS4Q'] == null) {
						inf['factEPS4Q'] = "0.00";
					} else
						inf['factEPS4Q'] = data['info'][0]['factEPS4Q'];

					if (data['info'][0]['forecastEPS2Q'] == null) {
						inf['forecastEPS2Q'] = "0.00";
					} else
						inf['forecastEPS2Q'] = data['info'][0]['forecastEPS2Q'];

					// inf['forecastEPS2Q'] = data['info'][0]['forecastEPS2Q'];
					inf['forecastEPS3Q'] = data['info'][0]['forecastEPS3Q'];
					inf['forecastEPS4Q'] = data['info'][0]['forecastEPS4Q'];
					inf['forecastEPS'] = data['info'][0]['forecastEPS'];
					inf['forecastEPS12M'] = data['info'][0]['forecastEPS12M'];
					inf['forecastEPS_NextYear'] = data['info'][0]['forecastEPS_NextYear'];
					inf['forecastEPS1QNext'] = data['info'][0]['forecastEPS1QNext'];
					inf['forecastEPS2QNext'] = data['info'][0]['forecastEPS2QNext'];
					inf['forecastEPS3QNext'] = data['info'][0]['forecastEPS3QNext'];
					inf['forecastEPS4QNext'] = data['info'][0]['forecastEPS4QNext'];
					inf['forecastEPScons'] = data['info'][0]['forecastEPScons'];
					inf['forecastEPScons12M'] = data['info'][0]['forecastEPScons12M'];
					inf['EPSttm'] = data['info'][0]['EPSttm'];
					inf['g5'] = data['info'][0]['g5'];
					inf['g10'] = data['info'][0]['g10'];
					inf['gk'] = data['info'][0]['gk'];
					inf['PE_5'] = data['info'][0]['PE_5'];
					inf['PE_10'] = data['info'][0]['PE_10'];
					inf['PE_current'] = data['info'][0]['PE_current'];
					inf['PE_ttm'] = data['info'][0]['PE_ttm'];
					inf['PE_cons'] = data['info'][0]['PE_cons'];

					// Set values for the form
					this.getForm().setValues(inf);
				}
			});

	return container;

})();
