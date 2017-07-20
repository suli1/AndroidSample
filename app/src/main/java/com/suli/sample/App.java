package com.suli.sample;

import com.qihoo360.replugin.RePluginApplication;
import com.suli.lib.utils.LogUtils;
import com.suli.lib.utils.Utils;

/**
 * Created by suli690 on 2017/2/9.
 * Application
 */

public class App extends RePluginApplication {

  @Override public void onCreate() {
    super.onCreate();
    init();
  }

  private void init() {
    Utils.init(this);
    LogUtils.init(true, false, 'v', "suli");
  }
}
