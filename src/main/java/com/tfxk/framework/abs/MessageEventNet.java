package com.tfxk.framework.abs;

/**
 * Created by chenchunpeng on 2017/4/25.
 */

public class MessageEventNet {
    private final boolean hasInternet;

    /**
     *
     * @param netConnected true表示网络已连接，false表示网络已断开
     */
    public MessageEventNet(boolean netConnected){
        this.hasInternet=netConnected;
    }

    /**
     *
     * @return true表示网络已连接，false表示网络已断开
     */
    public boolean isHasInternet() {
        return hasInternet;
    }
}
