package com.suli.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.suli.lib.security.Constant;
import com.suli.lib.security.SecurityUtils;
import com.suli.lib.utils.EncryptUtils;
import com.suli.lib.utils.LogUtils;
import com.suli.lib.utils.RandomUtils;

public class MainActivity extends AppCompatActivity {
  private final static String TAG = MainActivity.class.getSimpleName();

  // Used to load the 'native-liassetsb' library on application startup.
  static {
    System.loadLibrary("utils-lib");
  }

  private final String data = "1990390u908";

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

  @OnClick(R.id.btn_aes) void onClickAES() {
    String key = "308340jf0l3933n9";
    LogUtils.d("data:" + data);
    LogUtils.d("data len:" + data.length());

    String aesEncrypt = SecurityUtils.encryptAESBase64(data, key);
    LogUtils.d("AES c:" + aesEncrypt);

    String aesEncryptJava = EncryptUtils.encryptAES2Base64(data, key);
    LogUtils.d("AES java:" + aesEncryptJava);
    LogUtils.d("AES java len:" + aesEncryptJava.length());

    String aesDecrypt = EncryptUtils.decryptBase64AES(aesEncrypt, key);

    if (!data.equals(aesDecrypt)) {
      LogUtils.d("aes failed:" + data);
    } else {
      LogUtils.d("aes success:" + data);
    }
  }

  @OnClick(R.id.btn_rsa) void onClickRsa() {
    String encrypt =
        SecurityUtils.encryptBase64RsaByPublicKey(data, Constant.NORMAL_RSA_PUBLIC_KEY);
    //String encrypt =
    //    EncryptUtils.encryptBase64RSAByPublicKey(data, Constant.NORMAL_RSA_PUBLIC_KEY_JAVA);
    LogUtils.d("rsa encrypt:" + encrypt);
    LogUtils.d("rsa encrypt len:" + encrypt.length());
    String decrypt =
        EncryptUtils.decryptBase64RSAByPrivateKey(encrypt, Constant.NORMAL_RSA_PRIVATE_KEY_JAVA);
    LogUtils.d("rsa decrypt " + (data.equals(decrypt) ? "success" : "failed") + " :" + decrypt);
  }

  //@OnClick(R.id.btn_goto_plugin_app) void onClickGotoPluginApp() {
  //
  //  Intent intent = RePlugin.createIntent("demo", "com.suli.myapplication.MainActivity");
  //  RePlugin.startActivity(this, intent);
  //}
  //
  //@OnClick(R.id.btn_goto_plugin_demo1) void onClickGotoPluginDemo1() {
  //  RePlugin.startActivity(MainActivity.this,
  //      RePlugin.createIntent("demo1", "com.qihoo360.replugin.sample.demo1.MainActivity"));
  //}
  //
  //@OnClick(R.id.btn_goto_plugin_share) void onClickGotoPluginShare() {
  //  RePlugin.startActivity(MainActivity.this,
  //      RePlugin.createIntent("share", "com.paem.plugin.share.MainActivity"));
  //}
}
