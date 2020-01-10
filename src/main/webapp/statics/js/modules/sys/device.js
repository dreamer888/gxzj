$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/device/list',
        datatype: "json",
        colModel: [
            {label: '设备Id', name: 'deviceId', index: 'id', width: 30, key: true},
			{ label: '设备编号', name: 'deviceNo', index: 'deviceNo', width: 80 },
			{ label: '设备IMEI', name: 'imei', index: 'imei', width: 80 },
            { label: '绑定代理商', name: 'name', index: 'name', width: 70 },
			{ label: '设备地址详情', name: 'addressDetail', index: 'addressDetail', width: 130 },
		//	{ label: '设备地址Id', name: 'addressId', index: 'addressId', width: 80 },
/*			//{ label: '代理人Id', name: 'proxyId', index: 'proxyId', width: 80 },*/
			{ label: '设备状态', name: 'status', index: 'status', width: 40,
                formatter: function (value) {
                    if (value == 1) {
                        return '<span class="label label-success">在线</span>';
                    } else if (value == 2) {
                        return '<span class="label label-danger">离线</span>';
                    }else if(value == 3){
                        return '<span class="label label-warning">故障</span>';
                    }
                }
            },
			{ label: '商品库存', name: 'inventory', index: 'inventory', width: 40 },
			{ label: '商品图片', name: 'goodsImgUrl', index: 'goodsImgUrl', width: 80 ,
                formatter:function (value) {
                    return "<img src='"+ value+"' weight='80pk' height='80pk' />"
                }
            },
			{ label: '商品名称', name: 'goodsName', index: 'goodsName', width: 60 },
			{ label: '商品单价(元)', name: 'goodsPrice', index: 'goodsPrice', width: 50,
                formatter: function (value) {
                    return value / 100;
                }
            },
			//{ label: '单价', name: 'price', index: 'price', width: 80 },
			{ label: '创建时间', name: 'createTime', index: 'createTime', width: 80,
                formatter: function (value) {
                    return value == "" || value == null ? "" : formatDate(new Date(value))
                }
            }
        ],
		viewrecords: true,
        height: 600,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
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

                        vm.goodsImgUrl = url;
                        vm.device.goodsImgUrl = url;
                    })
                } else {
                    alert(r.msg);
                }
            }
        });
    });

});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
        showList2: true,
        showaddAgent: true,
        showOther: true,
		title: null,
		device: {address:"",agentId:""},
        agentType3:{},
        agentType2:{},
        address:{},
        goodsImgUrl:""
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
		    vm.showList = false;
            vm.showList2 = false;
			vm.title = "新增";
            vm.device = {};
            $(".logo img").attr("src", "/statics/img/addPhoto.svg");
            $(".logo img").attr("layer-src", "/statics/img/addPhoto.svg");

		},
        addRelation: function(){
            var deviceId = getSelectedRow();
            if(deviceId == null){
                return ;
            }
		    vm.showaddAgent = false;
            vm.showList = false;
            vm.title = "绑定代理商";
            vm.getAgentType2();
            //vm.getAgentType3();
            vm.getInfo(deviceId);
        },
        modifyRelation: function(){
            var deviceId = getSelectedRow();
            if(deviceId == null){
                return ;
            }
            vm.showOther = false;
            vm.showList = false;
            vm.title = "其他操作";
            //vm.getAgentType3();
            vm.getAgentType2();
            vm.getAddr();
            vm.getInfo(deviceId);
        },
		update: function (event) {
			var deviceId = getSelectedRow();
			if(deviceId == null){
				return ;
			}
			vm.showList = false;
            vm.showList2 = false;
            vm.title = "修改";
            vm.device.goodsImgUrl = vm.goodsImgUrl;
            vm.getInfo(deviceId);
            vm.getAgentType2();
		},
		saveOrUpdate: function (event) {
            if(vm.validator()){
                return;
            }
            if(vm.device.goodsPrice != 0){
                vm.device.goodsPrice = (vm.device.goodsPrice)*100;
            }
            vm.device.goodsImgUrl = vm.goodsImgUrl;
			var url = vm.device.deviceId == null ? "sys/device/save" : "sys/device/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.device),
			    success: function(result){
			    	if(result.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(result.msg);
					}
				}
			});
		},
		del: function (event) {
			var deviceIds = getSelectedRows();
			if(deviceIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "sys/device/delete",
                    contentType: "application/json",
				    data: JSON.stringify(deviceIds),
				    success: function(result){
						if(result.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(result.msg);
						}
					}
				});
			});
		},
        saveRelationInfo: function (event) {
            vm.device.deviceImei = vm.device.imei;
            var url = "sys/device/saveRelationInfo" ;
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.device),
                success: function(result){
                    if(result.code === 0){
                        alert('操作成功', function(index){
                            vm.reload();
                        });
                    }else{
                        alert(result.msg);
                    }
                }
            });
        },
        modifyRelationInfo: function (event) {
            console.log(vm.device)
            var url = "sys/device/modifyRelationInfo" ;
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.device),
                success: function(result){
                    if(result.code === 0){
                        alert('操作成功', function(index){
                            vm.reload();
                        });
                    }else{
                        alert(result.msg);
                    }
                }
            });
        },
        generateQrCode: function (event) {
            var ids = getSelectedRows();
            debugger;
            ids.forEach(function (id) {
                console.log(id);
                window.open(baseURL + "sys/device/generateQrCode?id=" + id);
            })

            /* $.each(ids, function (id) {
                 console.log(id);
                 window.open(baseURL + "sys/device/generateQrCode?id=" + id);
             });*/
        },

		getInfo: function(deviceId){
			$.get(baseURL + "sys/device/info/"+deviceId, function(result){
                vm.device = result.device;
                vm.device.goodsPrice = result.device.goodsPrice/100;
                var url = vm.device.goodsImgUrl;
                vm.goodsImgUrl = url;
                console.log(result);
                vm.device.addr = result.device.addressDetail;
                $(".logo img").attr("src", url);
                $(".logo img").attr("layer-src", url);
            });
		},
		reload: function (event) {
			vm.showList = true;
            vm.showList2 = true;
            vm.showaddAgent = true;
            vm.showOther = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		},
        exportExcel: function () {
            window.open(baseURL + "sys/device/exportExcel");
		},
        openUpload: function(){
            layer.open({
                type: 1,
                offset: '100px',
                skin: 'layui-layer-molv',
                title: "选择导入的设备文件",
                area: ['500px', '250px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#uploadDialog"),
            });
        },
        uploadFile: function(){
            $('#form1').ajaxSubmit({
                url:baseURL + "sys/device/importExcel",
                dataType: 'text',
                success: resutlMsg,
                error: errorMsg
            });
            function resutlMsg(result){
                result = eval('(' + result + ')')
                alert(result.msg);
                if(result.code == 0){
                    $("#textfield").val("");
                    layer.closeAll();
                    var page = 1;
                    vm.reload(page);
                }
            }
            function errorMsg(){
                alert("导入excel出错！");
            }
        },
        cancelBtn: function(){
            layer.closeAll();
        },
        downLoadTemplate: function(){
            window.open(baseURL + "statics/template/devicelist.xlsx");
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
        getAgentType2:function(){
            $.get(baseURL + "sys/agent/select?type=2", function(r){
                vm.agentType2 = r.agent;
            });
        },
/*        getAgentType3:function(){
            $.get(baseURL + "sys/agent/select?type=3", function(r){
                vm.agentType3 = r.agent;

            });

        },*/
        getAddr:function(){
            $.get(baseURL + "sys/address/select", function(r){
                vm.address = r.address;

            });

        },
        getRelation:function(deviceId){
            $.get(baseURL + "sys/device/info/"+deviceId, function(result){
                vm.device = result.device;
                vm.device.goodsPrice = result.device.goodsPrice/100;
            });
        },
        validator: function () {
            var reg = new RegExp("[\\u4E00-\\u9FFF]+", "g");
            var re = /^[1-9]\d*$/;
            if (isBlank(vm.device.deviceNo)) {
                alert("设备编号不能为空");
                return true;
            }
            if (reg.test(vm.device.deviceNo)) {
                alert("序设备编号不能包含汉字");
                return true;
            }
            if (isBlank(vm.device.imei)) {
                alert("设备IMEI不能为空");
                return true;
            }
            if (reg.test(vm.device.imei)) {
                alert("序设备IMEI不能包含汉字");
                return true;
            }
            if (isBlank(vm.device.addressDetail)) {
                alert("设备地址不能为空");
                return true;
            }
            if(vm.device.status == null){
                alert("状态不能为空");
                return true;
            }
            if (isBlank(vm.device.goodsName)) {
                alert("商品名称不能为空");
                return true;
            }
            if (isBlank(vm.device.goodsPrice)) {
                alert("商品单价不能为空");
                return true;
            }
            if (isBlank(vm.device.inventory)) {
                alert("库存不能为空");
                return true;
            }
            if (!re.test(vm.device.inventory)) {
                alert("库存只能输入正整数");
                return true;
            }
        }
    }
});