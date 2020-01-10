package com.dlc.common.utils;

/**
 * 常量
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月15日 下午1:23:52
 */
public class Constant {
    /**
     * 超级管理员ID
     */
    public static final int SUPER_ADMIN = 1;

    /**
     * 菜单类型
     *
     * @author chenshun
     * @email sunlightcs@gmail.com
     * @date 2016年11月15日 下午1:24:29
     */
    public enum MenuType {
        /**
         * 目录
         */
        CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        private MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 定时任务状态
     *
     * @author chenshun
     * @email sunlightcs@gmail.com
     * @date 2016年12月3日 上午12:07:22
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 暂停
         */
        PAUSE(1);

        private int value;

        private ScheduleStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3);

        private int value;

        private CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }


    public enum OrderStatus {
        /**
         * 待付款0
         */
        WAIT_PAY(0),
        /**
         * 待发货1
         */
        WAIT_SEED(1),
        /**
         * 待收货2
         */
        WAIT_RECIVE(2),
        /**
         * 待评价3
         */
        WAIT_COMMENT(3),
        /**
         * 已完成4
         */
        ORDER_OVER(4),
        /**
         * 已取消5
         */
        ORDER_CANCEL(5);

        private int value;

        OrderStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    /**上传图片限制大小*/
    public static final Long FILE_SIZE = 10485760l;//10M

    public enum AddressStatus {
        /**
         * 地址不可用
         */
        ADDRESS_UNUSABLE(0),

        /**
         * 地址可用
         */
        ADDRESS_USABLE(1);

        private int value;

        AddressStatus(int value) {
            this.value =value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum WalletDetailStatus{
        /**
         *审核中
         */
        CHECKING(1),

        /**
         * 审核失败
         */
        CHECK_FAILED(2),

        /**
         * 已完成
         */
        CHECK_PAY_SUCCESS(3),

        /**
         * 提现中
         */
        CHECK_PAYING(4),

        /**
         * 提现失败
         */
        CHECK_PAY_FAILED(5);


        private int value;

        WalletDetailStatus(int value){
            this.value =value;
        }

        public int getValue() {
            return value;
        }
    }

}
