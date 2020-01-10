$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/exceptionMessage/list',
        datatype: "json",
        colModel: [
            { label: '设备编号', name: 'deviceNo', index: 'deviceNo', width: 80 },
            {label: '地址', name: 'addressDetail', index: 'addressDetail', width: 60},
            {label: '消息内容', name: 'context', index: 'context', width: 60},
            {label: '代理人', name: 'agentName', index: 'agentName', width: 60},
            {label: '运维人员', name: 'opsName', index: 'opsName', width: 60},
            {label: '运维热线', name: 'opsPhone', index: 'opsPhone', width: 60},
            { label: '创建时间', name: 'createTime', index: 'createTime', width: 80,
                formatter: function (value) {
                    return value == "" || value == null ? "" : formatDate(new Date(value))
                }
            }
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList : [10,30,50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page",
            rows:"limit",
            order: "order"
        },
        gridComplete:function(){
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }

    });
});

var vm = new Vue({
    el:'#rrapp',
    data:{
        showList: true,
        title: null,
        device: {},
        q:{
            name: null
        },
        exceptionmessage:{}
    },
    methods: {
        query: function () {
            vm.reload();
        },add: function(){
            vm.showList = false;
            vm.title = "异常推送";
            vm.getdevice();
            vm.exceptionmessage.deviceId= "";

        },
        saveOrUpdate: function (event) {
            var url = "sys/exceptionMessage/save";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.exceptionmessage),
                success: function(result){
                    if(result.code === 0){
                        alert('操作成功', function(index){
                            vm.reload();
                        });
                    }else{
                        alert(result.msg);
                    }
                }
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{'name': vm.q.name},
                page:page
            }).trigger("reloadGrid");
        },
        getdevice:function(){
            $.get(baseURL + "sys/device/select", function(r){
                vm.device = r.device;

            });
        }
    }
});