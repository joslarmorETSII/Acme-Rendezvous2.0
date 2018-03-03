<%--
  Created by IntelliJ IDEA.
  User: yuzi
  Date: 2/23/18
  Time: 6:30 PM
  To change this template use File | Settings | File Templates.
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

<br/>
<br/>
<h1><spring:message code="user.name"/>:&nbsp;<jstl:out value="${user.name}" /></h1>
<br/>

<b><spring:message code="user.surname"/>:&nbsp;</b><jstl:out value="${user.surname}" />
<br/>

<b><spring:message code="user.phone"/>:&nbsp;</b><jstl:out value="${user.phone}" />
<br/>

<b><spring:message code="user.email"/>:&nbsp;</b><jstl:out value="${user.email}" />
<br/>

<b><spring:message code="user.postalAddresses"/>:&nbsp;</b><jstl:out value="${user.postalAddresses}" />
<br/>


<input type="button" name="cancel" value="<spring:message code="user.cancel" />"
       onclick="javascript: relativeRedir('rendezvous/listAll-2.do');" />
