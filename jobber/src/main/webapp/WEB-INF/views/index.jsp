<%@ page pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Jobber</title>
</head>
<body>
	<div>
		<h1>BloombergSession</h1>
		<ol>
			<li>0 00 3 * * ? <a href="rest/BdsLoad">executeBdsLoad</a></li>

			<li>0 15 4 * * ? <a href="rest/FuturesLoad">executeFuturesLoad</a></li>

			<li>0 00 5 * * ? <a href="rest/QuotesLoad">executeQuotesLoad</a></li>

			<li>0 10 5 * * ? <a href="rest/AtrLoad">executeAtrLoad</a></li>

			<li>0 00 6 * * ? <a href="rest/BdpOverrideLoad">executeBdpOverrideLoad</a></li>

			<li>0 00 7 * * ? <a href="rest/HistDataLoad">executeHistDataLoad</a></li>

			<li>0 00 8 * * ? <a href="rest/CurrenciesDataLoad">executeCurrenciesDataLoad</a></li>

			<li>0 59 11-18 * * ? <a href="rest/BondsLoad">executeBondsLoad</a></li>
		</ol>
	</div>
</body>
</html>
