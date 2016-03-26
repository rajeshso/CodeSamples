<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Spittles</title>
</head>
<body>
Spittles
    <div class="listTitle">
      <h1>Recent Spittles</h1>
      <ul class="spittleList">
        <c:forEach items="${spittleList}" var="spittle" >
          <li id="spittle_<c:out value="spittle.id"/>">
            <div class="spittleMessage"><c:out value="${spittle.message}" /></div>
            <div>
              <span class="spittleTime"><c:out value="${spittle.time}" /></span>
            </div>
          </li>
        </c:forEach>
      </ul>
    </div>
</body>
</html>