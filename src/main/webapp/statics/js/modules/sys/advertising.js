$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/advertising/list',
        datatype: "json",
        colModel: [
            { label: '序号', name: 'id', index: 'id', width: 40, key: true },
            {label: '广告主', name: 'aName', index: 'aName', width: 50},
            {label: '广告类型', name: 'type', index: 'type', width: 70,
                formatter: function (value) {
                    if(value != null){
                        if (value == 1) {
                            return 'CPC';
                        } else if (value == 2) {
                            return 'CPM';
                        }
                    }else {
                        return '无操作';
                    }
                }
            },
            {label: '投放城市', name: 'city', index: 'city', width: 50},
            /*{label: '投放性别', name: 'sex', index: 'sex', width: 60,
                formatter: function (value) {
                    if(value != null){
                        if (value == 1) {
                            return '全部';
                        } else if (value == 2) {
                            return '男';
                        }else if(value == 3){
                            return '女';
                        }
                    }else {
                        return '无操作';
                    }
                }
            },
            {label: '投放年龄', name: 'age', index: 'age', width: 60},
            {
                label: '消费水平', name: 'moneyLevel', index: 'moneyLevel', width: 60,
                formatter: function (value) {
                    if(value != null){
                        if (value == 1) {
                            return '全部';
                        } else if (value == 2) {
                            return '高';
                        }else if(value == 3){
                            return '中';
                        }else if(value == 4){
                            return '低';
                        }
                    }else {
                        return '无操作';
                    }
                }
            },*/
            {
                label: '广告价格(元)', name: 'price', index: 'price', width: 60,
                formatter: function (value) {
                    return value / 100;
                }
            },
            { label: '广告名称', name: 'name', index: 'name', width: 70 },
            {label: '广告链接', name: 'link', index: 'link', width: 100},
            {
                label: '投放开始时间', name: 'startTime', index: 'startTime', width: 95,
                formatter: function (value) {
                    return value == "" || value == null ? "" : formatDate(new Date(value))
                }
            },
            {
                label: '投放结束时间', name: 'endTime', index: 'endTime', width: 95,
                formatter: function (value) {
                    return value == "" || value == null ? "" : formatDate(new Date(value))
                }
            },
            /*{
                label: '广告图', name: 'imgUrl', index: 'imgUrl', width: 100,
                formatter:function (value) {
                    return "<img src='"+ value+"' weight='80pk' height='80pk' />"
                }
            },*/
            {
                label: '状态', name: 'status', index: 'status', width: 60,
                formatter: function (value) {
                    if(value != null){
                        if (value == 1) {
                            return '审核中';
                        } else if (value == 2) {
                            return '待投放';
                        }else if(value == 3){
                            return '投放中';
                        }else if(value == 4){
                            return '已结束';
                        }else if(value == 5) {
                            return '审核失败';
                        }
                    }else {
                        return '无操作';
                    }
                }
            },
            {
                label: '创建时间', name: 'createTime', index: 'createTime', width: 95,
                formatter: function (value) {
                    return value == "" || value == null ? "" : formatDate(new Date(value))
                }
            },
            {
                label: '审核时间', name: 'auditFailTime', index: 'auditFailTime', width: 95,
                formatter: function (value) {
                    return value == "" || value == null ? "" : formatDate(new Date(value))
                }
            },
            {label: '审核失败原因', name: 'auditFailRemark', index: 'auditFailRemark', width: 100}
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
                        vm.advertising.imgUrl = url;
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
        showList2: true,
        showObject:true,
        title: null,
        advertising: {},
        imgUrl:"",
        status:null,
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
            vm.advertising = {};
            $(".logo img").attr("src", "/statics/img/addPhoto.svg");
            $(".logo img").attr("layer-src", "/statics/img/addPhoto.svg");
        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.getInfo(id);
            if(vm.status != 1){
                alert("请选择审核中的记录")
                return true;
            }
            vm.showList2 = false;
            vm.showList = false;
            vm.title = "审核";
            vm.advertising.imgUrl = vm.imgUrl;

        },
        object: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.getInfo(id);
            vm.showObject = false;
            vm.showList = false;
            vm.title = "广告详情";
            vm.advertising.imgUrl = vm.imgUrl;

        },
        saveOrUpdate: function (event) {
            vm.advertising.imgUrl = vm.imgUrl;
            var url = vm.advertising.id == null ? "sys/advertising/save" : "sys/advertising/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.advertising),
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
        saveOrUpdates: function (event) {
            vm.advertising.imgUrl = vm.imgUrl;
            var url = vm.advertising.id == null ? "sys/advertising/save" : "sys/advertising/updates";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.advertising),
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
                    url: baseURL + "sys/advertising/delete",
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
            $.ajaxSettings.async = false;
            $.get(baseURL + "sys/advertising/info/" + id, function (result) {
                vm.advertising = result.advertising;
                vm.advertising.price = vm.advertising.price/100;
                vm.status = vm.advertising.status;
                var url = vm.advertising.imgUrl;
                vm.imgUrl = url;
                $(".logo img").attr("src", url);
                $(".logo img").attr("layer-src", url);
            });
        },
        reload: function (event) {
            vm.showList = true;
            vm.showObject = true;
            vm.showList2 = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page,
                postData: {'status': vm.q.status}
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
        previews: function () {
            $(document).on("click", ".layui-layer-shade", function () {
                $(".layui-layer-shade").remove();
                $(".layui-layer").remove();
            });
            layer.photos({
                photos: "#image" //格式见API文档手册页
                , anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机
            });
        }
    }
});