<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Log Out</title>
<link rel="stylesheet" href="/css/logout.css">
</head>
<body>
   <div class="nav">
<div class="button-container-1">
 <span class="mas">Home</span>
	<a id="navTitle" href="/"><button type="button" name="Hover">Home</button></a>
</div>
	
		<div class="button-container-1">
 			<span class="mas"><%= request.getSession().getAttribute("user") %></span>
			<a  href="/user"><button type="button" name="Hover"><%= request.getSession().getAttribute("user") %></button></a>
		</div>
   
		<div class="button-container-1">
				 <span class="mas">Conversation</span>
				<a href ="/conversations"><button type="button" name="Hover">Conversation</button></a>
		</div>
	 <div class="button-container-1">
 <span class="mas">About</span>
	<a href="/about.jsp"><button type="button" name="Hover">About</button></a>
</div>

</div>

    <% if(request.getAttribute("error") != null){ %>
        <h2 style="color:red"><%= request.getAttribute("error") %></h2>
    <% } %>

    <div id ="form">
    <form action="/logout" method="POST">    
      

		<div class="header label">Are you sure?</div>
  		<div class="label-2">
		      <button class="btn btn-two yes" type="submit" name ="button" value ="yes">YES</button>
		    
		    
		      <button class="btn btn-two no"type="submit" name = "button" value = "no">NO</button>
	  </div>

  
    </form>
  </div>
</body>
</html>
