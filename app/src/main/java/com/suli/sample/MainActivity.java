package com.suli.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.qihoo360.replugin.RePlugin;
import com.suli.lib.utils.EncryptUtils;
import com.suli.lib.utils.LogUtils;
import com.suli.lib.utils.RandomUtils;

public class MainActivity extends AppCompatActivity {
  private final static String TAG = MainActivity.class.getSimpleName();

  // Used to load the 'native-liassetsb' library on application startup.
  static {
    System.loadLibrary("utils-lib");
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    //RePlugin.install("/sdcard/app-debug.apk");
  }

  @OnClick(R.id.btn_crash) void onClickCrash() {
    throw new RuntimeException("Crash test!");
  }

  @OnClick(R.id.btn_webview) void onClickWebView() {
    startActivity(WebViewActivity.newIntent(this, null));
  }

  @OnClick(R.id.btn_encrypt) void onClickEncryptTest() {
    new Thread(new Runnable() {
      @Override public void run() {

        int count = 5000;
        int aesFailed = 0;
        String key = "308340jf0l3";
        for (int i = 0; i < count; i++) {
          String data = RandomUtils.getRandomString((int) (Math.random() * 100));

          String aesEncrypt =
              new String(EncryptUtils.encryptAES2Base64(data.getBytes(), key.getBytes()));

          try {
            Thread.sleep(10);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }

          String aesDecrypt =
              new String(EncryptUtils.decryptBase64AES(aesEncrypt.getBytes(), key.getBytes()));

          if (!data.equals(aesDecrypt)) {
            LogUtils.d(i + ":aes failed:" + data);
            aesFailed++;
          }
        }
        LogUtils.d("Count:" + count + ", aes failed:" + aesFailed);
      }
    }).start();
  }

  @OnClick(R.id.btn_goto_plugin_app) void onClickGotoPluginApp() {

    Intent intent = RePlugin.createIntent("demo", "com.suli.myapplication.MainActivity");
    RePlugin.startActivity(this, intent);
  }

  @OnClick(R.id.btn_goto_plugin_demo1) void onClickGotoPluginDemo1() {
    RePlugin.startActivity(MainActivity.this,
        RePlugin.createIntent("demo1", "com.qihoo360.replugin.sample.demo1.MainActivity"));
  }

  @OnClick(R.id.btn_goto_plugin_share) void onClickGotoPluginShare() {
    RePlugin.startActivity(MainActivity.this,
        RePlugin.createIntent("share", "com.paem.plugin.share.MainActivity"));
  }
}
