package com.kai.twilio.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.appengine.api.datastore.Entity;
import com.kai.twilio.main.DB.Trivia;
import com.kai.twilio.main.DB.User;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.resource.instance.Message;


@SuppressWarnings("serial")
public class DailyUpdateServlet extends HttpServlet {
	public static final String ACCOUNT_SID = TwilioResponseServlet.ACCOUNT_SID;
    public static final String AUTH_TOKEN = TwilioResponseServlet.AUTH_TOKEN;
    public static final String TWILIO_NUMBER = TwilioResponseServlet.TWILIO_NUMBER;
    
    @Override
	 public void doGet(HttpServletRequest req, HttpServletResponse resp)
	            throws IOException, ServletException {
		  Entity newTrivia = Trivia.updateCurrentTrivia();
		  sendMessage(newTrivia.getProperty("question").toString());
		  
		  //send out text here!
	  }
		
    public void sendMessage(String question) {
    	
    	TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
        Account account = client.getAccount();
        
        String number = "";
        
        Iterable<Entity> allPlayers = User.getAllUsers();
        MessageFactory messageFactory = account.getMessageFactory();
        
        for(Entity user : allPlayers) {
        	number = user.getProperty("number").toString();
        	
        	List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("To", number));
            params.add(new BasicNameValuePair("From", TWILIO_NUMBER));
            params.add(new BasicNameValuePair("Body", "Today's daily trivia: " + question));
            try {
    			Message sms = messageFactory.create(params);
    		} catch (TwilioRestException e) {
    			e.printStackTrace();
    		}
        }
    }
	
}


