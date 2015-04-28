<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ include file="navbarTemplate.jsp"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description"
	content="Home page of the ticket assignment portal">
<meta name="author" content="Anurag Chowdhury & Prateek Gupta">

<script type="text/javascript">
	var isIE = /*@cc_on!@*/false || !!document.documentMode;

	if (!isIE) {
		alert("This application is compatible with Internet Explorer 6 and above!");
	}

	function submitAutomatically() {
		document.getElementById("frm1").submit();
	}
</script>

<title>TAP - Check the Work Load</title>
</head>

<body>
	<div class="container">

		<h3 class="form-signin-heading"
			style="left: 38%; top: 6%; position: absolute;">Ticket
			Assignment Portal</h3>
		<s:form class="form-signin" action="upload.action" id="frm1"
			enctype="multipart/form-data" method="POST">
			<s:file
				cssStyle="width:500px; top: 20%; left: 30%; position: absolute"
				cssClass=" btn " name="fileUpload" />
			<s:submit cssClass=" btn btn-large btn-primary "
				cssStyle="top: 30%; left: 45%; position: absolute" value="Submit"
				name="submit" />
		</s:form>
		<br> <br> <font color="#FF0000">${error}</font>

	</div>
</body>
</html>