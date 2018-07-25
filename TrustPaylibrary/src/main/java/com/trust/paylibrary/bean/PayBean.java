package com.trust.paylibrary.bean;

/**
 * Created by Trust on 2018/6/5.
 */

public class PayBean {


    /**
     * status : 1
     * info : 支付宝支付-预支付下单已完成
     * code : null
     * type : null
     * content : {"out_trade_no":"201807251503243770","alipayResponse":"alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2018011901975149&biz_content=%7B%22body%22%3A%22%E6%B5%81%E9%87%8F%E5%85%85%E5%80%BC%22%2C%22out_trade_no%22%3A%22201807251503243770%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E6%B5%81%E9%87%8F%E5%85%85%E5%80%BC%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.01%22%7D&charset=utf-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2F58.246.120.86%3A50080%2Frent%2Frest%2FalipayNotify&sign=odO7FIMCB%2FR3cz8tSy%2F0qBWO1GFS1tU4IncDCkyKqRZaQ71M1ZRIad8VTfoWZBm3873jeIg4L%2BMl6gWxmm%2BhX6ut2a8%2Bs7W5YOHPiG6KiuCXvN2nL3%2Fxz8qUxhEWqeQnSf3dXTwwaoq6wN%2FyRCJ7tYTu2cjLD5W0tQkHjseG2AX02taxn7z3S2JwB3xraxyGoVGqLL80gaOBVf0XVtYsBRUw7y0IaQIIZp4fXh4mWFe%2B7sf%2Bz8KCxMgv4SHgIoNAhwpRsk1R7eQLifPTeopfPClvRT7nW4dHG%2BST%2B4wJT2Fy1JzzrIksWzUxwJNPVJKsoIf5SNYnI1Vdrv291eAWIg%3D%3D&sign_type=RSA2&timestamp=2018-07-25+15%3A03%3A24&version=1.0"}
     */

    private int status;
    private String info;
    private Object code;
    private Object type;
    private ContentBean content;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Object getCode() {
        return code;
    }

    public void setCode(Object code) {
        this.code = code;
    }

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * out_trade_no : 201807251503243770
         * alipayResponse : alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2018011901975149&biz_content=%7B%22body%22%3A%22%E6%B5%81%E9%87%8F%E5%85%85%E5%80%BC%22%2C%22out_trade_no%22%3A%22201807251503243770%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E6%B5%81%E9%87%8F%E5%85%85%E5%80%BC%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.01%22%7D&charset=utf-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2F58.246.120.86%3A50080%2Frent%2Frest%2FalipayNotify&sign=odO7FIMCB%2FR3cz8tSy%2F0qBWO1GFS1tU4IncDCkyKqRZaQ71M1ZRIad8VTfoWZBm3873jeIg4L%2BMl6gWxmm%2BhX6ut2a8%2Bs7W5YOHPiG6KiuCXvN2nL3%2Fxz8qUxhEWqeQnSf3dXTwwaoq6wN%2FyRCJ7tYTu2cjLD5W0tQkHjseG2AX02taxn7z3S2JwB3xraxyGoVGqLL80gaOBVf0XVtYsBRUw7y0IaQIIZp4fXh4mWFe%2B7sf%2Bz8KCxMgv4SHgIoNAhwpRsk1R7eQLifPTeopfPClvRT7nW4dHG%2BST%2B4wJT2Fy1JzzrIksWzUxwJNPVJKsoIf5SNYnI1Vdrv291eAWIg%3D%3D&sign_type=RSA2&timestamp=2018-07-25+15%3A03%3A24&version=1.0
         */

        private String out_trade_no;
        private String alipayResponse;

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public String getAlipayResponse() {
            return alipayResponse;
        }

        public void setAlipayResponse(String alipayResponse) {
            this.alipayResponse = alipayResponse;
        }
    }
}
