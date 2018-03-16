package com.trust.live.fragment

import android.app.Fragment
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by Trust on 2018/3/16.
 */
abstract class TrustBaseFragment :Fragment(){

    abstract fun getLayoutId():Int
    abstract fun start(url:String)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var view = inflater!!.inflate(getLayoutId(),container,false)
        return view
    }
}