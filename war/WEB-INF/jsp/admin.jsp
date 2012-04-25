<%@page import="org.telokers.service.utils.RequestUtils"%>
<%@page import="java.util.List"%>
<%@page import="org.telokers.model.User"%>
<%@page import="org.telokers.service.utils.MiscConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="/css/shoppingapp.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/js/shoppingapp.js"></script>
<title>Admin</title>
<%
	User user = (User) request.getAttribute(MiscConstants.KEY_USER);
	List<User> users = (List<User>) request.getAttribute(MiscConstants.KEY_USERS);
	String errorMsg = RequestUtils.getAttribute(request, MiscConstants.ERROR_MESSAGE);
%>
<script type="text/javascript">
	function onSelectAllClick() {
		var cb = document.getElementsByName("userIds");
		for (var i = 0; i < cb.length; i++) {
			cb[i].checked = $("selectAll").checked;
		}
	}
</script>
</head>
<body>
<div id="menuDiv">
<div style="float:left"><a href="/secured/home">&laquo; Home</a></div>
<div style="float:right">
Welcome, <%= user.getName() %>!&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Admin
|
<a href="/secured/editUser">Edit profile</a>
|
<a href="/logout">Log out</a>
</div>
</div>

<div id="mainDiv">
	<h3>Users</h3>
	<form action="/secured/admin" method="POST">
	<input type="hidden" id="csrfToken" name="csrfToken" value="<%= user.getCSRFToken()%>" />
	<div class="<%= (errorMsg.length() > 0 ? "errorMsg" : "") %>"><%= errorMsg%></div>
	<div id="buttons">
		<input type="submit" id="approve" name="approve" value="Approve" class="button" />
		<input type="submit" id="suspend" name="suspend" value="Suspend" class="button" />
	</div>
	<br/>
	<table border="0" class="container">
		<thead>
			<tr>
				<th><input type="checkbox" id="selectAll" name="selectAll" onclick="onSelectAllClick()"/></th>
				<th>User Id</th>
				<th>Account Type</th>
				<th>Status</th>
				<th>Last Modified of Status</th>
				<th>Suspension Period</th>
				<th>Remarks</th>
			</tr>
		</thead>
		<tbody>
			<%
				for (User u : users) {
					if (u.getUserId().equals(user.getUserId())) {
						continue;
					}
			%>
				<tr>
					<td width="19"><input type="checkbox" name="userIds" value="<%= u.getUserId()%>" /></td>
					<td><%= u.getUserId()%></td>
					<td><%= u.getName()%></td>
					<td><%= u.getEmail()%></td>
					<td><%= u.isActive() ? "ACTIVE" : "INACTIVE" %></td>
				</tr>
			<%
				}
			%>
		</tbody>
	</table>
	</form>
</div>
</body>
</html>