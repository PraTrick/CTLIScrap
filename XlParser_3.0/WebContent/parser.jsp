<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ include file="navbarTemplate.jsp" %>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<title>Ticket Assignment Portal</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description"
	content="Home page of the ticket assignment portal">
<meta name="author" content="Anurag Chowdhury & Prateek Gupta">

<script type="text/javascript">
		function submitManually()
		{
			document.getElementById("assignType").value = "manual";
			document.getElementById("frm1").submit();
		}
		function submitAutomatically()
		{
			document.getElementById("assignType").value = "automatic";
		    document.getElementById("frm1").submit();
		}
		</script>


<title>XceL-Parser</title>

</head>

<body>
	<div class="container">

		<h3 class="form-signin-heading"
			style="left: 38%; top: 6%; position: absolute;">Ticket
			Assignment Portal</h3>
		<div class="container"
			style="top: 20%; left: 10%; position: absolute;">
			<form class="form-signin" action="read.action" id="frm1">
				<input style="left: 30%; top: 17%; position: absolute;"
					class=" btn " type="file" multiple="multiple" name="browseFile"
					id="browseFile" required="required" autofocus="autofocus" /> <br>
				<br>
				<s:hidden id="assignType" name="assignType" value="automatic" />
				<br>
			</form>
			<br> <br>
			<button type="submit" class=" btn btn-large btn-primary"
				onclick="submitAutomatically()">Assign Automatically</button>
			<button type="submit" style="position: absolute; left: 70%"
				class=" btn btn-large btn-primary" onclick="submitManually()">Assign
				Manually</button>

			<br> <br> <font color="#FF0000">${error}</font>

		</div>
	</div>
</body>
</html>