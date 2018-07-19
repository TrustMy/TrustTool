package com.trust.maplibrary.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.amap.api.maps.AMap
import com.amap.api.maps.model.LatLng
import com.trust.demo.basis.trust.utils.TrustAppUtils
import com.trust.demo.basis.trust.utils.TrustStringUtils
import com.trust.maplibrary.BaseMapActivity
import com.trust.maplibrary.R
import com.trust.maplibrary.bean.TrajectoryBean
import com.trust.maplibrary.ipresenter.ITrajectoryPresenter
import com.trust.maplibrary.iview.ITrajectoryView
import com.trust.maplibrary.tool.gdgps.DrawLiner
import com.trust.statusbarlibrary.TrustStatusBarUtils
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_map_trajectory.*

@Route(path = "/map/activity/trajectory")
class MapTrajectoryActivity : BaseMapActivity<ITrajectoryView, ITrajectoryPresenter>(),ITrajectoryView {
    private var startTime:Long = 0
    private var endTime:Long = 0
    private var phone:String? = null
    override fun createPresenter(): ITrajectoryPresenter {
        return ITrajectoryPresenter()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_map_trajectory
    }

    override fun initView(savedInstanceState: Bundle?) {
        setStatusBar(Color.BLACK)
        mv_trajectory.onCreate(savedInstanceState)
        aMap = mv_trajectory.map
        aMap!!.mapType = AMap.MAP_TYPE_NORMAL
        mMapView = mv_trajectory
        baseSetOnClick(btn_map_trajectory_update,0)
    }

    override fun initData() {
        startTime =intent.getLongExtra("startTime",0)
        endTime =intent.getLongExtra("endTime",0)
        phone =intent.getStringExtra("phone")
        getGpsDate()
    }

    private fun getGpsDate() {
        if (!TrustStringUtils.isEmpty(phone) && startTime != 0L && endTime != 0L) {
            val map = hashMapOf<String, Any>()
            map["cellPhone"] = phone!!
            map["startTime"] = startTime
            map["endTime"] = endTime
            getPresenter()?.getGpsData(map)
        }else{
            showToast(TrustAppUtils.getResourcesString(this,R.string.error_time))
        }
    }

    override fun baseResultOnClick(v: View) {
        getGpsDate()
    }


    override fun showWaitDialog(msg: String?, isShow: Boolean, tag: String?) {
    }

    override fun diassDialog() {
    }

    override fun showToast(msg: String) {
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show()
    }

    override fun onTrustViewActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }

    override fun resultSuccess(msg: String) {
    }

    override fun resultError(msg: String) {
        showToast(msg)
    }

    override fun resultGpsData(bean: TrajectoryBean) {
        Observable.create(ObservableOnSubscribe<List<LatLng>> { e ->
            val ml = ArrayList<LatLng>()

            for (gpsBean in bean.content) {
                if (gpsBean.lat !== 0.0) {
                    ml.add(coordinateTransformation!!.transformation(LatLng(gpsBean.lat, gpsBean.lng)))
                }
            }
            e.onNext(ml)
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer <List<LatLng>>{
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(ml: List<LatLng>) {
                        aMap?.clear()
                        DrawLiner(mContext).draw(aMap!!,ml,"开始","结束")
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {

                    }
                })
    }
}
