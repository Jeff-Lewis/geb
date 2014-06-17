/*<%@ page pageEncoding="utf-8" %>
 * 
 */

/**
 * Header panel
 */
var panelHeader = {
	region : 'north',
	xtype : 'container',
	height : 84,
	layout : 'hbox',
	layoutConfig : {
		align : 'stretch'
	},
	items : [ {
		xtype : 'container',
		width : 300,
		style : {
			background : 'url(images/bank_v_life.png) center no-repeat'
		}
	}, {
		width : 521,
		border : false,
		unstyled : true,
		contentEl : 'intro-panel',
		buttonAlign : 'center',
		buttons : [ {
			width : 100,
			text : 'Регистрация',
			handler : function() {
				login();
			}
		}, {
			width : 100,
			text : 'Выход',
			handler : function() {
				logout();
			}
		} ]
	}, {
		xtype : 'container',
		width : '100%',
		style : {
			background : 'url(images/summer_bg.png) right repeat-x'
		}
	} ]
};
