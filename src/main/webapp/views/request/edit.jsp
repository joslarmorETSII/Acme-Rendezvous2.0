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


<form:form action="requestt/user/edit.do" modelAttribute="requestt">

    <form:hidden path="id"/>
    <form:hidden path="version"/>


    <acme:select path="servise" code="requestt.servise" items="${servises}" itemLabel="name"/>
    <acme:select path="rendezvous" code="requestt.rendezvous" items="${userRendezvous}" itemLabel="name"/>

    <acme:textarea path="comment" code="requestt.comment"/>

    <fieldset>
        <legend><b><spring:message code="creditCard.legend" var="legend"/><jstl:out value="${legend}"/>
            :&nbsp;</b></legend>

        <acme:textbox path="${user.creditCard.holder}" code="creditCard.holder"/>
        <acme:textbox path="${user.creditCard.brand}" code="creditCard.brand"/>
        <acme:textbox path="${user.creditCard.number}" code="creditCard.number"/>
        <acme:textbox path="${user.creditCard.expirationMonth}" code="creditCard.expirationMonth" placeHolder="XX"/>
        <acme:textbox path="${user.creditCard.expirationYear}" code="creditCard.expirationYear" placeHolder="XXXX"/>
        <acme:textbox path="${user.creditCard.cvv}" code="creditCard.cvv"/>

    </fieldset>

    <acme:submit name="save" code="button.save"/>
  <%--  <jstl:if test="${request.id!=0}">
        <acme:submit name="delete" code="request.delete"/>
    </jstl:if>--%>
    <acme:cancel code="button.cancel" url="servises/list.do"/>

</form:form>

