<%--
  Created by IntelliJ IDEA.
  User: yuzi
  Date: 2/17/18
  Time: 11:16 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1"%>
--%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<form:form action="question/user/edit.do" modelAttribute="question">

    <form:hidden path="id"/>
    <form:hidden path="version"/>
    <form:hidden path="answers"/>

    <acme:textbox path="text" code="question.text"/>

    <acme:select path="rendezvous" code="question.rendezvous" items="${rendezvousCollection}" itemLabel="name"/>

    <acme:submit name="save" code="question.save"/>
    <jstl:if test="${question.id!=0}">
        <acme:submit name="delete" code="question.delete"/>
    </jstl:if>
    <acme:cancel code="question.cancel" url="${cancelUri}"/>

</form:form>

