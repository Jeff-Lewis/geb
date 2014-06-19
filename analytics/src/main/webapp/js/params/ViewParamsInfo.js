/**
 * Справочник параметров - info
 */
(function() {

	return {
		xtype : 'form',
		id : 'ViewParamsInfo-component',
		title : 'Information about field',
		frame : true,
		closable : true,
		layout : 'border',

		items : [ {
			region : 'north',
			height : 70,
			padding : 10,
			frame : false,
			border : false,
			layout : 'form',
			labelWidth : 80,
			defaults : {
				labelStyle : 'color:#3764A0;',
				xtype : 'displayfield',
				cls : 'z-title',
				width : 200
			},

			items : [ {
				fieldLabel : 'FIELD_ID',
				name : 'field_id'
			}, {
				fieldLabel : 'FIELD',
				name : 'field_mnemonic'
			} ]
		}, {
			region : 'center',
			title : 'MAIN INFORMATION',
			frame : true,
			//collapsible : true,
			//collapsed : false,
			autoScroll : true,
			padding : 10,
			layout : 'form',
			labelWidth : 250,
			defaults : {
				xtype : 'displayfield',
				width : 500
			},

			items : [ {
				fieldLabel : 'DESCRIPTION',
				name : 'description'
			}, {
				fieldLabel : 'DATA_LICENCE_CATEGORY',
				name : 'data_license_category'
			}, {
				fieldLabel : 'CATEGORY',
				name : 'category'
			}, {
				fieldLabel : 'DEFINITION',
				name : 'definition'
			}, {
				fieldLabel : 'COMDTY',
				name : 'comdty'
			}, {
				fieldLabel : 'EQUITY',
				name : 'equity'
			}, {
				fieldLabel : 'MUNI',
				name : 'muni'
			}, {
				fieldLabel : 'PDF',
				name : 'pdf'
			}, {
				fieldLabel : 'M_MKT',
				name : 'm-mkt'
			}, {
				fieldLabel : 'GOVT',
				name : 'govt'
			}, {
				fieldLabel : 'CORP',
				name : 'corp'
			}, {
				fieldLabel : 'INDX',
				name : 'indx'
			}, {
				fieldLabel : 'CURNCY',
				name : 'curncy'
			}, {
				fieldLabel : 'MTGE',
				name : 'mtge'
			}, {
				fieldLabel : 'STANDART_WIDTH',
				name : 'standard_width'
			}, {
				fieldLabel : 'STANDART_DECIMAL_PLACES',
				name : 'standard_decimal_places'
			}, {
				fieldLabel : 'FIELD_TYPE',
				name : 'field_type'
			}, {
				fieldLabel : 'BACK_OFFICE',
				name : 'back_office'
			}, {
				fieldLabel : 'EXTENDED_BACK_OFFICE',
				name : 'extended_back_office'
			}, {
				fieldLabel : 'PRODUCTION_DATE',
				name : 'production_date'
			}, {
				fieldLabel : 'CURRENT_MAXIMUM_WIDTH',
				name : 'current_maximum_width'
			}, {
				fieldLabel : 'BVAL',
				name : 'bval'
			}, {
				fieldLabel : 'BVAL_BOLCKED',
				name : 'bval_blocked'
			}, {
				fieldLabel : 'GETFUNDAMENTALS',
				name : 'getfundamentals'
			}, {
				fieldLabel : 'GETHISTORY',
				name : 'gethistory'
			}, {
				fieldLabel : 'GETCOMPANY',
				name : 'getcompany'
			}, {
				fieldLabel : 'OLD_MNEMONIC',
				name : 'old_mnemonic'
			}, {
				fieldLabel : 'DATA_LICENSE_CATEGORY_2',
				name : 'data_license_category_2'
			}, {
				fieldLabel : 'PSBOOPT',
				name : 'psboopt'
			} ]
		} ],

		loadData : function(data) {
			this.setTitle('Information about field '
					+ data.item['field_mnemonic']);
			this.getForm().setValues(data.item);
		}
	};
})();
