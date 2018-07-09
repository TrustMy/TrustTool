package com.trust.demo.trusttool.activity

import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.trust.demo.basis.trust.utils.TrustLogUtils
import com.trust.demo.trusttool.R
import kotlinx.android.synthetic.main.activity_tbs.view.*
import java.util.*

/**
 * Created by Trust on 2018/7/6.
 */
class RecyclerAdapter:RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(),ItemTouchHelperAdapter{
    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        Collections.swap(list,fromPosition,toPosition)
        notifyItemMoved(fromPosition,toPosition)
    }

    override fun onItemDissmiss(toPosition: Int) {
        TrustLogUtils.d("list!!.size:"+list!!+"|postion:"+toPosition)
        list!!.remove(toPosition)
        notifyItemRemoved(toPosition)
    }

    private var list:ArrayList<Int>? = null

    fun setList(list: ArrayList<Int>?)  {
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerAdapter.ViewHolder {
        val s: View = LayoutInflater.from(parent!!.context).inflate(R.layout.item_test,null,false)
        s.setBackgroundColor(Color.RED)
        return ViewHolder(s)
    }
    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder?, position: Int) {
        holder!!.textView!!.text = "this is test this is test this is test this is test:"+list!![position].toString()
    }




    inner class ViewHolder(internal var view: View) : RecyclerView.ViewHolder(view){
        var textView:TextView? = null
        init {
            textView =  view.findViewById<TextView>(R.id.item_test_tv)
        }
    }
}