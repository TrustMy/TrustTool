package com.trust.demo.trusttool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.trust.demo.trusttool.tbs.TBSView;

import java.io.File;


public class TBSActivity extends AppCompatActivity {
    private TBSView tbsView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tbs);

        tbsView = (TBSView)findViewById(R.id.tbs_view);
        tbsView.show();

        File file = new File("/storage/emulated/0/test.txt");
        tbsView.displayFile(file);
    }






}
