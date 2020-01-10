/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: IDcard
 * Author:   Administrator
 * Date:     2018/7/26 13:50
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.dlc;

import java.util.regex.Pattern;

/**
 * @author chenyuexin
 * @date 2018-07-26 13:50
 * @version 1.0
 */
public class IDcard {

    /**
     * 我国公民的身份证号码特点如下
     * 1.长度18位
     * 2.第1-17号只能为数字
     * 3.第18位只能是数字或者x
     * 4.第7-14位表示特有人的年月日信息
     * 请实现身份证号码合法性判断的函数，函数返回值：
     * 1.如果身份证合法返回0
     * 2.如果身份证长度不合法返回1
     * 3.如果第1-17位含有非数字的字符返回2
     * 4.如果第18位不是数字也不是x返回3
     * 5.如果身份证号的出生日期非法返回4
     *
     * @since 0.0.1
     */
    private static int validator(String id) {
        String str = "[1-9]{2}[0-9]{4}(19|20)[0-9]{2}"
                + "((0[1-9]{1})|(1[1-2]{1}))((0[1-9]{1})|([1-2]{1}[0-9]{1}|(3[0-1]{1})))"
                + "[0-9]{3}[0-9x]{1}";
        Pattern pattern = Pattern.compile(str);
        return pattern.matcher(id).matches() ? 0 : 1;
    }

    public static void main(String[] args) {
        String idcard = "43522419840413332";
        int  a = validator(idcard);
        System.out.println("a-->"+a);
    }
}
