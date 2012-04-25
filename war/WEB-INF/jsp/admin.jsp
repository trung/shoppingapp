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
<title>Home</title>
<%
	User user = (User) request.getAttribute(MiscConstants.KEY_USER);
	List<User> users = (List<User>) request.getAttribute(MiscConstants.KEY_USERS);
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
<a href="/secured/editUser">Edit profile</a>
|
<a href="/logout">Log out</a>
</div>
</div>

<div id="mainDiv">
	<h3>Users</h3>
	<div id="buttons">
		<input type="button" id="approve" name="approve" value="Approve" class="button" />
		<input type="button" id="suspend" name="suspend" value="Suspend" class="button" />
	</div>
	<br/>
	<table border="0" class="container">
		<thead>
			<tr>
				<th><input type="checkbox" id="selectAll" name="selectAll" onclick="onSelectAllClick()"/></th>
				<th>User Id</th>
				<th>Name</th>
				<th>Email</th>
				<th>Status</th>
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
					<td width="19"><input type="checkbox" name="userIds" /></td>
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
</div>
</body>
</html>