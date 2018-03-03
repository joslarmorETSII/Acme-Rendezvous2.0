<%--
  Created by IntelliJ IDEA.
  User: yuzi
  Date: 2/18/18
  Time: 6:06 AM
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

<display:table name="answers" id="row" pagesize="5" class="displaytag" requestURI="answer/user/list.do">


    <acme:column code="answer.answer" value="${row.answer}"/>
    <security:authorize access="hasRole('ADMINISTRATOR')">
        <acme:columnButton url="answer/administrator/delete.do?answerId=${row.id}" codeButton="answer.delete"/>
    </security:authorize>


</display:table>

<acme:button code="question.create" url="question/user/create.do"/>
<input type="button" value="<spring:message code="question.cancel" /> " onclick="goBack()">
<script>
    function goBack() {
        window.history.back()
    }
</script>