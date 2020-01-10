package com.dlc.common.utils;

/**
 * 系统参数相关Key
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-26 10:33
 */
public class ConfigConstant {
    /**
     * 云存储配置KEY
     */
    public final static String CLOUD_STORAGE_CONFIG_KEY = "CLOUD_STORAGE_CONFIG_KEY";
    //迪尔西测试公众号2
    /** 微信APPID */
    public static final String WECHAT_APPID = "wx2c49fec17dc24b80";
    /**快递100KEY*/
    public static final String KUAIDI100_KEY = "aYOyhsTc7994";
    /**快递100订阅请求路劲*/
    public static final String KUAIDI100_REQUESTPATH = "http://poll.kuaidi100.com/poll";
    /**快递100回调地址*/
    public static final String CALLBACKPATH = "http://gxzjj.j.xiaozhuschool.com/api/express/expressCallBack";
    /** 微信WECHAT_APPSECRET */
    public static final String WECHAT_APPSECRET = "090250d12824be27d7c159361e7347e0";
    /** 验证码地址 */
    public static final String PHONE_VER_URL = "http://120.55.116.6:7822/sms";
    /** 域名 */
    public static final String PRO_VER_URL = "http://gxzjj.j.xiaozhuschool.com";
   /* *//**打开婴儿车监控地址*//*
    public static final String OPEN_BABY_CAR_ULR = "http://120.77.72.190/babycar/open";
    *//**打开婴儿车音乐播放地址*//*
    public static final String OPEN_MUSIC_ULR = "http://120.77.72.190/babycar/mp3play";*/
    /**迪尔西公众号链接*/
    public static final String QRCODE_URL = "http://weixin.qq.com/r/DTkQCODEjb3yraRr92wf";

    /**
     *  登录用户
     */
    public static final String ACCOUNT = "ACCOUNT";

    /**
     *  工作用户
     */
    public static final String ENTERPRISE = "ENTERPRISE";
    /**
     *  环信API  URL
     */
    public static final String HUANGXIN = "http://a1.easemob.com/";

    /**
     *  推荐标准  8人以上推荐
     */
    public static final int RECOMMENDEDSTANDARD = 8;

    /**
     * 未读消息
     */
    public static final int UN_READ = 0;
    /**
     * 系统消息
     */
    public static final int SYS_MESSAGE = 1;
    /**
     * 电子围栏消息
     */
    public static final Object ELECTRONIC_MESSAGE = 2;
    /**微信支付为1*/
    public static final Integer WXPAY = 1;
    /**支付宝支付为2*/
    public static final Integer ZFBPAY = 2;
    /**代理商缓存前缀*/
    public static final String USER = "gxzjj_user_";
    /**代理商缓存前缀*/
    public static final String AGENT = "gxzjj_agent_";
    /**商品缓存前缀*/
    public static final String GOODS = "gxzjj_goods_";


    /**下面是手机短信发送配置*/
    public static final String ACTION = "send";
    public static final String ACCOUNT_NUM = "940006";
    public static final String PASSWORD = "MGwmFS";
    public static final String EXTNO = "10690458";
    public static final String RT = "json";
    public static String ticket = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=";

    //购买纸巾微信支付回调
    public static String NOTIFY_URL = "http://gxzjj.j.xiaozhuschool.com/api/h5/wxNotify";

    //购买纸巾微信退款回调
    public static String REFOUND_NOTIFY_URL = "http://gxzjj.j.xiaozhuschool.com/api/h5/wxRefoundNotify";

    /**供货端支付回调链接*/
    public static final String FORGOODS_CALLBACK_URL = "http://gxzjj.j.xiaozhuschool.com/api/myWallet/goodsCallBk";

    /**企业向个人付款到微信零钱的接口地址*/
    public static final String PAY_TO_USER = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
    /**免费领取纸巾url*/
    public static final String FREE_MAPPER = "http://gxzjj.j.xiaozhuschool.com/h5/gxzjj/other/html/user_success.html?";
}
