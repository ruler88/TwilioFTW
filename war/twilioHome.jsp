<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="com.kai.twilio.main.DB.User" %>
<%@ page import="com.kai.twilio.main.DB.Trivia" %>
<%@ page import="com.kai.twilio.main.DB.Response" %>
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
      <h2>Today's trivia question:</h2>

      <div class="well">
          <%
            Entity currentTrivia = Trivia.getCurrentTrivia();
            pageContext.setAttribute("current_trivia_question", currentTrivia.getProperty("question"));
            String answerString = "";

            if(Trivia.currentTriviaSolvedStatus()) {
              answerString = "Today's trivia has been solved by " + currentTrivia.getProperty("solvedBy") + ", the correct answer is: " + currentTrivia.getProperty("answer");
            } else {
              Iterable<Entity> responses = Response.getAllResponses();
              for(Entity e : responses) {
                answerString += e.getProperty("answer").toString()+", \n";
              }
              if(answerString.isEmpty()) {
                answerString = "No answer yet :(";
              }
            }
            
          %>

          <div class="p2">
            <p>${fn:escapeXml(current_trivia_question)}</p>
          </div>
      <a id="example" class="btn btn-danger" rel="popover" trigger="hover" data-content="<%=answerString%>"}>hover to see other people's answers</a>  
      </div>  
     </div> 


    <div class="container">
        

        <div class="row">
          <div class="col-lg-12">
            <h2>Leaderboard</h2>
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


    <script src="js/tooltip.js" type="text/javascript"></script>
    <script src="js/popover.js" type="text/javascript"></script>

    <script type="text/javascript">
      $(function ()  
      { $("#example").popover({trigger: 'hover'});

      });

    </script>

  </body>
</html>
