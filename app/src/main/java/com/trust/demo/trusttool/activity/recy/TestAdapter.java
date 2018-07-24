package com.trust.demo.trusttool.activity.recy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trust.demo.basis.trust.weight.Body;
import com.trust.demo.basis.trust.weight.TrustBeseAdapter;
import com.trust.demo.trusttool.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trust on 2018/7/24.
 */

public class TestAdapter extends TrustBeseAdapter {
    public TestAdapter(Context context, List<Body> bodyList) {
        super(context, bodyList);
    }

    @NonNull
    @Override
    protected BodyViewHolder getBodyViewHolder(ViewGroup parent) {
        return new test(inflater.inflate(R.layout.item_test,parent,false));
    }


    @Override
    protected void bindBodyView(BodyViewHolder holder, int position) {
        test s = (test) holder;
        List<TestBody> ml  = (List<TestBody>) bodyList;
        s.textView.setText(ml.get(position).getName()+"\n"+ml.get(position).getOld());
    }


    class test extends BodyViewHolder{
        TextView textView;
        public test(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_test_tv);
        }
    }
}
