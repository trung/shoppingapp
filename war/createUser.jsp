<%@page import="org.telokers.service.utils.MiscConstants"%>
<%@page import="org.telokers.service.utils.RequestUtils"%>
<%@page import="org.telokers.service.utils.HTMLEncode"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create new account</title>
<link href="/css/shoppingapp.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/js/shoppingapp.js"></script>
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

	function validate_and_submit(){
		password = $("password").value;
		cpassword = $("cpassword").value;

		if(password != cpassword){
			alert("Confirmed password does not match");
		}
		else {
			$("submitForm").submit();
		}
	}
</script>
</head>

<%
	String errorMsg = RequestUtils.getAttribute(request, MiscConstants.ERROR_MESSAGE);
	String userIdErrorMsg = RequestUtils.getAttribute(request, MiscConstants.KEY_USER_ID_ERROR_MSG);
	String nameErrorMsg = RequestUtils.getAttribute(request, MiscConstants.KEY_NAME_ERROR_MSG);
	String emailErrorMsg = RequestUtils.getAttribute(request, MiscConstants.KEY_EMAIL_ERROR_MSG);
	String passwordErrorMsg = RequestUtils.getAttribute(request, MiscConstants.KEY_PASSWORD_ERROR_MSG);
	String cardHolderNameErrorMsg = RequestUtils.getAttribute(request, MiscConstants.KEY_CARD_HOLDER_NAME_ERROR_MSG);
	String typeOfCardErrorMsg = RequestUtils.getAttribute(request, MiscConstants.KEY_TYPE_OF_CARD_ERROR_MSG);
	String cardNumberErrorMsg = RequestUtils.getAttribute(request, MiscConstants.KEY_CARD_NUMBER_ERROR_MSG);
	String expiryDateErrorMsg = RequestUtils.getAttribute(request, MiscConstants.KEY_EXPIRY_DATE_ERROR_MSG);
%>

<body onload="startUp()">
	<div id="createUserDiv">
		<h1>Create new account</h1>
		<div id="infoMsg" class="infoMsg">All fields are compulsory</div>
		<div class="<%= (errorMsg.length() > 0 ? "errorMsg" : "") %>"><%= errorMsg%></div>
		<form id="submitForm" action="/createUser" method="POST">
		<h3>Profile</h3>
		<table border="0">
			<tbody>
				<tr>
					<td class="label">User Id</td>
					<td><input type="text" id="userId" name="userId" class="value" value="<%= HTMLEncode.encode(request.getParameter("userId"))%>" /></td>
					<td><div id="userIdErrorMsg" class="<%= (userIdErrorMsg.length() > 0 ? "errorMsg" : "")%>"><%= userIdErrorMsg%></div></td>
				</tr>
				<tr>
					<td class="label">Name</td>
					<td><input type="text" id="name" name="name" class="value" value="<%= HTMLEncode.encode(request.getParameter("name"))%>"/></td>
					<td><div id="nameErrorMsg" class="<%= (nameErrorMsg.length() > 0 ? "errorMsg" : "")%>"><%= nameErrorMsg%></div></td>
				</tr>
				<tr>
					<td class="label">Email</td>
					<td><input type="text" id="email" name="email" class="value" value="<%= HTMLEncode.encode(request.getParameter("email"))%>"/></td>
					<td><div id="emailErrorMsg" class="<%= (emailErrorMsg.length() > 0 ? "errorMsg" : "")%>"><%= emailErrorMsg%></div></td>
				</tr>
				<tr>
					<td class="label">Password</td>
					<td><input type="password" id="password" name="password" class="value" value="<%= HTMLEncode.encode(request.getParameter("password"))%>"/></td>
					<td><div id="passwordErrorMsg" class="<%= (passwordErrorMsg.length() > 0 ? "errorMsg" : "")%>"><%= passwordErrorMsg%></div></td>
				</tr>
				<tr>
					<td class="label">Confirm Password</td>
					<td><input type="password" id="cpassword" name="cpassword" class="value" value="<%= HTMLEncode.encode(request.getParameter("cpassword"))%>"/></td>
					<td><div id="cpasswordErrorMsg" class="<%= (passwordErrorMsg.length() > 0 ? "errorMsg" : "")%>"><%= passwordErrorMsg%></div></td>
				</tr>
			</tbody>
		</table>
		<h3>Credit Card Details</h3>
		<table border="0">
			<tbody>
				<tr>
					<td class="label">Card holder name</td>
					<td><input type="text" id="cardHolderName" name="cardHolderName" class="value" value="<%= HTMLEncode.encode(request.getParameter("cardHolderName"))%>"/></td>
					<td><div id="cardHolderNameErrorMsg" class="<%= (cardHolderNameErrorMsg.length() > 0 ? "errorMsg" : "")%>"><%= cardHolderNameErrorMsg%></div></td>
				</tr>
				<tr>
					<td class="label">Type of card</td>
					<td><table border="0" style="margin: 0; padding: 0">
						<tr><td><input type="radio" id="typeOfCard" name="typeOfcard" value="VISA" checked="checked" /></td>
							<td>VISA &nbsp;</td>
							<td><input type="radio" id="typeOfCard" name="typeOfcard" value="MASTER" checked="checked" /></td>
							<td>MASTER</td>
						</tr>
					</table>
					</td>

					<td><div id="typeOfCardErrorMsg" class="<%= (typeOfCardErrorMsg.length() > 0 ? "errorMsg" : "")%>"><%= typeOfCardErrorMsg%></div></td>
				</tr>
				<tr>
					<td class="label">Credit Card number</td>
					<td><input type="text" id="cardNumber" name="cardNumber" class="value" value="<%= HTMLEncode.encode(request.getParameter("cardNumber"))%>" /></td>
					<td><div id="cardNumberErrorMsg" class="<%= (cardNumberErrorMsg.length() > 0 ? "errorMsg" : "")%>"><%= cardNumberErrorMsg%></div></td>
				</tr>
				<tr>
					<td class="label">Expiry Date (mmyyyy)</td>
					<td><input type="text" id="simpleExpiryDate" name="simpleExpiryDate" class="value" style="width: 45px" value="<%= HTMLEncode.encode(request.getParameter("simpleExpiryDate"))%>"/></td>
					<td><div id="expiryDateErrorMsg" class="<%= (expiryDateErrorMsg.length() > 0 ? "errorMsg" : "")%>"><%= expiryDateErrorMsg%></div></td>
				</tr>
			</tbody>
		</table>
		<input type="hidden" id="action" name="action" value="CreateUser"/>
		<input type="button" id="createButton" name="createButton" value="Create" class="button" onclick="validate_and_submit()"/>
		</form>
	</div>
</body>
</html>