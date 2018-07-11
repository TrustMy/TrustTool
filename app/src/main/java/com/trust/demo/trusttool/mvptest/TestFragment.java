package com.trust.demo.trusttool.mvptest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.trust.demo.basis.base.TrustMVPFragment;
import com.trust.demo.basis.trust.utils.TrustLogUtils;
import com.trust.demo.trusttool.R;
import com.trust.demo.trusttool.activity.RecyclerViewActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Trust on 2018/6/26.
 */

public class TestFragment extends TrustMVPFragment<LoginView,LoginPresenter> implements LoginView{
    @Override
    protected int getLayoutId() {
        return R.layout.test_fragment;
    }

    @Override
    protected void initView(@NotNull View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.test_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().login("我是fragmnet","11111");
            }
        });
    }

    @Override
    protected void initData() {
        startActivityResult(getActivity(), RecyclerViewActivity.class,1);
    }

    @Override
    public void logingStatus(String status) {
        Toast.makeText(getContext(),status,Toast.LENGTH_SHORT).show();
        TrustLogUtils.d("这是优化后的 来自fragment"+status);
    }

    @NotNull
    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void showWaitDialog(@Nullable String msg, boolean isShow, @Nullable String tag) {
        TrustLogUtils.d("showWaitDialog:"+isShow+tag);
    }

    @Override
    public void diassDialog() {
        TrustLogUtils.d("diassDialog:");
    }

    @Override
    public void showToast(@NotNull String msg) {
        TrustLogUtils.d("showToast:"+msg);
    }

    @Override
    public void onTrustViewActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        TrustLogUtils.d("onTrustViewActivityResult:"+requestCode+"|resu"+resultCode+"|数据"+data.getStringExtra("test"));
    }
}
