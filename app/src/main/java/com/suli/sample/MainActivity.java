package com.suli.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
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
  }

  /**
   * A native method that is implemented by the 'native-lib' native library,
   * which is packaged with this application.
   */
  public native String stringFromJNI();
}
