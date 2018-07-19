package com.trust.maplibrary.tool.gdgps.routeplan;

import android.content.Context;
import android.util.Log;

import com.amap.api.maps.AMap;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.trust.demo.basis.trust.utils.TrustLogUtils;
import com.trust.maplibrary.tool.gdgps.gpsconfig.WalkRouteOverlay;
import com.trust.maplibrary.tool.gdgps.overlay.DrivingRouteOverlay;
import com.trust.maplibrary.tool.gdgps.util.AMapUtil;


/**
 * Created by Trust on 2017/8/3.
 */

public class RoutePlan implements RouteSearch.OnRouteSearchListener {
    private RouteSearch mRouteSearch;
    private DriveRouteResult mDriveRouteResult;
    private AMap mAmap;
    private Context mContext ;
    public RoutePlan(AMap aMap , Context context) {
        this.mContext = context.getApplicationContext();
        mRouteSearch = new RouteSearch(mContext);
        mRouteSearch.setRouteSearchListener(this);
        mAmap = aMap;
    }
    public DrivingRouteOverlay drivingRouteOverlay;

    public final int DRIVER_TYPE = 1;//驾车
    public final int WALK_TYPE = 0;//不行
    private boolean showAmap = true;
    /**
     * 开始搜索路径规划方案
     */
    public void searchRouteResult(int Type, LatLonPoint mStartPoint , LatLonPoint mEndPoint , boolean isShowAmap) {
        showAmap = isShowAmap;
        if (mStartPoint == null) {
//            ToastUtil.show(mContext, "定位中，稍后再试...");
            return;
        }
        if (mEndPoint == null) {
//            ToastUtil.show(mContext, "终点未设置");
        }
        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                mStartPoint, mEndPoint);

        switch (Type) {
            case DRIVER_TYPE:
                RouteSearch.DriveRouteQuery queryDriver = new RouteSearch.DriveRouteQuery(fromAndTo,
                        RouteSearch.DRIVING_SINGLE_DEFAULT, null,
                        null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
                mRouteSearch.calculateDriveRouteAsyn(queryDriver);// 异步路径规划驾车模式查询
                break;
            case WALK_TYPE:
                RouteSearch.WalkRouteQuery queryWalk=
                        new RouteSearch.WalkRouteQuery(fromAndTo);
                mRouteSearch.calculateWalkRouteAsyn(queryWalk);// 异步路径规划步行模式查询
                break;
        }




    }

    @Override
    public void onBusRouteSearched(BusRouteResult result, int errorCode) {

    }

    public interface onRouteSearchedCallBack{void CallBack(String time, String monny, String distance
    );};
    onRouteSearchedCallBack routeSearchedCallBack;
    public void setmRouteSearch(onRouteSearchedCallBack routeSearchedCallBack){
        this.routeSearchedCallBack = routeSearchedCallBack;
    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult result, int errorCode) {
        if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getPaths() != null) {
                if (result.getPaths().size() > 0) {
                    mDriveRouteResult = result;
                    final DrivePath drivePath = mDriveRouteResult.getPaths()
                            .get(0);
                    if (showAmap) {


                    mAmap.clear();


                    drivingRouteOverlay = new DrivingRouteOverlay(
                            mContext.getApplicationContext(), mAmap, drivePath,
                            mDriveRouteResult.getStartPos(),
                            mDriveRouteResult.getTargetPos(), null);
                    drivingRouteOverlay.setNodeIconVisibility(false);//设置节点marker是否显示
                    drivingRouteOverlay.setIsColorfulline(false);//是否用颜色展示交通拥堵情况，默认true
                    drivingRouteOverlay.removeFromMap();
                    drivingRouteOverlay.addToMap();
                    drivingRouteOverlay.zoomToSpan();



                    }

                    int dis = (int) drivePath.getDistance();
                    int dur = (int) drivePath.getDuration();
                    String des = AMapUtil.getFriendlyTime(dur)+"("+AMapUtil.getFriendlyLength(dis)+")";
                    String time = AMapUtil.getFriendlyTime(dur);
                    String distance = AMapUtil.getFriendlyLength(dis);
                    double taxiCost = (int) mDriveRouteResult.getTaxiCost();
                    TrustLogUtils.d("打车需要:"+taxiCost+"元");
                    if (routePlanListener != null) {
                        routePlanListener.result(taxiCost);
                    }

                    if (routeSearchedCallBack != null) {
                        routeSearchedCallBack.CallBack(time,taxiCost+"",distance);
                    }
                } else if (result != null && result.getPaths() == null) {
//                    ToastUtil.show(mContext, R.string.no_result);
                }

            } else {
//                ToastUtil.show(mContext, R.string.no_result);
            }
        } else {
//            ToastUtil.showerror(this.getApplicationContext(), errorCode);
        }
    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {
        boolean isSuccess = false;
        String info = null;
        if (i == 3003) {
            Log.d("lhh","距离车辆位置过长!");
            info = "距离车辆位置过长!";
        }else if(i ==3001)
        {
            Log.d("lhh","距离车辆位置过长!");
            info= "距离车辆位置过长!";
        }else if(i == 3002)
        {
            Log.d("lhh","路线计算失败!");
            info = "路线计算失败!";
        }else if(i == 3000)
        {
            Log.d("lhh","不在中国陆地范围内!");
            info = "不在中国陆地范围内!";
        }else
        {
            isSuccess = true;
            WalkPath walkPath = walkRouteResult.getPaths().get(0);

            WalkRouteOverlay walkRouteOverlay = new WalkRouteOverlay(mContext,
                    mAmap, walkPath, walkRouteResult.getStartPos(),
                    walkRouteResult.getTargetPos());

            walkRouteOverlay.removeFromMap();
            walkRouteOverlay.addToMap();
            walkRouteOverlay.zoomToSpan();

            int distance = (int)walkPath.getDistance();
            int duration = (int)walkPath.getDuration();

            String des = AMapUtil.getFriendlyTime(duration)+"("+AMapUtil.getFriendlyLength(distance)+")";
            TrustLogUtils.d("时间:"+des+"|duration:"+duration);
        }

        if (routePlanListener != null) {
            routePlanListener.walkCallBack(isSuccess,info);
        }


    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }


    public interface onRoutePlanListener{
        void result(Object money);
        void walkCallBack(boolean status, String msg);
    }

    private onRoutePlanListener routePlanListener;

    public void setOnRoutePlanListener(onRoutePlanListener routePlanListener){
        this.routePlanListener =routePlanListener;
    }
}
