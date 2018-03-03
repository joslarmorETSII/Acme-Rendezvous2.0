<%--
  Created by IntelliJ IDEA.
  User: yuzi
  Date: 2/19/18
  Time: 11:00 AM
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

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

<display:table name="rendezvous" id="row" pagesize="10" class="displaytag" requestURI="${requestUri}">
    <jstl:set var="testuser" value="${user}"/>
    <jstl:set var="now" value="${now}"/>



    <display:column>
        <security:authorize access="hasRole('USER')">
        <jstl:if test="${row.finalMode == false && row.creator eq testuser && row.deleted ne true}">
            <acme:button url="rendezvous/user/edit.do?rendezvousId=${row.id}" code="rendezvous.edit" />
        </jstl:if>
    </security:authorize>
    </display:column>
    <acme:column code="rendezvous.creator" value="${row.creator.name} " />
    <acme:column code="rendezvous.name" value="${row.name}"/>
    <acme:column code="rendezvous.description" value="${row.description}"/>

    <spring:message var="moment" code="rendezvous.moment"/>
    <spring:message var="formatDate" code="event.format.date"/>
    <display:column property="moment" title="${moment}" format="${formatDate}" sortable="true" />

    <security:authorize access="hasRole('USER')">
    <display:column>
        <jstl:set var="contains" value="false" />
        <jstl:forEach var="item" items="${row.participated}">
            <jstl:if test="${item.attendant == testuser}">
                <jstl:set var="contains" value="true"/>
            </jstl:if>
        </jstl:forEach>
        <jstl:if test="${contains eq false && row.deleted ne true && row.moment ge now && row.finalMode eq false}">
            <jstl:if test="${row.forAdults eq false}">
                <acme:button url="participate/user/create.do?rendezvousId=${row.id}" code="rendezvous.participate"/>
            </jstl:if>
            <jstl:if test="${row.forAdults eq true && mayor18 eq true}">
            <acme:button url="participate/user/create.do?rendezvousId=${row.id}" code="rendezvous.participate"/>
            </jstl:if>
        <jstl:if test="${row.forAdults eq true && mayor18 eq false }">
            <spring:message code="rendezvous.mayor18" var="mayor"/><jstl:out value="${mayor}"/>
        </jstl:if>
        </jstl:if>
        <jstl:if test="${row.deleted eq true && row.moment ge now}">
            <spring:message code="rendezvous.deleted" var="delet"/><jstl:out value="${delet}"/>
        </jstl:if>

        <jstl:if test="${contains eq true  && row.deleted ne true && row.creator ne testuser && row.moment ge now }">
            <acme:button url="participate/user/${cancelUri}.do?rendezvousId=${row.id}" code="rendezvous.cancelparticipate"/>
        </jstl:if>
        <jstl:if test="${row.moment lt now }">
            <spring:message code="rendezvous.passed" var="passed"/><jstl:out value="${passed}"/>
        </jstl:if>

    </display:column>
    </security:authorize>

    <security:authorize access="hasRole('USER')">
    <display:column >
        <jstl:if test="${contains eq true && row.deleted ne true  }">
             <acme:button url="comment/user/create.do?rendezvousId=${row.id}" code="comment.rendezvous.create"/>
        </jstl:if>
    </display:column>
    </security:authorize>

    <display:column >
        <acme:button url="rendezvous/display.do?rendezvousId=${row.id}" code="rendezvous.display"/>
    </display:column>


    <display:column >
    <jstl:if test="${row.forAdults eq true }">
        <spring:message code="rendezvous.forAdults.flag" var="forAdults"/><jstl:out value="${forAdults}"/>
    </jstl:if>
    </display:column>

    <security:authorize access="hasRole('USER')">
    <display:column>
        <jstl:if test="${row.creator eq user}">
            <acme:button code="rendezvous.associate" url="rendezvous/user/associate.do?rendezvousId=${row.id}"/>
        </jstl:if>
    </display:column>
    </security:authorize>

    <security:authorize access="isAuthenticated()">
    <jstl:if test="${row.creator eq user}">
    <display:column >
        <acme:button url="question/user/list.do?rendezvousId=${row.id}" code="rendezvous.question"/>
    </display:column>
    </jstl:if>
    </security:authorize>


</display:table>

<security:authorize access="hasRole('USER')">
<acme:button code="rendezvous.create" url="rendezvous/user/create.do"/>
</security:authorize>

<security:authorize access="isAnonymous()">

    <input type="button" value="<spring:message code="question.cancel" /> " onclick="goBack()">

</security:authorize>

<script>
    function goBack() {
        window.history.back()
    }
</script>


