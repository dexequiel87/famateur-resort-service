<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%-- Tag libraries for Expression language support --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%-- Tag libraries of Spring MVC --%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib  uri="http://www.springframework.org/tags" prefix="spring" %>

<sec:authorize var="isLogged" access="isAuthenticated()"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Famateur</title>
</head>
<body>
	<h1>Home</h1>
	<c:if test="${isLogged}">
		<a href="/logout">Log out</a>
	</c:if>
</body>
</html>