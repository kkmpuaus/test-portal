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
	div.cell1 {
		width: 150px;
		float: left;
	}
	div.cell2 {
		width: 300px;
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
	.galleryType {
		/*-webkit-appearance: none;*/
		height: 25px;
		width: 210px;
	}
	
	</style>
	<form style="margin:0" id="cameraUploadForm" name="cameraUploadForm" action="" method="post">
	<div class="content">
		<div class="title">
		<div class="title-left">
			<c:if test="${!isModify}"><spring:message code="camera.label.createcamera"/></c:if>
			<c:if test="${isModify}"><spring:message code="camera.label.modifycamera"/></c:if>
		</div>
		<div class="title-right"></div>
		</div>
		<div class="body" style="padding-left:50px">
		<div class="row heading">
			<spring:message code="camera.label.camerainfo"/>
		</div>
		

		<input type="hidden" name="id" id="id" value="${camera.id}">
		<input type="hidden" name="cameraStatus" id="cameraStatus" value="${camera.cameraStatus}">
		<div class="row">
			<div class="cell1">
				<spring:message code="camera.label.url"/>
			</div>
			<div class="cell2">
				<input class='pwd' type="text" name="url" id="url" value="${camera.url}" maxlength="50">
			</div>
		</div>
		<div class="row">
			<div class="cell1">
				<spring:message code="camera.label.cameraName"/>
			</div>
			<div class="cell2">
				<input class='pwd' type="text" name="cameraName" id="cameraName" value="${camera.cameraName}" maxlength="30">
			</div>
		</div>
		<div class="row">
			<div class="cell1">
				<spring:message code="camera.label.minFaceSize"/>
			</div>
			<div class="cell2">
				<c:if test="${!isModify}">
					<input class='pwd' type="number" name="minFaceSize" id="minFaceSize" value="170" step="10" min="0">
				</c:if>
				<c:if test="${isModify}">
					<input class='pwd' type="number" name="minFaceSize" id="minFaceSize" value="${camera.minFaceSize}" value="170" step="10" min="0">
				</c:if>
			</div>
		</div>
		<div class="row">
			<div class="cell1">
				<spring:message code="camera.label.minLiveness"/>
			</div>
			<div class="cell2">
				<c:if test="${!isModify}">
					<input class='pwd' type="number" name="minLiveness" id="minLiveness" value="0.5" step="0.1" min="0.0" max="1.0">
				</c:if>
				<c:if test="${isModify}">
					<input class='pwd' type="number" name="minLiveness" id="minLiveness" value="${camera.minLiveness}"step="0.1" min="0.0" max="1.0">
				</c:if>
			</div>
		</div>
		
		<div class="row heading">
			<spring:message code="camera.label.wiegandinfo"/>
		</div>
		

		<c:if test="${!isModify}">
		<c:forEach begin="0" end="1" varStatus="loop">
		<div class="row">
			<div class="cell1">
				<spring:message code="camera.label.ip"/>
			</div>
			<div class="cell2">
				<input class='pwd' type="text" name="wiegandIps" id="wiegandIps" maxlength="100">
			</div>
		</div>
		<div class="row">
			<div class="cell1">
				<spring:message code="camera.label.port"/>
			</div>
			<div class="cell2">
				<input class='pwd' type="number" name="wiegandPorts" id="wiegandPorts" value="0" min="0" max="65536">
			</div>
		</div>
		<div class="row">
			<div class="cell1">
				<spring:message code="camera.label.desc"/>
			</div>
			<div class="cell2">
				<input class='pwd' type="text" name="wiegandDescs" id="wiegandDescs" maxlength="30">
			</div>
		</div>
		</c:forEach>
		</c:if>
		

 		<c:if test="${isModify}">
<%-- 		<c:if test="${wiegandTotalNo > 0}"> --%>
		<c:forEach items="${wiegandList}" var="wieganddevice" varStatus="loop">
		<input type="hidden" id="wiegandId" name="wiegandIds" value="${wieganddevice.id}"/>
		<div class="row">
			<div class="cell1">
				<spring:message code="camera.label.ip"/>
			</div>
			<div class="cell2">
				<input class='pwd' type="text" name="wiegandIps" id="wiegandIps" value="${wieganddevice.ip}" maxlength="100">
			</div>
		</div>
		<div class="row">
			<div class="cell1">
				<spring:message code="camera.label.port"/>
			</div>
			<div class="cell2">
				<input class='pwd' type="number" name="wiegandPorts" id="wiegandPorts" value="${wieganddevice.port}" min="0" max="65536">
			</div>
		</div>
		<div class="row">
			<div class="cell1">
				<spring:message code="camera.label.desc"/>
			</div>
			<div class="cell2">
				<input class='pwd' type="text" name="wiegandDescs" id="wiegandDescs" value="${wieganddevice.description}" maxlength="30">
		</div>
		</div>
		</c:forEach>
<%-- 		</c:if> --%>
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
	$(document).ready(function() {

	})

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
	errList[0] = '<spring:message code="camera.result.success${saveType}"/>';
	<c:forEach var = "i" begin = "1" end = "${totalNoOfErrMsg}">
		errList[${i}] = '<spring:message code="camera.error.${i}"/>';
	</c:forEach>

	function cancel() {
		window.location = '<c:url value="/camera.do"/>';
	}
	function back() {
		window.location = '<c:url value="/camera.do"/>';
	}
	function save(isModify) {
		if ($("#url").val() == "") {
			generalJqDialog_g.fnShowGeneralAlertDialog(generalJqDialog_g.commonTitleError,
					errList[1],
					function() {
						$("#url").focus();
					});
			return;
		}
		
		if ($("#minFaceSize").val() == "") {
			generalJqDialog_g.fnShowGeneralAlertDialog(generalJqDialog_g.commonTitleError,
					errList[2],
					function() {
						$("#minFaceSize").focus();
					});
			return;
		}
		
		if(!isInt($("#minFaceSize").val())){
			generalJqDialog_g.fnShowGeneralAlertDialog(generalJqDialog_g.commonTitleError,
					errList[7],
					function() {
						$("#minFaceSize").focus();
					});
			return;
		}

		if ($("#minLiveness").val() == "") {
			generalJqDialog_g.fnShowGeneralAlertDialog(generalJqDialog_g.commonTitleError,
					errList[3],
					function() {
						$("#minLiveness").focus();
					});
			return;
		}

		var url = '<c:url value="/addcamera.do"/>';
		if (isModify) {
			url = '<c:url value="/editcamera.do"/>';
		}
		
		$.ajax({
			 url: url,
			 method :"post",
			 dataType: "json",
			 data: $("#cameraUploadForm").serialize(),
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

	function isInt(value) {
		  return !isNaN(value) && 
		         parseInt(Number(value)) == value && 
		         !isNaN(parseInt(value, 10));
		}
	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>