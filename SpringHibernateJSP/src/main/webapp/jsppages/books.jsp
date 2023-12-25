<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Spring JSP demo</title>
</head>
<body>
	<a>books</a>
	<a href="addbook">add book</a>
	<br/>
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
					<td><a href="bookdetail?id=${book.id}">Details</a></td>
					<td><a href="deletebook?id=${book.id}">Delete</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>