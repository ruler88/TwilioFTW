package com.kai.twilio.main;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.kai.twilio.main.DB.User;


@SuppressWarnings("serial")
public class TwilioFTWServlet extends HttpServlet {
	
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
	  
	  RequestDispatcher dipatcher = req.getRequestDispatcher("/newPlayer.jsp"); 
      dipatcher.forward(req, resp);
      
  }
  
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
          throws IOException, ServletException {
  
	  String name = req.getParameter("name");
	  String number = req.getParameter("number").trim();
	  number = "+1"+number;
	  
	  Entity user = User.createUser(name, number);
	  if(user == null) {
		  req.setAttribute("errors", "Phone number already in use");
		  req.getRequestDispatcher("/newPlayer.jsp").forward(req, resp); return;
	  }
	  
	  DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	  datastore.put(user);
	  resp.sendRedirect("/newPlayer.jsp");
	}
	
	
}


