<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="<c:url value="/scripts/jquery/jquery-3.2.1.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/jquery/jquery-ui-1.12.1/jquery-ui.js"/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/jquery/plugins/jquery.blockUI-2.7.js"/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/utils.js"/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/jquery/plugins/jquery.fancybox.min.js"/>"></script>
<!--<script type="text/javascript" src="<c:url value="/scripts/jquery/plugins/jquery.datetimepicker.full.min.js"/>"></script>-->
<script type="text/javascript">
$(document).ready(function() {
	initBlockUi({
		'imageSource': '<c:url value="/images/ajax-loader-2.gif"/>',
		'message.request': '<spring:message code="common.message.inProgress.requestPage" javaScriptEscape="true"/>',
		'message.process': '<spring:message code="common.message.inProgress.processing" javaScriptEscape="true"/>',
		'message.search': '<spring:message code="common.message.inProgress.search" javaScriptEscape="true"/>',
		'message.filter': '<spring:message code="common.message.inProgress.filter" javaScriptEscape="true"/>',
		'message.sorting': '<spring:message code="common.message.inProgress.sorting" javaScriptEscape="true"/>',
		'message.initialize': '<spring:message code="common.message.inProgress.initializing" javaScriptEscape="true"/>'
	});
});
/*
$(window).unload(function() {
	unblockPageUi();
}); */
</script>