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
	Product product = (Product) request.getAttribute(MiscConstants.KEY_MY_EDIT_PRODUCT);
	String errorMsg = RequestUtils.getAttribute(request, MiscConstants.ERROR_MESSAGE);
	if (errorMsg == null || errorMsg.length() == 0) {
		errorMsg = RequestUtils.getParameter(request, "errorMsg");
	}
	String infoMsg = RequestUtils.getParameter(request, "infoMsg");
%>
<script type="text/javascript">
	function onDetailsClick(id) {
		$("q").value=id;
		$("searchForm").submit();
	}

	function onSearchClick() {
		$("q").value=$("search").value;
		$("searchForm").submit();
	}

	function onAddToCartClick(productId) {
		$("productId").value = productId;
		$("addToCartForm").submit();
	}

	function onDeleteClick(productId) {
		$("deleteProductId").value = productId;
		$("deleteForm").submit();
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
	<h3>Search product</h3>
	<a href="/secured/search">Browse all</a><p/>
	<table border="0" style="margin: 0; padding: 0; border-collapse: collapse;">
						<tr><td>Search</td>
							<td><input type="text" id="search" name="search" class="value"/></td>
							<td><input type="button" id="searchBtn" name="searchBtn" value="Go" onclick="onSearchClick()"/></td>
						</tr>
					</table>
	<% if (product != null) { %>
		<h3>Product details</h3>
		<input type="button" id="addToCartBtn" name="addToCartBtn" value="Add to shopping cart" onclick="onAddToCartClick('<%= product.getProductId()%>')" />
		<table border="0">
			<tbody>
				<tr>
					<td class="label">Picture</td>
					<td><% if (product.hasPicture()) { %><img alt="picture" height="150px" src="<%= product.getPictureUrl()%>"/> <% }  else { %>No Picture <% } %></td>
				</tr>
				<tr>
					<td class="label">Product Name</td>
					<td><%= HTMLEncode.encode(product.getProductName())%></td>
				</tr>
				<tr>
					<td class="label">Category</td>
					<td><%= HTMLEncode.encode(product.getCategory())%></td>
				</tr>
				<tr>
					<td class="label">Price</td>
					<td><%= product.getPriceString()%></td>
				</tr>
				<tr>
					<td class="label">Seller</td>
					<td><%= product.getSeller()%></td>
				</tr>
				<tr>
					<td class="label">Seller's Comment</td>
					<td><%= HTMLEncode.encode(product.getComment())%></td>
				</tr>
				<tr>
					<td class="label">Rating</td>
					<td><%= product.getRating()%></td>
				</tr>
			</tbody>
		</table>
		<h3>Comments</h3>
		<% for (Comment c : product.getComments()) { %>
		<table border="0">
			<tbody>
				<tr>
					<td><%= c.getCreatedDateString()%></td>
					<td><%= c.getUserId()%></td>
					<td>rated: <%= c.getRating()%></td>
					<td>, said: <%= HTMLEncode.encode(c.getComment())%></td>
				</tr>
			</tbody>
		</table>
		<% } %>
		<% if (product.isCommentAllowed()) { %>
		<hr/>
		<form action="/secured/addComment" method="POST">
		<input type="hidden" id="csrfToken" name="csrfToken" value="<%= user.getCSRFToken()%>" />
		<input type="hidden" id="productId" name="productId" value="<%= product.getProductId()%>" />
		<table border="0">
			<tbody>
				<tr>
					<td class="label">Comment</td>
					<td><input type="text" id="comment" name="comment" class="value"/></td>
				</tr>
				<tr>
					<td class="label">Rating</td>
					<td><select id="rating" name="rating">
						<option value="0" selected="selected">0</option>
						<option value="1" selected="selected">1</option>
						<option value="2" selected="selected">2</option>
						<option value="3" selected="selected">3</option>
						<option value="4" selected="selected">4</option>
						<option value="5" selected="selected">5</option>
					</select></td>
				</tr>
			</tbody>
		</table>
		<input type="submit" id="submit" name="submit" value="Submit" />
		</form>
		<% } %>
	<% } else if (products == null || products.size() == 0)  {%>
	<div class="infoMsg">No products found</div>
	<% } else { %>
	<table id="myProductsTable" border="0" class="container">
		<thead>
			<tr>
				<th></th>
				<th>Picture</th>
				<th>Name</th>
				<th>Category</th>
				<th>Price</th>
				<th>Seller</th>
				<th>Posting Date</th>
				<th>Buyers' rating</th>
			</tr>
		</thead>
		<tbody>
			<%
				for (Product p : products) {
			%>
				<tr>
					<td width="50"><a href="javascript:void()" onclick="onDetailsClick('<%= p.getProductId()%>')">Details</a>
						<% if (user.isAdmin() || user.getUserId().equals(p.getSeller())) { %>
							<p/><a href="javascript:void()" onclick="onDeleteClick('<%= p.getProductId()%>')">Delete</a>
						<% } %>
					</td>
					<td width="110"><% if (p.hasPicture()) { %><img alt="picture" width="100px" src="<%= p.getPictureUrl()%>"/> <% } %></td>
					<td><%= HTMLEncode.encode(p.getProductName())%></td>
					<td><%= HTMLEncode.encode(p.getCategory())%></td>
					<td width="100"><%= p.getPriceString()%></td>
					<td><%= p.getSeller()%></td>
					<td><%= p.getPostedDateString()%></td>
					<td><%= (p.getRating() == 0 ? "No rating yet" : p.getRating())%></td>
				</tr>
			<%
				}
			%>
		</tbody>
	</table>
	<% } %>
	<form id="searchForm" action="/secured/search" method="POST">
		<input type="hidden" id="q" name="q" />
	</form>

	<form id="addToCartForm" action="/secured/addToCart" method="POST">
		<input type="hidden" id="productId" name="productId" />
	</form>

	<form id="deleteForm" action="/secured/deleteProduct" method="POST">
		<input type="hidden" id="deleteProductId" name="productId" />
		<input type="hidden" id="csrfToken" name="csrfToken" value="<%= user.getCSRFToken()%>" />
	</form>

</div>
</body>
</html>