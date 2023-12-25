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
	<table>
		<thead>
			<tr>
				<th>Name</th>
				<th>Year</th>
				<th>Author</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${books}" var="book">
				<tr>
					<td>${book.name}</td>
					<td>${book.year}</td>
					<td>${book.author}</td>
					<td><a href="bookdetail?id=${book.id}">detail</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>