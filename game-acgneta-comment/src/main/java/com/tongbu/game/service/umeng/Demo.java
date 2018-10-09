package com.tongbu.game.service.umeng;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tongbu.game.common.constant.UmengConstant;
import com.tongbu.game.service.umeng.android.*;
import com.tongbu.game.service.umeng.ios.*;

/**
 * @author jokin
 * @date 2018/9/20 10:23
 */
public class Demo {
    private String appkey = null;
    private String appMasterSecret = null;
    private String timestamp = null;

    public Demo(String key, String secret) {
        try {
            appkey = key;
            appMasterSecret = secret;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void sendAndroidBroadcast() throws Exception {
        AndroidBroadcast broadcast = new AndroidBroadcast(appkey, appMasterSecret);
        broadcast.setTicker("Android broadcast ticker");
        broadcast.setTitle("中文的title");
        broadcast.setText("Android broadcast text");
        broadcast.goAppAfterOpen();
        broadcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
        // TODO Set 'production_mode' to 'false' if it's a test device.
        // For how to register a test device, please see the developer doc.
        broadcast.setProductionMode();
        // Set customized fields
        broadcast.setExtraField("test", "helloworld");

        PushClient.send(broadcast);
    }

    public void sendAndroidUnicast() throws Exception {
        AndroidUnicast unicast = new AndroidUnicast(appkey, appMasterSecret);
        // TODO Set your device token
        unicast.setDeviceToken("AtRNxi94G0ZbwcUyaXSrEGOoTEvQQSgrI6VMzTQaxF10");
        unicast.setTicker("Android unicast ticker");
        unicast.setTitle("中文的title");
        unicast.setText("Android unicast text");
        unicast.goAppAfterOpen();
        unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
        // TODO Set 'production_mode' to 'false' if it's a test device.
        // For how to register a test device, please see the developer doc.
        unicast.setProductionMode();
        // Set customized fields
        // 内容
        unicast.setExtraField("content", "hello world");
        // 跳转信息
        JSONObject jsonObject = new JSONObject();
        // 0：动画 1：捏报（咨询）
        jsonObject.put("type",0);
        // 动画或者捏报（咨询）id
        jsonObject.put("id",1279);
        // 当评论时的评论ID
        jsonObject.put("commentId",2356);
        // {type:0,id:1,commentId:1}
        unicast.setExtraField("typeSource",jsonObject);

        PushClient.send(unicast);
    }

    public void sendAndroidGroupcast() throws Exception {
        AndroidGroupcast groupcast = new AndroidGroupcast(appkey, appMasterSecret);
        /*  TODO
		 *  Construct the filter condition:
		 *  "where":
		 *	{
    	 *		"and":
    	 *		[
      	 *			{"tag":"tag1"},
      	 *			{"tag":"tag2"}
    	 *		]
		 *	}
		 */
        JSONObject filterJson = new JSONObject();
        JSONObject whereJson = new JSONObject();
        JSONArray tagArray = new JSONArray();
        JSONObject tag1 = new JSONObject();
        JSONObject tag2 = new JSONObject();
        tag1.put("tag", "tag1");
        tag2.put("tag", "tag2");
        tagArray.add(tag1);
        tagArray.add(tag2);
        whereJson.put("and", tagArray);
        filterJson.put("where", whereJson);
        System.out.println(filterJson.toString());

        groupcast.setFilter(filterJson);
        groupcast.setTicker("Android groupcast ticker");
        groupcast.setTitle("中文的title");
        groupcast.setText("Android groupcast text");
        groupcast.goAppAfterOpen();
        groupcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
        // TODO Set 'production_mode' to 'false' if it's a test device.
        // For how to register a test device, please see the developer doc.
        groupcast.setProductionMode();
        PushClient.send(groupcast);
    }

    public void sendAndroidCustomizedcast() throws Exception {
        AndroidCustomizedcast customizedcast = new AndroidCustomizedcast(appkey, appMasterSecret);
        // TODO Set your alias here, and use comma to split them if there are multiple alias.
        // And if you have many alias, you can also upload a file containing these alias, then
        // use file_id to send customized notification.
        customizedcast.setAlias("alias", "alias_type");
        customizedcast.setTicker("Android customizedcast ticker");
        customizedcast.setTitle("中文的title");
        customizedcast.setText("Android customizedcast text");
        customizedcast.goAppAfterOpen();
        customizedcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
        // TODO Set 'production_mode' to 'false' if it's a test device.
        // For how to register a test device, please see the developer doc.
        customizedcast.setProductionMode();
        PushClient.send(customizedcast);
    }

    public void sendAndroidCustomizedcastFile() throws Exception {
        AndroidCustomizedcast customizedcast = new AndroidCustomizedcast(appkey, appMasterSecret);
        // TODO Set your alias here, and use comma to split them if there are multiple alias.
        // And if you have many alias, you can also upload a file containing these alias, then
        // use file_id to send customized notification.
        String fileId = PushClient.uploadContents(appkey, appMasterSecret, "aa" + "\n" + "bb" + "\n" + "alias");
        customizedcast.setFileId(fileId, "alias_type");
        customizedcast.setTicker("Android customizedcast ticker");
        customizedcast.setTitle("中文的title");
        customizedcast.setText("Android customizedcast text");
        customizedcast.goAppAfterOpen();
        customizedcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
        // TODO Set 'production_mode' to 'false' if it's a test device.
        // For how to register a test device, please see the developer doc.
        customizedcast.setProductionMode();
        PushClient.send(customizedcast);
    }

    public void sendAndroidFilecast() throws Exception {
        AndroidFilecast filecast = new AndroidFilecast(appkey, appMasterSecret);
        // TODO upload your device tokens, and use '\n' to split them if there are multiple tokens
        String fileId = PushClient.uploadContents(appkey, appMasterSecret, "aa" + "\n" + "bb");
        filecast.setFileId(fileId);
        filecast.setTicker("Android filecast ticker");
        filecast.setTitle("中文的title");
        filecast.setText("Android filecast text");
        filecast.goAppAfterOpen();
        filecast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
        PushClient.send(filecast);
    }

    public void sendIOSBroadcast() throws Exception {
        IOSBroadcast broadcast = new IOSBroadcast(appkey, appMasterSecret);

        broadcast.setAlert("IOS 广播测试");
        broadcast.setBadge(0);
        broadcast.setSound("default");
        // TODO set 'production_mode' to 'true' if your app is under production mode
        broadcast.setTestMode();
        // Set customized fields
        broadcast.setCustomizedField("test", "helloworld");
        PushClient.send(broadcast);
    }

    public void sendIOSUnicast() throws Exception {
        IOSUnicast unicast = new IOSUnicast(appkey, appMasterSecret);
        // TODO Set your device token
        // 生成环境：d13e32224b33fdca27e7789746f553d2c6ee40f464dfdc9b9d131a9e7e27711b
        unicast.setDeviceToken("4a26435da1ac21d05b6cb3f6e5c3410e3f5f8156fb69a90d75f439336b20dfe9");
        // 屏幕弹出的内容
        unicast.setAlert("赞了我的评论");
        unicast.setBadge(0);
        unicast.setDescription("点赞");
        unicast.setSound("default");
        // TODO set 'production_mode' to 'true' if your app is under production mode
         unicast.setTestMode();
        // 设置为生成环境 测试一下
        //unicast.setProductionMode();
        // Set customized fields
        // 内容
        unicast.setCustomizedField("content", "hello world");
        // 跳转信息
        JSONObject jsonObject = new JSONObject();
        // 0：动画 1：捏报（咨询）
        jsonObject.put("type",0);
        // 动画或者捏报（咨询）id
        jsonObject.put("id",556);
        // 当评论时的评论ID
        jsonObject.put("commentId",437);
        // {type:0,id:1,commentId:1}
        unicast.setCustomizedField("typeSource",jsonObject);
        PushClient.send(unicast);
    }

    public void sendIOSGroupcast() throws Exception {
        IOSGroupcast groupcast = new IOSGroupcast(appkey, appMasterSecret);
		/*  TODO
		 *  Construct the filter condition:
		 *  "where":
		 *	{
    	 *		"and":
    	 *		[
      	 *			{"tag":"iostest"}
    	 *		]
		 *	}
		 */
        JSONObject filterJson = new JSONObject();
        JSONObject whereJson = new JSONObject();
        JSONArray tagArray = new JSONArray();
        JSONObject testTag = new JSONObject();
        testTag.put("tag", "iostest");
        tagArray.add(testTag);
        whereJson.put("and", tagArray);
        filterJson.put("where", whereJson);
        System.out.println(filterJson.toString());

        // Set filter condition into rootJson
        groupcast.setFilter(filterJson);
        groupcast.setAlert("IOS 组播测试");
        groupcast.setBadge(0);
        groupcast.setSound("default");
        // TODO set 'production_mode' to 'true' if your app is under production mode
        groupcast.setTestMode();
        PushClient.send(groupcast);
    }

    public void sendIOSCustomizedcast() throws Exception {
        IOSCustomizedcast customizedcast = new IOSCustomizedcast(appkey, appMasterSecret);
        // TODO Set your alias and alias_type here, and use comma to split them if there are multiple alias.
        // And if you have many alias, you can also upload a file containing these alias, then
        // use file_id to send customized notification.
        customizedcast.setAlias("alias", "alias_type");
        customizedcast.setAlert("IOS 个性化测试");
        customizedcast.setBadge(0);
        customizedcast.setSound("default");
        // TODO set 'production_mode' to 'true' if your app is under production mode
        customizedcast.setTestMode();
        PushClient.send(customizedcast);
    }

    public void sendIOSFilecast() throws Exception {
        IOSFilecast filecast = new IOSFilecast(appkey, appMasterSecret);
        // TODO upload your device tokens, and use '\n' to split them if there are multiple tokens
        String fileId = PushClient.uploadContents(appkey, appMasterSecret, "aa" + "\n" + "bb");
        filecast.setFileId(fileId);
        filecast.setAlert("IOS 文件播测试");
        filecast.setBadge(0);
        filecast.setSound("default");
        // TODO set 'production_mode' to 'true' if your app is under production mode
        filecast.setTestMode();
        PushClient.send(filecast);
    }

    public static void main(String[] args) {
        // TODO set your appkey and master secret here
        Demo demo = new Demo(UmengConstant.IOS.APPKEY, UmengConstant.IOS.APP_MASTER_SECRET);
        //Demo demo = new Demo(UmengConstant.Android.APPKEY, UmengConstant.Android.APP_MASTER_SECRET);
        try {
            demo.sendIOSUnicast();
            //demo.sendAndroidUnicast();
			/* TODO these methods are all available, just fill in some fields and do the test
			 * demo.sendAndroidCustomizedcastFile();
			 * demo.sendAndroidBroadcast();
			 * demo.sendAndroidGroupcast();
			 * demo.sendAndroidCustomizedcast();
			 * demo.sendAndroidFilecast();
			 *
			 * demo.sendIOSBroadcast();
			 * demo.sendIOSUnicast();
			 * demo.sendIOSGroupcast();
			 * demo.sendIOSCustomizedcast();
			 * demo.sendIOSFilecast();
			 */
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
