var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url:"nourl"
        }
    }
};

var ztree;

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        proxylist: {
            parentName:null,
            parentId:0
        },
        Comm:"",
        headImgUrl: "",
        q:{
            name:null
        }
    },
    methods: {
        /*query: function () {
            vm.reload();
        },*/
        commValue:function () {
            var comm = $("#commType").val();
            vm.Comm = comm;
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.proxylist = {};
            $(".logo img").attr("src", "/statics/img/addPhoto.svg");
            $(".logo img").attr("layer-src", "/statics/img/addPhoto.svg");
            vm.commValue();
        },
        update: function (event) {
            var id = getMenuId();
            if(id == null){
                return ;
            }

            $.get(baseURL + "sys/proxylist/info/" + id, function (result) {
                vm.proxylist = result.proxylist;
                var url = vm.proxylist.headImgUrl;
                if(vm.proxylist.commissionType == 2) {
                    vm.proxylist.commissionValue = result.proxylist.commissionValue / 100;
                }
                vm.headImgUrl = url;
                $(".logo img").attr("src", url);
                $(".logo img").attr("layer-src", url);
                vm.proxylist.password = null;

                vm.showList = false;
                vm.title = "修改";
                vm.proxylist.headImgUrl = vm.headImgUrl;
            });
            vm.commValue();
        },
        saveOrUpdate: function (event) {
            vm.commValue();
            if(vm.validator()){
                return;
            }
            if(vm.proxylist.commissionType == 2){
                if(vm.proxylist.commissionValue != 0){
                    vm.proxylist.commissionValue = (vm.proxylist.commissionValue)*100;
                }
            }

            vm.proxylist.headImgUrl = vm.headImgUrl;
            var url = vm.proxylist.id == null ? "sys/proxylist/save" : "sys/proxylist/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.proxylist),
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
            var id = getMenuId();
            if(id == null){
                return ;
            }
            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                        type: "POST",
                        url: baseURL + "sys/proxylist/delete",
                        contentType: "application/json",
                        data: JSON.stringify(id),
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
        reload: function (event) {
            vm.showList = true;
            Menu.table.refresh();
        },
        validator: function () {
            /*var phoneReg = /(^1[3|4|5|7|8]\d{9}$)|(^09\d{8}$)/;
            var passwordReg = /^[0-9a-zA-Z]+$/;*/
            var re = /^[1-9]\d*$/;
            var comm = /^(\d{1,2}(\.\d+)?|100|NA)$/;
            /*if (isBlank(vm.proxylist.name)) {
                alert("姓名不能为空");
                return true;
            }
            if (vm.proxylist.name.length>30) {
                alert("姓名过长");
                return true;
            }
            if (isBlank(vm.proxylist.phone)) {
                alert("电话不能为空");
                return true;
            }
            if (vm.proxylist.phone.length>15) {
                alert("电话号码过长");
                return true;
            }
            if (!phoneReg.test(vm.proxylist.phone)) {
                alert('请输入有效的手机号码！');
                return true;
            }
            if(!passwordReg.test(vm.proxylist.password)){
                alert("密码只能是数字或者字母");
                return true;
            }
            if (vm.proxylist.role == null) {
                alert("用户角色不能为空");
                return true;
            }
            if (isBlank(vm.proxylist.city)) {
                alert("代理城市不能为空");
                return true;
            }*/
            /*if (vm.proxylist.commissionType == null) {
                alert("分佣类型不能为空");
                return true;
            }*/
            if (isBlank(vm.proxylist.commissionValue)) {
                alert("分佣值不能为空");
                return true;
            }
            /*if (!re.test(vm.proxylist.commissionValue)) {
                alert("分佣值只能输入数字");
                return true;
            }*/
            if(vm.Comm == 1) {
                if (!re.test(vm.proxylist.commissionValue)) {
                    alert("按比例指定分佣值只能输入正整数");
                    return true;
                }
            }
            /*if(vm.Comm == 2) {
                if (!re.test(vm.proxylist.commissionValue)) {
                    alert("按金额指定分佣值只能输入正整数");
                    return true;
                }
            }*/

            if(vm.Comm == 1){
                if (!comm.test(vm.proxylist.commissionValue)) {
                    alert("按比例指定分佣值只能0-100之间");
                    return true;
                }
            }
/*            if (vm.proxylist.city.length>20) {
                alert("代理城市名称过长");
                return true;
            }*/
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
        }
    }
});

var Menu = {
    id: "menuTable",
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Menu.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: '序号', field: 'id', visible: false, align: 'center', valign: 'middle', width: '80px'},
        {title: '用户角色', field: 'role', align: 'center', valign: 'middle', sortable: true, width: '180px',
            formatter: function (item,index) {
                if (item.role === 0) {
                    return '普通用户';
                } else if (item.role === 1) {
                    return '省代理';
                } else if (item.role === 2) {
                    return '市代理';
                } else if (item.role === 3) {
                    return '区代理';
                } else if (item.role === 4) {
                    return '个人代理';
                }
            }
        },
        {title: '姓名', field: 'name', align: 'center', valign: 'middle', sortable: true, width: '100px'},
        /*{title: '用户头像', field: 'icon', align: 'center', valign: 'middle', sortable: true, width: '80px', formatter: function(item, index){
            return "<img src='"+ item.headImgUrl+"' weight='80pk' height='80pk' />"
        }},*/
        {title: '代理地区', field: 'city', align: 'center', valign: 'middle', sortable: true, width: '100px'},
        {title: '电话', field: 'phone', align: 'center', valign: 'middle', sortable: true, width: '100px'},
        {title: '余额(元)', field: 'wallet', align: 'center', valign: 'middle', sortable: true, width: '100px',
            formatter: function (item,index) {
                return item.wallet / 100;
            }
        },
        {title: '状态', field: 'status', align: 'center', valign: 'middle', sortable: true, width: '100px',
            formatter: function (item,index) {
                return item.status === 2 ?
                    '<span class="label label-danger">禁用</span>' :
                    '<span class="label label-success">启用</span>';
            }
        },
        {title: '分佣类型', field: 'commissionType', align: 'center', valign: 'middle', sortable: true, width: '100px',
            formatter: function (item,index) {
                if (item.commissionType === 1) {
                    return '按比例';
                } else if (item.commissionType == 2) {
                    return '按金额';
                }
            }
        },
        {title: '分佣值', field: 'commissionValue', align: 'center', valign: 'middle', sortable: true, width: '100px',
            formatter:function(item, index){
                var str = "";
                if(item["commissionType"] == 1){
                    str = "<span style='color: black;'>%</span>";
                }else{
                    str = "<span style='color: black;'>元</span>";
                }
                if (item.commissionType == 2){
                    return  parseFloat(item.commissionValue).toFixed(2)/100+"&nbsp;"+str;
                }else{
                    return item.commissionValue+"&nbsp;"+str;
                }
            }

            /*formatter:function(item,index) {
                var str = "";
                if (item["commissionType"] == 1) {
                    str = "<span style='color: black;'>%</span>";
                    return (item.commissionValue)+"&nbsp;"+str;
                } else if(item["commissionType"] == 2){
                    str = "<span style='color: black;'>元</span>";
                    return (item.commissionValue/100)+"&nbsp;"+str;
                }
            }*/
            /*formatter: function (item,index) {
                return item.commissionValue / 100;
            }*/
        },
        /*{title: '删除状态', field: 'deleteStatus', align: 'center', valign: 'middle', sortable: true, width: '100px',
            formatter: function (item,index) {
                return item.status === 0 ?
                    '<span class="label label-danger">已删除</span>' :
                    '<span class="label label-success">可用</span>';
            }
        },*/
        {title: '创建时间', field: 'createTime', align: 'center', valign: 'middle', sortable: true, width: '100px',
            formatter: function (item,index) {
                return item.createTime === "" || item.createTime === null ? "" : formatDate(new Date(item.createTime))
            }
        }]

    return columns;
};


function getMenuId () {
    var selected = $('#menuTable').bootstrapTreeTable('getSelections');
    if (selected.length == 0) {
        alert("请选择一条记录");
        return false;
    } else {
        return selected[0].id;
    }
}

$(function () {
        var colunms = Menu.initColumn();
        var table = new TreeTable(Menu.id, baseURL + "sys/proxylist/list", colunms);
        table.setExpandColumn(2);
        table.setIdField("id");
        table.setCodeField("id");
        table.setParentCodeField("parentId");
        table.setHeight("650");
        table.setExpandAll(true);
        table.init();

        Menu.table = table;

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
                        vm.proxylist.headImgUrl = url;
                    })
                } else {
                    alert(r.msg);
                }
            }
        });
    });

});