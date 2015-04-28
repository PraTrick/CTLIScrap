<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Task Report Tool</title>
<link href="bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="bootstrap/css/bootstrap-responsive.css" rel="stylesheet">
<link rel="stylesheet" href="scripts/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="scripts/jquery-ui.js"></script>
<script type="text/javascript">
	function addRow(tableID) {
		var table = document.getElementById(tableID);
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);

		/* var cellChkBox = row.insertCell(0);
		var inputNode = document.createElement('input');
		inputNode.name = 'chk' + rowCount;
		inputNode.type = 'checkbox";
		cellChkBox.appendChild(inputNode); */

		var cellTaskSelect = row.insertCell(0);
		var sel = document.createElement("select");
		sel.name = "row[" + rowCount + "].selTaskRow";
		sel.options[0] = new Option("Data Fix", "dataFix");
		sel.options[1] = new Option("Code Fix", "codeFix");
		sel.options[2] = new Option("Enhancement", "enhancement");
		cellTaskSelect.appendChild(sel);

		var cellComplexitySelect = row.insertCell(1);
		var sel = document.createElement("select");
		sel.name = "row[" + rowCount + "].selComplexityRow";
		sel.options[0] = new Option("Simple", "simple");
		sel.options[1] = new Option("Medium", "medium");
		sel.options[2] = new Option("Complex", "complex");
		cellComplexitySelect.appendChild(sel);

		var cellStatusSelect = row.insertCell(2);
		var sel = document.createElement("select");
		sel.name = "row[" + rowCount + "].selStatusRow";
		sel.options[0] = new Option("In Progress", "inProgress");
		sel.options[1] = new Option("Close", "close");
		cellStatusSelect.appendChild(sel);

		var cellSubProcessSelect = row.insertCell(3);
		var sel = document.createElement("select");
		sel.name = "row[" + rowCount + "].selSubProcessRow";
		sel.options[0] = new Option(
				"Requirements understanding/gathering/Analysis", "requirementsunderstanding/gathering/Analysis");
		sel.options[1] = new Option("Coding", "coding");
		cellSubProcessSelect.appendChild(sel);
	}
	function deleteRow(tableID) {
		try {
			var table = document.getElementById(tableID);
			var rowCount = table.rows.length;
			if (rowCount > 2)
				table.deleteRow(rowCount - 1);
			else
				alert("Cannot delete all the rows.");

		} catch (e) {
			alert(e);
		}
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
</head>
<body>
	<h3 class="form-signin-heading"
		style="left: 38%; top: 6%; position: relative;">Task Report
		Analysis</h3>
	<s:form class="form-signin" action="home.action" id="frm1"
		enctype="multipart/form-data" method="POST">
		<table id="dataTable"
			style="left: 10%; top: 10%; position: relative; border: 1px;">
			<tr>
				<!-- <th></th> -->
				<th>Task Type</th>
				<th>Complexity</th>
				<th>Status</th>
				<th>Sub Process</th>
			</tr>
			<tr>
				<!-- <td><input name="chk0" type="checkbox"></td> -->
				<td><select name="row[1].selTaskRow">
						<option value="dataFix">Data Fix</option>
						<option value="codeFix">Code Fix</option>
						<option value="enhancement">Enhancement</option>
				</select></td>
				<td><select name="row[1].selComplexityRow">
						<option value="simple">Simple</option>
						<option value="medium">Medium</option>
						<option value="complex">Complex</option>
				</select></td>
				<td><select name="row[1].selStatusRow">
						<option value="inProgress">In Progress</option>
						<option value="close">Close</option>
				</select></td>
				<td><select name="row[1].selSubProcessRow">
						<option value="requirementsunderstanding/gathering/Analysis">Requirements
							understanding/ gathering/Analysis</option>
						<option value="coding">Coding</option>
				</select></td>
			</tr>
		</table>
		<br>
		<input class=" btn btn-small btn-primary "
			onclick="addRow('dataTable')" value="Add Row" type="button"
			style="left: 10%; position: relative;" />
		<input class=" btn btn-small btn-primary "
			onclick="deleteRow('dataTable')" value="Delete Row" type="button"
			style="left: 10%; position: relative;" />
		<br>
		<br>
		<h5 style="left: 30%; position: relative;">Project Effort Report</h5>
		<s:file cssStyle="width:500px; left: 30%; position: relative"
			cssClass=" btn " name="fileUpload" />
		<br>
		<br>
		<h5 style="left: 30%; position: relative;">Detailed Sum Report</h5>
		<s:file cssStyle="width:500px; left: 30%; position: relative"
			cssClass=" btn " name="fileUpload" />
		<br>
		<br>
		<div align="center">
			From <input type="text" id="from" name="fromDate" /> To <input
				type="text" id="to" name="toDate" />
		</div>
		<br>
		<br>
		<s:submit cssClass=" btn btn-large btn-primary "
			cssStyle="right: 50%; position: relative" value="Submit"
			name="submit" />
	</s:form>
</body>
</html>