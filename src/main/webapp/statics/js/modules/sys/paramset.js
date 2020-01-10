$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/paramset/list',
        datatype: "json",
        colModel: [
            /*{ label: '序号', name: 'id', index: 'id', width: 30, key: true },*/
            {label: '参数名称', name: 'name', index: 'name', width: 60},
            {
                label: '参数值', name: 'paramValue', index: 'paramValue', width: 35
            },
            {label: '参数类型', name: 'type', index: 'type', width: 40,
                formatter: function (value,rowId) {
                    if(value != null){
                        if(value == 1){
                            return '每周每个用户免费领取次数';
                        }else if (value == 2) {
                            return '提现周期';
                        }
                    }else {
                        return '无操作';
                    }
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
        height:385,
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
        paramset: {}
    },
    methods: {
        query: function () {
            vm.reload();
        },

        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.paramset = {};
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
            if(vm.validator()){
                return;
            }
            var url = vm.paramset.id == null ? "sys/paramset/save" : "sys/paramset/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.paramset),
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
                    url: baseURL + "sys/paramset/delete",
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
            $.get(baseURL + "sys/paramset/info/" + id, function (result) {
                vm.paramset = result.paramset;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page
            }).trigger("reloadGrid");
        },
        validator: function () {
            var retype = /^[0-9]\d*$/;
             var re = /^[1-7]*$/;
            if (isBlank(vm.paramset.name)) {
                alert("请输入配置名称");
                return true;
            }
            if (isBlank(vm.paramset.paramValue)) {
                alert("请输入参数值");
                return true;
            }
            if (vm.paramset.paramValue.length>50) {
                alert("参数值过长");
                return true;
            }
            if(vm.paramset.type == 2) {
                if (!retype.test(vm.paramset.paramValue)) {
                    alert("提现参数值只能输入正整数");
                    return true;
                }
            }
            if(vm.paramset.type == 1){
                if (!re.test(vm.paramset.paramValue)) {
                    alert("免费领取纸巾的参数只能是1~7之间");
                    return true;
                }
                if (!retype.test(vm.paramset.paramValue)) {
                    alert("免费领取纸巾的参数值只能输入正整数");
                    return true;
                }
            }

        }

    }
});