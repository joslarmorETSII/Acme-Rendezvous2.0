	<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="categories" id="row" requestURI="category/list.do" pagesize="5" class="displaytag">
	
	<div>
		<security:authorize access="hasRole('ADMINISTRATOR')">
			<display:column>
				<jstl:if test="${empty row.servises}">
				<spring:message var="categoryEdit" code="category.edit"/> 
				<a href="category/administrator/edit.do?categoryId=${row.id}"> ${categoryEdit} </a>
				</jstl:if>
			</display:column>
		</security:authorize>
		
		<spring:message var="categorySubcategories" code="category.subcategories"/>
		<display:column title="${categorySubcategories}">
			<jstl:if test="${not empty row.childrenCategories}">
				<spring:message var="categoryChildrenCategory" code="category.childrenCategory"/>
				<a href="category/list.do?categoryId=${row.id}">${categoryChildrenCategory}</a>
			</jstl:if>
		</display:column>
		
		<spring:message var="categoryParentCategory" code="category.parentCategory"/>
		
		<display:column title="${categoryParentCategory}" >
			<jstl:choose>
				<jstl:when test="${row.parentCategory.name != 'CATEGORY'}"> 
					<a href="category/list.do?categoryId=${row.parentCategory.parentCategory.id}">${row.parentCategory.name}</a>
				</jstl:when>
				<jstl:otherwise>
					${row.parentCategory.name}
					${row.parentCategory.description}
				</jstl:otherwise>
			</jstl:choose>
		</display:column>

		
		<spring:message var="categoryName" code="category.name"/>
		<display:column property="name" title="${categoryName}" />

		<spring:message var="categoryDescription" code="category.description"/>
		<display:column property="description" title="${categoryDescription}" />

		<spring:message var="categoryRendezvouses" code="category.categoryRendezvouses"/>
		<security:authorize access="isAnonymous()">
		<display:column title="${categoryRendezvouses}">
			<spring:message var="categoryRendezvouses" code="category.categoryRendezvouses"/>
			<a href="category/listRendezvous.do?categoryId=${row.id}">${categoryRendezvouses}</a>
		</display:column>
		</security:authorize>
	</div>
	
</display:table>

<security:authorize access="hasRole('ADMINISTRATOR')">
	<spring:message var="categoryCreate" code="category.create"/> 
	<a href="category/administrator/create.do"> ${categoryCreate}</a>
</security:authorize>