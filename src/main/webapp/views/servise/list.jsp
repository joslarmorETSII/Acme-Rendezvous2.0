<%--
  Created by IntelliJ IDEA.
  User: F�lix
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


<display:table id="servise" name="servises" requestURI="${requestURI}"
               pagesize="5" class = "displaytag">

    <acme:column code="servise.name" value="${servise.name}" />

    <acme:column code="servise.description" value="${servise.description}"/>

    <spring:message code="servise.picture" var="pic"/>

    <display:column title="${pic}"><img src="${servise.picture}" width="130" height="100"></display:column>

<%--
    <acme:column code="servise.assigned" value ="${servise.assigned}"/>
--%>


    <jstl:if test="${manager eq servise.manager}">
        <security:authorize access="hasRole('MANAGER')">
            <display:column>
                <acme:button code="servise.edit" url="servise/manager/edit.do?serviseId=${servise.id}" />
            </display:column>
        </security:authorize>
    </jstl:if>

    <jstl:if test="${servise.assigned eq false && manager eq servise.manager}">
        <security:authorize access="hasRole('MANAGER')">
            <display:column>
                <acme:button url="servise/manager/editDelete.do?serviseId=${servise.id}" code="button.delete" />
            </display:column>
        </security:authorize>
    </jstl:if>


    <security:authorize access="hasRole('ADMINISTRATOR')">
        <jstl:if test="${servise.inappropriate eq false}">
             <acme:columnButton url="servise/administrator/edit.do?serviseId=${servise.id}" codeButton="servise.inappropriate"/>
        </jstl:if>
    </security:authorize>


    <jstl:if test="${servise.inappropriate eq false}">
        <security:authorize access="hasRole('USER')">
            <acme:columnButton codeButton="servise.requestt" url="requestt/user/create.do?serviseId=${servise.id}"/>
        </security:authorize>
    </jstl:if>

    <jstl:if test="${servise.inappropriate eq true}">
        <display:column>
            <spring:message code="servise.inappropriate.flag" var="inappropriate"/><jstl:out value="${inappropriate}"/>
        </display:column>
    </jstl:if>

</display:table>

<security:authorize access="hasRole('MANAGER')">
    <acme:button code="button.create" url="servise/manager/create.do"/>
</security:authorize>

<security:authorize access="hasRole('MANAGER')">
    <acme:button code="button.cancel" url="welcome/index.do"/>
</security:authorize>