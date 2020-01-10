package com.dlc.modules.sys.service.impl;

import com.dlc.common.annotation.DataFilter;
import com.dlc.modules.sys.dao.SysDeptDao;
import com.dlc.modules.sys.entity.SysDeptEntity;
import com.dlc.modules.sys.entity.SysUserEntity;
import com.dlc.modules.sys.service.SysDeptService;
import com.dlc.modules.sys.service.SysRoleDeptService;
import com.dlc.modules.sys.service.SysRoleMenuService;
import com.dlc.modules.sys.service.SysUserRoleService;
import com.qiniu.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("sysDeptService")
public class SysDeptServiceImpl implements SysDeptService {
	@Autowired
	private SysDeptDao sysDeptDao;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysRoleDeptService sysRoleDeptService;
	
	@Override
	public SysDeptEntity queryObject(Long deptId){
		return sysDeptDao.queryObject(deptId);
	}
	
	@Override
	@DataFilter(tableAlias = "d", user = false)
	public List<SysDeptEntity> queryList(Map<String, Object> map){
		return sysDeptDao.queryList(map);
	}
	
	@Override
	public void save(SysDeptEntity sysDept, SysUserEntity user){
		sysDeptDao.save(sysDept);
		List<Long> roleList = sysUserRoleService.queryRoleIdList(user.getUserId());
		if(roleList != null && roleList.size() > 0){
			for(Long l : roleList){
				sysRoleDeptService.saveOne(l,sysDept.getDeptId());
			}
		}
	}
	
	@Override
	public void update(SysDeptEntity sysDept){
		sysDeptDao.update(sysDept);
	}
	
	@Override
	public void delete(Long deptId){
		sysDeptDao.delete(deptId);
	}

	@Override
	public List<Long> queryDetpIdList(Long parentId) {
		return sysDeptDao.queryDetpIdList(parentId);
	}

	@Override
	public String getSubDeptIdList(Long deptId){
		//部门及子部门ID列表
		List<Long> deptIdList = new ArrayList<>();

		//获取子部门ID
		List<Long> subIdList = queryDetpIdList(deptId);
		getDeptTreeList(subIdList, deptIdList);

		//添加本部门
		deptIdList.add(deptId);

		String deptFilter = StringUtils.join(deptIdList, ",");
		return deptFilter;
	}

	/**
	 * 递归
	 */
	private void getDeptTreeList(List<Long> subIdList, List<Long> deptIdList){
		for(Long deptId : subIdList){
			List<Long> list = queryDetpIdList(deptId);
			if(list.size() > 0){
				getDeptTreeList(list, deptIdList);
			}

			deptIdList.add(deptId);
		}
	}
}
