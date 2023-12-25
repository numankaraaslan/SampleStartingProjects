<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Spring JSP demo</title>
</head>
<body>
	<a href="books">books</a>
	<a>add book</a>
	<br />
	<form:form action="addbook" method="post" modelAttribute="book">
		<form:label path="name">Name: </form:label>
		<form:input type="text" path="name"></form:input>
		<br />
		<form:label path="year">Year: </form:label>
		<form:input type="text" path="year"></form:input>
		<br />
		<form:label path="author">Author: </form:label>
		<form:input path="author"></form:input>
		<br />
		<input type="submit" value="add" />
	</form:form>
</body>
</html>