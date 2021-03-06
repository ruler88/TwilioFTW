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
import com.kai.twilio.main.DB.Util;


@SuppressWarnings("serial")
public class PlayerRemoveServlet extends HttpServlet {
	
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
	  
      RequestDispatcher dipatcher = req.getRequestDispatcher("/removePlayer.jsp"); 
      dipatcher.forward(req, resp);
      
  }
  
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
          throws IOException, ServletException {
	  String name = req.getParameter("name");
	  String number = req.getParameter("number").trim();
	  number = "+1"+number;
	  
	  Entity user = User.getUser(number);
	  if(user==null){
		  req.setAttribute("errors", "Number does not exist");
		  req.getRequestDispatcher("/removePlayer.jsp").forward(req, resp); return;
	  }
	  
	  if(name.equals(user.getProperty("name"))) {
		  Util.deleteEntity(user.getKey());
	  } else {
		  req.setAttribute("errors", "Name does not match number");
		  req.getRequestDispatcher("/removePlayer.jsp").forward(req, resp); return;
	  }
	  
	  req.setAttribute("success", name + " successfully removed!");
	  req.getRequestDispatcher("/removePlayer.jsp").forward(req, resp); return;
	}
}


