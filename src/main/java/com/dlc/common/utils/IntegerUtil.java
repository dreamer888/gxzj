/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: IntegerUtil
 * Author:   Administrator
 * Date:     2018/5/30 19:10
 * Description: Integer 工具类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.dlc.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chenyuexin
 * @date 2018-05-30 19:10
 * @version 1.0
 */
public class IntegerUtil {

    public static boolean isContainNumber(String company) {

        Pattern p = Pattern.compile("[0-9]");
        Matcher m = p.matcher(company);
        if (m.find()) {
            return true;
        }
        return false;
    }
}
