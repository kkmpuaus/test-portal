<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
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
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/main.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/scripts/jquery/jquery-ui-1.12.1/jquery-ui.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/scripts/jquery/plugins/jquery.fancybox.min.css"/>"/>
<c:set var="reCaptchaSize" value="1.0"/>
<style>
.fancybox-slide--iframe .fancybox-content {
	<c:if test="${captchaSettings.on}">
	width  : 100%;
	height : 100%;
	max-width  : 80%;
	max-height : 95%;</c:if><c:if test="${!captchaSettings.on}">
	width  : 450px;
	height : 300px;
	max-width  : 80%;
	max-height : 80%;</c:if>
	border: 2px solid #DA1E48;
	box-shadow: 5px 5px 3px #888888;
	border-radius: 10px;
	margin: 0;
}
.fancybox-iframe {
	border-radius: 10px;
}
.reCaptcha {
	transform:scale(${reCaptchaSize});
	transform-origin:0;
	-webkit-transform:scale(${reCaptchaSize});
	transform:scale(${reCaptchaSize});
	-webkit-transform-origin:0 0;
	transform-origin:0 0;"
}
</style>
<script type="text/javascript" src="<c:url value="/scripts/jquery/jquery-3.2.1.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/jquery/jquery-ui-1.12.1/jquery-ui.js"/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/jquery/plugins/jquery.fancybox.min.js"/>"></script>
<script>
var isReCaptchaReady = false;
$(document).ready(function() {
	$('[data-fancybox]').fancybox({
		toolbar  : false,
		smallBtn : false,
		modal : true,
		iframe : {
			preload : false
		}
	})
});
function validate() {
	if ($("#username").val() == "") {
		$("#error").html(usernameEmptyErrorMsg);
		$("#username").focus();
		return false;
	}
	/*
	var re = /\S+@\S+/;
	if (!re.test($("#username").val())) {
		$("#error").html(emailFormatErrorMsg);
		$("#username").focus();
		return false;		
	}
	*/
	if ($("#password").val() == "") {
		$("#error").html(passwordEmptyErrorMsg);
		$("#password").focus();
		return false;
	}<c:if test="${captchaSettings.on}">
	if (!isReCaptchaReady) {
		$("#error").html(reCaptchaErrorMsg);
		return false;
	}</c:if>
	return true;
}
function forgetPassword() {
	generalJqDialog_g.fnShowGeneralInformationDialog('<spring:message code="dialog.commonTitle.information"/>',
			'<spring:message code="login.forgetpassword.msg"/>',null,null);
}
function reCaptchaCallback() {
	isReCaptchaReady = true;
}
var emailFormatErrorMsg = '<spring:message code="login.label.error.id.format.error"/>';
var usernameEmptyErrorMsg = '<spring:message code="login.label.error.username.empty"/>';
var passwordEmptyErrorMsg = '<spring:message code="login.label.error.password.empty"/>';
var reCaptchaErrorMsg = '<spring:message code="login.label.error.captcha.missing"/>';
</script>
<script src='https://www.google.com/recaptcha/api.js?hl=${pageContext.response.locale}'></script>
</head>
<body>
<div style="margin: 0px;width:100%">
	<div class="header">
		<div class="header-left">
			<div class="header-logo">
				<!-- <img src="images/ghdr-logo.gif" border="0" style="top:30px; left:50px; position: absolute;" />  -->
			</div>
			<div class="header-info">
			</div>
		</div>
    	<div class="header-right">
		</div>
	</div>
</div>
<div style="min-height:450px">
	<div style="height:100px"></div>
	<div class="loginbox">
	<div style="height: 50px"></div>
	<form action="<c:url value='j_spring_security_check' />" method="post" onSubmit="return validate();" id="loginForm" name="loginForm">
		<div class="login">
			<div class="loginTitle">
			<spring:message code="login.title"/>
			</div>
			<div class="logininput">
			<input id="username" name="username" class="required" type="text" tabindex="1" size="25" autocomplete="off" style="width: 85%;font-size:15px" placeholder="<spring:message code="login.label.username"/>"/>
			</div>
			<div class="logininput">
			<input id="password" name="password" class="required" type="password" tabindex="2" size="25" autocomplete="off" style="width: 85%;font-size:15px" placeholder="<spring:message code="login.label.password"/>"/>
			</div>
			<div id="error" class="loginerror">
				<c:if test="${not empty param.error}">
					<c:if test="${not empty param.r}">
						<c:if test="${param.r == 0}">
							<c:if test="${not empty sessionScope.userRole}">
								<spring:message code="login.label.error.locked.${sessionScope.userRole}" arguments="${sessionScope.adminDisplayName}"/>					
							</c:if>
							<c:if test="${empty sessionScope.userRole}">
								<spring:message code="login.label.error.locked"/>
							</c:if>						
						</c:if>
						<c:if test="${param.r < 0}">
							<spring:message code="login.label.error.invaliduser"/>
						</c:if>
						<c:if test="${param.r > 0}">
							<spring:message code="login.label.error.invaliduser.r" arguments="${param.r}"/>
						</c:if>
					</c:if>
					<c:if test="${empty param.r}">
						<spring:message code="login.label.error.invaliduser"/>
					</c:if>
				</c:if>
				<c:if test="${not empty param.acExpired}">
					<spring:message code="login.label.error.acexpired"/>
				</c:if>
				<c:if test="${not empty param.expired}">
					<spring:message code="login.label.error.expired"/>
				</c:if>
				<c:if test="${not empty param.disabled}">
					<c:if test="${not empty sessionScope.userRole}">
						<spring:message code="login.label.error.disabled.${sessionScope.userRole}" arguments="${sessionScope.adminDisplayName}"/>					
					</c:if>
					<c:if test="${empty sessionScope.userRole}">
						<spring:message code="login.label.error.disabled"/>
					</c:if>
				</c:if>
				<c:if test="${not empty param.locked}">
					<c:if test="${not empty sessionScope.userRole}">
						<spring:message code="login.label.error.locked.${sessionScope.userRole}" arguments="${sessionScope.adminDisplayName}"/>					
					</c:if>
					<c:if test="${empty sessionScope.userRole}">
						<spring:message code="login.label.error.locked"/>
					</c:if>						
				</c:if>
				<c:if test="${not empty param.kicked}">
					<spring:message code="login.label.error.loggedout"/>
				</c:if>
				<c:if test="${not empty param.duplicateLogin}">
					<spring:message code="login.label.error.duplicatelogin"/>
				</c:if>
				<c:if test="${not empty param.captchaerr}">
					<spring:message code="login.label.error.captchaerr"/>
				</c:if>
				<c:if test="${not empty param.invalid}">
					<spring:message code="login.label.error.others"/>
				</c:if>
			</div><c:if test="${captchaSettings.on}">
			<div style="margin-left: 25px;padding-top:5px">
				<div class="g-recaptcha reCaptcha" data-callback="reCaptchaCallback" data-sitekey="${captchaSettings.site}"></div>
			</div></c:if>
			<div>
			<ul style="padding-left: 45px;line-height:18px;margin:0;margin-top:5px">
				<li> <a href="javascript:void(0)" onclick="forgetPassword()"><spring:message code="login.label.forgetpassword"/></a>
			</ul>
			<div class="loginbutton">
<%-- 				<div class="loginbuttonleft">
					<c:set var="language" value="${pageContext.response.locale}"/>
					<c:if test="${language=='en_US'}">
						<font style="text-decoration: underline"><spring:message code="lang.en"/></font>
					</c:if>
					<c:if test="${language!='en_US'}">
						<a href="<c:url value="?lang=en_US"/>"><spring:message code="lang.en"/></a>
					</c:if>
					&nbsp;&nbsp;
					<c:if test="${language=='zh_HK'}">
						<font style="text-decoration: underline"><spring:message code="lang.zh_HK"/></font>
					</c:if>
					<c:if test="${language!='zh_HK'}">
						<a href="<c:url value="?lang=zh_HK"/>"><spring:message code="lang.zh_HK"/></a>
					</c:if>
					&nbsp;&nbsp;
					<c:if test="${language=='zh_CN'}">
						<font style="text-decoration: underline"><spring:message code="lang.zh_CN"/></font>
					</c:if>
					<c:if test="${language!='zh_CN'}">
						<a href="<c:url value="?lang=zh_CN"/>"><spring:message code="lang.zh_CN"/></a>
					</c:if>
				</div> --%>
				<div class="loginbuttonright">
					<sec:csrfInput />
					<input id="normalLogin" name="submit" value="<spring:message code="login.button.submit"/>" tabindex="4" type="submit" class="ui-button ui-widget ui-state-default ui-corner-all" style="font-size:14px;width:80px;margin-right:30px;background-color:#92d050;color:#ffffff;border: 1px solid #77b731"/>
				</div>
			</div>
			</div>
		</div>
	</form>
	</div>
</div>
<c:remove var="userRole" scope="session" /><c:remove var="adminDisplayName" scope="session" />
<div style="margin: 0px;width:100%;position:fixed;bottom:0">
	<%@ include file="include/commonFooter.jsp" %>
</div>
<%@ include file="include/generalJqDialog.jsp" %>
</body>
</html>