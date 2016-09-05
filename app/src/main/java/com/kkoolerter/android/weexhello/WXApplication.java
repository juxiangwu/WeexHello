package com.kkoolerter.android.weexhello;

import android.app.Application;

import com.kkoolerter.android.weexhello.commons.adapter.ImageAdapter;
import com.kkoolerter.android.weexhello.extend.PlayDebugAdapter;
import com.kkoolerter.android.weexhello.extend.component.RichText;
import com.kkoolerter.android.weexhello.extend.module.MyModule;
import com.kkoolerter.android.weexhello.extend.module.RenderModule;
import com.kkoolerter.android.weexhello.extend.module.WXEventModule;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;

public class WXApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//    initDebugEnvironment(false, "DEBUG_SERVER_HOST");
        WXSDKEngine.addCustomOptions("appName", "WXSample");
        WXSDKEngine.addCustomOptions("appGroup", "WXApp");
        WXSDKEngine.initialize(this,
                new InitConfig.Builder()
                        .setImgAdapter(new ImageAdapter())
                        .setDebugAdapter(new PlayDebugAdapter())
                        .build()
        );

        try {
            Fresco.initialize(this);
            WXSDKEngine.registerComponent("richtext", RichText.class);
            WXSDKEngine.registerModule("render", RenderModule.class);
            WXSDKEngine.registerModule("event", WXEventModule.class);

            WXSDKEngine.registerModule("myModule", MyModule.class);

        } catch (WXException e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * @param enable enable remote debugger. valid only if host not to be "DEBUG_SERVER_HOST".
     *               true, you can launch a remote debugger and inspector both.
     *               false, you can  just launch a inspector.
     * @param host the debug server host, must not be "DEBUG_SERVER_HOST", a ip address or domain will be OK.
     *             for example "127.0.0.1".
     */
    private void initDebugEnvironment(boolean enable, String host) {
        if (!"DEBUG_SERVER_HOST".equals(host)) {
            WXEnvironment.sRemoteDebugMode = enable;
            WXEnvironment.sRemoteDebugProxyUrl = "ws://" + host + ":8088/debugProxy/native";
        }
    }

}
