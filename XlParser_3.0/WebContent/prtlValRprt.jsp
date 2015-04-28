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
<link rel="stylesheet" href="stylesheets/tableSorter.css">
<script type="text/javascript" src="scripts/jquery-latest.js"></script>
<script type="text/javascript" src="scripts/jquery.tablesorter.js"></script>
<script src="scripts/jquery.tablesorter.widgets.js"></script>
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
	
	$(function() {

		// call the tablesorter plugin
		$('table').tablesorter({
			theme : 'green',

			// hidden filter input/selects will resize the columns, so try to minimize the change
			widthFixed : true,

			// initialize zebra striping and filter widgets
			widgets : [ "zebra" ],

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
table.tablesorter {
	font-size: 14px;
	background-color: #4D4D4D;
}

table.tablesorter th {
	text-align: left;
	background-color: #6D9BF1;
	color: white;
}

table td {
	color: #000;
	padding: 0px;
	margin: 0px;
	width: auto;
	border: 2px solid white;
}

table.tablesorter .even {
	background-color: #C6E2FF;
}

table.tablesorter .odd {
	background-color: #DAF4F0;
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

		<h3 class="form-signin-heading" align="center">Portal Validation
			Report</h3>


		<s:if test="%{error == null && flag != null}">

			<s:if test="%{portalStatusMismatchList != null}">
				<div id="selectableTableOne"
					onclick="selectText('selectableTableOne')">
					<b style="position: relative; left: 43%;">Status/Sub status
						Mismatch:</b>
					<table border="1" id="myTable1" class="tablesorter"
						title="Status/Sub status Mismatch:"
						style="margin-left: auto; margin-right: auto;">
						<thead>
							<tr>
								<th>IR#</th>
								<th>ASSIGNED TO</th>
								<th>ASSIGNED DATE</th>
								<th>ISSUE STATUS</th>
								<th>ISSUE SUBSTATUS</th>

							</tr>
						</thead>

						<tbody>
							<s:iterator value="portalStatusMismatchList">
								<tr>
									<td><s:property value="IR" /></td>
									<td><s:property value="assignedTo" /></td>
									<td><s:property value="assignedDate" /></td>
									<td><s:property value="issueStatus" /></td>
									<td><s:property value="issueSubStatus" /></td>
								</tr>
							</s:iterator>
						</tbody>

					</table>
				</div>
				<br>
				<br>
			</s:if>


			<s:if test="%{portalMissingResolutionDateList != null}">
				<b style="position: relative; left: 43%;">Missing Resolution
					Date:</b>
				<table border="1" id="myTable2" class="tablesorter"
					style="margin-left: auto; margin-right: auto;">
					<thead>
						<tr>
							<th>IR#</th>
							<th>ASSIGNED TO</th>
							<th>ASSIGNED DATE</th>
							<th>ISSUE STATUS</th>
							<th>ISSUE SUBSTATUS</th>
							<th>RESOLUTION DATE</th>
						</tr>
					</thead>

					<tbody>
						<s:iterator value="portalMissingResolutionDateList">
							<tr>
								<td><s:property value="IR" /></td>
								<td><s:property value="assignedTo" /></td>
								<td><s:property value="assignedDate" /></td>
								<td><s:property value="issueStatus" /></td>
								<td><s:property value="issueSubStatus" /></td>
								<td><s:property value="resolutionDate" /></td>
							</tr>
						</s:iterator>
					</tbody>
				</table>

				<br>
				<br>
			</s:if>

			<s:if test="%{portalMissingTATList != null}">
				<b style="position: relative; left: 39%;">Incorrect/Missing
					Turnaround Time:</b>
				<table border="1" id="myTable3" class="tablesorter"
					style="margin-left: auto; margin-right: auto;">
					<thead>
						<tr>
							<th>IR#</th>
							<th>ASSIGNED TO</th>
							<th>ASSIGNED DATE</th>
							<th>ISSUE STATUS</th>
							<th>ISSUE SUBSTATUS</th>
							<th>TAT</th>
							<th>ACTUAL HOURS</th>
						</tr>
					</thead>

					<tbody>
						<s:iterator value="portalMissingTATList">
							<tr>
								<td><s:property value="IR" /></td>
								<td><s:property value="assignedTo" /></td>
								<td><s:property value="assignedDate" /></td>
								<td><s:property value="issueStatus" /></td>
								<td><s:property value="issueSubStatus" /></td>
								<td><s:property value="TAT" /></td>
								<td><s:property value="actualTime" /></td>
							</tr>
						</s:iterator>
					</tbody>
				</table>

				<br>
				<br>
			</s:if>

			<s:if test="{portalZeroWRNo != null}">
				<b style="position: relative; left: 43%;">Incorrect/Zero WR#:</b>
				<table border="1" id="myTable4" class="tablesorter"
					style="margin-left: auto; margin-right: auto;">
					<thead>
						<tr>
							<th>IR#</th>
							<th>ASSIGNED TO</th>
							<th>ASSIGNED DATE</th>
							<th>ISSUE STATUS</th>
							<th>ISSUE SUBSTATUS</th>
							<th>WR#</th>

						</tr>
					</thead>

					<tbody>
						<s:iterator value="portalZeroWRNo">
							<tr>
								<td><s:property value="IR" /></td>
								<td><s:property value="assignedTo" /></td>
								<td><s:property value="assignedDate" /></td>
								<td><s:property value="issueStatus" /></td>
								<td><s:property value="issueSubStatus" /></td>
								<td><s:property value="WRNo" /></td>
							</tr>
						</s:iterator>
					</tbody>

				</table>

				<br>
				<br>
			</s:if>


			<s:if test="{portalRemedyCreatedResolved != null}">
				<b style="position: relative; left: 35%;">Issue Sub-Status Still
					Remains as 'Remedy Created'</b>
				<table border="1" id="myTable5" class="tablesorter"
					style="margin-left: auto; margin-right: auto;">
					<thead>
						<tr>
							<th>IR#</th>
							<th>ASSIGNED TO</th>
							<th>ASSIGNED DATE</th>
							<th>ISSUE STATUS</th>
							<th>ISSUE SUBSTATUS</th>
							<th>RESOLUTION DATE</th>

						</tr>
					</thead>

					<tbody>
						<s:iterator value="portalRemedyCreatedResolved">
							<tr>
								<td><s:property value="IR" /></td>
								<td><s:property value="assignedTo" /></td>
								<td><s:property value="assignedDate" /></td>
								<td><s:property value="issueStatus" /></td>
								<td><s:property value="issueSubStatus" /></td>
								<td><s:property value="resolutionDate" /></td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
			</s:if>
		</s:if>

		<s:if test="%{error != null || flag == null}">

			<div class="container"
				style="top: 13%; left: 12%; position: absolute;">
				<form class="form-signin" id="frm2" action="validate.action">
					<input style="left: 33%; top: 17%; position: absolute;"
						class=" btn " type="file" multiple="multiple" name="browseFile"
						id="browseFile" required="required" autofocus="autofocus" /> <br>
					<br>
					<s:hidden id="assignType" name="assignType" value="automatic" />
					<br> <br>
					<button style="align: center; position: absolute;" type="submit"
						class=" btn btn-primary">Generate Report</button>
				</form>
				<br> <br> <font color="#FF0000">${error}</font>

			</div>
		</s:if>
	</div>
</body>
</html>
