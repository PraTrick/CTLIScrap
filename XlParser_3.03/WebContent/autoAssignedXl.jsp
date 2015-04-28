<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ page import="java.lang.*"%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description"
	content="Automatically assigns tickets and displays data">
<meta name="author" content="Anurag Chowdhury & Prateek Gupta">

<!-- Le styles -->
<link href="bootstrap/css/bootstrap.css" rel="stylesheet">
<link rel="stylesheet" href="scripts/themes/blue/style.css">
<link href="stylesheets/formFormatter.css" rel="stylesheet">
<link rel="stylesheet" href="stylesheets/tableSorter.css">
<script type="text/javascript" src="scripts/jquery-latest.js"></script>
<script type="text/javascript" src="scripts/jquery.tablesorter.js"></script>
<script id="js">
	function selectText(containerid) {
		if (document.selection) {
			var range = document.body.createTextRange();
			range.moveToElementText(document.getElementById(containerid));
			range.select();
		} else if (window.getSelection) {
			var range = document.createRange();
			range.selectNode(document.getElementById(containerid));
			window.getSelection().addRange(range);
		}
	}

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

	$(function() {

		// call the tablesorter plugin
		$("#myTable2").tablesorter({
			theme : 'blue',
			// hidden filter input/selects will resize the columns, so try to minimize the change
			widthFixed : true,
			// initialize zebra striping and filter widgets
			widgets : [ "zebra", "filter" ],
			headers : {
				4 : {
					sorter : 'shortDate',
					filter : true
				}
			},
			widgetOptions : {
				// If there are child rows in the table (rows with class name from "cssChildRow" option)
				// and this option is true and a match is found anywhere in the child row, then it will make that row
				// visible; default is false
				filter_childRows : false,

				// if true, a filter will be added to the top of each table column;
				// disabled by using -> headers: { 1: { filter: false } } OR add class="filter-false"
				// if you set this to false, make sure you perform a search using the second method below
				filter_columnFilters : true,

				// css class applied to the table row containing the filters & the inputs within that row
				filter_cssFilter : 'tablesorter-filter',

				// add custom filter elements to the filter row
				// see the filter formatter demos for more specifics
				filter_formatter : null,

				// add custom filter functions using this option
				// see the filter widget custom demo for more specifics on how to use this option
				filter_functions : null,

				// if true, filters are collapsed initially, but can be revealed by hovering over the grey bar immediately
				// below the header row. Additionally, tabbing through the document will open the filter row when an input gets focus
				filter_hideFilters : true,

				// Set this option to false to make the searches case sensitive
				filter_ignoreCase : true,

				// if true, search column content while the user types (with a delay)
				filter_liveSearch : true,

				// jQuery selector string of an element used to reset the filters
				filter_reset : 'button.reset',

				// Delay in milliseconds before the filter widget starts searching; This option prevents searching for
				// every character while typing and should make searching large tables faster.
				filter_searchDelay : 300,

				// if true, server-side filtering should be performed because client-side filtering will be disabled, but
				// the ui and events will still be used.
				filter_serversideFiltering : false,

				// Set this option to true to use the filter to find text from the start of the column
				// So typing in "a" will find "albert" but not "frank", both have a's; default is false
				filter_startsWith : false,

				// Filter using parsed content for ALL columns
				// be careful on using this on date columns as the date is parsed and stored as time in seconds
				filter_useParsedData : false
			}
		});
	});
</script>


<style type="text/css">
.Ftable {
	width: 100%;
	border-color: #0066FF;
}

.FTable th {
	border: #4E95F4 1px solid;
	font-family: sans-serif;
	font-size: 13;
}

.FTable td {
	border: #4E95F4 1px solid;
	font-family: sans-serif;
	font-size: 13;
}

.FTable tr:nth-child(even) {
	background-color: #DAE5F4;
}

.FTable tr:nth-child(odd) {
	background-color: #B8D1F3;
}

.buttonStyle2 {
	-moz-box-shadow: inset 0px 1px 0px 0px #caefab;
	-webkit-box-shadow: inset 0px 1px 0px 0px #caefab;
	box-shadow: inset 0px 1px 0px 0px #caefab;
	background-color: transparent;
	-moz-border-radius: 7px;
	-webkit-border-radius: 7px;
	border-radius: 7px;
	border: 2px solid #268a16;
	display: inline-block;
	color: #306108;
	font-family: arial;
	font-size: 20px;
	font-weight: bold;
	padding: 15px 24px;
	text-decoration: none;
	text-shadow: 1px 1px 0px #aade7c;
}

.buttonStyle2:active {
	position: relative;
	top: 1px;
}

#fixed {
	width: 100%;
	height: 30px;
	position: absolute;
	top: 0;
	background-color: #e0e0eA;
	z-index: -2
}

.imgClass {
	background-image: url(images/home3.png);
	background-position: 40% 0%;
	background-repeat: no-repeat;
	width: 300px;
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

<link href="bootstrap/css/bootstrap-responsive.css" rel="stylesheet">
<link rel="apple-touch-icon-precomposed" sizes="144x144"
	href="bootstrap/ico/apple-touch-icon-144-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114"
	href="bootstrap/ico/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72"
	href="bootstrap/ico/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed"
	href="bootstrap/ico/apple-touch-icon-57-precomposed.png">
<link rel="shortcut icon" href="bootstrap/ico/favicon.png">

<title>TAP - Check the Work Load</title>
</head>
<body>

	<form action="home.action"
		style="position: absolute; top: 0%; left: 47%">
		<input type="submit" name="Home" value="          " class="imgClass">
	</form>

	<br>
	<br>
	<br>

	<%
	    java.text.DateFormat df = new java.text.SimpleDateFormat(
	            "dd/MM/yyyy");
	%>
	<h2>
		Daily ticket assignment (<%=df.format(new java.util.Date())%>)
	</h2>
	<a
		href="mailto:${mailTo}
	?cc=${cc}
	&subject=${subject} (<%= df.format(new java.util.Date()) %>)
	&body=${body}
	Total No. of Tickets : ${totalTickets}
	%0DTotal No. of Assigned Tickets : ${totalAssignedTickets}
	%0DTotal No. of Pending Tickets : ${totalPendingTickets}
	%0DTotal No. of Ignored Tickets : ${totalRestrictedTickets}
	"
		onclick="copyToClipboard('selectable')">Send Email</a>
	<br>
	<br>
	<div id="selectable" onclick="selectText('selectable')">
		<s:if test="%{newTicketList != null}">
			<div>
				<b>New Assigned Tickets</b>
				<table border="1" id="myTable3" class="FTable">
					<s:iterator value="newTicketList" var="testVar">
						<s:if test="key == 'Individual+:'">
							<tr>
								<th><s:property value="key" /></th>
								<th><s:property value="value" /></th>
							</tr>
						</s:if>
					</s:iterator>

					<s:iterator value="newTicketList" var="testVar">
						<s:if test="key != 'Individual+:'">
							<tr>
								<td><s:property value="key" /></td>
								<td><s:property value="value" /></td>
							</tr>
						</s:if>
					</s:iterator>
				</table>
			</div>
			<br>
		</s:if>
		<div>
			<b>Tickets</b>
			<table border="1" id="myTable2" class="tablesorter">
				<thead>
					<s:iterator value="assignedTable" var="testVar" status="stat">
						<s:if test="%{key == 0}">
							<tr>
								<s:iterator value="#testVar.value" var="testVar1"
									status="testStat">
									<th><s:property value="%{#testVar1}" /></th>
								</s:iterator>
							</tr>
						</s:if>
					</s:iterator>
				</thead>

				<tbody>
					<s:iterator value="assignedTable" var="testVar" status="stat">
						<s:if test="%{key != 0}">
							<tr>
								<s:iterator value="#testVar.value" var="testVar1"
									status="testStat">
									<td><s:property value="%{#testVar1}" /></td>
								</s:iterator>
							</tr>
						</s:if>
					</s:iterator>
				</tbody>
			</table>
		</div>
	</div>

	<form action="thanks.action">
		<button type="submit" class="buttonStyle2">Take Me Away</button>
	</form>

</body>
</html>