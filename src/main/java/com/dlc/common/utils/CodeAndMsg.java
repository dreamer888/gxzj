package com.dlc.common.utils;

/**
 * @author tangxs
 * @date 2017/12/13
 */
public enum CodeAndMsg {

    OK(1,"成功"),
    ERROR_LACK_PARAM(-1,"缺少参数"),
    ERROR_USER_NOT_LOGIN(-2,"用户未登录"),
    ERROR_USER_LOGIN(-3,"用户名或密码有误，登录失败"),
    ERROR_REPEAT(-4,"帐号重复"),
    ERROR_NOT_CODE(-7,"Code is wrong,请与管理员联系"),
    ERROR_NOT_EXIS(-8,"对象不存在"),
    ERROR_PHONE_SEND_CODE_FAIL(-9,"发送验证码失败,请与管理员联系"),
    ERROR_CODE(-10,"验证码错误"),
    ERROR_NOT_ACCESS_TOKEN(-11,"AccessToken is wrong,请与管理员联系"),
    ERROR_NOT_OPENID(-12,"Openid is wrong,请与管理员联系"),
    ERROR_NOT_TICKET(-13,"ticket is wrong,请与管理员联系"),
    ERROR_IS_BLACKLIST(-14,"存在逾期未还的伞,已被加入黑名单"),
    ERROR_USER_IS_BLACKLIST(-14,"已被加入黑名单"),
    ERROR_UPDATE_FAIL(-15,"修改失败"),
    ERROR_CODE_INVALID(-16,"二维码已经失效"),
    ERROR_CODE_TYPE(-17,"二维码传入类型有误"),
    ERROR_NOT_ORDER_ID(-18,"还伞必须传入OrderId"),
    ERROR_ADD_ORDER(-19,"新增订单失败"),
    ERROR_UMB_NOT_NORM(-20,"传入的雨伞编号状态异常"),
    ERROR_ORDER_NOT_NORM(-21,"同一伞id，不能同时存在俩个未完成的订单"),
    ERROR_USER_NOT_NORM(-22,"用户权限不足"),
    ERROR_USER_MANAGER_NOT_NORM(-23,"经理绑定请先导入经理信息"),
    ERROR_ALREADY_USED(-24,"亲很抱歉，只能同时借一把伞哦，请您先归还已借的伞"),
    ERROR_PAY_TYPE_ERROR(-25,"支付类型有误"),
    ERROR_PAY_ERROR(-26,"支付失败"),
    ERROR_PAY_NOT_ORDERNO(-27,"消费须传入消费订单号"),
    ERROR_PAY_NOT_OPENID(-28,"支付参数openid不能为空"),
    ERROR_PAY_NOT_MONEY(-29,"支付参数money不能为空，且须大于0"),
    ERROR_USER_MONEY_NOT(-31,"余额不足"),
    ERROR_USER_NOT_MONEY(-30,"用户信息异常"),
    ERROR_USER_DEPOSIT_YET(-32,"用户已交押金，请勿重复操作"),
    ERROR_BANK_NO_ERROR(-33,"银行卡号有误，请填写正确的银行卡号"),
    ERROR_BANK_NAME_ERROR(-34,"开户银行名称有误，请填写与卡号对应的银行名称，如兴业银行、中国银行"),
    ERROR_USER_DEPOSIT_ERROR(-35,"用户未交押金，或押金不足"),
    ERROR_RETURN_DEPOSIT_ERROR(-36,"押金退还失败"),
    ERROR_TAPE_OUT(-37,"你已在其它设备登录，请重新登录"),
    ;

    CodeAndMsg(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;
    private String msg;
    /**
     * @Package com.dlc.common.utils
     * @Description: CodeAndMsg
     * @Copyright: Copyright (c) 2017
     * Author tangxs
     * @date 2017/12/13 17:12
     * version V1.0.0
     */
    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
