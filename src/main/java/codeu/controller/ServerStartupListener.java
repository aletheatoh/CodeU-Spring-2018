package codeu.controller;

import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import codeu.model.store.persistence.PersistentDataStoreException;
import codeu.model.store.persistence.PersistentStorageAgent;
import java.util.List;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Listener class that fires when the server first starts up, before any servlet classes are
 * instantiated.
 */
public class ServerStartupListener implements ServletContextListener {

  private static final Logger LOG = Logger.getLogger("ServerStartupListener");

  /** Loads data from Datastore. */
  @Override
  public void contextInitialized(ServletContextEvent sce) {
    try {
      List<User> users = PersistentStorageAgent.getInstance().loadUsers();
      UserStore.getInstance().setUsers(users);
      LOG.info(String.format(">>>> Loaded %d users", users.size()));

      List<Conversation> conversations = PersistentStorageAgent.getInstance().loadConversations();
      ConversationStore.getInstance().setConversations(conversations);
      LOG.info(String.format(">>>> Loaded %d conversations", conversations.size()));

      List<Message> messages = PersistentStorageAgent.getInstance().loadMessages();
      MessageStore.getInstance().setMessages(messages);
      LOG.info(String.format(">>>> Loaded %d messages", messages.size()));

    } catch (PersistentDataStoreException e) {
      System.err.println("Server didn't start correctly. An error occurred during Datastore load!");
      System.err.println("This is usually caused by loading data that's in an invalid format.");
      System.err.println("Check the stack trace to see exactly what went wrong.");
      throw new RuntimeException(e);
    }
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {}
}
