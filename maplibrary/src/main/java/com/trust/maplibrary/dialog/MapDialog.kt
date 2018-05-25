package com.trust.maplibrary.dialog

import android.app.DialogFragment
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trust.maplibrary.R
import android.view.Gravity
import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import android.util.DisplayMetrics
import android.widget.ImageView
import com.trust.maplibrary.MapUtils
import com.trust.maplibrary.callback.MapDialogCallBack


/**
 * Created by Trust on 2018/5/24.
 */
class MapDialog() : DialogFragment(), Parcelable {
    private var callBack:MapDialogCallBack ?= null

    @SuppressLint("ValidFragment")
    constructor(parcel: Parcel) : this() {

    }


    @SuppressLint("ValidFragment")
    constructor(callBack: MapDialogCallBack?) : this() {
        this.callBack = callBack
    }


    override fun onStart() {
        super.onStart()
        var win = dialog.window
        win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val dm = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(dm)

        val params = win.attributes
//        params.dimAmount = 0F  //设置其他地方没有阴影
        params.gravity = Gravity.BOTTOM
        // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        win.attributes = params
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var view = inflater.inflate(R.layout.dialog_layout, container)
        view.findViewById<ImageView>(R.id.dialog_gao_de_img).setOnClickListener { callBack?.dialogCallBack(MapUtils.GAO_DE_PACKAGE_NAME) }
        view.findViewById<ImageView>(R.id.dialog_bai_du_img).setOnClickListener { callBack?.dialogCallBack(MapUtils.BAI_DU_PACKAGE_NAME) }
        return view
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MapDialog> {
        override fun createFromParcel(parcel: Parcel): MapDialog {
            return MapDialog(parcel)
        }

        override fun newArray(size: Int): Array<MapDialog?> {
            return arrayOfNulls(size)
        }
    }


}