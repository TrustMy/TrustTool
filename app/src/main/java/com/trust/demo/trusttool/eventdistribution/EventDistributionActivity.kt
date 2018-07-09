package com.trust.demo.trusttool.eventdistribution

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.trust.demo.trusttool.R
import kotlinx.android.synthetic.main.activity_event_distribution.*

class EventDistributionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_distribution)


        layout.setOnClickListener {
            Log.d("TAG", "点击了ViewGroup")
        }

        button_1.setOnTouchListener { v, event ->
            Log.d("TAG", "执行了onTouch(), 动作是:" + event.getAction()+"|返回了：false")
             false
        }

        button_1.setOnClickListener { Log.d("TAG", "点击了button1"); }

        button_2.setOnTouchListener { v, event ->
            Log.d("TAG", "执行了onTouch(), 动作是:" + event.getAction()+"|返回了：true")
            true
        }

        button_2.setOnClickListener { Log.d("TAG", "点击了button2"); }
    }






}
