package txt.vodfone.com.speedtestapplication.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.tm.corelib.ROContext;

import java.text.DecimalFormat;

import txt.vodfone.com.speedtestapplication.MainActivity;
import txt.vodfone.com.speedtestapplication.Mainpage.Speedchekerhome;
import txt.vodfone.com.speedtestapplication.R;


/**
 * Utilization class for global usage
 * <p/>
 * Copyright (c) 2016 RadioOpt GmbH. All rights reserved.
 */
public class Utils {

  public final static String APPNAME_MASK = "AppName";

  //default Activity when service is running
  private static final Class<? extends Activity> DEFAULT_ACTIVITY = Speedchekerhome.class;

  public enum Unit {
    B, KB, MB, GB
  }

  /**
   * Format bytes to data unit string
   *
   * @param _dataBytes bytes
   * @return formated string
   */
  public static String ToDataUnitStringMB(double _dataBytes) {
    double dDataBytes;

    Unit myUnit = Unit.MB;
    dDataBytes = _dataBytes / 1048576;

    String res = getDecimalFormat().format(dDataBytes);

    res = res + "" + myUnit.name();
    return res;
  }

  private static DecimalFormat getDecimalFormat() {
    return new DecimalFormat("####0.##");
  }

  private static DecimalFormat getDecimalFormat2() {
    return new DecimalFormat("####0.#");
  }

  private static DecimalFormat getDecimalFormat3() {
    return new DecimalFormat("#####");
  }

  /**
   * Format bytes to data unit string
   *
   * @param _dataBytes bytes
   * @return formated string
   */
  public static String ToDataUnitString(double _dataBytes) {

    Unit myUnit;
    String res;

    if (_dataBytes >= 1073741824000L) {
      res = getDecimalFormat3().format(_dataBytes / (1024 * 1024 * 1024));
      myUnit = Unit.GB;
    } else if (_dataBytes >= 1048576000L) {
      double dBytesGB = _dataBytes / (1024 * 1024 * 1024);

      if (dBytesGB % 1000 >= 100) {
        res = getDecimalFormat3().format(dBytesGB);
      } else if (dBytesGB % 1000 >= 10) {
        res = getDecimalFormat2().format(dBytesGB);
      } else
        res = getDecimalFormat().format(dBytesGB);

      myUnit = Unit.GB;
    } else {
      double dBytesMB = _dataBytes / (1024 * 1024);

      if (dBytesMB % 1000 >= 100) {
        res = getDecimalFormat3().format(dBytesMB);
      } else if (dBytesMB % 1000 >= 10) {
        res = getDecimalFormat2().format(dBytesMB);
      } else
        res = getDecimalFormat().format(dBytesMB);

      myUnit = Unit.MB;
    }

    res = res + "" + myUnit.name();
    return res;
  }

  /**
   * Format timestamp to formatted duration
   *
   * @param timestamp
   * @return formatted duration
   */
  public static String formatDuration(long timestamp) {
    long duration = timestamp * 1000;

    long s = (duration / 1000) % 60;
    long m = (duration / (1000 * 60)) % 60;
    long h = (duration / (1000 * 60 * 60)) % 24;

    return String.format("%02d:%02d:%02d", h, m, s);

  }

  static String sAppNameReplacer = null;

  /**
   * Return UI app name for string replacement in text resources.
   */
  public static String getUIAppName(Context ctx) {
    if (sAppNameReplacer == null) {
      sAppNameReplacer = ctx.getString(R.string.app_name);
    }
    return sAppNameReplacer;
  }

  public static String replaceAppName(String strWithPattern, Context ctx) {
    return strWithPattern.replaceAll(APPNAME_MASK, getUIAppName(ctx));
  }

  public static void startActivityWithNoAnimation(Activity _activity, Intent _intent) {
    _intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    _activity.startActivity(_intent);
    _activity.overridePendingTransition(0,0);
  }

  public static double getmeasurevalue_Mbps(float nmeasurevaluedisplay) {
    return (roundScale1(nmeasurevaluedisplay / (1000)));
  }

  public static String getmeasureunit_Mbps() {
    return ("Mbit/s");
  }

  public static double roundScale1(double d) {
    return Math.rint(d * 10) / 10.;
  }

  /**
   * Evaluates the ConnectivityManager.getActiveNetworkInfo().getType() in order
   * to make a decision whether the current active connection type is "mobile" or "wifi".
   * WIMAX is assigned to the "mobile" type.
   *
   * @return true if connections is of type MOBILE or WIMAX<br>otherwise: false
   * (e.g. WIFI / error occurred while requesting active network info)
   */
  public static boolean isCurrentConnectionMobile() {
    try {
      ConnectivityManager connectivityManager = (ConnectivityManager) ROContext.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo ni = connectivityManager.getActiveNetworkInfo();

      switch (ni.getType()) {
        case ConnectivityManager.TYPE_WIFI:
        case ConnectivityManager.TYPE_WIMAX:
             return false;
        case ConnectivityManager.TYPE_MOBILE:
        case ConnectivityManager.TYPE_MOBILE_DUN:
        case ConnectivityManager.TYPE_MOBILE_HIPRI:
        case ConnectivityManager.TYPE_MOBILE_MMS:
        case ConnectivityManager.TYPE_MOBILE_SUPL:
          return true;
        // handle everything else as WIFI in order to keep critical MOBILE counters clean
        default:
          return false;

      }
    } catch (Exception e) {
      ROContext.onException(e);
    }
    return false;
  }

  /**
   * Check if the mobile device is roaming.
   * Please note that a possible WiFi connection is not taken into account!
   * So check also isCurrentConnectionWiFi() in order to evaluate roaming scenarios.
   *
   * @return <b>true</b> if the current cellular network connection is considered roaming
   */
  public static boolean isCurrentConnectionRoaming() {
    try {
      TelephonyManager tm = (TelephonyManager) ROContext.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
      if (tm != null) return tm.isNetworkRoaming();
    } catch (Exception e) {
      ROContext.onException(e);
    }
    return false;
  }

  /**
   * @return true if mobile or wifi on
   */
  public static boolean isRadioOn() {
    ConnectivityManager man =
            (ConnectivityManager) ROContext.getApplicationContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
    if (man != null) {
      NetworkInfo ni = man.getActiveNetworkInfo();
      if (ni == null || !ni.isConnected()) {
        return false;
      }
      return ni.isConnected();
    }
    return false;
  }

  /**
   * Checks whether the underlying CoreLib is enabled or not. If the state is disabled the hosting app
   * handles this event by starting a default activity
   *
   * @param activity the calling activity
   */
  public static void onCoreLibDisabledRemotely(Activity activity) {
    if (ROContext.isCoreLibDisabledRemotely()) {
      activity.startActivity(new Intent(activity, MainActivity.class));
      activity.finish();
    }
  }

  /**
   * Get the default startup Activity depending on the current service state.
   *
   * @return Activity reference
   */
  public static Class<? extends Activity> getStartupActivity() {


    Log.d("Utils",""+ROContext.isServiceRunning());
    if (ROContext.isServiceRunning()) {
      return DEFAULT_ACTIVITY;
    } else {
      return DEFAULT_ACTIVITY;
    }
  }
}