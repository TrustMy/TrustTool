package com.trust.demo.basis.base.delegate.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trust.demo.basis.base.presenter.TrustPresenters
import com.trust.demo.basis.base.veiw.TrustView

/**
 * Created by Trust on 2018/6/27.
 * 第一重代理：代理生命周期
 * 目标接口
 */
interface TrustMvpFragmentDelegate <V : TrustView, P : TrustPresenters<V>>{

    fun onCreate(savedInstanceState:Bundle?)

    fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?)

    fun onViewCreated(view: View?, savedInstanceState: Bundle?)

    fun onStart()

    fun onResume()

    fun onPause()

    fun onStop()

    fun onDestroyView()

    fun onSaveInstanceState(outState: Bundle?)
}