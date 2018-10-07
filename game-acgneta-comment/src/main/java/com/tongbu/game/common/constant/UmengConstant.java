package com.tongbu.game.common.constant;

import com.alibaba.fastjson.JSONObject;

/**
 * @author jokin
 * @date 2018/9/20 11:21
 *
 * 捏他 友盟信息：
 *  账号：401120187@qq.com
 *  密码：jie88854859
 *
 */
public class UmengConstant {
    public class Android{
        /**
         * acgneta Android 包名
         * */
        public static final String PACK_NAME = "scholar.acgneta.com.acgneta";
        /**
         * PUSH Appkey
         * */
        public static final String APPKEY = "5b6a8106f43e480edc00017a";

        /**
         * Umeng Message Secret
         * */
        public static final String UMENG_MESSAGE_SECRET = "0ca3d0f9b7d5504791c3cb5a01c8a5f9";
        /**
         * App Master Secret
         * */
        public static final String APP_MASTER_SECRET = "8p0gawi9kjf5vnrrtcn2grsmhhbvndwe";
    }

    public class IOS{
        /**
         * acgneta Android 包名
         * */
        public static final String PACK_NAME = "com.tongbu.ACGNeta";
        public static final String APPKEY = "5b7bae78a40fa30b03000086";
        public static final String UMENG_MESSAGE_SECRET = "";
        public static final String APP_MASTER_SECRET = "qndw2kn5k3ahomjjhypykjroihuttns4";
        /**
         * 声音显示，这里使用默认的
         * */
        public static final String SOUND = "default";
    }

    public static final String CUSTOMZIED_FIELD_CONTENT = "content";

    public static final String CUSTOMZIED_FIELD_TYPE_SOURCE = "typeSource";
}
