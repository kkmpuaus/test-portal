<html>
<head>
<style>
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
  text-align: center;
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
</head>
<body onload="startStreaming()" style="margin:20;padding:20;">

<!-- Video Element & Canvas -->
<div class="play-area" style="width: 80%">
	<!-- The buttons to control the stream -->
	<div class="button-group" style="display:block" style="width: 80%">
	<font>Buttons here are used to test the control of the video stream only! The button "Capture" is the same as the button "Register"</font><p>
	  <button id="btn-start" type="button" class="button">Start</button>
	  <button id="btn-stop" type="button" class="button">Stop</button>
	  <button id="btn-capture" type="button" class="button">Capture</button>
	</div>
  <div class="play-area-sub">
    <h3>Login ID: <input id="loginId" type="text" name="loginId" value=""> <button id="btn-login" type="button" class="button">Register</button></h3>
    <video id="stream" width="320" height="240"></video>
  </div>
  <div style="display: none">
    <h3>The Capture</h3>
    <canvas id="capture" width="800" height="600"></canvas>
    <div id="snapshot"></div>
  </div>
</div>

</body>
<script>
//The buttons to start & stop stream and to capture the image
var btnStart = document.getElementById( "btn-start" );
var btnStop = document.getElementById( "btn-stop" );
var btnCapture = document.getElementById( "btn-capture" );
var btnLogin = document.getElementById( "btn-login" );
var txtLoginId = document.getElementById("loginId");

// The stream & capture
var stream = document.getElementById( "stream" );
var capture = document.getElementById( "capture" );
var snapshot = document.getElementById( "snapshot" );

//The video stream
var cameraStream = null;

// Attach listeners
btnStart.addEventListener( "click", startStreaming );
btnStop.addEventListener( "click", stopStreaming );

//Start Streaming
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

btnCapture.addEventListener( "click", captureSnapshot );
btnLogin.addEventListener( "click", captureSnapshot );

function captureSnapshot() {

	if (txtLoginId.value == "") {
		alert("Please input Login ID!");
		return;
	}
	
	if( null != cameraStream ) {

		var ctx = capture.getContext( '2d' );
		var img = new Image();

		ctx.drawImage( stream, 0, 0, capture.width, capture.height );

		img.src		= capture.toDataURL( "image/jpeg" );
		img.width	= 800;
		img.height	= 600;

		snapshot.innerHTML = '';

		snapshot.appendChild( img );
		
		submitImg();
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

function submitImg() {
	var request = new XMLHttpRequest();

	request.open( "POST", "register.do", true );
	
	request.onreadystatechange = function() {
		if (this.readyState == 4) {
			stopStreaming();
			if (this.status == 200) {
				if (request.responseText == 'true') {
					window.location = "registersuccess.jsp";
				} else {
					alert("failed");
					window.location = "registerfail.jsp;"
				}
			} else {
				alert("Error: HTTP Status Code " + this.status + " received!");
			}
		}
	}

	var data	= new FormData();
	var dataURI	= snapshot.firstChild.getAttribute( "src" );
	var imageData   = dataURItoBlob( dataURI );
	
	data.append( "loginId", txtLoginId.value);
	data.append( "selfie", imageData, "selfie.jpg" );

	request.send( data );
}
</script>
</html>