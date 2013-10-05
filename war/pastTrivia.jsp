<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="com.kai.twilio.main.DB.Trivia" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="java.text.SimpleDateFormat"%>

<html>
  <jsp:include page="include/headNav.jsp" />
  <div class="container">
	    <table class="table table-striped">
	  		<thead>  
	          <tr>  
	            <th>Question</th>  
	            <th>Answer</th>  
	            <th>Date</th>
	            <th>Solved By</th> 
	          </tr>  
	        </thead>
	        <tbody>  
	            <%
	                Iterable<Entity> allPastQuestions = Trivia.getAllPastQuestions();
	                for(Entity q : allPastQuestions) {
			          pageContext.setAttribute("r_question", q.getProperty("question"));
			          pageContext.setAttribute("r_answer", q.getProperty("answer"));
			          pageContext.setAttribute("r_date", new SimpleDateFormat("yyyy-MM-dd").format(q.getProperty("date")));
			          pageContext.setAttribute("r_solvedBy", q.getProperty("solvedBy"));
			          %>
			          <tr>  
	                    <td>${fn:escapeXml(r_question)}</td>  
	                    <td>${fn:escapeXml(r_answer)} </td>  
	                    <td>${fn:escapeXml(r_date)}</td>  
	                    <td>${fn:escapeXml(r_solvedBy)}</td>
	                  </tr>
			          <%
			        }

	            %>
	           
	        </tbody>
		</table>
	</div>
  </body>
</html>