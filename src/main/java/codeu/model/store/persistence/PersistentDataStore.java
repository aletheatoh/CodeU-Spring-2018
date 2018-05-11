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

package codeu.model.store.persistence;

import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.basic.UserStore;
import codeu.model.store.persistence.PersistentDataStoreException;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

import com.google.appengine.api.datastore.Key;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import org.mortbay.log.Log;

/**
 * This class handles all interactions with Google App Engine's Datastore service. On startup it
 * sets the state of the applications's data objects from the current contents of its Datastore. It
 * also performs writes of new of modified objects back to the Datastore.
 */
public class PersistentDataStore {

  // Handle to Google AppEngine's Datastore service.
  private DatastoreService datastore;
  private static final Logger LOG = Logger.getLogger("DataStoreListener");

  /**
   * Constructs a new PersistentDataStore and sets up its state to begin loading objects from the
   * Datastore service.
   */
  public PersistentDataStore() {
    datastore = DatastoreServiceFactory.getDatastoreService();
  }

  /**
   * Get all the security questions choice stored in database and return them in a hashmap 
   * @return a hashmap with key = value property in database and in jsp and value = question
   * @throws PersistentDataStoreException if an error was detected during the load from the
   *     Datastore service
   */
  public HashMap<String, String> loadSecurityQuestions() throws PersistentDataStoreException {
      HashMap<String, String> securityQuestions = new HashMap<String, String>();
      //still working on this -> incomplete
   // Retrieve all questions from the datastore.
      Query query = new Query("security-question");
      PreparedQuery results = datastore.prepare(query);

      for (Entity entity : results.asIterable()) {
        try {
          String question = (String) entity.getProperty("question");
          String value = (String)entity.getProperty("value");
         
          securityQuestions.put(value, question);
        } catch (Exception e) {
          // In a production environment, errors should be very rare. Errors which may
          // occur include network errors, Datastore service errors, authorization errors,
          // database entity definition mismatches, or service mismatches.
            
          throw new PersistentDataStoreException(e);
        }
      } 
      if(securityQuestions.isEmpty())
      {
          securityQuestions.put("2kiss", "What was the name of the boy/girl you had your second kiss with?");
          securityQuestions.put("3teacher", "What was the last name of your third grade teacher?");
          securityQuestions.put("2pet", "What was the name of your second dog/cat/goldfish/etc?");
          securityQuestions.put("ny2000", "Where were you New Year's 2000?");
          securityQuestions.put("1kiss", "Where were you when you had your first kiss?");
          securityQuestions.put("villian", "Who is your least favorite villian in movie/book/story/life/etc.?");
      }
      return securityQuestions;
  }
  
  /**
   * Loads all User objects from the Datastore service and returns them in a List.
   *
   * @throws PersistentDataStoreException if an error was detected during the load from the
   *     Datastore service
   */
  public List<User> loadUsers() throws PersistentDataStoreException {

    List<User> users = new ArrayList<>();

    // Retrieve all users from the datastore.
    Query query = new Query("chat-users");
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      try {
        UUID uuid = UUID.fromString((String) entity.getProperty("uuid"));
        String userName = (String) entity.getProperty("username");
        String password = (String)entity.getProperty("password");

        String aboutme = (String)entity.getProperty("aboutme");
        String profilePic = (String)entity.getProperty("profilePic");

        Instant creationTime = Instant.parse((String) entity.getProperty("creation_time"));

        User user = new User(uuid, userName, password, creationTime, aboutme, profilePic);
        if(entity.hasProperty("question1")){
        user.answerSecurityQuestions((String)entity.getProperty("question1"), (String)entity.getProperty("answer1"));
        user.answerSecurityQuestions((String)entity.getProperty("question2"), (String)entity.getProperty("answer2"));
        }
        UserStore.getInstance().addUserKey(uuid, entity.getKey());

        users.add(user);
      
      } catch (Exception e) {
        // In a production environment, errors should be very rare. Errors which may
        // occur include network errors, Datastore service errors, authorization errors,
        // database entity definition mismatches, or service mismatches.
        throw new PersistentDataStoreException(e);
      }
    }

    return users;
  }

  /**
   * Loads all Conversation objects from the Datastore service and returns them in a List.
   *
   * @throws PersistentDataStoreException if an error was detected during the load from the
   *     Datastore service
   */
  public List<Conversation> loadConversations() throws PersistentDataStoreException {

    List<Conversation> conversations = new ArrayList<>();

    // Retrieve all conversations from the datastore.
    Query query = new Query("chat-conversations");
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      try {
        UUID uuid = UUID.fromString((String) entity.getProperty("uuid"));
        UUID ownerUuid = UUID.fromString((String) entity.getProperty("owner_uuid"));
        String title = (String) entity.getProperty("title");
        Instant creationTime = Instant.parse((String) entity.getProperty("creation_time"));
        Conversation conversation = new Conversation(uuid, ownerUuid, title, creationTime);
        conversations.add(conversation);
      } catch (Exception e) {
        // In a production environment, errors should be very rare. Errors which may
        // occur include network errors, Datastore service errors, authorization errors,
        // database entity definition mismatches, or service mismatches.
        throw new PersistentDataStoreException(e);
      }
    }

    return conversations;
  }

  /**
   * Loads all Message objects from the Datastore service and returns them in a List.
   *
   * @throws PersistentDataStoreException if an error was detected during the load from the
   *     Datastore service
   */
  public List<Message> loadMessages() throws PersistentDataStoreException {

    List<Message> messages = new ArrayList<>();

    // Retrieve all messages from the datastore.
    Query query = new Query("chat-messages");
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      try {
        UUID uuid = UUID.fromString((String) entity.getProperty("uuid"));
        UUID conversationUuid = UUID.fromString((String) entity.getProperty("conv_uuid"));
        UUID authorUuid = UUID.fromString((String) entity.getProperty("author_uuid"));
        Instant creationTime = Instant.parse((String) entity.getProperty("creation_time"));
        String content = (String) entity.getProperty("content");
        Message message = new Message(uuid, conversationUuid, authorUuid, content, creationTime);
        messages.add(message);
      } catch (Exception e) {
        // In a production environment, errors should be very rare. Errors which may
        // occur include network errors, Datastore service errors, authorization errors,
        // database entity definition mismatches, or service mismatches.
        throw new PersistentDataStoreException(e);
      }
    }

    return messages;
  }

  /** Write a User object to the Datastore service. */
  public void writeThrough(User user) {
    Entity userEntity = new Entity("chat-users");
    userEntity.setProperty("uuid", user.getId().toString());
    userEntity.setProperty("username", user.getName());
    userEntity.setProperty("password", user.getPassword());
    userEntity.setProperty("creation_time", user.getCreationTime().toString());

    userEntity.setProperty("question1", user.getQuestionAnswer().get(0).getValue());
    userEntity.setProperty("question2", user.getQuestionAnswer().get(1).getValue());
    userEntity.setProperty("answer1", user.getQuestionAnswer().get(0).getAnswer());
    userEntity.setProperty("answer2", user.getQuestionAnswer().get(1).getAnswer());
    
    Key key = datastore.put(userEntity);
    UserStore.getInstance().addUserKey(user.getId(), key);

    userEntity.setProperty("aboutme", user.getAboutMe());
    userEntity.setProperty("profilePic", user.getProfilePic());
    
    datastore.put(userEntity);

  }
/** Update the password of an entity after reset */
  public Key updatePassword(User user, String newPass)
  {
   try {
    Entity updateUser = datastore.get(UserStore.getInstance().getUserKey(user.getId()));
    updateUser.setProperty("password", newPass);
    LOG.info(String.format(">>> Username: %s, new password: %s", (String)updateUser.getProperty("username"),(String)updateUser.getProperty("password") ));
    updateUser.setProperty("reset_time", user.getResetPassTime().toString());
    Key key = datastore.put(updateUser);
    return key;
}
catch (EntityNotFoundException e) {
   Log.info(String.format(">>>> User %s is not in datastore", user.getName()));
   return null;
}  
   
  }
  
  /** Write a Message object to the Datastore service. */
  public void writeThrough(Message message) {
    Entity messageEntity = new Entity("chat-messages");
    messageEntity.setProperty("uuid", message.getId().toString());
    messageEntity.setProperty("conv_uuid", message.getConversationId().toString());
    messageEntity.setProperty("author_uuid", message.getAuthorId().toString());
    messageEntity.setProperty("content", message.getContent());
    messageEntity.setProperty("creation_time", message.getCreationTime().toString());
    datastore.put(messageEntity);
  }

  /** Write a Conversation object to the Datastore service. */
  public void writeThrough(Conversation conversation) {
    Entity conversationEntity = new Entity("chat-conversations");
    conversationEntity.setProperty("uuid", conversation.getId().toString());
    conversationEntity.setProperty("owner_uuid", conversation.getOwnerId().toString());
    conversationEntity.setProperty("title", conversation.getTitle());
    conversationEntity.setProperty("creation_time", conversation.getCreationTime().toString());
    datastore.put(conversationEntity);
  }
}
