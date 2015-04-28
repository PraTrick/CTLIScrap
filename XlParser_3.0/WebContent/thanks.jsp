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
    cursor:pointer;
}

.imgClass:hover {
	background-position: 0px -52px;
}

.imgClass:active {
	background-position: 0px -104px;
}
</style>
<title>Thanks!!</title>
</head>
<body>

	<form action="home.action"
		style="position: absolute; top: 0%; left: 49%">
		<input type="submit" name="Home" value="" class="imgClass">
	</form>

	<h3 class="form-signin-heading"
		style="left: 30%; top: 5%; position: absolute;">Thanks for using
		Ticket Assignment portal.</h3>
	<div class="container"
		style="top: 20%; position: absolute; left: 12%; border: medium; border-color: black; border-style: groove; background-color: #87CEFA">

		<h4 style="position: relative; left: 10%;">Your file has been
			saved at ${browseFile}</h4>
	</div>
</body>
</html>