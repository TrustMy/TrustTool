package com.trust.maplibrary.tool.gdgps;

import android.content.Context;

import com.amap.api.maps.CoordinateConverter;
import com.amap.api.maps.model.LatLng;

/**
 * Created by Trust on 16/12/28.
 */
public class CoordinateTransformation {
    private Context context;


    public CoordinateTransformation(Context context)
    {
        this.context = context.getApplicationContext();

    }


    public LatLng transformation  (LatLng sourceLatLng)
    {
        CoordinateConverter converter  = new CoordinateConverter(context);
// CoordType.GPS 待转换坐标类型
        converter.from(CoordinateConverter.CoordType.GPS);
// sourceLatLng待转换坐标点 LatLng类型
        converter.coord(sourceLatLng);
// 执行转换操作
        LatLng desLatLng = converter.convert();
        return desLatLng;
    }
}
