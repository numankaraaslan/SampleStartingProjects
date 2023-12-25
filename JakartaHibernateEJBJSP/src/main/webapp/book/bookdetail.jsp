<%@ page isELIgnored="false" language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Jakarta JSP demo</title>
</head>
<body>
	<a href="../index.jsp">home</a>
	<a href="books">books</a>
	<a href="addbook">add</a>
	<br />
	<label>Name: </label>
	<input type="text" name="name" value="${book.name}"/>
	<br />
	<label>Year: </label>
	<input type="text" name="year" value="${book.year}"/>
	<br />
	<label>Author: </label>
	<input type="text" name="author" value="${book.author}"/>
</body>
</html>