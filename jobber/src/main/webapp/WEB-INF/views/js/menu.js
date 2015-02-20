/*<%@ page pageEncoding="utf-8" %>
 * Главное меню
 */

/**
 * Ночные задачи
 */
var jobberTasks = {
	title : 'Ночные задачи',
	icon : 'images/grid.png',
	items : [ {
		text : '03:00 BdsLoad',
		handler : function() {
			window.location = 'JobberTasks/LoadBds';
        }
	}, {
		text : '04:15 FuturesLoad',
		handler : function() {
			window.location = 'JobberTasks/LoadFutures';
        }
	}, {
		text : '05:00 QuotesLoad',
		handler : function() {
			window.location = 'JobberTasks/LoadQuotes';
        }
	}, {
		text : '05:10 AtrLoad',
		handler : function() {
			window.location = 'JobberTasks/LoadAtr';
        }
	}, {
		text : '06:00 BdpOverrideLoad',
		handler : function() {
			window.location = 'JobberTasks/LoadBdpOverride';
        }
	}, {
		text : '07:00 HistDataLoad',
		handler : function() {
			window.location = 'JobberTasks/LoadHistData';
        }
	}, {
		text : '08:00 CurrenciesDataLoad',
		handler : function() {
			window.location = 'JobberTasks/LoadCurrenciesData';
        }
	}, {
		text : '11-19 BondsLoad',
		handler : function() {
			window.location = 'JobberTasks/LoadBonds';
        }
	} ]
};

/**
 * 
 */
var messageTasks = {
		title : 'Отправка сообщений',
		icon : 'images/grid.png',
		items : [ {
			text : '0 */15 8-23 * * MON-FRI<br>Проверка работы real-time обновлений',
			handler : function() {
				window.location = 'JobberTasks/MsgSubscription';
	        }
		}, {
			text : '0 0 10 * * MON-FRI<br>Проверка результатов ночных загрузок',
			handler : function() {
				window.location = 'JobberTasks/MsgJobbers';
	        }
		}, {
			text : '0 0,30 0,10-23 * * MON-FRI<br>Рассылка котировок',
			handler : function() {
				window.location = 'JobberTasks/MsgQuotes';
	        }
		}, {
			text : '0 0 12-19 * * MON-FRI<br>Рассылка СМС с котировками бондов',
			handler : function() {
				window.location = 'JobberTasks/MsgBonds';
	        }
		}, {
			text : '0 0,30 * * * ?<br>Рассылка E-mail с котировками по России',
			handler : function() {
				window.location = 'JobberTasks/MsgQuotesRus';
	        }
		}, {
			text : '0 30 12 * * TUE-SAT<br>Рассылка ссылок на Fullermoney Audio',
			handler : function() {
				window.location = 'JobberTasks/MsgFullermoneyAudio';
	        }
		}, {
			text : '0 50 18 * * ?<br>Рассылка СМС с котировками по США',
			handler : function() {
				window.location = 'JobberTasks/MsgQuotesUsa';
	        }
		}, {
			text : '0 0 20 * * MON-FRI<br>Рассылка СМС со ссылкой на ежедневный выпуск сводной',
			handler : function() {
				window.location = 'JobberTasks/MsgAnalytics';
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

	return [ _createMenuGroup(jobberTasks), _createMenuGroup(messageTasks), _createMenuGroup(services), _createMenuGroup(logs) ];
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
