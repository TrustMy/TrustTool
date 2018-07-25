package com.trust.maplibrary.ui.activity

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.amap.api.location.AMapLocation
import com.amap.api.maps.AMap
import com.amap.api.maps.model.LatLng
import com.amap.api.services.core.LatLonPoint
//import com.dn.tim.lib_permission.annotation.Permission
import com.trust.maplibrary.BaseMapActivity
import com.trust.maplibrary.R
import com.trust.maplibrary.ipresenter.IRoutePlanPresenter
import com.trust.maplibrary.iview.IRoutePlanView
import com.trust.maplibrary.tool.gdgps.Maker
import com.trust.maplibrary.tool.gdgps.Positioning
import com.trust.maplibrary.tool.gdgps.routeplan.RoutePlan
import kotlinx.android.synthetic.main.activity_maproute_plan.*

@Route(path = "/map/activity/route_plan")
class MapRoutePlanActivity : BaseMapActivity<IRoutePlanView,IRoutePlanPresenter>(),IRoutePlanView {
    private var mapType:Int = 1
    private var routePlanType = 0
    //单次定位
    private var positioning:Positioning? = null

    override fun showWaitDialog(msg: String?, isShow: Boolean, tag: String?) {

    }

    override fun diassDialog() {
    }

    override fun showToast(msg: String) {
    }

    override fun onTrustViewActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }

    override fun resultSuccess(msg: String) {
    }

    override fun resultError(msg: String) {
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_maproute_plan
    }

    override fun initView(savedInstanceState: Bundle?) {
        mv_route_plan.onCreate(savedInstanceState)
        aMap = mv_route_plan.map
        aMap!!.mapType = AMap.MAP_TYPE_NORMAL
        mMapView = mv_route_plan

        baseSetOnClick(btn_route_plan_map_type,0)
        baseSetOnClick(btn_route_plan_positioning,0)
        baseSetOnClick(btn_route_planning,0)
    }

    override fun initData() {
        positioning = Positioning(this)
        positioning!!.setPostitioningListener ( object :Positioning.PositioningListener{
            override fun onLocationChanged(aMapLocation: AMapLocation?) {
                if (aMapLocation != null) {
                    if (aMapLocation.errorCode == 0) {
                        aMap!!.clear()
                       Maker.showMaker(aMap,LatLng(aMapLocation.latitude,aMapLocation.longitude))
                    }else{

                    }
                }
            }

            override fun onLocationError(aMapLocation: AMapLocation?) {

            }

        })
    }

    override fun baseResultOnClick(v: View) {
        when (v.id) {
            R.id.btn_route_plan_map_type -> {
                changeMapType()
            }
            R.id.btn_route_plan_positioning -> {
                positioning()
            }
            R.id.btn_route_planning->{
                routePlan()
            }
        }
    }

    private fun routePlan() {
        val routePlan = RoutePlan(aMap, this)

        if (routePlanType ==routePlan.WALK_TYPE) {
            routePlanType = routePlan.DRIVER_TYPE
        }else{
            routePlanType =routePlan.WALK_TYPE
        }

        routePlan.searchRouteResult(routePlanType, LatLonPoint(31.229413,121.416629),
                LatLonPoint(31.287843,121.307121),true)
    }


    /**
     * 单次定位
     */

//    @Permission(Manifest.permission.ACCESS_FINE_LOCATION)
    private fun positioning() {
        positioning!!.startGps()
    }

    private fun changeMapType() {
        if (mapType ==1) {
            mapType++
        }else{
            mapType =1
        }
        aMap!!.mapType = mapType
    }

    override fun createPresenter(): IRoutePlanPresenter {
        return IRoutePlanPresenter()
    }
}
