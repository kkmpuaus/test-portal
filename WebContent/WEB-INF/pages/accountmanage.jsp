<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<tiles:insertDefinition name="mainTemplate">
	<tiles:putAttribute name="body">
	<style>
	div.subbut {
		width: 100%;
		text-align: left;
		padding-top: 10px;
		padding-bottom: 10px;
	}
 	div.userlist .title {
		border-bottom: 2px solid #898989;
	}
	div.row {
		line-height:30px;
		width: 100%;
		float: left;
		border-bottom: 1px solid #dfdfdf
	}
	div.cell1 {
		width: 5%;
		float: left;
	}
	div.cell2 {
		width: 25%;
		float: left;
		white-space: nowrap;
	}
	div.cell3 {
		width: 25%;
		float: left;
		white-space: nowrap;
	}
	div.cell4 {
		width: 15%;
		float: left;
		white-space: nowrap;
	}
	div.cell5 {
		width: 15%;
		float: left;
		white-space: nowrap;
	}
	div.cell6 {
		width: 15%;
		float: left;
		white-space: nowrap;
	}
	div.cellbold {
		font-weight: bold;
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
	</style>
	<div class="content">
		<div class="title">
			<div class="title-left">
				<spring:message code="accountmanage.title"/>
			</div>
			<div class="title-right">
			</div>
		</div>
		<div class="body">
			<div class="subbut">
				<input id="butAdd" name="Add" value="<spring:message code="common.button.add"/>" type="button" class="ui-button ui-widget ui-state-default ui-corner-all but" onClick="addUser()"/>
				<input id="butModify" name="Modify" value="<spring:message code="common.button.modify"/>" type="button" class="ui-button ui-widget ui-state-default ui-corner-all but" onClick="modifyUser()"/>
				<input id="butDel" name="Delete" value="<spring:message code="common.button.delete"/>" type="button" class="ui-button ui-widget ui-state-default ui-corner-all but" onClick="deleteUser()"/>
				<input id="butUnlock" name="Unlock" value="<spring:message code="common.button.unlock"/>" type="button" class="ui-button ui-widget ui-state-default ui-corner-all but" onClick="unlockUser()"/>
			</div>
			<form style="margin:0" id="userListForm" action="" method="post">
			<div class="userlist">
				
				<div class="row title">
					<div class="cell1 cellbold">&nbsp;
					</div>
					<div class="cell2 cellbold">
					<spring:message code="accountmanage.label.userid"/>
					</div>
					<div class="cell3 cellbold">
					<spring:message code="accountmanage.label.name"/>
					</div>
					<div class="cell4 cellbold">
					<spring:message code="accountmanage.label.role"/>
					</div>
					<div class="cell5 cellbold">
					<spring:message code="accountmanage.label.locked"/>
					</div>
					<div class="cell6 cellbold">
					<spring:message code="accountmanage.label.status"/>
					</div>
				</div>
				<c:forEach items="${userList}" var="user" varStatus="loop">
				<div class="row">
					<div class="cell1">
					<input type="checkbox" name="userId" value='<c:out value="${user.userId}"/>'/>
					</div>	
					<div class="cell2">
					<a class="mgr" href="javascript:void(0)" onClick="modifyUser('${loop.index}')">${user.userId}</a>&nbsp;
					</div>
					<div class="cell3">
					<c:out value="${user.displayName}"/>
					</div>
					<div class="cell4">
					<spring:message code="accountmanage.label.role.${user.userRole}"/>
					<input type="hidden" id="userRole${loop.index}" name="userRole${loop.index}" value="${user.userRole}"/>
					</div>
					<div class="cell5">
					<c:if test="${user.locked == true}">
					<spring:message code="accountmanage.label.Y"/>
					</c:if>
					<c:if test="${user.locked == false}">
					<spring:message code="accountmanage.label.N"/>
					</c:if>
					</div>
					<div class="cell6">
					<c:if test="${user.locked == true}">
					<spring:message code="accountmanage.label.status.L"/>
					</c:if>
					<c:if test="${user.locked == false}">
					<spring:message code="accountmanage.label.status.${user.status}"/>
					</c:if>
					<input type="hidden" id="locked${loop.index}" name="locked${loop.index}" value="${user.locked}"/>
					</div>
				</div>
				</c:forEach>
			</div>
			<sec:csrfInput />
			</form>
			<form style="margin:0" id="userEditForm" action="<c:url value="/edituser.do"/>" method="post">
			<input type="hidden" name="userId" id="userEditFormUserId" value=''/>
			<sec:csrfInput />
			</form>
		</div>
	</div>
	<script>
	var isEnableAdded = ${enableAdd}; 
	function addUser() {
		if (!isEnableAdded) {
			generalJqDialog_g.fnShowGeneralAlertDialog(
					generalJqDialog_g.commonTitleError, '<spring:message code="userdetail.error.12" arguments="${maxNormalUser}"/>', null);
			return;
		}
		window.location='<c:url value="/adduser.do"/>';
	}
	function refreshPage() {
		window.location = '<c:url value="/accountmanage.do"/>';
	}
	function deleteUser() {
		if ($("#userListForm input[type=checkbox]:checked").length == 0) return;
		var isAdmin = false;
		$("#userListForm input[type=checkbox]").each(function(i) {
			if (this.checked) {
				if ($("#userRole"+i).val() == "ROLE_ADMIN") {
					isAdmin = true;
				}
			}
		});
		if (isAdmin) {
			generalJqDialog_g.fnShowGeneralAlertDialog(
					generalJqDialog_g.commonTitleError, '<spring:message code="accountmanage.label.deleteuser.error"/>', null);	
			return;			
		}
		generalJqDialog_g.fnShowGeneralConfirmDialog(
				"<spring:message code="accountmanage.label.deleteuser.title" javaScriptEscape="true"/>",
				"<spring:message code="accountmanage.label.deleteuser.confirm" javaScriptEscape="true"/>".replace("{0}",$("#userListForm input[type=checkbox]:checked").length),
				generalJqDialog_g.buttonConfirm,
				function() {
					$.ajax({
						url: '<c:url value="/deleteuser.do"/>',
						method :"post",
						dataType:"json",
						data: $("#userListForm").serialize(),
						success: function(result) {
							generalJqDialog_g.fnShowGeneralInformationDialog('<spring:message code="dialog.commonTitle.information"/>',
									(($("#userListForm input[type=checkbox]:checked").length == 1)?'<spring:message code="accountmanage.label.deleteuser.success1"/>':'<spring:message code="accountmanage.label.deleteuser.success2"/>'),
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
	function unlockUser() {
		if ($("#userListForm input[type=checkbox]:checked").length == 0) return;
		var isNotLocked = false;
		$("#userListForm input[type=checkbox]").each(function(i) {
			if (this.checked) {
				if ($("#locked"+i).val() == 'false') {
					isNotLocked = true;
				}
			}
		});
		if (isNotLocked) {
			generalJqDialog_g.fnShowGeneralAlertDialog(
					generalJqDialog_g.commonTitleError, '<spring:message code="accountmanage.label.unlock.error"/>', null);	
			return;			
		}
		generalJqDialog_g.fnShowGeneralConfirmDialog(
				"<spring:message code="accountmanage.label.unlock.title" javaScriptEscape="true"/>",
				"<spring:message code="accountmanage.label.unlock.confirm" javaScriptEscape="true"/>".replace("{0}",$("#userListForm input[type=checkbox]:checked").length),
				generalJqDialog_g.buttonConfirm,
				function() {
					$.ajax({
						url: '<c:url value="/unlockuser.do"/>',
						method :"post",
						dataType:"json",
						data: $("#userListForm").serialize(),
						success: function(result) {
							generalJqDialog_g.fnShowGeneralInformationDialog('<spring:message code="dialog.commonTitle.information"/>',
									(($("#userListForm input[type=checkbox]:checked").length == 1)?'<spring:message code="accountmanage.label.unlock.success1"/>':'<spring:message code="accountmanage.label.unlock.success2"/>'),
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
	function modifyUser(userIdIdx) {
		var userId;
		if (userIdIdx == null) {
			if ($("#userListForm input[type=checkbox]:checked").length == 0) {
				return;
			}
			if ($("#userListForm input[type=checkbox]:checked").length != 1) {
				generalJqDialog_g.fnShowGeneralAlertDialog(
					generalJqDialog_g.commonTitleError, '<spring:message code="userdetail.error.toomany.user"/>', null);
				return;
			}
			userId = $("#userListForm input[type=checkbox]:checked").val();
		} else {
			$("#userListForm input[type=checkbox]").each(function(i) {
				if (i == userIdIdx) {
					userId = this.value;
				}
			});
		}
		$("#userEditFormUserId").val(userId);
		$("#userEditForm").submit();
	}
	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>