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
  <link href="https://fonts.googleapis.com/css?family=Delius+Swash+Caps" rel="stylesheet">
  <script src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.3.1/semantic.min.js"></script>
  <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.3.1/semantic.min.css">
  <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
</head>
<body>

  <nav>
    <a id="navTitle" href="/">CodeU Chat App</a>
    <a href="/conversations">Conversations</a>
    <% if(request.getSession().getAttribute("user") != null){ %>
      <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
      <a href="/user">Profile</a>
      <a href="/logout">Log Out</a>
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
      <h1 id="profile-page-title"><span id="username-title"><%= request.getSession().getAttribute("user")%>'s</span> Profile Page</h1>

      <hr/>

    <div class="imagearea"></div>
    <div id="edit-profile-container">
      <h4 >Edit your Profile Details (only you can see this)</h4>
      <form action="/user" method="POST">
          <div class="form-group">
          <label>Change your profile picture</label><input class="profilePic" type="file" name="profilePic" accept="image/*">
          <br/>
          <label>Change your About Me</label>
          <br/>
          <textarea type="text" name="aboutme"><%= request.getAttribute("aboutme")%></textarea>
        </div>

        <button type="submit">Submit</button>
      </form>
    </div>
      <hr/>

      <div id="location">
        <b>Your Current Location</b>:
      </div>
      <button id="location-button">Check In</button>
      <div id="output"></div>

      <script src="http://maps.google.com/maps/api/js?sensor=false"></script>
      <p id='latitudeAndLongitude'></p>
      <p id='address'></p>

      <h3><%= request.getSession().getAttribute("user")%>'s Sent Messages</h3>

    <% } else{ %>
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
    <% if(message.getAuthorId() === request.getSession().getAttribute("user_id")){ %>
      <li><b><%= message.getCreationTime()%>:</b> <%= message.getContent()%></li>
    <% } %>

      </ul>
    <%
    }
    }
    %>
    <hr/>
  </div>
<script>

  var img = new Image();
  img.src = localStorage.theImage;
  img.id = "profile-pic-profile";

  $('.imagearea').html(img);

  $("body").on("change",".profilePic",function(){
      //Equivalent of getElementById
      var fileInput = $(this)[0];//returns a HTML DOM object by putting the [0] since it's really an associative array.
      var file = fileInput.files[0]; //there is only '1' file since they are not multiple type.

      var reader = new FileReader();
      reader.onload = function(e) {
           // Create a new image.
           var img = new Image();

           img.src = reader.result;
           img.id = "profile-pic-profile";
           localStorage.theImage = reader.result; //stores the image to localStorage
           $(".imagearea").html(img);
       }

       reader.readAsDataURL(file);//attempts to read the file in question.
    });


$('#location-button').click(function() {
  navigator.geolocation.getCurrentPosition(function(position){
    console.log('works!');
    console.log(position);

    $.get("https://maps.googleapis.com/maps/api/geocode/json?latlng=" + "1.3193873999999999" + "," + "103.9528409" + "&key=AIzaSyCYzIzHZUMxbmbGWmWNvF6C0Ojzi3XTN1E", function(data) {
      console.log(data["results"][0]["formatted_address"]);
      var address = data["results"][0]["formatted_address"];
      $('#location').empty();
      $('#location').append(`<b>Your Current Location</b>: ${address}`);
    })

  });
});

</script>

</body>
</html>
