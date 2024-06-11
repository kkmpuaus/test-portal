if (typeof String.prototype.startsWith == 'undefined') {
	String.prototype.startsWith = function(prefix) {
		return this.indexOf(prefix) === 0;
	};
}
if (typeof String.prototype.endsWith == 'undefined') {
	String.prototype.endsWith = function(suffix) {
		// return this.match(suffix.replace('.', '\\.').replace('[', '\\[').replace(']', '\\]') + '$') == suffix;
		return this.match(suffix.replace(new RegExp('\\.', 'g'), '\\.').replace(new RegExp('\\[', 'g'), '\\[').replace(
				new RegExp('\\]', 'g'), '\\]')
				+ '$') == suffix;
	};
}
if (typeof String.prototype.trimL == 'undefined') {
	String.prototype.trimL = function() {
		return this.replace(/(^\s*)/g, '');
	};
}
if (typeof String.prototype.trimR == 'undefined') {
	String.prototype.trimR = function() {
		return this.replace(/(\s*$)/g, '');
	};
}
if (typeof String.prototype.trim == 'undefined') {
	String.prototype.trim = function() {
		return this.trimL().trimR();
	};
}
if (typeof Array.prototype.indexOf == 'undefined') {
	Array.prototype.indexOf = function(obj) {
		for ( var i = 0; i < this.length; i++) {
			if (this[i] == obj) {
				return i;
			}
		}
		return -1;
	};
}

var isEventSupported = function() {
	var TAGNAMES = {
		'select' : 'input',
		'change' : 'input',
		'submit' : 'form',
		'reset' : 'form',
		'error' : 'img',
		'load' : 'img',
		'abort' : 'img'
	};

	function isEventSupported(eventName, element) {
		element = element || document.createElement(TAGNAMES[eventName] || 'div');
		eventName = 'on' + eventName;

		// When using `setAttribute`, IE skips "unload", WebKit skips "unload" and "resize", whereas `in` "catches"
		// those
		var isSupported = eventName in element;

		if (!isSupported) {
			// If it has no `setAttribute` (i.e. doesn't implement Node interface), try generic element
			if (!element.setAttribute) {
				element = document.createElement('div');
			}
			if (element.setAttribute && element.removeAttribute) {
				element.setAttribute(eventName, '');
				isSupported = typeof element[eventName] == 'function';

				// If property was created, "remove it" (by setting value to `undefined`)
				if (typeof element[eventName] != 'undefined') {
					element[eventName] = undefined;
				}
				element.removeAttribute(eventName);
			}
		}
		element = null;

		return isSupported;
	}

	return isEventSupported;
}();
var isFileAPIEnabled = function() {
	return !!window.FileReader;
};

function buildDataMapForForm(formId) {
	var _form = $('form#' + formId);
	var _data = {};
	if (_form.length == 1) {
		_form.find('input[type=text]:enabled').each(function() {
			_data[this.name] = this.value;
		});
		_form.find('input[type=password]:enabled').each(function() {
			_data[this.name] = this.value;
		});
		_form.find('input[type=checkbox]:enabled:checked').each(function() {
			_data[this.name] = this.value;
		});
		_form.find('input[type=radio]:enabled:checked').each(function() {
			_data[this.name] = this.value;
		});
		_form.find('input[type=hidden]').each(function() {
			_data[this.name] = this.value;
		});
		_form.find('select:enabled').each(function() {
			_data[this.name] = $(this).val();
		});
		_form.find('textarea:enabled').each(function() {
			_data[this.name] = $(this).val();
		});
	}

	return _data;
}

function ajaxSubmitForm(formId, successCallback, dataType) {
	if (!dataType) {
		dataType = 'html';
	}
	var _form = $('form#' + formId);
	if (jQuery && _form.length == 1) {
		var _data = buildDataMapForForm(formId);
		jQuery.post(_form.attr('action'), _data, function(data, textStatus, jqXHR) {
			if (successCallback) {
				successCallback(textStatus, data);
			}
		}, dataType);
	}
}

function disableAllFormFields(formId) {
	var _form = $('form#' + formId);
	if (_form.length == 1) {
		_form.find('input.main-text').attr('disabled', true);
		_form.find('select.main-select').attr('disabled', true);
		_form.find('textarea.main-textarea').attr('disabled', true);
	}
}

function enableAllFormFields(formId) {
	var _form = $('form#' + formId);
	if (_form.length == 1) {
		_form.find('input.main-text').attr('disabled', false);
		_form.find('select.main-select').attr('disabled', false);
		_form.find('textarea.main-textarea').attr('disabled', false);
	}
}

function synchronizeTableColumnWidth(table1Sel, table2Sel, colsWidthPercent) {
	var _table1 = $(table1Sel);
	var _table2 = $(table2Sel);
	if (_table1.length == 1 && _table2.length == 1) {
		var _table1Rows = _table1.children('thead').children('tr');
		var _table2Rows = _table2.children('tbody').children('tr');
		if (_table1Rows.length > 0 && _table2Rows.length > 0) {
			var _table1Cols = _table1.children('thead').children('tr').first().children('th');
			var _table2Cols = _table2.children('tbody').children('tr').first().children('td');
			if (_table1Cols.length == _table2Cols.length) {
				if (_table1Cols.length == colsWidthPercent.length) {
					_table1Rows.first().each(function() {
						$(this).children('th').each(function(index) {
							$(this).css('width', colsWidthPercent[index]);
						});
					});
					_table2Rows.first().each(function() {
						$(this).children('td').each(function(index) {
							$(this).css('width', colsWidthPercent[index]);
						});
					});
				}
			}
		} else if (_table1Rows.length > 0 && _table2Rows.length == 0) {
			var _table1Cols = _table1.children('thead').children('tr').first().children('th');
			if (_table1Cols.length == colsWidthPercent.length) {
				_table1Rows.first().each(function() {
					$(this).children('th').each(function(index) {
						$(this).css('width', colsWidthPercent[index]);
					});
				});
			}
		}
	}
}

function sortingNumberAsc(n1, n2) {
	return n1 - n2;
}

function soringNumberDesc(n1, n2) {
	return n2 - n1;
}

function setupAnotherAutoCompleteForMasterDataField(id, params) {
	var _obj = $('#' + id.replace(/\./g, '\\\.'));
	setupAnotherAutoCompleteForMasterDataFieldByObject(_obj, params);
}

function setupAnotherAutoCompleteForMasterDataFieldByObject(obj, params) {
	if (obj.length == 1) {
		var _fieldName = obj.attr('name');
		if (typeof _sFieldName == 'undefined' || _fieldName == null || _fieldName.length == 0) {
			_fieldName = obj.attr('id');
		}
		if (typeof params == 'undefined') {
			params = {};
		}
		var _reqParams = params['requestParams'];
		if (typeof _reqParams == 'undefined') {
			_reqParams = {};
		}
		_reqParams['fieldName'] = _fieldName;
		params['requestParams'] = _reqParams;

		_setupAnotherAutoComplete(obj, '../autosuggest/masterdatafield', params);
	}
}

function setupAnotherAutoCompleteForCodeField(id, url, params) {
	var _obj = $('#' + id.replace(/\./g, '\\\.'));
	setupAnotherAutoCompleteForCodeFieldByObject(_obj, url, params);
}

function setupAnotherAutoCompleteForCodeFieldByObject(obj, url, params) {
	if (obj.length == 1) {
		_setupAnotherAutoComplete(obj, url, params);
	}
}

function _setupAnotherAutoComplete(obj, url, params) {
	if (obj.length == 1) {
		var _params = {
			minChars : 2,
			maxHeight : 380,
			zIndex : 999,
			deferRequestBy : 1000,
			noCache : false
		};
		if (typeof params == 'undefined') {
			params = {};
		}
		for ( var _key in params) {
			if (_key == 'requestParams')
				continue;
			_params[_key] = params[_key];
		}
		var _reqParams = params['requestParams'];
		if (typeof _reqParams == 'undefined') {
			_reqParams = {};
		}
		if (typeof _reqParams['max'] == 'undefined') {
			_reqParams['max'] = userPreferences_g.autoSuggestMaxResultRows;
		}
		_params['requestParams'] = _reqParams;

		obj.anotherautocomplete({
			serviceUrl : url,
			minChars : _params['minChars'],
			maxHeight : _params['maxHeight'],
			// width: 'auto',
			width : obj.width() < 200 ? 200 : obj.width(),
			zIndex : _params['zIndex'],
			deferRequestBy : _params['deferRequestBy'],
			params : _reqParams,
			noCache : _params['noCache'],
			onSelect : function(value, data) {
				obj.val(data);
				obj.blur();
				obj.focus();
			}
		});
	}
}

var blockUiMessage_g = {};
function initBlockUi(data) {
	$.blockUI.defaults.css['padding'] = 0;
	$.blockUI.defaults.css['margin'] = 0;
	$.blockUI.defaults.css['width'] = '40%';
	$.blockUI.defaults.css['top'] = '40%';
	$.blockUI.defaults.css['left'] = '30%';
	$.blockUI.defaults.css['textAlign'] = 'center';
	$.blockUI.defaults.css['color'] = '#333';
	$.blockUI.defaults.css['border'] = '5px solid #0C6B6D';
	$.blockUI.defaults.css['backgroundColor'] = '#D8F4F4';
	$.blockUI.defaults.css['cursor'] = 'auto';
	$.blockUI.defaults.css['border-radius'] = '15px';
	$.blockUI.defaults.css['-moz-border-radius'] = '15px';
	$.blockUI.defaults.css['-webkit-border-radius'] = '15px';
	$.blockUI.defaults.css['-khtml-border-radius'] = '15px';
	$.blockUI.defaults.baseZ = 99999;
	$.blockUI.defaults.fadeIn = 0;
	$.blockUI.defaults.fadeOut = 0;
	for ( var _key in data) {
		var _index = _key.indexOf('message.');
		if (_index == 0) {
			_index += 'message.'.length;
			blockUiMessage_g[_key.substr(_index)] = data[_key];
		}
	}
	if ($('div#blockUi\\.pleaseWait').length == 0) {
		var _html = '';
		_html = _html + '<div id="blockUi.pleaseWait" style="display:none;">';
		_html = _html + '<table width="100%" height="100" border="0" cellpadding="0" cellspacing="0">';
		_html = _html + '<tr>';
		_html = _html + '<td width="100%" height="100%" align="center" valign="middle">';
		_html = _html + '<img src="';
		_html = _html + data['imageSource'];
		_html = _html + '" border="0" align="absmiddle" hspace="4" vspace="2"/>';
		_html = _html
				+ '<span style="color:#333;font-family:Arial,Helvetica,sans-serif;font-size:18px;font-weight:bold;padding-left:10px;">';
		_html = _html + '</span>';
		_html = _html + '</td>';
		_html = _html + '</tr>';
		_html = _html + '</table>';
		_html = _html + '<img src="';
		$(_html).appendTo($('body'));
	}
}

// default key: 'request'
function blockPageUi(key) {
	var _div = $('div#blockUi\\.pleaseWait');
	if (_div.length == 1) {
		if (typeof key == 'undefined' || key == null || key == '') {
			key = 'request';
		}
		var _message = blockUiMessage_g[key];
		if (typeof _message == 'undefined' || _message == null || _message == '') {
			_message = blockUiMessage_g['request'];
		}
		_div.find('span').text(_message);
		$.blockUI({
			message : _div
		});
	}
}

function blockPageUiProcessing() {
	blockPageUi('process');
}

function unblockPageUi() {
	$.unblockUI();
}

function changeDateSep(dateStr) {
	var _from = './';
	var _to = '-';
	if (dateStr.length == 8 && (parseInt(dateStr) + '' == dateStr)) {
		return dateStr.substr(0, 4) + _to + dateStr.substr(4, 2) + _to + dateStr.substr(6);
	}
	var _new = dateStr;
	for ( var _i = 0; _i < _from.length; _i++) {
		_new = _changeDateSep(_new, _from.charAt(_i), _to);
	}
	return _new;
}

function _changeDateSep(dateStr, from, to) {
	var _new = '';
	for ( var _i = 0; _i < dateStr.length; _i++) {
		_new = (dateStr.charAt(_i) == from) ? (_new + to) : (_new + dateStr.charAt(_i));
	}
	return _new;
}

function formatDate(date) {
	if (date == null)
		return '';

	var _year = padZero(date.getFullYear(), 4);
	var _month = padZero(date.getMonth() + 1, 2);
	var _day = padZero(date.getDate(), 2);

	return _year + '-' + _month + '-' + _day;
}
function formatTime(date) {
	if (date == null)
		return '';

	var _hour = padZero(date.getHours(), 2);
	var _minute = padZero(date.getMinutes(), 2);
	var _second = padZero(date.getSeconds(), 2);

	return _hour + ':' + _minute + ':' + _second;
}
function formatDateTime(date) {
	return formatDate(date) + ' ' + formatTime(date);
}
function padZero(num, len) {
	var _s = '' + num;
	while (_s.length < len) {
		_s = '0' + _s;
	}
	return _s;
}

function setupUiLayoutRectifyForLookupIFrame(selector) {
	$(selector).load(function(event) {
		var _html = $(this).contents().find('html');
		if (_html) {
			$(this).css('height', _html.css('height'));

			_html.find('table.srchblock').eq(0).css('border-color', '#0C6B6D');
		}
	});
}
function sortSelectOptions(id) {
	var selectedValue = $('select#' + id).val();
	$('select#' + id + ' option').sort(function(a, b) {
		return a.innerHTML < b.innerHTML ? -1 : 1;
	}).appendTo('select#' + id);
	$('select#' + id).val(selectedValue);
}

function escapeHtml(text) {
	return text.replace(/&/g, '&amp;').replace(/>/g, '&gt;').replace(/</g, '&lt;').replace(/"/g, '&quot;');
}

function splitOneStr2MultiStr(vStr, vTotLine, vLineMax, vSep) {
	var _result = new Array();
	_result[0] = false;
	var _lineCnt = 0;

	var _sepPos = new Array();
	var _i = 0;
	var _start = 0;
	while (true) {
		_sepPos[_i] = vStr.indexOf(vSep, _start);
		if (_sepPos[_i] == -1)
			break;
		_start = _sepPos[_i] + vSep.length;
		_i++;

		if (_i >= vTotLine + 1)
			return _result;
	}

	var _str;
	for (_i = 0; _i < _sepPos.length; _i++) {
		_i == 0 ? _start = 0 : _start = _sepPos[_i - 1] + vSep.length;
		if (_sepPos[_i] != -1)
			_str = vStr.substring(_start, _sepPos[_i]);
		else
			_str = vStr.substring(_start);

		_lineCnt++;
		if (_lineCnt > vTotLine) {
			_result.length = 1;
			return _result;
		}

		if (_str.length <= vLineMax || vLineMax == -1) {
			_result[_lineCnt] = _str;
		} else {
			var _splitStr = _lineAISplitter(_str, vLineMax);
			if (_lineCnt + _splitStr.length - 1 > vTotLine) {
				_result.length = 1;
				return _result;
			}
			_lineCnt--;
			for ( var _k = 0; _k < _splitStr.length; _k++)
				_result[++_lineCnt] = _splitStr[_k];
		}
	}

	for (_i = _result.length; _i <= vTotLine; _i++)
		_result[_i] = '';
	_result[0] = true;

	if (vSep == '\n') {
		for ( var _j = 1; _j < _result.length; _j++) {
			if (_result[_j].charAt(_result[_j].length - 1) == '\r')
				_result[_j] = _result[_j].substr(0, _result[_j].length - 1);
		}
	}

	return _result;
}

function groupMultiStr2OneStr(vStrs, vStart, vEnd, vSep) {
	var _result = '';

	if (vEnd > vStrs.length)
		vEnd = vStrs.length;

	var _i = 0;
	for (_i = vEnd - 1; _i >= vStart; _i--) {
		if (vStrs[_i] == '')
			vEnd--;
		else
			break;
	}
	for (_i = vStart; _i < vEnd; _i++) {
		_result = _result + vStrs[_i];
		if (_i != vEnd - 1)
			_result = _result + vSep;
	}

	return _result;
}