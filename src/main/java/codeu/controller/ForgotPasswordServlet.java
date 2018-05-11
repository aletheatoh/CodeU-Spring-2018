                                           
// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package codeu.controller;

import codeu.model.data.SecurityQuestion;
import codeu.model.data.User;
import codeu.model.store.basic.UserStore;
import codeu.model.store.persistence.PersistentDataStoreException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForgotPasswordServlet extends HttpServlet {

    public static UserStore userStore;
    public static HashMap<String, String> securityQuestions;
    public User user;
    private static final Logger LOG = Logger.getLogger("ForgotPassListener");


    /**
     * This function fires when a user requests the /forgotpass URL. It simply
     * forwards the request to
     * forgotpass.jsp.
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException,
        ServletException {
        request.getRequestDispatcher("/WEB-INF/view/forgotpass.jsp").forward(
            request, response);
    }


    /**
     * This function fires when a user submits the login form. It gets the
     * username and password from the submitted
     * form data, checks that they're valid, and either adds the user to the
     * session so we know the user is logged in or shows an error to the user.
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException,
        ServletException { 
        
        String action = request.getParameter("button");
        if(action.equals("checkUsername")){
        String username = request.getParameter("username");
        String confirm = request.getParameter("confirm");
            if (userStore.isUserRegistered(username)) {
                this.user = userStore.getUser(username);
                request.getSession().setAttribute("user", username);
                request.setAttribute("user", username);
                request.setAttribute("validUser", true);
               String question1 = this.securityQuestions.get(user.getQuestionAnswer().get(0).getValue());
               String question2 = this.securityQuestions.get(user.getQuestionAnswer().get(1).getValue());
                request.setAttribute("question1", question1);
                request.setAttribute("question2", question2);
                request.getRequestDispatcher("/WEB-INF/view/forgotpass.jsp").forward(
                    request, response);
            }
            else {
                request.setAttribute("error", "That username was not found.");
                request.getRequestDispatcher("/WEB-INF/view/forgotpass.jsp").forward(
                    request, response);
            }
        }
        
        if(action.equals("answers")){
        String answer1 = request.getParameter("answer1");
        String answer2 = request.getParameter("answer2");
        LOG.info(String.format(">>>> answer 1 is %s--", answer1));
        LOG.info(String.format(">>>> answer 2 is %s--", answer2));
        
        
        ArrayList<SecurityQuestion> answers = user.getQuestionAnswer();
        LOG.info(String.format(">>>> comparing to answer 1 is %s--", answers.get(0).getAnswer()));
        LOG.info(String.format(">>>> comparing to answer 2 is %s--", answers.get(1).getAnswer()));
        if ((answers.get(0).checkAnswer(answer1)) & (answers.get(1).checkAnswer(
            answer2))) {
            LOG.info("Got it!");
            request.setAttribute("user", request.getAttribute("user"));
            LOG.info(String.format(">>> username is in forgot request: %s",(String)request.getAttribute("user")));
            LOG.info(String.format(">>> username is forgot session : %s",(String)request.getSession(false).getAttribute("user")));
            request.getRequestDispatcher("/WEB-INF/view/resetpass.jsp").forward(
                request, response);
        }
        else
        {
            request.setAttribute("error", "Wrong answers! Account is now blocked! Please contact your admin to reactivate the account!");
            request.getRequestDispatcher("/WEB-INF/view/forgotpass.jsp").forward(
                request, response);
        }
        }
    }


    /**
     * Set up state for handling registration-related requests. This method is
     * only called when
     * running in a server, not when running in a test.
     */
    @Override
    public void init() throws ServletException {
        super.init();
        setUserStore(UserStore.getInstance());
    }


    /**
     * Sets the UserStore used by the servlet. THis function provides a common
     * setup method
     * for use by the test framework or the servlet's init() function.
     */
    void setUserStore(UserStore userStore) {
        this.userStore = userStore;
            securityQuestions = userStore.getSecurityQuestions();
            LOG.info(String.format(">>>> length of hashmap %d", securityQuestions.size()));
            for(String value : securityQuestions.keySet())
            {
                LOG.info(String.format(">>>> value: %s\n", value));
            }
        
    }

}
