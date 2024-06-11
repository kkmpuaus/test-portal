<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@page
	import="com.tradelink.biometric.r2fas.portal.security.model.WebUser"%>
<%@page import="com.tradelink.biometric.r2fas.portal.utils.AuthUtils"%>
<tiles:insertDefinition name="mainTemplate">
	<tiles:putAttribute name="body">
		<style>
div.heading {
	font-weight: bold;
}

div.row {
	line-height: 35px;
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

div.cell3 {
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

.galleryType, .selectCardNumber {
	/*-webkit-appearance: none;*/
	height: 25px;
	width: 210px;
}

.button-group, .play-area {
	border: 1px solid grey;
	padding: 1em 1%;
	margin-bottom: 1em;
	text-align: center;
	margin: auto;
}

.button {
	padding: 0.5em;
	margin-right: 1em;
	cursor: hand;
}

.play-area-sub {
	width: 100%;
	padding: 0em 1%;
	display: inline-block;
	text-align: left;
}

#capture {
	display: none;
}

#snapshot {
	display: inline-block;
	width: 800px;
	height: 600px;
}
</style>
		<body onload="startStreaming()" style="margin: 20; padding: 20;">
			<form style="margin: 0" id="faceUserUploadForm"
				name="faceUserUploadForm" enctype="multipart/form-data"
				method="post">
				<div class="content">
					<div class="title">
						<div class="title-left">
							<c:if test="${!isModify}">
								<spring:message code="faceuser.label.createuser" />
							</c:if>
							<c:if test="${isModify}">
								<spring:message code="faceuser.label.modifyuser" />
							</c:if>
						</div>
						<div class="title-right"></div>
					</div>
					<div class="body" style="padding-left: 50px">
						<div class="row heading">
							<spring:message code="faceuser.label.personalinfo" />
						</div>

						<div class="row">
							<div class="cell1">
								<spring:message code="faceuser.label.cardNumber" />
							</div>
							<div class="cell2">
								<c:if test="${!isModify}">
									<input class='pwd' type="text" name="cardNumber"
										id="cardNumber" value="${faceUser.cardNumber}" maxlength="6">
								</c:if>
								<c:if test="${isModify}">
									<input disabled class='pwd' type="text"
										name="cardNumberReadonly" id="cardNumberReadonly"
										value="${faceUser.cardNumber}" maxlength="6">
									<input type="hidden" name="cardNumber" id="cardNumber"
										value='<c:out value="${faceUser.cardNumber}"/>'>
								</c:if>
							</div>
							<div class="cell3">
								<c:if test="${!isModify}">
									<select class="selectCardNumber" id="selectCardNumber">
										<option value="">SELECT CARD NUMBER</option>
										<c:forEach items="${accessCardList}" var="item">
											<option value="${item.cardNumber}">${item.cardNumber}
												- ${item.gender}</option>
										</c:forEach>
									</select>
								</c:if>
							</div>
						</div>
						<div class="row">
							<div class="cell1">
								<spring:message code="faceuser.label.galleryType" />
							</div>
							<div class="cell2">
								<c:if test="${!isModify}">
									<select class="galleryType" name="galleryType" id="galleryType">
										<option
											value="<spring:message code="faceuser.galleryType.STAFF"/>"
											id="staff"
											<c:if test="${faceUser.galleryType == 'STAFF'}">selected="selected"</c:if>><spring:message
												code="faceuser.galleryType.STAFF" /></option>
										<option
											value="<spring:message code="faceuser.galleryType.VIP"/>"
											id="vip"
											<c:if test="${faceUser.galleryType == 'VIP'}">selected="selected"</c:if>><spring:message
												code="faceuser.galleryType.VIP" /></option>
										<option
											value="<spring:message code="faceuser.galleryType.GUEST"/>"
											id="guest"
											<c:if test="${faceUser.galleryType == 'GUEST'}">selected="selected"</c:if>><spring:message
												code="faceuser.galleryType.GUEST" /></option>
									</select>
								</c:if>
								<c:if test="${isModify}">
									<input disabled class='pwd' type="text"
										name="galleryTypeReadonly" id="galleryTypeReadonly"
										value="${faceUser.galleryType}">
								</c:if>
							</div>
						</div>
						<div class="row">
							<div class="cell1">
								<spring:message code="faceuser.label.personName" />
							</div>
							<div class="cell2">
								<input class='pwd' type="text" name="personName" id="personName"
									value="${faceUser.personName}" maxlength="30">
							</div>
						</div>
						<div class="row">
							<div class="cell1">
								<spring:message code="faceuser.label.company" />
							</div>
							<div class="cell2">
								<input class='pwd' type="text" name="company" id="company"
									value="${faceUser.company}" maxlength="100">
							</div>
						</div>
						<div class="row">
							<div class="cell1">
								<spring:message code="faceuser.label.title" />
							</div>
							<div class="cell2">
								<input class='pwd' type="text" name="title" id="title"
									value="${faceUser.title}" maxlength="100">
							</div>
						</div>
						<div class="row">
							<div class="cell1">
								<spring:message code="faceuser.label.phone" />
							</div>
							<div class="cell2">
								<input class='pwd' type="text" name="phone" id="phone"
									value="${faceUser.phone}" maxlength="100">
							</div>
						</div>
						<div class="row">
							<div class="cell1">
								<spring:message code="faceuser.label.email" />
							</div>
							<div class="cell2">
								<input class='pwd' type="text" name="email" id="email"
									value="${faceUser.email}" maxlength="100">
							</div>
						</div>
						<c:if test="${!isModify}">
							<div class="row">
								<div class="cell1">
									<spring:message code="faceuser.label.personPhoto" />
								</div>
								<div class="cell2">
									<input class="pwd" type="text" name="personPhotoFileName"
										id="personPhotoFileName" maxlength="100" readonly /> <input
										id="personPhotoFile" type="file" size="50"
										name="personPhotoFile" style="display: none"> <input
										id="butUpload" name="Upload"
										value="<spring:message code="common.button.upload"/>"
										type="button"
										class="ui-button ui-widget ui-state-default ui-corner-all but"
										onClick="uploadPhoto()" />
								</div>
							</div>
						</c:if>
						<c:if test="${!isModify}">
							<div class="row">
								<spring:message code="faceuser.label.instruction.upload" />
							</div>
						</c:if>
						<c:if test="${!isModify}">
							<div class="row">
								<spring:message code="faceuser.label.webCam" />
								<button id="btn-start"
									class="ui-button ui-widget ui-state-default ui-corner-all but"
									type="button" class="button">Start</button>
								<button id="btn-stop"
									class="ui-button ui-widget ui-state-default ui-corner-all but"
									type="button" class="button">Stop</button>
								<button id="btn-capture"
									class="ui-button ui-widget ui-state-default ui-corner-all but"
									type="button" class="button">Capture</button>
							</div>
							<div class="row">
								<div class="play-area-sub">
									<video id="stream" width="800" height="600"></video>
								</div>
							</div>
							<div class="row">
								<div class="cell1">
									<spring:message code="faceuser.label.capture" />
								</div>
							</div>
							<div class="row">
								<canvas id="capture" width="800" height="600"></canvas>
								<div id="snapshot"></div>
							</div>
						</c:if>
						<!--<c:if test="${!isModify}">
							<div class="row">
								<button id="btn-start"
									class="ui-button ui-widget ui-state-default ui-corner-all but"
									type="button" class="button">Start</button>
								<button id="btn-stop"
									class="ui-button ui-widget ui-state-default ui-corner-all but"
									type="button" class="button">Stop</button>
								<button id="btn-capture"
									class="ui-button ui-widget ui-state-default ui-corner-all but"
									type="button" class="button">Capture</button>
							</div>
						</c:if>-->
						<div class="row">
							<input id="butSave" name="Save"
								value="<spring:message code="common.button.save"/>"
								type="button"
								class="ui-button ui-widget ui-state-default ui-corner-all but"
								onClick="save(${isModify})" /> <input id="butCancel"
								name="Cancel"
								value="<spring:message code="common.button.cancel"/>"
								type="button"
								class="ui-button ui-widget ui-state-default ui-corner-all but"
								onClick="cancel()" />
						</div>
					</div>
					<sec:csrfInput />
			</form>
			<script>
	$(document).ready(function() {
		$("#selectCardNumber").change(function(){
			$(cardNumber).val(document.getElementById("selectCardNumber").value);
		});
		
		$("#personPhotoFile").change(function() {
			document.getElementById("personPhotoFileName").value = document.getElementById("personPhotoFile").files[0].name;
		});
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
	errList[0] = '<spring:message code="faceuser.result.success${saveType}"/>';
	<c:forEach var = "i" begin = "1" end = "${totalNoOfErrMsg}">
		errList[${i}] = '<spring:message code="faceuser.error.${i}"/>';
	</c:forEach>

	function cancel() {
		window.location = '<c:url value="/faceuser.do"/>';
	}
	function back() {
		window.location = '<c:url value="/faceuser.do"/>';
	}
	function save(isModify) {
		
		event.preventDefault();
		
		var form = $('#faceUserUploadForm')[0];
		var data = new FormData(form);
		
		if(!isModify && $('#snapshot').children().length > 0){
			var dataURI	= snapshot.firstChild.getAttribute( "src" );
			var imageData   = dataURItoBlob( dataURI );
			data.append( "cameraPhotoFile", imageData, "selfie.jpg" );
		}
		
		if ($("#cardNumber").val() == "") {
			generalJqDialog_g.fnShowGeneralAlertDialog(generalJqDialog_g.commonTitleError,
					errList[1],
					function() {
						$("#cardNumber").focus();
					});
			return;
		}
		if ($("#personName").val() == "") {
			generalJqDialog_g.fnShowGeneralAlertDialog(generalJqDialog_g.commonTitleError,
					errList[2],
					function() {
						$("#personName").focus();
					});
			return;
		}
		if ($("#galleryType").val() == "") {
			generalJqDialog_g.fnShowGeneralAlertDialog(generalJqDialog_g.commonTitleError,
					errList[3],
					function() {
						$("#galleryType").focus();
					});
			return;
		}

		var url = '<c:url value="addfaceuser.do"/>';
		if (isModify) {
			url = '<c:url value="/editfaceuser.do"/>';
		}
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
	
		$.ajax({
			 type: "POST",
		     enctype: 'multipart/form-data',
		     url: url,
		     data: data,
		     processData: false,
		     contentType: false,
		     cache: false,
		     timeout: 1000000,
			 beforeSend : function(xhr) {
					xhr.setRequestHeader(header, token);
				},  
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
	
	function uploadPhoto() {
		document.getElementById("personPhotoFile").value = "";
		document.getElementById("personPhotoFile").click();
	}
	
	//The buttons to start & stop stream and to capture the image
	var btnStart = document.getElementById( "btn-start" );
	var btnStop = document.getElementById( "btn-stop" );
	var btnCapture = document.getElementById( "btn-capture" );
	
	//The buttons to start & stop stream and to capture the image
	var btnStart = document.getElementById( "btn-start" );
	var btnStop = document.getElementById( "btn-stop" );
	var btnCapture = document.getElementById( "btn-capture" );

	// The stream & capture
	var stream = document.getElementById( "stream" );
	var capture = document.getElementById( "capture" );
	var snapshot = document.getElementById( "snapshot" );

	//The video stream
	var cameraStream = null;

	// Attach listeners
	btnStart.addEventListener( "click", startStreaming );
	btnStop.addEventListener( "click", stopStreaming );
	btnCapture.addEventListener( "click", captureSnapshot );
	
	function startStreaming() {

		var mediaSupport = 'mediaDevices' in navigator;

		if( mediaSupport && null == cameraStream ) {

			navigator.mediaDevices.getUserMedia( { 
				video: {
					width: { width: 800 },
					height: { height: 600 }
				}
			} ).then( function( mediaStream ) {

				cameraStream = mediaStream;

				stream.srcObject = mediaStream;

				stream.play();
			})
			.catch( function( err ) {

				console.log( "Unable to access camera: " + err );
			});
		}
		else {

			alert( 'Your browser does not support media devices.' );

			return;
		}
	}

	//Stop Streaming
	function stopStreaming() {

		if( null != cameraStream ) {

			var track = cameraStream.getTracks()[ 0 ];

			track.stop();
			stream.load();

			cameraStream = null;
		}
	}
	
	function captureSnapshot() {		
		if( null != cameraStream ) {

			var ctx = capture.getContext( '2d' );
			var img = new Image();

			ctx.drawImage( stream, 0, 0, capture.width, capture.height );

			img.src		= capture.toDataURL( "image/jpeg" );
			img.width	= 800;
			img.height	= 600;

			snapshot.innerHTML = '';

			snapshot.appendChild( img );
		}
	}
	
	function dataURItoBlob( dataURI ) {

		var byteString = atob( dataURI.split( ',' )[ 1 ] );
		var mimeString = dataURI.split( ',' )[ 0 ].split( ':' )[ 1 ].split( ';' )[ 0 ];
		
		var buffer	= new ArrayBuffer( byteString.length );
		var data	= new DataView( buffer );
		
		for( var i = 0; i < byteString.length; i++ ) {
		
			data.setUint8( i, byteString.charCodeAt( i ) );
		}
		
		return new Blob( [ buffer ], { type: mimeString } );
	}
	</script>
	</tiles:putAttribute>
</tiles:insertDefinition>