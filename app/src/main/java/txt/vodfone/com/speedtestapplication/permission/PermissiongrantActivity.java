package txt.vodfone.com.speedtestapplication.permission;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.tm.corelib.ROContext;

import txt.vodfone.com.speedtestapplication.R;
import txt.vodfone.com.speedtestapplication.util.Utils;
import txt.vodfone.com.speedtestapplication.util.VFPreferences;

public class PermissiongrantActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback
{
    private final int PERMISSION_REQUEST = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_permissiongrant);
    }

    public void grantPermissions(View v) {
        ActivityCompat.requestPermissions(this, ROContext.getRequiredPermissionsNotGranted(), PERMISSION_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST) {
            if (ROContext.checkRequiredPermissions()) {
                //all permissions granted
                startDefaultActivity();

            } else if (!ActivityCompat.shouldShowRequestPermissionRationale(this, ROContext.getRequiredPermissionsNotGranted()[0])) {
                //not all/any permission granted
                showNoPermissionsActivity();
            }else{
                showWarning(getString(R.string.vodafone_wifi));
            }

            VFPreferences.updateFirstAppStart(false);
        }
    }

    public void exitApp(View v)
    {
        ActivityCompat.finishAffinity(this);
    }

    private void startDefaultActivity() {
        Utils.startActivityWithNoAnimation(this, new Intent(this, Utils.getStartupActivity()));
        finish();
    }

    private void showNoPermissionsActivity() {
        Utils.startActivityWithNoAnimation(this, new Intent(this, NoPermissionsActivity.class));
        finish();
    }

    private void showWarning(String s) {
     //   Snackbar.make(findViewById(R.id.root), s, Snackbar.LENGTH_LONG).show();
    }

}
