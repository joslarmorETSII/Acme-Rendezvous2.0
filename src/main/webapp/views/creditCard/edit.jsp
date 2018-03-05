<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="creditCard/edit.do" modelAttribute="creditCard">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="actor"/>

	<div >
	
		<fieldset id="credit"> 
	
		
			<legend> <spring:message code="datos.creditCard" /> </legend>
			
			
			<div> 
			
				<form:label path="holderName">
					<spring:message code="consumer.creditCard.holderName" />:
				</form:label>
				<form:input path="holderName" />
				<form:errors cssClass="error" path="holderName" />
				
			</div>
			
<%-- 			<div>
			
				<form:label path="brandName">
					<spring:message code="consumer.creditCard.brandName" />:
				</form:label>
				<form:input path="brandName" />
				<form:errors cssClass="error" path="brandName" />
			
			</div> --%>
			
			<div>
			
			<form:label path="brandName" ><spring:message code="consumer.creditCard.brandName"/></form:label>
			<form:select path="brandName">
				<form:option value="undefined"> -- </form:option>
				<form:option value="VISA">VISA</form:option>
				<form:option value="MASTERCARD">MASTERCARD</form:option>
				<form:option value="DISCOVER">DISCOVER</form:option>
				<form:option value="DINNERS">DINNERS</form:option>
				<form:option value="AMEX">AMEX</form:option>
			</form:select>
			<form:errors cssClass="error" path="brandName" />
			
			</div>
			
			<div>
			
				<form:label path="number">
					<spring:message code="consumer.creditCard.number" />:
				</form:label>
				<form:input path="number" />
				<form:errors cssClass="error" path="number" />
			
			</div>
			
			<div>
			
				<form:label path="expirationMonth">
					<spring:message code="consumer.creditCard.expirationMonth" />:
				</form:label>
				<form:input path="expirationMonth" />
				<form:errors cssClass="error" path="expirationMonth" />
			
			</div>
			
			<div>
			
				<form:label path="expirationYear">
					<spring:message code="consumer.creditCard.expirationYear" />:
				</form:label>
				<form:input path="expirationYear" />
				<form:errors cssClass="error" path="expirationYear" />
			
			</div>
			
			<div> 
			
				<form:label path="CVV">
					<spring:message code="consumer.creditCard.cvv" />:
				</form:label>
				<form:input path="CVV" />
				<form:errors cssClass="error" path="CVV" />
		
			</div>
		
		</fieldset>
	
	</div>

		<div id="botones">

		<acme:submit code="creditCard.submit" name="save" />
		<jstl:if test="${creditCard.id != 0}">
			<acme:submit name="delete" code="creditCard.delete" />
		</jstl:if>
		<acme:cancel code="creditCard.cancel" url="welcome/index.do" />

	</div>

</form:form>

<%-- </security:authorize> --%>