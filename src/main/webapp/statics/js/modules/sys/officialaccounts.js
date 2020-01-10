$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/officialaccounts/list',
        datatype: "json",
        colModel: [
            {label: '序号', name: 'id', index: 'id', width: 6, key: true},
            {label: '代理商', name: 'agentName', index: 'agentName', width: 26,
                formatter:function (value) {
                    if(value===""){
                        return "个联总代";
                    }else {
                        return value;
                    }
                }
            },
            {label: '公众号名称', name: 'name', index: 'name', width: 15},
            {label: 'appId', name: 'appId', index: 'appId', width: 27},
            {label: 'appSecret', name: 'appSecret', index: 'appSecret', width: 45},
           /* {label: '免费领取码', name: 'freeCode', index: 'freeCode', width: 30},
            {label: '原价(元)', name: 'originalPrice', index: 'originalPrice', width: 30,
                formatter: function (value) {
                    return value / 100;
                }
            },
            {label: '分成单价(元)', name: 'deductPrice', index: 'deductPrice', width: 35,
                formatter: function (value) {
                    return value / 100;
                }
            },
            {label: '每日关注上限', name: 'upperLimit', index: 'upperLimit', width: 40},
            {label: '公众号Log', name: 'imgUrl', index: 'imgUrl', width: 40,
                formatter:function (value) {
                    return "<img src='"+ value +"' weight='50pk' height='40pk' />"
                }
            },*/
           /* {label: '设备编码', name: 'deviceNo', index: 'deviceNo', width: 30},*/

            {label: '令牌(Token)', name: 'token', index: 'token', width: 45},
            {label: '服务器地址(URL)', name: 'serverURL', index: 'serverURL', width: 80},
            {
                label: '状态', name: 'status', index: 'status', width: 10,
                formatter: function (value) {
                    return value === 2 ?
                        '<span class="label label-danger">下架</span>' :
                        '<span class="label label-success">上架</span>';
                }
            },
            {
                label: '创建时间', name: 'createTime', index: 'createTime', width: 25,
                formatter: function (value) {
                    return value == "" || value == null ? "" : formatDate(new Date(value))
                }
            }
        ],
        viewrecords: true,
        height: 385,
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
                        // vm.appImgUrl += r.imgPath[0];
                        vm.imgUrl = url;
                        console.log(vm.imgUrl);
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
        officialAccounts: {},
        imgUrl: "",
        areaType: {},
        addressData: null
    },

    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.officialAccounts = {};
        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.officialAccounts.imgUrl = vm.imgUrl;
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
        },
        saveOrUpdate: function (event) {
            vm.officialAccounts.imgUrl = vm.imgUrl;

            if(vm.officialAccounts.deductPrice != 0){
                vm.officialAccounts.deductPrice = (vm.officialAccounts.deductPrice)*100;
            }
            if(vm.officialAccounts.originalPrice != 0){
                vm.officialAccounts.originalPrice = (vm.officialAccounts.originalPrice)*100;
            }

            var url = vm.officialAccounts.id == null ? "sys/officialaccounts/save" : "sys/officialaccounts/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.officialAccounts),
                success: function (result) {
                    if (result.code === 0) {
                        alert('操作成功', function (index) {
                            vm.reload();
                        });
                    } else {
                        //alert(result.msg);
                        //alert("哈哈");
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
                    url: baseURL + "sys/officialaccounts/delete",
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
            $.get(baseURL + "sys/officialaccounts/info/" + id, function (result) {
                vm.officialAccounts = result.officialAccounts;
                vm.officialAccounts.originalPrice = result.officialAccounts.originalPrice/100;
                vm.officialAccounts.deductPrice = result.officialAccounts.deductPrice/100;
                var url = result.officialAccounts.imgUrl;
                $(".logo img").attr("src", url);
                $(".logo img").attr("layer-src", url);

            });
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
        selectAddress: function () {
            loadAddressData();          //渲染 省市区 数据
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-rim',
                title: "选择省份",
                area: ['300px', '450px'],
                shadeClose: true,
                content: $("#addressLayer")
            });
        },
        liClick: function (e) {
            var index = $(e.target).attr("data-index");
            var selectData = vm.addressData[index];
            var name = selectData.name;
            vm.officialAccounts.address = vm.officialAccounts.address + name;
            var childs = vm.addressData[index].childs;
            if (childs && childs.length > 0) {
                var len = selectData.code.length;
                if (len == 2) vm.officialAccounts.province = name;
                else if (len == 4) vm.officialAccounts.city = name;
                else if (len == 6) {
                    vm.officialAccounts.area = name;
                    layer.closeAll();
                }
                vm.addressData = vm.addressData[index].childs;
            } else {
                layer.closeAll();
            }

        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page
            }).trigger("reloadGrid");
        }
    }
});

function loadAddressData() {
    $.getJSON(baseURL + "/statics/plugins/Cities.json", function (data) {
        vm.addressData = data;
        vm.officialAccounts.address = "";
    });
}