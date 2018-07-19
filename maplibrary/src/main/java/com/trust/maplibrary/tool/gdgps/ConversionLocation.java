package com.trust.maplibrary.tool.gdgps;

import android.content.Context;
import android.util.Log;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;


/**
 * Created by Trust on 2017/5/19.
 */

public class ConversionLocation implements GeocodeSearch.OnGeocodeSearchListener {
    private GeocodeSearch geocoderSearch;
    public static final int CITY = 0,ADDRESS = 2;
    public static final int USER_LOCATION = 0,CAR_LOCATION = 2;
    private int mTag = ADDRESS;
    public ConversionLocation(Context context) {
        geocoderSearch = new GeocodeSearch(context.getApplicationContext());
        geocoderSearch.setOnGeocodeSearchListener(this);
    }

    /**
     * 响应逆地理编码
     */
    public void getAddress(final LatLonPoint latLonPoint) {
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200,
                GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        geocoderSearch.getFromLocationAsyn(query);// 设置异步逆地理编码请求
    }

    public void getAddress(final LatLonPoint latLonPoint, int tag){
        this.mTag = tag;
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200,
                GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        geocoderSearch.getFromLocationAsyn(query);// 设置异步逆地理编码请求
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
                String addressName =null;
                if (mTag == CITY) {
                    addressName = result.getRegeocodeAddress().getCity();
                }else if(mTag == USER_LOCATION || mTag == CAR_LOCATION){
                    addressName = result.getRegeocodeAddress().getProvince() +
                            result.getRegeocodeAddress().getDistrict();
                    if (!result.getRegeocodeAddress().getAois().isEmpty()) {
                    addressName  = result.getRegeocodeAddress().getAois().get(0).getAoiName()+ "附近";
                    }else{
                        addressName = addressName+"附近";
                    }

                }
                Log.e("lhh","addressName:"+result.getRegeocodeAddress().getFormatAddress());
                addressListener.getAddress(true,addressName,mTag);
            } else {
                addressListener.getAddress(false,null,mTag);
                Log.e("lhh","没有搜索到");
            }
        } else {
            addressListener.getAddress(false,null,mTag);
            Log.e("lhh","错误吗:"+rCode);
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    public void setAddressListener(ConversionLocationAddressListener conversionLocationAddressListener){
        this.addressListener = conversionLocationAddressListener;
    }


    public interface ConversionLocationAddressListener{
        void getAddress(boolean status, String msg, int type);
        void getAddressList(boolean status, TripAddress tripAddress);
    }

    public ConversionLocationAddressListener addressListener;

    public class TripAddress{
        private String startName,endName;

        public TripAddress(String startName, String endName) {
            this.startName = startName;
            this.endName = endName;
        }

        public String getStartName() {
            return startName;
        }

        public void setStartName(String startName) {
            this.startName = startName;
        }

        public String getEndName() {
            return endName;
        }

        public void setEndName(String endName) {
            this.endName = endName;
        }
    }
}
