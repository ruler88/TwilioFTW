package com.kai.twilio.main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kai.twilio.main.DB.Trivia;
import com.kai.twilio.main.DB.User;


@SuppressWarnings("serial")
public class DailyUpdateServlet extends HttpServlet {
	
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
	  Trivia.updateCurrentTrivia();
	  User.increasePoint("+16313779060");
	  //send out text here!
  }
	
	
}


