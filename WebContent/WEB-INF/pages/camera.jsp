<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<tiles:insertDefinition name="mainTemplate">
	<tiles:putAttribute name="body">
		<style>
	font.header {
		color: black;
	}
	div.subbut {
		width: 100%;
		text-align: left;
		padding-top: 10px;
		padding-bottom: 10px;
	}
	div.cameraList .title {
		border-bottom: 2px solid #898989;
	}
	div.rowbar {
		line-height:25px;
		width: 100%;
		float: left;
	}
	div.row {
		line-height:30px;
		width: 100%;
		float: left;
		border-bottom: 1px solid #dfdfdf
	}
	
	div.btnrow-left {
		float:	left;
		padding-top: 10px;
		padding-bottom: 10px;
	}
	
	div.btnrow-right {
		float:	right;
		padding-top: 10px;
		padding-bottom: 10px;
	}
	
	div.row-left {

	}
	
	div.row-right {

	}
	
	div.cellbold {
		font-weight: bold;
	}

	div.cell1 {
		width: 5%;
		float: left;
		white-space: normal;
	}
	div.cell2 {
		width: 60%;
		float: left;
		white-space: normal;
	}
	div.cell3 {
		width: 25%;
		float: left;
		white-space: normal;
	}
	div.cell4 {
		width: 10%;
		float: left;
		white-space: normal;
	}

	a.mgr {
		text-decoration: none;
		color: black;
	}
	a.mgr:link {
		text-decoration: none;
	}
	a.mgr:active {
		text-decoration: none;
	}
	a.mgr:visited {
		text-decoration: none;
	}
	a.mgr:hover {
		text-decoration: none;
	}
	.fancybox-slide--iframe .fancybox-content {
		width  : 640px;
		height : 480px;
		max-width  : 640px;
		max-height : 480px;
		border: 5px solid #4196E5;
		box-shadow: 5px 5px 3px #888888;
		border-radius: 10px;
		margin: 0;
	}
	.fancybox-iframe {
		border-radius: 10px;
	}
	</style>
	<div class="content">
		<div class="title">
			<div class="title-left">
				<spring:message code="camera.title"/>
			</div>
			<div class="title-right">
			</div>
		</div>
		<div class="body">
			<div class="btnrow-left">
				<input id="butAdd" name="Add" value="<spring:message code="common.button.add"/>" type="button" class="ui-button ui-widget ui-state-default ui-corner-all but" onClick="addCamera()"/>
				<input id="butModify" name="Modify" value="<spring:message code="common.button.modify"/>" type="button" class="ui-button ui-widget ui-state-default ui-corner-all but" onClick="modifyCamera()"/>
				<input id="butDelete" name="Delete" value="<spring:message code="common.button.delete"/>" type="button" class="ui-button ui-widget ui-state-default ui-corner-all but" onClick="deleteCamera()"/>
				<input id="butDeactive" name="Deactive" value="<spring:message code="common.button.deactive"/>" type="button" class="ui-button ui-widget ui-state-default ui-corner-all but" onClick="deactiveCamera()"/>
				<input id="butActive" name="Active" value="<spring:message code="common.button.active"/>" type="button" class="ui-button ui-widget ui-state-default ui-corner-all but" onClick="activeCamera()"/>
			</div>			
			<div class="btnrow-right">
			<form style="margin:0" id="cameraSearchForm" action="<c:url value="/camera.do"/>" method="post">
				<!--  <input type="text" style="height: 22px;" id="cardNumber" name="cardNumber"  placeholder="<spring:message code="facehistory.label.search.cardNumber"/>" value="${cardNumber}"/>
				<input id="butSearch" name="Search" value="<spring:message code="common.button.search"/>" type="button" class="ui-button ui-widget ui-state-default ui-corner-all but" onClick="search()"  align="right"/>-->
				<input type="hidden" id="pageSize" name="pageSize" value="${pageSize}"/>
				<input type="hidden" id="pageNo" name="pageNo" value="${pageNo}"/>
				<input type="hidden" id="orderBy" name="orderBy" value="${orderBy}"/>
				<input type="hidden" id="ordering" name="ordering" value="${ordering}"/>
	 		<sec:csrfInput />
			</form>
			</div>

			<form style="margin:0" id="cameraListForm">
			<div class="cameraList">
				<div class="rowbar" style="text-align:right">
				<c:if test="${pageNo > 1}"><a href="javascript:void(0)" onClick="goToPage(${pageNo - 1})"><font style="color:black">&lt;&lt;</font></a></c:if>&nbsp;
				<spring:message code="common.label.page" arguments="${pageNo},${totalPageNo}"/> <c:if test="${pageNo < totalPageNo}"><a href="javascript:void(0)" onClick="goToPage(${pageNo + 1})"><font style="color:black">&gt;&gt;</font></a></c:if>
				&nbsp;| <spring:message code="common.label.total" arguments="${totalNo}"/>
				</div>
				
				<div class="row title">
					<div class="cell1 cellbold">&nbsp;
					</div>
					<div class="cell2 cellbold">
					<a href="javascript:void(0)" onClick="changeOrder('url')"><font class="header"><spring:message code="camera.label.url"/></font></a>
					<c:if test="${orderBy eq 'url' }">
					<c:if test="${ordering eq 'ASC' }">
					<span style="margin-top: 0px;overflow: hidden;position:absolute;display:inline;cursor: pointer !important" class="ui-icon-asc ui-icon ui-icon-triangle-1-n"></span>
					</c:if>
					<c:if test="${ordering eq 'DESC' }">
					<span style="margin-top: 0px;overflow: hidden;position:absolute;display:inline;cursor: pointer !important" class="ui-icon-desc ui-icon ui-icon-triangle-1-s"></span>
					</c:if>
					</c:if>
					</div>
					<div class="cell3 cellbold">
					<a href="javascript:void(0)" onClick="changeOrder('cameraName')"><font class="header"><spring:message code="camera.label.cameraName"/></font></a>
					<c:if test="${orderBy eq 'cameraName' }">
					<c:if test="${ordering eq 'ASC' }">
					<span style="margin-top: 0px;overflow: hidden;position:absolute;display:inline;cursor: pointer !important" class="ui-icon-asc ui-icon ui-icon-triangle-1-n"></span>
					</c:if>
					<c:if test="${ordering eq 'DESC' }">
					<span style="margin-top: 0px;overflow: hidden;position:absolute;display:inline;cursor: pointer !important" class="ui-icon-desc ui-icon ui-icon-triangle-1-s"></span>
					</c:if>
					</c:if>
					</div>
					<div class="cell4 cellbold">
					<a href="javascript:void(0)" onClick="changeOrder('cameraStatus')"><font class="header"><spring:message code="camera.label.cameraStatus"/></font></a>
					<c:if test="${orderBy eq 'cameraStatus' }">
					<c:if test="${ordering eq 'ASC' }">
					<span style="margin-top: 0px;overflow: hidden;position:absolute;display:inline;cursor: pointer !important" class="ui-icon-asc ui-icon ui-icon-triangle-1-n"></span>
					</c:if>
					<c:if test="${ordering eq 'DESC' }">
					<span style="margin-top: 0px;overflow: hidden;position:absolute;display:inline;cursor: pointer !important" class="ui-icon-desc ui-icon ui-icon-triangle-1-s"></span>
					</c:if>
					</c:if>
					</div>
				</div>
				<c:if test="${totalNo eq 0}">
				<div class="row" style="text-align:center">
					<spring:message code="common.label.norec"/>
				</div>
				</c:if>
				<c:if test="${totalNo > 0}">
				<c:forEach items="${resultList}" var="camera" varStatus="loop">
				<div class="row">
				<div class="row-right">
					<div class="cell1">
					<input type="checkbox" name="id" value='<c:out value="${camera.id}"/>'/>
					</div>					
					<div class="cell2">
					<c:choose>
    				<c:when test="${empty camera.url}">        			
        			&nbsp
    				</c:when>
    				<c:otherwise>
        			<!--<c:out value="${faceUser.cardNumber}"/>-->
        			<a class="mgr" href="javascript:void(0)" onClick="modifyCamera('${camera.id}')">${camera.url}</a>&nbsp;
    				</c:otherwise>
					</c:choose>
					</div>
					<div class="cell3">
					<c:choose>
    				<c:when test="${empty camera.cameraName}">        			
        			&nbsp
    				</c:when>
    				<c:otherwise>
        			<c:out value="${camera.cameraName}"/>
    				</c:otherwise>
					</c:choose>
					</div>
					<div class="cell4">
					<c:choose>
    				<c:when test="${empty camera.cameraStatus}">        			
        			&nbsp
    				</c:when>
    				<c:otherwise>
        			<c:out value="${camera.cameraStatus}"/>
    				</c:otherwise>
					</c:choose>
					</div>
					<input type="hidden" id="cameraStatus${loop.index}" name="cameraStatus${loop.index}" value="${camera.cameraStatus}"/>	
				</div>
				</div>
				</c:forEach>
				</c:if>
			</div>
			<sec:csrfInput />
			</form>
			<form style="margin:0" id="cameraEditForm" action="<c:url value="/modifycamera.do"/>" method="post">
			<input type="hidden" name="id" id="cameraEditFormId" value=''/>
			<sec:csrfInput />
			</form>
		</div>
	</div>
	<script>
	$(document).ready(function() {
		$('[data-fancybox]').fancybox({
			toolbar  : false,
			smallBtn : false,
			modal : true,
			iframe : {
				preload : false
			}
		})
	})
	
	function addCamera() {
		window.location='<c:url value="/newcamera.do"/>';
	}
	
 	function modifyCamera(_id) {
		var id;
		
		if (_id == null) {
			if ($("#cameraListForm input[type=checkbox]:checked").length == 0) {
				return;
			}
			if ($("#cameraListForm input[type=checkbox]:checked").length != 1) {
				generalJqDialog_g.fnShowGeneralAlertDialog(
					generalJqDialog_g.commonTitleError, '<spring:message code="userdetail.error.toomany.user"/>', null);
				return;
			}
			id = $("#cameraListForm input[type=checkbox]:checked").val();
		} else {
			id = _id;
		}
		
		$("#cameraEditFormId").val(id);
		$("#cameraEditForm").submit();
	}
	
	function deleteCamera() {
		if ($("#cameraListForm input[type=checkbox]:checked").length == 0) return;
 		generalJqDialog_g.fnShowGeneralConfirmDialog(
				"<spring:message code="camera.label.delete.title" javaScriptEscape="true"/>",
				"<spring:message code="camera.label.delete.confirm" javaScriptEscape="true"/>".replace("{0}",$("#cameraListForm input[type=checkbox]:checked").length),
				generalJqDialog_g.buttonConfirm,
				function() {
					$.ajax({
						url: '<c:url value="/deletecamera.do"/>',
						method :"post",
						dataType:"json",
						data: $("#cameraListForm").serialize(),
						success: function(result) {
							generalJqDialog_g.fnShowGeneralInformationDialog('<spring:message code="dialog.commonTitle.information"/>',
									(($("#cameraListForm input[type=checkbox]:checked").length == 1)?'<spring:message code="camera.label.delete.success1"/>':'<spring:message code="camera.label.delete.success2"/>'),
									function() {
										refreshPage();
									},
									function() {
										refreshPage();
									}
								);
						},
						error: function() {
							generalJqDialog_g.fnShowGeneralAlertDialog(
									generalJqDialog_g.commonTitleError, '<spring:message code="http.error.title"/>', null);	
						}
					});
				}
			); 
	}
	
	function deactiveCamera() {
		if ($("#cameraListForm input[type=checkbox]:checked").length == 0) return;
		var isNotActive = false;
		$("#cameraListForm input[type=checkbox]").each(function(i) {
			if (this.checked) {
				if ($("#cameraStatus"+i).val() == 'DEACTIVE') {
					isNotActive = true;
				} 
			}
		});
		if (isNotActive) {
			generalJqDialog_g.fnShowGeneralAlertDialog(
					generalJqDialog_g.commonTitleError, '<spring:message code="camera.label.deactive.error"/>', null);	
			return;			
		}
		
 		generalJqDialog_g.fnShowGeneralConfirmDialog(
				"<spring:message code="camera.label.deactive.title" javaScriptEscape="true"/>",
				"<spring:message code="camera.label.deactive.confirm" javaScriptEscape="true"/>".replace("{0}",$("#cameraListForm input[type=checkbox]:checked").length),
				generalJqDialog_g.buttonConfirm,
				function() {
					$.ajax({
						url: '<c:url value="/deactivecamera.do"/>',
						method :"post",
						dataType:"json",
						data: $("#cameraListForm").serialize(),
						success: function(result) {
							generalJqDialog_g.fnShowGeneralInformationDialog('<spring:message code="dialog.commonTitle.information"/>',
									(($("#cameraListForm input[type=checkbox]:checked").length == 1)?'<spring:message code="camera.label.deactive.success1"/>':'<spring:message code="camera.label.deactive.success2"/>'),
									function() {
										refreshPage();
									},
									function() {
										refreshPage();
									}
								);
						},
						error: function() {
							generalJqDialog_g.fnShowGeneralAlertDialog(
									generalJqDialog_g.commonTitleError, '<spring:message code="http.error.title"/>', null);	
						}
					});
				}
			); 
	}
	
	function activeCamera() {
		if ($("#cameraListForm input[type=checkbox]:checked").length == 0) return;
		var isActive = false;
		$("#cameraListForm input[type=checkbox]").each(function(i) {
			if (this.checked) {
				if ($("#cameraStatus"+i).val() == 'ACTIVE') {
					isActive = true;
				} 
			}
		});
		if (isActive) {
			generalJqDialog_g.fnShowGeneralAlertDialog(
					generalJqDialog_g.commonTitleError, '<spring:message code="camera.label.active.error"/>', null);	
			return;			
		}
		
 		generalJqDialog_g.fnShowGeneralConfirmDialog(
				"<spring:message code="camera.label.active.title" javaScriptEscape="true"/>",
				"<spring:message code="camera.label.active.confirm" javaScriptEscape="true"/>".replace("{0}",$("#cameraListForm input[type=checkbox]:checked").length),
				generalJqDialog_g.buttonConfirm,
				function() {
					$.ajax({
						url: '<c:url value="/activecamera.do"/>',
						method :"post",
						dataType:"json",
						data: $("#cameraListForm").serialize(),
						success: function(result) {
							generalJqDialog_g.fnShowGeneralInformationDialog('<spring:message code="dialog.commonTitle.information"/>',
									(($("#cameraListForm input[type=checkbox]:checked").length == 1)?'<spring:message code="camera.label.active.success1"/>':'<spring:message code="camera.label.active.success2"/>'),
									function() {
										refreshPage();
									},
									function() {
										refreshPage();
									}
								);
						},
						error: function() {
							generalJqDialog_g.fnShowGeneralAlertDialog(
									generalJqDialog_g.commonTitleError, '<spring:message code="http.error.title"/>', null);	
						}
					});
				}
			); 
	}



	function changeOrder(orderBy) {
		if ($("#orderBy").val() == orderBy) {
			if ($("#ordering").val() == "ASC") {
				$("#ordering").val("DESC");
			} else {
				$("#ordering").val("ASC");
			}
		} else {
			$("#orderBy").val(orderBy);
			$("#ordering").val("ASC");
		}
		goToPage(1);
	}
	
	function search(){
		$("#pageNo").val("1");
		$("#cameraSearchForm").submit();
	}
	
	function goToPage(pageNo) {
		blockPageUiProcessing();
		$("#pageNo").val(pageNo);
		$("#cameraSearchForm").submit();
	}
	
	function refreshPage() {
		window.location = '<c:url value="/camera.do"/>';
	}
	
	$('#cameraSearchForm').keypress(function (e) {
		  if (e.which == 13) {
			$("#pageNo").val("1");
		    $("#cameraSearchForm").submit();
		  }
		});
	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
