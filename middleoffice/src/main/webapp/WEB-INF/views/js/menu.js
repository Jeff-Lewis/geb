/*<%@ page pageEncoding="utf-8" %>
 * Главное меню
 */

/**
 * Справочники
 */
var dictionary = {
    title : 'Справочники',
    icon : 'images/excel.png',
    items : [ {
        text : 'Брокеры',
        handler : function() {
	        showPanel('dictionary/Brokers');
        }
    }, {
        text : 'Клиенты',
        handler : function() {
	        showPanel('dictionary/Clients');
        }
    }, {
        text : 'Фонды',
        handler : function() {
	        showPanel('dictionary/Funds');
        }
    }, {
        text : 'Брокерские счета',
        handler : function() {
	        showPanel('dictionary/BrokerAccounts');
        }
    }, {
        text : 'Торговые системы',
        handler : function() {
	        showPanel('dictionary/Tradesystems');
        }
    }, {
        text : 'Фьючерсы',
        handler : function() {
	        showPanel('dictionary/Futures');
        }
    }, {
        text : 'Опционы',
        handler : function() {
	        showPanel('dictionary/Options');
        }
    }, {
        text : 'Праздники',
        icon : 'images/user-plus.png',
        handler : function() {
	        showPanel('dictionary/Holidays');
        }
    }, {
        text : 'Страны',
        handler : function() {
	        showPanel('dictionary/Countries');
        }
    }, {
        text : 'Налоги по странам',
        handler : function() {
	        showPanel('dictionary/CountryTaxes');
        }
    }, {
        text : 'Регистрация инструментов',
        handler : function() {
	        showPanel('dictionary/SecurityIncorporations');
        }
    }, {
        text : 'Дивиденды',
        handler : function() {
	        showPanel('dictionary/Dividends');
        }
    }, {
        text : 'Купоны (погашение)',
        handler : function() {
	        showPanel('dictionary/Coupons');
        }
    }, {
        text : 'Свопы',
        handler : function() {
	        showPanel('dictionary/Swaps');
        }
    }, {
        text : 'Валюты',
        handler : function() {
	        showPanel('dictionary/Currencies');
        }
    }, {
        text : 'Курсы валют',
        handler : function() {
	        showPanel('dictionary/CurrencyRate');
        }
    }, {
        text : 'Инициаторы',
        handler : function() {
	        showPanel('dictionary/Initiators');
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
        text : 'Нет соответствия',
        icon : 'images/excel.png',
        handler : function() {
	        showPanel('services/NoConformity');
        }
    }, {
        text : 'Не хватает котировок',
        icon : 'images/excel.png',
        handler : function() {
	        showPanel('services/NotEnoughQuotations');
        }
    }, {
        text : 'Нет настроек для дивидендов',
        icon : 'images/excel.png',
        handler : function() {
	        showPanel('services/NotVisibleDividends');
        }
    }, {
        text : 'Нет настроек для купонов',
        icon : 'images/excel.png',
        handler : function() {
	        showPanel('services/NotVisibleCoupons');
        }
    }, {
        text : 'Не хватает коэффициентов',
        icon : 'images/excel.png',
        handler : function() {
	        showPanel('services/NoCoefficients');
        }
    }, {
        text : 'Цена1 для RR',
        // ext:qtitle="Цена1 для risk-reward"
        // ext:qtip="Средневзвешенная цена без учета переброски между ПРББ и WW"
        handler : function() {
	        showPanel('services/RiskRewardPrice1');
        }
    }, {
        text : 'Задание параметров отчета RR',
        handler : function() {
	        showPanel('services/RiskRewardSetupParams');
        }
    }, {
    	text : 'Сортировка клиентов',
        handler : function() {
	        showPanel('services/ClientSort');
        }
    }, {
        text : 'Верификация остатков',
        handler : function() {
	        showPanel('services/SecuritiesRests');
        }
    }, {
        text : 'Рассылка E-mail и SMS',
        icon : 'images/users.png',
        handler : function() {
	        showPanel('services/Sending');
        }
    }, {
        text : 'Справочник контактов',
        icon : 'images/user-plus.png',
        handler : function() {
	        showPanel('services/Contacts');
        }
    } ]
};

/**
 * Редактирование
 */
var views = {
    title : 'Редактирование',
    icon : 'images/30.png',
    items : [ {
        text : 'Акции - Share',
        handler : function() {
	        showPanel('services/ViewShare');
        }
    }, {
        text : 'Свопы - Swaps',
        handler : function() {
	        showPanel('services/ViewSwaps');
        }
    }, {
        text : 'Облигаций - Bonds',
        handler : function() {
	        showPanel('services/ViewBonds');
        }
    }, {
        text : 'Фьючерсы - Futures',
        handler : function() {
	        showPanel('services/ViewFutures');
        }
    }, {
        text : 'Опционы - Options',
        handler : function() {
	        showPanel('services/ViewOptions');
        }
    }, {
        text : 'Ввод нового инструмента',
        handler : function() {
	        showPanel('services/NewInstrument');
        }
    }, {
        text : 'Сохраненные шаблоны',
        icon : 'images/excel.png',
        handler : function() {
	        showPanel('services/DealsPattern');
        }
    } ]
};

/**
 * Загрузка
 */
var loading = {
    title : 'Загрузка',
    icon : 'images/30.png',
    items : [ {
        text : 'Загрузка котировок',
        handler : function() {
	        showPanel('loading/LoadQuotes');
        }
    }, {
        text : 'Загрузка доходности облигаций',
        handler : function() {
	        showPanel('loading/LoadBondYeild');
        }
    }, {
        text : 'Загрузка номинала',
        handler : function() {
	        showPanel('loading/LoadValues');
        }
    }, {
        text : 'Загрузка ставки по купонам',
        handler : function() {
	        showPanel('loading/LoadRateCoupon');
        }
    }, {
        text : 'Загрузка дат погашений',
        handler : function() {
	        showPanel('loading/LoadCashFlow');
        }
    }, {
        text : 'Загрузка ATR',
        handler : function() {
	        showPanel('loading/LoadATR');
        }
    }, {
        text : 'Загрузка курсов валют',
        handler : function() {
	        showPanel('loading/LoadCurrencies');
        }
    } ]
};

/**
 * Операции с ЦБ
 */
var operationsCB = {
    title : 'Операции с ЦБ',
    icon : 'images/30.png',
    items : [ {
        text : 'Загрузка единичной сделки',
        handler : function() {
	        showModal('operations/DealsOneNew');
        }
    }, {
        text : 'Загрузка сделок',
        handler : function() {
	        showPanel('operations/DealsLoading');
        }
    }, {
        text : 'Перекидка ЦБ между фондами',
        handler : function() {
	        showPanel('operations/DealsTransfer');
        }
    }, {
        text : 'Задать параметры риска',
        handler : function() {
	        showPanel('operations/SetSecurityRiscs');
        }
    }, {
        text : 'Загрузка дивидендов из файла',
        handler : function() {
	        showPanel('operations/DividendsLoading');
        }
    }, {
        text : 'Загрузка погашения купонов из файла',
        handler : function() {
	        showPanel('operations/CouponsLoading');
        }
    }, {
        text : 'Загрузка сделок РЕПО из файла',
        handler : function() {
	        showPanel('operations/DealsLoadingREPO');
        }
    } ]
};

/**
 * Портфель
 */
var portfolio = {
    title : 'Портфель',
    icon : 'images/grid.png',
    items : [ {
        text : 'Текущий портфель',
        handler : function() {
	        showPanel('portfolio/ViewPortfolio');
        }
    }, {
        text : 'Текущий финрез',
        handler : function() {
	        showPanel('portfolio/ViewDetailedFinrez');
        }
    }, {
        text : 'Список сделок',
        handler : function() {
	        showPanel('portfolio/ViewDeals');
        }
    }, {
        text : 'Список перекидок',
        handler : function() {
	        showPanel('portfolio/TransferOperations');
        }
    }, {
        text : 'Сделки РЕПО',
        handler : function() {
	        showPanel('portfolio/ViewDealsREPO');
        }
    }, {
        text : 'Котировки',
        handler : function() {
	        showPanel('portfolio/ViewQuotes');
        }
    }, {
        text : 'Отображение ATR',
        handler : function() {
	        showPanel('portfolio/ViewAtr');
        }
    }, {
        text : 'Заданные параметры риска',
        handler : function() {
	        showPanel('portfolio/SecurityRiscs');
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

	return [ _createMenuGroup(dictionary), _createMenuGroup(views), _createMenuGroup(loading),
	        _createMenuGroup(services), _createMenuGroup(operationsCB), _createMenuGroup(portfolio),
	        _createMenuGroup(logs) ];
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
