<%--
 * action-1.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<jstl:if test="${pageContext.response.locale.language == 'es' }">
    <jstl:set value="{0,date,dd/MM/yyyy HH:mm}" var="formatDate"/>
</jstl:if>

<jstl:if test="${pageContext.response.locale.language == 'en' }">
    <jstl:set value="{0,date,yyyy/MM/dd HH:mm}" var="formatDate"/>
</jstl:if>

<div id="banner">
    <jstl:if test="${testPicture eq false}">
        <img src="${rendezvous.picture}" width="500px" height="100%" />
    </jstl:if>
    <jstl:if test="${testPicture eq true}">
        <img src="images/no_image.png" width="300px" height="100%" />
    </jstl:if>
</div>
<br>

<h3><spring:message code="rendezvous.name" />:&nbsp;<jstl:out value="${rendezvous.name}"/></h3>
<h3><spring:message code="rendezvous.description"/>:&nbsp;<jstl:out value="${rendezvous.description}"/></h3>

<jstl:if test="${pageContext.response.locale.language == 'es'}">
    <h3><p><b><spring:message code="rendezvous.moment" />:&nbsp;${momentEs} </b></p></h3>
</jstl:if>

<jstl:if test="${pageContext.response.locale.language == 'en'}">
    <h3><p><b><spring:message code="rendezvous.moment" />:&nbsp;${momentEn} </b></p></h3>
</jstl:if>

<h3><spring:message code="rendezvous.location.longitude"/>:&nbsp;<jstl:out value="${rendezvous.location.longitude}"/></h3>
<h3><spring:message code="rendezvous.location.latitude"/>:&nbsp;<jstl:out value="${rendezvous.location.latitude}"/></h3>
<h3><spring:message code="rendezvous.creator"/>:&nbsp;<jstl:out value="${rendezvous.creator.name}"/></h3>
<h3><spring:message code="rendezvous.forAdults"/>:&nbsp;<jstl:out value="${rendezvous.forAdults}" /></h3>

<a href="announcement/list.do?rendezvousId=${rendezvous.id}"><spring:message code="rendezvous.listAnnouncement"/></a>
<br/>

<a href="answer/list.do?rendezvousId=${rendezvous.id}"><spring:message code="rendezvous.listParticipated"/></a>
<br/>

<security:authorize access="hasRole('USER')">
<div id="comment-box">

    <spring:message code="rendezvous.comments" var="comments2"/>
    <h2><jstl:out value="${comments2}"/></h2>

    <display:table pagesize="2" class="displaytag" keepStatus="true" name="rendezvous.comments" requestURI="${requestUri}" id="row">

        <spring:message code="comment.user" var="user"/>
        <display:column property="user.name" title="${user}"/>
        <spring:message code="comment.text" var="text"/>
        <display:column property="text" title="${text}" />

    </display:table>
</security:authorize>

<spring:message code="rendezvous.associated" var="associated"/>
<h2><jstl:out value="${associated}"/></h2>
    <display:table pagesize="5" class="displaytag" keepStatus="true" name="rendezvous.associated" requestURI="${requestUri}" id="row">

        <spring:message code="rendezvous.name" var="name"/>
        <display:column property="name" title="${name}"/>
        <spring:message code="rendezvous.description" var="description"/>
        <display:column property="description" title="${description}"/>
        <spring:message var="moment" code="rendezvous.moment"/>
        <spring:message var="formatDate" code="event.format.date"/>
        <display:column property="moment" title="${moment}" format="${formatDate}" sortable="true" />¡
        <spring:message code="rendezvous.picture" var="pic"/>
        <display:column title="${pic}"><img src="${row.picture}" width="130" height="100"></display:column>
        <display:column >
            <acme:button url="rendezvous/display.do?rendezvousId=${row.id}" code="rendezvous.display"/>
        </display:column>

 </display:table>



<br>
<input type="button" name="cancel" value="<spring:message code="rendezvous.cancel" />"
       onclick="javascript: relativeRedir('${cancelURI}');" />