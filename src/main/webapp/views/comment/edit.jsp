<%--
  Created by IntelliJ IDEA.
  User: khawla
  Date: 17/02/2018
  Time: 12:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<form:form action="${actionURI}" modelAttribute="comment">

    <form:hidden path="id"/>
    <form:hidden path="version"/>

    <form:hidden path="user"/>
    <form:hidden path="rendezvous"/>
    <form:hidden path="childrenComments"/>
    <form:hidden path="parentComment"/>
    <form:hidden path="moment" />

    <acme:textbox path="text" code="comment.text"/>
    <acme:textbox path="picture" code="comment.picture"/>

    <acme:submit name="save" code="comment.save"/>

    <acme:cancel code="comment.cancel" url="comment/user/list.do"/>



</form:form>