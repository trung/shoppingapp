<%@page import="org.telokers.model.PaymentTransaction"%>
<%@page import="org.telokers.model.ShoppingCart"%>
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
	Product product = (Product) request.getAttribute(MiscConstants.KEY_MY_EDIT_PRODUCT);
	Boolean isEditO = (Boolean) request.getAttribute(MiscConstants.IS_EDIT);
	boolean isEdit = isEditO == null ? false : isEditO.booleanValue();
	String productId = product == null ? "" : product.getProductId();
	List<Product> myProducts = (List<Product>) request.getAttribute(MiscConstants.KEY_MY_PRODUCTS);
	List<PaymentTransaction> myTransactions = (List<PaymentTransaction>) request.getAttribute(MiscConstants.KEY_MY_TRANSACTIONS);
	if (myProducts == null) {
		myProducts = new ArrayList<Product>();
	}
	String errorMsg = RequestUtils.getAttribute(request, MiscConstants.ERROR_MESSAGE);
	if (errorMsg == null || errorMsg.length() == 0) {
		errorMsg = RequestUtils.getParameter(request, MiscConstants.ERROR_MESSAGE);
	}
	String infoMsg = RequestUtils.getParameter(request, MiscConstants.INFO_MESSAGE);
%>
<script type="text/javascript">
	function onCreateClick() {
		$("action").value = "create";
		$("myProductForm").submit();
	}

	function onSaveClick() {
		$("action").value = "save";
		$("myProductForm").action = "<%= BlobstoreServiceFactory.getBlobstoreService().createUploadUrl("/secured/home")%>";
		$("myProductForm").enctype = "multipart/form-data";
		$("myProductForm").submit();
	}

	function onCancelClick() {
		$("action").value = "cancel";
		$("myProductForm").submit();
	}

	function onEditClick(pId) {
		$("productId").value = pId;
		$("action").value = "edit";
		$("myProductForm").submit();
	}

	function onDeleteClick(pId) {
		var r = confirm("Do you want to delete the selected product?");
		if (r ==  true) {
			$("productId").value = pId;
			$("action").value = "delete";
			$("myProductForm").submit();
		}
	}

	function onSearchClick() {
		$("q").value=$("search").value;
		$("searchForm").submit();
	}

	function startUp() {
	}
</script>
</head>
<body onload="startUp()">
<div id="menuDiv" style="height: 20px">
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
	<form id="myProductForm" action="/secured/home" method="POST">
	<input type="hidden" id="csrfToken" name="csrfToken" value="<%= user.getCSRFToken()%>" />
	<input type="hidden" id="productId" name="productId" value="<%= productId%>"/>
	<input type="hidden" id="action" name="action" value=""/>
	<div class="<%= (errorMsg.length() > 0 ? "errorMsg" : "") %>"><%= errorMsg%></div>
	<div class="<%= (infoMsg.length() > 0 ? "infoMsg" : "") %>"><%= infoMsg%></div>

	<% if (product != null) { %>
		<h3><%= (isEdit ? "Edit" : "Create") %> Product</h3>
		<table border="0">
			<tbody>
				<tr>
					<td class="label">Picture (PNG only)</td>
					<td><% if (product.hasPicture()) { %><img alt="picture" height="150px" src="<%= product.getPictureUrl()%>"/> <% } %>
					<input type="file" id="picture" name="picture" style="border: none" onchange="return checkFileExtension(this);" /></td>
				</tr>
				<tr>
					<td class="label">Product Name</td>
					<td><input type="text" id="name" name="name" class="value" value="<%= HTMLEncode.encode(product.getProductName())%>"/></td>
				</tr>
				<tr>
					<td class="label">Category</td>
					<td><input type="text" id="category" name="category" class="value" value="<%= HTMLEncode.encode(product.getCategory())%>"/></td>
				</tr>
				<tr>
					<td class="label">Price</td>
					<td><input type="text" id="price" name="price" class="value" value="<%= product.getPriceString()%>"/></td>
				</tr>
				<tr>
					<td class="label">Comment</td>
					<td><input type="text" id="comment" name="comment" class="value" value="<%= HTMLEncode.encode(product.getComment())%>"/></td>
				</tr>
			</tbody>
		</table>
		<input type="button" id="save" name="save" value="Save" class="button" onclick="onSaveClick()"/>
		<input type="button" id="cancel" name="cancel" value="Cancel" class="button" onclick="onCancelClick()" />

	<% } else { %>
	<h3>Search product</h3>
	<a href="/secured/search">Browse all</a><p/>
	<table border="0" style="margin: 0; padding: 0; border-collapse: collapse;">
						<tr><td>Search</td>
							<td><input type="text" id="search" name="search" class="value"/></td>
							<td><input type="button" id="searchBtn" name="searchBtn" value="Go" onclick="onSearchClick()"/></td>
						</tr>
					</table>
	<h3>My product listing</h3>
	<input type="button" id="create" name="create" value="Create product listing" onclick="onCreateClick()" />
	<table id="myProductsTable" border="0" class="container">
		<thead>
			<tr>
				<th></th>
				<th>Picture</th>
				<th>Name</th>
				<th>Category</th>
				<th>Price</th>
				<th>Comment</th>
			</tr>
		</thead>
		<tbody>
			<%
				for (Product p : myProducts) {
			%>
				<tr>
					<td width="100"><table border="0" style="margin: 0; padding: 0; border-collapse: collapse;">
						<tr><td><a href="javascript:void()" onclick="onEditClick('<%= p.getProductId()%>')">Edit</a></td>
							<td><a href="javascript:void()" onclick="onDeleteClick('<%= p.getProductId()%>')">Delete</a></td>
						</tr>
					</table></td>
					<td width="110"><% if (p.hasPicture()) { %><img alt="picture" width="100px" src="<%= p.getPictureUrl()%>"/> <% } %></td>
					<td><%= HTMLEncode.encode(p.getProductName())%></td>
					<td><%= HTMLEncode.encode(p.getCategory())%></td>
					<td width="100"><%= p.getPriceString()%></td>
					<td><%= HTMLEncode.encode(p.getComment())%></td>
				</tr>
			<%
				}
			%>
		</tbody>
	</table>
	<h3>My transactions</h3>
	<table id="myTransactionTable" border="0" class="container">
		<thead>
			<tr>
				<th>Ref Id</th>
				<th>Status</th>
				<th>Transaction Date</th>
				<th>Amount</th>
			</tr>
		</thead>
		<tbody>
			<%
				for (PaymentTransaction p : myTransactions) {
			%>
				<tr>
					<td><%= HTMLEncode.encode(p.getRefId())%></td>
					<td><%= HTMLEncode.encode(p.getStatus())%></td>
					<td><%= p.getTimestampString()%></td>
					<td><%= p.getAmountString()%></td>
				</tr>
			<%
				}
			%>
		</tbody>
	</table>

	<% } %>
	</form>
	<form id="searchForm" action="/secured/search" method="POST">
		<input type="hidden" id="q" name="q" />
	</form>
</div>
</body>
</html>