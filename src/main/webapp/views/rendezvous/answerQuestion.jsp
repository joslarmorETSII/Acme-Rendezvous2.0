<%--
  Created by IntelliJ IDEA.
  User: yuzi
  Date: 2/19/18
  Time: 5:49 PM
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
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<form:form action="participate/user/answerQuestion.do" modelAttribute="questionForm">

    <form:hidden path="questions"/>
    <form:hidden path="answer"/>

    <c:forEach items="${questions}"  var="item">
       <b> <jstl:out value="${item.text}"/></b>
        <input type="text" name="answer" id="question.id" property="answer"><br>
        <form:errors cssClass="error" path="answer"/>
    </c:forEach>

    <acme:submit name="save" code="question.save"/>
</form:form>
