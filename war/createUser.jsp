<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create new account</title>
<link href="css/shoppingapp.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/shoppingapp.js"></script>
<script type="text/javascript">
	function startUp() {
		$("userId").focus();
		// populate expiry date
		var month = new Array("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept", "Oct", "Nov", "Dec");
		var selectEl = $("expiryMonth");
		for (var i = 0; i < month.length; i++) {
			var opt = document.createElement("option");
			opt.value = (i + 1);
			opt.text = month[i];
			try {
				selectEl.add(opt, null); // standards compliant; doesn't work in IE
			} catch(ex) {
				selectEl.add(opt); // IE only
			}
		}
		selectEl = $("expiryYear");
		var year = new Date();
		var year2 = year.getFullYear();
		for (var y = 0; y < 20; y++) {
			var opt = document.createElement("option");
			opt.value = year2 + y;
			opt.text = year2 + y;
			try {
				selectEl.add(opt, null); // standards compliant; doesn't work in IE
			} catch(ex) {
				selectEl.add(opt); // IE only
			}
		}
	}
</script>
</head>

<body onload="startUp()">
	<div id="createUserDiv">
		<h1>Create new account</h1>
		<div id="infoMsg">All fields are compulsory</div>
		<form action="/createNewUserServlet" method="POST">
		<h3>Profile</h3>
		<table border="0">
			<tbody>
				<tr>
					<td class="label">User Id</td>
					<td><input type="text" id="userId" name="userId" class="value" /></td>
					<td><div id="userIdErrorMsg" class="errorMsg"></div></td>
				</tr>
				<tr>
					<td class="label">Name</td>
					<td><input type="text" id="name" name="name" class="value"/></td>
					<td><div id="nameErrorMsg" class="errorMsg"></div></td>
				</tr>
				<tr>
					<td class="label">Email</td>
					<td><input type="text" id="email" name="email" class="value"/></td>
					<td><div id="emailErrorMsg" class="errorMsg"></div></td>
				</tr>
				<tr>
					<td class="label">Password</td>
					<td><input type="text" id="password" name="password" class="value"/></td>
					<td><div id="passwordErrorMsg" class="errorMsg"></div></td>
				</tr>
			</tbody>
		</table>
		<h3>Payment Details</h3>
		<table border="0">
			<tbody>
				<tr>
					<td class="label">Card holder name</td>
					<td><input type="text" id="cardHolderName" name="cardHolderName" class="value"/></td>
					<td><div id="cardHolderNameErrorMsg" class="errorMsg"></div></td>
				</tr>
				<tr>
					<td class="label">Type of card</td>
					<td><input type="radio" id="typeOfCard" name="typeOfcard" value="visa" checked="checked" /> VISA
					<input type="radio" id="typeOfCard" name="typeOfcard" value="master" checked="checked" /> MASTER</td>
					<td><div id="typeOfCardErrorMsg" class="errorMsg"></div></td>
				</tr>
				<tr>
					<td class="label">Credit Card number</td>
					<td><input type="text" id="cardNumber" name="cardNumber" class="value"/></td>
					<td><div id="cardNumberErrorMsg" class="errorMsg"></div></td>
				</tr>
				<tr>
					<td class="label">Expiry Date</td>
					<td><select id="expiryMonth" name="expiryMonth" title="Month" >
							<option selected="selected"  value="0">Month</option>
						</select><select id="expiryYear" name="expiryYear" title="Year">
							<option selected="selected">Year</option>
						</select></td>
					<td><div id="expiryDateErrorMsg" class="errorMsg"></div></td>
				</tr>
			</tbody>
		</table>
		<input type="submit" id="submit" name="submit" value="Create" class="button" />
		</form>
	</div>
</body>
</html>