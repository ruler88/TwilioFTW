package com.kai.twilio.main.DB;

import java.util.Date;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.Entity;


public class Trivia {
	private static final Logger logger = Logger.getLogger(Trivia.class.getCanonicalName());
	
	public static Iterable<Entity> getAllPastQuestions() {
		Iterable<Entity> entities = Util.listSortedEntities("PastTrivia", "date");
	  	return entities;
	}
	public static void pushPastQuestion(Entity e) {
		Entity pastQuestion = new Entity("PastTrivia");
		pastQuestion.setPropertiesFrom(e);
		Util.persistEntity(pastQuestion);
	}
	
	public static Entity updateCurrentTrivia() {
		clearCurrentTrivia();
		Response.clearResponses();
		Entity currentQuestion = new Entity("CurrentTrivia");
		
		Entity randomQuestion = getOneQuestion();
		if(randomQuestion==null) {
			currentQuestion = getEmptyEntity();
		} else {
			currentQuestion.setPropertiesFrom(randomQuestion);
			currentQuestion.setProperty("date", new Date());
			currentQuestion.setProperty("solved", false);
			Util.persistEntity(currentQuestion);
		}
		return currentQuestion;
	}
	
	public static boolean currentTriviaSolvedStatus() {
		Entity currentTrivia = getCurrentTrivia();
		return Boolean.parseBoolean(currentTrivia.getProperty("solved").toString());
	}
	
	public static void solveCurrentTrivia(String name) {
		Entity currentTrivia = getCurrentTrivia();
		currentTrivia.setProperty("solved", true);
		currentTrivia.setProperty("solvedBy", name);
		Util.persistEntity(currentTrivia);
	}
	
	public static Entity getCurrentTrivia() {
		//consider caching this at some point
		for(Entity e : Util.listEntities("CurrentTrivia", null, null)) {
	  		return e;
	  	}
		return getEmptyEntity();
	}
	
	public static Entity getEmptyEntity() {
		Entity currentQuestion = new Entity("CurrentTrivia");
		currentQuestion.setProperty("question", "We are out of questions today... sorry about that");
		currentQuestion.setProperty("answer", "kai is so awesome"); //no one will ever get this!!
		currentQuestion.setProperty("points", Integer.MAX_VALUE);
		currentQuestion.setProperty("solved", true);
		return currentQuestion;
	}
	
	public static void clearCurrentTrivia() {
		for(Entity e : Util.listEntities("CurrentTrivia", null, null)) {
			pushPastQuestion(e);
	  		Util.deleteEntity(e.getKey());
	  	}
	}
	
  public static Entity createQuestion(String question, String answer) {
	  //default one point
	    return createQuestion(question, answer, 1);
  }
  
  public static Entity createQuestion(String question, String answer, int points) {
	  if(getQuestion(question) != null) {
		  return null;
	  }
		Entity questionE = new Entity("Trivia");
		questionE.setProperty("question", question);
		questionE.setProperty("answer", answer);
		questionE.setProperty("points", points);
		questionE.setProperty("solved", false);
		Util.persistEntity(questionE);
		return questionE;
  }

  public static Iterable<Entity> getAllQuestions() {
  	Iterable<Entity> entities = Util.listEntities("Trivia", null, null);
  	return entities;
  }
  
  public static Entity getOneQuestion() {
	  Iterable<Entity> allQuestions = getAllQuestions();
	  for(Entity e : allQuestions) {
		  Util.deleteEntity(e.getKey());
		  return e;
	  }
	  return null;
  }
  
  public static Entity getQuestion(String question) {
  	for(Entity e : Util.listEntities("Trivia", "question", question)) {
  		return e;
  	}
  	return null;
  }
  
  public static String deleteQuestion(String question)
  {
    Entity entity = getQuestion(question);    
    if(entity != null){
      Util.deleteEntity(entity.getKey());
      return("User deleted successfully.");
    } else
      return("User not found");      
  }
}
