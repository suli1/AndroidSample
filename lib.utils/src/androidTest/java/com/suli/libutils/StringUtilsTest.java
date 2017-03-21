package com.suli.libutils;

import android.text.SpannedString;
import com.suli.lib.utils.StringUtils;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by suli on 2017/3/21.
 * {@link StringUtils} 单元测试
 */

public class StringUtilsTest {

  @Test public void emptyTest() {
    assertTrue(StringUtils.isEmpty(null));
    assertTrue(StringUtils.isEmpty(""));
    assertFalse(StringUtils.isEmpty("test"));

    assertTrue(StringUtils.isSpace(null));
    assertTrue(StringUtils.isSpace("   "));
    assertTrue(StringUtils.isSpace(""));
  }

  @Test public void equalsTest() {
    assertTrue(StringUtils.equals("test", "test"));
    assertTrue(StringUtils.equalsIgnoreCase("TEST", "test"));

    SpannedString spannedString = new SpannedString("spanned");
    assertTrue(StringUtils.equals("spanned", spannedString));
  }

  @Test public void letterTest() {
    assertTrue(StringUtils.upperFirstLetter("test").equals("Test"));
    assertTrue(StringUtils.lowerFirstLetter("TEST").equals("tEST"));

    assertTrue(StringUtils.reverse("reverse").equals("esrever"));
  }

  @Test public void dbcAndSbcTest() {
    assertTrue(StringUtils.toDBC("，").equals(","));
    assertTrue(StringUtils.toSBC(",").equals("，"));
  }
}
