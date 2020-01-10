$(function () {
    vm.loadMonyTotal();
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/deviceconsumetotal/list',
        datatype: "json",
        colModel: [
            { label: '序号', name: 'id', index: 'id', width: 50, key: true },
            {label: '代理商姓名', name: 'name', index: 'name', width: 60,
                formatter:function (value) {
                    if(value == null || value == ''){
                        return '个联总代';
                    }else {
                        return value;
                    }
                }
            },
            {label: '设备号', name: 'deviceNo', index: 'deviceNo', width: 80},
            {label: '设备IMEI', name: 'deviceImei', index: 'deviceImei', width: 80},
            {label: '免费领取数量', name: 'freePaperTotal', index: 'freePaperTotal', width: 70},
            {label: '购买商品数量', name: 'payPaperTotal', index: 'payPaperTotal', width: 70},
            {label: '商品单价(元)', name: 'goodsPrice', index: 'goodsPrice', width: 70,
                formatter: function (value) {
                    return value / 100;
                }
            },
            /*{label: '商品单价', name: 'price', index: 'price', width: 70},*/
            {label: '设备收益(元)', name: 'payPaperSum', index: 'payPaperSum', width: 70,
                formatter: function (value) {
                    return value / 100;
                }
            },
            {
                label: '创建时间', name: 'createTime', index: 'createTime', width: 80,
                formatter: function (value) {
                    return value == "" || value == null ? "" : formatDate(new Date(value))
                }
            }
        ],
        viewrecords: true,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        height:600,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            /*vm.priceTotal = 0;
            var arr = $("#jqGrid").jqGrid('getRowData');
            var arrid = $("#jqGrid").jqGrid('getDataIDs');
            if(arrid.length == 0)return;
            arr.push($("#jqGrid").jqGrid('getRowData',arrid[arrid.length-1]));
            for(var i=0;i<arr.length;i++){
                vm.priceTotal += parseFloat(arr[i].payPaperSum);
                vm.num = Math.round(vm.priceTotal * 100)/100;
            }*/
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });

});

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        deviceConsumeTotal: {},
        num:0,
        q:{
          name:null,
          deviceNo:null
        },
        priceTotal:0
    },
    methods: {
        query: function () {
            vm.reload();
        },

        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.deviceConsumeTotal = {};
        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
        },
        saveOrUpdate: function (event) {
            var url = vm.deviceConsumeTotal.id == null ? "sys/deviceconsumetotal/save" : "sys/deviceconsumetotal/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.deviceConsumeTotal),
                success: function (result) {
                    if (result.code === 0) {
                        alert('操作成功', function (index) {
                            vm.reload();
                        });
                    } else {
                        alert(result.msg);
                    }
                }
            });
        },
        del: function (event) {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/deviceconsumetotal/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (result) {
                        if (result.code == 0) {
                            alert('操作成功', function (index) {
                                $("#jqGrid").trigger("reloadGrid");
                            });
                        } else {
                            alert(result.msg);
                        }
                    }
                });
            });
        },
        getInfo: function (id) {
            $.get(baseURL + "sys/deviceconsumetotal/info/" + id, function (result) {
                vm.deviceConsumeTotal = result.deviceConsumeTotal;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page,
                postData:{'name':vm.q.name,'deviceNo':vm.q.deviceNo,"startTime": $("#sdate").val(),
                    "endTime": $("#edate").val()}
            }).trigger("reloadGrid");
        },
        loadMonyTotal:function() {
            $.ajax({
                type: "POST",
                url: baseURL + 'sys/deviceconsumetotal/priceTotal',
                async: false,
                dataType: "json",
                success: function (r) {
                    if (r.code === 1) {
                        vm.priceTotal = parseFloat(r.priceTotal)/100;
                    } else {
                        alert(r.msg);
                    }
                }
            });
        }

    }
});