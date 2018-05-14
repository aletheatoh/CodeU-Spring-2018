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

package codeu.model.data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;

/** Class representing a registered user. */
public class User {
  private final UUID id;
  private final String name;
  private final Instant creation;
  private String hashedPassword;
  //private boolean active;
  //private String email;
  private ArrayList<SecurityQuestion> securityAnswers;
  private Instant passReset;
  private String aboutMe = "No Bio Available"; // default value
  private String profilePicture;

  /**
   * Constructs a new User.
   *
   * @param id the ID of this User
   * @param name the username of this User
   * @param password the password of this User
   * @param creation the creation time of this User
   */
  public User(UUID id, String name, String password, Instant creation) {
    this.id = id;
    this.name = name;
    this.hashedPassword = password;
    this.creation = creation;
    this.securityAnswers = new ArrayList<SecurityQuestion>();
  }

  /** Returns the ID of this User. */
  public UUID getId() {
    return id;
  }


  /** Returns the username of this User. */
  public String getName() {
    return name;
  }

  /**
   * Returns the password of this User.
   */
  public String getPassword() {
    return hashedPassword;
  }

  /** Returns the creation time of this User. */
  public Instant getCreationTime() {
    return creation;
  }

  /** Save user answers to security questions */
  public void answerSecurityQuestions(String questionValue, String answer)
  {
      this.securityAnswers.add(new SecurityQuestion(questionValue, answer));
  }

  /**
   * get the question-answer pair for the user
   * @return the questions and answers of the user
   */
  public ArrayList<SecurityQuestion> getQuestionAnswer()
  {
      return this.securityAnswers;
  }

  /**
   * Reset the password of the user
   * @param newPassword
   */
  public void resetPass(String newPassword)
  {
      this.hashedPassword=newPassword;

  }

  /**
   * get the last time user reset his/her password
   * @return
   */
  public Instant getResetPassTime()
  {
      return this.passReset;
  }
  /**
   * record the last time user reset his/her password
   */
  public  void setResetPassTime()
  {
      this.passReset = Instant.now();
  }


  /** Returns the about me of this User. */
  public String getAboutMe() {
    return aboutMe;
  }

  /** Returns the profile picture src of this User. */
  public String getProfilePic() {
    return profilePicture;
  }

  // only allow user to update password, aboutMe, and profilePicture
  public void updateUser(String password, String aboutMe, String profilePicture) {
    this.hashedPassword = password;
    this.aboutMe = aboutMe;
    this.profilePicture = profilePicture;
  }
}
