<%@page import="org.telokers.service.utils.HTMLEncode"%>
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
	User editedUser = (User) request.getAttribute(MiscConstants.KEY_EDIT_USER);
	String editedUserId = editedUser != null ? editedUser.getUserId() : "";
	List<User> users = (List<User>) request.getAttribute(MiscConstants.KEY_USERS);
	String errorMsg = RequestUtils.getAttribute(request, MiscConstants.ERROR_MESSAGE);
%>
<script type="text/javascript">
	function onEditClick(userId) {
		$("userId").value = userId;
		$("userForm").submit();
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
	<form id="userForm" action="/secured/admin" method="POST">
	<input type="hidden" id="csrfToken" name="csrfToken" value="<%= user.getCSRFToken()%>" />
	<input type="hidden" id="userId" name="userId" value="<%= editedUserId%>"/>
	<div class="<%= (errorMsg.length() > 0 ? "errorMsg" : "") %>"><%= errorMsg%></div>
	<% if (editedUser != null)  { %>
	<h3>Edit User</h3>
		<table border="0">
			<tbody>
				<tr>
					<td class="label">User Id</td>
					<td><%= editedUser.getUserId() %></td>
				</tr>
				<tr>
					<td class="label">Account Type</td>
					<td><select id="accountType" name="accountType">
						<% for (String r : MiscConstants.ROLES) { %>
						<option value="<%= r%>" <%= (r.equals(editedUser.getRole()) ? "selected='selected'" : "") %>><%= r%></option>
						<% } %>
					</select></td>
				</tr>
				<tr>
					<td class="label">Status</td>
					<td><select id="status" name="status">
						<% for (String r : MiscConstants.STATUSES) { %>
						<option value="<%= r%>" <%= (r.equals(editedUser.getStatus()) ? "selected='selected'" : "") %>><%= r%></option>
						<% } %>
					</select></td>
				</tr>
				<tr>
					<td class="label">Suspension Period</td>
					<td><table border="0" style="margin: 0; padding: 0; border-collapse: collapse;">
						<tr><td><input type="text" id="suspensionStart" name="suspensionStart" value="<%= editedUser.getSuspensionStartString() %>" /></td>
							<td> to </td>
							<td><input type="text" id="suspensionEnd" name="suspensionEnd" value="<%= editedUser.getSuspensionEndString() %>"/></td>
							<td>(DD/MM/YYYY)</td>
						</tr>
					</table></td>
				</tr>
				<tr>
					<td class="label">Remarks</td>
					<td><input type="text" id="remarks" name="remarks" class="value"/></td>
				</tr>
			</tbody>
		</table>
		<input type="submit" id="action" name="action" value="Save" class="button" />
		<input type="submit" id="cancel" name="cancel" value="Cancel" class="button" />
	<% } else { %>
	<input type="hidden" id="action" name="action" value="edit"/>
	<h3>Users</h3>
	<table id="userListTable" border="0" class="container">
		<thead>
			<tr>
				<th></th>
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
					<td width="50"><a href="javascript:void()" onclick="onEditClick('<%= u.getUserId()%>')">Edit</a></td>
					<td><%= u.getUserId()%></td>
					<td><%= u.getRole()%></td>
					<td><%= u.getStatus()%></td>
					<td><%= u.getLastModifiedOfStatusString()%></td>
					<td><%= u.getSuspensionStartString() + " - " + u.getSuspensionEndString()%></td>
					<td><%= HTMLEncode.encode(u.getRemarks())%></td>
				</tr>
			<%
				}
			%>
		</tbody>
	</table>
	<% } %>
	</form>
</div>
</body>
</html>