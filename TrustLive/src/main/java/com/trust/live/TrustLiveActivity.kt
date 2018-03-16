package com.trust.live

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.ksyun.media.streamer.capture.CameraCapture
import com.ksyun.media.streamer.kit.KSYStreamer
import com.trust.live.activity.BaseCameraActivity
import com.trust.live.fragment.*
import kotlinx.android.synthetic.main.activity_base_camera.*
import kotlinx.android.synthetic.main.activity_trust_live.*

class TrustLiveActivity : AppCompatActivity() {
    private var mTrustBaseFragment : TrustBaseFragment? = null
    var mStreamer:KSYStreamer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trust_live)
        init()
        trust_live_start_live_btn.setOnClickListener {
            doStart()
        }
    }

    private fun init() {
        val items = arrayOf("基础Demo", "标准Demo", "悬浮窗Demo", "纯音频推流")
        var adapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        trust_live_type_sp.adapter = adapter
        trust_live_type_sp.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    //基础Demo
                    0 -> {
                        mTrustBaseFragment = TrustBasisFragment()
                    }
                    //标准Demo
                    1 -> {
                        mTrustBaseFragment = TrustStdDemoFragment()
                    }
                    //悬浮窗Demo
                    2 -> {
                        mTrustBaseFragment = TrustFloatDemoFragment()
                    }
                    //纯音频推流
                    3 -> {
                        mTrustBaseFragment = TrustAudioDemoFragment()
                    }

                }

                fragmentManager.beginTransaction().replace(R.id.trust_live_fragment_layout,
                        mTrustBaseFragment).commit()
            }

        }





    }


    protected fun doStart() {
        if (!TextUtils.isEmpty(trust_live_start_live_btn.text)) {
            val url = trust_live_start_live_btn.text.toString()

            startActivity(Intent(this,BaseCameraActivity::class.java))

        }
    }


}
