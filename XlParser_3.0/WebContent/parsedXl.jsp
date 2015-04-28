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
    function myfunc () {
        var frm = document.getElementById("saveToExcel");
        frm.submit();
        /* 
        var frm1 = document.getElementById("getEscalatedTickets");
		frm1.submit(); */
    }
    window.onload = myfunc;
</script>


<title>Generating Xcel</title>

</head>

<body
	style="background-image: url('images/Generating.jpg'); background-repeat: no-repeat;">
	<h2 style="position: absolute; left: 50%; font-size: x-large;">Generating...</h2>
	<br>
	<br>
	<s:set name="parsedSheet" value="table" scope="session" />
	<form id="saveToExcel" action="assignTickets.action"></form>
	<!-- <form id="getEscalatedTickets" action="getEscalatedTickets.action"></form> -->

</body>

</html>