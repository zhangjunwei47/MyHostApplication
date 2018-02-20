package com.example.kaola.myrepluginhostapplication;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.qihoo360.replugin.RePlugin;

/**
 * Created by zhaojing on 2018/2/20.
 */

public class TestShowFragmentActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * "com.example.kaola.myrepluginpluginapplication";
         */
        String pluginName = "myplugin1";
        RePlugin.registerHookingClass("com.example.kaola.myrepluginpluginapplication.FirstTestFragment", RePlugin.createComponentName(pluginName, "com.example.kaola.myrepluginpluginapplication.FirstTestFragment"), null);
        setContentView(R.layout.activity_show_fragment);
        findViewById(R.id.test_viewx).postDelayed(new Runnable() {
            @Override
            public void run() {
                showFragment();
            }
        }, 2000);
    }

    private void showFragment() {

        /**
         * "com.example.kaola.myrepluginpluginapplication";
         */
        String pluginName = "myplugin1";
        /**
         * 获取插件的ClassLoader
         */
        ClassLoader d1ClassLoader = RePlugin.fetchClassLoader(pluginName);
        try {
            Fragment fragment = d1ClassLoader.loadClass("com.example.kaola.myrepluginpluginapplication.FirstTestFragment").asSubclass(Fragment.class).newInstance();
            getFragmentManager().beginTransaction().add(R.id.fragment_layoutx, fragment).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
