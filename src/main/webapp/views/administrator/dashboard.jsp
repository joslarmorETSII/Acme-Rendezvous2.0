<%--
  Created by IntelliJ IDEA.
  User: Félix
  Date: 22/02/2018
  Time: 12:02
  To change this template use File | Settings | File Templates.
--%>


<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<br>

<!-- Queries level c -->

<fieldset>
    <div class="panel-body">
        <b><spring:message code="rendezvous.avgDevRendezvousesPerUser"/></b>
        <br/>
        <h4><jstl:out value=" AVG: "/><jstl:out value="${avgDevRendezvousesPerUser[0]}"/> <br/></h4>
        <h4><jstl:out value=" STDDEV: "/><jstl:out value="${avgDevRendezvousesPerUser[1]}"/> <br/></h4>
    </div>
</fieldset>

<fieldset>
    <div class="panel-body">
        <legend><b><spring:message code="user.RatioCreatorsVsNoCreators"/></b></legend>

        <jstl:out value="${RatioCreatorsVsNoCreators}"></jstl:out><br>
    </div>
</fieldset>

<fieldset>
    <div class="panel-body">
        <b><spring:message code="rendezvous.avgDevRendezvousPerUser"/></b>
        <br/>
        <h4><jstl:out value=" AVG: "/><jstl:out value="${avgDevRendezvousPerUser[0]}"/> <br/></h4>
        <h4><jstl:out value=" STDDEV: "/><jstl:out value="${avgDevRendezvousPerUser[1]}"/> <br/></h4>
    </div>
</fieldset>

<fieldset>
    <div class="panel-body">
        <b><spring:message code="rendezvous.avgDevRendezvousParticipatePerUser"/></b>
        <br/>
        <h4><jstl:out value=" AVG: "/><jstl:out value="${avgDevRendezvousParticipatePerUser[0]}"/> <br/></h4>
        <h4><jstl:out value=" STDDEV: "/><jstl:out value="${avgDevRendezvousParticipatePerUser[1]}"/> <br/></h4>
    </div>
</fieldset>


<fieldset>
    <b><spring:message code="rendezvous.top10RendezvousParticipated"/></b>
    <display:table pagesize="10" class="displaytag" keepStatus="true" name="top10RendezvousParticipated"
                   requestURI="administrator/dashboard.do" id="row">

        <spring:message code="rendezvous.name" var="name"/>
        <display:column property="name" title="${name}"/>
        <spring:message code="rendezvous.description" var="description"/>
        <display:column property="description" title="${description}"/>
        <spring:message code="rendezvous.moment" var="moment"/>
        <display:column property="moment" title="${moment}"/>
        <spring:message code="rendezvous.picture" var="pic"/>

    </display:table>
</fieldset>

<!-- Queries level B -->

<fieldset>
    <div class="panel-body">
        <b><spring:message code="announcement.avgDevAnnouncementsPerRendezvous"/></b>
        <br/>
        <h4><jstl:out value=" AVG: "/><jstl:out value="${avgDevAnnouncementsPerRendezvous[0]}"/> <br/></h4>
        <h4><jstl:out value=" STDDEV: "/><jstl:out value="${avgDevAnnouncementsPerRendezvous[1]}"/> <br/></h4>
    </div>
</fieldset>

<fieldset>
    <div class="panel-body">
        <b><spring:message code="rendezvous.rendezvousPlus75AvgAnnouncements"/></b>
        <display:table pagesize="2" class="displaytag" keepStatus="true" name="rendezvousPlus75AvgAnnouncements"
                       requestURI="administrator/dashboard.do" id="row">

            <spring:message code="rendezvous.name" var="name"/>
            <display:column property="name" title="${name}"/>
            <spring:message code="rendezvous.description" var="description"/>
            <display:column property="description" title="${description}"/>
            <spring:message code="rendezvous.moment" var="moment"/>
            <display:column property="moment" title="${moment}"/>
            <spring:message code="rendezvous.picture" var="pic"/>

        </display:table>
    </div>
</fieldset>

<fieldset>
    <b><spring:message code="rendezvous.rendezvousPlus10AvgAssociated"/></b>
    <display:table pagesize="2" class="displaytag" keepStatus="true" name="rendezvousPlus10AvgAssociated"
                   requestURI="administrator/dashboard.do" id="row">

        <spring:message code="rendezvous.name" var="name"/>
        <display:column property="name" title="${name}"/>
        <spring:message code="rendezvous.description" var="description"/>
        <display:column property="description" title="${description}"/>
        <spring:message code="rendezvous.moment" var="moment"/>
        <display:column property="moment" title="${moment}"/>
        <spring:message code="rendezvous.picture" var="pic"/>

    </display:table>
</fieldset>
<!-- Queries level a -->

<fieldset>
    <div class="panel-body">
        <b><spring:message code="questions.questionsPerRendezvous"/></b>
        <br/>
        <h4><jstl:out value=" AVG: "/><jstl:out value="${questionsPerRendezvous[0]}"/> <br/></h4>
        <h4><jstl:out value=" STDDEV: "/><jstl:out value="${questionsPerRendezvous[1]}"/> <br/></h4>
    </div>
</fieldset>

<fieldset>
    <div class="panel-body">
        <b><spring:message code="answer.avgDevAnswersQuestionsPerRendezvous"/></b>
        <br/>
        <h4><jstl:out value=" AVG: "/><jstl:out value="${avgDevAnswersQuestionsPerRendezvous[0]}"/> <br/></h4>
        <h4><jstl:out value=" STDDEV: "/><jstl:out value="${avgDevAnswersQuestionsPerRendezvous[1]}"/> <br/></h4>
    </div>
</fieldset>

<fieldset>
    <div class="panel-body">
        <b><spring:message code="comment.avgDevRepliesPerComment"/></b>
        <br/>
        <h4><jstl:out value=" AVG: "/><jstl:out value="${avgDevRepliesPerComment[0]}"/> <br/></h4>
        <h4><jstl:out value=" STDDEV: "/><jstl:out value="${avgDevRepliesPerComment[1]}"/> <br/></h4>
    </div>
</fieldset>
