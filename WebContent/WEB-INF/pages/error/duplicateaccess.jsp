<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<tiles:insertDefinition name="mainTemplate">
    <tiles:putAttribute name="body">
		<div class="content">
			<div class="errorTitle">
				<spring:message code="http.error.duplicateaccess.title" />
			</div>
			<div class="errorMsg">
				<spring:message code="http.error.duplicateaccess.msg" />
			</div>
			<div class="errorAction">
			<a href="<c:url value="/login.do"/>"><spring:message code="http.error.duplicateaccess.action"/></a>
			</div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>