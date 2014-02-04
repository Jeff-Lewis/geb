<%@ page language="java" pageEncoding="utf-8"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>АРМ Аналитика</title>
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
						<h2>АНАЛИТИКА</h2>
					</div>
					<div style="margin-top: 6px;">
						<h1>Текущий пользователь: ${username}</h1>
						[ <a href="signout.html">Выйти</a> ]
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
		<ul id="current-model" class="x-hidden">
			<li><img class="icon-show-all" src="images/bogus.png"> <a
				href="#" onclick="menu.showPane(menu, 'model/BuildModel');">
					Расчёт модели по компании </a></li>

			<li><img class="icon-show-all" src="images/bogus.png"> <a
				href="#" onclick="menu.showPane(menu, 'model/BuildEPS');">
					Расчёт EPS по компании </a></li>

			<li><img class="icon-show-all" src="images/bogus.png"> <a
				href="#" onclick="menu.showPane(menu, 'model/ViewModel');">
					Просмотр текущей модели </a></li>

			<li><img class="icon-show-all" src="images/bogus.png"> <a
				href="#" onclick="menu.showPane(menu, 'model/CompanyGroup');">
					Компании и группы </a></li>

			<li><img class="icon-show-all" src="images/bogus.png"> <a
				href="#" onclick="menu.showPane(menu, 'model/CompanyReports');">
					Компании и отчёты </a></li>

		</ul>

		<ul id="task-portfolio" class="x-hidden">
			<li><img class="icon-show-all" src="images/bogus.png"> <a
				href="#" onclick="menu.showPane(menu, 'portfolio/ViewPortfolio');">
					Добавление организаций в Portfolio </a></li>
		</ul>

		<ul id="task-sprav-org" class="x-hidden">
			<li><img class="icon-show-all" src="images/bogus.png"> <a
				href="#" onclick="menu.showPane(menu, 'company/Companies');">
					Список компаний </a></li>

			<li><img class="icon-show-all" src="images/bogus.png"> <a
				href="#" onclick="menu.showPane(menu, 'company/CompanyAdd');">
					Добавление компаний </a></li>

		</ul>

		<ul id="task-sprav-params" class="x-hidden">
			<li><img class="icon-show-all" src="images/bogus.png"> <a
				href="#" onclick="menu.showPane(menu, 'params/ViewParams');">
					Справочник параметров </a></li>

			<li><img class="icon-show-all" src="images/bogus.png"> <a
				href="#" onclick="menu.showModal(menu, 'params/NewParam');">
					Ввод нового параметра </a></li>

			<li><img class="icon-show-all" src="images/bogus.png"> <a
				href="#" onclick="menu.showModal(menu, 'params/NewParamOver');">
					Ввод нового параметра blm_datasource_ovr </a></li>

		</ul>

		<ul id="task-reports" class="x-hidden">
			<li><img class="icon-show-all" src="images/excel.png"> <a
				href="#" onclick="menu.showPane(menu, 'reports/ViewExceptions');">
					Отчёт по исключениям </a></li>

			<li><img class="icon-show-all" src="images/excel.png"> <a
				href="#" onclick="menu.showPane(menu, 'reports/BrokersForecast');">
					Прогнозы по брокерам </a></li>

			<li><img class="icon-show-all" src="images/excel.png"> <a
				href="#" onclick="menu.showPane(menu, 'reports/BrokersCoverage');">
					Покрытие брокеров </a></li>

			<li><img class="icon-show-all" src="images/excel.png"> <a
				href="#"
				onclick="menu.showPane(menu, 'reports/BrokersEstimateChange');">
					Изменение оценок брокеров </a></li>

			<li><img class="icon-show-all" src="images/excel.png"> <a
				href="#" onclick="menu.showPane(menu, 'reports/ViewCompaniesEps');">
					EPS по компаниям </a></li>

		</ul>

		<ul id="task-grid" class="x-hidden">

			<li><img class="icon-show-all" src="images/bogus.png"> <a
				href="#" onclick="menu.showPane(menu, 'bloomberg/RequestBDHeps');">
					BDH запрос с EPS </a></li>

			<li><img class="icon-show-all" src="images/bogus.png"> <a
				href="#" onclick="menu.showPane(menu, 'bloomberg/RequestBDH');"
				ext:qtitle="Multi Request History Params Data"
				ext:qtip="Запрос с выбором нескольких организаций и параметров">
					BDH запрос </a></li>

			<li><img class="icon-show-all" src="images/bogus.png"> <a
				href="#" onclick="menu.showPane(menu, 'bloomberg/RequestBDP');"
				ext:qtitle="Запрос текущих параметров для рассчёта Target Price"
				ext:qtip="Запрос текущих параметров"> BDP запрос </a></li>

			<li><img class="icon-show-all" src="images/bogus.png"> <a
				href="#"
				onclick="menu.showPane(menu, 'bloomberg/RequestBDPovr');"
				ext:qtitle="Форма параметров для запроса импорта параметров"
				ext:qtip="Форма параметров для запроса импорта параметров"> BDP
					с override </a></li>

			<li><img class="icon-show-all" src="images/bogus.png"> <a
				href="#" onclick="menu.showPane(menu, 'bloomberg/RequestBDS');">
					BDS запрос </a></li>

			<li><img class="icon-show-all" src="images/bogus.png"> <a
				href="#"
				onclick="menu.showPane(menu, 'subscription/ViewSubscription');"
				ext:qtitle="Форма параметров для запроса Subscription"
				ext:qtip="Форма параметров для запроса Subscription">
					Subscription </a></li>

		</ul>

		<ul id="task-utils" class="x-hidden">

			<li><img class="icon-show-all" src="images/users.png"> <a
				href="#" onclick="menu.showPane(menu, 'utils/Sending');">
					Рассылка E-mail и SMS </a></li>

			<li><img class="icon-show-all" src="images/user-plus.png">
				<a href="#" onclick="menu.showPane(menu, 'utils/Contacts');">
					Справочник контактов </a></li>

			<li><img class="icon-show-all" src="images/user-plus.png">
				<a href="#" onclick="menu.showPane(menu, 'utils/Brokers');">
					Справочник брокеров </a></li>

		</ul>

		<ul id="task-logs" class="x-hidden">
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