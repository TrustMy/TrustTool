package com.trust.demo.trusttool;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Trust on 2017/11/2.
 */

public class TestClasz {
    private static final String TAG = "lhh";
    public TestInterface testInterface = new TestInterface() {
        @Override
        public void toIntent(String msg , Context context) {
            Log.d(TAG, "toIntent: "+msg);
            Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
        }
    };

    public TestInterface getTestInterface(){
        return testInterface;
    }

}
