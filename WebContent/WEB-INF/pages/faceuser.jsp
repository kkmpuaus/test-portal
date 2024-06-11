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
	div.faceUserList .title {
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
		width: 10%;
		float: left;
		white-space: normal;
	}
	div.cell3 {
		width: 20%;
		float: left;
		white-space: normal;
	}
	div.cell4 {
		width: 15%;
		float: left;
		white-space: normal;
	}
	div.cell5 {
		width: 15%;
		float: left;
		white-space: normal;
	}
	div.cell6 {
		width: 15%;
		float: left;
		white-space: normal;
	}
	div.cell7 {
		width: 10%;
		float: left;
		white-space: normal;
	}
/* 	div.cell8 {
		width: 13%;
		float: left;
		white-space: normal;
	} */
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
				<spring:message code="faceuser.title"/>
			</div>
			<div class="title-right">
			</div>
		</div>
		<div class="body">
			<div class="btnrow-left">
				<input id="butAdd" name="Add" value="<spring:message code="common.button.add"/>" type="button" class="ui-button ui-widget ui-state-default ui-corner-all but" onClick="addFaceUser()"/>
				<input id="butModify" name="Modify" value="<spring:message code="common.button.modify"/>" type="button" class="ui-button ui-widget ui-state-default ui-corner-all but" onClick="modifyFaceUser()"/>
				<input id="butDelete" name="Delete" value="<spring:message code="common.button.delete"/>" type="button" class="ui-button ui-widget ui-state-default ui-corner-all but" onClick="deleteFaceUser()"/>
				<input id="butDeactive" name="Deactive" value="<spring:message code="common.button.deactive"/>" type="button" class="ui-button ui-widget ui-state-default ui-corner-all but" onClick="deactiveFaceUser()"/>
				<input id="butActive" name="Active" value="<spring:message code="common.button.active"/>" type="button" class="ui-button ui-widget ui-state-default ui-corner-all but" onClick="activeFaceUser()"/>
			</div>
			
			<div class="btnrow-right">
			<form style="margin:0" id="faceUserSearchForm" action="<c:url value="/faceuser.do"/>" method="post">
				<input type="text" style="height: 22px;" id="cardNumber" name="cardNumber"  placeholder="<spring:message code="faceuser.label.search.cardNumber"/>" value="${personName}"/>
				<input type="text" style="height: 22px;" id="cardNumber" name="personName"  placeholder="<spring:message code="faceuser.label.search.personName"/>" value="${cardNumber}"/>
				<input id="butSearch" name="Search" value="<spring:message code="common.button.search"/>" type="button" class="ui-button ui-widget ui-state-default ui-corner-all but" onClick="search()"  align="right"/>
				<input type="hidden" id="pageSize" name="pageSize" value="${pageSize}"/>
				<input type="hidden" id="pageNo" name="pageNo" value="${pageNo}"/>
				<input type="hidden" id="orderBy" name="orderBy" value="${orderBy}"/>
				<input type="hidden" id="ordering" name="ordering" value="${ordering}"/>
	 		<sec:csrfInput />
			</form>
			</div>

			<form style="margin:0" id="faceUserListForm">
			<div class="faceUserList">
				<div class="rowbar" style="text-align:right">
				<c:if test="${pageNo > 1}"><a href="javascript:void(0)" onClick="goToPage(${pageNo - 1})"><font style="color:black">&lt;&lt;</font></a></c:if>&nbsp;
				<spring:message code="common.label.page" arguments="${pageNo},${totalPageNo}"/> <c:if test="${pageNo < totalPageNo}"><a href="javascript:void(0)" onClick="goToPage(${pageNo + 1})"><font style="color:black">&gt;&gt;</font></a></c:if>
				&nbsp;| <spring:message code="common.label.total" arguments="${totalNo}"/>
				</div>
				
				<div class="row title">
					<div class="cell1 cellbold">&nbsp;
					</div>
					<div class="cell2 cellbold">
					<a href="javascript:void(0)" onClick="changeOrder('cardNumber')"><font class="header"><spring:message code="faceuser.label.cardNumber"/></font></a>
					<c:if test="${orderBy eq 'cardNumber' }">
					<c:if test="${ordering eq 'ASC' }">
					<span style="margin-top: 0px;overflow: hidden;position:absolute;display:inline;cursor: pointer !important" class="ui-icon-asc ui-icon ui-icon-triangle-1-n"></span>
					</c:if>
					<c:if test="${ordering eq 'DESC' }">
					<span style="margin-top: 0px;overflow: hidden;position:absolute;display:inline;cursor: pointer !important" class="ui-icon-desc ui-icon ui-icon-triangle-1-s"></span>
					</c:if>
					</c:if>
					</div>
					<div class="cell3 cellbold">
					<a href="javascript:void(0)" onClick="changeOrder('personName')"><font class="header"><spring:message code="faceuser.label.personName"/></font></a>
					<c:if test="${orderBy eq 'personName' }">
					<c:if test="${ordering eq 'ASC' }">
					<span style="margin-top: 0px;overflow: hidden;position:absolute;display:inline;cursor: pointer !important" class="ui-icon-asc ui-icon ui-icon-triangle-1-n"></span>
					</c:if>
					<c:if test="${ordering eq 'DESC' }">
					<span style="margin-top: 0px;overflow: hidden;position:absolute;display:inline;cursor: pointer !important" class="ui-icon-desc ui-icon ui-icon-triangle-1-s"></span>
					</c:if>
					</c:if>
					</div>
					<div class="cell4 cellbold">
					<a href="javascript:void(0)" onClick="changeOrder('galleryType')"><font class="header"><spring:message code="faceuser.label.galleryType"/></font></a>
					<c:if test="${orderBy eq 'galleryType' }">
					<c:if test="${ordering eq 'ASC' }">
					<span style="margin-top: 0px;overflow: hidden;position:absolute;display:inline;cursor: pointer !important" class="ui-icon-asc ui-icon ui-icon-triangle-1-n"></span>
					</c:if>
					<c:if test="${ordering eq 'DESC' }">
					<span style="margin-top: 0px;overflow: hidden;position:absolute;display:inline;cursor: pointer !important" class="ui-icon-desc ui-icon ui-icon-triangle-1-s"></span>
					</c:if>
					</c:if>
					</div>
					<div class="cell5 cellbold">
					<a href="javascript:void(0)" onClick="changeOrder('created')"><font class="header"><spring:message code="faceuser.label.created"/></font></a>
					<c:if test="${orderBy eq 'created' }">
					<c:if test="${ordering eq 'ASC' }">
					<span style="margin-top: 0px;overflow: hidden;position:absolute;display:inline;cursor: pointer !important" class="ui-icon-asc ui-icon ui-icon-triangle-1-n"></span>
					</c:if>
					<c:if test="${ordering eq 'DESC' }">
					<span style="margin-top: 0px;overflow: hidden;position:absolute;display:inline;cursor: pointer !important" class="ui-icon-desc ui-icon ui-icon-triangle-1-s"></span>
					</c:if>
					</c:if>
					</div>
 					<div class="cell6 cellbold">
					<a href="javascript:void(0)" onClick="changeOrder('updated')"><font class="header"><spring:message code="faceuser.label.updated"/></font></a>
					<c:if test="${orderBy eq 'updated' }">
					<c:if test="${ordering eq 'ASC' }">
					<span style="margin-top: 0px;overflow: hidden;position:absolute;display:inline;cursor: pointer !important" class="ui-icon-asc ui-icon ui-icon-triangle-1-n"></span>
					</c:if>
					<c:if test="${ordering eq 'DESC' }">
					<span style="margin-top: 0px;overflow: hidden;position:absolute;display:inline;cursor: pointer !important" class="ui-icon-desc ui-icon ui-icon-triangle-1-s"></span>
					</c:if>
					</c:if>
					</div>
					<div class="cell7 cellbold">
					<a href="javascript:void(0)" onClick="changeOrder('userStatus')"><font class="header"><spring:message code="faceuser.label.userStatus"/></font></a>
					<c:if test="${orderBy eq 'userStatus' }">
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
				<c:forEach items="${resultList}" var="faceUser" varStatus="loop">
				<div class="row">
				<div class="row-right">
					<div class="cell1">
					<input type="checkbox" name="cardNumber" value='<c:out value="${faceUser.cardNumber}"/>'/>
					</div>					
					<div class="cell2">
					<c:choose>
    				<c:when test="${empty faceUser.cardNumber}">        			
        			&nbsp
    				</c:when>
    				<c:otherwise>
        			<!--<c:out value="${faceUser.cardNumber}"/>-->
        			<a class="mgr" href="javascript:void(0)" onClick="modifyFaceUser('${faceUser.cardNumber}')">${faceUser.cardNumber}</a>&nbsp;
    				</c:otherwise>
					</c:choose>
					</div>
					<div class="cell3">
					<c:choose>
    				<c:when test="${empty faceUser.personName}">        			
        			&nbsp
    				</c:when>
    				<c:otherwise>
        			<c:out value="${faceUser.personName}"/>
    				</c:otherwise>
					</c:choose>
					</div>
					<div class="cell4">
					<c:choose>
    				<c:when test="${empty faceUser.galleryType}">        			
        			&nbsp
    				</c:when>
    				<c:otherwise>
        			<c:out value="${faceUser.galleryType}"/>
    				</c:otherwise>
					</c:choose>
					</div>
					<div class="cell4">
					<c:choose>
    				<c:when test="${empty faceUser.created}">        			
        			&nbsp
    				</c:when>
    				<c:otherwise>
        			<fmt:formatDate pattern = "yyyy-MM-dd HH:mm:ss" value = "${faceUser.created}" />
    				</c:otherwise>
					</c:choose>
					</div>
					<div class="cell6">
					<c:choose>
    				<c:when test="${empty faceUser.updated}">        			
        			&nbsp
    				</c:when>
    				<c:otherwise>
        			<fmt:formatDate pattern = "yyyy-MM-dd HH:mm:ss" value = "${faceUser.updated}" />
    				</c:otherwise>
					</c:choose>
					</div>
					<%-- <div class="cell7">
					<c:choose>
    				<c:when test="${empty faceUser.archived}">        			
        			&nbsp
    				</c:when>
    				<c:otherwise>
        			<fmt:formatDate pattern = "yyyy-MM-dd HH:mm:ss" value = "${faceUser.archived}" />
    				</c:otherwise>
					</c:choose>
					</div> --%>
					<div class="cell7">
					<c:choose>
    				<c:when test="${empty faceUser.userStatus}">        			
        			&nbsp
    				</c:when>
    				<c:otherwise>
        			<c:out value="${faceUser.userStatus}"/>
    				</c:otherwise>
					</c:choose>
					</div>
					<input type="hidden" id="userStatus${loop.index}" name="userStatus${loop.index}" value="${faceUser.userStatus}"/>	
				</div>
				</div>
				</c:forEach>
				</c:if>
			</div>
			<sec:csrfInput />
			</form>
			<form style="margin:0" id="faceUserEditForm" action="<c:url value="/modifyfaceuser.do"/>" method="post">
			<input type="hidden" name="cardNumber" id="faceUserEditFormCardNumber" value=''/>
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
	
	function addFaceUser() {
		window.location='<c:url value="/newfaceuser.do"/>';
	}
	
 	function modifyFaceUser(_cardNumber) {
		var cardNumber;
		
		if (_cardNumber == null) {
			if ($("#faceUserListForm input[type=checkbox]:checked").length == 0) {
				return;
			}
			if ($("#faceUserListForm input[type=checkbox]:checked").length != 1) {
				generalJqDialog_g.fnShowGeneralAlertDialog(
					generalJqDialog_g.commonTitleError, '<spring:message code="userdetail.error.toomany.user"/>', null);
				return;
			}
			cardNumber = $("#faceUserListForm input[type=checkbox]:checked").val();
		} else {
			cardNumber = _cardNumber;
		}
		
		$("#faceUserEditFormCardNumber").val(cardNumber);
		$("#faceUserEditForm").submit();
	}
	
	function deactiveFaceUser() {
		if ($("#faceUserListForm input[type=checkbox]:checked").length == 0) return;
		var isNotActive = false;
		$("#faceUserListForm input[type=checkbox]").each(function(i) {
			if (this.checked) {
				if ($("#userStatus"+i).val() == 'DEACTIVE') {
					isNotActive = true;
				} 
			}
		});
		if (isNotActive) {
			generalJqDialog_g.fnShowGeneralAlertDialog(
					generalJqDialog_g.commonTitleError, '<spring:message code="faceuser.label.deactive.error"/>', null);	
			return;			
		}
		
 		generalJqDialog_g.fnShowGeneralConfirmDialog(
				"<spring:message code="faceuser.label.deactive.title" javaScriptEscape="true"/>",
				"<spring:message code="faceuser.label.deactive.confirm" javaScriptEscape="true"/>".replace("{0}",$("#faceUserListForm input[type=checkbox]:checked").length),
				generalJqDialog_g.buttonConfirm,
				function() {
					$.ajax({
						url: '<c:url value="/deactivefaceuser.do"/>',
						method :"post",
						dataType:"json",
						data: $("#faceUserListForm").serialize(),
						success: function(result) {
							generalJqDialog_g.fnShowGeneralInformationDialog('<spring:message code="dialog.commonTitle.information"/>',
									(($("#faceUserListForm input[type=checkbox]:checked").length == 1)?'<spring:message code="faceuser.label.deactive.success1"/>':'<spring:message code="faceuser.label.deactive.success2"/>'),
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
	
	
	function activeFaceUser() {
		if ($("#faceUserListForm input[type=checkbox]:checked").length == 0) return;
		var isActive = false;
		$("#faceUserListForm input[type=checkbox]").each(function(i) {
			if (this.checked) {
				if ($("#userStatus"+i).val() == 'ACTIVE') {
					isActive = true;
				} 
			}
		});
		if (isActive) {
			generalJqDialog_g.fnShowGeneralAlertDialog(
					generalJqDialog_g.commonTitleError, '<spring:message code="faceuser.label.active.error"/>', null);	
			return;			
		}
		
 		generalJqDialog_g.fnShowGeneralConfirmDialog(
				"<spring:message code="faceuser.label.active.title" javaScriptEscape="true"/>",
				"<spring:message code="faceuser.label.active.confirm" javaScriptEscape="true"/>".replace("{0}",$("#faceUserListForm input[type=checkbox]:checked").length),
				generalJqDialog_g.buttonConfirm,
				function() {
					$.ajax({
						url: '<c:url value="/activefaceuser.do"/>',
						method :"post",
						dataType:"json",
						data: $("#faceUserListForm").serialize(),
						success: function(result) {
							generalJqDialog_g.fnShowGeneralInformationDialog('<spring:message code="dialog.commonTitle.information"/>',
									(($("#faceUserListForm input[type=checkbox]:checked").length == 1)?'<spring:message code="faceuser.label.active.success1"/>':'<spring:message code="faceuser.label.active.success2"/>'),
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

	var errList = new Array();
	errList[0] = '<spring:message code="faceuser.result.success${saveType}"/>';
	<c:forEach var = "i" begin = "1" end = "${totalNoOfErrMsg}">
		errList[${i}] = '<spring:message code="faceuser.error.${i}"/>';
	</c:forEach>
	
	function deleteFaceUser() {
		if ($("#faceUserListForm input[type=checkbox]:checked").length == 0) return;
 		generalJqDialog_g.fnShowGeneralConfirmDialog(
				"<spring:message code="faceuser.label.delete.title" javaScriptEscape="true"/>",
				"<spring:message code="faceuser.label.delete.confirm" javaScriptEscape="true"/>".replace("{0}",$("#faceUserListForm input[type=checkbox]:checked").length),
				generalJqDialog_g.buttonConfirm,
				function() {
					$.ajax({
						url: '<c:url value="/deletefaceuser.do"/>',
						method :"post",
						dataType:"json",
						data: $("#faceUserListForm").serialize(),
						success: function(result) {
							if (result == 0) {
								generalJqDialog_g.fnShowGeneralInformationDialog('<spring:message code="dialog.commonTitle.information"/>',
										(($("#faceUserListForm input[type=checkbox]:checked").length == 1)?'<spring:message code="faceuser.label.delete.success1"/>':'<spring:message code="faceuser.label.delete.success2"/>'),
										function() {
											refreshPage();
										},
										function() {
											refreshPage();
										}
									);
							} else {
								generalJqDialog_g.fnShowGeneralAlertDialog(generalJqDialog_g.commonTitleError,
										errList[result], null);	
							}
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
		$("#faceUserSearchForm").submit();
	}
	
	function goToPage(pageNo) {
		blockPageUiProcessing();
		$("#pageNo").val(pageNo);
		$("#faceUserSearchForm").submit();
	}
	
	function refreshPage() {
		window.location = '<c:url value="/faceuser.do"/>';
	}
	
	$('#faceUserSearchForm').keypress(function (e) {
		  if (e.which == 13) {
			$("#pageNo").val("1");
		    $("#faceUserSearchForm").submit();
		  }
		});
	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
