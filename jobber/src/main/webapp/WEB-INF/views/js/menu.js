/*<%@ page pageEncoding="utf-8" %>
 * Главное меню
 */

/**
 * Ночные задачи
 */
var jobs = {
	title : 'Ночные задачи',
	icon : 'images/grid.png',
	items : [ {
		text : '03:00 BdsLoad',
		handler : function() {
			window.location = 'rest/BdsLoad';
        }
	}, {
		text : '04:15 FuturesLoad',
		handler : function() {
			window.location = 'rest/FuturesLoad';
        }
	}, {
		text : '05:00 QuotesLoad',
		handler : function() {
			window.location = 'rest/QuotesLoad';
        }
	}, {
		text : '05:10 AtrLoad',
		handler : function() {
			window.location = 'rest/AtrLoad';
        }
	}, {
		text : '06:00 BdpOverrideLoad',
		handler : function() {
			window.location = 'rest/BdpOverrideLoad';
        }
	}, {
		text : '07:00 HistDataLoad',
		handler : function() {
			window.location = 'rest/HistDataLoad';
        }
	}, {
		text : '08:00 CurrenciesDataLoad',
		handler : function() {
			window.location = 'rest/CurrenciesDataLoad';
        }
	}, {
		text : '11-19 BondsLoad',
		handler : function() {
			window.location = 'rest/BondsLoad';
        }
	} ]
};

/**
 * Сервис
 */
var services = {
    title : 'Сервис',
    icon : 'images/30.png',
    items : [ {
        text : 'Агенты БЛУМБЕРГа',
        icon : 'images/users.png',
        handler : function() {
	        showPanel('services/ViewAgents');
        }
    }, {
        text : 'Рассылка E-mail и SMS',
        icon : 'images/users.png',
        handler : function() {
	        showPanel('services/Sending');
        }
    }, {
		text : 'Subscription',
		handler : function() {
			showPanel('services/ViewSubscription');
		}
	}, {
        text : 'Справочник контактов',
        icon : 'images/user-plus.png',
        handler : function() {
	        showPanel('services/Contacts');
        }
    }, {
		text : 'Справочник пользователей',
		handler : function() {
			showPanel('services/Users');
		}
	} ]
};

/**
 * Журнализация
 */
var logs = {
		title : 'Журнализация',
		icon : 'images/user-plus.png',
		items : [ {
			text : 'Журнал отправки сообщений',
			handler : function() {
				showPanel('logs/LogMessages');
			}
		}, {
			text : 'Журнал изменений справочника контактов',
			handler : function() {
				showPanel('logs/LogContacts');
			}
		}, {
			text : 'Журнал подписки',
			handler : function() {
				showPanel('logs/LogSubscription');
			}
		}, {
			text : 'Журнал действий пользователя',
			handler : function() {
				showPanel('logs/LogUserActions');
			}
		} ]
};

function createMenuItems() {
	function _createMenuGroup(menu) {
		return {
		    title : menu.title,
		    collapsible : true,
		    frame : true,
		    autoHeight : true,
		    items : {
		        xtype : 'menu',
		        floating : false,
		        defaults : {
			        icon : menu.icon
		        },
		        items : menu.items
		    }
		};
	}

	return [ _createMenuGroup(jobs), _createMenuGroup(services), _createMenuGroup(logs) ];
}

/**
 * Menu panel
 */
var panelMenu = {
    region : 'west',
    width : 300,
    autoScroll : true,
    baseCls : 'x-plain',
    frame : true,
    // split : true,
    items : createMenuItems()
};
