package codeu.controller;

import codeu.model.data.User;
import codeu.model.store.basic.EmailValidate;
import codeu.model.store.basic.UserStore;
import codeu.model.store.persistence.PersistentDataStoreException;
import org.mindrot.jbcrypt.BCrypt;
import org.mortbay.log.Log;
import com.google.appengine.api.datastore.Key;
import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class is responsible for user registration.
 */
public class ResetPassServlet extends HttpServlet {

    /**
     * Store class that gives access to Users.
     */
    public static UserStore userStore;
    private static final Logger LOG = Logger.getLogger("ResetPassListener");

    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
                request.getRequestDispatcher("/WEB-INF/view/resetpass.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        
        String password = request.getParameter("password");
        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
        LOG.info(String.format(">>>> new password: %s", password));
        String username = (String)request.getSession(false).getAttribute("user");
        LOG.info(String.format(">>> username is : %s, which is %s",username, userStore.isUserRegistered(username)));
        User user = userStore.getUser(username);
        LOG.info(String.format(">>> username is from userstore is : %s", user.getName()));
        user.setResetPassTime();
        Key key = userStore.updatePassword(user, passwordHash);
        if(key == userStore.getUserKey(user.getId()))
        {
            LOG.info("Reset successfully!");
        }
        userStore.getUser((String)request.getSession().getAttribute("user")).resetPass(passwordHash);
        request.getSession(false).removeAttribute("user");
        request.getSession(false).setAttribute("reset", "successful");
        response.sendRedirect("/login");
        
    }
    
    
    
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
