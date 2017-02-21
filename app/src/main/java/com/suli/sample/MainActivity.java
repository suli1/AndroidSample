package com.suli.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.suli.lib.utils.ConvertUtils;
import com.suli.lib.utils.tools.AESUtils;

public class MainActivity extends AppCompatActivity {
  private final static String TAG = MainActivity.class.getSimpleName();
  // Used to load the 'native-liassetsb' library on application startup.
  static {
    System.loadLibrary("native-lib");
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Example of a call to a native method
    TextView tv = (TextView) findViewById(R.id.tv_message);
    tv.setText(stringFromJNI());

    findViewById(R.id.btn_crash).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        throw new RuntimeException("Crash test!");
      }
    });

    findViewById(R.id.btn_aes).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        testEncrypt();
      }
    });

    testEncrypt();
  }

  private void testEncrypt() {
    //byte[] key = new byte[]{0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08,0x09,0x0a,0x0b,0x0c,0x0d,0x0e,0x0f,0x10};
    //byte[] data = new byte[]{0x10,0x11,0x12,0x13,0x14,0x15,0x16,0x17,0x18,0x19,0x1a,0x1b,0x1c,0x1d,0x1e,0x1f};
    //
    //Log.i(TAG, "密钥:" + ConvertUtils.bytes2HexString(key));
    //Log.i(TAG, "明文:" + ConvertUtils.bytes2HexString(data));
    //byte[] encrypt = AESUtils.encryptWithVi(data, key);
    //Log.i(TAG, "密文:" + ConvertUtils.bytes2HexString(encrypt));

    String plainText = "1234567890123456";
    String strKey = "1234567890123456";
    Log.i(TAG, "明文:" + ConvertUtils.bytes2HexString(plainText.getBytes()));
    byte[] bEncrypt = AESUtils.encryptWithVi(plainText.getBytes(), strKey.getBytes());
    Log.i(TAG, "java 加密:" + ConvertUtils.bytes2HexString(bEncrypt));
    Log.i(TAG, "java 解密:" + new String(AESUtils.decryptWithVi(bEncrypt, strKey.getBytes())));
    Log.i(TAG, "native 加密:" + encryptImportantInfo(plainText, strKey));
    //Log.i(TAG, "native 解密:" + decryptImportantInfo(plainText, strKey));

  }

  /**
   * A native method that is implemented by the 'native-lib' native library,
   * which is packaged with this application.
   */
  public native String stringFromJNI();

  public native byte[] encryptAES(byte[] data, byte[] password);

  public native byte[] dencryptAES(byte[] data, byte[] password);

  public native String encryptImportantInfo(String data, String key);

  public native String decryptImportantInfo(String data, String key);
}
