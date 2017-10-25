package com.trust.demo.trustlibrary.updateapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;

/**
 * Created by Trust on 2016/8/29.
 * 检查APK更新
 */
public class CheckVersionTask implements Runnable {
    private Context context;

    private InputStream is;


    private final int UPDATA_NONEED = 0;

    private final int UPDATA_CLIENT = 1;

    private final int GET_UNDATAINFO_ERROR = 2;//

    private final int SDCARD_NOMOUNTED = 3;

    private final int DOWN_ERROR = 4;

    private UpdataInfo info;

    private String localVersion;

    public CheckVersionTask(Context context , String localVersion , UpdataInfo info ) {
        this.context = context;
        this.localVersion = localVersion;
        this.info = info;
    }

    @Override
    public void run()
    {

//        try {
//            info = UpdataInfoParser.getUpdataInfo(is);
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }

        if (!info.getVersion().equals(localVersion)) {
                Message msg = new Message();
                msg.what = UPDATA_CLIENT;
                UpHandler.sendMessage(msg);
            }
    }

    Handler UpHandler = new Handler() {

        @Override

        public void handleMessage(Message msg) {

            super.handleMessage(msg);

            switch (msg.what) {

                case UPDATA_NONEED:

                    Toast.makeText(context.getApplicationContext(), "不需要更新",

                            Toast.LENGTH_SHORT).show();

                    break;

                case UPDATA_CLIENT:

                    //对话框通知用户升级程序

                    showUpdataDialog();

                    break;

                case GET_UNDATAINFO_ERROR:

                    //服务器超时

                    Toast.makeText(context.getApplicationContext(), "获取服务器更新信息失败", Toast.LENGTH_LONG).show();

                    break;

                case DOWN_ERROR:

                    //下载apk失败
                    Toast.makeText(context.getApplicationContext(), "下载新版本失败", Toast.LENGTH_LONG).show();

                    break;

            }

        }

    };



    /*

	 *

	 * 弹出对话框通知用户更新程序

	 *

	 * 弹出对话框的步骤：

	 *  1.创建alertDialog的builder.

	 *  2.要给builder设置属性, 对话框的内容,样式,按钮

	 *  3.通过builder 创建一个对话框

	 *  4.对话框show()出来

	 */

    protected void showUpdataDialog()
    {

        AlertDialog.Builder builer = new AlertDialog.Builder(context);

        builer.setTitle("版本更新");

        builer.setMessage(info.getDescription());

        //当点确定按钮时从服务器上下载 新的apk 然后安装   װ

        builer.setPositiveButton("确定", new DialogInterface.OnClickListener()
        {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                downLoadApk();

            }

        });

        builer.setNegativeButton("取消", new DialogInterface.OnClickListener()
        {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // TODO Auto-generated method stub

                //do sth
            }

        });

        AlertDialog dialog = builer.create();
        dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        /*
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK)
                {
                    T.showToast(context,"请升级!");
                        return true;

                }
                return false;
            }
        });
        */
        dialog.show();

    }



    /*

    * 从服务器中下载APK

    */
    protected void downLoadApk()
    {

        final ProgressDialog pd;    //进度条对话框

        pd = new ProgressDialog(context);

        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        pd.setMessage("正在下载更新");

        pd.show();

        new Thread()
        {

            @Override

            public void run() {

                try {

                    File file = DownLoadManager.getFileFromServer(info.getUrl(), pd);

                    sleep(3000);

                    installApk(file);

                    pd.dismiss(); //结束掉进度条对话框

                }
                catch (Exception e)
                {

                    Message msg = new Message();

                    msg.what = DOWN_ERROR;

                    UpHandler.sendMessage(msg);

                    e.printStackTrace();

                }

            }}.start();

    }




    //安装apk

    protected void installApk(File file) {
        Intent intent = new Intent();
        //执行动作
        intent.setAction(Intent.ACTION_VIEW);

        if (Build.VERSION.SDK_INT >= 24) {
            Uri apkUri =
                    FileProvider.getUriForFile(context, "com.trust.shengyu.rentalcarclient.fileprovider", file);
            // 由于没有在Activity环境下启动Activity,设置下面的标签
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        }else{
            //执行的数据类型
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }




}
