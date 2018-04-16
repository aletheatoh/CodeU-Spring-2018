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
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;

import java.awt.*;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/** Servlet class responsible for the chat page. */
public class ChatServlet extends HttpServlet {

  private static final Logger LOG = Logger.getLogger("Chat Server Startup");

  /** Store class that gives access to Conversations. */
  private ConversationStore conversationStore;

  /** Store class that gives access to Messages. */
  private MessageStore messageStore;

  /** Store class that gives access to Users. */
  private UserStore userStore;

  /** Set up state for handling chat requests. */
  @Override
  public void init() throws ServletException {
    super.init();
    setConversationStore(ConversationStore.getInstance());
    setMessageStore(MessageStore.getInstance());
    setUserStore(UserStore.getInstance());
  }

  /**
   * Sets the ConversationStore used by this servlet. This function provides a common setup method
   * for use by the test framework or the servlet's init() function.
   */
  void setConversationStore(ConversationStore conversationStore) {
    this.conversationStore = conversationStore;
  }

  /**
   * Sets the MessageStore used by this servlet. This function provides a common setup method for
   * use by the test framework or the servlet's init() function.
   */
  void setMessageStore(MessageStore messageStore) {
    this.messageStore = messageStore;
  }

  /**
   * Sets the UserStore used by this servlet. This function provides a common setup method for use
   * by the test framework or the servlet's init() function.
   */
  void setUserStore(UserStore userStore) {
    this.userStore = userStore;
  }

  private String parsedEmojis(String messageUnparsed) {
    //Parse any emoji text before sending message
    messageUnparsed = messageUnparsed.replaceAll(":grinning:", "\uD83D\uDE00");
    messageUnparsed = messageUnparsed.replaceAll(":laughing:", "\uD83D\uDE02");
    messageUnparsed = messageUnparsed.replaceAll(":winking:", "\uD83D\uDE09");
    messageUnparsed = messageUnparsed.replaceAll(":sunglasses:", "\uD83D\uDE0E");
    messageUnparsed = messageUnparsed.replaceAll(":heart_eyes:", "\uD83D\uDE0D");
    messageUnparsed = messageUnparsed.replaceAll(":neutral:", "\uD83D\uDE10");
    messageUnparsed = messageUnparsed.replaceAll(":confused:", "\uD83D\uDE15");
    messageUnparsed = messageUnparsed.replaceAll(":disappointed:", "\uD83D\uDE1E");
    messageUnparsed = messageUnparsed.replaceAll(":crying:", "\uD83D\uDE2D");
    messageUnparsed = messageUnparsed.replaceAll(":angry:", "\uD83D\uDE20");
    return messageUnparsed;
  }

  //Bold - **, Underline - //, Italicize - ~, Strikethrough - =
  private String parsedTextStyles(String messageUnparsed) {
    messageUnparsed = replaceAllStyles("underline", messageUnparsed);
    messageUnparsed = replaceAllStyles("bold", messageUnparsed);
    messageUnparsed = replaceAllStyles("italicize", messageUnparsed);
    messageUnparsed = replaceAllStyles("strikethrough", messageUnparsed);
    return messageUnparsed;
  }

  /**
   * Function will return -1, -1 if none of the stylizations encountered is correctly formatted.
   * Otherwise, it will return the indices in which it encounters a correctly formatted stylization.
   * @param stylization
   * @return
   */
  private int[] getIndexOfStyle(String stylization, String messageUnparsed) {
    int[] indicies = new int[2];
    indicies[0] = -1; indicies[1] = -1;
    String style = "*";
    if(stylization.equals("underline")) {
      style = "/";
    }
    if (stylization.equals("italicize")) {
      style = "~";
    }
    if (stylization.equals("strikethrough")) {
      style = "=";
    }
    if (messageUnparsed.indexOf(style) == -1) return indicies;
    else {
      if (messageUnparsed.substring(messageUnparsed.indexOf(style) + 1).indexOf(style) == -1) return indicies;
      else {
        indicies[0] = messageUnparsed.indexOf(style);
        indicies[1] = messageUnparsed.substring(messageUnparsed.indexOf(style) + 1).indexOf(style) + 1;
        LOG.info(""+ indicies[0]);
        LOG.info("" + indicies[1]);
        return indicies;
      }
    }
  }

  private String replaceAllStyles(String stylization, String messageUnparsed) {
    String style = "<strong>";
    String styleEnding = "</strong>";
    if(stylization.equals("underline")) {
      style = "<u>";
      styleEnding = "</u>";
    }
    if (stylization.equals("italicize")) {
      style = "<i>";
      styleEnding = "</i>";
    }
    if (stylization.equals("strikethrough")) {
      style = "<strike>";
      styleEnding = "</strike>";
    }
    StringBuilder messageReplaced = new StringBuilder();
    int[] indicies = getIndexOfStyle(stylization, messageUnparsed);
    while (messageUnparsed.length() != 0 && indicies[0] != -1) {
      if (indicies[0] == 0) {
        messageReplaced.append(style + messageUnparsed.substring(1, indicies[1]) + styleEnding);
      }
      else {
        messageReplaced.append(messageUnparsed.substring(0, indicies[0]) + style + messageUnparsed.substring(indicies[0]
                + 1, indicies[1]) + styleEnding);
      }
      if(indicies[1] == messageUnparsed.length() - 1) {
        messageUnparsed = "";
      } else {
        messageUnparsed = messageUnparsed.substring(indicies[1] + 1);
      }
    }
    if (indicies[0] == -1) {
      messageReplaced.append(messageUnparsed);
    }
    return messageReplaced.toString();
  }

  /**
   * This function fires when a user navigates to the chat page. It gets the conversation title from
   * the URL, finds the corresponding Conversation, and fetches the messages in that Conversation.
   * It then forwards to chat.jsp for rendering.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    String requestUrl = request.getRequestURI();
    String conversationTitle = requestUrl.substring("/chat/".length());

    Conversation conversation = conversationStore.getConversationWithTitle(conversationTitle);
    if (conversation == null) {
      // couldn't find conversation, redirect to conversation list
      System.out.println("Conversation was null: " + conversationTitle);
      response.sendRedirect("/conversations");
      return;
    }

    UUID conversationId = conversation.getId();

    List<Message> messages = messageStore.getMessagesInConversation(conversationId);

    request.setAttribute("conversation", conversation);
    request.setAttribute("messages", messages);
    request.getRequestDispatcher("/WEB-INF/view/chat.jsp").forward(request, response);
  }

  /**
   * This function fires when a user submits the form on the chat page. It gets the logged-in
   * username from the session, the conversation title from the URL, and the chat message from the
   * submitted form data. It creates a new Message from that data, adds it to the model, and then
   * redirects back to the chat page.
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

    String username = (String) request.getSession().getAttribute("user");
    if (username == null) {
      // user is not logged in, don't let them add a message
      response.sendRedirect("/login");
      return;
    }

    User user = userStore.getUser(username);
    if (user == null) {
      // user was not found, don't let them add a message
      response.sendRedirect("/login");
      return;
    }

    String requestUrl = request.getRequestURI();
    String conversationTitle = requestUrl.substring("/chat/".length());

    Conversation conversation = conversationStore.getConversationWithTitle(conversationTitle);
    if (conversation == null) {
      // couldn't find conversation, redirect to conversation list
      response.sendRedirect("/conversations");
      return;
    }

    String messageContent = request.getParameter("message");

    // this removes any HTML from the message content
    String cleanedMessageContent = Jsoup.clean(messageContent, Whitelist.none());

    cleanedMessageContent = parsedEmojis(cleanedMessageContent);
    cleanedMessageContent = parsedTextStyles(cleanedMessageContent);

    Message message =
        new Message(
            UUID.randomUUID(),
            conversation.getId(),
            user.getId(),
            cleanedMessageContent,
            Instant.now());

    messageStore.addMessage(message);

    // redirect to a GET request
    response.sendRedirect("/chat/" + conversationTitle);
  }
}
