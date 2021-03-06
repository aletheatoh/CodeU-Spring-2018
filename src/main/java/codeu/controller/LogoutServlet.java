 // Copyright 2017 Google Inc.
    //
    // Licensed under the Apache License, Version 2.0 (the "License");
    // you may not use this file except in compliance with the License.
    // You may obtain a copy of the License at
    //
//        http://www.apache.org/licenses/LICENSE-2.0
    //
    // Unless required by applicable law or agreed to in writing, software
    // distributed under the License is distributed on an "AS IS" BASIS,
    // WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    // See the License for the specific language governing permissions and
    // limitations under the License.

    package codeu.controller;

    import codeu.model.data.User;
    import codeu.model.store.basic.UserStore;
    import org.mindrot.jbcrypt.BCrypt;

    import java.io.IOException;
    import java.time.Instant;
    import java.util.UUID;
    import java.util.logging.Logger;
    import javax.servlet.ServletException;
    import javax.servlet.http.HttpServlet;
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;

    /** Servlet class responsible for the log out page. */
    public class LogoutServlet extends HttpServlet {

      private static final Logger LOG = Logger.getLogger("ServerStartupListener");

      /**
       * Set up state for handling login-related requests. This method is only called when running in a
       * server, not when running in a test.
       */
      @Override
      public void init() throws ServletException {
        super.init();
        LOG.info("servletInfo: " + super.getServletInfo());
      }
      /**
       * This function fires when a user requests the /logout URL. It simply forwards the request to
       * logout.jsp.
       */
      @Override
      public void doGet(HttpServletRequest request, HttpServletResponse response)
          throws IOException, ServletException {
        request.getRequestDispatcher("/WEB-INF/view/Logout.jsp").forward(request, response);
      }

      /**
       * This function fires when a user submits the logout form. It simply log the user out of the current
       * session and send them back to the index.jsp page
       */
      @Override
      public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
          if(request.getParameter("button").equals("yes"))
              { 
              request.getSession().setAttribute("user", null);
              response.sendRedirect("/index.jsp");
              }
          else {
              request.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(request, response);
          }
    }

}
