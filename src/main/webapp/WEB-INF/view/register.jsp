
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register</title>
<link rel="stylesheet" href="/css/register.css">

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
				<a href ="/conversation"><button type="button" name="Hover">Conversation</button></a>
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
 

<div id="container">
		<h1 id ="formTitle">Register</h1>

		
		

		<form action="/register" method="POST">
		<div class = "row">
			<div class = "col-label"><label for="username">Username: </label> </div>
			<div class = "col-input"><input type="text" name="username" id="username" required> </div>
		</div>
		<div class = "row">
			<div class = "col-label"><label for="password">Password:</label> </div>
			<div class = "col-input"><input type="password" name="password" id="password"required>
			<br><br>
			<input type="checkbox" onclick="myFunction()">Show Password
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
			<div class = "col-label"><label for="question1">Security Question 1:</label> </div>
				<div class = "col-input">
					<select id="question1" name="question1">
						<option selected disabled>Choose a security question</option>
	         		 	<option value="2kiss">What was the name of the boy/girl you had your second kiss with?</option>
	         		 	<option value="3teacher">What was the last name of your third grade teacher?</option>
	          			<option value="ny2000">Where were you New Year's 2000?</option>
	          			<option value ="2pet">What was the name of your second dog/cat/goldfish/etc?</option>
	          			<option value ="1kiss">Where were you when you had your first kiss?</option>
	          			<option value = "villian">Who is your least favorite villian in movie/book/story/life/etc.?</option>
	        		</select>
	        	</div>
			</div>
		<div class = "row">
			<div class = "col-label"><label for="answer1">Answer: </label> </div>
			<div class = "col-input"><input type="text" name="answer1" id="answer1" required> </div>
		</div>
		<div class = "row">
			<div class = "col-label"><label for="question2">Security Question 2:</label> </div>
				<div class = "col-input">
					<select id="question2" name="question2">
						<option selected disabled>Choose a security question</option>
	         		 	<option value="2kiss">What was the name of the boy/girl you had your second kiss with?</option>
	         		 	<option value="3teacher">What was the last name of your third grade teacher?</option>
	          			<option value="ny2000">Where were you New Year's 2000?</option>
	          			<option value ="2pet">What was the name of your second dog/cat/goldfish/etc?</option>
	          			<option value ="1kiss">Where were you when you had your first kiss?</option>
	          			<option value = "villian">Who is your least favorite villian in movie/book/story/life/etc.?</option>
	        		</select>
	        	</div>
			</div>

		<div class = "row">
			<div class = "col-label"><label for="answer2">Answer: </label> </div>
			<div class = "col-input"><input type="text" name="answer2" id="answer2" required> </div>
		</div>
		<div class = "row">
			<button type="submit" id="submit">Submit</button>
		</div>
		</form>

		</div>

<script>



var myInput = document.getElementById("password");
var letter = document.getElementById("letter");
var capital = document.getElementById("capital");
var number = document.getElementById("number");
var length = document.getElementById("length");

var question1 = document.getElementById("question1");
var question2 = document.getElementById("question2");
var previousSelectIndex =0;//keep track of the previous selection
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

//disable the user selection so that user cannot choose 1 question for both security questions
question1.onchange = function()
{
	var optionsForQuestion2 = question2.options;
	var selectedIndex=question1.selectedIndex;
    if(previousSelectIndex!=0)
   	{
    question2.options[previousSelectIndex].disabled = false;//enable previous selection in case user change mind
    }
	question2.options[selectedIndex].disabled = true;
    previousSelectIndex = selectedIndex;
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

function myFunction() {
    var x = document.getElementById("password");
    if (x.type === "password") {
        x.type = "text";
    } else {
        x.type = "password";
    }
}

</script>	
	
</body>
</html>
