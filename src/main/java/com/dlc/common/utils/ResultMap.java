package com.dlc.common.utils;

import java.awt.*;

/**
 * @Package com.dlc.common.utils
 * @Description: ResultMap
 * @Copyright: Copyright (c) 2017
 * @Author tangxs
 * @date 2018年3月14日 21:07
 * @version V1.0.0
 */
public class ResultMap {

        public class ResultList{
            List list ;

            public ResultList(List list) {
                this.list = list;
            }
        }

    public class ResultObject{
        Object object;

        public ResultObject(Object object) {
            this.object = object;
        }
    }

}
