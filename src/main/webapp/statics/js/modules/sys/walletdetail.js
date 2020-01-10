$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/walletdetail/list',
        datatype: "json",
        colModel: [			
			{ label: '序号', name: 'id', index: 'id', width: 20, key: true },
			{ label: '用户账号', name: 'phone', index: 'phone', width: 50},
            { label: '用户类型', name: 'userType', index: 'userType', width: 35,
                formatter: function (value) {
                    if (value == 0) {
                        return '广告主';
                    } else if (value == 1) {
                        return '供货商';
                    } else if (value ==2){
                        return '代理商';
                    }
                }
            },
            { label: '姓名', name: 'username', index: 'username', width: 35 },
            { label: '电话', name: 'phone', index: 'phone', width: 45 },
			/*{ label: '明细类型（1 提现 2销售收入 3充值 4广告消费）', name: 'type', index: 'type', width: 80 }, 		*/
			{ label: '金额数', name: 'money', index: 'money', width: 30 ,
                formatter: function (value) {
                    return value / 100;
                }
            },
		/*	{ label: '提现到的微信号', name: 'wxCount', index: 'wxCount', width: 80 }, 			*/
			{ label: '流水号', name: 'transactionNumber', index: 'transactionNumber', width: 80 },
            { label: '提现订单号', name: 'orderNo', index: 'orderNo', width: 60 },
			{ label: '状态', name: 'status', index: 'status', width: 30,
                formatter: function (value) {
                    if (value == 1) {
                        return '审核中';
                    } else if (value == 2) {
                        return '审核失败';
                    } else if (value ==3){
                        return '已完成';
                    }else if (value ==4){
                        return '提现中';
                    }else if (value ==5){
                        return '提现失败';
                    }

                }
            },
            { label: '失败原因', name: 'reason', index: 'reason', width: 120 },
			{ label: '申请时间', name: 'createTime', index: 'createTime', width: 50,
                formatter: function (value) {
                    return value == "" || value == null ? "" : formatDate(new Date(value))
                }
            }
        ],
		viewrecords: true,
        height: 385,
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
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		walletDetail: {},
        status:null
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			/*vm.showList = false;
			vm.title = "新增";
			vm.walletDetail = {};*/
			//window.href="./modules/sys/paramset.html";
            window.open(baseURL + "index.html#modules/sys/paramset.html");
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			console.log(id);
            vm.getInfo(id)

            if(vm.status!=1){
               alert("请选择审核中的记录")
                return;
            }
            vm.showList = false;
            vm.title = "修改";


		},
        pay:function(){
            var id = getSelectedRow();
            if(id == null){
                return ;
            }

            confirm('确定要通过选中的提现记录吗？,通过后资金将打入代理商微信零钱', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/walletdetail/payToUser",
                    contentType: "application/json",
                    data: JSON.stringify(vm.walletDetail),
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



            vm.getInfo(id)
        },

		saveOrUpdate: function (event) {
			var url = vm.walletDetail.id == null ? "sys/walletdetail/save" : "sys/walletdetail/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.walletDetail),
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
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "sys/walletdetail/delete",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
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
		getInfo: function(id){
            $.ajaxSettings.async = false;
			$.get(baseURL + "sys/walletdetail/info/"+id, function(result){
                vm.walletDetail = result.walletDetail;
                vm.status = result.walletDetail.status;


                console.log(result.walletDetail);
                console.log(vm.walletDetail);
               // vm.walletDetail.status = 2;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});