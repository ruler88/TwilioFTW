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
import com.kai.twilio.main.DB.Response;
import com.kai.twilio.main.DB.Trivia;
import com.kai.twilio.main.DB.User;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.resource.instance.Message;


@SuppressWarnings("serial")
public class TwilioResponseServlet extends HttpServlet {
	public static final String ACCOUNT_SID = "AC6c1f534804cac1c26dd1de98462b8f8b";
    public static final String AUTH_TOKEN = "4e5764c850f0e637eab5e9e987c252a6";
    public static final String TWILIO_NUMBER = "+12014904989";
    
	 public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	        String fromNumber = request.getParameter("From");
	        Entity user = User.getUser(fromNumber);
	        String message = "";
	        if(user==null) {
	        	message = "Number not found! Sign up at magicianp0424.appspot.com/addPlayer";
	        } else {
	        	if(Trivia.currentTriviaSolvedStatus()) {
	        		message = "Sorry, today's question has already been solved!";
	        	} else {
	        		String content = request.getParameter("Body");
		        	Response.addResponse(content);
		        	if(checkAnswer(content)) {
		        		message = "You are boss " + user.getProperty("name") + "! \"" + content + "\" is correct!";
		        		User.increasePoint(fromNumber);
		        		Trivia.solveCurrentTrivia(user.getProperty("name").toString());
		        	} else {
		        		message = "Sorry " + user.getProperty("name") + " your response \"" + 
		        				content + "\" is incorrect";
		        	}
	        	}
	        }
	        
	        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
	        Account account = client.getAccount();
	        
	        MessageFactory messageFactory = account.getMessageFactory();
	        List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("To", fromNumber));
	        params.add(new BasicNameValuePair("From", TWILIO_NUMBER));
	        params.add(new BasicNameValuePair("Body", message));
	        try {
				Message sms = messageFactory.create(params);
			} catch (TwilioRestException e) {
				e.printStackTrace();
			}
	 }
  
	 public boolean checkAnswer(String s) {
		 Entity currentTrivia = Trivia.getCurrentTrivia();
		 String correctAnswer = currentTrivia.getProperty("answer").toString().toLowerCase();
		 if(correctAnswer.equals(s.toLowerCase())) {
			 return true;
		 }
		 return false;
	 }
	
}


