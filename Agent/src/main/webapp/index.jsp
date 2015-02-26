<!DOCTYPE html>

<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<html>
<head>
<meta charset="utf-8">
<title>Bloomberg Agent</title>
<script src="js/jquery-2.1.3.js"></script>
<script src="js/jquery.cookie.js"></script>
<script type="text/javascript">
	var intervalId;

	$(document).ready(function(doc) {
		$('#start').click(function onStartClick() {
			var val = $('#address').val();
			$.post('start', {
				host : val
			}, function successStart() {
				intervalId = window.setInterval(function() {
					$.get('status', function(data) {
						$('#info').html(data);
					}, 'html');
				}, 1000);
			});
		});

		$('#stop').click(function onStopClick() {
			$.post('stop', null, function successStop() {
				window.clearInterval(intervalId);
			});
		});
	});
</script>
</head>
<body>
	<header>
		<div id="control">
			<h1>Bloomberg Agent at <%=request.getLocalAddr()%></h1>
			Сервер Jobber <input id="address" type="text" size="50"
				value="http://172.16.15.36:10180/Jobber/AgentTask" /><br /> Агент <input
				id="start" type="button" value="Старт" /> <input id="stop"
				type="button" value="Стоп" />
		</div>
	</header>
	<div id="info">
	<jsp:useBean id="worker" class="ru.prbb.agent.services.TaskWorkerService">
	</jsp:useBean>
	<jsp:getProperty property="status" name="worker"/>
	</div>
</body>
</html>
