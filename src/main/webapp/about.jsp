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
  <title>CodeU Chat App</title>
  <link rel="stylesheet" href="/css/main.css">
</head>
<body>

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
 
  
  <div id="container">
    
	<h1>"Always be yourself, unless you can be Team 9, then be Team 9"</h1>
	<ul>
    <div id="Henry" class="member">	
    <h3>Henry (Advisor)</h1> 	
    <li><img src="<%=request.getContextPath()%>/images/playing-piano-in-a-band.jpg" alt="The Pulpit Rock" width="300" height="200"></li>
    <li></br>  Keeping his day job for now. <i>Carnegie Hall will have to wait.</i></li>
    </div>
    <div id="Liz" class="member">
    <h3>Liz Dao</h3> </br> 		
     <li><img src="<%=request.getContextPath()%>/images/f2c3dc6428c07ff998fc512f94484539.jpg" alt="donut bulldog" width="300" height="200"></li>
    <li>CS sophomore, Vietnamese, runs on donuts, black coffee, and Netflix</li>
     
    </div>
		<li>Sergio</li>
		<li>Alethea</li>
		<li>Erin</li>
</br>
	<h3>As you can see, this web chat is superhero themed</h3>
	<h3>Some of the amazing features we developed:</h3>
	<ul>
		<li>Registration
		<li>Security and Account Recover Option
		<li>Admin and Statistic feature
		<li>Profile (bio, avatar, etc.)	
	</ul>
	<a href="https://docs.google.com/document/d/1iDMfiYjOTJYbCzZS_uglQFQeGxbEsiaT2qB1eQMsFL8/edit?ts=5af5bf4a#">>>>Click here to read the Design Doc of the this web chat!</a>
  </div>
</body>
</html>
