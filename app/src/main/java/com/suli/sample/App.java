package com.suli.sample;

import android.app.Application;
import com.suli.lib.utils.LogUtils;
import com.suli.lib.utils.Utils;

/**
 * Created by suli690 on 2017/2/9.
 * Application
 */

public class App extends Application {

  @Override public void onCreate() {
    super.onCreate();
    init();
  }

  private void init() {
    Utils.init(this);
    LogUtils.init(true, false, 'v', "suli");
  }
}
