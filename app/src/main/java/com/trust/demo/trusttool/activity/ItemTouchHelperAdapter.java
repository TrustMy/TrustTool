package com.trust.demo.trusttool.activity;

/**
 * Created by Trust on 2018/7/6.
 */

public interface ItemTouchHelperAdapter {
    void onItemMove(int fromPosition,int toPosition);
    void onItemDissmiss(int toPosition);
}
