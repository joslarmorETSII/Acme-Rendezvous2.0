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

<jstl:if test="${pageContext.response.locale.language == 'es' }">
    <jstl:set value="{0,date,dd/MM/yyyy}" var="formatDate"/>
</jstl:if>

<jstl:if test="${pageContext.response.locale.language == 'en' }">
    <jstl:set value="{0,date,yyyy/MM/dd}" var="formatDate"/>
</jstl:if>

<display:table id="user" name="user" requestURI="${requestURI}"
               pagesize="5">

    <acme:column code="user.name" value="${user.name}" />
    <acme:column code="user.surname" value="${user.surname}" />
    <acme:column code="user.email" value="${user.email}"/>
    <acme:column code="user.phone" value="${user.phone}"/>
    <acme:column code="user.postalAddresses" value="${user.postalAddresses}"/>

    <spring:message var="birthday" code="user.birthday"/>
    <spring:message var="formatDate" code="event.format.date"/>
    <display:column property="birthday" title="${birthday}" format="${formatDate}" sortable="true" />

    <security:authorize access="hasRole('USER')">
        <display:column>
            <acme:button code="user.edit" url="user/editProfile.do?userId=${user.id}" />
        </display:column>
    </security:authorize>

    <display:column>
        <acme:button code="user.rendezvous.list" url="rendezvous/user/list.do?userId=${user.id}" />
    </display:column>

</display:table>

<input type="button" value="<spring:message code="question.cancel" /> " onclick="goBack()">
<script>
    function goBack() {
        window.history.back()
    }
</script>