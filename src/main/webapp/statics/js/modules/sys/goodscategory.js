$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/goodscategory/list',
        datatype: "json",
        colModel: [
			{ label: '序号', name: 'id', index: 'id', width: 50, key: true },
			{ label: '分类名称', name: 'name', index: 'name', width: 80 },
			{ label: '排序', name: 'sort', index: 'sort', width: 80},
			{ label: '创建时间', name: 'createTime', index: 'createTime', width: 80,
                formatter: function (value) {
                    return value == "" || value == null ? "" : formatDate(new Date(value))
                }
            },
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
		goodsCategory: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.goodsCategory = {};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
            if(vm.validator()){
                return ;
            }

			var url = vm.goodsCategory.id == null ? "sys/goodscategory/save" : "sys/goodscategory/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.goodsCategory),
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
				    url: baseURL + "sys/goodscategory/delete",
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
			$.get(baseURL + "sys/goodscategory/info/"+id, function(result){
                vm.goodsCategory = result.goodsCategory;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                page:page
            }).trigger("reloadGrid");
		},
        validator: function () {
            if (isBlank(vm.goodsCategory.name)) {
                alert("请输入分类名称");
                return true;
            }
            if (vm.goodsCategory.name.length>20) {
                alert("分类名称过长");
                return true;
            }
            if (isBlank(vm.goodsCategory.sort)) {
                alert("请输入排序");
                return true;
            }
            if (vm.goodsCategory.sort.length>11) {
                alert("排序值过长");
                return true;
            }
            var retype = /^[1-9]\d*$/;
            if (!retype.test(vm.goodsCategory.sort)) {
                alert("排序只能输入正整数");
                return true;
            }
        }


	}
});