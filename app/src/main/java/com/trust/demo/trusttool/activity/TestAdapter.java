package com.trust.demo.trusttool.activity;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trust.demo.trusttool.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by Trust on 2018/7/6.
 */

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.test>implements ItemTouchHelperAdapter {

    private List<Integer> ml;

    public void setMl(List<Integer> ml) {
        this.ml = ml;
    }

    @Override
    public test onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test, null, false);
        inflate.setBackgroundColor(Color.RED);
        return new test(inflate);
    }

    @Override
    public void onBindViewHolder(test holder, int position) {
        holder.textView.setText("this is test this is test this is test this is test:"+ml.get(position));
    }

    @Override
    public int getItemCount() {
        return ml == null?0:ml.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
         Collections.swap(ml,fromPosition,toPosition);
        notifyItemMoved(fromPosition,toPosition);
    }

    @Override
    public void onItemDissmiss(int toPosition) {
        ml.remove(toPosition);
        notifyItemRemoved(toPosition);
    }

    class test extends RecyclerView.ViewHolder{
        TextView textView;
        public test(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_test_tv);
        }
    }
}
