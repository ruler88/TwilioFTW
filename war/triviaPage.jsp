<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="com.kai.twilio.main.DB.Trivia" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>

<html>
  <jsp:include page="include/headNav.jsp" />

    <br><br>
    <form class="form-horizontal" role="form" action="/setTrivia" method="post">
      <div class="form-group">
        <label for="name" class="col-lg-2 control-label">Question</label>
        <div class="col-lg-10">
          <input name="question" type="text" class="form-control" pattern="\w+*" title="Question" placeholder="ex. Bob" required>
        </div>
      </div>
      <div class="form-group">
        <label for="number" class="col-lg-2 control-label">Answer</label>
        <div class="col-lg-10">
          <input name="answer" type="text" class="form-control" pattern="\w+*"  title="Answer" placeholder="ex. Dole" required>
        </div>
      </div>
      <div class="form-group">
        <div class="col-lg-offset-2 col-lg-10">
          <button type="submit" value="Add" class="btn btn-default">Submit</button>
        </div>
      </div>
      <c:if test="${not empty errors}">
          <div class="alert alert-danger alert-pad">
          <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
          Error
          </div>
      </c:if>
      
    </form>
    <%
        Iterable<Entity> allQuestions = Trivia.getAllQuestions();
        for(Entity q : allQuestions) {
          pageContext.setAttribute("r_question", q.getProperty("question"));
          pageContext.setAttribute("r_answer", q.getProperty("answer"));
          %>
          <p><b>${fn:escapeXml(r_question)}</b> : <i>${fn:escapeXml(r_answer)}</i></p>
          <%
        }
    %>

  </body>
</html>
