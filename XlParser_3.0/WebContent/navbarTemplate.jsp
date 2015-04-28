<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<link href="bootstrap/css/bootstrap.css" rel="stylesheet">
<style>
body {
	padding-top: 60px;
}
</style>
<link href="bootstrap/css/bootstrap-responsive.css" rel="stylesheet">


</head>

<body>

	<div class="navbar navbar-inverse navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<button type="button" class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<span class="brand">Ensemble CSM</span>
				<div class="nav-collapse collapse">
					<ul class="nav">
						<li class="active"><a href="home.jsp">Home</a></li>
						<li><a href="parser.jsp">Ticket Assignment</a></li>
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown">Reports <b class="caret"></b></a>
							<ul class="dropdown-menu">
								<li><a href="prtlValRprt.jsp">Portal Validation</a></li>
								<li class="divider"></li>
								<li class="nav-header">Ticketing</li>
								<li><a href="ticketWeeklyReport.jsp">Tickets
										Report</a></li>
								<!-- <li><a href="ticketMonthlyReport.jsp">Tickets - Monthly
										Report</a></li> -->
							</ul></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	
	<script src="scripts/jquery-1.9.1.js"></script>
	<script src="bootstrap/js/bootstrap.js"></script>
</body>
</html>
