package txt.vodfone.com.speedtestapplication.permission;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tm.corelib.ROContext;

import txt.vodfone.com.speedtestapplication.R;
import txt.vodfone.com.speedtestapplication.util.Utils;


/**
 * NetPerformPlus
 * <p/>
 * User: anton.augsburg
 * Date: 06.01.2016
 * Time: 11:34
 * <p/>
 * Copyright (c) 2016 RadioOpt GmbH. All rights reserved.
 */
public class NoPermissionsActivity extends AppCompatActivity {

  private boolean mPermissionGranted = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_nopermissions);
  }

  @Override
  protected void onResume() {
    super.onResume();

    mPermissionGranted = ROContext.checkRequiredPermissions();

    checkForStart();
  }

  private void checkForStart() {
    if (mPermissionGranted) {
      showDefaultActivity();
    }
  }

  public void showSystemSettings(View v) {
    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
      Uri.fromParts("package", getPackageName(), null));
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
  }

  public void exitApp(View v) {
    ActivityCompat.finishAffinity(this);
  }

  private void showDefaultActivity() {
    Utils.startActivityWithNoAnimation(this, new Intent(this, Utils.getStartupActivity()));
  }

}
