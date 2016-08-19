package txt.vodfone.com.speedtestapplication.Mainpage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tm.corelib.ROContext;

import txt.vodfone.com.speedtestapplication.R;
import txt.vodfone.com.speedtestapplication.permission.PermissionHandlingActivity;
import txt.vodfone.com.speedtestapplication.util.TextViewValidate;
import txt.vodfone.com.speedtestapplication.util.Utils;

public class Splashscreen extends PermissionHandlingActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        String txt = getString(R.string.vodafone_splashscreen_loading) + " " + Utils.APPNAME_MASK;

        TextViewValidate tv = (TextViewValidate) findViewById(R.id.splashLoading);
        tv.setText(txt);

        //check whether RO service is disabled remotely first as we can skip the general Android M permission check
        //which is handled in #onResume() if so

        Utils.onCoreLibDisabledRemotely(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (arePermissionsGranted()) {

            final Intent openStartingPoint = new Intent(this, Utils.getStartupActivity());

            Thread timer = new Thread()
            {
                public void run()
                {
                    try
                    {
                        ROContext.startService();
                        sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    finally
                    {
                        startActivity(openStartingPoint);
                        finish();
                    }
                }
            };
            timer.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}
