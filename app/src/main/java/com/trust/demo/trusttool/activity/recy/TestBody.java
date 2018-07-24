package com.trust.demo.trusttool.activity.recy;

import com.trust.demo.basis.trust.weight.Body;

/**
 * Created by Trust on 2018/7/24.
 */

public class TestBody extends Body {
    private String name;
    private String old;

    public TestBody(String name, String old) {
        this.name = name;
        this.old = old;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOld() {
        return old;
    }

    public void setOld(String old) {
        this.old = old;
    }
}
