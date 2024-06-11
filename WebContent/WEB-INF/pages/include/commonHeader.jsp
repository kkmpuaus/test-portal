<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="com.tradelink.biometric.r2fas.portal.utils.AuthUtils" %><%
String username = ""; String companyId = "";
if (AuthUtils.currentUserDetails() != null) {
	username = AuthUtils.currentUserDetails().getDisplayName();
	companyId = AuthUtils.currentUserDetails().getSubId();
}
%>
<script>
function changeLocale(locale) {
	if (window.changeFormLocale != null) {
		changeFormLocale(locale);
	} else {
		changeLocaleForGET(locale);
	}
}
function changeLocaleForGET(locale) {
	var href = location.href;
	var idx = href.indexOf('?');
	var qs;
	if (idx == -1) {
		location=location.href+"?lang="+locale;
		return;
	}
	var loc = href.substring(0, idx);
	qs = href.substring(idx);
	var newqs = changeParameter(qs, "lang", locale);
	location=loc+newqs;
}
function changeParameter(qs, param, value) {
	var newqs = qs;
	var idx2=-1;
	idx = qs.indexOf(param+'=');
	if (idx!=-1) {
		idx2 = qs.indexOf('&', idx+1);
		newqs = qs.substring(0, idx);
	}	
	if (idx2!=-1) newqs += qs.substring(idx2+1);
	var pValue=param+"="+value;
	if (newqs.lastIndexOf("&")!=newqs.length-1 && newqs.lastIndexOf("?")!=newqs.length-1) pValue = "&"+pValue;
	return newqs+pValue;
}
function logout() {
	generalJqDialog_g.fnShowGeneralConfirmDialog(
			"<spring:message code="header.logout.confirm.title" javaScriptEscape="true"/>",
			"<spring:message code="header.logout.confirm.msg" javaScriptEscape="true"/>",
			generalJqDialog_g.buttonConfirm,
			function() {
				document.getElementById("logoutForm").submit();
	});
}
function login() {
	window.location = '<c:url value="/login.do" />';
}
function changePwd() {
	window.location = '<c:url value="/changepwd.do" />';
}
function gohome(locale) {
	window.location = '<c:url value="/home.do"/>?lang='+locale;
}
</script>
	<div class="header">
		<div class="header-left">
			<div class="header-logo">
				<!-- <img src="<c:url value="/images/ghdr-logo.gif" />" border="0" />  -->
				<font style="font-size:20pt;font-weight:bold"><spring:message code="login.title"/></font>
			</div>
			<!-- 
			<div class="header-info">
			<spring:message code="header.title"/>
			</div>
			 -->
		</div>
    	<div class="header-right">
			<div class="userinfo">
			<c:set var = "isAuth" value = "false"/>
			<sec:authorize access="isAuthenticated()"><c:set var = "isAuth" value = "true"/>
			<spring:message code="header.greeting"/>
			<a href="javascript:void(0)" onClick="changePwd()"><%= username %></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" onClick="logout()"><spring:message code="header.logout"/></a>
			</sec:authorize>
			<c:if test="${isAuth eq false}">
			<a href="javascript:void(0);" onclick="login();"><spring:message code="header.login"/></a>
			</c:if>
			</div>
<%-- 			<sec:authorize access="isAuthenticated()">
			<div class="compinfo">
			<spring:message code="header.company.id"/> <%=companyId %>
			</div>
			</sec:authorize> --%>
			<!--  
    		<div class="lang">
			<c:set var="language" value="${pageContext.response.locale}"/>
			<c:if test="${language=='en_US'}">
				<font style="text-decoration: underline"><spring:message code="lang.en"/></font>
			</c:if>
			<c:if test="${language!='en_US'}">
				<a href="javascript:void(0)" onclick="changeLocale('en_US')"><spring:message code="lang.en"/></a>
			</c:if>
			&nbsp;&nbsp;
			<c:if test="${language=='zh_HK'}">
				<font style="text-decoration: underline"><spring:message code="lang.zh_HK"/></font>
			</c:if>
			<c:if test="${language!='zh_HK'}">
				<a href="javascript:void(0)" onclick="changeLocale('zh_HK')"><spring:message code="lang.zh_HK"/></a>
			</c:if>
			&nbsp;&nbsp;
			<c:if test="${language=='zh_CN'}">
				<font style="text-decoration: underline"><spring:message code="lang.zh_CN"/></font>
			</c:if>
			<c:if test="${language!='zh_CN'}">
				<a href="javascript:void(0)" onclick="changeLocale('zh_CN')"><spring:message code="lang.zh_CN"/></a>
			</c:if>
			</div>
    		<div class="hotline">
    		<spring:message code="header.cshotline"/><br>
    		<spring:message code="header.cshotline.tel"/>
    		</div>
    		-->
		</div>
	</div>
<c:url var="logoutUrl" value="/logout.do"/>
<form style="margin:0" id="logoutForm" action="${logoutUrl}" method="post">
    <sec:csrfInput />
</form>