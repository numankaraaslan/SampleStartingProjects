<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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
	<form action="addbook" method="post">
		<label>Name: </label> <input type="text" name="name" />
		<br />
		<label>Year: </label> <input type="text" name="year" />
		<br />
		<label>Author: </label> <input type="text" name="author" />
		<br />
		<input type="submit" value="add" />
	</form>
</body>
</html>