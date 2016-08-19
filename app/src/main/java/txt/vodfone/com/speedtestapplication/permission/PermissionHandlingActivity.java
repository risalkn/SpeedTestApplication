package txt.vodfone.com.speedtestapplication.permission;

import android.content.Intent;
import android.support.annotation.CallSuper;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.tm.corelib.ROContext;

import android.content.Intent;
import android.support.annotation.CallSuper;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.tm.corelib.ROContext;
import txt.vodfone.com.speedtestapplication.MainActivity;
import txt.vodfone.com.speedtestapplication.util.Utils;
import txt.vodfone.com.speedtestapplication.util.VFPreferences;

/**
 * NetPerformPlus
 * <p/>
 * User: anton.augsburg
 * Date: 06.01.2016
 * Time: 11:19
 * <p/>
 * Copyright (c) 2016 RadioOpt GmbH. All rights reserved.
 */
public abstract class PermissionHandlingActivity extends AppCompatActivity {

  protected final String TAG = getLogTag();

  private boolean mPermissionsGranted;

  @Override
  @CallSuper
  protected void onResume() {
    super.onResume();

    mPermissionsGranted = checkForPermissions();
  }

  private boolean checkForPermissions() {
    Log.d(TAG, "checking for permissions");

    boolean permissionsGranted = ROContext.checkRequiredPermissions();

    if (!permissionsGranted) {
      //check if we should show some more explanation to the user
      String[] permissions = ROContext.getRequiredPermissionsNotGranted();

      for (String s : permissions)
      {
        /**
         * First app start: false
         * subsequent calls (denied): true
         * subsequent calls (do not ask again): false
         */
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, s) || VFPreferences.isFirstAppStart()) {
          //first app start / OOBE -> explainshouldShowRequestPermissionRationale and ask for permissions
          showTutorialActivity();

          finish();
          return false;
        } else {
          //subsequent app start with missing/no permissions -> point the user to settings
          showNoPermissionsActivity();
          finish();
        }
      }
    }

    return permissionsGranted;
  }

  private void showTutorialActivity() {
    Utils.startActivityWithNoAnimation(this, new Intent(this, PermissiongrantActivity.class));
  }

  private void showNoPermissionsActivity() {
    Utils.startActivityWithNoAnimation(this, new Intent(this, NoPermissionsActivity.class));
  }

  protected boolean arePermissionsGranted() {
    return mPermissionsGranted;
  }

  protected String getLogTag() {
    return "NP." + getClass().getSimpleName();
  }
}
