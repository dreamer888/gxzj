$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/agencycomm/list',
        datatype: "json",
        colModel: [
            { label: '序号', name: 'id', index: 'id', width: 30, key: true },
            /*{
                label: '用户类型', name: 'type', index: 'type', width: 40,
                formatter: function (value) {
                    if(value!=null){
                        if (value == 0) {
                            return '广告主';
                        } else if (value == 1) {
                            return '供货主';
                        } else if (value == 2) {
                            return '管理员';
                        }else if (value == 3) {
                            return '运维人员';
                        } else if (value == 4) {
                            return '财务人员';
                        }
                    }else{
                        return '无操作';
                    }

                }
            },*/
            {
                label: '姓名', name: 'name', index: 'name', width: 40
            },
            {
                label: '电话', name: 'phone', index: 'phone', width: 50
            },
            {
                label: '用户角色', name: 'role', index: 'role', width: 40,
                formatter: function (value) {
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
                }
            },
            {
                label: '代理城市', name: 'city', index: 'city', width: 40
            },
            {
                label: '分佣值', name: 'commissionValue', index: 'commissionValue', width: 40,
                formatter: function (value) {
                    return value / 100;
                }
            },
            {
                label: '佣金类型', name: 'commissionType', index: 'commissionType', width: 40,
                formatter: function (value) {
                    if(value!=null){
                        if (value == 1) {
                            return '按比例';
                        } else if (value == 2) {
                            return '按金额';
                        }else{
                            return '无';
                        }
                    }else{
                        return '无操作';
                    }
                }
            },
            {
                label: '创建时间', name: 'createTime', index: 'createTime', width: 60,
                formatter: function (value) {
                    return value == "" || value == null ? "" : formatDate(new Date(value))
                }
            },
        ],
        viewrecords: true,
        height: 600,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "pages.list",
            pages: "pages.currPage",
            total: "pages.totalPage",
            records: "pages.totalCount"
        },
        prmNames: {
            pages: "pages",
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
        agencycomm: {},
        Comm:"",
        q: {
            role:null
        }
    },

    methods: {
        query: function () {
            vm.reload();
        },
        commValue:function () {
            var comm = $("#commType").val();
            vm.Comm = comm;
        },
        add: function () {

            vm.showList = false;
            vm.title = "新增";
            vm.agencycomm = {};
        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";
            vm.commValue();
            vm.getInfo(id)
        },
        saveOrUpdate: function (event) {
            if(vm.validator()){
                return;
            }

            var url = vm.agencycomm.id == null ? "sys/agencycomm/save" : "sys/agencycomm/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.agencycomm),
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
            var ids = getSelectedRow();
            if (ids == null) {
                return;
            }
            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                        type: "POST",
                        url: baseURL + "sys/agencycomm/delete",
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
            $.get(baseURL + "sys/agencycomm/info/" + id, function (result) {
                vm.agencycomm = result.agencycomm;
                /*vm.agencycomm.commissionValue = result.agencycomm.commissionValue/100;*/
                vm.commValue();
            });
        },
        reload: function (event) {
            vm.showList = true;
            var pages = $("#jqGrid").jqGrid('getGridParam', 'pages');
            $("#jqGrid").jqGrid('setGridParam', {
                pages: pages,
                postData: {'role': vm.q.role}
            }).trigger("reloadGrid");
        },
        validator: function () {
            var comm = /^(\d{1,2}(\.\d+)?|100|NA)$/;
            if (vm.agencycomm.commissionValue == null) {
                alert("分佣类型不能为空");
                return true;
            }
            if (isBlank(vm.agencycomm.commissionValue)) {
                alert("分佣值不能为空");
                return true;
            }
            var retype = /^[1-9]\d*$/;
            if (!retype.test(vm.agencycomm.commissionValue)) {
                alert("分佣值只能输入正整数");
                return true;
            }
            if(vm.Comm == 1){
                if (!comm.test(vm.agencycomm.commissionValue)) {
                    alert("按比例指定分佣值只能0-100之间");
                    return true;
                }
            }
        }
    }
});