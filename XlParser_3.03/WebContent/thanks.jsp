<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description"
	content="Returns the path of the saved excel file">
<meta name="author" content="Anurag Chowdhury & Prateek Gupta">
<link href="bootstrap/css/bootstrap.css" rel="stylesheet">
<style>
.imgClass {
	background-image: url(images/home3.png);
	background-position: 50% 0%;
	background-repeat: no-repeat;
	width: 50px;
	height: 50px;
	border: 0px;
	cursor: pointer;
}

.imgClass:hover {
	background-position: 0px -52px;
}

.imgClass:active {
	background-position: 0px -104px;
}
</style>
<title>TAP - Check the Work Load</title>
</head>
<body>

	<form action="home.action"
		style="position: absolute; top: 0%; left: 47%">
		<input type="submit" name="Home" value="          " class="imgClass">
	</form>

	<h3 class="form-signin-heading"
		style="left: 30%; top: 5%; position: absolute;">Thanks for using
		Ticket Assignment Portal.</h3>
</body>
</html>