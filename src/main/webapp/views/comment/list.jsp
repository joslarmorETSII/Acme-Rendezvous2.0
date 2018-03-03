<%--
  Created by IntelliJ IDEA.
  User: yuzi
  Date: 2/19/18
  Time: 11:00 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<jstl:if test="${pageContext.response.locale.language == 'es' }">
    <jstl:set value="{0,date,dd/MM/yyyy HH:mm}" var="formatDate"/>
</jstl:if>

<jstl:if test="${pageContext.response.locale.language == 'en' }">
    <jstl:set value="{0,date,yyyy/MM/dd HH:mm}" var="formatDate"/>
</jstl:if>

<display:table name="comments" id="row" pagesize="5" class="displaytag" requestURI="${requestURI}">

    <acme:column code="comment.user" value="${row.user.name}" />
    <acme:column code="comment.id" value="${row.id}" sortable="true"/>
    <acme:column code="comment.parentComment" value="${row.parentComment.id}" sortable="true"/>
    <acme:column code="comment.text" value="${row.text}"/>

    <spring:message var="moment" code="comment.moment"/>
    <spring:message var="formatDate" code="event.format.date"/>
    <display:column property="moment" title="${moment}" format="${formatDate}" sortable="true" />

    <acme:column code="comment.picture" value="${row.picture}"/>

    <security:authorize access="hasRole('ADMINISTRATOR')">
        <acme:columnButton url="comment/administrator/edit.do?commentId=${row.id}" codeButton="comment.delete" />
    </security:authorize>

    <security:authorize access="hasRole('USER')">
        <acme:columnButton url="comment/user/edit.do?commentId=${row.id}" codeButton="comment.edit" />
        <acme:columnButton url="comment/user/response.do?commentId=${row.id}" codeButton="comment.response" />
    </security:authorize>



</display:table>

<security:authorize access="hasRole('USER')">
<acme:button code="comment.rendezvous.list" url="rendezvous/listAll.do"/>
</security:authorize>

