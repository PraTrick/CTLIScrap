<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Intermediate 'meme' sheet">
<meta name="author" content="Anurag Chowdhury & Prateek Gupta">

<script type="text/javascript">
	function myfunc() {
		var frm = document.getElementById("getEscalatedTickets");
		frm.submit();
	}
	window.onload = myfunc;
</script>


<title>Welcome</title>

</head>

<body
	style="background-image: url('images/loading.gif'); background-repeat: no-repeat; background-position:center;">
	<form id="getEscalatedTickets" action="getEscalatedTickets.action"></form>

</body>

</html>