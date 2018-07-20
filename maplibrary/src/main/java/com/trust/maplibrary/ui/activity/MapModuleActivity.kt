package com.trust.maplibrary.ui.activity

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.trust.demo.basis.base.presenter.TrustPresenters
import com.trust.demo.basis.base.veiw.TrustView
import com.trust.maplibrary.BaseMapActivity
import com.trust.maplibrary.R
import kotlinx.android.synthetic.main.activity_map_module.*

@Route(path = "/map/controll")
class MapModuleActivity : BaseMapActivity<TrustView,TrustPresenters<TrustView>>(),TrustView {
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
        return R.layout.activity_map_module
    }

    override fun initView(savedInstanceState: Bundle?) {
        setStatusBar(Color.YELLOW)
        baseSetOnClick(btn_map_trajectory,0)
        baseSetOnClick(btn_map_route_plan,0)
        baseSetOnClick(btn_map_pay,0)
    }

    override fun initData() {
    }

    override fun baseResultOnClick(v: View) {
        when (v.id) {
            R.id.btn_map_trajectory -> {
                ARouter.getInstance()
                        .build("/map/activity/trajectory")
                        .withLong("startTime",1531277805000)
                        .withLong("endTime",1531277973000)
                        .withString("phone","15021570474")
                        .navigation()
            }
            R.id.btn_map_route_plan -> {
                ARouter.getInstance()
                        .build("/map/activity/route_plan")
                        .navigation()
            }
            R.id.btn_map_pay->{
                ARouter.getInstance()
                        .build("/pay/controll")
                        .navigation()
            }
        }
    }

    override fun createPresenter(): TrustPresenters<TrustView> {
        return object :TrustPresenters<TrustView>(){

        }
    }
}
