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

    <display:table name="rendezvouses" id="row" pagesize="10" class="displaytag" requestURI="/category/listRendezvous.do">

        <jstl:set var="now" value="${now}"/>


        <acme:column code="rendezvous.name" value="${row.name}"/>
        <acme:column code="rendezvous.description" value="${row.description}"/>

        <spring:message var="moment" code="rendezvous.moment"/>
        <spring:message var="formatDate" code="event.format.date"/>
        <display:column property="moment" title="${moment}" format="${formatDate}" sortable="true" />

        <display:column>
            <jstl:set var="contains" value="false" />


            <jstl:if test="${row.deleted eq true && row.moment ge now}">
                <spring:message code="rendezvous.deleted" var="delet"/><jstl:out value="${delet}"/>
            </jstl:if>


            <jstl:if test="${row.moment lt now }">
                <spring:message code="rendezvous.passed" var="passed"/><jstl:out value="${passed}"/>
            </jstl:if>
        </display:column>


        <acme:columnButton url="rendezvous/display.do?rendezvousId=${row.id}" codeButton="rendezvous.display"/>


    </display:table>