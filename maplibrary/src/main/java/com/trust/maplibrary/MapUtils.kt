package com.trust.maplibrary

import android.annotation.SuppressLint
import android.app.FragmentManager
import android.content.Context
import android.content.Intent
import com.trust.maplibrary.bean.LocationBean
import com.trust.maplibrary.callback.MapDialogCallBack
import com.trust.maplibrary.callback.MapUtilsCallBack
import com.trust.maplibrary.dialog.MapDialog
import java.io.File

/**
 * Created by Trust on 2018/5/23.
 *应用内部 调用外部 高德百度地图导航
 */
class MapUtils (context: Context,mapUtilsCallBack : MapUtilsCallBack){
    private var mContext:Context = context
    private var mapUtilsCallBack : MapUtilsCallBack = mapUtilsCallBack
    private var locationBean: LocationBean?=null
    private val MAP_PACKAGE_NAMES :ArrayList<String> = arrayListOf(BAI_DU_PACKAGE_NAME, GAO_DE_PACKAGE_NAME)
    private var mapPackageName:String ?= null
    private var mapStatus:Boolean = false
    private var mapDialog:MapDialog? = null
    companion object {
        @SuppressLint("StaticFieldLeak")
        private var mapUtils : MapUtils? = null
         val BAI_DU_PACKAGE_NAME:String = "com.baidu.BaiduMap"
         val GAO_DE_PACKAGE_NAME:String = "com.autonavi.minimap"
        fun getSingleton(context: Context,mapUtilsCallBack : MapUtilsCallBack): MapUtils {
            if (mapUtils == null) {
                mapUtils = MapUtils(context, mapUtilsCallBack)
            }
            return mapUtils!!
        }

    }

    fun showMapDialog(manager: FragmentManager, locationBean: LocationBean ):MapDialog{
         mapDialog = MapDialog(object : MapDialogCallBack {
            override fun dialogCallBack(type: String) {
                getSingleton(mContext, mapUtilsCallBack).startMapUtils(type, locationBean)
            }
        })
        mapDialog!!.show(manager,"dialog")
        return mapDialog as MapDialog
    }


    private fun startMapUtils(mapType:String,locationBean: LocationBean){
        mapPackageName = mapType
        this.locationBean = locationBean
//        mapUtilsCallBack!!.isInstallByread(mapStatus)

        if (locationBean.mLat!=0.0) {

        }

        when(mapPackageName){
            BAI_DU_PACKAGE_NAME ->{startBaiDuMap()}
            GAO_DE_PACKAGE_NAME ->{startGaoDeMap()}
        }
    }

    /**
     * 检查有没有安装地图
     */
    @SuppressLint("SdCardPath")
    private fun isInstallByread(packageName:String):Boolean {
        return File("/data/data/$packageName").exists()
    }

    /**
     * 开始使用高德地图
     */
    private fun startGaoDeMap(){
        if (isInstallByread(mapPackageName!!)) {
            val intent = Intent("android.intent.action.VIEW",
                    android.net.Uri.parse("androidamap://navi?sourceApplication=test&lat=" + locationBean?.mLat + "&lon=" + locationBean?.mLon + "&dev=0&style=2"))
            intent.`package` = "com.autonavi.minimap"
            mContext.startActivity(intent)
            mapUtilsCallBack.isUseSuccess(true,"正在拉起地图请稍后...")
        }else{
            mapUtilsCallBack.isUseSuccess(false,"没有安装高德地图")
        }
    }

    /**
     * 开始使用百度地图
     */
    private fun startBaiDuMap(){
        if (isInstallByread(mapPackageName!!)) {
            val intent = Intent.parseUri("intent://map/navi?location="+locationBean?.mLat+","+locationBean?.mLon +
                    "&type=TIME&src=thirdapp.navi.hndist.sydt#Intent;scheme=bdapp;" +
                    "package=com.baidu.BaiduMap;end",0)
            mContext.startActivity(intent)
            mapUtilsCallBack.isUseSuccess(true,"正在拉起地图请稍后...")
        }else{
            mapUtilsCallBack.isUseSuccess(false,"没有安装百度地图")
        }
    }

}