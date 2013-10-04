package com.kai.twilio.main.DB;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.Entity;


public class User {
	private static final Logger logger = Logger.getLogger(User.class.getCanonicalName());
  /**
   * 
   * @param name
   * @param number
   * @param score
   * @return
   */
  public static Entity createUser(String name, String number) {
	  	if(getUser(number) != null) {
	  		logger.log(Level.INFO, "User already exists");
	  		return null;
	  	}
		Entity  user = new Entity("User");
	      user.setProperty("name", name);
	      user.setProperty("number", number);
	      user.setProperty("score", 0);
	      user.setProperty("date", new Date());
	    Util.persistEntity(user);
	    return user;
  }

  /**
   * get All the users in the list
   */
  public static Iterable<Entity> getAllUsers() {
  	Iterable<Entity> entities = Util.listEntities("User", null, null);
  	return entities;
  }
  
  /**
   * get All the users in the list
   */
  public static Iterable<Entity> getAllUsersByScore() {
  	Iterable<Entity> entities = Util.listSortedEntities("User", "score");
  	return entities;
  }

  /**
   * Get the user by name, return an Iterable
   */
  public static Entity getUser(String number) {
	  Iterable<Entity> tmpIte = Util.listEntities("User", "number", number);
	  if(tmpIte != null) {
		  for(Entity e : tmpIte) {
			  return e;
		  }
	  }
  	return null;
  }
  
  /*
   * Increase player by one point
   */
  public static void increasePoint(String number) {
	  Entity e = getUser(number);
	  int score = Integer.parseInt(e.getProperty("score").toString());
	  e.setProperty("score", score+1);
	  Util.persistEntity(e);
  }
  
  public static String deleteUser(String number)
  {
    Entity entity = getUser(number);    
    if(entity != null){
      Util.deleteEntity(entity.getKey());
      return("User deleted successfully.");
    } else
      return("User not found");      
  }
}
