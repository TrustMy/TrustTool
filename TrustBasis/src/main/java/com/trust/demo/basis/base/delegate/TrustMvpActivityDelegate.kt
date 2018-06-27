package com.trust.demo.basis.base.delegate

import android.os.Bundle
import com.trust.demo.basis.base.presenter.TrustPresenters
import com.trust.demo.basis.base.veiw.TrustView

/**
 * Created by Trust on 2018/6/27.
 * activity声明周期代理  双重代理
 * 第一重代理 ： 目标接口
 */
interface TrustMvpActivityDelegate <V : TrustView , P :TrustPresenters<V>>{

    fun onCreate(savedInstanceState:Bundle?)

    fun onStart()

    fun onRestart()

    fun onResume()

    fun onPause()

    fun onStop()

    fun onDestroy()

}