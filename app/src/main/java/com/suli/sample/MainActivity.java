package com.suli.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.suli.lib.utils.EncryptUtils;
import com.suli.lib.utils.LogUtils;
import com.suli.lib.utils.RandomUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

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
  }

  @OnClick(R.id.btn_crash) void onClickCrash() {
    throw new RuntimeException("Crash test!");
  }

  @OnClick(R.id.btn_webview) void onClickWebView() {
    startActivity(WebViewActivity.newIntent(this, null));
  }

  @OnClick(R.id.btn_encrypt)
  void onClickEncryptTest() {
    new Thread(new Runnable() {
      @Override
      public void run() {

        int count = 5000;
        int aesFailed = 0;
        String key = "308340jf0l3";
        for (int i = 0; i < count; i++) {
          String data = RandomUtils.getRandomString((int) (Math.random() * 100));

          String aesEncrypt = new String(EncryptUtils.encryptAES2Base64(data.getBytes(), key.getBytes()));

          try {
            Thread.sleep(10);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }

          String aesDecrypt = new String(EncryptUtils.decryptBase64AES(aesEncrypt.getBytes(), key.getBytes()));

          if (!data.equals(aesDecrypt)) {
            LogUtils.d(i + ":aes failed:" + data);
            aesFailed++;
          }
        }
        LogUtils.d("Count:" + count + ", aes failed:" + aesFailed);
      }
    }).start();
  }
}
