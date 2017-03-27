package com.suli.libutils;

import com.suli.lib.utils.StringUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by suli on 2017/3/21.
 * {@link StringUtils} 单元测试
 */

public class StringUtilsTest {

  @Test public void emptyTest() {
    Assert.assertTrue(StringUtils.isEmpty(null));
    Assert.assertTrue(StringUtils.isEmpty(""));
    Assert.assertFalse(StringUtils.isEmpty("test"));

    Assert.assertTrue(StringUtils.isSpace(null));
    Assert.assertTrue(StringUtils.isSpace("   "));
    Assert.assertTrue(StringUtils.isSpace(""));
  }

  @Test public void equalsTest() {
    Assert.assertTrue(StringUtils.equals("test", "test"));
    Assert.assertTrue(StringUtils.equalsIgnoreCase("TEST", "test"));

    String spannedString = "spanned";
    Assert.assertTrue(StringUtils.equals("spanned", spannedString));
  }

  @Test public void letterTest() {
    Assert.assertEquals(StringUtils.upperFirstLetter("test"), "Test");
    Assert.assertEquals(StringUtils.lowerFirstLetter("TEST"), "tEST");

    Assert.assertEquals(StringUtils.reverse("reverse"), "esrever");
  }

  @Test public void dbcAndSbcTest() {
    Assert.assertEquals(StringUtils.toDBC("，"), ",");
    Assert.assertEquals(StringUtils.toSBC(","), "，");
  }
}
