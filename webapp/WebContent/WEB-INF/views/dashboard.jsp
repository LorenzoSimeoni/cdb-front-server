<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="<spring:url value="/static/css/bootstrap.min.css" />" rel="stylesheet" media="screen">
<link href="<spring:url value="/static/css/font-awesome.css" />" rel="stylesheet" media="screen">
<link href="<spring:url value="/static/css/main.css" />" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="Computer"> Application
				- Computer Database </a>
			<div class="pull-right" role="group">
				<a class="navbar-brand" href="logout"> Logout </a>
			</div>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">
				<c:out value="${ sizeComputerFound }" />
				<spring:message code="lang.count" />
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" method="GET" class="form-inline">
						<input type="search" id="searchbox"
							name="search" class="form-control" placeholder="<spring:message code="lang.search"/>" />
						<input type="submit" id="searchsubmit" value="<spring:message code="lang.filter"/>"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="Computer/add">
						<spring:message code="lang.addComputer"/>
					</a> 
					<a class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();">
						<spring:message code="lang.edit"/>
					</a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="" method="POST">
			<input type="hidden" name="selection" value="">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

			<div class="container" style="margin-top: 10px;">
				<table class="table table-striped table-bordered">
					<thead>
						<tr>
							<!-- Variable declarations for passing labels as parameters -->
							<!-- Table header for Computer Name -->

							<th class="editMode" style="width: 60px; height: 22px;"><input
								type="checkbox" id="selectall" /> <span
								style="vertical-align: top;"> - <a href="/webapp/Computer"
									id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
										class="fa fa-trash"></i>
								</a>
							</span></th>
							<th id="columnComputeName">
								<spring:message code="lang.computerName" />	
							</th>
							<th id="columnIntroduced">
								<spring:message code="lang.introduced" />	
							</th>
							<th id="columnDiscontinued">
								<spring:message code="lang.discontinued" />
							</th>
							<th id="columnCompanyName">
								<spring:message code="lang.company" />
							</th>
						</tr>
					</thead>
					<!-- Browse attribute computers -->
					<tbody id="results">
						<c:forEach items="${ listComputer }" var="computer">
							<tr>
								<td class="editMode"><input type="checkbox" name="cb"
									class="cb" value="${ computer.id }"></td>
								<td><a href="Computer/update?id=${ computer.id }"><c:out
											value="${ computer.name }" /></a></td>
								<td><c:out value="${ computer.introduced }" /></td>
								<td><c:out value='${ computer.discontinued }' /></td>
								<td><c:out value='${ computer.companyName }' /></td>
							</tr>
						</c:forEach>

					</tbody>
				</table>
			</div>
		</form>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<div class="btn-group btn-group-sm pull-left" role="group">
				<button type="button" class="btn btn-default"
					onclick="location.replace('?lang=fr')">Français</button>
				<button type="button" class="btn btn-default"
					onclick="location.replace('?lang=en')">English</button>
			</div>

			<ul class="pagination">
				<li><a
					href='?numPage=${pagination.pageNumber-1}'
					aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
				</a></li>
				<c:forEach var="i" begin="${pagination.minNumberPage}" end="${pagination.maxNumberPage}" step="1">
					<li><a
						href='?numPage=${i}'>
						<c:out value="${i}" />
					</a></li>
				</c:forEach>
				<li><a
					href='?numPage=${listComputer.size()!=pagination.offset?pagination.pageNumber:pagination.pageNumber+1}'
					aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
			</ul>

			<div class="btn-group btn-group-sm pull-right" role="group">
				<button type="button" class="btn btn-default"
					onclick="location.replace('?nbElement=10')">10</button>
				<button type="button" class="btn btn-default"
					onclick="location.replace('?nbElement=50')">50</button>
				<button type="button" class="btn btn-default"
					onclick="location.replace('?nbElement=100')">100</button>
			</div>
		</div>
	</footer>
	<script src="<spring:url value="/static/js/jquery.min.js" />"></script>
	<script src="<spring:url value="/static/js/bootstrap.min.js" />"></script>
	<script src="<spring:url value="/static/js/dashboard.js" />"></script>
	<script type="text/javascript">
		document.getElementById("columnComputeName").onclick = function() {
			window.location.href='?order=name';
		}
		document.getElementById("columnIntroduced").onclick = function() {
			window.location.href='?order=introduced';
		}
		document.getElementById("columnDiscontinued").onclick = function() {
			window.location.href='?order=discontinued';
		}
		document.getElementById("columnCompanyName").onclick = function() {
			window.location.href='?order=company';
		}
	</script>
	
</body>
</html>