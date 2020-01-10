var map = new BMap.Map("map");
function init(lng, lat){
	map.clearOverlays(); 
	lat == null ? lat = 39.915 : lat = lat;
	lng == null ? lng = 116.404 : lng = lng;
	// 创建地图实例  
	var point = new BMap.Point(lng, lat);
	// 创建点坐标
	map.centerAndZoom(point, 18);
	map.panBy(250, 225);
	var marker = new BMap.Marker(point); // 创建点
	map.addOverlay(marker);

	map.enableScrollWheelZoom();
	map.enableKeyboard();
	map.enableDragging();
	map.enableDoubleClickZoom();
	map.addControl(new BMap.NavigationControl({anchor:BMAP_ANCHOR_TOP_LEFT,type:BMAP_NAVIGATION_CONTROL_LARGE}));

	dragMap(marker);
}

// 地图拖拽事件
function dragMap(marker){
    var geocoder = new BMap.Geocoder();
    marker.enableDragging();
    marker.addEventListener("dragend",function(e){
        geocoder.getLocation(e.point, function(rs){
            showLocationInfo(e.point, rs, marker); // 显示地址信息窗口
        });
    });
}

// 显示地址信息窗口
function showLocationInfo(pt, rs, marker){
    var opts = {
        width : 250,     //信息窗口宽度
        height: 50,     //信息窗口高度
        title : "当前位置"  //信息窗口标题
    }

    var addComp = rs.addressComponents;

    resetLatLngAndLocation(pt, addComp); // 重置经纬度和地址信息

    var addr = addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber + "<br>";

    var infoWindow = new BMap.InfoWindow(addr, opts);  //创建信息窗口对象
    marker.openInfoWindow(infoWindow);
}

// 重置经纬度和地址信息
function resetLatLngAndLocation(point, addComp){
    vm.longitude = point.lng; // 经度
    vm.latitude = point.lat; // 纬度
    vm.address = addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber; // 地址
}

function searchKeyword(){
	var keyword = $("#keyword").val().trim();
	if(!keyword){
		return;
	}
	var options = {
			onSearchComplete: function(results){
				// 判断状态是否正确
				if (local.getStatus() == BMAP_STATUS_SUCCESS){
					var s = [];
					for (var i = 0; i < results.getCurrentNumPois(); i ++){
						addressJson = {"title":results.getPoi(i).title,"lat":results.getPoi(i).point.lat,"lng":results.getPoi(i).point.lng,
								"province":results.getPoi(i).province,"city":results.getPoi(i).city,"address":results.getPoi(i).address};
						s.push(addressJson);
					}
					vm.baiduAddress = s;
				}
			}
	};
	var local = new BMap.LocalSearch(map, options);
	local.search(keyword);
}
init();
