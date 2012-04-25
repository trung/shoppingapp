<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>Shopping App</title>
    <link href="css/shoppingapp.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="js/shoppingapp.js"></script>
  </head>
  <script type="text/javascript">
  	function startUp() {
		$("userId").focus();
  	}

  	function onLoginClick() {

  	}

  </script>
  <body onload="startUp()">
	<div id="loginDiv">
		<center>
		<form action="/loginServlet" method="POST">
		    <h1>Shopping App</h1>
		    <div id="errorMsg"></div>
		    <table border="0">
		    	<tbody>
		    		<tr>
		    			<td class="label">User Id</td>
		    			<td><input type="text" name="userId" id="userId" class="value" /></td>
		    		</tr>
		    		<tr>
		    			<td class="label">Password</td>
		    			<td><input type="password" name="password" id="password" class="value" /></td>
		    		</tr>
		    		<tr>
		    			<td colspan="2">
		    				<a href="createUser.jsp" style="float:left">Create new account</a>
		    				<input type="submit" id="login" name="login" style="float:right" class="button" />
		    			</td>
		    		</tr>
		    	</tbody>
		    </table>
		</form>
		</center>
	</div>
  </body>
</html>
