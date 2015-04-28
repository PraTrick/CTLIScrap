<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ include file="navbarTemplate.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Home-Ensemble CSM</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<link rel="stylesheet" href="scripts/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="scripts/jquery-ui.js"></script>
<link rel="stylesheet" href="stylesheets/tableSorter.css">
<script src="scripts/jquery.tablesorter.widgets.js"></script>
<%-- <script type="text/javascript" src="scripts/jquery-latest.js"></script> --%>

<script>
function copyToClipboard(divID) {
    var div = document.getElementById(divID);
    div.contentEditable = 'true';
    var controlRange;
    if (document.body.createControlRange) {
    controlRange = document.body.createControlRange();
    controlRange.addElement(div);
    controlRange.execCommand('Copy');
    }
    div.contentEditable = 'false'; 
}
</script>
<script>




	$(function() {
		$("#from").datepicker({
			defaultDate : "+1w",
			changeMonth : true,
			numberOfMonths : 2,
			onClose : function(selectedDate) {
				$("#to").datepicker("option", "minDate", selectedDate);
			}
		});
		$("#to").datepicker({
			defaultDate : "+1w",
			changeMonth : true,
			numberOfMonths : 2,
			onClose : function(selectedDate) {
				$("#from").datepicker("option", "maxDate", selectedDate);
			}
		});
	});
	
	  


</script>

<style type="text/css">
table {
	font-size: 14px;
	background-color: #4D4D4D;
}

table th {
	text-align: left;
	background-color: #6D9BF1;
	color: white;
}

tr:nth-child(even) {
	background-color: #C6E2FF
}

tr:nth-child(odd) {
	background-color: #DAF4F0
}

table.tablesorter .header {
	border: 2px solid white;
	padding-left: 30px;
	padding-top: 8px;
	height: auto;
}
</style>

</head>

<body>


	<div class="container">

		<h3 class="form-signin-heading" align="center">BOT Report</h3>
		<br> <br> <br> <br> <br>


		<s:if test="%{portalDataList==null || error != null}">
			<form class="form-signin" action="botreport.action" id="frm1">
				<div align="center">
					From <input type="text" id="from" name="fromDate" /> To <input
						type="text" id="to" name="toDate" />
				</div>

				<br> <br> <input
					style="left: 39%; top: 50%; position: relative;" class=" btn "
					type="file" multiple="multiple" name="browseFile" id="browseFile"
					required="required" autofocus="autofocus" /> <br> <br>

				<button style="left: 45%; top: 45%; position: absolute;"
					type="submit" class=" btn btn-primary">Generate Report</button>

				<br>

			</form>
			<br>
			<br>
			<br>
			<br>
			<font color="#FF0000">${error}</font>


		</s:if>
	</div>
	
	<s:if test="%{portalDataList!=null && error == null}">
	<a
            href="mailto:${mailTo}
    ?cc=${cc}
    &subject=${subject}
    &body=${body}"
            onclick="copyToClipboard('selectable')">Send Email</a>
	</s:if>
	
    <div id="selectable" >
	<s:if test="%{portalDataList!=null && error == null}">

		

        
		<table border="1" id="myTable" class="tablesorter"
			style="margin-left: auto; margin-right: auto;">
			<tr>
				<th>Title</th>
				<th>Issue Keyword</th>
				<th>Issue Assigned Date</th>
				<th>Assigned To</th>

			</tr>
			<s:iterator value="portalDataList" status="key">
				<tr>
					
						<td><s:property value="title" /></td>
						<td><s:property value="issueKeyword" /></td>
						<td><s:property value="assignedDate" /></td>
						<td><s:property value="assignedTo" /></td>
						
				</tr>

			</s:iterator>
		</table>

		<br>
		<br>

				
	</s:if>
</div>

</body>
</html>
