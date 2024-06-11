<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

    <div class="mainMenu">
        <div class="menuLeft">&nbsp;</div>
		<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_HR')">
	    <div class="menuItem ${menuFaceUser}" style="width: 180px">
		<a href="<c:url value="faceuser.do"/>"><font class="menulabel ${menuFaceUser}"><spring:message code="menu.label.faceuser"/></font></a>
		</div>
		</sec:authorize>
		<div class="menuLeft">&nbsp;</div>
		<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_IT')">
	    <div class="menuItem ${menuCamera}" style="width: 180px">
		<a href="<c:url value="camera.do"/>"><font class="menulabel ${menuCamera}"><spring:message code="menu.label.camera"/></font></a>
		</div>
		</sec:authorize>
		<div class="menuLeft">&nbsp;</div>
		<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_IT')">
	    <div class="menuItem ${menuFaceHistory}" style="width: 180px">
		<a href="<c:url value="facehistory.do"/>"><font class="menulabel ${menuFaceHistory}"><spring:message code="menu.label.facehistory"/></font></a>
		</div>
		</sec:authorize>
		<div class="menuLeft">&nbsp;</div>
		<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
	    <div class="menuItem ${menuAccountManage}" style="width: 180px">
		<a href="<c:url value="accountmanage.do"/>"><font class="menulabel ${menuAccountManage}"><spring:message code="menu.label.accountmanage"/></font></a>
		</div>
		</sec:authorize>
    </div>
