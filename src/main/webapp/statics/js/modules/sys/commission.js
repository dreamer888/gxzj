$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/commission/list',
        datatype: "json",
        colModel: [
            { label: '序号', name: 'id', index: 'id', width: 30, key: true },
            {
                label: '代理账号', name: 'phone', index: 'phone', width: 60
            },
            {
                label: '账号身份', name: 'role', index: 'role', width: 40,
                formatter: function (value) {
                    if(value != null){
                        if (value == 0) {
                            return '普通用户';
                        } else if (value == 1) {
                            return '省代理';
                        } else if (value == 2) {
                            return '市代理';
                        }else if (value == 3) {
                            return '区代理';
                        } else if (value == 4) {
                            return '个人代理';
                        }
                    }else {
                        return '无操作';
                    }
                }
            },
            {
                label: '姓名', name: 'name', index: 'name', width: 40
            },
            {
                label: '地区', name: 'city', index: 'city', width: 60
            },
            {
                label: '电话', name: 'phone', index: 'phone', width: 60
            },
            {
                label: '佣金收益(元)', name: 'money', index: 'money', width: 35,
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
            vm.priceTotal = 0;
            var arr = $("#jqGrid").jqGrid('getRowData');
            var arrid = $("#jqGrid").jqGrid('getDataIDs');
            if(arrid.length == 0)return;
            arr.push($("#jqGrid").jqGrid('getRowData',arrid[arrid.length-1]));
            for(var i=0;i<arr.length;i++){
                console.log(arr[i].money);
                vm.priceTotal += parseFloat(arr[i].money);
                vm.num = Math.round(vm.priceTotal * 100)/100;
            }
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
        commission: {},
        priceTotal:0,
        num:0,
        q:{
            role:null
        }

    },
    methods: {
        query: function () {
            vm.reload();
        },

        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.commission = {};
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
            var url = vm.commission.id == null ? "sys/commission/save" : "sys/commission/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.commission),
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
                    url: baseURL + "sys/commission/delete",
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
            $.get(baseURL + "sys/commission/info/" + id, function (result) {
                vm.commission = result.commission;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page,
                postData:{'role':vm.q.role,"startTime": $("#sdate").val(),
                    "endTime": $("#edate").val()}
            }).trigger("reloadGrid");
        }

    }
});