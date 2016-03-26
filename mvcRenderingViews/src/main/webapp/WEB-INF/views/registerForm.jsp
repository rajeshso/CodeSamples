<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<sf:form method="POST" commandName="spitter">
		FirstName	:	<sf:input path="firstname"/> </br>
		<sf:errors path="firstname" cssClass="error"></sf:errors>
		LastName	:	<sf:input path="lastname"/></br>
		UserName	:	<sf:input path="username"/></br>
		Password	:	<sf:input path="password" /></br>	
		<input type="submit" value="register">
	</sf:form>
</body>
</html>