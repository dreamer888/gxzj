package com.dlc.modules.sys.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

import com.dlc.common.annotation.SysLog;
import com.dlc.common.exception.RRException;
import com.dlc.common.utils.*;
import com.dlc.modules.sys.entity.AgentDeviceRelationEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dlc.modules.sys.entity.DeviceEntity;
import com.dlc.modules.sys.service.DeviceService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * 纸巾机设备表
 * 
 * @author dlc.dg.java
 * @email dlc.dg.java@163.com
 * @date 2018-07-15 18:06:09
 */
@RestController("sysDeviceController")
@RequestMapping("/sys/device")
public class DeviceController {
	@Autowired
	private DeviceService deviceService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:device:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<Map<String,Object>> deviceList = deviceService.queryList(query);
		int total = deviceService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(deviceList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{deviceId}")
	@RequiresPermissions("sys:device:info")
	public R info(@PathVariable("deviceId") Long deviceId){
		//DeviceEntity device = deviceService.queryObject(deviceId);
		Map<String,Object> params = new HashMap<>();
		params.put("deviceId",deviceId);
		List<Map<String,Object>> deviceList = deviceService.queryList(params);
		return R.ok().put("device", deviceList.get(0));
	}

	/**
	 * 绑定信息
	 */
	@RequestMapping("/relationInfo/{deviceId}")
	@RequiresPermissions("sys:device:info")
	public R relationInfo(@PathVariable("deviceId") Long deviceId){
		DeviceEntity device = deviceService.queryObject(deviceId);

		return R.ok().put("device", device);
	}
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sys:device:save")
	public R save(@RequestBody DeviceEntity device){
		if (!deviceService.queryByCondition(device).isEmpty()) {
			return R.error("设备编号为：" + device.getDeviceNo() + " 的序列号已存在!");
		}
		if (!deviceService.queryByImeiCondition(device).isEmpty()) {
			return R.error("设备IMEI为：" + device.getImei() + " 的序列号已存在!");
		}
		deviceService.save(device);

		return R.ok();
	}

	/**
	 * 绑定设备
	 */
	@RequestMapping("/saveRelationInfo")
	@RequiresPermissions("sys:device:save")
	public R saveRelationInfo(@RequestBody AgentDeviceRelationEntity agentDeviceRelationEntity){
		return deviceService.saveRelationInfo(agentDeviceRelationEntity);
	}

	/**
	 * 其他操作
	 */
	@RequestMapping("/modifyRelationInfo")
	@RequiresPermissions("sys:device:update")
	public R modifyRelationInfo(@RequestBody Map<String,Object> params){
		if(StringUtils.isBlank((String)params.get("deviceNo"))||params.get("deviceNo")==null){
			return R.error("设备号不能为空");
		}
		if(params.get("agentId")==null||StringUtils.isBlank(String.valueOf(params.get("agentId")))){
			return R.error("代理商id不能为空");
		}
		return deviceService.modifyRelationInfo(params);
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sys:device:update")
	public R update(@RequestBody DeviceEntity device){
		if(!device.getDeviceNo().equals(deviceService.queryObject(device.getDeviceId()).getDeviceNo())){
			Map<String,Object> map = new HashMap<>();
			map.put("deviceNo",device.getDeviceNo());
			if (!deviceService.queryByCondition(device).isEmpty()) {
				return R.error("设备编号为：" + device.getDeviceNo() + " 的序列号已存在!");
			}
		}
		if(!device.getImei().equals(deviceService.queryObject(device.getDeviceId()).getImei())) {
			Map<String, Object> map = new HashMap<>();
			map.put("imei", device.getImei());
			if (!deviceService.queryByImeiCondition(device).isEmpty()) {
				return R.error("设备IMEI为：" + device.getImei() + " 的序列号已存在!");
			}
		}
		deviceService.update(device);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:device:delete")
	public R delete(@RequestBody Long[] deviceIds){
		deviceService.deleteBatch(deviceIds);
		
		return R.ok();
	}
    /**
     * 批量生成二维码
     */
    @RequestMapping("/generateQrCode")
    @RequiresPermissions("sys:device:generateQrCode")
    public R generateQrCode(Long id, HttpServletResponse response) {

       /* List<DeviceEntity> deviceLists = deviceService.queryByIds(ids);
        for (DeviceEntity deviceList : deviceLists) {

        }

        Long id = deviceLists.get(0).getDeviceId();
        String imgName = deviceLists.get(0).getDeviceNo(); // 二维码图片名称(以设备编号命名)
        String content = deviceLists.get(); // 二维码地址

        try {
            BufferedImage image = QRCodeUtil.encode(content, null, true, imgName);
            String fileName = imgName + ".jpg";
            response.addHeader("Cache-Control", "no-cache");
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            ImageIO.write(image, "JPEG", response.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
            return R.error("生成二维码失败");
        }
        return R.ok();
    }*/
        Map<String,Object> map = deviceService.queryDeviceNoById(id);
        String deviceNo = map.get("deviceNo").toString();
        String imei = map.get("imei").toString();
        StringBuffer sb = new StringBuffer();
        String deviceAndImei = sb.append(deviceNo).append(",").append(imei).toString();
        String content = ConfigConstant.QRCODE_URL+"?deviceNo="+deviceNo+"&imei="+imei; // 二维码内容
        try {

            BufferedImage image = QRCodeUtil.encode(content, null, true, String.valueOf(deviceNo));
            String fileName = String.valueOf(deviceNo) + ".jpg";
            response.addHeader("Cache-Control", "no-cache");
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            ImageIO.write(image, "JPEG", response.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
            return R.error("生成二维码失败");
        }
        return R.ok();


    }

	/**
	 * 列表
	 */
	@RequestMapping("/select")
	@RequiresPermissions("sys:device:list")
	public R select(){
		List<Map<String,Object>> list = deviceService.select();
		return R.ok().put("device", list);
	}


	/**
	 * 导出excel
	 */
	@SysLog("导出设备表数据到Excel表格")
	@RequestMapping("/exportExcel")
	public R exportExcel(HttpServletResponse response) {

		String fileName = "设备列表" + System.currentTimeMillis() + ".xls"; // 文件名
		String sheetName = "设备列表";// sheet名

		String[] title = new String[] { "设备编号","设备IMEI", "代理商", "设备地址详情", "设备状态", "商品库存", "商品名称","商品单价", "创建时间" };// 标题

		List<Map<String, Object>> list = deviceService.exportExcel();// 内容list

		String[][] values = new String[list.size()][];
		for (int i = 0; i < list.size(); i++) {
			values[i] = new String[title.length];
			// 将对象内容转换成string
			Map<String, Object> map = list.get(i);

			values[i][0] = map.get("deviceNo").toString();
			values[i][1] = map.get("imei").toString();
			if (map.get("name") != null){
				values[i][2] = map.get("name").toString();
			}else{
				values[i][2] = "无";
			}
			values[i][3] = map.get("addressDetail").toString();
			if ((Integer) map.get("status") == 1){
				values[i][4] = "正常";
			}else if((Integer) map.get("status") == 2){
				values[i][4] = "离线";
			}else if((Integer) map.get("status") == 3){
				values[i][4] = "故障";
			}

			Integer inventory = (Integer) map.get("inventory"); //库存
			values[i][5] = inventory.toString();
			values[i][6] = map.get("goodsName").toString();

			Integer goodsPrice = (Integer) map.get("goodsPrice"); //单价
			//Integer price = goodsPrice/100;
			values[i][7] = goodsPrice.toString();

		}

		HSSFWorkbook wb = ExportExcel.getHSSFWorkbook(sheetName, title, values, null);

		// 将文件存到指定位置
		try {
			this.setResponseHeader(response, fileName);
			OutputStream os = response.getOutputStream();
			wb.write(os);
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return R.ok();
	}


	public void setResponseHeader(HttpServletResponse response, String fileName) {
		try {
			try {
				fileName = new String(fileName.getBytes(), "ISO8859-1");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			response.setContentType("application/octet-stream;charset=ISO8859-1");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
			response.addHeader("Pargam", "no-cache");
			response.addHeader("Cache-Control", "no-cache");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/***
	 * 读取Excel数据到数据库
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@SysLog("读取Excel数据到数据库的设备表")
	@RequestMapping(value = "/importExcel")
	@RequiresPermissions("sys:device:importExcel")
	public R readExcel(HttpServletRequest request, HttpSession session) throws IOException {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("excelFile");
		// 判断文件是否为空
		if (file == null) {
			return R.error(-1, "文件不能为空");
		}
		String name = file.getOriginalFilename();
		long size = file.getSize();
		if (name == null || ExcelUtil.EMPTY.equals(name) && size == 0) {
			return R.error("文件内容为空");
		}
		// 读取Excel数据到List中
		List<ArrayList<String>> list = new ExcelRead().readExcel(file);
		if (list == null) {
			return R.error("文件格式不正确！");
		}
		// list中存的就是excel中的数据，可以根据excel中每一列的值转换成你所需要的值（从0开始），如：
		DeviceEntity device = null;
		List<DeviceEntity> listDevice = new ArrayList<DeviceEntity>();
		try {
			for (int i = 0; i < list.size(); i++) {
				device = new DeviceEntity();

				if (StringUtils.isBlank(list.get(i).get(0))) {
					return R.error("设备编号不能为空");
				}
				device.setDeviceNo(list.get(i).get(0));
				if (!deviceService.queryByCondition(device).isEmpty()) {
					return R.error("设备编号为：" + device.getDeviceNo() + " 的序列号已存在!");
				}
				if (StringUtils.isBlank(list.get(i).get(1))) {
					return R.error("设备IMEI不能为空");
				}
				device.setImei(list.get(i).get(1));
				if (!deviceService.queryByImeiCondition(device).isEmpty()) {
					return R.error("设备IMEI为：" + device.getImei() + " 的序列号已存在!");
				}
				if (StringUtils.isBlank(list.get(i).get(1))) {
					return R.error("设备地址不能为空");
				}
				device.setAddressDetail(list.get(i).get(2));
				device.setStatus(2);
				if (StringUtils.isBlank(list.get(i).get(1))) {
					return R.error("库存不能为空");
				}
				device.setInventory(Integer.valueOf(list.get(i).get(3)));
				if (StringUtils.isBlank(list.get(i).get(4))) {
					return R.error("商品名称不能为空");
				}
				device.setGoodsName(list.get(i).get(4));
				if (StringUtils.isBlank(list.get(i).get(5))) {
					return R.error("商品价格不能为空");
				}
				device.setGoodsPrice(Integer.valueOf(list.get(i).get(5)));
				device.setCreateTime(new Date());

				listDevice.add(device);

			}
		} catch (Exception e) {
			throw new RRException("导入失败，请检查你的excel格式是否正确！");
		}
		deviceService.saveBatch(listDevice);
		return R.ok("导入成功！");
	}


}
