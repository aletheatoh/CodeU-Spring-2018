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

package codeu.model.store.basic;

import codeu.controller.RegisterServlet;
import codeu.model.data.User;
import codeu.model.store.persistence.PersistentDataStoreException;
import codeu.model.store.persistence.PersistentStorageAgent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import com.google.appengine.api.datastore.Key;

/**
 * Store class that uses in-memory data structures to hold values and automatically loads from and
 * saves to PersistentStorageAgent. It's a singleton so all servlet classes can access the same
 * instance.
 */
public class UserStore {

  /** Singleton instance of UserStore. */
  private static UserStore instance;

  /**
   * Returns the singleton instance of UserStore that should be shared between all servlet classes.
   * Do not call this function from a test; use getTestInstance() instead.
   */
  public static UserStore getInstance() {
    if (instance == null) {
      instance = new UserStore(PersistentStorageAgent.getInstance());
    }
    return instance;
  }

  /**
   * Instance getter function used for testing. Supply a mock for PersistentStorageAgent.
   *
   * @param persistentStorageAgent a mock used for testing
   */
  public static UserStore getTestInstance(PersistentStorageAgent persistentStorageAgent) {
    return new UserStore(persistentStorageAgent);
  }

  /**
   * The PersistentStorageAgent responsible for loading Users from and saving Users to Datastore.
   */
  private PersistentStorageAgent persistentStorageAgent;

  private static HashMap<UUID, Key> keysMap;

  /** The in-memory list of Users. */
  private List<User> users;
  
  /** The in-memory list of security question. */
  private HashMap<String, String> securityQuestions;

  /** This class is a singleton, so its constructor is private. Call getInstance() instead. */
  private UserStore(PersistentStorageAgent persistentStorageAgent) {
    this.persistentStorageAgent = persistentStorageAgent;
    users = new ArrayList<>();
    this.keysMap = new HashMap<UUID, Key>();
  }

  /** Load a set of randomly-generated Message objects. */
  public void loadTestData() {
    users.addAll(DefaultDataStore.getInstance().getAllUsers());
  }

  /**
   * Access the User object with the given name.
   *
   * @return null if username does not match any existing User.
   */
  public User getUser(String username) {
    // This approach will be pretty slow if we have many users.
    for (User user : users) {
      if (user.getName().equals(username)) {
        return user;
      }
    }
    return null;
  }

  /**
   * Access the User object with the given UUID.
   *
   * @return null if the UUID does not match any existing User.
   */
  public User getUser(UUID id) {
    for (User user : users) {
      if (user.getId().equals(id)) {
        return user;
      }
    }
    return null;
  }

  /** Add a new user to the current set of users known to the application. */
  public void addUser(User user) {
    users.add(user);
    persistentStorageAgent.writeThrough(user);
  }

  /** Return true if the given username is known to the application. */
  public boolean isUserRegistered(String username) {
    for (User user : users) {
      if (user.getName().equals(username)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Sets the List of Users stored by this UserStore. This should only be called once, when the data
   * is loaded from Datastore.
   */
  public void setUsers(List<User> users) {
    this.users = users;
  }
  
  /**
   * Set the securityQuestions HashMap for the UserStore
   * @param questions
   *        HashMap to be set
   */
  public void setSecurityQuestions(HashMap<String, String> questions){
      this.securityQuestions =questions;
  }
  
  /**
   * Get the securityQuestion HashMap
   * @return securityQuestion HashMap
   */
  public HashMap<String, String> getSecurityQuestions()
  {
    return this.securityQuestions;
      
  }
  
  /**
   * Add a pair of user id, user key to the keysMap
   * @param id
   *        uuid of the user
   * @param key
   *        key of the user entity in database
   */
  public void addUserKey(UUID id, Key key)
  {
      if(!this.keysMap.containsKey(id))
      {
          this.keysMap.put(id, key);
      }
  }
  
  /**
   * Get user key corresponding to specified uuid
   * @param id
   *        user uuid
   * @return key
   */
  public Key getUserKey(UUID id)
  {
      return this.keysMap.get(id);
  }
  
  /**
   * Update the password of the user in the database
   * @param user
   *        user to be update
   * @param newPass
   *        new password
   * @return the key of the user
   * 
   */
  public Key updatePassword(User user, String newPass)
  {
      
      return persistentStorageAgent.updatePassword(user, newPass);
  }
}
