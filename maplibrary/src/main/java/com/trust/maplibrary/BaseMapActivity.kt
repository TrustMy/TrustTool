package com.trust.maplibrary

import com.amap.api.maps.AMap
import com.trust.demo.basis.base.TrustMVPActivtiy
import com.trust.demo.basis.base.delegate.TrustMvpCallback
import com.trust.demo.basis.base.presenter.TrustPresenters
import com.trust.demo.basis.base.veiw.TrustView
import android.os.Bundle
import com.amap.api.maps.MapView
import com.trust.maplibrary.tool.gdgps.CoordinateTransformation


/**
 * Created by Trust on 2018/7/18.
 *
 */
abstract class BaseMapActivity <V : TrustView,P : TrustPresenters<V>>:TrustMVPActivtiy<V,P>(),TrustView , TrustMvpCallback<V, P> {
    protected var aMap:AMap? = null
    protected var mMapView:MapView? = null
    protected var coordinateTransformation: CoordinateTransformation?=null

    override fun onStart() {
        super.onStart()
        coordinateTransformation = CoordinateTransformation(mContext)
    }

    override fun onDestroy() {
        super.onDestroy()
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView?.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView?.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView?.onSaveInstanceState(outState)
    }
}