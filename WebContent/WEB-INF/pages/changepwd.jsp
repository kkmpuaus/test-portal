<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@page import="com.tradelink.biometric.r2fas.portal.security.model.WebUser" %>
<%@page import="com.tradelink.biometric.r2fas.portal.utils.AuthUtils" %><%
WebUser webUser = null;
String userRole = "";
if (AuthUtils.currentUserDetails() != null) {
	webUser = AuthUtils.currentUserDetails();
	userRole = AuthUtils.currentUserDetails().getRole();
	pageContext.setAttribute("webUser", webUser);
	pageContext.setAttribute("userRole", userRole);
}
%>
<tiles:insertDefinition name="mainTemplate">
	<tiles:putAttribute name="body">
	<style>
	div.row {
		line-height:50px;
		width: 100%;
		float: left;
	}
	div.cell {
		width: 150px;
		float: left;
	}
	input.pwd {
		height: 20px;
		width: 200px;
	}
	input.but {
		font-size: 14px;
		width: 80px;
	}
	.fancybox-slide--iframe .fancybox-content {
		width  : 800px;
		height : 600px;
		max-width  : 80%;
		max-height : 80%;
		margin: 0;
	}
	</style>
	<script>
	var errList = new Array();
	errList[0] = '<spring:message code="changepwd.result.success"/>';<c:forEach var = "i" begin = "1" end = "${totalNoOfError}">
	errList[${i}] = '<spring:message code="changepwd.error.${i}"/>';</c:forEach>
	function cancelPwd() {
		var userRole = '${userRole}';
		if (userRole == "ROLE_ADMIN" || userRole == "ROLE_HR") {
			window.location = '<c:url value="/faceuser.do"/>';
		} else {
			window.location = '<c:url value="/camera.do"/>';
		}
	}
	function savePwd() {
		if ($("#oldpwd").val() == "") {
			generalJqDialog_g.fnShowGeneralAlertDialog(generalJqDialog_g.commonTitleError,
					'<spring:message code="changepwd.error.1"/>',
					function() {
						$("#oldpwd").focus();
					});
			return;
		}
		if ($("#newpwd").val() == "") {
			generalJqDialog_g.fnShowGeneralAlertDialog(generalJqDialog_g.commonTitleError,
					'<spring:message code="changepwd.error.2"/>',
					function() {
						$("#newpwd").focus();
					});
			return;
		}
		if ($("#newpwd1").val() == "") {
			generalJqDialog_g.fnShowGeneralAlertDialog(generalJqDialog_g.commonTitleError,
					'<spring:message code="changepwd.error.3"/>',
					function() {
						$("#newpwd1").focus();
					});
			return;
		}
		if ($("#newpwd").val() != $("#newpwd1").val()) {
			generalJqDialog_g.fnShowGeneralAlertDialog(generalJqDialog_g.commonTitleError,
					'<spring:message code="changepwd.error.4"/>', null);
			return;			
		}
		if ($("#oldpwd").val() == $("#newpwd").val()) {
			generalJqDialog_g.fnShowGeneralAlertDialog(generalJqDialog_g.commonTitleError,
					'<spring:message code="changepwd.error.8"/>', null);
			return;			
		}
		$.ajax({
			 url: '<c:url value="/changepwd.do"/>',
			 method :"post",
			 dataType:"json",
			 data: $("#userForm").serialize(),
			 success: function(result) {
				 if (result == 0) {
					generalJqDialog_g.fnShowGeneralInformationDialog(generalJqDialog_g.commonTitleInformation,
							errList[result], null, null);
					clearField();
				 } else {
					generalJqDialog_g.fnShowGeneralAlertDialog(generalJqDialog_g.commonTitleError,
							errList[result], null);					 
				 }
			 },
			 error:  function (e) {
				generalJqDialog_g.fnShowGeneralAlertDialog(
						generalJqDialog_g.commonTitleError, '<spring:message code="http.error.title"/>', null);
			 }
		});
	}
	function clearField() {
		$("#oldpwd").val("");
		$("#newpwd").val("");
		$("#newpwd1").val("");
	}
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
	</script>
	<form style="margin:0" id="userForm" action="" method="post">
	<div class="content">
		<div class="title">
		<div class="title-left">
			<spring:message code="changepwd.label.title"/>
		</div>
		<div class="title-right">
		</div>
		</div>
		<div class="body" style="padding-left:50px">
		<div class="row">
			<font style="color:red;font-weight: bold"><spring:message code="changepwd.label.user"/></font> ${webUser.displayName}
		</div>
<%-- 		<div class="row">
			<div class="cell">
				<spring:message code="changepwd.label.subid"/>
			</div>
			<div class="cell">
				<input class='pwd' type="text" name="email" value="${webUser.getSubId()}" disabled>
			</div>
		</div> --%>
		<div class="row">
			<div class="cell">
				<spring:message code="changepwd.label.oldpwd"/>
			</div>
			<div class="cell">
				<input class='pwd' type="password" name="oldpwd" id="oldpwd" value="">
			</div>
		</div>
		<div class="row">
			<div class="cell">
				<spring:message code="changepwd.label.newpwd"/>
			</div>
			<div class="cell">
				<input class='pwd' type="password" name="newpwd" id="newpwd" value="">
			</div>
		</div>
		<div class="row">
			<div class="cell">
				<spring:message code="changepwd.label.confirm.pwd"/>
			</div>
			<div class="cell">
				<input class='pwd' type="password" name="newpwd1" id="newpwd1" value="">
			</div>
		</div>
		<div class="row">
		<input id="butSave" name="Save" value="<spring:message code="common.button.save"/>" type="button" class="ui-button ui-widget ui-state-default ui-corner-all but" onClick="savePwd()"/>
		<input id="butCancel" name="Cancel" value="<spring:message code="common.button.cancel"/>" type="button" class="ui-button ui-widget ui-state-default ui-corner-all but" onClick="cancelPwd()"/>
		</div>
		</div>
	</div>
	<sec:csrfInput />
	</form>
	</tiles:putAttribute>
</tiles:insertDefinition>