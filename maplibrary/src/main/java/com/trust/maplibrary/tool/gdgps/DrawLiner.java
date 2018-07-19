package com.trust.maplibrary.tool.gdgps;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.trust.maplibrary.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trust on 2017/6/5.
 */

public class DrawLiner implements Serializable {

    private List<BitmapDescriptor> texTuresList;
    private static final int START = 0;
    private static final int END = 1;
    private String startName,endName;
    private Context context;
//    private CarLoationMessage carLoationMessage;
    public DrawLiner(Context context) {
        this.context = context;
        init();
    }

    private void  init(){
        texTuresList = new ArrayList<BitmapDescriptor>();
        texTuresList.add(BitmapDescriptorFactory.fromResource(R.drawable.ic_launchers));

    }


    public void draw(AMap aMap, List<LatLng> latLngs, String startName , String endName){
        Amap = aMap;
        this.startName = startName;
        this.endName = endName;
        //线段是指定的图片
//        aMap.addPolyline(new PolylineOptions().addAll(latLngs).width(30).color(Color.parseColor("#020176")).
//                setCustomTextureList(texTuresList));
        aMap.addPolyline(new PolylineOptions().
                addAll(latLngs).width(10).color(Color.BLUE));


        if(latLngs.size() != 0){
            addResIcon(latLngs.get(0),"起点", R.drawable.start,"",START);
//            addResIcon(latLngs.get(latLngs.size()-1),"终点",R.mipmap.ic_launcher,"",END);   addResIcon(latLngs.get(0),"起点", R.mipmap.ic_launcher,"",START);
            addResIcon(latLngs.get(latLngs.size()-1),"终点",R.drawable.end,"",END);
            centerMaker(aMap, latLngs.get(0).latitude,latLngs.get(0).longitude);

        }
    }

    private void centerMaker(AMap aMap, double lat, double lng) {
        aMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                new LatLng(lat, lng),//新的中心点坐标
                500, //新的缩放级别
                0, //俯仰角0°~45°（垂直与地图时为0）
                0  ////偏航角 0~360° (正北方为0)
        )));
    }

    private String titleMessage, bodyMessage;
    View infoWindow = null;
    AMap Amap;
    public void addResIcon(LatLng latLng, String title, int Icon, String msg, int types) {

        Amap.setOnMarkerClickListener(onMarkerClickListener);
        Amap.setInfoWindowAdapter(new AMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                if (infoWindow == null) {
                    infoWindow = LayoutInflater.from(context).inflate(
                            R.layout.info_window, null);
                }
                TextView titleTv = (TextView) infoWindow.findViewById(R.id.info_window_title);
                TextView msgTv = (TextView) infoWindow.findViewById(R.id.info_window_msg);
                titleTv.setText(titleMessage);
                msgTv.setText(bodyMessage);

                return infoWindow;
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });

        Marker marker = Amap.addMarker(new MarkerOptions().
                position(latLng).
                title(title).
                snippet(msg).icon(BitmapDescriptorFactory.
                fromBitmap(BitmapFactory.decodeResource(context.getResources(), Icon))));
        marker.setObject(types);


    }



    public AMap.OnMarkerClickListener onMarkerClickListener = new AMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            String path = marker.getId();
            String newPath = path.substring(6);
            int a = Integer.parseInt(newPath);

            if (marker.getObject().equals(START)) {
                titleMessage = "起点";
                bodyMessage = startName;
            } else if (marker.getObject().equals(END)) {
                titleMessage = "终点";
                bodyMessage = endName;
            }
            marker.showInfoWindow(); // 显示改点对应 的infowindow
            return true;
        }
    };


    public void drawTrickLine(AMap aMap, List<LatLng> latLngs , long time){
        aMap.clear();
        aMap.addPolyline(new PolylineOptions().addAll(latLngs).width(30).color(Color.parseColor("#020176")).
                setCustomTextureList(texTuresList));
        Maker.showMaker(aMap,latLngs.get(latLngs.size()-1));
//        Maker.showMakerGif(aMap,carLoationMessage = new CarLoationMessage(latLngs.get(latLngs.size()-1).latitude,latLngs.get(latLngs.size()-1).longitude
//        ,time,"asd",0),false,4000);
    }

    public static float getDistance(LatLng latLng1 , LatLng latLng2){
        return AMapUtils.calculateLineDistance(latLng1, latLng2);
    }
}
