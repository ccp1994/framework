package com.tfxk.framework.utils;

import android.content.Context;
import android.view.Gravity;

import com.tfxk.framework.R;
import com.tfxk.framework.ui.ToastView;

/**
 * Created by chenchunpeng on 2017/4/25.
 */

public class DialogUtils {

    /**
     * 显示无网络提示
     *
     * @param context
     */
    public static void showNoNetWorkTipsDialog(Context context,int imageRes) {
        String exit = context.getString(R.string.have_no_network);
        ToastView toast = new ToastView(context, exit,imageRes);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
