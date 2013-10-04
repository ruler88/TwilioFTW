package com.kai.twilio.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.twilio.sdk.verbs.Message;
import com.twilio.sdk.verbs.TwiMLException;
import com.twilio.sdk.verbs.TwiMLResponse;


@SuppressWarnings("serial")
public class TwilioResponseServlet extends HttpServlet {
	public static final String ACCOUNT_SID = "AC6c1f534804cac1c26dd1de98462b8f8b";
    public static final String AUTH_TOKEN = "4e5764c850f0e637eab5e9e987c252a6";
    
	 public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		 	HashMap<String, String> callers = new HashMap<String, String>();
	        callers.put("+14158675309", "Curious George");
	        callers.put("+14158675310", "Boots");
	        callers.put("+14158675311", "Virgil");
	        
	        String fromNumber = request.getParameter("From");
	        String knownCaller = callers.get(fromNumber);
	        String message;
	        if (knownCaller == null) {
	            // Use a generic message
	            message = "Monkey, thanks for the message!";
	        } else {
	            // Use the caller's name
	            message = knownCaller + ", thanks for the message!";
	        }
	 
	        // Create a TwiML response and add our friendly message.
	        TwiMLResponse twiml = new TwiMLResponse();
	        Message sms = new Message();
	        sms.set("Message", message);
	        //sms.setAttribute("Message", message);
	        try {
	            twiml.append(sms);
	        } catch (TwiMLException e) {
	            e.printStackTrace();
	        }
	 
	        response.setContentType("application/xml");
	        response.getWriter().print(twiml.toXML());
			
	    }
  
	
}


