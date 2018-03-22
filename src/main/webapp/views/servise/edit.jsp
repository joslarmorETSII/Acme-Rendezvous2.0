<%--
  Created by IntelliJ IDEA.
  User: Felix
  Date: 2/17/18
  Time: 11:16 AM
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<%--@elvariable id="rendezvous" type=""--%>
<form:form action="${actionUri}" modelAttribute="servise">

    <form:hidden path="id"/>
    <form:hidden path="version"/>

    <form:hidden path="inappropriate"/>
    <form:hidden path="assigned"/>
    <form:hidden path="manager"/>
    <form:hidden path="rendezvouses"/>


    <acme:textbox path="name" code="servise.name"/>
    <acme:textarea path="description" code="servise.description"/>
    <acme:textbox path="picture" code="servise.picture"/>
    <acme:select path="category" code="service.category" items="${categories}" itemLabel="name"/>

    <security:authorize access="hasRole('MANAGER')">
        <acme:submit name="save" code="button.save"/>
        <jstl:if test="${servise.assigned eq false && servise.id!=0}">
            <acme:submit name="delete" code="button.delete" />
        </jstl:if>
    </security:authorize>


    <acme:cancel code="button.cancel" url="${cancelUri}"/>

</form:form>
