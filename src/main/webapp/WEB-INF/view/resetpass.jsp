<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Reset Password</title>

<link rel="stylesheet" href="/css/resetpass.css">
</head>
<body>

<div class="nav">
<% if(request.getSession().getAttribute("user") != null){ %>
	
		<div class="button-container-1">
 			<span class="mas"><%= request.getSession().getAttribute("user") %></span>
			<a ><button type="button" name="Hover"><%= request.getSession().getAttribute("user") %></button></a>
		</div>
   
		<div class="button-container-1">
 			<span class="mas">Try Login</span>
			<a  href="/login"><button type="button" name="Hover">Try Login</button></a>
		</div>
		
		<div class="button-container-1">
 			<span class="mas">Switch User</span>
			<a  href="/logout"><button type="button" name="Hover">Switch User</button></a>
		</div>
		
<% } else{ %>
	
	<div class="button-container-1">
	 <span class="mas">Home</span>
		<a id="navTitle" href="/"><button type="button" name="Hover">Home</button></a>
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

		<form action="/resetpass" method="POST">
		<div class = "row">
			<div class = "col-label"><label for="password">New Password:</label> </div>
			<div class = "col-input"><input type="password" name="password" id="password"required>
				<div id="message">
					<p>Password requirement:</p>
	  				<p id="letter" class="invalid">A <b>lowercase</b> letter</p>
	  				<p id="capital" class="invalid">A <b>capital (uppercase)</b> letter</p>
	  				<p id="number" class="invalid">A <b>number</b></p>
	  				<p id="length" class="invalid">Minimum <b>8 characters</b></p>
			</div>

			</div>

		</div>

  		<div class = "row">
			<div class = "col-label"><label for="password">Confirm New Password:</label> </div>
			<div class = "col-input"><input type="password" name="confirm_password" id="confirm_password"required><img id="symbol" src="<%=request.getContextPath()%>/images/tickmark.png" alt="Tick mark" width="30" height="20">
			</div>

			</div>

		<div class = "row">
			<button type="submit" id = "submit" disabled>Submit</button>
		</div>
		</form>

		</div>

		<script>

	var myInput = document.getElementById("password");
	var letter = document.getElementById("letter");
	var capital = document.getElementById("capital");
	var number = document.getElementById("number");
	var length = document.getElementById("length");
	var confirm = document.getElementById("confirm_password");

	confirm.onkeyup = function() {
		//Check if the confirm password match with the first one
		if(confirm.value.localeCompare(myInput.value)==0)
			{
				document.getElementById("symbol").src = "<%=request.getContextPath()%>/images/tickmark.png";
				document.getElementById("submit").disabled= false;

			}
		else
			{
				document.getElementById("symbol").src = "<%=request.getContextPath()%>/images/xicon.png";
				document.getElementById("submit").disabled= true;

			}

	}


	// When the user clicks on the password field, show the message box
	myInput.onfocus = function() {
    document.getElementById("message").style.display = "block";
	}

	// When the user clicks outside of the password field, hide the message box
	myInput.onblur = function() {
    document.getElementById("message").style.display = "none";
	}

	// When the user starts to type something inside the password field
	myInput.onkeyup = function() {
 	 // Validate lowercase letters
  	var lowerCaseLetters = /[a-z]/g;
  	if(myInput.value.match(lowerCaseLetters)) {
    letter.classList.remove("invalid");
    letter.classList.add("valid");
  	} else {
    letter.classList.remove("valid");
    letter.classList.add("invalid");
  	}

  	// Validate capital letters
  	var upperCaseLetters = /[A-Z]/g;
  	if(myInput.value.match(upperCaseLetters)) {
    capital.classList.remove("invalid");
    capital.classList.add("valid");
 	 } else {
    capital.classList.remove("valid");
    capital.classList.add("invalid");
  	}

  	// Validate numbers
  	var numbers = /[0-9]/g;
  	if(myInput.value.match(numbers)) {
    number.classList.remove("invalid");
    number.classList.add("valid");
  	} else {
    number.classList.remove("valid");
    number.classList.add("invalid");
  	}

  // Validate length
  if(myInput.value.length >= 8) {
    length.classList.remove("invalid");
    length.classList.add("valid");
  } else {
    length.classList.remove("valid");
    length.classList.add("invalid");
  }
}
	</script>
</body>
</html>
