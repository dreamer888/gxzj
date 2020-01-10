$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/income/list',
        datatype: "json",
        colModel: [
            { label: '公众号收益(元)', name: 'gzhIncomeSum', index: 'gzhIncomeSum', width: 80 ,
                formatter:function(value){
                    return  value==null?value=0:parseFloat(value).toFixed(2)/100;
                }},
            {label: '设备收益(元)', name: 'payPaperSum', index: 'payPaperSum', width: 60,
                formatter:function(value){
                    return  value==null?value=0:parseFloat(value).toFixed(2)/100;
                }},
            {label: '广告收益(元)', name: 'advIncome', index: 'advIncome', width: 60,
                formatter:function(value){
                    return  value==null?value=0:parseFloat(value).toFixed(2)/100;
                }},
            {label: '佣金收益(元)', name: 'commissionSum', index: 'commissionSum', width: 60,
                formatter:function(value){
                    return  value==null?value=0:parseFloat(value).toFixed(2)/100;
                }},
            {label: '汇总统计(元)', name: 'RowCount', index: 'RowCount', width: 60,
                formatter:function(value){
                    return  value==null?value=0:parseFloat(value).toFixed(2)/100;
                }},
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
                postData:{'name': vm.q.name,"startTime": $("#sdate").val(),
                    "endTime": $("#edate").val()},
                page:page
            }).trigger("reloadGrid");
        }
    }
});