<%--
  Created by IntelliJ IDEA.
  User: Felix
  Date: 2/17/18
  Time: 11:16 AM
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1" %>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="${actionUri}" modelAttribute="announcement">

    <form:hidden path="id"/>
    <form:hidden path="version"/>
    <form:hidden path="moment"/>

    <acme:textbox path="title" code="announcement.title"/>

    <acme:textarea path="description" code="announcement.description"/>

    <acme:select path="rendezvous" code="announcement.rendezvous" items="${myRendezvouses}" itemLabel="name"/>

    <security:authorize access="hasRole('USER')">
        <acme:submit name="save" code="announcement.save"/>
    </security:authorize>

    <security:authorize access="hasRole('ADMINISTRATOR')">
        <acme:submit name="delete" code="announcement.delete"/>
    </security:authorize>

    <acme:cancel code="announcement.cancel" url="${cancelUri}"/>

</form:form>
