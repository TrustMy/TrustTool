package com.trust.demo.basis.trust;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.trust.demo.basis.trust.utils.TrustLogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Trust on 2017/8/7.
 * 基础工具类
 */

public class TrustTools<T extends View> implements Serializable {
    private static  TrustTools trustTools;
    private  static String configPackName;
    public static  TrustTools create(String packName){
        configPackName = packName;
        if (trustTools == null) {
            trustTools = new TrustTools();
        }
        return trustTools;
    }

    /**
     * 倒计时显示
     */
    public  void  Countdown(Activity activity, final T value , final int time, final String msg){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Observable.interval(0,1, TimeUnit.SECONDS).take(time +1).map(new Function<Long, Object>() {
                    @Override
                    public Object apply(@NonNull Long aLong) throws Exception {

                        return time -aLong;
                    }
                }).subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(@NonNull Disposable disposable) throws Exception {
                                value.setEnabled(false);//不可点击
                            }
                        }).observeOn(AndroidSchedulers.mainThread())//操作UI主要在UI线程
                        .subscribe(new Observer<Object>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@NonNull Object o) {
                                checkT(value,o+"秒");
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }

                            @Override
                            public void onComplete() {
                                value.setEnabled(true);//不可点击
                                checkT(value,msg);
                            }
                        });
            }
        });
    }

    private void checkT(T v,String msg){
        if(v instanceof Button){
            ((Button)v).setText(msg);
        }else if(v instanceof TextView){
            ((TextView)v).setText(msg);
        }
    }

    /**
     * 自定义时间时间倒计时
     * @param time
     */
    public  TrustTools setCountdown( int time){
        Observable
                .fromArray(100)
                .subscribeOn(Schedulers.io())
                .timer(time*1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new ObservableTransformer<Object, Object>() {

                    @Override
                    public ObservableSource<Object> apply(Observable<Object> upstream) {
                        return upstream.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                })
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        countdownCallBack.callBackCountDown();
                    }
                });

        return this;
    }


    public interface CountdownCallBack{
        void callBackCountDown();
    }
    public CountdownCallBack countdownCallBack;

    public void setCountdownCallBack(CountdownCallBack countdownCallBack){
        this.countdownCallBack = countdownCallBack;
    }


    //-----------------------时间--------------------------------------

    /**
     * 获取系统时间
     * @return
     */
    public static String getSystemTimeString(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateTime = new Date(System.currentTimeMillis());//获取当前时间
        String systemTime = formatter.format(dateTime);
        return systemTime;
    }

    /**
     * 获取系统时间 data
     * @return
     */
    public static long getSystemTimeData(){
        return System.currentTimeMillis();
    }

    /**
     * 根据指定的时间 获取long
     * @param format
     * @return
     */
    public static long getTime( String format) {
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(format).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getTime(Date time){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str=sdf.format(time);
        return str;
    }

    public static String getTime(Date time , String pattern){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf=new SimpleDateFormat(pattern);
        String str=sdf.format(time);
        return str;
    }

    private static final long ONE_MINUTE = 60;
    public static int getMinute(long time){
        int day = (int) (time / (24 * 60 * 60 * 1000));
        int hour = (int) (time / (60 * 60 * 1000) - day * 24);
        int mine = (int) ((time / (60 * 1000)) - day * 24 * 60 - hour * 60);
        int seconds = (int) (time / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - mine * 60);

        return mine;
    }

    /**
     * 省略小数点后几位
     * @param v  小数
     * @param scale  几位
     *
     */
    public   static   double   round(double v,int   scale){
        if(scale<0){
            throw   new IllegalArgumentException(
                    "The   scale   must   be   a   positive   integer   or   zero");
        }
        BigDecimal b   =   new BigDecimal(Double.toString(v));
        BigDecimal one   =   new BigDecimal("1");
        return   b.divide(one,scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 获取app当前版本
     * @param context
     * @return
     * @throws PackageManager.NameNotFoundException
     */
    public static String resultAppVersion(Context context)  {

        try {
            PackageManager packageManager = context.getPackageManager();

            PackageInfo packInfo = null;
            packInfo = packageManager.getPackageInfo(context.getPackageName(),
                    0);
            return packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    //-------------------获取相册里面的相片------------------------------------------------------------------

    /**
     * 得到图片的bitmap
     * @param data
     * @param context
     * @return
     */
    public Bitmap getImages(Intent data , Context context){
        Bitmap bitmap = null;
        if(Build.VERSION.SDK_INT >=19){
            //4.4以上
            bitmap =  handlerImageOnKikat(data,context);
        }else{
            //4.4一下
            bitmap =  handlerImageBeforeKiKat(data ,context);
        }

        return bitmap;
    }

    /**
     * 4.4以上获取
     * @param data
     * @param context
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private Bitmap handlerImageOnKikat(Intent data , Context context){
        String imagePath = null;
        Uri url = data.getData();
        if(DocumentsContract.isDocumentUri(context,url)){
            //如果是document类型的url,通过document ID 处理
            String docId = DocumentsContract.getDocumentId(url);
            if("com.android.providers.media.documents".equals(url.getAuthority())){
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection
                        ,context);
            }else if("com.android.providers.downloads.documents".equals(url.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null,context);
            }

        }else if("content".equalsIgnoreCase(url.getScheme())){
            //如果是content类型Uri 使用普通方式
            imagePath = getImagePath(url,null,context);
        } else if ("file".equalsIgnoreCase(url.getScheme())) {
            //如果是file类型的uri 直接获取图片路径
            imagePath = url.getPath();
        }
        return displayImage(imagePath);
    }

    /**
     * 4.4一下获取
     * @param data
     * @param context
     * @return
     */
    private Bitmap handlerImageBeforeKiKat(Intent data , Context context){
        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null ,context);
        return displayImage(imagePath);
    }



    private String getImagePath(Uri uri, String selection , Context context){
        String path = null;
        //通过Uri和selection 获取真实图片路径
        Cursor cursor = context.getContentResolver().query(uri,null,selection,null,null);
        if (cursor != null) {
            if (cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return  path;
    }

    private Bitmap displayImage(String imagePath) {
        if (imagePath == null) {
            TrustLogUtils.e("找不到这个文件!");
            return null;
        }else{

            return   bitmapCompressionRotate(imagePath);
        }
    }

    //-----------------------图片压缩--------------------------------------------------------------

    public static int BASE64_FLAGS = Base64.NO_WRAP;
    /**
     * 图片转成string
     *
     * @param bitmap
     * @return
     */
    public static String convertIconToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] appicon = baos.toByteArray();// 转为byte数组
        return Base64.encodeToString(appicon,BASE64_FLAGS );
    }


    /**
     * 图片转成string
     *
     * @param map
     * @return
     */
    public static void convertIconToString(final Map<String,Bitmap> map , final onBitmapStringCallBack bitmapStringCallBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
               Map<String,String> newMap = new WeakHashMap<String, String>();
                for (String key : map.keySet()) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
                    Bitmap bitmap = map.get(key);
                    int num = bitmap.getByteCount();

                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] appicon = baos.toByteArray();// 转为byte数组
                    newMap.put(key, Base64.encodeToString(appicon, BASE64_FLAGS));
                }
                bitmapStringCallBack.CallBack(newMap);
            }
        }).start();
    }
    public interface onBitmapStringCallBack{void CallBack(Object objectList);}


    /**
     * 图片转成string
     *
     * @return
     */
    public static void convertIconToString(final List<Bitmap> bitmapList, final onBitmapStringCallBack bitmapStringCallBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> newBitmapList = new ArrayList<String>();
                for (Bitmap bitmap : bitmapList) {
                    if (bitmap != null) {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                        byte[] appicon = baos.toByteArray();// 转为byte数组
                        newBitmapList.add(Base64.encodeToString(appicon, BASE64_FLAGS));
                    }
                }

                bitmapStringCallBack.CallBack(newBitmapList);
            }
        }).start();
    }




    /**
     * string转成bitmap
     *
     * @param st
     */
    public static Bitmap convertStringToIcon(String st) {
        // OutputStream out;
        Bitmap bitmap = null;
        try {
            // out = new FileOutputStream("/sdcard/aa.jpg");
            byte[] bitmapArray;
            bitmapArray = Base64.decode(st, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                            bitmapArray.length);
            // bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            return bitmap;
        }
        catch (Exception e) {
            return null;
        }
    }

    public static byte[] convertStringToByte(String img){
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(img, BASE64_FLAGS);
            return bitmapArray;
        }catch (Exception e) {
            return null;
        }
    }

    /**
     *
     * @param bitmap
     * @return
     */
    public static Bitmap bitmapCompression(Bitmap bitmap){
        // 尺寸压缩倍数,值越大，图片尺寸越小
        int ratio = 2;
        // 压缩Bitmap到对应尺寸
        Bitmap result = Bitmap.createBitmap(bitmap.getWidth() / ratio, bitmap.getHeight() / ratio, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        Rect rect = new Rect(0, 0, bitmap.getWidth() / ratio, bitmap.getHeight() / ratio);
        canvas.drawBitmap(bitmap, null, rect, null);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 把压缩后的数据存放到baos中
        result.compress(Bitmap.CompressFormat.JPEG, 100 ,baos);
        TrustLogUtils.d("bitmap:"+(bitmap.getByteCount() / 1024 )+"KB"
                +"|bitmap2 大小:"+(result.getByteCount() / 1024 )+"KB");
        return result;
    }

    /**
     * bitmap 压缩,旋转,保存
     * @param fileName
     * @param filePath
     * @return
     */
    public static Bitmap bitmapCompressionRotate(String fileName, String filePath){
        try{
            int quality = 80;
            Bitmap bitmap = null;
            //
            File f = new File(filePath,fileName + ".jpg");
            int rotate = 0;
            try {
                ExifInterface exifInterface = new ExifInterface(filePath);
                int result = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
                switch(result) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        rotate = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        rotate = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        rotate = 270;
                        break;
                    default:
                        rotate =  0;
                        break;
                }
            } catch (IOException e) {

                e.printStackTrace();
                TrustLogUtils.e("读取img 错误:"+e.toString());

            }
            TrustLogUtils.d("rotate :"+rotate);
            // 1:compress bitmap
            try {
                BitmapFactory.Options o = new BitmapFactory.Options();
                o.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(new FileInputStream(f), null, o);
                int width_tmp = o.outWidth, height_tmp = o.outHeight;
                int scale = 1;
                while (true) {
                    int VARIETY_SIZE = (rotate==90 || rotate==270)?height_tmp:width_tmp;
                    if (VARIETY_SIZE / 2 <= 600){
                        if(VARIETY_SIZE>600 && VARIETY_SIZE-600>300){
                        }else{
                            break;
                        }
                    }
                    width_tmp /= 2;
                    height_tmp /= 2;
                    scale *= 2;
                }
                // decode with inSampleSize
                BitmapFactory.Options o2 = new BitmapFactory.Options();
                o2.inSampleSize = scale;
                bitmap =  BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
                int max = width_tmp>height_tmp?width_tmp:height_tmp;
                int min = width_tmp>height_tmp?height_tmp:width_tmp;
                int value = (int)((float)max*10/min);
                if(value>15){
                    quality = 80;
                }else{
                    quality = 90;
                }
            } catch (FileNotFoundException e) {
                System.gc();
            } catch(OutOfMemoryError e){
                System.gc();
                e.printStackTrace();
            }
            // 2:rotate bitmap
            if(f.exists()){
//                f.delete();
            }

            if(rotate>0){
                Matrix mtx = new Matrix();
                mtx.postRotate(rotate);
                try{
                    Bitmap roateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),bitmap.getHeight(), mtx, true);
                    if(roateBitmap!=null && roateBitmap!=bitmap){
                        bitmap.recycle();
                        bitmap = null;
                        System.gc();
                        bitmap = roateBitmap;
                    }
                }catch(OutOfMemoryError e){
                    System.gc();
                    e.printStackTrace();
                }
            }




            /*
            压缩后的bitmap  保存到指定的路径
            // 3:save bitmap
            FileOutputStream fileOutputStream = new FileOutputStream(f.getPath());
            BufferedOutputStream os = new BufferedOutputStream(fileOutputStream);
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, os);
            bitmap.recycle();
            os.flush();
            os.close();
            */

            System.gc();
            return bitmap;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }




    /**
     * bitmap 压缩,旋转,保存

     * @return
     */
    public Bitmap bitmapCompressionRotate(String path){
        try{
            InputStream inputStream= new FileInputStream(new File(path));
            return getBitmap(path);
        }catch(FileNotFoundException e){
            TrustLogUtils.d("找不到文件使用url查找:");
            e.printStackTrace();
                return getBitmap();
//            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }


    @Nullable
    private Bitmap getBitmap(String path) {
        int quality = 80;
        Bitmap bitmap = null;
        //
        File f = new File(path);
        int rotate = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
            }
        }catch (FileNotFoundException e){
            TrustLogUtils.e("没有找到文件");
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }


        // 1:compress bitmap
        try {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                int VARIETY_SIZE = (rotate==90 || rotate==270)?height_tmp:width_tmp;
                if (VARIETY_SIZE / 2 <= 600){
                    if(VARIETY_SIZE>600 && VARIETY_SIZE-600>300){
                    }else{
                        break;
                    }
                }
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }
            // decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            bitmap =  BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
            int max = width_tmp>height_tmp?width_tmp:height_tmp;
            int min = width_tmp>height_tmp?height_tmp:width_tmp;
            int value = (int)((float)max*10/min);
            if(value>15){
                quality = 80;
            }else{
                quality = 90;
            }
        } catch (FileNotFoundException e) {
            TrustLogUtils.e("FileNotFoundException:"+e.toString());
            System.gc();
        } catch(OutOfMemoryError e){
            System.gc();
            e.printStackTrace();
        }
        // 2:rotate bitmap
        if(f.exists()){
//                f.delete();
        }
        if(rotate>0){
            Matrix mtx = new Matrix();
            mtx.postRotate(rotate);
            try{
                Bitmap roateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),bitmap.getHeight(), mtx, true);
                if(roateBitmap!=null && roateBitmap!=bitmap){
                    bitmap.recycle();
                    bitmap = null;
                    System.gc();
                    bitmap = roateBitmap;
                }
            }catch(OutOfMemoryError e){
                System.gc();
                e.printStackTrace();
            }
        }


            /*
            压缩后的bitmap  保存到指定的路径
            // 3:save bitmap
            FileOutputStream fileOutputStream = new FileOutputStream(f.getPath());
            BufferedOutputStream os = new BufferedOutputStream(fileOutputStream);
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, os);
            bitmap.recycle();
            os.flush();
            os.close();
            */

        System.gc();
        return bitmap;
    }

    public InputStream inputStream;//小米手机拍摄路径不对 获取不到图片是用流来读

    public void setInputStream(InputStream inputStream) {
        try {
            trustTools.baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) > -1 ) {
                trustTools.baos.write(buffer, 0, len);
            }
            trustTools.baos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public InputStream getInputStream() {
        return new ByteArrayInputStream(trustTools.baos.toByteArray());
    }

    public ByteArrayOutputStream baos ;//缓存流
    public Bitmap getBitmap(){
        Bitmap bitmap = null;
        int rotate = 0;
        int quality = 80;
        try {

            ExifInterface exifInterface = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                exifInterface = new ExifInterface(trustTools.getInputStream());
            }
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
            }
        }catch (FileNotFoundException e){
            TrustLogUtils.e("ExifInterface 没有找到文件");
            System.gc();
        }
        catch (IOException e) {
            e.printStackTrace();
            System.gc();
        }


        //

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        BitmapFactory.decodeStream(trustTools.getInputStream(),null,options);

        int width_tmp = options.outWidth, height_tmp = options.outHeight;
        int scale = 1;
        while (true) {
            int VARIETY_SIZE = (rotate==90 || rotate==270)?height_tmp:width_tmp;
            if (VARIETY_SIZE / 2 <= 600){
                if(VARIETY_SIZE>600 && VARIETY_SIZE-600>300){
                }else{
                    break;
                }
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }
        // decode with inSampleSize
        BitmapFactory.Options options2 = new BitmapFactory.Options();
        options2.inSampleSize = scale;
        options2.inPreferredConfig = Bitmap.Config.RGB_565;
        bitmap =  BitmapFactory.decodeStream(trustTools.getInputStream(), null, options2);
        int max = width_tmp>height_tmp?width_tmp:height_tmp;
        int min = width_tmp>height_tmp?height_tmp:width_tmp;
        int value = (int)((float)max*10/min);
        if(value>15){
            quality = 80;
        }else{
            quality = 90;
        }

        if(rotate>0){
            Matrix mtx = new Matrix();
            mtx.postRotate(rotate);
            try{
                Bitmap roateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),bitmap.getHeight(), mtx, true);
                if(roateBitmap!=null && roateBitmap!=bitmap){
                    bitmap.recycle();
                    bitmap = null;
                    System.gc();
                    bitmap = roateBitmap;
                }
            }catch(OutOfMemoryError e){
                System.gc();
                e.printStackTrace();
            }
        }


        System.gc();
        return bitmap;
    }



    //--------------------------调用色相头------------------------------------------------------------------
    public static final int TAKE_PHOtO = 1;
    public Uri imageUri;
    public  String openCamera(Activity activity, int requestCode){

        //创建File 对象，用于存储拍照后的图片
        File outputImage = new File(activity.getExternalCacheDir(), TrustTools.getSystemTimeData()+".jpg");

        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");

        if (Build.VERSION.SDK_INT >= 24) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            if (configPackName != null) {
                trustTools.imageUri = FileProvider.getUriForFile(activity, configPackName+".fileprovider", outputImage);
            }else{
                throw new RuntimeException("包名不能为空!");
            }
        } else {
            trustTools.imageUri = Uri.fromFile(outputImage);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT,trustTools.imageUri);
        activity.startActivityForResult(intent, requestCode);
        return trustTools.imageUri.getPath();
    }





    /**
     * 打开本地相册

     */
    public  void openAlbum(Activity activity, int requestCode) {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        activity.startActivityForResult(intent, requestCode);
    }



    //------------------------------------检查是不是json----------------------------------------------------------------------
    public static boolean checkIsJson(String value){
        try {
            new JSONObject(value);
        } catch (JSONException e) {
            return false;
        }
        return true;
    }


    //------------------------------------保存用户密码----------------------------------------------------------------------
    public final String spfTag = "UserMsg";
    public  void setUserMsg(Context context , String editorTag, String key, String value){
        SharedPreferences.Editor editdf = context.getSharedPreferences(editorTag,
                Activity.MODE_PRIVATE).edit();
        editdf.putString(key,value);
        editdf.apply();
    }

    public String getUserMsg(Context context , String editorTag, String key){
        SharedPreferences editor = context.getSharedPreferences(editorTag,
                Context.MODE_PRIVATE);
        return editor.getString(key, null);
    }




    //------------------------------------保留小数点几位----------------------------------------------------------------------

    public String conversionType(float num){
        DecimalFormat decimalFormat=new DecimalFormat("0.0");//构造方法的字符格式这里如果小数不足1位,会以0补足.
        return decimalFormat.format(num);
    }

}
