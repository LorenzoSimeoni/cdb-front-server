<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="UTF-8">
<link href="<spring:url value="/static/css/bootstrap.min.css" />" rel="stylesheet" media="screen">
<link href="<spring:url value="/static/css/font-awesome.css" />" rel="stylesheet" media="screen">
<link href="<spring:url value="/static/css/main.css" />" rel="stylesheet" media="screen">
<title>Insert title here</title>
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="Computer"> Application - Computer
				Database </a>
			<div class="pull-right" role="group">
				<a class="navbar-brand" href="logout"> Logout </a>
			</div>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<h1 id="homeTitle">
				<c:out value="MENU" />
			</h1>

			<table class="table table-striped table-bordered">
				<tbody id="results">
					<tr>
						<td><a href="#">Registration page</a></td>
					<tr>
						<td><a href="Computer">Computer page</a></td>
					</tr>
					<tr>
						<td><a href="Computer/add">Add computer page</a></td>
					</tr>
					<tr>
						<td><a href="Computer/edit">Edit computer page</a></td>
					</tr>
				</tbody>
			</table>
		</div>
	</section>


	<script src="<spring:url value="/static/js/jquery.min.js" />"></script>
	<script src="<spring:url value="/static/js/bootstrap.min.js" />"></script>
	<script src="<spring:url value="/static/js/dashboard.js" />"></script>
</body>
</html>