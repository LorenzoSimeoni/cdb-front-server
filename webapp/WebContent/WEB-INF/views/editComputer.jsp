<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
	<head>
	<title>Computer Database</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<!-- Bootstrap -->
	<link href="<spring:url value="/static/css/bootstrap.min.css" />" rel="stylesheet" media="screen">
	<link href="<spring:url value="/static/css/font-awesome.css" />" rel="stylesheet" media="screen">
	<link href="<spring:url value="/static/css/main.css" />" rel="stylesheet" media="screen">
	</head>
	<body>
		<header class="navbar navbar-inverse navbar-fixed-top">
			<div class="container">
				<a class="navbar-brand" href="/webapp/Computer"> Application
					- Computer Database </a>
			</div>
		</header>
		<section id="main">
			<div class="container">
				<div class="row">
					<div class="col-xs-8 col-xs-offset-2 box">
						<div class="label label-default pull-right">id: ${ id }</div>
						<h1><spring:message code="lang.editComputer" /></h1>
						<form:form method="POST" modelAttribute="computerDTO" action="">
							<input type="hidden" value="${ id }" id="id" name="id" />
							<fieldset>
								<div class="form-group">
									<form:label path="name">
										<spring:message code="lang.computerName" />
									</form:label> 
									<form:input path="name" type="text" class="form-control required" id="computerName"
										name="computerName" value="${ computerDTO.name }"/>
									<form:errors path="name" cssClass="error"/>									
								</div>
								<div class="form-group">
									<form:label path="introduced">
										<spring:message code="lang.introduced" />
									</form:label> 
									<form:input path="introduced"
										type="date" class="form-control" id="introduced"
										name="introduced" value="${ computerDTO.introduced }"/>
								</div>
								<div class="form-group">
									<form:label path="discontinued">
										<spring:message code="lang.discontinued" />
									</form:label> 
									<form:input path="discontinued"
										type="date" class="form-control" id="discontinued"
										name="discontinued" value="${ computerDTO.discontinued }"/>
								</div>
								<div class="form-group">
									<label for="companyId">
										<spring:message code="lang.company" />
									</label> 
									<form:select path="companyId"
										class="form-control" id="companyId" name="companyId">
										<option value=""></option>
										<c:forEach items="${listCompany}" var="company">
											<c:choose>
												<c:when test="${company.id == computerDTO.companyId}">
													<option selected="${computerDTO.companyId}">${computerDTO.companyId}. ${company.name}</option>
												</c:when>
												<c:otherwise>
													<option value="${company.id}">${company.id}. ${company.name}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</form:select>
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								</div>
							</fieldset>
							<div class="actions pull-right">
								<input type="submit" value="<spring:message code="lang.edit" />" class="btn btn-primary">
								<spring:message code="lang.or" /> <a href="/webapp/Computer" class="btn btn-default">
								<spring:message code="lang.cancel" /></a>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</section>
	</body>
	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<div class="btn-group btn-group-sm pull-left" role="group">
					<button type="button" class="btn btn-default"
						onclick="location.replace('?lang=fr&id=${id}')">Français</button>
					<button type="button" class="btn btn-default"
						onclick="location.replace('?lang=en&id=${id}')">English</button>
			</div>
		</div>
	</footer>
<script src="<spring:url value="/static/js/jquery.min.js" />"></script>
<script type="text/javascript" charset="utf-8">
$('form').submit(function (event) {
    var name = $.trim($('#computerName').val());
    if (!name) {
        alert('You have to put a name on this computer');
        event.preventDefault(); 
    }
    
    var introduced = $.trim($('#introduced').val());
    var discontinued = $.trim($('#discontinued').val());
    
	if ((new Date(introduced).getTime()) > (new Date(discontinued).getTime())) {
		alert('The introduced Date must be before the discontinued Date.');
		e.preventDefault();
	} else if (discontinued && !introduced) {
		alert('Cannot input a discontinued Date without an introduced Date.');
		e.preventDefault();
	}
});
</script>


</html>


