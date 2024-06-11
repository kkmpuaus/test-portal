<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	response.setDateHeader("Expires", 0);
%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Expires" content="0" />
<meta http-equiv="Pragma" content="NO-CACHE" />
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<sec:csrfMetaTags />
<title><spring:message code="webapp.title"/></title>
<tiles:insertAttribute name="css" />
<tiles:insertAttribute name="js" />
<script>
$(document).ready(function() {
});
</script>
</head>
<body>
    <div style="margin: 0px;width:100%;float:left;min-height: calc(100vh - 42px);">
		<tiles:insertAttribute name="header" />
		<tiles:insertAttribute name="menu" ignore="true" />
		<div style="min-height:450px">
		<tiles:insertAttribute name="body" />
		</div>
    </div> <!-- /container -->
	<div style="margin: 0px;width:100%;float:left">
		<tiles:insertAttribute name="footer" />
	</div>

	<tiles:insertAttribute name="generalJqDialog" ignore="true" />
	<tiles:insertAttribute name="datePicker" ignore="true" />
</body>
</html>