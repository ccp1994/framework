package com.tfxk.framework;

import com.tfxk.framework.utils.VersionControler;

/**
 * Created by diannao on 2017/4/24.
 */

public class Constants {


    public class RequestCode {
        public static final int REQUEST_CODE_SCAN = 101;

    }


    public static class Action {
        /**
         * 网络监听服务
         */
        public static  String ACTION_NET_SERVICE = VersionControler.getPackageName(GlobalApplication.getContext()) + ".NET_STATE_SERVICE";
    }
}
