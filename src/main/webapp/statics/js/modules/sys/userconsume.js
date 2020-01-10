$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/userConsume/log',
        datatype: "json",
        colModel: [
            { label: '创建时间', name: 'createTime', index: 'createTime', width: 80,
                formatter: function (value) {
                    return value == "" || value == null ? "" : formatDate(new Date(value))
                }
            },
            { label: '订单编号', name: 'orderNo', index: 'orderNo', width: 80 },
            { label: '流水号', name: 'transactionNo', index: 'transactionNo', width: 80 },
            { label: '商品名称', name: 'goodsName', index: 'goodsName', width: 80 },
            { label: '合计价格(元)', name: 'totalPrice', index: 'totalPrice', width: 80 },
            { label: '单价(元)', name: 'price', index: 'price', width: 80 },
            { label: '数量(元)', name: 'num', index: 'num', width: 80 },
            { label: '微信openId', name: 'openId', index: 'openId', width: 80 },
            {label: '姓名', name: 'name', index: 'name', width: 60},
            {label: '手机号', name: 'phone', index: 'phone', width: 60}

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