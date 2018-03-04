<%--
  Created by IntelliJ IDEA.
  User: yuzi
  Date: 3/03/18
  Time: 11:16 AM
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<form:form action="request/user/edit.do" modelAttribute="request">

    <form:hidden path="id"/>
    <form:hidden path="version"/>
    <form:hidden path="user"/>


    <acme:select path="services" code="request.service" items="${serviceCollection}" itemLabel="name"/>
    <acme:textarea path="comment" code="request.comment"/>

    <fieldset>
        <legend><b><spring:message code="creditCard.legend" var="legend"/><jstl:out value="${legend}"/>
            :&nbsp;</b></legend>

        <acme:textbox path="creditCard.holder" code="creditCard.holder"/>
        <acme:textbox path="creditCard.brand" code="creditCard.brand"/>
        <acme:textbox path="creditCard.number" code="creditCard.number"/>
        <acme:textbox path="creditCard.expirationMonth" code="creditCard.expirationMonth" placeHolder="XX"/>
        <acme:textbox path="creditCard.expirationYear" code="creditCard.expirationYear" placeHolder=""/>
        <acme:textbox path="creditCard.ccv" code="creditCard.ccv"/>

    </fieldset>

    <acme:submit name="save" code="request.save"/>
  <%--  <jstl:if test="${request.id!=0}">
        <acme:submit name="delete" code="request.delete"/>
    </jstl:if>--%>
    <acme:cancel code="request.cancel" url="${cancelUri}"/>

</form:form>

