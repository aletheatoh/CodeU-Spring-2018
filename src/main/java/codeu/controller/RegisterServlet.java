package codeu.controller;

<<<<<<< HEAD
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class is responsible for user registration
 */
public class RegisterServlet extends HttpServlet {

	/**
	 * Store class that gives access to Users.
	 */
	private UserStore userStore;

	@Override
	public void doGet (HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
				request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		response.getWriter().println("<p>Username: " + username + "</p>");
		response.getWriter().println("<p>Password: " + password + "</p>");
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
