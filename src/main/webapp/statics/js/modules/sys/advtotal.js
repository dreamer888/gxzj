$(function () {
    vm.loadMonyTotal();
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/advtotal/list',
        datatype: "json",
        colModel: [
            { label: '序号', name: 'id', index: 'id', width: 50, key: true },
            {label: '广告主', name: 'aName', index: 'aName', width: 50},
            {label: '点击量(次)', name: 'clickNum', index: 'clickNum', width: 80},
            {label: '展示量(次)', name: 'showNum', index: 'showNum', width: 50},
            {
                label: '广告名称', name: 'name', index: 'name', width: 80
            },
            {
                label: '广告类型', name: 'advType', index: 'advType', width: 80,
                formatter: function (value) {
                    if (value == 1) {
                        return 'CPC';
                    } else if (value == 2) {
                        return 'CPM';
                    }
                }
            },
            {
                label: '广告价格(元)', name: 'goodsPrice', index: 'goodsPrice', width: 80/*,
                formatter: function (value) {
                    return value / 100;
                }*/
            },
            {
                label: '广告收益(元)', name: 'inCome', index: 'inCome', width: 80/*,
                formatter: function (value) {
                    return value / 100;
                }*/
            },
            {
                label: '投放开始时间', name: 'startTime', index: 'startTime', width: 80,
                formatter: function (value) {
                    return value == "" || value == null ? "" : formatDate(new Date(value))
                }
            },
            {
                label: '投放结束时间', name: 'endTime', index: 'endTime', width: 80,
                formatter: function (value) {
                    return value == "" || value == null ? "" : formatDate(new Date(value))
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
                vm.priceTotal += parseFloat(arr[i].count);
                vm.num = Math.round(vm.priceTotal * 100)/100;
            }*/

            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });

    //设置日期时间控件
    /*function Datetime() {
        $('#datetimepicker1').datetimepicker({
            format: 'YYYY-MM-DD',
            locale: moment.locale('zh-cn')
        });
    }*/

});

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        advTotal: {},
        priceTotal:0,
        num:0,
        q:{
            name:null
        }

    },
    methods: {
        query: function () {
            vm.reload();
        },

        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.advTotal = {};
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
            var url = vm.advTotal.id == null ? "sys/advtotal/save" : "sys/advtotal/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.advTotal),
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
                    url: baseURL + "sys/advtotal/delete",
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
            $.get(baseURL + "sys/advtotal/info/" + id, function (result) {
                vm.advTotal = result.advTotal;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page,
                postData: {'name':vm.q.name,"startTime": $("#sdate").val(),
                            "endTime": $("#edate").val()}
            }).trigger("reloadGrid");
        },
        loadMonyTotal:function() {
            $.ajax({
                type: "POST",
                url: baseURL + 'sys/advtotal/priceTotal',
                async: false,
                dataType: "json",
                success: function (r) {
                    if (r.code === 1) {
                        vm.priceTotal = parseFloat(r.priceTotal);
                    } else {
                        alert(r.msg);
                    }
                }
            });
        }
    }
});