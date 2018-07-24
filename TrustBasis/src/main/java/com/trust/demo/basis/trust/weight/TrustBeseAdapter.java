package com.trust.demo.basis.trust.weight;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.trust.demo.basis.R;

import java.util.List;

import static com.trust.demo.basis.trust.weight.TrustRecyclerView.SHOW_FOOT_COUNT;


/**
 * @author Administrator
 */
public class TrustBeseAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected final LayoutInflater inflater;
    //数据
    protected List<?> bodyList;

    RecyclerView.ViewHolder holder;

    public TrustBeseAdapter(Context context, List<?> bodyList) {
        this.bodyList = bodyList;
        inflater = LayoutInflater.from(context);

    }

    private static final int TYPE_BODY = 1;
    private static final int TYPE_FOOT = 2;
    private static final int TYPE_EMPTY = 3;

    @Override
    public int getItemViewType(int position) {
        int viewType = -1;

     if (position == bodyList.size() && bodyList.size()>= SHOW_FOOT_COUNT ) {
            viewType = TYPE_FOOT;
        }
        else if(bodyList.size() == 0){
         viewType = TYPE_EMPTY;
        }
        else {
            viewType = TYPE_BODY;
        }
        return viewType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case TYPE_BODY:
                holder = getBodyViewHolder(parent);
                break;
            case TYPE_FOOT:
                holder = new FootViewHolder(inflater.inflate(R.layout.item_foot, parent, false));
                break;
            case TYPE_EMPTY:
                holder = new EmptyViewHolder(inflater.inflate(R.layout.item_empty,parent,false));
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof BodyViewHolder) {
            bindBodyView((BodyViewHolder) holder,position);
        }else if(holder instanceof EmptyViewHolder){
            bindEmptyView((EmptyViewHolder)holder,position);
        } else{
            bindFootView((FootViewHolder) holder,position);
        }
    }

    @NonNull
    protected BodyViewHolder getBodyViewHolder(ViewGroup parent) {
        return new MyBodyHolder(inflater.inflate(R.layout.item_body, parent, false));
    }



    protected void bindEmptyView(EmptyViewHolder holder, int position) {
    }


    //数据绑定
    protected void bindBodyView(BodyViewHolder holder, final int position) {
        /*MyBodyHolder myBodyHolder = (MyBodyHolder) holder;
        Body body = bodyList.get(position);
        myBodyHolder.tvBody.setText(body.getName()+"     hello1     "+body.getAge());*/
    }


    protected void bindFootView(FootViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        if (bodyList.size() == 0) {
            //没有数据
            return 1;
        }else if (bodyList.size() >= SHOW_FOOT_COUNT){
            return bodyList.size() + 1;
        }
        return bodyList.size();
    }




    protected abstract class BodyViewHolder extends RecyclerView.ViewHolder {
        public BodyViewHolder(View itemView) {
            super(itemView);

        }
    }


    protected class FootViewHolder extends RecyclerView.ViewHolder {

        public FootViewHolder(View itemView) {
            super(itemView);
        }
    }


    protected class EmptyViewHolder extends RecyclerView.ViewHolder {

        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class MyBodyHolder extends BodyViewHolder{
        private final TextView tvBody;

        public MyBodyHolder(View itemView) {
            super(itemView);
            tvBody = (TextView) itemView.findViewById(R.id.tv_body);
        }
    }

}
