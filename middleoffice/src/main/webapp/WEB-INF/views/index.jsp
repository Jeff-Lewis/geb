<%@ page language="java" pageEncoding="utf-8"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>АРМ Middle-Office</title>
<link rel="icon" type="image/png" href="images/favicon.png" />
<link rel="shortcut icon" type="image/vnd.microsoft.icon"
	href="images/favicon.ico" />

<link rel="stylesheet" type="text/css"
	href="ext/resources/css/ext-all.css">
<link rel="stylesheet" type="text/css"
	href="ext/resources/css/xtheme-gray.css">
<link rel="stylesheet" type="text/css"
	href="ext/ux/statusbar/css/statusbar.css">
<link rel="stylesheet" type="text/css"
	href="ext/ux/css/fileuploadfield.css">

<link rel="stylesheet" type="text/css" href="css/overrides.css">
<link rel="stylesheet" type="text/css" href="css/app-theme.css">

<script type="text/javascript" src="ext/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="ext/ext-all.js"></script>
<script type="text/javascript" src="ext/ux-all.js"></script>
<script type="text/javascript" src="ext/ext-lang-ru.js"></script>
<script type="text/javascript">
	Ext.BLANK_IMAGE_URL = 'ext/resources/images/default/s.gif';
</script>

<script type="text/javascript" src="js/application.js"></script>
<script type="text/javascript" src="js/main-page.js"></script>
</head>
<body>
	<div id="loading-mask" style=""></div>

	<div id="header-table" class="x-hidden">
		<table style="width: 100%; padding: 0; border-spacing: 0">
			<tr>
				<td align="center" width="200px"><img
					src="images/bank_v_life.png" /></td>
				<td align="center">
					<div style="margin-top: 6px;">
						<h2>Система автоматизированной подачи финансовой информации</h2>
						<h2>Middle-Office</h2>
					</div>
					<div style="margin-top: 6px;">
						<h1>Текущий пользователь: ${username}</h1>
						[ <a href="logout">Выйти</a> ]
					</div>
				</td>
				<td align="right" width="500px"><img src="images/summer_bg.png" /></td>
			</tr>
		</table>
	</div>

	<div id="intro-panel" class="x-hidden">
		<h5>Здесь будет справка о программе...</h5>
	</div>

	<div>
		<ul id="task-dictionary" class="x-hidden" title="Справочники">
			<li><img class="icon-show-all" src="images/excel.png"><a
				href="#" onclick="menu.showPane(menu, 'dictionary/Brokers');">
					Брокеры </a></li>

			<li><img class="icon-show-all" src="images/excel.png"><a
				href="#" onclick="menu.showPane(menu, 'dictionary/Clients');">
					Клиенты </a></li>

			<li><img class="icon-show-all" src="images/excel.png"><a
				href="#" onclick="menu.showPane(menu, 'dictionary/Funds');">
					Фонды </a></li>

			<li><img class="icon-show-all" src="images/excel.png"><a
				href="#" onclick="menu.showPane(menu, 'dictionary/BrokerAccounts');">
					Брокерские счета </a></li>

			<li><img class="icon-show-all" src="images/excel.png"><a
				href="#" onclick="menu.showPane(menu, 'dictionary/Tradesystems');">
					Торговые системы </a></li>

			<li><img class="icon-show-all" src="images/excel.png"><a
				href="#" onclick="menu.showPane(menu, 'dictionary/Futures');">
					Фьючерсы </a></li>

			<li><img class="icon-show-all" src="images/user-plus.png">
				<a href="#"
				onclick="menu.showPane(menu, 'dictionary/Holidays');">
					Праздники </a></li>

			<li><img class="icon-show-all" src="images/excel.png"><a
				href="#" onclick="menu.showPane(menu, 'dictionary/Countries');">
					Страны </a></li>

			<li><img class="icon-show-all" src="images/excel.png"><a
				href="#" onclick="menu.showPane(menu, 'dictionary/CountryTaxes');">
					Налоги по странам </a></li>

			<li><img class="icon-show-all" src="images/excel.png"><a
				href="#"
				onclick="menu.showPane(menu, 'dictionary/SecurityIncorporations');">
					Регистрация инструментов </a></li>

			<li><img class="icon-show-all" src="images/excel.png"><a
				href="#" onclick="menu.showPane(menu, 'dictionary/Dividends');">
					Дивиденды </a></li>

			<li><img class="icon-show-all" src="images/excel.png"><a
				href="#" onclick="menu.showPane(menu, 'dictionary/Coupons');">
					Купоны (погашение) </a></li>

			<li><img class="icon-show-all" src="images/excel.png"><a
				href="#" onclick="menu.showPane(menu, 'dictionary/Swaps');">
					Свопы </a></li>

			<li><img class="icon-show-all" src="images/excel.png"><a
				href="#" onclick="menu.showPane(menu, 'dictionary/Currencies');">
					Валюты </a></li>

			<li><img class="icon-show-all" src="images/excel.png"><a
				href="#" onclick="menu.showPane(menu, 'dictionary/CurrencyRate');">
					Курсы валют </a></li>

		</ul>

		<ul id="task-services" class="x-hidden" title="Сервис">
			<li><img class="icon-show-all" src="images/excel.png"><a
				href="#" onclick="menu.showPane(menu, 'services/NoConformity');">
					Нет соответствия </a></li>

			<li><img class="icon-show-all" src="images/excel.png"><a
				href="#"
				onclick="menu.showPane(menu, 'services/NotEnoughQuotations');">
					Не хватает котировок</a></li>

			<li><img class="icon-show-all" src="images/30.png"><a
				href="#" onclick="menu.showPane(menu, 'services/ViewShare');">
					Редактирование акций </a></li>

			<li><img class="icon-show-all" src="images/30.png"><a
				href="#" onclick="menu.showPane(menu, 'services/ViewSwaps');">
					Редактирование свопов </a></li>

			<li><img class="icon-show-all" src="images/30.png"><a
				href="#" onclick="menu.showPane(menu, 'services/ViewBonds');">
					Редактирование облигаций </a></li>

			<li><img class="icon-show-all" src="images/30.png"><a
				href="#" onclick="menu.showPane(menu, 'services/ViewFutures');">
					Редактирование фьючерсов </a></li>

			<li><img class="icon-show-all" src="images/30.png"><a
				href="#" onclick="menu.showPane(menu, 'services/NewInstrument');">
					Ввод нового инструмента </a></li>

			<li><img class="icon-show-all" src="images/excel.png"><a
				href="#" onclick="menu.showPane(menu, 'services/DealsPattern');">
					Сохраненные шаблоны </a></li>

			<li><img class="icon-show-all" src="images/30.png"><a
				href="#" onclick="menu.showPane(menu, 'services/LoadQuotes');">
					Загрузка котировок </a></li>

			<li><img class="icon-show-all" src="images/30.png"><a
				href="#" onclick="menu.showPane(menu, 'services/LoadBondYeild');">
					Загрузка доходности облигаций </a></li>

			<li><img class="icon-show-all" src="images/30.png"><a
				href="#" onclick="menu.showPane(menu, 'services/LoadValues');">
					Загрузка номинала </a></li>

			<li><img class="icon-show-all" src="images/30.png"><a
				href="#" onclick="menu.showPane(menu, 'services/LoadRateCoupon');">
					Загрузка ставки по купонам </a></li>

			<li><img class="icon-show-all" src="images/30.png"><a
				href="#" onclick="menu.showPane(menu, 'services/LoadCashFlow');">
					Загрузка дат погашений </a></li>

			<li><img class="icon-show-all" src="images/30.png"><a
				href="#" onclick="menu.showPane(menu, 'services/LoadATR');">
					Загрузка ATR </a></li>

			<li><img class="icon-show-all" src="images/30.png"><a
				href="#" ext:qtitle="Цена1 для risk-reward"
				ext:qtip="Средневзвешенная цена без учета переброски между ПРББ и WW"
				onclick="menu.showPane(menu, 'services/RiskRewardPrice1');">
					Цена1 для RR </a></li>

			<li><img class="icon-show-all" src="images/30.png"><a
				href="#"
				onclick="menu.showPane(menu, 'services/RiskRewardSetupParams');">
					Задание параметров отчета RR </a></li>

			<li><img class="icon-show-all" src="images/30.png"><a
				href="#"
				onclick="menu.showPane(menu, 'services/SecuritiesRests');">
					Верификация остатков </a></li>

			<li><img class="icon-show-all" src="images/users.png"><a
				href="#" onclick="menu.showPane(menu, 'services/Sending');">
					Рассылка E-mail и SMS </a></li>

			<li><img class="icon-show-all" src="images/user-plus.png"><a
				href="#" onclick="menu.showPane(menu, 'services/Contacts');">
					Справочник контактов </a></li>
		</ul>

		<ul id="operations-cb" class="x-hidden" title="Операции с ЦБ">
			<li><img class="icon-show-all" src="images/30.png"><a
				href="#" onclick="menu.showModal(menu, 'operations/DealsOneNew');">
					Загрузка единичной сделки </a></li>

			<li><img class="icon-show-all" src="images/30.png"><a
				href="#" onclick="menu.showPane(menu, 'operations/DealsLoading');">
					Загрузка сделок </a></li>

			<li><img class="icon-show-all" src="images/30.png"><a
				href="#" onclick="menu.showPane(menu, 'operations/DealsTransfer');">
					Перекидка ЦБ между фондами </a></li>

			<li><img class="icon-show-all" src="images/30.png"><a
				href="#"
				onclick="menu.showPane(menu, 'operations/SetSecurityRiscs');">
					Задать параметры риска </a></li>

			<li><img class="icon-show-all" src="images/30.png"><a
				href="#"
				onclick="menu.showPane(menu, 'operations/DividendsLoading');">
					Загрузка дивидендов из файла </a></li>

			<li><img class="icon-show-all" src="images/30.png"><a
				href="#"
				onclick="menu.showPane(menu, 'operations/CouponsLoading');">
					Загрузка погашения купонов из файла </a></li>

			<li><img class="icon-show-all" src="images/30.png"><a
				href="#"
				onclick="menu.showPane(menu, 'operations/DealsLoadingREPO');">
					Загрузка сделок РЕПО из файла </a></li>

		</ul>

		<ul id="task-portfolio" class="x-hidden" title="Портфель">
			<li><img class="icon-show-all" src="images/grid.png"><a
				href="#"
				onclick="menu.showPane(menu, 'portfolio/ViewPortfolio');">
					Текущий портфель </a></li>

			<li><img class="icon-show-all" src="images/grid.png"><a
				href="#"
				onclick="menu.showPane(menu, 'portfolio/ViewDetailedFinrez');">
					Текущий финрез </a></li>

			<li><img class="icon-show-all" src="images/grid.png"><a
				href="#" onclick="menu.showPane(menu, 'portfolio/ViewDeals');">
					Список сделок </a></li>

			<li><img class="icon-show-all" src="images/grid.png"><a
				href="#"
				onclick="menu.showPane(menu, 'portfolio/TransferOperations');">
					Список перекидок </a></li>

			<li><img class="icon-show-all" src="images/grid.png"><a
				href="#" onclick="menu.showPane(menu, 'portfolio/ViewDealsREPO');">
					Сделки РЕПО </a></li>

			<li><img class="icon-show-all" src="images/grid.png"><a
				href="#" onclick="menu.showPane(menu, 'portfolio/ViewQuotes');">
					Котировки </a></li>

			<li><img class="icon-show-all" src="images/grid.png"><a
				href="#" onclick="menu.showPane(menu, 'portfolio/ViewAtr');">
					Отображение ATR </a></li>

			<li><img class="icon-show-all" src="images/grid.png"><a
				href="#" onclick="menu.showPane(menu, 'portfolio/SecurityRiscs');">
					Заданные параметры риска </a></li>

		</ul>

		<ul id="task-logs" class="x-hidden" title="Журнализация">
			<li><img class="icon-show-all" src="images/user-plus.png">
				<a href="#" onclick="menu.showPane(menu, 'logs/LogMessages');">
					Журнал отправки сообщений </a></li>

			<li><img class="icon-show-all" src="images/user-plus.png">
				<a href="#" onclick="menu.showPane(menu, 'logs/LogContacts');">
					Журнал изменений справочника контактов </a></li>

			<li><img class="icon-show-all" src="images/user-plus.png">
				<a href="#" onclick="menu.showPane(menu, 'logs/LogSubscription');">
					Журнал подписки </a></li>

		</ul>

	</div>
</body>
</html>