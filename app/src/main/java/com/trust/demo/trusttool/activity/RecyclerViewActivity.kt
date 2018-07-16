package com.trust.demo.trusttool.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import com.trust.demo.basis.base.TrustMVPActivtiy
import com.trust.demo.basis.base.presenter.TrustPresenters
import com.trust.demo.basis.base.veiw.TrustView
import com.trust.demo.trusttool.R
import com.trust.demo.trusttool.mvptest.LoginView
import kotlinx.android.synthetic.main.activity_recycler_view.*

class RecyclerViewActivity : TrustMVPActivtiy<TrustView,TrustPresenters<TrustView>>(), TrustView {
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
        val intent = Intent()
        intent.putExtra("test","我是返回得数据")
        setResult(5,intent)
        finish()
    }

    override fun createPresenter(): TrustPresenters<TrustView> {
        return object :TrustPresenters<TrustView>(){

        }
    }
}
