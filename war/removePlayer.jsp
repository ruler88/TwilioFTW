<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <jsp:include page="include/headNav.jsp" />

    <br><br>
    <form class="form-horizontal" role="form" action="/removePlayer" method="post">
      <div class="form-group">
        <label for="name" class="col-lg-2 control-label">Name</label>
        <div class="col-lg-10">
          <input name="name" type="text" class="form-control" maxlength="50" pattern="\w+" title="any non-empty name" placeholder="ex. Joe &quot;sexy butt&quot; Kawasaki" required>
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
      <div class="container">
        <c:if test="${not empty errors}">
            <div class="alert alert-danger alert-pad">\
            ${errors}
            </div>
        </c:if>
        <c:if test="${not empty success}">
            <div class="alert alert-success alert-pad">\
            ${success}
            </div>
        </c:if>
      </div>
      
    </form>

    <div class="container">
      <span class="label label-warning alert-pad">Warning</span>
      <div class="alert alert-warning alert-pad">
        <p>Please don't remove other players!!!!</p>
      </div>
    </div>
    
  </body>
</html>
