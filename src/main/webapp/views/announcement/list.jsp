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
    <jstl:set value="{0,date,dd/MM/yyyy HH:mm}" var="formatDate"/>
</jstl:if>

<jstl:if test="${pageContext.response.locale.language == 'en' }">
    <jstl:set value="{0,date,yyyy/MM/dd HH:mm}" var="formatDate"/>
</jstl:if>



<display:table id="announcement" name="announcements" requestURI="${requestURI}"
               pagesize="5">

    <acme:column code="announcement.title" value="${announcement.title}" />

    <spring:message var="moment" code="announcement.moment"/>
    <spring:message var="formatDate" code="event.format.date"/>
    <display:column property="moment" title="${moment}" format="${formatDate}" sortable="true" />

    <acme:column code="announcement.description" value="${announcement.description}"/>
    <acme:column code="announcement.rendezvous.name" value="${announcement.rendezvous.name}"/>

    <security:authorize access="hasRole('USER')">
        <jstl:if test="${announcement.rendezvous.creator.id eq user.id}">
        <display:column>
            <acme:button code="announcement.edit" url="announcement/user/edit.do?announcementId=${announcement.id}" />
        </display:column>
        </jstl:if>
</security:authorize>

    <security:authorize access="hasRole('ADMINISTRATOR')">
        <acme:columnButton url="announcement/administrator/edit.do?announcementId=${announcement.id}" codeButton="announcement.delete" />
    </security:authorize>

</display:table>

<security:authorize access="hasRole('USER')">

    <acme:button code="announcement.create" url="announcement/user/create.do" />

</security:authorize>
<input type="button" value="<spring:message code="question.cancel" /> " onclick="goBack()">

<script>
    function goBack() {
        window.history.back()
    }
</script>