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

<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.User" %>
<%@ page import="codeu.model.data.Message" %>
<%@ page import="codeu.model.store.basic.UserStore" %>

<%-- <%
List<Message> messages = (List<Message>) request.getAttribute("messages");
%> --%>

<!DOCTYPE html>
<html>
<head>
  <title>Conversations</title>
  <link rel="stylesheet" href="/css/main.css">
  <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
</head>
<body>

  <nav>
    <a id="navTitle" href="/">CodeU Chat App</a>
    <a href="/conversations">Conversations</a>
    <% if(request.getSession().getAttribute("user") != null){ %>
      <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
    <% } else{ %>
      <a href="/login">Login</a>
      <a href="/register">Register</a>
    <% } %>
    <a href="/about.jsp">About</a>
  </nav>

  <div id="container">

    <% if(request.getAttribute("error") != null){ %>
        <h3 style="color:red"><%= request.getAttribute("error") %></h3>
    <% } %>

    <% if(request.getSession().getAttribute("user") != null){ %>

      <%-- put user profile here --%>
      <h1><%= request.getSession().getAttribute("user")%>'s Profile Page</h1>

      <hr/>

      <h3>About <%= request.getSession().getAttribute("user")%></h3>
      <p></p>

      <h4>Edit your Profile Details (only you can see this)</h4>
      <form action="/user" method="POST">
          <div class="form-group">
          <label>Change your profile picture</label><input type="file" name="profilePic" accept="image/*">
          <br/>
          <p><%= request.getAttribute("production")%></p>
          <label>Change your About Me</label>
          <br/>
          <textarea type="text" name="aboutme"><%= request.getAttribute("aboutme")%></textarea>
        </div>

        <button type="submit">Submit</button>
      </form>
      <hr/>

      <button id="location-button">Check In</button>
      <div id="output"></div>

      <script src="http://maps.google.com/maps/api/js?sensor=false"></script>
      <p id='latitudeAndLongitude'></p>
      <p id='address'></p>

      <h3>All Messages Available:</h3>

    <% } %>

    <% if(request.getSession().getAttribute("user") == null){ %>
      <h1>You're not authorized to view this page</h1>
    <% } %>

    <%
    List<Message> messages =
      (List<Message>) request.getAttribute("messages");
    if(messages == null || messages.isEmpty()){
    %>
      <p>You have no messages.</p>
    <%
    }
    else{
    %>
      <ul class="mdl-list">
    <%
      for(Message message : messages){
    %>
      <li><b><%= message.getCreationTime()%>:</b> <%= message.getContent()%></li>
    <%
      }
    %>
      </ul>
    <%
    }
    %>
    <hr/>
  </div>
<script>

$('#location-button').click(function() {
  navigator.geolocation.getCurrentPosition(function(position){
    console.log('works!');
    console.log(position);
    $.get("https://maps.googleapis.com/maps/api/geocode/json?latlng=" + position.coords.latitude + "," + position.coords.longitude + "&key=AIzaSyCEpO1YXIeCe8igHrFGb1xHTPjRtKSsgzo", function(data) {
      console.log(data["results"][0]["formatted_address"]);
    })
  });
});
</script>

</body>
</html>
