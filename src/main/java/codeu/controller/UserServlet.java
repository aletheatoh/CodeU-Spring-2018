// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package codeu.controller;

import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.UserStore;
import codeu.model.store.basic.MessageStore;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet class responsible for the user profile page. */
public class UserServlet extends HttpServlet {

  /** Store class that gives access to Users. */
  private UserStore userStore;

  /** Store class that gives access to Conversations. */
  private ConversationStore conversationStore;

  /** Store class that gives access to Messages. */
  private MessageStore messageStore;

  /**
   * Set up state for handling conversation-related requests. This method is only called when
   * running in a server, not when running in a test.
   */
  @Override
  public void init() throws ServletException {
    super.init();
    setUserStore(UserStore.getInstance());
    setConversationStore(ConversationStore.getInstance());
    setMessageStore(MessageStore.getInstance());
  }

  /**
   * Sets the UserStore used by this servlet. This function provides a common setup method for use
   * by the test framework or the servlet's init() function.
   */
  void setUserStore(UserStore userStore) {
    this.userStore = userStore;
  }

  /**
   * Sets the ConversationStore used by this servlet. This function provides a common setup method
   * for use by the test framework or the servlet's init() function.
   */
  void setConversationStore(ConversationStore conversationStore) {
    this.conversationStore = conversationStore;
  }

  void setMessageStore(MessageStore messageStore) {
    this.messageStore = messageStore;
  }

  /**
   * This function fires when a user navigates to the conversations page. It gets all of the
   * conversations from the model and forwards to conversations.jsp for rendering the list.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    List<Conversation> conversations = conversationStore.getAllConversations();
    request.setAttribute("conversations", conversations);

    List<Message> messages = messageStore.getAllMessages();
    request.setAttribute("messages", messages);

    String username = (String) request.getSession().getAttribute("user");
    User user = userStore.getUser(username);
    request.setAttribute("userObj", user);

    // to get all messages:
    // UUID conversationId = conversation.getId();
    //
    // List<Message> messages = messageStore.getMessagesInConversation(conversationId);
    //
    // request.setAttribute("conversation", conversation);
    // request.setAttribute("messages", messages);

    request.getRequestDispatcher("/WEB-INF/view/user.jsp").forward(request, response);
  }

  /**
   * This function fires when a user submits the form on the user page. It gets the
   * logged-in username from the session and the updated profile features from the submitted form
   * data. It uses this to update the user object that it adds to the model.
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

    String username = (String) request.getSession().getAttribute("user");

    if (username == null) {
      // user is not logged in, don't let them edit a user profile
      response.sendRedirect("/users");
      return;
    }

    User user = userStore.getUser(username);

    if (user == null) {
      // user was not found, don't let them update their profile
      System.out.println("User not found: " + username);
      response.sendRedirect("/users");
      return;
    }

    String aboutme = request.getParameter("aboutme");
    String profilePic = request.getParameter("profilePic");

    // update user
    user.updateUser(user.id, user.name, user.password, user.creation, aboutme, profilePic);

    // String conversationTitle = request.getParameter("conversationTitle");
    // if (!conversationTitle.matches("[\\w*]*")) {
    //   request.setAttribute("error", "Please enter only letters and numbers.");
    //   request.getRequestDispatcher("/WEB-INF/view/conversations.jsp").forward(request, response);
    //   return;
    // }
    //
    // if (conversationStore.isTitleTaken(conversationTitle)) {
    //   // conversation title is already taken, just go into that conversation instead of creating a
    //   // new one
    //   response.sendRedirect("/chat/" + conversationTitle);
    //   return;
    // }

    // Conversation conversation =
    //     new Conversation(UUID.randomUUID(), user.getId(), conversationTitle, Instant.now());
    //
    // conversationStore.addConversation(conversation);

    response.sendRedirect("/users/");
  }
}
