<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Forgot Password</title>
<link rel="stylesheet" href="/css/forgotpass.css">

</head>
<body>
  <div class="nav">
<% if(request.getSession().getAttribute("user") != null){ %>
	
		<div class="button-container-1">
 			<span class="mas"><%= request.getSession().getAttribute("user") %></span>
			<a ><button type="button" name="Hover"><%= request.getSession().getAttribute("user") %></button></a>
		</div>
   
		<div class="button-container-1">
 			<span class="mas">Switch User</span>
			<a  href="/logout"><button type="button" name="Hover">Switch User</button></a>
		</div>
		
		<div class="button-container-1">
 			<span class="mas">Try Login</span>
			<a  href="/login"><button type="button" name="Hover">Try Login</button></a>
		</div>
<% } else{ %>
		<div class="button-container-1">
		 <span class="mas">Home</span>
			<a href="/about.jsp"><button type="button" name="Hover">Home</button></a>
		</div>
		<div class="button-container-1">
 			<span class="mas">Register</span>
			<a href="/register"><button type="button" name="Hover">Register</button></a>
		</div>
		
		<div class="button-container-1">
 			<span class="mas">Login</span>
			<a  href="/login"><button type="button" name="Hover">Login</button></a>
		</div>
		<div class="button-container-1">
		 <span class="mas">About</span>
			<a href="/about.jsp"><button type="button" name="Hover">About</button></a>
		</div>
		
		
	
	 <% } %>

		

</div>

	<div id="container">
		<h1 id ="formTitle">Reset Password</h1>

		<%
		    if (request.getAttribute("error") != null) {
		%>
		<h2 style="color: red"><%=request.getAttribute("error")%></h2>
		<%
		    }
		%>

		<form action="/forgotpass" method="POST">
		<%
			if (request.getAttribute("validUser")==null) {
		%>
		<div class = "row">
			<div class = "col-label"><label for="username">Username: </label> </div>
			<div class = "col-input"><input type="text" name="username" id="username" required> </div>
		</div>

		<div class = "row">
			<button type="submit" name="button" value = "checkUsername">Check Username</button>
		</div>
		  		<%
			}
			else {
		  		%>
		<div class = "row">
			<div class = "col-label"><label for="question1">Security Question 1:</label> </div>
				<div class = "col-input">
					<h4><%=request.getAttribute("question1")%></h4>
	        	</div>
			</div>
		<div class = "row">
			<div class = "col-label"><label for="answer1">Answer: </label> </div>
			<div class = "col-input"><input type="text" name="answer1" id="answer1"> </div>
		</div>
		<div class = "row">
			<div class = "col-label"><label for="question1">Security Question 1:</label> </div>
				<div class = "col-input">
					<h4><%=request.getAttribute("question2")%></h4>
	        	</div>
			</div>
		<div class = "row">
			<div class = "col-label"><label for="answer2">Answer: </label> </div>
			<div class = "col-input"><input type="text" name="answer2" id="answer2" > </div>
		</div>
		<div class = "row">
			<button type="submit" name="button" value="answers">Submit</button>
		</div>
		<%
			}
			%>
		</form>

		</div>

</body>
</html>
