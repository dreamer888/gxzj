$(function () {
    vm.loadMonyTotal();
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/gzhincomedetail/list',
        datatype: "json",
        colModel: [
            { label: '序号', name: 'id', index: 'id', width: 30, key: true },
            {
                label: '创建者', name: 'aName', index: 'aName', width: 60,
                formatter: function (value) {
                    if (value == null || value == ''){
                        return '个联总代';
                    }else{
                        return value;
                    }
                }
            },
            {
                label: '公众号ID', name: 'appId', index: 'appId', width: 70
            },
            {
                label: '公众号名称', name: 'oName', index: 'oName', width: 60
            },
            {
                label: '关注次数', name: 'likeNum', index: 'likeNum', width: 60
            },
            {
                label: '原价', name: 'originalPrice', index: 'originalPrice', width: 60,
                formatter: function (value) {
                    return value / 100;
                }
            },
            {
                label: '分成单价', name: 'deductPrice', index: 'deductPrice', width: 60,
                formatter: function (value) {
                    return value / 100;
                }
            },
            {
                label: '公众号收益(元)', name: 'gzhcount', index: 'gzhcount', width: 60,
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
                vm.priceTotal += parseFloat(arr[i].gzhcount);
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
        gzhincomedetail: {},
        num:0,
        q:{
            oName:null
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
            vm.gzhincomedetail = {};
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
            var url = vm.gzhincomedetail.id == null ? "sys/gzhincomedetail/save" : "sys/gzhincomedetail/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.gzhincomedetail),
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
                    url: baseURL + "sys/gzhincomedetail/delete",
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
            $.get(baseURL + "sys/gzhincomedetail/info/" + id, function (result) {
                vm.gzhincomedetail = result.gzhincomedetail;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page,
                postData:{'oName':vm.q.oName,"startTime": $("#sdate").val(),
                    "endTime": $("#edate").val()}
            }).trigger("reloadGrid");
        },
        loadMonyTotal:function() {
            $.ajax({
                type: "POST",
                url: baseURL + 'sys/gzhincomedetail/priceTotal',
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