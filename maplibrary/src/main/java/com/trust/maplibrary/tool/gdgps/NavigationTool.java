package com.trust.maplibrary.tool.gdgps;

import android.content.Context;
import android.view.View;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;
import com.trust.demo.basis.trust.utils.TrustLogUtils;


/**
 * Created by Trust on 2017/10/24.
 */

public class NavigationTool implements INaviInfoCallback {
    private static NavigationTool navigationTool;

    public static NavigationTool getNavigationTool() {
        if (navigationTool == null) {
            navigationTool = new NavigationTool();
        }
        return navigationTool;
    }



    public void  startNavigation(Context context, String startName, LatLng startLocation, String endName, LatLng endLocation){
        Poi start = new Poi(startName, startLocation, "");
        Poi end = new Poi(endName, endLocation, "");
        AmapNaviPage.getInstance().showRouteActivity(context, new AmapNaviParams(start, null, end, AmapNaviType.DRIVER), this);
    }


    @Override
    public void onInitNaviFailure() {
        TrustLogUtils.d("onInitNaviFailure:");
    }

    @Override
    public void onGetNavigationText(String s) {
        TrustLogUtils.d("onGetNavigationText:"+s);
    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {
        TrustLogUtils.d("onLocationChange:");
    }

    @Override
    public void onArriveDestination(boolean b) {
        TrustLogUtils.d("onArriveDestination:");
    }

    @Override
    public void onStartNavi(int i) {
        TrustLogUtils.d("onStartNavi:");
    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {
        TrustLogUtils.d("onCalculateRouteSuccess:");
    }

    @Override
    public void onCalculateRouteFailure(int i) {
        TrustLogUtils.d("onCalculateRouteFailure:");
    }

    @Override
    public void onStopSpeaking() {
        TrustLogUtils.d("onStopSpeaking:");
    }

    @Override
    public void onReCalculateRoute(int i) {

    }

    @Override
    public void onExitPage(int i) {

    }

    @Override
    public void onStrategyChanged(int i) {

    }

    @Override
    public View getCustomNaviBottomView() {
        return null;
    }

    @Override
    public View getCustomNaviView() {
        return null;
    }

    @Override
    public void onArrivedWayPoint(int i) {

    }
}
