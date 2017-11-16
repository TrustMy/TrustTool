package com.trust.demo.basis.updateapp;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trust on 2016/8/16.
 */
public class BeautyParserImpl implements  IBeautyParser
{
    @Override
    public List<AppBean> parse(InputStream is) throws Exception {
        List<AppBean> mList = null;
        AppBean beauty = null;

        // 由android.util.Xml创建一个XmlPullParser实例
        XmlPullParser xpp = Xml.newPullParser();
        // 设置输入流 并指明编码方式
        xpp.setInput(is,"UTF-8");
        // 产生第一个事件
        int eventType = xpp.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT){
            switch (eventType) {
                // 判断当前事件是否为文档开始事件
                case XmlPullParser.START_DOCUMENT:
                    mList = new ArrayList<AppBean>(); // 初始化books集合
                    break;
                // 判断当前事件是否为标签元素开始事件
                case XmlPullParser.START_TAG:
                    if (xpp.getName().equals("beauty")) { // 判断开始标签元素是否是book
                        beauty = new AppBean();
                    } else if (xpp.getName().equals("version")) {
                        eventType = xpp.next();//让解析器指向name属性的值
                        // 得到name标签的属性值，并设置beauty的name
                        beauty.setVersion(xpp.getText());
                    } else if (xpp.getName().equals("url")) { // 判断开始标签元素是否是book
                        eventType = xpp.next();//让解析器指向age属性的值
                        // 得到age标签的属性值，并设置beauty的age
                        beauty.setUrl(xpp.getText());
                    }else if (xpp.getName().equals("description")) { // 判断开始标签元素是否是book
                        eventType = xpp.next();//让解析器指向age属性的值
                        // 得到age标签的属性值，并设置beauty的age
                        beauty.setDescription(xpp.getText());
                    }
                    break;

                // 判断当前事件是否为标签元素结束事件
                case XmlPullParser.END_TAG:
                    if (xpp.getName().equals("beauty")) { // 判断结束标签元素是否是book
                        mList.add(beauty); // 将book添加到books集合
                        beauty = null;
                    }
                    break;
            }
            // 进入下一个元素并触发相应事件
            eventType = xpp.next();
        }
        return mList;

    }


    @Override
    public String serialize(List<AppBean> beauties) throws Exception {

        return null;
    }




}
