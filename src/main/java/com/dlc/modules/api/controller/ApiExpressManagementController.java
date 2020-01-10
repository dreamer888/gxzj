package com.dlc.modules.api.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dlc.common.utils.JacksonHelper;
import com.dlc.common.utils.R;
import com.dlc.modules.api.entity.*;
import com.dlc.modules.api.service.expressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 廖修坤
 * @date 2018/8/10
 * 物流跟踪
 * @param: String nu:快递单号
 * @param: String com:快递公司简称（不支持中文）
 */
@RestController
@RequestMapping("/api/express")
public class ApiExpressManagementController extends HttpServlet {
    private Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private expressService expressService;

    @RequestMapping("/queryExpress")
    public R queryExpress(String param){
        JSONObject obj = JSONObject.parseObject(param);
        TaskRequest taskRequest = JSONObject.toJavaObject(obj,TaskRequest.class);
        TaskResponse resp = expressService.queryExpressByOrderNo(taskRequest);
        return R.reOk().put("resp",resp);
    }

    /**
     * 物流跟踪回调
     * */
    @RequestMapping("/expressCallBack")
    public void callbackExpress(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
        log.info("快递100进入回调》》");
        String param = null;
        NoticeResponse resp = new NoticeResponse();
        resp.setResult(false);
        resp.setReturnCode("500");
        resp.setMessage("保存失败");
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> mapList = new HashMap<>();
        try {
            param = request.getParameter("param");
            if(param!=null){
            NoticeRequest nReq = JacksonHelper.fromJSON(param,
                    NoticeRequest.class);

            Result result = nReq.getLastResult();
            log.info("<<param回调数据>>"+param+"<<");
            // 处理快递结果
            resp.setResult(true);
            resp.setReturnCode("200");
            resp.setMessage("提交成功");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(JacksonHelper.toJSON(resp)); //这里必须返回，否则认为失败，过30分钟又会重复推送。
            log.info("获取回调数据:param》》"+param);

            //1.查询数据库是否有记录
            JSONObject obj = JSONObject.parseObject(param);//String转JSONObject
            String lastResultTemp = obj.getString("lastResult");
            JSONObject lastResult= JSONObject.parseObject(lastResultTemp);

            String nu = lastResult.getString("nu");//订单号
            String ischeck = lastResult.getString("ischeck");//是否签收，0为签收
            String condition = lastResult.getString("condition");
            String com = lastResult.getString("com");//快递公司简称
            String state = lastResult.getString("state");//快递单当前签收状态

            map.put("status",obj.getString("status"));
            map.put("message",obj.getString("message"));
            map.put("ischeck",ischeck);
            map.put("condition",condition);
            map.put("com",com);
            map.put("nu",nu);
            mapList.put("nu",nu);
            map.put("state",state);

            Result resultQuery = expressService.queryExpress(map);//查询快递表
            if(resultQuery!=null){
                //数据库已经有此数据，修改
                log.info("<<数据库已有快递信息,更新数据>>");
                expressService.updateExpressByExpressNu(map,mapList);//修改快递信息
                JSONArray data = lastResult.getJSONArray("data");

                for(int i=0;i<data.size();i++){
                    JSONObject jsonDate = data.getJSONObject(0);
                   // log.info("<<jsonDate>>"+jsonDate+">>>");
                    mapList.put("time",jsonDate.getString("time"));
                    mapList.put("ftime",jsonDate.getString("ftime"));
                    mapList.put("context",jsonDate.getString("context"));
                }
                    //新增快递详情
                    expressService.uppExpressDetail(mapList);
                }else {
                    //无此数据，新增
                  log.info("<<没有这个快递信息新增>>");
                  int exprsCount=  expressService.addExpress(map,mapList);//新增快递
                    if(exprsCount>0){
                        JSONArray data = lastResult.getJSONArray("data");
                        for(int i=0;i<data.size();i++){
                            JSONObject jsonDate = data.getJSONObject(i);
                            mapList.put("time",jsonDate.getString("time"));
                            mapList.put("ftime",jsonDate.getString("ftime"));
                            mapList.put("context",jsonDate.getString("context"));
                            //2.新增快递详情
                            expressService.addExpressDetail(mapList);
                        }
                    }
                }
            }
            //return R.reOk().put("result",result);
        } catch (Exception e) {
            response.setCharacterEncoding("UTF-8");
            resp.setMessage("保存失败" + e.getMessage());
            response.getWriter().print(JacksonHelper.toJSON(resp));//保存失败，服务端等30分钟会重复推送。
        }
        log.info("<<物流跟踪回调成功了>>");
        //return R.reOk().put("param",param);
    }

    /**
     * 查询快递接口
     * 1.快递列表；2.倒叙
     * */
    @RequestMapping("/queryExpressMsg")
    public R queryExpressMsg(String nu,String com){
        Result result =new Result();
        result.setCom(com);
        result.setNu(nu);
        List<Map<String,Object>> list = expressService.queryExpressList(result);//查询快递详情
        return R.reOk().put("list",list);
    }

    /**
     * 根据订单号查询快递最新列表
     * 只显示最后（最新）一条快递信息
     * */
    @RequestMapping("/queryExpressLastMsg")
    public R queryExpressLastMsg(String nu){
        List<ResultItem> resultItemList= expressService.queryExpressLastMsg(nu);
        return R.reOk().put("resultItemList",resultItemList);
    }
}
