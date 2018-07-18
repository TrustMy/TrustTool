package com.trust.maplibrary.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.amap.api.maps.AMap
import com.trust.maplibrary.BaseMapActivity
import com.trust.maplibrary.R
import com.trust.maplibrary.ipresenter.ITrajectoryPresenter
import com.trust.maplibrary.iview.ITrajectoryView
import kotlinx.android.synthetic.main.activity_map_trajectory.*

@Route(path = "/map/activity/trajectory")
class MapTrajectoryActivity : BaseMapActivity<ITrajectoryView, ITrajectoryPresenter>(),ITrajectoryView {

    override fun createPresenter(): ITrajectoryPresenter {
        return ITrajectoryPresenter()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_map_trajectory
    }

    override fun initView(savedInstanceState: Bundle?) {
        mv_trajectory.onCreate(savedInstanceState)
        aMap = mv_trajectory.map
        aMap!!.mapType = AMap.MAP_TYPE_NORMAL
        mMapView = mv_trajectory
    }

    override fun initData() {

    }

    override fun baseResultOnClick(v: View) {
    }


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

}
