package com.trust.demo.basis.updateapp;

/**
 * Created by Trust on 2016/7/28.
 */
public class UpdataInfoParser {
/*
    public static UpdataInfo getUpdataInfo(InputStream is) throws Exception {
        List<AppBean> beautyList =new ArrayList<AppBean>();;
        UpdataInfo info = new UpdataInfo();
        BeautyParserImpl pbp = new BeautyParserImpl();

        //装载Bean类型的链表，其内容由XML文件解析得到
        //调用pbp的parse()方法，将输入流传进去解析，返回的链表结果赋给beautyList
        beautyList = pbp.parse(is);
        Log.i("lhh","beautyList:"+beautyList.size());
        String version = null, url = null, message= null;
        for(int i = 0 ; i<beautyList.size() ; i++)
        {
            version = beautyList.get(i).getVersion();
            url = beautyList.get(i).getUrl();
            message = beautyList.get(i).getDescription();
        }
        Log.i("lhh","version:"+version+"url:"+url+"或message"+message);
        if(!version.equals(null) )
        {
            info.setVersion(version);
        }
        if(!url.equals(null))
        {
            info.setUrl(url);
        }
        if (!message.equals(null)) {
            info.setDescription(message);
        }


        return info;

    }

*/
}
