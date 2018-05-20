<%--
  Copyright 2017 Google Inc.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
--%>
<!DOCTYPE html>
<html>
<head>
  <title>Login</title>
  <link rel="stylesheet" href="/css/login.css">
</head>
<body>

  <div class="nav">
<div class="button-container-1">
 <span class="mas">Home</span>
	<a id="navTitle" href="/"><button type="button" name="Hover">Home</button></a>
</div>

<% if(request.getSession().getAttribute("user") != null){ %>
	
		<div class="button-container-1">
 			<span class="mas"><%= request.getSession().getAttribute("user") %></span>
			<a  href="/user"><button type="button" name="Hover"><%= request.getSession().getAttribute("user") %></button></a>
		</div>
   
		<div class="button-container-1">
 			<span class="mas">Log Out</span>
			<a  href="/logout"><button type="button" name="Hover">Log Out</button></a>
		</div>
		
		<div class="button-container-1">
				 <span class="mas">Conversation</span>
				<a href ="/conversations"><button type="button" name="Hover">Conversation</button></a>
		</div>
		
<% } else{ %>
		<div class="button-container-1">
 			<span class="mas">Register</span>
			<a href="/register"><button type="button" name="Hover">Register</button></a>
		</div>
		
		<div class="button-container-1">
 			<span class="mas">Login</span>
			<a  href="/login"><button type="button" name="Hover">Login</button></a>
		</div>
		
		
	
	 <% } %>

<div class="button-container-1">
 <span class="mas">About</span>
	<a href="/about.jsp"><button type="button" name="Hover">About</button></a>
</div>

</div>
	<%
	if("successful".equalsIgnoreCase((String) session.getAttribute("reset"))){
	%>
	<script>alert('Your password have been successfully reset. Please log in again! ');</script>
	<% }
	else if("successful".equalsIgnoreCase((String) session.getAttribute("registered")))
	{
	 %>
	    <script>alert('Registerd Successfully! Please log in to start using the webchat!');</script>
	<%}
	else if(request.getSession().getAttribute("user") != null)
	{
	    request.setAttribute("error","already logged in");
	 %>
	    <script>alert('You are already logged in. Please log out before logging in as other users!');</script>
	<%}
	%>
  <div id="container">
    <h1 id="formTitle">Login</h1>

    <% if(request.getAttribute("error") != null){ %>
        <h2 style="color:red"><%= request.getAttribute("error") %></h2>
    <% } %>

    <form action="/login" method="POST">
      <label for="username">Username: </label>
      <input type="text" name="username" id="username">
      <br></br>
      <label for="password">Password: </label>
      <input type="password" name="password" id="password">
      <br></br>
      <a href = "/forgotpass" id="forgotpass">Forgot Password?</a>
      <br></br>
      <button type="submit" id="submit">Login</button>
    </form>
  </div>
</body>
</html>
