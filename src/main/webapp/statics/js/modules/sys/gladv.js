$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/gladv/list',
        datatype: "json",
        colModel: [
            { label: '序号', name: 'id', index: 'id', width: 30, key: true },
            { label: '广告标题', name: 'name', index: 'name', width: 60},
            { label: '广告内容', name: 'content', index: 'content', width: 60},
            {
                label: '广告图', name: 'imgUrl', index: 'imgUrl', width: 80,
                formatter:function (value) {
                    return "<img src='"+ value+"' weight='80pk' height='80pk' />"
                }
            },
            { label: '广告地址', name: 'linkUrl', index: 'linkUrl', width: 40},
            { label: '广告上/下架', name: 'status', index: 'status', width: 40,
                formatter: function (value) {
                    return value === 0 ?
                        '<span class="label label-danger">下架</span>' :
                        '<span class="label label-success">上架</span>';
                }
            },
            { label: '排序', name: 'sort', index: 'sort', width: 40},
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

                        vm.imgUrl = url;
                        vm.gladv.imgUrl = url;
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
        gladv: {},
        imgUrl: "",
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
            vm.gladv = {};
            $(".logo img").attr("src", "/statics/img/addPhoto.svg");
            $(".logo img").attr("layer-src", "/statics/img/addPhoto.svg");
        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.gladv.imgUrl = vm.imgUrl;
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
        },
        saveOrUpdate: function (event) {
            if(vm.validator()){
                return;
            }
            vm.gladv.imgUrl = vm.imgUrl;
            var url = vm.gladv.id == null ? "sys/gladv/save" : "sys/gladv/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.gladv),
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
                    url: baseURL + "sys/gladv/delete",
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
            $.get(baseURL + "sys/gladv/info/" + id, function (result) {
                vm.gladv = result.gladv;
                var url = vm.gladv.imgUrl;
                vm.imgUrl = url;
                $(".logo img").attr("src", url);
                $(".logo img").attr("layer-src", url);
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
            var re = /^[1-9]\d*$/;
            if (isBlank(vm.gladv.name)) {
                alert("请输入广告标题");
                return true;
            }
            if (isBlank(vm.gladv.content)) {
                alert("请输入广告内容");
                return true;
            }
            if (vm.gladv.status == null) {
                alert("请输入选择广告上下架");
                return true;
            }
            if (isBlank(vm.gladv.linkUrl)) {
                alert("请输入广告跳转的地址");
                return true;
            }

            if (isBlank(vm.gladv.sort)) {
                alert("请输入排序");
                return true;
            }
            if (!re.test(vm.gladv.sort)) {
                alert("排序只能输入正整数");
                return true;
            }
        }

    }
});