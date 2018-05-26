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
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
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
			<ahref="/about.jsp"><button type="button" name="Hover">Home</button></a>
		</div>
	</div>

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
            <h4 class="profile-headings">About Me</h4>
            <p><%= request.getAttribute("aboutme")%></p>
            <div id="location">
              <b>Your Current Location</b>:
            </div>
            <img id="location-button" src="../images/gps.svg" style="width: 80px;">
            <div id="output"></div>

            <!-- Trigger/Open The Modal -->
            <button id="myBtn">Edit Profile</button>

          </div>
            <hr/>

            <!-- The Modal -->
            <div id="myModal" class="modal" style="display:none;">

              <!-- Modal content -->
              <div class="modal-content">
                <div class="modal-header">
                  <span class="close">&times;</span>
                  <h4 class="profile-headings" style="margin-bottom:5px;margin-top:5px">Don't worry, only you can see this.</h4>
                </div>
                <div class="modal-body">
                  <form action="/user" method="POST" style="margin-top:10px;margin-bottom:10px;">
                    <div class="form-group">
                      <label>Change your Profile Picture</label>
                      <input style="margin-bottom:10px" class="profilePic" type="file" name="profilePic" accept="image/*">
                        <br/>
                        <label style="margin-top:10px">Change your Bio</label>
                        <br/>
                        <textarea type="text" name="aboutme"><%= request.getAttribute("aboutme")%></textarea>
                      </div>

                      <button type="submit">Update</button>
                    </form>
                </div>
                <%-- <div class="modal-footer">
                  <h3>Modal Footer</h3>
                </div> --%>
              </div>

            </div>


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
                for (Message message : messages){
                  %>
                  <% if(message.getAuthorId() == request.getSession().getAttribute("user_id")){ %>
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

          $('#myBtn').click(function() {
            $('#myModal').show();

            // Get the <span> element that closes the modal
            var span = document.getElementsByClassName("close")[0];

            // When the user clicks on <span> (x), close the modal
            span.onclick = function() {
                $('#myModal').css('display', 'none');
            }

            // When the user clicks anywhere outside of the modal, close it
            window.onclick = function(event) {
                if (event.target == $('#myModal')) {
                    $('#myModal').css('display', 'none');
                }
            }
          });


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

          if (localStorage.location != undefined) {
            console.log('not undefined')
            $('#location').empty();
            $('#location').append("<b>Your Current Location</b>: " + `'${localStorage.location}'`);
          }

          $('#location-button').click(function() {
            navigator.geolocation.getCurrentPosition(function(position){
              console.log('works!');
              console.log(position);

              $.get("https://maps.googleapis.com/maps/api/geocode/json?latlng=" + "1.3193873999999999" + "," + "103.9528409" + "&key=AIzaSyCYzIzHZUMxbmbGWmWNvF6C0Ojzi3XTN1E", function(data) {
                console.log(data["results"][0]["formatted_address"]);
                var address = data["results"][0]["formatted_address"];
                localStorage.location = address; //stores the location to localStorage
                $('#location').empty();
                $('#location').append(`<b>Your Current Location</b>: ${localStorage.location}`);
              })

            });
          });

        </script>

      </body>
    </html>
