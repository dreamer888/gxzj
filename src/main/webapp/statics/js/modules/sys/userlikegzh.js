$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/userLikeGzh/list',
        datatype: "json",
        colModel: [
            { label: '微信openId', name: 'openId', index: 'openId', width: 80 },
            {label: '昵称', name: 'name', index: 'name', width: 60},
            /*{label: '手机号', name: 'phone', index: 'phone', width: 60},*/
            {label: '所在城市', name: 'city', index: 'city', width: 60},
            /*{label: '公众号AppID', name: 'appId', index: 'appId', width: 60},
            { label: '公众号名称', name: 'gzhName', index: 'gzhName', width: 80 },*/
            { label: '创建时间', name: 'createTime', index: 'createTime', width: 80,
                formatter: function (value) {
                    return value == "" || value == null ? "" : formatDate(new Date(value))
                }
            }
        ],
        viewrecords: true,
        height: 600,
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
        userLikeGzh: {},
        q:{
            name: null
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{'name': vm.q.name},
                page:page
            }).trigger("reloadGrid");
        }
    }
});