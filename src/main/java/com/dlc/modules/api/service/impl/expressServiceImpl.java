package com.dlc.modules.api.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dlc.common.utils.ConfigConstant;
import com.dlc.common.utils.HttpRequest;
import com.dlc.common.utils.JacksonHelper;
import com.dlc.modules.api.dao.ExpressMapper;
import com.dlc.modules.api.entity.Result;
import com.dlc.modules.api.entity.ResultItem;
import com.dlc.modules.api.entity.TaskRequest;
import com.dlc.modules.api.entity.TaskResponse;
import com.dlc.modules.api.service.expressService;
import com.mchange.v1.db.sql.ConnectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author 廖修坤
 * @date 2018/8/10 18:56
 */
@Service
public class expressServiceImpl implements expressService {
    @Autowired
    private ExpressMapper expressMapper;

    private Logger log = LoggerFactory.getLogger(getClass());
    HttpRequest httpRequest = new HttpRequest();
    /**
     * 物流跟踪：方式一：物流推送（有回调）
     * */
    public TaskResponse queryExpressByOrderNo( TaskRequest taskRequest)  {
        String key = ConfigConstant.KUAIDI100_KEY;//快递100KEY
        String reqPath = ConfigConstant.KUAIDI100_REQUESTPATH;//订阅请求地址
        String callbackPath = ConfigConstant.CALLBACKPATH;//快递100回调地址
        String codePage = "UTF-8";//编码方式
        HashMap<String, String> parameters = new HashMap<String, String>();
        taskRequest.getParameters().put("callbackurl",callbackPath);//回调地址
        taskRequest.setKey(key);
        parameters.put("schema", "json");
        parameters.put("param", JacksonHelper.toJSON(taskRequest));

        try {
          String result =  httpRequest.postData(reqPath,parameters,codePage);
          TaskResponse resp = JacksonHelper.fromJSON(result, TaskResponse.class);
            if(resp.getResult()==true){
                //log.info("result。akon>>:"+resp);
                System.out.println("订阅成功");
                return resp;
            }else{
               //log.info("result。error>>:"+resp);
                System.out.println("订阅失败");
               return resp;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        /*方式二：没有回调*/
        /*InputStream urlStream = null;
        String urlPath = "http://api.kuaidi100.com/api?id="+key+"&com="+com+"&nu="+nu+"&show=2&muti=1&order=desc";
        try
        {
            //URL url= new URL(urlPath);
            URL url= new URL("http://api.kuaidi100.com/api?id=tEstYvCTEomr2181&com=yunda&nu=383175856023&show=2&muti=1&order=desc");
            URLConnection con=url.openConnection();
            con.setAllowUserInteraction(false);
            urlStream = url.openStream();
            String type = con.guessContentTypeFromStream(urlStream);
            String charSet=null;
            if (type == null)
                type = con.getContentType();

            if (type == null || type.trim().length() == 0 ) {
                log.info("type>>:" + type + "<<length>>:" + type.trim().length() + "<<indexOf>>:" + type.trim().indexOf("text/html"));
                return null;
            }

            if(type.indexOf("charset=") > 0)
                charSet = type.substring(type.indexOf("charset=") + 8);

            byte b[] = new byte[10000];
            int numRead = urlStream.read(b);
            String content = new String(b, 0, numRead);
            while (numRead != -1) {
                numRead = urlStream.read(b);
                if (numRead != -1) {
                    //String Content = new String(b, 0, numRead);
                    String newContent = new String(b, 0, numRead,charSet);
                    content += newContent;
                }
            }
            //System.out.println("content:" + content);
            map = new HashMap<>();
            map.put("content",content);
            return map;

        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }finally {
            try {
                urlStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
*/
        return null;
    }

    /**
     * 查询快递
     * */
    public Result queryExpress(Map<String,Object> map) {
        return expressMapper.queryExpress(map);
    }

    /**
     * 更新快递
     * */
    public void updateExpressByExpressNu(Map<String,Object> map,Map<String,Object> mapList) {
       //String nu = result.getNu();
        //1.根据快递单号更新快递表
        expressMapper.updateExpressByExpressNu(map);

    }

    /**
     * 新增快递
     * */
    public int addExpress(Map<String,Object> map,Map<String,Object> mapList) {
       int exprsCount = expressMapper.addExpress(map);
        return exprsCount;
    }

    /**
     * 查询快递详情
     * */
    public List<Map<String,Object>> queryExpressList(Result result) {
        JSONObject data = null;
        List<Map<String, Object>> exprsDetail = new ArrayList<>();//储存快递详情
        List<Map<String, Object>> list = expressMapper.queryExpressList(result);
        Map<String, Object> exprsList = new HashMap<String, Object>();//存储处理后的数据
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, Object> map = list.get(0);
            exprsList.put("message",map.get("message"));
            exprsList.put("nu",map.get("nu"));
            exprsList.put("ischeck",map.get("ischeck"));
            exprsList.put("com",map.get("com"));
            exprsList.put("status",map.get("status"));
            exprsList.put("state",map.get("state"));
            exprsList.put("condition",map.get("condition"));
            for (Map<String, Object> objectMap : list) {
                data = new JSONObject();
                String context = objectMap.get("context").toString();
                Date ftime = (Date) objectMap.get("ftime");
                Date time = (Date) objectMap.get("time");
                String nu = objectMap.get("nu").toString();

                //put
                data.put("context", context);
                data.put("ftime", ftime);
                data.put("time", time);
                data.put("nu", nu);
                exprsDetail.add(data);
            }
            exprsList.put("exprsDetail",exprsDetail);
            list.clear();
            list.add(exprsList);
        }
        return list;
    }

    /**
     * 根据订单号查询快递最新列表
     * */
    public List<ResultItem> queryExpressLastMsg(String nu) {
        return expressMapper.queryExpressLastMsg(nu);
    }

    /**
     * 新增快递详情
     * */
    public void addExpressDetail(Map<String, Object> mapList) {
        //删除快递详情
        //expressMapper.delExpressDetail(mapList);
        expressMapper.addExpressDetail(mapList);

    }

    /**
     * 更新快递详情
     * */
    public void uppExpressDetail(Map<String, Object> mapList) {

        //int exprsCount =  expressMapper.delExpressDetail(mapList);
        //log.info("《《更新快递成功了吗？》》"+exprsCount);
        //if(exprsCount>0){}
            expressMapper.addExpressDetail(mapList);

    }
}
