<%--
  Created by IntelliJ IDEA.
  User: yuzi
  Date: 3/03/18
  Time: 11:16 AM
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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<jstl:if test="${nothingToDisplay }">Opps!! no rendezvous to assosiat</jstl:if>

<jstl:if test="${nothingToDisplay eq false}">
<form:form action="requestt/user/edit.do" modelAttribute="requesttForm" name="myForm">

    <form:hidden path="servise"/>

    <acme:select path="rendezvous" code="requestt.rendezvous" items="${userRendezvous}" itemLabel="name"/>

    <acme:textarea path="comment" code="requestt.comment"/>

    <fieldset>
        <legend><b><spring:message code="creditCard.legend" var="legend"/><jstl:out value="${legend}"/>
            :&nbsp;</b></legend>


        <form:label path="creditCard.holder"><spring:message code="creditCard.holder" /></form:label>
        <form:input path="creditCard.holder" id="holderId" alt="${sessionScope.holderId}"/>
        <form:errors path="creditCard.holder" cssClass="error" />
        <br/>

        <form:label path="creditCard.brand"><spring:message code="creditCard.brand" /></form:label>
        <form:input path="creditCard.brand" id="brandId"/>
        <form:errors path="creditCard.brand" cssClass="error" />
        <br/>

        <form:label path="creditCard.number"><spring:message code="creditCard.number" /></form:label>
        <form:input path="creditCard.number" id="numberId"/>
        <form:errors path="creditCard.number" cssClass="error" />
        <br/>

        <form:label path="creditCard.expirationMonth"><spring:message code="creditCard.expirationMonth" /></form:label>
        <form:input path="creditCard.expirationMonth" id="expirationMonthId" alt=""/>
        <form:errors path="creditCard.expirationMonth" cssClass="error" />
        <br/>

        <form:label path="creditCard.expirationYear"><spring:message code="creditCard.expirationYear" /></form:label>
        <form:input path="creditCard.expirationYear" id="expirationYearId"/>
        <form:errors path="creditCard.expirationYear" cssClass="error" />
        <br/>

        <form:label path="creditCard.cvv"><spring:message code="creditCard.cvv" /></form:label>
        <form:input path="creditCard.cvv" id="cvvId"/>
        <form:errors path="creditCard.cvv" cssClass="error" />
        <br/>

    </fieldset>

    <acme:submit name="save" code="button.save"/>
  <%--  <jstl:if test="${request.id!=0}">
        <acme:submit name="delete" code="request.delete"/>
    </jstl:if>--%>
    <acme:cancel code="button.cancel" url="servise/listAll.do"/>

</form:form>
</jstl:if>

<script type="text/javascript">

    if(holderId = getCookie("holderId")) document.myForm.holderId.value = holderId;
    if(brandId = getCookie("brandId")) document.myForm.brandId.value = brandId;
    if(numberId = getCookie("numberId")) document.myForm.numberId.value = numberId;
    if(expirationMonthId = getCookie("expirationMonthId")) document.myForm.expirationMonthId.value = expirationMonthId;
    if(expirationYearId = getCookie("expirationYearId")) document.myForm.expirationYearId.value = expirationYearId;
    if(cvvId = getCookie("cvvId")) document.myForm.cvvId.value = cvvId;

    function getCookie(name)
    {
        var re = new RegExp(name + "=([^;]+)");
        var value = re.exec(document.cookie);
        return (value != null) ? unescape(value[1]) : null;
    }

</script>