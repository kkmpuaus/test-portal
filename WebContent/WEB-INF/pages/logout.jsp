<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<tiles:insertDefinition name="mainTemplate">
    <tiles:putAttribute name="body">
    	<div class="content">
		   	<div class="title">
				<div class="title-left">
					<spring:message code="logout.header" />
				</div>
				<div class="title-right">
				</div>
			</div>   	
			<div class="msg">
				<spring:message code="logout.success" /><br/>
				<spring:message code="logout.security" />
			</div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>