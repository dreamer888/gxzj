$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/agency/list',
        datatype: "json",
        colModel: [
            { label: '序号', name: 'id', index: 'id', width: 30, key: true },
            {
                label: '代理账号', name: 'phone', index: 'phone', width: 50,
            },
            /*{
                label: '用户类型', name: 'type', index: 'type', width: 40,
                formatter: function (value) {
                    if(value!=null){
                        if (value == 0) {
                            return '广告主';
                        } else if (value == 1) {
                            return '供货主';
                        } else if (value == 2) {
                            return '代理商';
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
                label: '用户头像', name: 'headImgUrl', index: 'headImgUrl', width: 80,
                formatter:function (value) {
                    return "<img src='"+ value+"' weight='80pk' height='80pk' />"
                }
            },
            {
                label: '代理地区', name: 'city', index: 'city', width: 40,
            },
            {
                label: '电话', name: 'phone', index: 'phone', width: 50,
            },
            {
                label: '钱包(元)', name: 'wallet', index: 'wallet', width: 30,
                formatter: function (value) {
                    return value / 100;
                }
            },
            {
                label: '账号状态', name: 'status', index: 'status', width: 40,
                formatter: function (value) {
                    return value === 2 ?
                        '<span class="label label-danger">禁用</span>' :
                        '<span class="label label-success">启用</span>';
                }
            },
            {
                label: '分佣类型', name: 'commissionType', index: 'commissionType', width: 40,
                formatter: function (value) {
                    if(value != null){
                        if (value == 1) {
                            return '按比例';
                        } else if (value == 2) {
                            return '按金额';
                        }else{
                            return '无';
                        }
                    }else {
                        return '无操作';
                    }
                }
            },
            {
                label: '分佣值', name: 'commissionValue', index: 'commissionValue', width: 40,
                formatter:function(cellvalue, options, rowObject){
                    var str = "";
                    if(rowObject["commissionType"] == 1){
                        str = "<span style='color: black;'>%</span>";
                    }else{
                        str = "<span style='color: black;'>元</span>";
                    }
                    if (rowObject.commissionType == 2){
                        return  parseFloat(cellvalue).toFixed(2)/100+"&nbsp;"+str;
                    }else{
                        return cellvalue+"&nbsp;"+str;
                    }
                }

                /*formatter: function (value,obj,rowObject) {
                    if(rowObject.commissionType == 2){
                        return value / 100;
                    }
                    return value;
                }*/
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
                        vm.agency.headImgUrl = url;
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
        agency: {},
        headImgUrl: "",
        Comm:"",
        q:{
            name:null,
            temp:null
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
            vm.agency = {};
            $(".logo img").attr("src", "/statics/img/addPhoto.svg");
            $(".logo img").attr("layer-src", "/statics/img/addPhoto.svg");
            vm.commValue();
        },
        update: function (event) {
            vm.commValue();
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";
            vm.agency.headImgUrl = vm.headImgUrl;
            vm.getInfo(id);
            vm.commValue();
        },
        saveOrUpdate: function (event) {
            vm.commValue();
            if(vm.validator()){
                return;
            }
            if(vm.agency.commissionType == 2){
                if(vm.agency.commissionValue != 0){
                    vm.agency.commissionValue = (vm.agency.commissionValue)*100;
                }
            }
            vm.agency.headImgUrl = vm.headImgUrl;
            var url = vm.agency.id == null ? "sys/agency/save" : "sys/agency/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.agency),
                success: function (result) {
                   /*vm.agency.commissionValue = result.commissionValue*100;*/
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
                        url: baseURL + "sys/agency/delete",
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
            $.get(baseURL + "sys/agency/info/" + id, function (result) {
                vm.agency = result.agency;
                if(vm.agency.commissionType == 2){
                    vm.agency.commissionValue = result.agency.commissionValue/100;
                }
                var url = vm.agency.headImgUrl;
                vm.headImgUrl = url;
                $(".logo img").attr("src", url);
                $(".logo img").attr("layer-src", url);
                vm.agency.password = null;
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
            var re = /^[1-9]\d*$/;
            var comm = /^(\d{1,2}(\.\d+)?|100|NA)$/;
            if (isBlank(vm.agency.name)) {
                alert("姓名不能为空");
                return true;
            }
            if (vm.agency.name.length>30) {
                alert("姓名过长");
                return true;
            }
            if (isBlank(vm.agency.phone)) {
                alert("电话不能为空");
                return true;
            }
            if (vm.agency.phone.length>15) {
                alert("电话号码过长");
                return true;
            }
            if (!phoneReg.test(vm.agency.phone)) {
                alert('请输入有效的手机号码！');
                return true;
            }
            if(!passwordReg.test(vm.agency.password)){
                alert("密码只能是数字或者字母");
                return true;
            }
            if (vm.agency.status == null) {
                alert("用户状态不能为空");
                return true;
            }
            if (isBlank(vm.agency.city)) {
                alert("代理城市不能为空");
                return true;
            }
            if (vm.agency.commissionType == null) {
                alert("分佣类型不能为空");
                return true;
            }
            if (vm.agency.commissionValue == null) {
                alert("分佣值不能为空");
                return true;
            }
            if(vm.Comm == 1) {
                if (!re.test(vm.agency.commissionValue)) {
                    alert("按比例指定分佣值只能输入正整数");
                    return true;
                }
            }
            /*if(vm.Comm == 2) {
                if (!re.test(vm.agency.commissionValue)) {
                    alert("按金额指定分佣值只能输入正整数");
                    return true;
                }
            }*/
            if(vm.Comm == 1){
                if (!comm.test(vm.agency.commissionValue)) {
                    alert("按比例指定分佣值只能0-100之间");
                    return true;
                }
            }
            if (vm.agency.city.length>20) {
                alert("代理城市名称过长");
                return true;
            }
        }
    }
});