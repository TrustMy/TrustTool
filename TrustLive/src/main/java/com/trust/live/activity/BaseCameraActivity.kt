package com.trust.live.activity

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.google.gson.GsonBuilder
import com.ksyun.media.streamer.capture.CameraCapture
import com.ksyun.media.streamer.encoder.VideoEncodeFormat
import com.ksyun.media.streamer.kit.KSYStreamer
import com.ksyun.media.streamer.kit.StreamerConstants
import com.ksyun.media.streamer.util.StringWrapper
import com.trust.live.R
import kotlinx.android.synthetic.main.activity_base_camera.*

class BaseCameraActivity : AppCompatActivity() {
    private var TAG = "BaseCameraActivity"
    protected var mStreamer:KSYStreamer? = null
    protected var mIsLandscape: Boolean = false
    protected var mHWEncoderUnsupported: Boolean = false
    protected var mSWEncoderUnsupported: Boolean = false
    protected var mIsChronometerStarted: Boolean = false
    protected var mStreaming: Boolean = false

    protected fun  getLayoutId():Int{return R.layout.activity_base_camera}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        // 4.4以上系统，自动隐藏导航栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }


        // 创建KSYStreamer实例
        mStreamer =  KSYStreamer(this)
// 设置预览View
        mStreamer!!.setDisplayPreview(gl_surface_view);
// 设置推流url（需要向相关人员申请，测试地址并不稳定！）
        mStreamer!!.setUrl("rtmp://mobile.kscvbu.cn/live/{streamName}");
// 设置预览分辨率, 当一边为0时，SDK会根据另一边及实际预览View的尺寸进行计算
        mStreamer!!.setPreviewResolution(480, 0);
// 设置推流分辨率，可以不同于预览分辨率（不应大于预览分辨率，否则推流会有画质损失）
        mStreamer!!.setTargetResolution(480, 0);
// 设置预览帧率
        mStreamer!!.setPreviewFps(15F);
// 设置推流帧率，当预览帧率大于推流帧率时，编码模块会自动丢帧以适应设定的推流帧率
        mStreamer!!.setTargetFps(15F);
// 设置视频码率，分别为初始平均码率、最高平均码率、最低平均码率，单位为kbps，另有setVideoBitrate接口，单位为bps
        mStreamer!!.setVideoKBitrate(600, 800, 400);
// 设置音频采样率
        mStreamer!!.setAudioSampleRate(44100);
// 设置音频码率，单位为kbps，另有setAudioBitrate接口，单位为bps
        mStreamer!!.setAudioKBitrate(48);
        /**
         * 设置编码模式(软编、硬编)，请根据白名单和系统版本来设置软硬编模式，不要全部设成软编或者硬编,白名单可以联系金山云商务:
         * StreamerConstants.ENCODE_METHOD_SOFTWARE
         * StreamerConstants.ENCODE_METHOD_HARDWARE
         */
        mStreamer!!.setEncodeMethod(StreamerConstants.ENCODE_METHOD_SOFTWARE);
// 设置屏幕的旋转角度，支持 0, 90, 180, 270
        mStreamer!!.setRotateDegrees(0);
// 设置开始预览使用前置还是后置摄像头
        mStreamer!!.setCameraFacing(CameraCapture.FACING_BACK);


        mStreamer!!.setOnInfoListener { what, msg1, i ->
            Log.d("lhh","setOnInfoListener what:"+what)
        }

        mStreamer!!.setOnErrorListener { what, msg1, i ->
            Log.d("lhh","setOnErrorListener what:"+what)

        }



        start_live.setOnClickListener {
            mStreamer!!.url = "rtmp://139.196.200.160:1935/oflaDemo/abc"
            mStreamer!!.startStream()

        }

    }






    override fun onResume() {
        super.onResume()
        mStreamer!!.startCameraPreview()
        mStreamer!!.onResume()
        mStreamer!!.setUseDummyAudioCapture(false)
    }


}
