package com.trust.demo.trusttool.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.View
import com.trust.demo.basis.base.TrustMVPActivtiy
import com.trust.demo.basis.base.presenter.TrustPresenters
import com.trust.demo.basis.base.veiw.TrustView
import com.trust.demo.basis.trust.weight.Body
import com.trust.demo.basis.trust.weight.TrustBeseAdapter
import com.trust.demo.basis.trust.weight.TrustRecyclerView
import com.trust.demo.trusttool.R
import com.trust.demo.trusttool.activity.recy.TestBody
import com.trust.demo.trusttool.mvptest.LoginView
import kotlinx.android.synthetic.main.activity_recycler_view.*

class RecyclerViewActivity : TrustMVPActivtiy<TrustView,TrustPresenters<TrustView>>(), TrustView {
    val ml:ArrayList<Body> = arrayListOf()
    val handler = Handler()
    var i = 0
    override fun resultSuccess(msg: String) {
    }

    override fun resultError(msg: String) {
    }

    override fun baseResultOnClick(v: View) {
    }

    override fun onTrustViewActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }

    override fun showWaitDialog(msg: String?, isShow: Boolean, tag: String?) {
    }

    override fun diassDialog() {
    }

    override fun showToast(msg: String) {
    }

    val recyclerAdapter = TestAdapter()

    override fun getLayoutId(): Int {
        return R.layout.activity_recycler_view
    }

    override fun initView(savedInstanceState: Bundle?) {
        test_recycler.initView(null,null)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        test_recycler.setLayoutManager(linearLayoutManager)
        test_recycler.setAdapter(com.trust.demo.trusttool.activity.recy.TestAdapter(this, ml))
        test_recycler.setOnPullListener(object :TrustRecyclerView.OnPullListener{
            override fun onRefresh() {
                handler.postDelayed({
                    Log.d("lhh","刷新完成")
                    ml.add(0, TestBody("我是刷新的数据",""+(i++)))
                    test_recycler.refreshFinish()
                }, 3000)
            }

            override fun onLoadMore() {
                val mpre = arrayListOf<Body>()
                handler.postDelayed({
                    Log.d("lhh","加载完成")
                    for ( x in 0..10){
                        mpre.add(TestBody("我是加载数据",""+x))
                    }
                    ml.addAll(mpre)
                    test_recycler.loadMroeFinish()
                }, 3000)
            }

        })


    }

    override fun initData() {

    }

    override fun createPresenter(): TrustPresenters<TrustView> {
        return object :TrustPresenters<TrustView>(){

        }
    }
}
