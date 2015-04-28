<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ include file="navbarTemplate.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Home-Ensemble CSM</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
</head>

<body>
	<div class="container">

		<h3 class="form-signin-heading" align = "center" >Ticketing Report - Monthly</h3>
        <div class="container"
            style="top: 13%; left: 12%; position: absolute;">
            <form class="form-signin" action="" id="frm1">
                <input style="left: 33%; top: 17%; position: absolute;" 
                    class=" btn " type="file" multiple="multiple" name="browseFile"
                    id="browseFile" required="required" autofocus="autofocus" /> <br>
                <br>
                <s:hidden id="assignType" name="assignType" value="automatic" />
                <br>
            </form>
            <br> <br>
            <button style="left: 85%; top: 20%; position: absolute;" type="submit" class=" btn btn-primary"
                onclick="submitAutomatically()">Generate Report</button>
            

            <br> <br> <font color="#FF0000">${error}</font>

        </div>
    </div>

</body>
</html>
