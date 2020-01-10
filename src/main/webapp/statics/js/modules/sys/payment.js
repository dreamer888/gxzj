$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/glWallet/payment',
        datatype: "json",
        colModel: [
            { label: '金额（元）', name: 'money', index: 'money', width: 80 ,
                formatter:function(value){
                    return  value==null?value=0:parseFloat(value).toFixed(2)/100;
                }},
            {label: '类型', name: 'type', index: 'type', width: 60,
                formatter:function(value){
                    return  value ==1?value= "用户购买纸巾":value==2?value="广告商充值":value==3?value="代理商购买商品":"提现"
                }},
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