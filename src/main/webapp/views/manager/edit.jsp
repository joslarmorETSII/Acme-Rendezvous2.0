<%--
 * action-1.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="manage/register.do" modelAttribute="managerForm" onsubmit="return validatePhone()">


        <acme:textbox code="manager.username" path="username" />
        <br/>

        <acme:password code="manager.password" path="password" />
        <br/>

        <acme:password code="manager.repeatPassword" path="repeatPassword" />
        <br/>  <br/>


        <acme:textbox code="manager.name" path="name" />
        <br />

        <acme:textbox code="manager.surname" path="surname" />
        <br />

        <acme:textbox code="manager.vat" path="vat"/>
        <br />

        <form:label path="phone"><spring:message code="manager.phone" /></form:label>:&nbsp;
        <form:input id="phoneId" path="phone" placeholder="+34 611222333" />
        <form:errors cssClass="error" path="phone" />
        <br />

        <acme:textbox code="manager.email" path="email"/>
        <br />

        <acme:textbox code="manager.postalAddresses" path="postalAddresses"/>
        <br/>


        <div>
                <form:checkbox id="checkTerm" onclick="comprobar();" path="check"/>
                <form:label path="check">
                        <spring:message code="button.accept" />
                        <a href="termAndCondition/termAndCondition.do"><spring:message code="button.lopd"/></a>
                </form:label>
        </div>


	<input type="submit" name="save" id="saveButton" value="<spring:message code="button.save"/>" />
	
	<input type="button" name="cancel" value="<spring:message code="button.cancel" />"
			onclick="javascript: relativeRedir('welcome/index.do');" />

</form:form>

<script>

function validatePhone() {
 <spring:message code="manager.phone.ask" var="ask"/>
    var x = document.getElementById("phoneId").value;
    var patt = new RegExp("^\\+([3][4])( )(\\d{9})|()$");
    if(x != "" && !patt.test(x)){
        return confirm('<jstl:out value="${ask}"/>');
    } 
}

</script>

<script>

    document.getElementById("saveButton").disabled = true;
    document.getElementById("checkTerm").checked = false;

    function comprobar() {

        var aux = document.getElementById("checkTerm").checked;

        if(aux == true){
            document.getElementById("saveButton").disabled = false;
        }
        else{
            document.getElementById("saveButton").disabled = true;
        }
    }
</script>

