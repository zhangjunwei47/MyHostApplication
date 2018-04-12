package com.example.kaola.myrepluginhostapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.qihoo360.replugin.RePlugin;
import com.qihoo360.replugin.model.PluginInfo;
import com.qihoo360.replugin.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import xiaofei.library.hermeseventbus.HermesEventBus;

public class MainActivity extends Activity {


    Button preloadBtn, installBtn, startBtn, startTwoBtn, startFragment, sendMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preloadBtn = findViewById(R.id.preloadPlugin);
        preloadBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                RePlugin.preload("myplugin1");
            }
        });

        installBtn = findViewById(R.id.install_btn);
        installBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                installExternalPlugin("oneApp.apk");
            }
        });
        startBtn = findViewById(R.id.start_activity_btn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RePlugin.startActivity(MainActivity.this, RePlugin.createIntent("com.example.kaola.myrepluginpluginapplication", "com.example.kaola.myrepluginpluginapplication.MainActivity"));
            }
        });
        startTwoBtn = findViewById(R.id.startTwoPlugin);
        startTwoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RePlugin.startActivity(MainActivity.this, RePlugin.createIntent("com.example.kaola.mypluginapplicationtwo", "com.example.kaola.mypluginapplicationtwo.MainActivity"));
            }
        });
        startFragment = findViewById(R.id.start_fragment);
        startFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, TestShowFragmentActivity.class);
                startActivity(intent);
            }
        });
        sendMessage = findViewById(R.id.sendMessage);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.kaola.broadcast.test");
                sendBroadcast(intent);
            }
        });
        // 20s 后发送一个消息
        sendMessage.postDelayed(new Runnable() {
            @Override
            public void run() {
                EventMessage eventMessage = new EventMessage();
                eventMessage.setMsg("宿主app");
                eventMessage.setTag("event");
                HermesEventBus.getDefault().post(eventMessage);
            }
        }, 20000);
    }


    private void installExternalPlugin(String oneAppName) {
        String oneAppPath = "myapk" + File.separator + oneAppName;

        // 文件是否已经存在？直接删除重来
        String pluginFilePath = getFilesDir().getAbsolutePath() + File.separator + oneAppName;
        File pluginFile = new File(pluginFilePath);
        if (pluginFile.exists()) {
            FileUtils.deleteQuietly(pluginFile);
        }

        // 开始复制
        copyAssetsFileToAppFiles(oneAppPath, oneAppName);

        PluginInfo info = null;
        if (pluginFile.exists()) {
            info = RePlugin.install(pluginFilePath);
        }
    }

    /**
     * 从assets目录中复制某文件内容
     *
     * @param assetFileName assets目录下的Apk源文件路径
     * @param newFileName   复制到/data/data/package_name/files/目录下文件名
     */
    private void copyAssetsFileToAppFiles(String assetFileName, String newFileName) {
        InputStream is = null;
        FileOutputStream fos = null;
        int buffsize = 1024;

        try {
            is = this.getAssets().open(assetFileName);
            fos = this.openFileOutput(newFileName, Context.MODE_PRIVATE);
            int byteCount = 0;
            byte[] buffer = new byte[buffsize];
            while ((byteCount = is.read(buffer)) != -1) {
                fos.write(buffer, 0, byteCount);
            }
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
