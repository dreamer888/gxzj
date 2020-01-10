package com.dlc.common.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Package com.dlc.common.utils
 * @Description: PageHelp
 * @Copyright: Copyright (c) 2017
 * @Author tangxs
 * @date 2018年3月14日 20:30
 * @version V1.0.0
 */
public class PageHelp {

    /**
     * 假分页
     * @param list
     * @param pageSize
     * @param page  从 0 开始，0为第一页
     * @return
     */
    public static List getListPage(List list,Integer pageSize,Integer page){
        page = page == null ? 0:page;
        pageSize = pageSize == null || pageSize.intValue() <= 0 ? 5:pageSize;
        if(list == null || list.size() < 1){
            return new ArrayList();
        }
        List listR = new ArrayList();
        int sum = list.size();
        //总页数
        int totalPage = sum%pageSize == 0? sum/pageSize:sum/pageSize+1;
        page = page > totalPage ? totalPage : page;
        pageSize = pageSize > sum ? sum : pageSize;
        for(int i =page*pageSize ;i<pageSize*(page+1);i++){
            listR.add(list.get(i));
        }
        return listR;
    }

    public static void main(String[] args) {
        List list = new ArrayList();
        for (int i = 0;i<10;i++){
            list.add(i);
        }

        for (Object s : getListPage(list,2,3)){
            System.out.println(s);
        }

    }
}
