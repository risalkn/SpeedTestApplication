package txt.vodfone.com.speedtestapplication.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.tm.corelib.ROContext;

/**
 * Use methods to store data to persistent storage
 *
 * Net Perform 3 Sample View
 * Date: 2015-08-24
 *
 * Copyright (c) 2016 RadioOpt GmbH. All rights reserved.
 */
public class VFPreferences {

  protected static final String PREFSNAME = "corelib_prefs";

  private static final String KEY_DO_NOT_ASK_AGAIN = "KEY_DO_NOT_ASK_AGAIN";
  private static final String KEY_SORT_ORDER_HISTORY = "KEY_SORT_ORDER_HISTORY";

  private static final String KEY_SPEEDTEST_COUNT = "KEY_SPEEDTEST_COUNT";
  
  public static final String KEY_WARN_CELLULAR = "KEY_WARN_CELLULAR";


  public static boolean getConfirmDelete() {
    return getBoolean(KEY_DO_NOT_ASK_AGAIN, false);
  }

  public static void updateConfirmDelete(boolean value) {
    updateBoolean(KEY_DO_NOT_ASK_AGAIN, value);
  }

  public  static void updateSortOder(int value) {
    updateInt(KEY_SORT_ORDER_HISTORY, value);
  }

  public static int getSortOrder() {
    return getInt(KEY_SORT_ORDER_HISTORY, 0);
  }

  public static int getSpeedTestCount() {
    return getInt(KEY_SPEEDTEST_COUNT,0);
  }

  public static void updateSpeedTestCount(int _value) {
    updateInt(KEY_SPEEDTEST_COUNT,_value);
  }

  public static final String KEY_FIRST_APP_START = "KEY_FIRST_APP_START";

  /**
   * Checks if mobile warning is required for speed test
   *
   * @return true if mobile warning checked
   */
  public static boolean isMobileWarning() {
    SharedPreferences prefs = getSharedPreferences();
    return prefs.getBoolean(KEY_WARN_CELLULAR, true);
  }

  /**
   * Updates mobile warning value
   *
   * @param value
   */
  public static void updateMobileWarning(boolean value) {
    SharedPreferences prefs = getSharedPreferences();
    Editor edit = prefs.edit();
    edit.putBoolean(KEY_WARN_CELLULAR, value);
    edit.commit();
  }

  public static boolean isFirstAppStart() {
    return getBoolean(KEY_FIRST_APP_START, true);
  }

  public static void updateFirstAppStart(boolean isFirstAppStart) {
    updateBoolean(KEY_FIRST_APP_START, isFirstAppStart);
  }

  private static SharedPreferences getSharedPreferences() {
    return ROContext.getApplicationContext().getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE);
  }

  private static int getInt(String key, int defaultValue) {
    SharedPreferences prefs = getSharedPreferences();
    int value = prefs.getInt(key, defaultValue);
    return value;
  }

  private static void updateInt(String key, int value) {
    SharedPreferences prefs = getSharedPreferences();
    Editor editor = prefs.edit();
    editor.putInt(key, value);
    editor.commit();
  }

  private static boolean getBoolean(String key, boolean defaultValue) {
    SharedPreferences prefs = getSharedPreferences();
    boolean value = prefs.getBoolean(key, defaultValue);
    return value;
  }

  private static void updateBoolean(String key, boolean value) {
    SharedPreferences prefs = getSharedPreferences();
    Editor editor = prefs.edit();
    editor.putBoolean(key, value);
    editor.commit();
  }

}
