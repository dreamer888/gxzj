package com.dlc.modules.api.controller;

import com.dlc.common.utils.PageUtils;
import com.dlc.common.utils.Query;
import com.dlc.common.utils.R;
import com.dlc.modules.api.entity.OfficialAccounts;
import com.dlc.modules.api.service.OfficialAccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 公众号模块controller
 *
 * @author chenyuexin
 * @version 1.0
 * @date 2018-07-18 13:58
 */
@RestController
@RequestMapping("/api/officialAccounts")
public class OfficialAccountsController extends BaseController {

    @Autowired
    private OfficialAccountsService officialAccountsService;

    /**
     * 公众号列表(管理端)
     *
     * @return
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        if (null == getAgentId(request)) {
            return R.reError("用户未登录!");
        }
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return R.error("分页信息不能为空");
        }
        Query query = new Query(params);
        //List<Map<String, Object>> list = officialAccountsService.queryOfficialAccountsList(query);
        List<Map<String, Object>> list = officialAccountsService.queryManagementOfficialAccountsList(query);

        int total = officialAccountsService.queryofficialAccountsCount(query);
        PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());
        return R.reOk(pageUtil);
    }

    /**
     * 公众号上下架
     *
     * @param id
     * @param status
     * @return
     */
    @RequestMapping("/updateStatus")
    public R updateStatus(Long id, Integer status) {
        if (null == id || null == status) {
            return R.reError("参数不能为空!");
        }
        int res = officialAccountsService.updateStatus(status, id);
        if (res > 0) {
            return R.reOk();
        }
        return R.reError("操作失败");
    }


    /**
     * 添加公众号接口
     *
     * @param officialAccounts
     * @param request
     * @return
     */
    @RequestMapping("/add")
    public R add(OfficialAccounts officialAccounts, HttpServletRequest request) {
        Long userId = getAgentId(request);
        int res = officialAccountsService.addOfficialAccounts(officialAccounts, userId);
        if (res > 0) {
            return R.reOk();
        }
        return R.reError("保存失败");
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public R delete(Long id) {
        int res = officialAccountsService.delete(id);
        if (res > 0) {
            return R.reOk();
        }
        return R.reError("删除失败");
    }

    /**
     * 公众号详情接口
     *
     * @return
     */
    @RequestMapping("/info")
    public R info(Long id) {
        Map<String, Object> map = officialAccountsService.queryManagementOfficialInfoById(id);
        return R.reOk(map);
    }

    /**
     * 更新
     * @param officialAccounts
     * @return
     */
    @RequestMapping("/update")
    public R update(OfficialAccounts officialAccounts){
        int res  =officialAccountsService.updateOfficialAccount(officialAccounts);
        if (res>0){
            return R.reOk();
        }
        return R.reError("更新失败,请联系管理员!");
    }


    @RequestMapping("isEdit")
    public R isEdit(Long agentId){
        if (agentId==0){
            return R.reError("该公众号为平台添加,您无权编辑!");
        }
        return R.reOk();
    }

}
