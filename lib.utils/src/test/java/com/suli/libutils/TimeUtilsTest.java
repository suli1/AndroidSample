package com.suli.libutils;

import com.suli.lib.utils.TimeUtils;
import java.util.Date;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

/**
 * Created by suli on 2017/3/21.
 * {@link TimeUtils} 单元测试
 */

public class TimeUtilsTest {

  @Rule public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

  private long timestamp;
  private String date;

  @Before public void setup() {
    timestamp = 1490105889000L;
    date = "2017-03-21 22:18:09";   // 北京时间
  }

  @Test public void millis2StringTest() {
    System.out.print("test");
    Assert.assertEquals(TimeUtils.millis2String(timestamp), date);
    Assert.assertEquals(TimeUtils.millis2String(timestamp, "yyyy-MM-dd HH:mm:ss"), date);
  }

  @Test public void string2MillisTest() {
    Assert.assertEquals(TimeUtils.string2Millis(date), timestamp);
    Assert.assertEquals(TimeUtils.string2Millis(date, "yyyy-MM-dd HH:mm:ss"), timestamp);
    Assert.assertEquals(TimeUtils.string2Millis(date, "yyyy-MM-dd HH:mm:aa"), -1);
  }

  @Test public void string2DateTest() {
    Date d = new Date(timestamp);
    Assert.assertEquals(TimeUtils.string2Date(date), d);
  }
}
