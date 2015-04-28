<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta charset="utf-8">
<title>ManuallyAssigned Tickets</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Manual ticket assigning tool">
<meta name="author" content="Anurag Chowdhury & Prateek Gupta">

<script type="text/javascript" src="scripts/jquery-latest.js"></script>
<link rel="stylesheet" href="scripts/themes/blue/style.css">
<link rel="stylesheet" href="stylesheets/tableSorter.css">
<script type="text/javascript" src="scripts/jquery.tablesorter.js"></script>
<script src="scripts/jquery.tablesorter.widgets.js"></script>
<script type="text/javascript" src="scripts/scrollfix.js"></script>

<script id="js">
	var myValue;
	var newWindow;
	
	function changeVal(value , formId) {
		myValue = value;
		formId.form.submit();
	}

	window.onbeforeunload = function() {
		if (window.myValue != "formcall")
			location.reload();
	};

	function select_all(urlObj) {
		window.clipboardData.setData('Text', urlObj);
	}

	
	$(document).ready(function() {
		$("table").tablesorter({
			sortList : [ [ 2, 0 ] ]
		});
	});

	$(function() {

		// call the tablesorter plugin
		$("table.tablesorter").tablesorter({
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
				filter_childRows : true,

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

	$(function() {

		// *** widgetfilter_startsWith toggle button ***
		$('button.toggle')
				.click(
						function() {
							var c = $('table.tablesorter')[0].config,
							// toggle the boolean
							fsw = !c.widgetOptions.filter_startsWith, fic = !c.widgetOptions.filter_ignoreCase;
							if ($(this).hasClass('fsw')) {
								c.widgetOptions.filter_startsWith = fsw;
								$('#start').html(fsw.toString());
							} else {
								c.widgetOptions.filter_ignoreCase = fic;
								$('#case').html(fic.toString());
							}
							// update search after option change; add false to trigger to skip search delay
							$('table.tablesorter').trigger('search', false);
						});

	});
</script>

<style type="text/css">
.buttonStyle1 {
	background-color: #e0e0e0;
	-moz-border-radius: 7px;
	-webkit-border-radius: 7px;
	border-radius: 7px;
	border: 2px;
	display: inline-block;
	color: #777777;
	font-family: arial;
	font-size: 11px;
	font-weight: bold;
	padding: 3px 2px;
	text-decoration: none;
}

.buttonStyle1:hover {
	background-color: #dfdfdf;
}

.buttonStyle1:active {
	position: relative;
	top: 1px;
}
</style>
<style type="text/css">
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


<title>XceL-Parser</title>
</head>
<body onunload="unloadP('UniquePageNameHereScroll')" onload="loadP('UniquePageNameHereScroll')">


	<form action="home.action"
		style="position: absolute; top: 0%; left: 50%">
		<input type="submit" name="Home" value="" class="imgClass"
			onclick="changeVal('formcall');">
	</form>

	<br>
	<br>
	<h1 style="position: absolute; left: 35%">Manual Ticket Assignment</h1>
	<br>
	<br>
	<br>
	<br>

	<table border="1" id="myTable1" class="tablesorter">
		<thead>
			<s:iterator value="modifiedTable" var="testVar" status="stat">
				<s:if test="%{key == 0}">
					<tr>
						<s:iterator value="#testVar.value" var="testVar1"
							status="testStat">
							<th data-placeholder=""><s:property value="%{#testVar1}" /></th>
						</s:iterator>

					</tr>

				</s:if>
			</s:iterator>
		</thead>

		<tbody>
			<s:iterator value="modifiedTable" var="testVar" status="stat">
				<s:if test="%{key != 0}">
					<tr>
						<s:iterator value="#testVar.value" var="testVar1"
							status="testStat">

							<s:if test="%{#testStat.index == 0}">
								<td>
									<span style="display: none;"><s:property value="%{#testVar1}" /></span>
									<form action="modifyStatus.action">
										
										<s:set name="parsedSheet" value="modifiedTable"
											scope="session" />
										<s:hidden name="button" value="%{key}" />
										<s:select name="theElement" list="%{statusList}"
											value="%{#testVar1}" theme="simple" onchange="changeVal('formcall', this);"/>
									</form>
								</td>
							</s:if>

							<s:elseif test="%{#testStat.index == 1}">
								<td>
									<span style="display: none;"><s:property value="%{#testVar1}" /></span>
									<s:hidden id="%{#testVar1}" value="%{#testVar1}" />	
								<a href="http://10.249.8.216:8080/WebRepSerena/baseFormatPage.jsp?topic=sql&page=listIRById"
									target="_blank" onclick="select_all(document.getElementById('${testVar1}').value);">
									<s:property value="%{#testVar1}" />
								</a></td>
							</s:elseif>

							<s:elseif test="%{#testStat.index == 2}">
								<td>
									<span style="display: none;"><s:property value="%{#testVar1}" /></span> 
									<form action="modifyName.action">
										<s:set name="parsedSheet" value="modifiedTable"
											scope="session" />
										<s:hidden name="button" value="%{key}" />
										<s:select name="theElement" list="%{nameList}"
											value="%{#testVar1}" theme="simple" onchange="changeVal('formcall', this);"/>
									</form></td>
							</s:elseif>
							<s:else>
								<td><s:property value="%{#testVar1}" /></td>
							</s:else>

						</s:iterator>
					</tr>
				</s:if>
			</s:iterator>
		</tbody>

	</table>

	<form action="writeManualExcel.action">
		<s:set name="parsedSheet" value="modifiedTable" scope="session" />
		<button type="submit" class="buttonStyle2"
			onclick="changeVal('formcall');">Save to Excel</button>
		<br> <br>
	</form>

</body>
</html>