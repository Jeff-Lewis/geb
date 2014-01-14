<%@ page pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<title>Spring MVC Bloomberg Application</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
	<div>
		<ol>
			<li>Загрузка доходности облигаций <a href="/rest/BondYeildLoad">/rest/BondYeildLoad</a>
				(String[] securities{code}, String begin, String end)
			</li>
			<li>Загрузка дат погашений <a href="/rest/CashFlowLoad">/rest/CashFlowLoad</a>
				(String[] securities{id:date:name})
			</li>
			<li>Ввод нового инструмента <a href="/rest/InstrumentAdd">/rest/InstrumentAdd</a>
				(String[] instruments{type:code})
			</li>
			<li>Загрузка котировок <a href="/rest/QuotesLoad">/rest/QuotesLoad</a>
				(String[] securities{code}, String begin, String end)
			</li>
			<li>Загрузка ставки по купонам <a href="/rest/RateCouponLoad">/rest/RateCouponLoad</a>
				(String[] securities{id:name})
			</li>
			<li>Загрузка номинала <a href="/rest/ValuesLoad">/rest/ValuesLoad</a>
				(String[] securities{id:name})
			</li>
		</ol>
	</div>
</body>
</html>
