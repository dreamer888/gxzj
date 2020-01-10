$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/conduct/list',
        datatype: "json",
        colModel: [
            { label: '序号', name: 'id', index: 'id', width: 30, key: true },
            {
                label: '姓名', name: 'name', index: 'name', width: 40
            },
            {
                label: '用户头像', name: 'headImgUrl', index: 'headImgUrl', width: 80,
                formatter:function (value) {
                    return "<img src='"+ value+"' weight='80pk' height='80pk' />"
                }
            },
            {
                label: '电话', name: 'phone', index: 'phone', width: 50,
            },
            {
                label: '余额(元)', name: 'wallet', index: 'wallet', width: 30,
                formatter: function (value) {
                    return value / 100;
                }
            },
            {
                label: '用户状态', name: 'status', index: 'status', width: 40,
                formatter: function (value) {
                    return value === 2 ?
                        '<span class="label label-danger">禁用</span>' :
                        '<span class="label label-success">启用</span>';
                }
            },
            {
                label: '删除状态', name: 'deleteStatus', index: 'deleteStatus', width: 40,
                formatter: function (value) {
                    return value === 1 ?
                        '<span class="label label-danger">已删除</span>' :
                        '<span class="label label-success">可用</span>';
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


    $.each($(".upload"), function () {
        var _this = $(this);
        new AjaxUpload($(this), {
            action: baseURL + "sys/upload",
            name: 'files',
            autoSubmit: true,
            responseType: "json",
            onSubmit: function (file, extension) {
                if (!(extension && /^(jpg|jpeg|png|gif)$/.test(extension.toLowerCase()))) {
                    alert('只支持jpg、png、gif格式的图片！');
                    return false;
                }
            },
            onComplete: function (file, r) {
                if (r.code == 1) {
                    var urls = r.data.imgPath;
                    urls.forEach(function (url) {
                        _this.prev().attr("src", url);
                        _this.prev().attr("layer-src", url);

                        vm.headImgUrl = url;
                        vm.conduct.headImgUrl = url;
                    })
                } else {
                    alert(r.msg);
                }
            }
        });
    });

});

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        conduct: {},
        headImgUrl: "",
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
            vm.conduct = {};
            $(".logo img").attr("src", "/statics/img/addPhoto.svg");
            $(".logo img").attr("layer-src", "/statics/img/addPhoto.svg");
        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";
            vm.conduct.headImgUrl = vm.headImgUrl;
            vm.getInfo(id)
        },
        saveOrUpdate: function (event) {
            if(vm.validator()){
                return;
            }
            vm.conduct.headImgUrl = vm.headImgUrl;
            var url = vm.conduct.id == null ? "sys/conduct/save" : "sys/conduct/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.conduct),
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
                        url: baseURL + "sys/conduct/delete",
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
            $.get(baseURL + "sys/conduct/info/" + id, function (result) {
                vm.conduct = result.conduct;
                var url = vm.conduct.headImgUrl;
                vm.headImgUrl = url;
                $(".logo img").attr("src", url);
                $(".logo img").attr("layer-src", url);
                vm.conduct.password = null;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page,
                postData: {'name': vm.q.name}
            }).trigger("reloadGrid");
        },
        /*预览大图*/
        preview: function () {
            $(document).on("click", ".layui-layer-shade", function () {
                $(".layui-layer-shade").remove();
                $(".layui-layer").remove();
            });
            layer.photos({
                photos: "#images" //格式见API文档手册页
                , anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机
            });
        },
        validator: function () {
            var phoneReg = /(^1[3|4|5|7|8|9]\d{9}$)|(^09\d{8}$)/;
            var passwordReg = /^[0-9a-zA-Z]+$/;
            if (isBlank(vm.conduct.name)) {
                alert("姓名不能为空");
                return true;
            }
            if (vm.conduct.name.length>30) {
                alert("姓名过长");
                return true;
            }
            if (isBlank(vm.conduct.phone)) {
                alert("电话不能为空");
                return true;
            }
            if (vm.conduct.phone.length>15) {
                alert("电话号码过长");
                return true;
            }
            if (!phoneReg.test(vm.conduct.phone)) {
                alert('请输入有效的手机号码！');
                return true;
            }
            if(!passwordReg.test(vm.conduct.password)){
                alert("密码只能是数字或者字母");
                return true;
            }
            if (vm.conduct.status == null) {
                alert("用户状态不能为空");
                return true;
            }

        }
    }
});