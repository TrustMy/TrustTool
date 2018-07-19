package com.trust.maplibrary.tool.gdgps;

import android.app.Activity;
import android.graphics.Point;
import android.view.animation.LinearInterpolator;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.VisibleRegion;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.ScaleAnimation;
import com.amap.api.services.core.LatLonPoint;


import java.util.ArrayList;
import java.util.List;



/**
 * Created by Trust on 2017/9/19.
 */

public class AmapTool {
   /* private ConversionLocation conversionLocation;
    public static OutletsLatLong startOutlesMsgBean;//最新的一个网点信息
    private int markerIcon;
    private Marker beforMarker;//点击后 把前一个点  图标变回来
    public static int IMGS[] = {R.mipmap.ordinary_point,R.mipmap.ordinary_point,R.mipmap.ordinary_point
            ,R.mipmap.ordinary_point,R.mipmap.ordinary_point,R.mipmap.ordinary_point,R.mipmap.ordinary_point,R.mipmap.ordinary_point
            ,R.mipmap.ordinary_point,R.mipmap.ordinary_point,R.mipmap.ordinary_point,R.mipmap.ordinary_point};
    private Marker centerMarker;
    private LatLng destinationLatLng;//目标地点
    private OutletsTool outletsTool;
    private List<Marker> markerList;//网点marker的集合
    private ArrayList<MarkerOptions> growMarkerList;
    public Marker getCenterMarker() {
        return centerMarker;
    }
    private List<OutletsLatLong> outletsLatLongs = new ArrayList<>();//全部网点

    public List<OutletsLatLong> getOutletsLatLongs() {
        return outletsLatLongs;
    }



    public interface locationNameCallBackListener{
        void locationNameCallBack(boolean status, String msg);
    }
    locationNameCallBackListener callBackListener;
    public void setConversionLocation(locationNameCallBackListener callBackListener){
        this.callBackListener = callBackListener;
    }


    public AmapTool() {
        outletsTool = new OutletsTool();
        conversionLocation = new ConversionLocation();
        conversionLocation.setAddressListener(new ConversionLocation.ConversionLocationAddressListener() {
            @Override
            public void getAddress(boolean status, String msg, int type) {
                callBackListener.locationNameCallBack(status,msg);
            }

            @Override
            public void getAddressList(boolean status, ConversionLocation.TripAddress tripAddress) {

            }
        });
    }

    private STRtree stRtree;

    public STRtree getStRtree() {
        return stRtree;
    }

    public void setStRtree(STRtree stRtree) {
        this.stRtree = stRtree;
    }

    private List<OutletsLatLong> mapOutletsLatLngList = new ArrayList<>();//当前地图页面网点集合

    private float zoom;//记录缩放级别

    public List<OutletsLatLong> getMapOutletsLatLngList() {
        return mapOutletsLatLngList;
    }

    private boolean isClickMarker =false;

    public void setClickMarker(boolean clickMarker) {
        isClickMarker = clickMarker;
    }


    public void init(String city, List<OutletsLatLong> ml){
        outletsTool.init(city,ml);
    }




    *//**
     * 地图中心显示点
     * @param mMap
     *//*
    public void showCenterMaker(final Activity activity, final AMap mMap){

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMap.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
                    @Override
                    public void onMapLoaded() {
                        addMarkerInScreenCenter(mMap);
                    }
                });

                mMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
                    @Override
                    public void onCameraChange(CameraPosition cameraPosition) {

                    }

                    @Override
                    public void onCameraChangeFinish(CameraPosition cameraPosition) {
                        LatLng myLatLng = centerMarker.getPosition();
                        if (destinationLatLng != null && isClickMarker == false) {
                            float distance = DrawLiner.getDistance(destinationLatLng, myLatLng);
                            if (zoom == 0) {
                                zoom = cameraPosition.zoom;
                            }
                            L.d("distance"+distance+"|destinationLatLng:"+destinationLatLng.toString()+"|myLatLng:"
                            +myLatLng);
                            if (distance >= DISTANCE || (Math.abs(zoom - cameraPosition.zoom))>1) { //移动距离超过阀值或缩放等级不等于前一个等级 刷新网点
                                zoom = cameraPosition.zoom;
                                conversionLocation.getAddress(new LatLonPoint(myLatLng.latitude, myLatLng.longitude),
                                        ConversionLocation.CITY);

                                if (isClickMarker == false) {
                                    showOutls(mMap);
                                    destinationLatLng = centerMarker.getPosition();
                                } else {
                                    L.d("当前不能 刷新网点 因为点击了marker");
                                }

                            } else {
                                L.d("滑动距离太近");
                            }
                        }else{
                            destinationLatLng = centerMarker.getPosition();
                        }

                    }
                });
            }
        });
    }

    public LatLng getDestinationLatLng() {
        return destinationLatLng;
    }

    public void showOutls(AMap mMap) {
        List<String> fenceIdList = getFenceIdList(mMap.getProjection().getVisibleRegion(),stRtree);
        if (fenceIdList == null || fenceIdList.size() ==0) {
//                                        activity.showToast("没有找到对应的fenceId");
            L.d("fenceIdList = null || == 0");
        }else{
            mapOutletsLatLngList.clear();
            for (String fenceId : fenceIdList) {
                L.d("fenceId:"+fenceId);
                OutletsLatLong aLong =  TrustAnalysis.resultTrustBean(fenceId,OutletsLatLong.class);
                if (aLong != null) {
                    mapOutletsLatLngList.add(aLong);
                }
            }
            if ( mapOutletsLatLngList == null || mapOutletsLatLngList.size() == 0) {
//                                            activity.showToast("周围没有找到网点!");
                L.d("没有找到网点");
            }else{
                L.d("找到网点");
                if (markerList != null) {
                    for( Marker marker :markerList){
                        marker.destroy();
                    }
                    markerList.clear();
                    growMarkerList.clear();
                }

                addGrowMarker(mMap,mapOutletsLatLngList);
                destinationLatLng = centerMarker.getPosition();
            }
        }
    }


    *//**
     * 在屏幕中心添加一个Marker
     *//*
    private Marker addMarkerInScreenCenter(AMap mMap) {
        LatLng latLng = mMap.getCameraPosition().target;
        Point screenPosition = mMap.getProjection().toScreenLocation(latLng);
        MarkerOptions icon = new MarkerOptions()
                .anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.purple_pin));
        icon.zIndex(20);//调整 marker z轴的数值 大于3  marker 始终处于 图层的最上方

        Marker marker = mMap.addMarker(icon);
        marker.setObject("center");
        //设置Marker在屏幕上,不跟随地图移动
        marker.setPositionByPixels(screenPosition.x, screenPosition.y);
        centerMarker = marker;
        return marker;
    }

    public static int getIcon(int vehicleNum){
       return  vehicleNum<11?IMGS[vehicleNum]:IMGS[11];
    }

    *//**
     * 添加一个从地上生长的Marker
     *//*
    public void addGrowMarker(AMap mMap, List<OutletsLatLong> ml) {
        L.d("ml:"+ml.size());
        growMarkerList = new ArrayList<>();

        for (OutletsLatLong aLong : ml) {
            int vehicleNum = aLong.getVehicleNum();
            MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                    .icon(BitmapDescriptorFactory.fromResource(getIcon(vehicleNum)))
                    .position(aLong.getLatLng());
            markerOptions.zIndex(2);
            growMarkerList.add(markerOptions);
        }

        if (markerList != null) {
            for (Marker marker : markerList) {
                marker.destroy();
            }
        }
        markerList = mMap.addMarkers(growMarkerList, false);
        L.d("markerList:"+markerList.size());
        startGrowAnimation(mMap,markerList);
    }

    *//**
     * 地上生长的Marker
     *//*
    private void startGrowAnimation(AMap mMap, List<Marker> markerList) {

        for (int i = 0; i < markerList.size(); i++) {
            markerList.get(i).setObject(i);
            Animation animation = new ScaleAnimation(0, 1, 0, 1);
            animation.setInterpolator(new LinearInterpolator());
            //整个移动所需要的时间
            animation.setDuration(1000);
            //设置动画
            markerList.get(i).setAnimation(animation);
            //开始动画
            markerList.get(i).startAnimation();
        }


    }



    //获取网点集合
    public List<String> getFenceIdList(VisibleRegion visibleRegion, STRtree rtrees){
        LatLng nearLeft = visibleRegion.nearLeft;//左下
        LatLng farRight = visibleRegion.farRight;//右上
        L.d("nearLeft:"+nearLeft.toString()+"|farRight:"+farRight.toString());
        return outletsTool.getFenceIdList(nearLeft.longitude, nearLeft.latitude, farRight.longitude,
                farRight.latitude,rtrees);
    }


    public void setOnMarkerClickListener(AMap aMap, onRequestCallBack requestCallBack){
        this.requestCallBack = requestCallBack;
        aMap.setOnMarkerClickListener(onMarkerClickListener);
        aMap.setOnMapClickListener(onMapClickListener);
    }

    //地图点击事件
    private AMap.OnMapClickListener onMapClickListener = new AMap.OnMapClickListener(){

        @Override
        public void onMapClick(LatLng latLng) {
            restore();
        }
    };

    public void restore() {
        setClickMarker(false);//允许移动刷新网点
        if (beforMarker != null) {
            beforMarker.setIcon(BitmapDescriptorFactory.fromResource(AmapTool.getIcon(markerIcon)));
            beforMarker.setZIndex(1);
        }
    }

    //maker点击事件
    private AMap.OnMarkerClickListener onMarkerClickListener = new AMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            if (!marker.getObject().equals("center")) {
                setClickMarker(true);//禁止移动刷新网点

//                screenMarker.remove();
                if (beforMarker != null) {
                    beforMarker.setZIndex(1);
                    beforMarker.setIcon(BitmapDescriptorFactory.fromResource(AmapTool.getIcon(markerIcon)));
                }
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.four_s_shop_points));
                marker.setZIndex(1);
                beforMarker = marker;

                if (mapOutletsLatLngList != null && mapOutletsLatLngList.size() != 0) {
                    startOutlesMsgBean = mapOutletsLatLngList.get((Integer) marker.getObject());
                    markerIcon = startOutlesMsgBean.getVehicleNum();
                    requestCallBack.requestCallBack(startOutlesMsgBean.getWebsiteNodeId());
                }

            }
            return false;
        }
    };


    public interface onRequestCallBack{void requestCallBack(String id);};
    onRequestCallBack requestCallBack;*/
}
