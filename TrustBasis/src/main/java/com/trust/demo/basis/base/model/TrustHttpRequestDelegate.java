package com.trust.demo.basis.base.model;


import com.trust.demo.basis.trust.utils.TrustAnalysis;
import com.trust.demo.basis.trust.utils.TrustLogUtils;

/**
 * Created by Trust on 2018/6/29.
 */

public class TrustHttpRequestDelegate <M extends TrustHttpRequestModel>  {
    private TrustHttpRequestListener trustHttpRequestListener;

    public TrustHttpRequestDelegate(TrustHttpRequestListener trustHttpRequestListener) {
        this.trustHttpRequestListener = trustHttpRequestListener;
    }

    public void onCreate(){
        TrustHttpRequestModel requestModule = trustHttpRequestListener.getRequestModule();
        if (requestModule == null) {
            requestModule = trustHttpRequestListener.createRequestModule();
        }
        trustHttpRequestListener.setRequestMidule(requestModule);
    }


}
