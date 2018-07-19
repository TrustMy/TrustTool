package com.trust.maplibrary.tool.gdgps.gpsconfig;

/**
 * Created by Trust on 2017/6/26.
 */

public class ConversionLocationAddress  {
    private String address;
    private String errAddress = "车辆坐标有误!";

/*
    private GeocodeSearch geocodeSearch;




    private PostResult.alarmLocationAddress alarmCallBack;

    public ConversionLocationAddress(List<AlarmBean.ContentBean.AlarmsBean> ml , PostResult.alarmLocationAddress alarmCallBack) {
        this.ml = ml;
        this.alarmCallBack = alarmCallBack;
    }

    @Override
    public void run() {
        Looper.prepare();
        init();
        Looper.loop();
    }
    public void init(){
        geocodeSearch =  new GeocodeSearch(Config.context);
        geocodeSearch.setOnGeocodeSearchListener(onGeocodeSearchListener);

        batchConversion(ml);
    }



    private GeocodeSearch.OnGeocodeSearchListener   onGeocodeSearchListener = new GeocodeSearch.OnGeocodeSearchListener() {
        @Override
        public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
            L.d("地理解析返回码:"+i);
        }

        @Override
        public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

        }
    };


    private void batchConversion(List<AlarmBean.ContentBean.AlarmsBean> ml) {
        List<LatLonPoint> addressList = new ArrayList<>();
        for (AlarmBean.ContentBean.AlarmsBean bean :ml){
            addressList.add(new LatLonPoint(bean.getLat(),bean.getLng()));
        }

        for (LatLonPoint point :addressList){
            try {
                RegeocodeQuery query = new RegeocodeQuery(point, 200,
                        GeocodeSearch.GPS);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
                RegeocodeAddress result = geocodeSearch.getFromLocation(query);// 设置同步逆地理编码请求
                if(result != null && result.getFormatAddress() != null && !result.getFormatAddress().equals("")){

                    String address = result.getFormatAddress()+"附近";

                    alarmAddressList.add(new AlarmLocationAddressBean(address));
                }else{
                    alarmAddressList.add(new AlarmLocationAddressBean(errAddress));
                }

            } catch (AMapException e) {
                e.printStackTrace();
                L.e(e.toString());
                alarmAddressList.add(new AlarmLocationAddressBean(errAddress));
            }
        }

        handler.sendEmptyMessage(1);

    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            alarmCallBack.addressCallBack(alarmAddressList);
        }
    };

    */
}
