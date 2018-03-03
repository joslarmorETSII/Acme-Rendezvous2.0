<%--
 * action-1.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="administrator/edit.do" modelAttribute="administrator" onsubmit="return validatePhone()">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="userAccount" />
	
	<b><form:label path="name"><spring:message code="administrator.name"/></form:label>:&nbsp;</b>
	<form:input path="name"/>
	<form:errors path="name" cssClass="error"/>
	<br/>
	
	<b><form:label path="surname"><spring:message code="administrator.surname"/></form:label>:&nbsp;</b>
	<form:input path="surname"/>
	<form:errors path="surname" cssClass="error"/>
	<br/>
	
	<b><form:label path="email"><spring:message code="administrator.email"/></form:label>:&nbsp;</b>
	<form:input path="email"/>
	<form:errors path="email" cssClass="error"/>
	<br/>
	
	<b><form:label path="postalAddresses"><spring:message code="administrator.postalAddresses"/></form:label>:&nbsp;</b>
	<form:input path="postalAddresses"/>
	<form:errors path="postalAddresses" cssClass="error"/>
	<br/>

    <form:label path="phone"><spring:message code="user.phone" /></form:label>:&nbsp;
    <form:input id="phoneId" path="phone" placeholder="+34 611222333" />
    <form:errors cssClass="error" path="phone" />
    <br />

	<acme:submit name="save" code="administrator.save"/>

	<acme:cancel code="administrator.cancel" url="welcome/index.do"/>

</form:form>

<script>

function validatePhone() {
 <spring:message code="administrator.phone.ask" var="ask"/>
    var x = document.getElementById("phoneId").value;
    var patt = new RegExp("^\\+([3][4])( )(\\d{9})|()$");
    if(x != "" && !patt.test(x)){
        return confirm('<jstl:out value="${ask}"/>');
    } 
}

</script>

