<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!-- The HTML 4.01 Transitional DOCTYPE declaration-->
<!-- above set at the top of the file will set     -->
<!-- the browser's rendering engine into           -->
<!-- "Quirks Mode". Replacing this declaration     -->
<!-- with a "Standards Mode" doctype is supported, -->
<!-- but may lead to some differences in layout.   -->

<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>Shopping App</title>
    <script type="text/javascript" src="js/shoppingapp.js"></script>
    <script type="text/javascript" src="css/shoppingapp.css"></script>
  </head>

  <body onload="startUp()">
	<div id="loginDiv">
	    <h1>Shopping App</h1>
	    <span id="userName">Username</span> <input type="text" name="userName" id="userName" /> <br/>
		<span id="password">Password</span> <input type="password" name="password" id="password" /> <br/>
		<a href="createUser.jsp">Create new account</a>
		<button id="login" name="login" onclick="onLoginClick()">Login</button>
	</div>
  </body>
</html>
