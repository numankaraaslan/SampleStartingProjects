<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Spring JSP demo</title>
</head>
<body>
	<a href="books">books</a>
	<a href="addbook">add book</a>
	<br />
	<label>Name: ${book.name}</label>
	<br />
	<label>Year: ${book.year}</label>
	<br />
	<label>Author: ${book.author}</label>
</body>
</html>