<%--
  Created by IntelliJ IDEA.
  User: khawla
  Date: 24/02/2018
  Time: 17:03
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<form:form action="rendezvous/user/associate.do" modelAttribute="associatForm">


    <form:hidden path="formid"/>


    <security:authorize access="hasRole('USER')">
        <jstl:forEach items="${allRendezvous}" var="allRendezvous">

            <input type="checkbox" name="rendezvous" value="${allRendezvous.id}">${allRendezvous.name}<br>

        </jstl:forEach>
    </security:authorize>

<jstl:if test="${not empty allRendezvous }">
    <input type="submit" name="save" value="<spring:message code="rendezvous.save"/>" />
</jstl:if>
    <jstl:if test="${empty allRendezvous }">
        <spring:message code="rendezvous.associate.nothing" var="nothing"/><jstl:out value="${nothing}"/><br>
    </jstl:if>
    <input type="button" name="cancel" value="<spring:message code="rendezvous.cancel" />"
           onclick="javascript: relativeRedir('rendezvous/user/list.do');" />

</form:form>




