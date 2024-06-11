<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
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
	div.faceHistorylist .title {
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
		width: 10%;
		float: left;
		white-space: normal;
	}
	div.cell2 {
		width: 25%;
		float: left;
		white-space: normal;
	}
	div.cell3 {
		width: 10%;
		float: left;
		white-space: normal;
	}
	div.cell4 {
		width: 10%;
		float: left;
		white-space: normal;
	}
	div.cell5 {
		width: 10%;
		float: left;
		white-space: normal;
	}
	div.cell6 {
		width: 10%;
		float: left;
		white-space: normal;
	}
	div.cell7 {
		width: 15%;
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
				<spring:message code="facehistory.title"/>
			</div>
			<div class="title-right">
			</div>
		</div>
		<div class="body">
		
			<div class="btnrow-right">
			<form style="margin:0" id="faceHistorySearchForm" action="<c:url value="/facehistory.do"/>" method="post">
				<input type="hidden" id="pageSize" name="pageSize" value="${pageSize}"/>
				<input type="hidden" id="pageNo" name="pageNo" value="${pageNo}"/>
				<input type="hidden" id="orderBy" name="orderBy" value="${orderBy}"/>
				<input type="hidden" id="ordering" name="ordering" value="${ordering}"/>
				<input type="text" style="height: 22px;" id="cardNumber" name="cardNumber"  placeholder="<spring:message code="facehistory.label.search.cardNumber"/>" value="${cardNumber}"/>	
 				<input type="text" style="height: 22px;" id="startDate" name="startDate"  placeholder="<spring:message code="facehistory.label.search.startDate"/>" value="${startDate}"/>
				<input type="text" style="height: 22px;" id="endDate" name="endDate"  placeholder="<spring:message code="facehistory.label.search.endDate"/>" value="${endDate}"/>
				<input id="butSearch" name="Search" value="<spring:message code="common.button.search"/>" type="button" class="ui-button ui-widget ui-state-default ui-corner-all but" onClick="search()"  align="right"/>					
	 		<sec:csrfInput />
			</form>
			</div>
	
			<form style="margin:0" id="faceHistoryListForm">
			<div class="faceHistorylist">
				<div class="rowbar" style="text-align:right">
				<c:if test="${pageNo > 1}"><a href="javascript:void(0)" onClick="goToPage(${pageNo - 1})"><font style="color:black">&lt;&lt;</font></a></c:if>&nbsp;
				<spring:message code="common.label.page" arguments="${pageNo},${totalPageNo}"/> <c:if test="${pageNo < totalPageNo}"><a href="javascript:void(0)" onClick="goToPage(${pageNo + 1})"><font style="color:black">&gt;&gt;</font></a></c:if>
				&nbsp;| <spring:message code="common.label.total" arguments="${totalNo}"/>
				</div>
				
				<div class="row title">
					<div class="cell1 cellbold">
					<a href="javascript:void(0)" onClick="changeOrder('historyType')"><font class="header"><spring:message code="facehistory.label.historyType"/></font></a>
					<c:if test="${orderBy eq 'historyType' }">
					<c:if test="${ordering eq 'ASC' }">
					<span style="margin-top: 0px;overflow: hidden;position:absolute;display:inline;cursor: pointer !important" class="ui-icon-asc ui-icon ui-icon-triangle-1-n"></span>
					</c:if>
					<c:if test="${ordering eq 'DESC' }">
					<span style="margin-top: 0px;overflow: hidden;position:absolute;display:inline;cursor: pointer !important" class="ui-icon-desc ui-icon ui-icon-triangle-1-s"></span>
					</c:if>
					</c:if>
					</div>
					<div class="cell2 cellbold">
					<a href="javascript:void(0)" onClick="changeOrder('cameraName')"><font class="header"><spring:message code="facehistory.label.cameraName"/></font></a>
					<c:if test="${orderBy eq 'cameraName' }">
					<c:if test="${ordering eq 'ASC' }">
					<span style="margin-top: 0px;overflow: hidden;position:absolute;display:inline;cursor: pointer !important" class="ui-icon-asc ui-icon ui-icon-triangle-1-n"></span>
					</c:if>
					<c:if test="${ordering eq 'DESC' }">
					<span style="margin-top: 0px;overflow: hidden;position:absolute;display:inline;cursor: pointer !important" class="ui-icon-desc ui-icon ui-icon-triangle-1-s"></span>
					</c:if>
					</c:if>
					</div>
					<div class="cell3 cellbold">
					<a href="javascript:void(0)" onClick="changeOrder('wiegandIp')"><font class="header"><spring:message code="facehistory.label.wiegandIp"/></font></a>
					<c:if test="${orderBy eq 'wiegandIp' }">
					<c:if test="${ordering eq 'ASC' }">
					<span style="margin-top: 0px;overflow: hidden;position:absolute;display:inline;cursor: pointer !important" class="ui-icon-asc ui-icon ui-icon-triangle-1-n"></span>
					</c:if>
					<c:if test="${ordering eq 'DESC' }">
					<span style="margin-top: 0px;overflow: hidden;position:absolute;display:inline;cursor: pointer !important" class="ui-icon-desc ui-icon ui-icon-triangle-1-s"></span>
					</c:if>
					</c:if>
					</div>
					<div class="cell4 cellbold">
					<a href="javascript:void(0)" onClick="changeOrder('wiegandPort')"><font class="header"><spring:message code="facehistory.label.wiegandPort"/></font></a>
					<c:if test="${orderBy eq 'wiegandPort' }">
					<c:if test="${ordering eq 'ASC' }">
					<span style="margin-top: 0px;overflow: hidden;position:absolute;display:inline;cursor: pointer !important" class="ui-icon-asc ui-icon ui-icon-triangle-1-n"></span>
					</c:if>
					<c:if test="${ordering eq 'DESC' }">
					<span style="margin-top: 0px;overflow: hidden;position:absolute;display:inline;cursor: pointer !important" class="ui-icon-desc ui-icon ui-icon-triangle-1-s"></span>
					</c:if>
					</c:if>
					</div>
					<div class="cell5 cellbold">
					<a href="javascript:void(0)" onClick="changeOrder('cardNumber')"><font class="header"><spring:message code="facehistory.label.cardNumber"/></font></a>
					<c:if test="${orderBy eq 'cardNumber' }">
					<c:if test="${ordering eq 'ASC' }">
					<span style="margin-top: 0px;overflow: hidden;position:absolute;display:inline;cursor: pointer !important" class="ui-icon-asc ui-icon ui-icon-triangle-1-n"></span>
					</c:if>
					<c:if test="${ordering eq 'DESC' }">
					<span style="margin-top: 0px;overflow: hidden;position:absolute;display:inline;cursor: pointer !important" class="ui-icon-desc ui-icon ui-icon-triangle-1-s"></span>
					</c:if>
					</c:if>
					</div>
					<div class="cell6 cellbold">
					<a href="javascript:void(0)" onClick="changeOrder('galleryType')"><font class="header"><spring:message code="facehistory.label.galleryType"/></font></a>
					<c:if test="${orderBy eq 'galleryType' }">
					<c:if test="${ordering eq 'ASC' }">
					<span style="margin-top: 0px;overflow: hidden;position:absolute;display:inline;cursor: pointer !important" class="ui-icon-asc ui-icon ui-icon-triangle-1-n"></span>
					</c:if>
					<c:if test="${ordering eq 'DESC' }">
					<span style="margin-top: 0px;overflow: hidden;position:absolute;display:inline;cursor: pointer !important" class="ui-icon-desc ui-icon ui-icon-triangle-1-s"></span>
					</c:if>
					</c:if>
					</div>
					<div class="cell7 cellbold">
					<a href="javascript:void(0)" onClick="changeOrder('created')"><font class="header"><spring:message code="facehistory.label.created"/></font></a>
					<c:if test="${orderBy eq 'created' }">
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
				<c:forEach items="${resultList}" var="faceHistory" varStatus="loop">

				<div class="row">
				<div class="row-right">				
					<div class="cell1">
					<c:choose>
    				<c:when test="${empty faceHistory.historyType}">        			
        			&nbsp
    				</c:when>
    				<c:otherwise>
        			<c:out value="${faceHistory.historyType}"/>
    				</c:otherwise>
					</c:choose>
					</div>
					<div class="cell2">
					<c:choose>
    				<c:when test="${empty faceHistory.cameraName}">        			
        			&nbsp
    				</c:when>
    				<c:otherwise>
        			<c:out value="${faceHistory.cameraName}"/>
    				</c:otherwise>
					</c:choose>
					</div>
					<div class="cell3">
					<c:choose>
    				<c:when test="${empty faceHistory.wiegandIp}">        			
        			&nbsp
    				</c:when>
    				<c:otherwise>
        			<c:out value="${faceHistory.wiegandIp}"/>
    				</c:otherwise>
					</c:choose>
					</div>
					<div class="cell4">
					<c:choose>
    				<c:when test="${faceHistory.wiegandPort eq 0}">        			
        			&nbsp
    				</c:when>
    				<c:otherwise>
        			<c:out value="${faceHistory.wiegandPort}"/>
    				</c:otherwise>
					</c:choose>
					</div>
					<div class="cell5">
					<c:choose>
    				<c:when test="${empty faceHistory.cardNumber}">        			
        			&nbsp
    				</c:when>
    				<c:otherwise>
        			<c:out value="${faceHistory.cardNumber}"/>
    				</c:otherwise>
					</c:choose>
					</div>
					<div class="cell6">
					<c:choose>
    				<c:when test="${empty faceHistory.galleryType}">        			
        			&nbsp
    				</c:when>
    				<c:otherwise>
        			<c:out value="${faceHistory.galleryType}"/>
    				</c:otherwise>
					</c:choose>
					</div>
					<div class="cell7">
					<c:choose>
    				<c:when test="${empty faceHistory.created}">        			
        			&nbsp
    				</c:when>
    				<c:otherwise>
        			<fmt:formatDate pattern = "yyyy-MM-dd HH:mm:ss" value = "${faceHistory.created}" />
    				</c:otherwise>
					</c:choose>
					</div>
					
				</div>
				</div>
				</c:forEach>
				</c:if>
			</div>
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
		
		
		$(function() {
			    $( "#startDate" ).datepicker();
			    $( "#endDate" ).datepicker();
			  } );
	})
	


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
		$("#faceHistorySearchForm").submit();
	}
	
	function goToPage(pageNo) {
		blockPageUiProcessing();
		$("#pageNo").val(pageNo);
		$("#faceHistorySearchForm").submit();
	}
	
	$('#facehistorySearchForm').keypress(function (e) {
		  if (e.which == 13) {
			$("#pageNo").val("1");
		    $("#faceHistorySearchForm").submit();
		  }
		});
	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
