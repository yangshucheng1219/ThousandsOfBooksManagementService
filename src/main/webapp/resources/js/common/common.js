Date.prototype.Format = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}
/**
 * 
 */
function changeVerifyCode(img) {
	img.src = "../Kaptcha?" + Math.floor(Math.random() * 100);
}



function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null) {
		return decodeURIComponent(r[2]);
	}
	return '';
}

/**
 * 获取项目的ContextPath以便修正图片路由让其正常显示
 * @returns
 */
function getContextPath(){
	return "/o2o/";
}

// Zepto.cookie plugin
//
// Copyright (c) 2010, 2012
// @author Klaus Hartl (stilbuero.de)
// @author Daniel Lacy (daniellacy.com)
//
// Dual licensed under the MIT and GPL licenses:
// http://www.opensource.org/licenses/mit-license.php
// http://www.gnu.org/licenses/gpl.html
;(function($){
	$.extend($.fn, {
		cookie : function (key, value, options) {
			var days, time, result, decode

			// A key and value were given. Set cookie.
			if (arguments.length > 1 && String(value) !== "[object Object]") {
				// Enforce object
				options = $.extend({}, options)

				if (value === null || value === undefined) options.expires = -1

				if (typeof options.expires === 'number') {
					days = (options.expires * 24 * 60 * 60 * 1000)
					time = options.expires = new Date()

					time.setTime(time.getTime() + days)
				}

				value = String(value)

				return (document.cookie = [
					encodeURIComponent(key), '=',
					options.raw ? value : encodeURIComponent(value),
					options.expires ? '; expires=' + options.expires.toUTCString() : '',
					options.path ? '; path=' + options.path : '',
					options.domain ? '; domain=' + options.domain : '',
					options.secure ? '; secure' : ''
				].join(''))
			}

			// Key and possibly options given, get cookie
			options = value || {}

			decode = options.raw ? function (s) { return s } : decodeURIComponent

			return (result = new RegExp('(?:^|; )' + encodeURIComponent(key) + '=([^;]*)').exec(document.cookie)) ? decode(result[1]) : null
		}

	})
})(Zepto)