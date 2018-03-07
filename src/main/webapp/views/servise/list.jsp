<%--
  Created by IntelliJ IDEA.
  User: Félix
  Date: 17/02/2018
  Time: 18:51
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<display:table id="servise" name="servises" requestURI="${requestURI}"
               pagesize="5">

    <acme:column code="servise.name" value="${servise.name}" />

    <acme:column code="servise.description" value="${servise.description}"/>
    <acme:column code="servise.picture" value="${servise.picture}"/>
    <acme:column code="service.assigned" value ="${servise.assigned}"/>
    <acme:column code="service.appropiate" value ="${servise.appropiate}"/>

    <security:authorize access="hasRole('MANAGER')">
        <display:column>
            <acme:button code="servise.edit" url="servise/manager/edit.do?serviseId=${servise.id}" />
        </display:column>
    </security:authorize>

    <security:authorize access="hasRole('MANAGER')">
        <acme:columnButton url="servise/manager/edit.do?serviseId=${servise.id}" codeButton="button.delete" />
    </security:authorize>

    <security:authorize access="hasRole('ADMINISTRATOR')">
        <jstl:if test="${servise.inappropriate eq false}">
            <acme:button url="servise/administrator/edit.do?serviseId=${servise.id}" code="servise.inappropriate"/>
        </jstl:if>
    </security:authorize>

</display:table>

<security:authorize access="hasRole('USER')">

    <acme:button code="button.create" url="requestt/user/create.do" />

</security:authorize>
<input type="button" value="<spring:message code="button.cancel" /> " onclick="goBack()">

<script>
    function goBack() {
        window.history.back()
    }
</script>