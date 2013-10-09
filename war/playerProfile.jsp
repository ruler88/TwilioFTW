<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="com.kai.twilio.main.DB.User" %>
<%@ page import="com.kai.twilio.main.DB.Trivia" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.net.URLEncoder"%>

<html>
    <jsp:include page="include/headNav.jsp" />

    <%
    String paramUserName=request.getParameter("userName");
    %>
    <div class="container">
      <ul class="nav nav-pills nav-stacked">
      <%
          Iterable<Entity> allUsers = User.getAllUsersByScore();
          for(Entity user1 : allUsers) {
            String userName = user1.getProperty("name").toString();
            String link = "/playerProfile.jsp?userName=" + URLEncoder.encode(userName);

            if(!userName.equals(paramUserName)) {
            %>
            <li><a href="<%=link%>"><%=userName%></a></li>
            <%
            } else { %>
            <li class="active"><a href="<%=link%>"><%=userName%></a></li>
            <%
          }
        }
      %>
      </ul>
    </div>

    <%
    Iterable<Entity> userResponses = User.getUserResponses(paramUserName);
    if(userResponses!=null && userResponses.iterator().hasNext()) {
    %>
    <br>
    <div class="container">
      <table class="table">
        <thead>  
          <tr>  
            <th>Question</th>  
            <th>Answer</th>  
            <th>Date</th>  
          </tr>  
        </thead>  
        <tbody>  
          <tr>
              <%
                
                for(Entity singleResponse : userResponses) {
                  String question = singleResponse.getProperty("question").toString();
                  String answer = singleResponse.getProperty("answer").toString();
                  String date = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss a").format(singleResponse.getProperty("date"));
                  %>
                  <tr>  
                    <td><%=question%></td>  
                    <td><%=answer%></td>  
                    <td><%=date%></td>
                  </tr>
                  <%
                }
            %>
          </tr>  
        </tbody>
      </table>
    </div>
    <%
    }
    %>

  


    
  </body>
</html>
