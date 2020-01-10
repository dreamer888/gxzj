//jqGrid的配置信息
$.jgrid.defaults.width = 1000;
$.jgrid.defaults.responsive = true;
$.jgrid.defaults.styleUI = 'Bootstrap';

var baseURL = "../../";

//工具集合Tools
window.T = {};

// 获取请求参数
// 使用示例
// location.href = http://localhost:8080/index.html?id=123
// T.p('id') --> 123;
var url = function(name) {
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r!=null)return  unescape(r[2]); return null;
};
T.p = url;

//全局配置
$.ajaxSetup({
	dataType: "json",
	cache: false
});

//重写alert
window.alert = function(msg, callback){
	parent.layer.alert(msg, function(index){
		parent.layer.close(index);
		if(typeof(callback) === "function"){
			callback("ok");
		}
	});
}

//重写confirm式样框
window.confirm = function(msg, callback){
	parent.layer.confirm(msg, {btn: ['确定','取消']},
	function(){//确定事件
		if(typeof(callback) === "function"){
			callback("ok");
		}
	});
}

//选择一条记录
function getSelectedRow() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
    	alert("请选择一条记录");
    	return ;
    }
    
    var selectedIDs = grid.getGridParam("selarrrow");
    if(selectedIDs.length > 1){
    	alert("只能选择一条记录");
    	return ;
    }
    
    return selectedIDs[0];
}

//选择多条记录
function getSelectedRows() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
    	alert("请选择一条记录");
    	return ;
    }
    
    return grid.getGridParam("selarrrow");
}

//判断是否为空
function isBlank(value) {
    return !value || !/\S/.test(value)
}

//判断是否是数字
function isNumber(value) {
    return !/^([0-9][0-9]*)+(.[0-9]{1,2})?$/.test(value)
}

//判断是否是有效的手机号
function isPhoneNum(phone) {
    var phoneReg = /^(((13[0-9]{1})|(14[0-9]{1})|(17[0]{1})|(15[0-3]{1})|(15[5-9]{1})|(18[0-9]{1}))+\d{8})$/;
    if (!phoneReg.test(phone)) {
    	return false;
	}
	return true;
}

//格式化时间
function formatDate(now) {
    var year = now.getFullYear(),
        month = now.getMonth() + 1,
        date = now.getDate(),
        hour = now.getHours(),
        minute = now.getMinutes(),
        second = now.getSeconds();

    return year + "-" + fixZero(month,2) + "-" + fixZero(date,2) + " " + fixZero(hour,2) + ":" + fixZero(minute,2) + ":" + fixZero(second,2);
}

//时间如果为单位数补0
function fixZero(num, length) {
    var str = "" + num;
    var len = str.length;
    var s = "";
    for(var i = length; i-- > len;) {
        s += "0";
    }
    return s + str;
}


window.ajaxPost = function(url,data){
    var d;
    $.ajax({
        type: "POST",
        url: baseURL + url,
        async:false,
        dataType: "json",
        data: data,
        success: function(r){
            if(r.code === 1){
                d = r;
            }else{
                alert(r.msg);
            }
        }
    });
    return d;
}