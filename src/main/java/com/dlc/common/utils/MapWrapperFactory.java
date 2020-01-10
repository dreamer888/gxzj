/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: MapWrapperFactory
 * Author:   Administrator
 * Date:     2018/5/31 20:20
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.dlc.common.utils;

/**
 * @author chenyuexin
 * @date 2018-05-31 20:20
 * @version 1.0
 */

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;

import java.util.Map;

/**
 * Map 类型结果转驼峰
 *
 * @author liuzh
 * @since 2017/6/18.
 */
public class MapWrapperFactory implements ObjectWrapperFactory {

    @Override
    public boolean hasWrapperFor(Object object) {
        return object != null && object instanceof Map;
    }

    @Override
    public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
        return new MyMapWrapper(metaObject, (Map) object);
    }

}
