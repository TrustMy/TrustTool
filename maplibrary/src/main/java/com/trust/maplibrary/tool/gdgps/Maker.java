package com.trust.maplibrary.tool.gdgps;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Created by Trust on 2017/5/16.
 */

public class Maker {

    public static void showMaker (AMap map, LatLng data , String title, String msg){
        map.addMarker(new MarkerOptions().
                position(data).
                title(title).snippet(msg)).showInfoWindow();

        map.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                new LatLng(data.latitude,data.longitude),//新的中心点坐标
                500, //新的缩放级别
                0, //俯仰角0°~45°（垂直与地图时为0）
                0  ////偏航角 0~360° (正北方为0)
        )));
    }

    public static void showMaker (AMap map, LatLng data , String title, String msg, int ic){
        map.addMarker(new MarkerOptions().
                position(data).icon(BitmapDescriptorFactory.fromResource(ic)).
                title(title).snippet(msg)).showInfoWindow();

        map.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                new LatLng(data.latitude,data.longitude),//新的中心点坐标
                500, //新的缩放级别
                0, //俯仰角0°~45°（垂直与地图时为0）
                0  ////偏航角 0~360° (正北方为0)
        )));
    }

    public static void clearMap(AMap map){
        if (map != null) {
            map.clear();
        }
    }

    public static void showMaker (AMap map, LatLng data , String msg, int ic){
        map.addMarker(new MarkerOptions().
                position(data).icon(BitmapDescriptorFactory.fromResource(ic)).
                snippet(msg)).showInfoWindow();

        map.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                new LatLng(data.latitude,data.longitude),//新的中心点坐标
                500, //新的缩放级别
                0, //俯仰角0°~45°（垂直与地图时为0）
                0  ////偏航角 0~360° (正北方为0)
        )));
    }

    public static void showMaker (AMap map, LatLng data , int ic){
        map.addMarker(new MarkerOptions().
                position(data).icon(BitmapDescriptorFactory.fromResource(ic))).showInfoWindow();

        map.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                new LatLng(data.latitude,data.longitude),//新的中心点坐标
                500, //新的缩放级别
                0, //俯仰角0°~45°（垂直与地图时为0）
                0  ////偏航角 0~360° (正北方为0)
        )));
    }




    public static void showMaker(AMap map, LatLng data){
        map.addMarker(new MarkerOptions().
                position(data));
        map.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                new LatLng(data.latitude,data.longitude),//新的中心点坐标
                500, //新的缩放级别
                0, //俯仰角0°~45°（垂直与地图时为0）
                0  ////偏航角 0~360° (正北方为0)
        )));
    }

    /**
     * 移动视角中心
     * @param map
     *
     */
    public  static void mobileMarker(AMap map, double lat, double lon){
        map.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                new LatLng(lat,lon),//新的中心点坐标
                16, //新的缩放级别
                0, //俯仰角0°~45°（垂直与地图时为0）
                0  ////偏航角 0~360° (正北方为0)
        )));
    }
    public static void showMakerGif(AMap aMap, LatLng data , int [] imgs , int size){

        MarkerOptions markerOptions = new MarkerOptions();
        ArrayList<BitmapDescriptor> ml = new ArrayList<>();


        for (int i = 0; i < imgs.length; i++) {
            ml.add(BitmapDescriptorFactory.fromResource(imgs[i]));
        }

        markerOptions.icons(ml);
        markerOptions.position(data);
        markerOptions.period(3);

        aMap.addMarker(markerOptions).showInfoWindow();
        aMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                new LatLng(data.latitude,data.longitude),//新的中心点坐标
                size, //新的缩放级别
                0, //俯仰角0°~45°（垂直与地图时为0）
                0  ////偏航角 0~360° (正北方为0)
        )));
    }
}
