$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/consumerecords/list',
        datatype: "json",
        colModel: [
            { label: '序号', name: 'id', index: 'id', width: 30, key: true },
            {
                label: '用户名', name: 'name', index: 'name', width: 35
            },
            {label: '设备编号', name: 'deviceNo', index: 'deviceNo', width: 60},
            {
                label: '合计价格', name: 'totalPrice', index: 'totalPrice', width: 45,
                formatter: function (value) {
                    return value / 100;
                }
            },
            {
                label: '数量(包)', name: 'num', index: 'num', width: 35
            },
            {
                label: '单价(元)', name: 'price', index: 'price', width: 35,
                formatter: function (value) {
                    return value / 100;
                }
            },
            {label: '状态', name: 'status', index: 'status', width: 40,
                formatter: function (value) {
                    if(value != null){
                        if(value == 0){
                            return '未支付';
                        }else if (value == 1) {
                            return '已完成';
                        } else if (value == 2) {
                            return '未出货';
                        }
                    }else {
                        return '无操作';
                    }
                }
            },
            {
                label: '流水号', name: 'transactionNo', index: 'transactionNo', width: 115
            },
            {
                label: 'IMEI', name: 'imei', index: 'imei', width: 70
            },
            {
                label: '订单编号', name: 'orderNo', index: 'orderNo', width: 80
            },
            {
                label: '商品名称', name: 'goodsName', index: 'goodsName', width: 40
            },
            {
                label: '创建时间', name: 'createTime', index: 'createTime', width: 80,
                formatter: function (value) {
                    return value == "" || value == null ? "" : formatDate(new Date(value))
                }
            }
            /*,
            {
                label: '商品图片地址', name: 'goodsImgUrl', index: 'goodsImgUrl', width: 70,
                formatter:function (value) {
                    return "<img src='"+ value+"' weight='80pk' height='80pk' />"
                }
            }*/
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
        consumerecords: {},
        q:{
            status:null
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },

        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.consumerecords = {};
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
            var url = vm.consumerecords.id == null ? "sys/consumerecords/save" : "sys/consumerecords/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.consumerecords),
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
                    url: baseURL + "sys/consumerecords/delete",
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
            $.get(baseURL + "sys/consumerecords/info/" + id, function (result) {
                vm.consumerecords = result.consumerecords;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page,
                postData:{'status':vm.q.status}
            }).trigger("reloadGrid");
        }

    }
});