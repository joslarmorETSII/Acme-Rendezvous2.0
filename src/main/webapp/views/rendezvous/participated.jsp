<%--
  Created by IntelliJ IDEA.
  User: khawla
  Date: 25/02/2018
  Time: 20:11
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

<jstl:forEach items="${questions}" var="item" >
    <acme:out code="question.text" value="${item.text}"/>
    <display:table name="${item.answers}" id="row" pagesize="5" class="displaytag" requestURI="${requestURI}">
        <acme:column code="user.name" value="${row.user.name}"/>
        <acme:column code="answer.answer" value="${row.answer}"/>
    </display:table>
    <br>

</jstl:forEach>

<br>
<acme:cancel code="rendezvous.cancel" url="rendezvous/display.do?rendezvousId=${rendezvous.id}" />
