package com.trust.demo.trusttool.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.trust.demo.basis.base.TrustMVPActivtiy
import com.trust.demo.basis.base.presenter.TrustPresenters
import com.trust.demo.basis.base.veiw.TrustView
import com.trust.demo.trusttool.R
import com.trust.demo.trusttool.mvptest.LoginView
import kotlinx.android.synthetic.main.activity_recycler_view.*

class RecyclerViewActivity : TrustMVPActivtiy<TrustView,TrustPresenters<TrustView>>(), TrustView {
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
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        test_recyclerview.layoutManager = linearLayoutManager
        test_recyclerview.adapter = recyclerAdapter


        ItemTouchHelper(RecyclerTest(recyclerAdapter)).attachToRecyclerView(test_recyclerview)
    }

    override fun initData() {
        var list:ArrayList<Int> = arrayListOf()
        for (x in 0..20){
            list.add(x)
        }
        recyclerAdapter.setMl(list)
        recyclerAdapter.notifyDataSetChanged()
    }

    override fun createPresenter(): TrustPresenters<TrustView> {
        return object :TrustPresenters<TrustView>(){

        }
    }
}
