package com.trust.demo.basis.updateapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by Trust on 2016/7/28.
 */
public class DownLoadManager {
    public static File getFileFromServer(String path, ProgressDialog pd,Context context) throws Exception {
        //如果相等的话表示当前的sdcard挂载在手机上并且是可用的

        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){

            HttpsURLConnection
                    .setDefaultSSLSocketFactory(getSSLSocketFactory(context,""));


            URL url = new URL(path);

            HttpURLConnection conn =  (HttpURLConnection) url.openConnection();

            HttpsURLConnection https = (HttpsURLConnection) conn;
            https.setHostnameVerifier(DO_NOT_VERIFY);
            conn = https;

            conn.setConnectTimeout(5000);

            //获取到文件的大小

            pd.setMax(conn.getContentLength());

            InputStream is = conn.getInputStream();

            File file = new File(Environment.getExternalStorageDirectory(), "updata.apk");

            FileOutputStream fos = new FileOutputStream(file);

            BufferedInputStream bis = new BufferedInputStream(is);

            byte[] buffer = new byte[1024];

            int len ;

            int total=0;

            while((len =bis.read(buffer))!=-1){

                fos.write(buffer, 0, len);

                total+= len;

                //获取当前下载量

                pd.setProgress(total);

            }

            fos.close();

            bis.close();

            is.close();

            return file;

        }

        else{

            return null;

        }

    }



    public static SSLSocketFactory getSSLSocketFactory(Context context, String name)  {

        try
        {
            /*
            InputStream certificates = context.getAssets().open(name);
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;

            for (InputStream certificate : certificates)
            {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias,
                        certificateFactory.generateCertificate(certificate));

                try
                {
                    if (certificate != null){
                        certificate.close();}
                } catch (IOException e)
                {
                }
            }

*/

            //初始化keystore
            KeyStore clientKeyStore = KeyStore.getInstance("BKS");
            clientKeyStore.load(context.getAssets().open("cacerts_sy.bks"), "changeit".toCharArray());
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory
                    .getDefaultAlgorithm());

            keyManagerFactory.init(clientKeyStore, "000000".toCharArray());

            SSLContext sslContext = SSLContext.getInstance("TLS");

            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

            trustManagerFactory.init(clientKeyStore);

//
//            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers()
//                    , new SecureRandom());


            sslContext.init
                    (null,trustManagerFactory.getTrustManagers(),new SecureRandom()
                            //第一个参数是验证服务器返回 第二个参数 是请求的时候带着 服务器的证书让服务器验证的
                    );

            return sslContext.getSocketFactory();

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }


    public final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };
}
