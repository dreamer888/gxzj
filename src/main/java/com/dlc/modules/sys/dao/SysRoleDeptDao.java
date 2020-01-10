package com.dlc.modules.sys.dao;

import com.dlc.modules.sys.entity.SysRoleDeptEntity;

import java.util.List;
import java.util.Map;

/**
 * 角色与部门对应关系
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017年6月21日 23:33:46
 */
public interface SysRoleDeptDao extends BaseDao<SysRoleDeptEntity> {

	void saveOne(Map<String, Object> map);
	/**
	 * 根据角色ID，获取部门ID列表
	 */
	List<Long> queryDeptIdList(Long roleId);
}
