<%@ page language="java"%>
<%@ page pageEncoding="utf-8"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>АРМ Аналитика</title>

<meta http-equiv="X-UA-Compatible" content="IE=8" />
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<link rel="icon" type="image/png" href="images/favicon.png" />
<link rel="shortcut icon" type="image/vnd.microsoft.icon"
	href="images/favicon.ico" />

<link rel="stylesheet" type="text/css"
	href="/ExtJS/resources/css/ext-all.css">
<link rel="stylesheet" type="text/css"
	href="/ExtJS/resources/css/xtheme-gray.css">
<link rel="stylesheet" type="text/css"
	href="/ExtJS/ux/statusbar/css/statusbar.css">
<link rel="stylesheet" type="text/css"
	href="/ExtJS/ux/css/fileuploadfield.css">

<link rel="stylesheet" type="text/css" href="css/overrides.css">
<link rel="stylesheet" type="text/css" href="css/app-theme.css">
</head>
<body>
	<div id="loading-mask"></div>

	<div id="intro-panel">
		<h1>Система автоматизированной подачи финансовой информации</h1>
		<h1>АНАЛИТИКА</h1>
	</div>

	<script type="text/javascript" src="/ExtJS/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="/ExtJS/ext-all.js"></script>
	<script type="text/javascript" src="/ExtJS/ux-all.js"></script>
	<script type="text/javascript" src="/ExtJS/ext-lang-ru.js"></script>
	<script type="text/javascript">
		Ext.BLANK_IMAGE_URL = "/ExtJS/resources/images/default/s.gif";
	</script>

	<script type="text/javascript"><%@ include file="js/application.js" %></script>
	<!-- Header -->
	<script type="text/javascript"><%@ include file="js/header.js" %></script>
	<!-- Menu -->
	<script type="text/javascript"><%@ include file="js/menu.js" %></script>
	<!-- View -->
	<script type="text/javascript"><%@ include file="js/view.js" %></script>

	<script type="text/javascript">
		Ext.QuickTips.init();

		new Ext.Viewport({
			layout : "border",
			forceLayout : true,
			items : [ panelHeader, panelMenu, panelView, {
				region : 'south',
				xtype : 'container',
				height : 25
			} ],
			listeners : {
				afterrender : function() {
					Ext.get("loading-mask").fadeOut({
						//useDisplay : true,
						remove : true
					});
				}
			}
		});
	</script>
</body>
</html>
