<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>


<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<img src="images/logo.png" alt="Acme-Rendezvous2.0 Co., Inc." />
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMINISTRATOR')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
			<ul>
					<li class="arrow"></li>
					<li><a href="administrator/edit.do"><spring:message code="master.page.administrator.edit" /></a></li>
					<li><a href="comment/administrator/list.do"><spring:message code="master.page.comment.administrator.list" /></a></li>
					<li><a href="announcement/listAll.do"><spring:message code="master.page.announcement.listAll" /></a></li>
					<li><a href="rendezvous/administrator/listAll.do"><spring:message code="master.page.rendezvous.administrator.list" /></a></li>
                <li><a href="administrator/dashboard.do"><spring:message
                        code="master.page.administrator.dashboard"/></a></li>
			</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('USER')">
			<li><a class="fNiv"><spring:message	code="master.page.user" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="user/editProfile.do"><spring:message code="master.page.user.edit" /></a></li>
                    <li><a href="rendezvous/user/list.do"><spring:message code="master.page.customer.rendezvous.list" /></a></li>
                    <li><a href="comment/user/list.do"><spring:message code="master.page.user.comment.list" /></a></li>
					<li><a href="announcement/user/list.do"><spring:message code="master.page.user.announcement.list" /></a></li>
                </ul>
			</li>
			<li><a class="fNiv" href="rendezvous/listAll.do"><spring:message code="master.page.rendezvous.listAll-2" /></a></li>
			<li><a class="fNiv" href="servise/listAll.do"><spring:message code="master.page.servise.listAll" /></a></li>
			<li><a class="fNiv" href="announcement/user/listAllUser.do"><spring:message code="master.page.announcement.listAll" /></a></li>

		</security:authorize>
<security:authorize access="hasRole('MANAGER')">
		<li><a class="fNiv"><spring:message	code="master.page.manager" /></a>
			<ul>
				<li class="arrow"></li>
				<li><a href="manage/editProfile.do"><spring:message code="master.page.manager.edit" /></a></li>
            </ul>
</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="user/list.do"><spring:message code="master.page.user.list" /></a></li>
			<li><a class="fNiv" href="announcement/listAll.do"><spring:message code="master.page.announcement.listAll" /></a></li>
			<li><a class="fNiv" href="rendezvous/listAll-2.do"><spring:message code="master.page.rendezvous.listAll-2" /></a></li>
			<li><a class="fNiv" href="user/register.do"><spring:message code="master.page.user.create" /></a>
			<li><a class="fNiv" href="manage/register.do"><spring:message code="master.page.manager.create" /></a>

			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv" href="user/list.do"><spring:message code="master.page.user.list" /></a></li>
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

