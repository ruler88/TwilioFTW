package com.kai.twilio.main.DB;

import com.google.appengine.api.datastore.Entity;

public class Response {
	
	public static void clearResponses() {
		for(Entity e : Util.listEntities("Response", null, null)) {
	  		Util.deleteEntity(e.getKey());
	  	}
	}
	
	public static Iterable<Entity> getAllResponses() {
		Iterable<Entity> entities = Util.listEntities("Response", null, null);
		return entities;
	}
	
	public static Entity addResponse(String response) {
		Entity entity = new Entity("Response");
		entity.setProperty("answer", response);
		Util.persistEntity(entity);
		return entity;
	}
}
