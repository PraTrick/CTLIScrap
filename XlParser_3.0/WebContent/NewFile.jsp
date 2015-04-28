<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>jQuery UI Datepicker - Select a Date Range</title>
<link rel="stylesheet"
	href="scripts/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="scripts/jquery-ui.js"></script>
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
<style>
div.ui-datepicker{
 font-size:10px;
}
</style>
</head>
<body>
	<label for="from">From</label>
	<input type="text" id="from" name="from" />
	<label for="to">to</label>
	<input type="text" id="to" name="to" />
</body>
</html>