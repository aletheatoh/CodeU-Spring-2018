package codeu.controller;

import codeu.model.data.User;
import codeu.model.store.basic.EmailValidate;
import codeu.model.store.basic.UserStore;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.UUID;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class is responsible for user registration.
 */
public class RegisterServlet extends HttpServlet {

	/**
	 * Store class that gives access to Users.
	 */
	public static UserStore userStore;
	public static HashMap<String,Integer> securityQuestionHash = new HashMap<>();
	@Override
	public void doGet (HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
				request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
		//String question1 = request.getParameter("question1");
		//String answer1 =  request.getParameter("answer1");
		//String question2 =  request.getParameter("question2");
		//String answer2 =  request.getParameter("answer2");
		
		

		if (!username.matches("[\\w*\\s]*")) {
			request.setAttribute("error", "Please enter only letters, numbers, and spaces.");
			request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
			return;
		}

		if (userStore.isUserRegistered(username)) {
			request.setAttribute("error", "That username is already taken.");
			request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
			return;
		}

		//if (!answer1.matches("[\\w*\\s]*")) {
          //  request.setAttribute("error", "Please enter only letters, numbers, and spaces.");
           // request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
           // return;
        //}
		
		//if (!answer2.matches("[\\w*\\s]*")) {
          //  request.setAttribute("error", "Please enter only letters, numbers, and spaces.");
            //request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
            //return;
        //}
        
		
		User user = new User(UUID.randomUUID(), username, passwordHash, Instant.now());
		
		//I am still working on email validation so this part is incomplete
		//try {
          //  EmailValidate.sendEmailRegistrationLink(user.getName(), user.getEmail(), null);
        //}
        //catch (MessagingException e) {
            // TODO Auto-generated catch block
          //  e.printStackTrace();
        //}
		userStore.addUser(user);
		response.sendRedirect("/login");
	}

	/**
     * Set up state for handling registration-related requests. This method is only called when
	 * running in a server, not when running in a test.
	 */
	@Override
	public void init() throws ServletException {
		super.init();
		setUserStore(UserStore.getInstance());
	}

	/**
	 * Sets the UserStore used by the servlet. THis function provides a common setup method
	 * for use by the test framework or the servlet's init() function.
	 */
	 void setUserStore(UserStore userStore) {
		 this.userStore = userStore;
	 }
}
