/**
 * 人性化时间处理 传入时间戳
 */
export function beautifyTime (strDate) {
	var timestamp = new Date(strDate);
	var mistiming = Math.round(new Date()) - timestamp;
	var postfix = mistiming > 0 ? '前' : '后'
	mistiming = Math.abs(mistiming / 1000)
	var arrr = ['年', '个月', '星期', '天', '小时', '分钟', '秒'];
	var arrn = [31536000, 2592000, 604800, 86400, 3600, 60, 1];
	for (var i = 0; i < 7; i++) {
		var inm = Math.floor(mistiming / arrn[i])
		if (inm != 0) {
			return inm + arrr[i] + postfix
		}
	}
}
//第一种：时间格式为 2020.08.09
export function timestampToData (shijian) {
	var date = new Date(shijian)
	var Y = date.getFullYear() + '.'
	var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '.'
	var D = (date.getDate() < 10 ? '0' + date.getDate() : date.getDate()) + ''
	return Y + M + D
}

// 时间格式为 2020/08/09
export function timestampToDataByLine (shijian) {
	var date = new Date(shijian)
	var Y = date.getFullYear() + '/'
	var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '/'
	var D = (date.getDate() < 10 ? '0' + date.getDate() : date.getDate()) + ''
	return Y + M + D
}

//第二种：时间格式为 2020/08/09 06:05
export function timestampToTime (shijian) {
	var date = new Date(shijian)
	var Y = date.getFullYear() + '/'
	var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '/'
	var D = (date.getDate() < 10 ? '0' + date.getDate() : date.getDate()) + ' '
	var h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':'
	var m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()) 
	// var s = (date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds())
	// return Y + M + D + h + m + s
	return Y + M + D + h + m
}

//第二种：时间格式为 12分钟
export function timestampToM (shijian) {
	var date = new Date(shijian)
	// var Y = date.getFullYear() + '/'
	// var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '/'
	// var D = (date.getDate() < 10 ? '0' + date.getDate() : date.getDate()) + ' '
	// var h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':'
	var m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()) 
	// var s = (date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds())
	// return Y + M + D + h + m + s
	return m
}
//时间格式为 2020年08月09日 06:05:04
export function timestampToTimeByStr (shijian) {
	var date = new Date(shijian)
	var Y = date.getFullYear() + '年'
	var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '月'
	var D = (date.getDate() < 10 ? '0' + date.getDate() : date.getDate()) + '日 '
	var h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':'
	var m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()) + ':'
	var s = (date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds())
	return Y + M + D + h + m + s
}
export function formatDate (time, format) {
	var time = new Date(time);
	var date = {
		"M+": time.getMonth() + 1,
		"d+": time.getDate(),
		"h+": time.getHours(),
		"m+": time.getMinutes(),
		"s+": time.getSeconds(),
		"q+": Math.floor((time.getMonth() + 3) / 3),
		"S+": time.getMilliseconds()
	};
	if (/(y+)/i.test(format)) {
		format = format.replace(RegExp.$1, (time.getFullYear() + '').substr(4 - RegExp.$1.length));
	}
	for (var k in date) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ?
				date[k] : ("00" + date[k]).substr(("" + date[k]).length));
		}
	}
	return format;
}
