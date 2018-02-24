package com.example.kaola.myrepluginhostapplication;

import android.content.Context;

import com.qihoo360.replugin.RePlugin;
import com.qihoo360.replugin.RePluginApplication;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.List;

/**
 * Created by zhaojing on 2018/2/12.
 */

public class MyApplication extends RePluginApplication {
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        AndPermission.with(this)
                .permission(Permission.WRITE_EXTERNAL_STORAGE)
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        // TODO what to do.
                    }
                }).onDenied(new Action() {
            @Override
            public void onAction(List<String> permissions) {
                // TODO what to do
            }
        }).start();
        mContext = getApplicationContext();
        RePlugin.enableDebugger(mContext, true);
    }
}
