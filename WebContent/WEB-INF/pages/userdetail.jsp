<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@page import="com.tradelink.biometric.r2fas.portal.security.model.WebUser" %>
<%@page import="com.tradelink.biometric.r2fas.portal.utils.AuthUtils" %>
<tiles:insertDefinition name="mainTemplate">
	<tiles:putAttribute name="body">
	<style>
	div.heading {
		font-weight: bold;
	}
	div.row {
		line-height:35px;
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
	select.notif {
		width: 80px;
	}
	.userRole {
		/*-webkit-appearance: none;*/
		height: 25px;
		width: 210px;
	}
	</style>
	<form style="margin:0" id="loginEx" name="loginEx" action="" method="post">
	<div class="content">
		<div class="title">
		<div class="title-left">
			<c:if test="${!isModify}"><spring:message code="accountmanage.label.createuser"/></c:if>
			<c:if test="${isModify}"><spring:message code="accountmanage.label.modifyuser"/></c:if>
		</div>
		<div class="title-right"></div>
		</div>
		<div class="body" style="padding-left:50px">
		<div class="row heading">
			<spring:message code="accountmanage.label.personalinfo"/>
		</div>
		
		
		
		<div class="row">
			<div class="cell">
				<spring:message code="accountmanage.label.userid"/>
			</div>
			<div class="cell">
				<c:if test="${!isModify}"><input class='pwd' type="text" name="userId" id="userId" value="${user.userId}" maxlength="100"></c:if>
				<c:if test="${isModify}"><input disabled class='pwd' type="text" name="userIdReadonly" id="euserIdReadonly" value="${user.userId}" maxlength="100"><input type="hidden" name="userId" id="userId" value='<c:out value="${user.userId}"/>'></c:if>
			</div>
		</div>
		<div class="row">
			<div class="cell">
				<spring:message code="accountmanage.label.name"/>
			</div>
			<div class="cell">
				<input class='pwd' type="text" name="displayName" id="displayName" value='<c:out value="${user.displayName}"/>' maxlength="60">
			</div>
		</div><c:if test="${not empty oldPwdChk && oldPwdChk == 'true'}">
		<div class="row">
			<div class="cell">
				<spring:message code="accountmanage.label.oldpwd"/>
			</div>
			<div class="cell">
				<input class='pwd' type="password" name="oldPassword" id="oldPassword" value="" maxlength="20">
			</div>
		</div></c:if>
		<div class="row">
			<div class="cell">
				<spring:message code="accountmanage.label.password"/>
			</div>
			<div class="cell">
				<input class='pwd' type="password" name="password" id="password" value="" maxlength="20">
			</div>
		</div>
		<div class="row">
			<div class="cell">
				<spring:message code="accountmanage.label.confirmpwd"/>
			</div>
			<div class="cell">
				<input class='pwd' type="password" name="passwordConfirm" id="passwordConfirm" value="" maxlength="20">
			</div>
		</div>
		<c:if test="${user.userRole != 'ROLE_ADMIN'}">
		<div class="row">
			<div class="cell">
				<spring:message code="accountmanage.label.role"/>
			</div>
			
			<div class="cell">
				<select class="userRole" name="userRole" id="userRole">
					<option value="ROLE_HR" id="roleHR" <c:if test="${user.userRole == 'ROLE_HR'}">selected="selected"</c:if>><spring:message code="accountmanage.label.role.ROLE_HR"/></option>
					<option value="ROLE_IT" id="roleIT" <c:if test="${user.userRole == 'ROLE_IT'}">selected="selected"</c:if>><spring:message code="accountmanage.label.role.ROLE_IT"/></option>
				</select>
			</div>
		</div>
		</c:if>

		<div class="row">
		<input id="butSave" name="Save" value="<spring:message code="common.button.save"/>" type="button" class="ui-button ui-widget ui-state-default ui-corner-all but" onClick="save(${isModify})"/>
		<input id="butCancel" name="Cancel" value="<spring:message code="common.button.cancel"/>" type="button" class="ui-button ui-widget ui-state-default ui-corner-all but" onClick="cancel()"/>
		</div>
		</div>
	</div>
	<sec:csrfInput />
	</form>
	<script>
	function changeFormLocale(locale) {
		generalJqDialog_g.fnShowGeneralConfirmDialog(
				"<spring:message code="dialog.changelocale.title" javaScriptEscape="true"/>",
				"<spring:message code="dialog.changelocale.msg" javaScriptEscape="true"/>",
				generalJqDialog_g.buttonConfirm,
				function() {
					gohome(locale);
		});
	}
	var errList = new Array();
	errList[0] = '<spring:message code="userdetail.result.success${saveType}"/>';<c:forEach var = "i" begin = "1" end = "${totalNoOfErrMsg}"><c:choose><c:when test = "${i == 12}">
	errList[${i}] = '<spring:message code="userdetail.error.${i}" arguments="${maxNoOfNormalUser}"/>';</c:when><c:otherwise>
	errList[${i}] = '<spring:message code="userdetail.error.${i}"/>';</c:otherwise></c:choose></c:forEach>

	function cancel() {
		window.location = '<c:url value="/accountmanage.do"/>';
	}
	function back() {
		window.location = '<c:url value="/accountmanage.do"/>';
	}
	function save(isModify) {
		if ($("#displayName").val() == "") {
			generalJqDialog_g.fnShowGeneralAlertDialog(generalJqDialog_g.commonTitleError,
					errList[3],
					function() {
						$("#displayName").focus();
					});
			return;
		}
		if (!isModify) {
			if ($("#password").val() == "") {
				generalJqDialog_g.fnShowGeneralAlertDialog(generalJqDialog_g.commonTitleError,
						errList[4],
						function() {
							$("#password").focus();
						});
				return;
			}
			if ($("#passwordConfirm").val() == "") {
				generalJqDialog_g.fnShowGeneralAlertDialog(generalJqDialog_g.commonTitleError,
						errList[5],
						function() {
							$("#passwordConfirm").focus();
						});
				return;
			}			
		}
		if ($("#password").val() != $("#passwordConfirm").val()) {
			generalJqDialog_g.fnShowGeneralAlertDialog(generalJqDialog_g.commonTitleError,
					errList[6], null);
			return;
		}<c:if test="${not empty oldPwdChk && oldPwdChk == 'true'}">
		if ($("#oldPassword").val() == "") {
			if ($("#password").val() != "") {
				generalJqDialog_g.fnShowGeneralAlertDialog(generalJqDialog_g.commonTitleError,
						errList[17], null);
				return;
			}
		} else {
			if ($("#oldPassword").val() == $("#password").val()) {
				generalJqDialog_g.fnShowGeneralAlertDialog(generalJqDialog_g.commonTitleError,
						errList[9], null);
				return;
			}
		}</c:if>
		var url = '<c:url value="/adduser.do"/>';
		if (isModify) {
			url = '<c:url value="/edituser.do"/>';
		}
		$.ajax({
			 url: url,
			 method :"post",
			 dataType:"json",
			 data: $("#loginEx").serialize(),
			 success: function(result) {
				 if (result == 0) {
					generalJqDialog_g.fnShowGeneralInformationDialog(generalJqDialog_g.commonTitleInformation,
							errList[result],
							function() {
								back();
							},
							function() {
								back();
							});
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
	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>