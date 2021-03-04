package Application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Application.entity.User;
import Application.repository.UserRepository;

@RestController
@RequestMapping("/")
public class UserController {

  @Autowired
  private UserRepository userRepository;

  public static final int SUPER_USER = 1;
  public static final int ADMIN = 2;
  public static final int GENERAL = 3;

  public static final int ACCESS_LEVEL_EVERYONE = 1;
  public static final int ACCESS_LEVEL_AUTHENTICATED = 2;
  public static final int ACCESS_LEVEL_ROLE_BASED = 3;
  /** if frontend give me a userId that means the user must have logged in, we just need to check if that user is paid or not
   * or exceed our 5 person count if not a paid user
   * if frontend don't give me a userId that means it's a brand new user, we need to create a super user first
   *
   * @param username
   * @param password
   * @param email
   * @param phoneNumber
   * @param position
   * @return
   */
  @RequestMapping(value = "register", method = RequestMethod.POST)
  public ResponseEntity<?> register(@RequestParam String username, @RequestParam String password,
                                    @RequestParam String email, @RequestParam  String phoneNumber, @RequestParam String position,
                                    @RequestParam int userId, @RequestParam int accessLevel) {

    if (userRepository.findByUsername(username) != null) {

      return new ResponseEntity<String>(HttpStatus.CONFLICT);
    }
    User user;
    if (userId != 0) {
      user = userRepository.findById(userId).get();
      if (user.isPaidUser()) {
        userRepository.save(User.builder().email(email).username(username).password(password).phoneNumber(phoneNumber)
          .role(GENERAL).isPaidUser(true).position(position).accessLevel(accessLevel).build());
        return new ResponseEntity<String>(HttpStatus.CREATED);
      }
      if (userRepository.getUserCountFromSuperUser(userId) < 4) {
        userRepository.save(User.builder().email(email).username(username).password(password).phoneNumber(phoneNumber)
          .role(GENERAL).isPaidUser(false).position(position).accessLevel(accessLevel).build());
        return new ResponseEntity<String>(HttpStatus.CREATED);
      }
      else
         return new ResponseEntity<String>("Please pay us, you already have 5 users", HttpStatus.FORBIDDEN);
      }

    userRepository.save(User.builder().email(email).username(username).password(password).phoneNumber(phoneNumber)
      .role(GENERAL).isPaidUser(false).position(position).accessLevel(accessLevel).build());
    return new ResponseEntity<String>(HttpStatus.CREATED);
  }


  @RequestMapping(value = "login", method = RequestMethod.GET)
  public HttpEntity<?> login(@RequestParam String username, @RequestParam String password) {

    User user = userRepository.findByUsernameAndPassword(username, password);
    if(user == null){

      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(user,HttpStatus.OK);
  }

  /**
   * front end should block any non super user sending request to update any user to super user
   * @param username
   * @param password
   * @param email
   * @param phoneNumber
   * @param position
   * @param userId
   * @param accessLevel
   * @param role
   * @return
   */
  @RequestMapping(value = "update", method = RequestMethod.PUT)
  public HttpEntity<?> updateUser(@RequestParam String username, @RequestParam String password,
                                  @RequestParam String email, @RequestParam String phoneNumber, @RequestParam String position,
                                  @RequestParam int userId, @RequestParam int accessLevel, @RequestParam int role) {
    userRepository.save(User.builder().id(userId).email(email).username(username).password(password).phoneNumber(phoneNumber)
      .role(role).isPaidUser(false).position(position).accessLevel(accessLevel).build());
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
