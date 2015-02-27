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
	
	function updateStatus() {
		$.get('status', function(data) {
			$('#serversBody').html(data);
		}, 'html');
	}

	$(document).ready(function(doc) {
		updateStatus();
		intervalId = window.setInterval(updateStatus, 5000);

		$('#add').click(function onStartClick() {
			var val = $('#address').val();
			//$.post('start', {
			//	host : val
			//}, function successStart() {
			//});
		});

		$('#del').click(function onStopClick() {
			var val = $('#address').val();
			//$.post('stop', null, function successStop() {
			//	window.clearInterval(intervalId);
			//});
		});

	});
</script>
</head>
<body>
	<header>
		<div id="control">
			<h1>Bloomberg Agent at <%=request.getLocalAddr()%></h1>
			Сервер Jobber <input id="address" type="text" size="50"
				value="172.16.15.36:10180" />
			<input id="add" type="button" value="Добавить" />
		 	<input id="del" type="button" value="Удалить" />
		</div>
	</header>

	<div id="servers">
	<table border="1">
	<thead>
	<tr><td>Сервер</td><td>Статус</td></tr>
	</thead>
	<tbody id="serversBody">
	</tbody>
	</table>
	</div>

	<div id="info">
	</div>
</body>
</html>
