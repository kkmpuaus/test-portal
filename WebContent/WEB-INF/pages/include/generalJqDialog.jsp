<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script type="text/javascript">
function GeneralJqDialog() {
	this.commonTitleError = '<spring:message code="dialog.commonTitle.error" javaScriptEscape="true"/>';
	this.commonTitleWarning = '<spring:message code="dialog.commonTitle.warning" javaScriptEscape="true"/>';
	this.commonTitleInformation = '<spring:message code="dialog.commonTitle.information" javaScriptEscape="true"/>';
	this.commonTitleQuestion = '<spring:message code="dialog.commonTitle.question" javaScriptEscape="true"/>';
	this.commonTitleConfirm = '<spring:message code="dialog.commonTitle.confirm" javaScriptEscape="true"/>';
	this.commonTitleQuestionClearData = '<spring:message code="dialog.commonTitle.question.clearData" javaScriptEscape="true"/>';

	this.buttonCancel = '<spring:message code="common.button.cancel" javaScriptEscape="true"/>';
	this.buttonClose = '<spring:message code="common.button.close" javaScriptEscape="true"/>';
	this.buttonConfirm = '<spring:message code="common.button.confirm" javaScriptEscape="true"/>';
	this.buttonContinue = '<spring:message code="common.button.continue" javaScriptEscape="true"/>';
	this.buttonCreate = '<spring:message code="common.button.create" javaScriptEscape="true"/>';
	this.buttonCopy = '<spring:message code="common.button.copy" javaScriptEscape="true"/>';
	this.buttonDelete = '<spring:message code="common.button.delete" javaScriptEscape="true"/>';
	this.buttonOk = '<spring:message code="common.button.ok" javaScriptEscape="true"/>';
	this.buttonOverwrite = '<spring:message code="common.button.overwrite" javaScriptEscape="true"/>';
	this.buttonSave = '<spring:message code="common.button.save" javaScriptEscape="true"/>';
	this.buttonUpdate = '<spring:message code="common.button.update" javaScriptEscape="true"/>';
	this.buttonYes = '<spring:message code="common.button.yes" javaScriptEscape="true"/>';
	this.buttonNo = '<spring:message code="common.button.no" javaScriptEscape="true"/>';
	this.buttonRestore = '<spring:message code="common.button.restore" javaScriptEscape="true"/>';
}
GeneralJqDialog.prototype.fnShowGeneralConfirmDialog = function(title, msg, btn, callback) {
	$('#dialog\\.generalConfirm').dialog({
		width: 400,
		height: 'auto',
		minHeight : 200,
		minWidth : 300,
		title : title,
		modal: true,
		open : function() {
			$(this).scrollTop(0);
		},
		buttons : [ {
			text : btn,
			click : function() {
				$(this).dialog('close');
				callback();
			}
		}, {
			text : this.buttonCancel,
			click : function() {
				$(this).dialog('close');
			}
		} ]
	});
	$('#dialog\\.generalConfirm\\.message').html(msg);
	$('#dialog\\.generalConfirm').dialog('open');
};
GeneralJqDialog.prototype.fnShowGeneralYesNoDialog = function(title, msg, callbackYes, callbackNo) {
	$('#dialog\\.generalConfirm').dialog({
		width: 400,
		height: 'auto',
		minHeight : 200,
		minWidth : 300,
		title : title,
		modal: true,
		open : function() {
			$(this).scrollTop(0);
		},
		buttons : [ {
			text : this.buttonYes,
			click : function() {
				$(this).dialog('close');
				callbackYes();
			}
		}, {
			text : this.buttonNo,
			click : function() {
				$(this).dialog('close');
				callbackNo();
			}
		} ]
	});
	$('#dialog\\.generalConfirm\\.message').html(msg);
	$('#dialog\\.generalConfirm').dialog('open');
};
GeneralJqDialog.prototype.fnShowGeneralAlertDialog = function(title, msg, callback) {
	$('#dialog\\.generalAlert').dialog({
		width: 400,
		height: 'auto',
		minHeight : 200,
		minWidth : 300,
		title : title,
		modal: true,
		open : function() {
			$(this).scrollTop(0);
		},
		buttons : [ {
			text : this.buttonClose,
			click : function() {
				$(this).dialog('close');
				if (callback && callback != null) {
					callback();
				}
			}
		} ]
	});
	$('#dialog\\.generalAlert\\.message').html(msg);
	$('#dialog\\.generalAlert').dialog('open');
};
GeneralJqDialog.prototype.fnShowGeneralInformationDialog = function(title, msg, callback, callbackClose) {
	$('#dialog\\.generalInformation').dialog({
		width: 400,
		height: 'auto',
		minHeight : 200,
		minWidth : 300,
		title : title,
		modal: true,
		open : function() {
			$(this).scrollTop(0);
		},
		close: function(event, ui) {
			if (callbackClose && callbackClose != null) {
				callbackClose();
			}
		},
		buttons : [ {
			text : this.buttonOk,
			click : function() {
				$(this).dialog('close');
				if (callback && callback != null) {
					callback();
				}
			}
		} ]
	});
	$('#dialog\\.generalInformation\\.message').html(msg);
	$('#dialog\\.generalInformation').dialog('open');
};
GeneralJqDialog.prototype.fnShowGeneralPromptDialog = function(title, msg, btn, presetVal, callback) {
	$('#dialog\\.generalPrompt').dialog({
		width: 400,
		height: 'auto',
		minHeight : 200,
		minWidth : 300,
		title : title,
		modal: true,
		buttons : [ {
			text : btn && btn != '' ? btn : this.buttonOk,
			click : function() {
					$(this).dialog('close');
					var _val = $('#dialog\\.generalPrompt\\.form\\.input\\.text').val();
					callback(_val);
			}
		}, {
			text : this.buttonCancel,
			click : function() {
				$(this).dialog('close');
			}
		} ],
		open : function() {
			$(this).scrollTop(0);
			$('#dialog\\.generalPrompt\\.form\\.input\\.text').val(presetVal ? presetVal : '').focus();
		}
	});
	$('#dialog\\.generalPrompt\\.message').html(msg);
	$('#dialog\\.generalPrompt').dialog('open');
};
GeneralJqDialog.prototype.fnShowGeneralDetailsDialog = function(title, msg, callback)
{
	var maxDialogWidth = 500;
	var minDialogWidth = 300;
	$("#dialog\\.generalDetails").dialog({
		width: 400,
		position: { my: 'center', at: 'center' },
		height: "auto",
		minHeight: 200,
		minWidth: 300,
		title: title,
		modal: true,
		open : function() {
			$(this).scrollTop(0);
		},
		buttons: [ {
			text: this.buttonClose,
			click: function() {
				$('#dialog\\.generalDetails').dialog('close');
				if (callback && callback != null)
					callback();
			}
		} ]
	});
	$("#dialog\\.generalDetails\\.message").html(msg);
	$("#dialog\\.generalDetails").dialog("open");
	if ($("#dialog\\.generalDetails").width() > maxDialogWidth) $("#dialog\\.generalDetails").width(maxDialogWidth);
	if ($("#dialog\\.generalDetails").width() < minDialogWidth) $("#dialog\\.generalDetails").width(minDialogWidth);
	$("#dialog\\.generalDetails").dialog({position: "center"});
};

var generalJqDialog_g = new GeneralJqDialog();

$(document).ready(function() {
	// general confirm dialog
	$('#dialog\\.generalConfirm').dialog({
		autoOpen : false,
		width: 400,
		height: 'auto',
		minHeight : 200,
		minWidth : 300,
		//resizable : false,
		modal : true,
		zIndex : 9010,
		closeOnEscape : false,
		buttons : [ {
			text : generalJqDialog_g.buttonConfirm,
			click : function() {
				$(this).dialog('close');
			}
		}, {
			text : generalJqDialog_g.buttonCancel,
			click : function() {
				$(this).dialog('close');
			}
		} ],
		open : function() {
			$(this).scrollTop(0);
			$(this).parent().find('button:nth-child(2)').focus();
		}
	});
	// general alert dialog
	$('#dialog\\.generalAlert').dialog({
		autoOpen : false,
		width: 400,
		minHeight : 200,
		minWidth : 300,
		//resizable : false,
		modal : true,
		zIndex : 9020,
		closeOnEscape : false,
		buttons : [ {
			text : generalJqDialog_g.buttonClose,
			click : function() {
				$(this).dialog('close');
			}
		} ],
		open : function() {
			$(this).scrollTop(0);
		}
	});

	// general information dialog
	$('#dialog\\.generalInformation').dialog({
		autoOpen : false,
		width: 400,
		height: 'auto',
		minHeight : 200,
		minWidth : 300,
		//resizable : false,
		modal : true,
		zIndex : 9020,
		closeOnEscape : false,
		buttons : [ {
			text : generalJqDialog_g.buttonOk,
			click : function() {
				$(this).dialog('close');
			}
		} ],
		open : function() {
			$(this).scrollTop(0);
			//$(this).parent().find('button').blur();
		}
	});
	// general prompt dialog
	$('#dialog\\.generalPrompt').dialog({
		
		autoOpen : false,
		width: 400,
		height: 'auto',
		minHeight : 200,
		minWidth : 300,
		//resizable : false,
		modal : true,
		zIndex : 9010,
		closeOnEscape : false,
		buttons : [ {
			text : generalJqDialog_g.buttonOk,
			click : function() {
				$(this).dialog('close');
			}
		} ],
		open : function() {
			$(this).scrollTop(0);
		}
	});
	$('#dialog\\.generalPrompt').find('#dialog\\.generalPrompt\\.form').submit(function(event) {
		event.preventDefault();
		$(this).parent().parent().parent().find('button:first').click();
	});

	if (typeof afterGeneralJqDialogReady === 'function') {
		afterGeneralJqDialogReady();
	}
});
</script>
<div style="display:none;">
<div id="dialog.generalConfirm" title="General Confirm">
	<table><tr>
	<td valign="top">
	<span style="float: left; margin: 0 5px 5px 0; display: block;"><img src="<c:url value="/images/dialog-confirm.png"/>" alt="?" border="0"/></span>
	</td>
	<td>
	<div id="dialog.generalConfirm.message" style="color: blue; font-size: 10pt;"></div>
	</td>
	</tr></table>
</div>
<div id="dialog.generalAlert" title="General Alert">
	<table><tr>
	<td valign="top">
	<span style="float: left; margin: 0 5px 5px 0; display: block;"><img src="<c:url value="/images/dialog-alert.png"/>" alt="?" border="0"/></span>
	</td>
	<td>
	<div id="dialog.generalAlert.message" style="color: red; font-size: 10pt;"></div>
	</td>
	</tr></table>
</div>
<div id="dialog.generalInformation" title="General Information">
	<table><tr>
	<td valign="top">
	<span style="float: left; margin: 0 5px 5px 0; display: block;"><img src="<c:url value="/images/dialog-info.png"/>" alt="?" border="0"/></span>
	</td>
	<td>
	<div id="dialog.generalInformation.message" style="font-size: 10pt;"></div>
	</td>
	</tr></table>
</div>
<div id="dialog.generalPrompt" title="General Prompt">
	<table><tr>
	<td valign="top">
	<span style="float: left; margin: 0 5px 5px 0; display: block;"><img src="<c:url value="/images/dialog-prompt.png"/>" alt="?" border="0"/></span>
	</td>
	<td>
	<div id="dialog.generalPrompt.message" style="font-size: 10pt;"></div>
	<div>
		<form id="dialog.generalPrompt.form">
			<input type="text" name="dialog.generalPrompt.form.input.text" id="dialog.generalPrompt.form.input.text" class="field ui-widget-content ui-corner-all" style="width: 100%;" value=""/>
		</form>
	</div>
	</td>
	</tr></table>
</div>
<div id="dialog.generalDetails" title="General Details">
	<div id="dialog.generalDetails.message" style="font-size: 10pt;"></div>
</div>
</div>
