<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<tiles:insertDefinition name="mainTemplate">
    <tiles:putAttribute name="body">
		<div class="content">
			<div class="errorTitle">
				<spring:message code="http.error.405.title" />
			</div>
			<div class="errorMsg">
				<spring:message code="http.error.405.msg" />
			</div>
			<div class="errorAction">
			<a href="<c:url value="/home.do"/>"><spring:message code="http.error.405.action"/></a>
			</div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>