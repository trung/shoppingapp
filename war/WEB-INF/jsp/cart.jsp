<%@page import="org.telokers.service.utils.MiscUtils"%>
<%@page import="org.telokers.model.ShoppingCart"%>
<%@page import="org.telokers.model.Comment"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory"%>
<%@page import="org.telokers.service.utils.HTMLEncode"%>
<%@page import="org.telokers.service.utils.RequestUtils"%>
<%@page import="org.telokers.model.Product"%>
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
	ShoppingCart cart = (ShoppingCart) request.getAttribute(MiscConstants.KEY_CART);
	List<Product> products = (List<Product>) request.getAttribute(MiscConstants.KEY_ALL_PRODUCTS);
	String errorMsg = RequestUtils.getAttribute(request, MiscConstants.ERROR_MESSAGE);
	if (errorMsg == null || errorMsg.length() == 0) {
		errorMsg = RequestUtils.getParameter(request, "errorMsg");
	}
	String infoMsg = RequestUtils.getParameter(request, "infoMsg");
%>
<script type="text/javascript">
	function onDeleteProductOnCartClick(productId) {
		$("productId").value = productId;
		$("deleteFromCartForm").submit();
	}

	function onMakePaymentClick() {
		$("makePaymentForm").submit();
	}

	function startUp() {
	}
</script>
</head>
<body onload="startUp()">
<div id="menuDiv" style="height: 20px">
<div style="float:left"><a href="/secured/home">&laquo; Home</a></div>
<div style="float:right">
Welcome, <%= user.getName() %>!&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href="/secured/cart">My cart (<%= (cart == null ? 0 : cart.countProducts())%>)</a> |
<% if (user.isAdmin()) { %><a href="/secured/admin">Admin</a> |<% } %>
<a href="/secured/editUser">Edit profile</a>
|
<a href="/logout">Log out</a>
</div>
</div>
<div id="productDiv">
	<div class="<%= (errorMsg.length() > 0 ? "errorMsg" : "") %>"><%= errorMsg%></div>
	<div class="<%= (infoMsg.length() > 0 ? "infoMsg" : "") %>"><%= infoMsg%></div>
	<h3>My shopping cart</h3>
	<table id="myProductsTable" border="0" class="container">
		<thead>
			<tr>
				<th></th>
				<th>Picture</th>
				<th>Name</th>
				<th>Category</th>
				<th>Price</th>
			</tr>
		</thead>
		<tbody>
			<%
				double totalPrice = 0;
				for (Product p : products) {
					totalPrice += p.getPrice();
			%>
				<tr>
					<td width="50"><a href="javascript:void()" onclick="onDeleteProductOnCartClick('<%= p.getProductId()%>')">Delete</a></td>
					<td width="110"><% if (p.hasPicture()) { %><img alt="picture" width="100px" src="<%= p.getPictureUrl()%>"/> <% } %></td>
					<td><%= HTMLEncode.encode(p.getProductName())%></td>
					<td><%= HTMLEncode.encode(p.getCategory())%></td>
					<td width="100"><%= p.getPriceString()%></td>
				</tr>
			<%
				}
			%>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td><strong>Total</strong></td>
				<td><%= MiscUtils.formatPrice(totalPrice) %></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td><input type="button" id="payment" name="payment" value="Make Payment" onclick="onMakePaymentClick()" /></td>
			</tr>
		</tbody>
	</table>

	<form id="deleteFromCartForm" action="/secured/deleteFromCart" method="POST">
		<input type="hidden" id="productId" name="productId" />
	</form>

	<form id="makePaymentForm" action="/secured/makePayment" method="POST">
		<input type="hidden" id="csrfToken" name="csrfToken" value="<%= user.getCSRFToken()%>" />
	</form>

</div>
</body>
</html>