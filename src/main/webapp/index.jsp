<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="/css/test2.css">
</head>
<body>


<div class='section section--stars'>
  <div class='btn btn--stars' id = "start">
   <p id ="message">Hover me!</p>
    <div class='stars'>
      <i class='fa fa-star star-one'></i>
      <i class='fa fa-star star-two'></i>
      <i class='fa fa-star star-three'></i>
    </div>
  </div>
</div>

<div class="nav">
<div class="button-container-3">
 <span class="mas">Home</span>
	<a id="navTitle" href="/"><button type="button" name="Hover">Home</button></a>
</div>

<% if(request.getSession().getAttribute("user") != null){ %>
		<div class="button-container-3">
 			<span class="mas"><%= request.getSession().getAttribute("user") %></span>
			<a  href="/user"><button type="button" name="Hover"><%= request.getSession().getAttribute("user") %></button></a>
		</div>
   
		<div class="button-container-3">
 			<span class="mas">Log Out</span>
			<a  href="/logout"><button type="button" name="Hover">Log Out</button></a>
		</div>
		
		<div class="button-container-3">
				 <span class="mas">Conversation</span>
				<a href ="/conversation"><button type="button" name="Hover">Conversation</button></a>
		</div>
		
<% } else{ %>
		<div class="button-container-3">
 			<span class="mas">Register</span>
			<a href="/register"><button type="button" name="Hover">Register</button></a>
		</div>
		
		<div class="button-container-3">
 			<span class="mas">Login</span>
			<a  href="/login"><button type="button" name="Hover">Login</button></a>
		</div>
		
		
	
	 <% } %>



<div class="button-container-3">
 <span class="mas">About</span>
	<a href="/about.jsp"><button type="button" name="Hover">About</button></a>
</div>

</div>
 
 <script type="text/javascript">
 
 document.getElementById('start').onmouseover = function() {
	  document.getElementById("message").innerHTML = "<br>1. Register and login to start using the web chat " +"<br><br>"+" 2. Go to conversation to send messages" +"<br><br>"+" 3. Go to about to know more about the project and our awesome team!";
  document.getElementById("message").style.fontSize = "10px";
	}

	document.getElementById('start').onmouseout = function() {
		document.getElementById("message").innerHTML = "Hover me!";
   document.getElementById("message").style.fontSize = "15px";
	}
	
	
 </script>
</body>
</html>