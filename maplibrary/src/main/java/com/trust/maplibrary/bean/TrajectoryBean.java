package com.trust.maplibrary.bean;

import java.util.List;

/**
 * Created by Trust on 2017/11/17.
 */

public class TrajectoryBean {

    /**
     * status : 1
     * info : gps查询成功
     * code : 700019
     * content : [{"terminalId":0,"gpsTime":"Nov 17, 2017 11:21:23 AM","lat":29.653485,"lng":106.55761},{"terminalId":0,"gpsTime":"Nov 17, 2017 12:21:23 PM","lat":29.653485,"lng":106.55761},{"terminalId":0,"gpsTime":"Nov 17, 2017 1:21:23 PM","lat":29.653485,"lng":106.55761},{"terminalId":0,"gpsTime":"Nov 17, 2017 1:46:32 PM","lat":29.653485,"lng":106.55761},{"terminalId":0,"gpsTime":"Nov 17, 2017 1:46:32 PM","lat":29.653485,"lng":106.55761}]
     */

    private int status;
    private String info;
    private String code;
    private List<ContentBean> content;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * terminalId : 0
         * gpsTime : Nov 17, 2017 11:21:23 AM
         * lat : 29.653485
         * lng : 106.55761
         */

        private String gpsTime;
        private double lat;
        private double lng;



        public String getGpsTime() {
            return gpsTime;
        }

        public void setGpsTime(String gpsTime) {
            this.gpsTime = gpsTime;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }
    }
}
