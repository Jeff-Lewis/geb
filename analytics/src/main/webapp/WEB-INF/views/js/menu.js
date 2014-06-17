/*<%@ page pageEncoding="utf-8" %>
 * Главное меню
 */

/**
 * Текущая модель
 */
var currentModel = {
		title : 'Текущая модель',
		icon : 'images/bogus.png',
		items : [ {
			text : 'Расчёт модели по компании',
			handler : function() {
				showPanel('model/BuildModel');
			}
		}, {
			text : 'Расчёт EPS по компании',
			handler : function() {
				showPanel('model/BuildEPS');
			}
		}, {
			text : 'Просмотр текущей модели',
			handler : function() {
				showPanel('model/ViewModel');
			}
		}, {
			text : 'Компании и группы',
			handler : function() {
				showPanel('model/CompanyGroup');
			}
		}, {
			text : 'Компании и отчёты',
			handler : function() {
				showPanel('model/CompanyReports');
			}
		} ]
};

/**
 * Portfolio
 */
var portfolio = {
		title : 'Portfolio',
		icon : 'images/bogus.png',
		items : [ {
			text : 'Добавление организаций в Portfolio',
			handler : function() {
				showPanel('portfolio/ViewPortfolio');
			}
		} ]
};

/**
 * Компании
 */
var companies = {
		title : 'Компании',
		icon : 'images/bogus.png',
		items : [ {
			text : 'Список компаний',
			handler : function() {
				showPanel('company/Companies');
			}
		}, {
			text : 'Добавление компаний',
			handler : function() {
				showPanel('company/CompanyAdd');
			}
		} ]
};

/**
 * Параметры
 */
var params = {
		title : 'Параметры',
		icon : 'images/bogus.png',
		items : [ {
			text : 'Справочник параметров',
			handler : function() {
				showPanel('params/ViewParams');
			}
		}, {
			text : 'Ввод нового параметра',
			handler : function() {
				showModal('params/NewParam');
			}
		}, {
			text : 'Ввод нового параметра blm_datasource_ovr',
			handler : function() {
				showModal('params/NewParamOver');
			}
		} ]
};

/**
 * Отчётные формы
 */
var reports = {
		title : 'Отчётные формы',
		icon : 'images/excel.png',
		items : [ {
			text : 'Отчёт по исключениям',
			handler : function() {
				showPanel('reports/ViewExceptions');
			}
		}, {
			text : 'Прогнозы по брокерам',
			handler : function() {
				showPanel('reports/BrokersForecast');
			}
		}, {
			text : 'Покрытие брокеров',
			handler : function() {
				showPanel('reports/BrokersCoverage');
			}
		}, {
			text : 'Изменение оценок брокеров',
			handler : function() {
				showPanel('reports/BrokersEstimateChange');
			}
		}, {
			text : 'EPS по компаниям',
			handler : function() {
				showPanel('reports/ViewCompaniesEps');
			}
		} ]
};

/**
 * Запросы к терминалу Bloomberg
 */
var bloomberg = {
		title : 'Запросы к терминалу Bloomberg',
		icon : 'images/bogus.png',
		items : [ {
			text : 'BDH запрос с EPS',
			handler : function() {
				showPanel('bloomberg/RequestBDHeps');
			}
		}, {
			text : 'BDH запрос',
			handler : function() {
				showPanel('bloomberg/RequestBDH');
			}
		}, {
			text : 'BDP запрос',
			handler : function() {
				showPanel('bloomberg/RequestBDP');
			}
		}, {
			text : 'BDP с override',
			handler : function() {
				showPanel('bloomberg/RequestBDPovr');
			}
		}, {
			text : 'BDS запрос',
			handler : function() {
				showPanel('bloomberg/RequestBDS');
			}
		}, {
			text : 'Subscription',
			handler : function() {
				showPanel('subscription/ViewSubscription');
			}
		} ]
};

/**
 * Утилиты
 */
var utils = {
		title : 'Утилиты',
		icon : 'images/user-plus.png',
		items : [ {
			text : 'Рассылка E-mail и SMS',
			icon : 'images/users.png',
			handler : function() {
				showPanel('utils/Sending');
			}
		}, {
			text : 'Справочник контактов',
			handler : function() {
				showPanel('utils/Contacts');
			}
		}, {
			text : 'Справочник брокеров',
			handler : function() {
				showPanel('utils/Brokers');
			}
		}, {
			text : 'Справочник пользователей',
			handler : function() {
				showPanel('utils/Users');
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

	return [ _createMenuGroup(currentModel), _createMenuGroup(portfolio),
			_createMenuGroup(companies), _createMenuGroup(params),
			_createMenuGroup(reports), _createMenuGroup(bloomberg),
			_createMenuGroup(utils), _createMenuGroup(logs) ];
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
