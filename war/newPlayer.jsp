<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <jsp:include page="include/headNav.jsp" />

    <br><br>
    <form class="form-horizontal" role="form" action="/addPlayer" method="post">
      <div class="form-group">
        <label for="name" class="col-lg-2 control-label">Name</label>
        <div class="col-lg-10">
          <input name="name" type="text" class="form-control" maxlength="50" title="any non-empty name" placeholder="ex. Joe &quot;sexy butt&quot; Kawasaki" required>
        </div>
      </div>
      <div class="form-group">
        <label for="number" class="col-lg-2 control-label">Number</label>
        <div class="col-lg-10">
          <input name="number" type="text" class="form-control" maxlength="10" pattern="\d{10}" title="10 digit phone number" placeholder="ex. 6319092834" required>
        </div>
      </div>
      <div class="form-group">
        <div class="col-lg-offset-2 col-lg-10">
          <button type="submit" value="Add" class="btn btn-default">Submit</button>
        </div>
      </div>
      <c:if test="${not empty errors}">
        <div class="container">
          <div class="alert alert-danger alert-pad">
          <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
          Phone number is already in use
          </div>
        </div>
      </c:if>
      
    </form>

    
  </body>
</html>
