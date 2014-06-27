<%@ page contentType="text/html;charset=Windows-1251"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.DriverManager"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.ResultSetMetaData"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="java.sql.Statement"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<!DOCTYPE html>
<html>
<head>
<title>Direct JDBC JSP</title>
</head>
<body>
	<h3>QUOTES</h3>
	<%
		out.print(new SimpleDateFormat("dd.MM.yyyy ").format(new Date()));

		Class.forName("com.sybase.jdbc3.jdbc.SybDriver");

		String url = "jdbc:sybase:Tds:172.16.21.14:5000/inv_db";
		Connection conn = DriverManager.getConnection(url, "dgluhov", "lbfcjan");

		try {
			String sql = "select top 1 run_time from xls_quotes_v order by color, SecShortName";
			Statement stmt = null;
			ResultSet rs = null;
			try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				if (rs.next()) {
					out.println(rs.getString(1));
				}
			} finally {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			}
		} catch (Exception e) {
			conn.close();
			throw e;
		}
	%>
	<table style="width: 93%" border="1">
		<tr>
			<%
				String s[] = { "Ticker", "Last Price", "Change Close,%", "last" };
				for (int i = 0; i < s.length; ++i) {
					out.print("<th>");
					out.print(s[i]);
					out.print("</th>");
				}
			%>
		</tr>
		<%
			try {
				String sql = "select SecShortName ,last, lastchangecalc, chngtime, color" +
						" from xls_quotes_v order by color, SecShortName";
				Statement stmt = null;
				ResultSet rs = null;
				try {
					stmt = conn.createStatement();
					rs = stmt.executeQuery(sql);
					while (rs.next()) {
						String bgcolor = "";
						int color = rs.getInt("color");
						switch (color) {
						case 1:
							bgcolor = "#FFFF00";
							break;
						case 2:
							bgcolor = "#AFEEEE";
							break;
						case 3:
							bgcolor = "#9370D8";
							break;
						case 4:
							bgcolor = "#FFFFFF";
							break;
						case 5:
							bgcolor = "#DA70D6";
							break;
						}

						if (bgcolor.isEmpty()) {
							out.println("<tr>");
						} else {
							out.println("<tr bgcolor='" + bgcolor + "'>");
						}

						out.print("\t\t\t");
						for (int c = 0; c < s.length; ++c) {
							out.print("<td>");
							out.print(rs.getString(c + 1));
							out.print("</td>");
						}

						out.println("</tr>");
					}
				} finally {
					if (rs != null)
						rs.close();
					if (stmt != null)
						stmt.close();
				}
			} catch (Exception e) {
				conn.close();
				throw e;
			}

			conn.close();
		%>
	</table>
</body>
</html>
