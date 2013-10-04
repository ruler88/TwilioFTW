<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="com.kai.twilio.main.DB.User" %>
<%@ page import="com.kai.twilio.main.DB.Trivia" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="java.text.SimpleDateFormat"%>


<html lang="en">
    
    <jsp:include page="include/headNav.jsp" />

    <div class="jumbotron">
      <div class="container">
        <h1>Welcome to Daily Trivia!!</h1>
        <p>Participants in daily trivia will receive a trivia question via text at noon (PST) everyday, and the first person to answer the question correctly will score a point!</p>
        <p><a class="btn btn-primary btn-lg" href="/addPlayer">Sign me up!</a></p>
      </div>
    </div>


    <div class="container">  
      <h2>Example of creating Modal with Twitter Bootstrap</h2>  
      <div class="well">  
      <a href="#" id="example" class="btn btn-danger" rel="popover" data-content="It's so simple to create a tooltop for my website!" data-original-title="Twitter Bootstrap Popover">hover for popover</a>  
      </div>  
     </div> 


    <div class="container">
        <div class="row">
          <div class="col-lg-12">
              <%
              Entity currentTrivia = Trivia.getCurrentTrivia();
              pageContext.setAttribute("current_trivia_question", currentTrivia.getProperty("question"));
              %>

            <h1>${fn:escapeXml(current_trivia_question)}</h1>
          </div>
        </div>

        <div class="row">
          <div class="col-lg-12">
            <h1>Leaderboard</h1>
          </div>
        </div>

      <table class="table table-hover">
          <thead>  
            <tr>  
              <th>Name</th>  
              <th>Join Date</th>  
              <th>Score</th> 
            </tr>  
          </thead>  
          <tbody>  
              <%
                  Iterable<Entity> allUsers = User.getAllUsersByScore();
                  for(Entity user1 : allUsers) {
                    pageContext.setAttribute("user_name", user1.getProperty("name"));
                    pageContext.setAttribute("user_date", new SimpleDateFormat("yyyy-MM-dd").format(user1.getProperty("date")));
                    pageContext.setAttribute("user_score", user1.getProperty("score"));
                    %>
                    <tr>  
                      <td>${fn:escapeXml(user_name)}</td>  
                      <td>${fn:escapeXml(user_date)} </td>  
                      <td>${fn:escapeXml(user_score)}</td>  
                      
                    </tr>
                    <%
                  }
              %>
             
          </tbody>
      </table>
    
    

    </div><!-- /.container -->
  </body>
</html>
