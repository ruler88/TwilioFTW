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
import com.kai.twilio.main.DB.Trivia;


@SuppressWarnings("serial")
public class TriviaServlet extends HttpServlet {
	
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
	  
      RequestDispatcher dipatcher = req.getRequestDispatcher("/triviaPage.jsp"); 
      dipatcher.forward(req, resp);
      
  }
  
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
          throws IOException, ServletException {
  
	  String question = req.getParameter("question");
	  String answer = req.getParameter("answer").trim();
	  
	  Entity trivia = Trivia.createQuestion(question, answer);
	  if(trivia == null) {
		  req.setAttribute("errors", "Trivia already exists");
		  req.getRequestDispatcher("/triviaPage.jsp").forward(req, resp); return;
	  }
	  
	  DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	  datastore.put(trivia);
	  resp.sendRedirect("/triviaPage.jsp");
	}
}


