
$(function () {
    vm.loadMonyTotal();
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/glWallet/list',
        datatype: "json",
        colModel: [
            {label: '金额(元)', name: 'money', index: 'money', width: 60,
                formatter:function(cellvalue, options, rowObject){
                    var str = "";
                    if(rowObject["type"] == 4){
                        str = "<span style='color: red;'>-</span>";
                    }else{
                        str = "<span style='color: red;'>+</span>";
                    }
                    return  str +"&nbsp;"+ parseFloat(cellvalue).toFixed(2)/100;
                }},
            {label: '类型', name: 'type', index: 'type', width: 60,
                formatter:function(value){
                    if (value == 0) {
                        return '个联的佣金';
                    } else if (value == 1) {
                        return '用户购买纸巾';
                    }else if(value == 2){
                        return '广告商充值';
                    }else if(value == 3){
                        return '代理商购买商品';
                    }else if(value == 4) {
                        return '提现';
                    }
            }
            },
            { label: '创建时间', name: 'createTime', index: 'createTime', width: 80,
                formatter: function (value) {
                    return value == "" || value == null ? "" : formatDate(new Date(value))
                }
            },
            {label: '操作', name: 'id', index: 'id',width:60, formatter: function (value) {
                return '<a href="javascript:void(0)" onclick="vm.updateFirm(' + value + ')">详情</a>'
            }}
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
            type: null
        },
        moneyTotal:0
    },
    methods: {
        query: function () {
            vm.reload();
        },
        reload: function (event) {
            vm.closeAllLayer();
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                page:page,
                postData: {'type':vm.q.type,"startTime": $("#sdate").val(),
                    "endTime": $("#edate").val()}
            }).trigger("reloadGrid");
        },
        updateFirm:function(e){                        //固件管理 - 重新上传
            vm.getInfo(e);
            layer.open({
                type: 1,//弹出一个也m页面层
                offset: '100px',
                skin: 'layui-layer-molv',
                title: "详情",
                area: ['450px', '250px'],
                shade: 0,
                shadeClose: false,
                content: $("#anewFileWindow")
            });
        },
        closeAllLayer:function () {
            layer.closeAll();
        },
        getInfo: function (id) {
            $.get(baseURL + "sys/glWallet/info/" + id, function (result) {
                vm.userLikeGzh = result.userLikeGzh;
                vm.userLikeGzh.money = vm.userLikeGzh.money/100;
            });
        },
        loadMonyTotal:function(){
           //vm.moneyTotal = ajaxPost(baseURL + 'sys/glWallet/moneyTotal',null).moneyTotal;
            $.ajax({
                type: "POST",
                url: baseURL + 'sys/glWallet/moneyTotal',
                async:false,
                dataType: "json",
                success: function(r){
                    if(r.code === 1){
                        vm.moneyTotal = parseFloat(r.moneyTotal).toFixed(2)/100;
                    }else{
                        alert(r.msg);
                    }
                }
            });
        }
    }
});



