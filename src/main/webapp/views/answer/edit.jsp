<%--
  Created by IntelliJ IDEA.
  User: yuzi
  Date: 2/18/18
  Time: 11:31 AM
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

<form:form action="answer/user/edit.do" modelAttribute="answer">

    <jstl:forEach items="${questions}" var="question" >
        <jstl:out value="question.text"/>
        <br/>
        <acme:textbox path="${question.answer}" code="answer.question"/>

    </jstl:forEach>

</form:form>
<acme:submit name="save" code="question.save"/>
