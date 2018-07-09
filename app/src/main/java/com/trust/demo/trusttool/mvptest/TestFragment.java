package com.trust.demo.trusttool.mvptest;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.trust.demo.basis.base.TrustMVPFragment;
import com.trust.demo.basis.trust.utils.TrustLogUtils;
import com.trust.demo.trusttool.R;

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

    }

    @Override
    public void diassDialog() {

    }

    @Override
    public void showToast(@NotNull String msg) {

    }
}
