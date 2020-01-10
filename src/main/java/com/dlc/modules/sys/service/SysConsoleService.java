package com.dlc.modules.sys.service;

import com.dlc.modules.sys.entity.SysConsoleEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-01-08 09:59:06
 */
public interface SysConsoleService {
	
	SysConsoleEntity queryObject(Integer id);
	
	List<SysConsoleEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysConsoleEntity sysConsole);
	
	void update(SysConsoleEntity sysConsole);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
