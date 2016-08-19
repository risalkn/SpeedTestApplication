package txt.vodfone.com.speedtestapplication.Speedtest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tm.stlib.ROSTTaskResult;
import com.tm.stlib.ROSpeedTest;
import com.tm.stlib.ROSpeedTestListener;

import java.text.DecimalFormat;

import txt.vodfone.com.speedtestapplication.R;
import txt.vodfone.com.speedtestapplication.customdigrams.Speedmeterview;

public class SpeedTestActivity extends AppCompatActivity implements ROSpeedTestListener, Handler.Callback {


    private static float SPEEDO_SCALE_SPEEDS[];
    private static float SPEEDO_SCALE_ANGLES[];
    private static boolean SPEEDO_SCALE_LINEAR[];
    private static int SPEEDO_SCALE_SEGEMNTS;

    private ProgressBar progressbar;
    /**
     * The decimal formatter for the MBit/s display in the upper left corner.
     */
    private DecimalFormat mFormatter = new DecimalFormat("#00.000");
    private DecimalFormat mFormatter2 = new DecimalFormat("##0.0");

    // Progress indicator for the test server url request
    private int mProgressServerRequest = 0;
    private Speedmeterview speedometer;
    // Values for the speedo meter tacho animation
    private int mCurrSpeedoValue;
    private long mLastSpeedoUpdate;
    Runnable runnable;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_test);


        boolean switch_ICMP_ping = true;
        boolean switch_HTTP_ping = true;

        ROSpeedTest ROSpeedtest = new ROSpeedTest(getApplicationContext(), this, true,
                true, switch_ICMP_ping, switch_HTTP_ping, false, true);
        // Start the speed test
        ROSpeedtest.startSpeedtest();
        progressbar = (ProgressBar) findViewById(R.id.progressBar);
        speedometer = (Speedmeterview) findViewById(R.id.Speedometer);


//        Button increaseSpeed = (Button) findViewById(R.id.IncreaseSpeed);
//        Button decreaseSpeed = (Button) findViewById(R.id.DecreaseSpeed);
//        increaseSpeed.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//
//
//                speedometer.onSpeedChanged(speedometer.getCurrentSpeed() + 8);
//                // Log.d("current speed",""+speedometer.getCurrentSpeed());
//
//
//            }
//
//        });
//        decreaseSpeed.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                speedometer.onSpeedChanged(speedometer.getCurrentSpeed() - 8);
//                //Log.d("current speed",""+speedometer.getCurrentSpeed());
//
//            }
//        });




        runnable = new Runnable() {
            @Override
            public void run() {

                     int value = 35;
                   // doFakeWork();
//                    pwOne.post(new Runnable() {
//                        @Override
//                        public void run() {
                    //pwOne.setText("" + value + "%");
                   value=(int)speedometer.getCurrentSpeed()+1;
                    speedometer.onSpeedChanged(speedometer.getCurrentSpeed());
//                        }
//                    });
                  value=value+1;
                  speedometer.setCurrentSpeed(value);
                    afficher();

            }
        };

        //handler.postAtTime(runnable,1000);
        // runOnUiThread(runnable);
       // new Thread(runnable).start();

runnable.run();
    }

    public void afficher()
    {
        Toast.makeText(getBaseContext(),
                "test",
                Toast.LENGTH_SHORT).show();
        handler.postDelayed(runnable, 1000);
    }
    private void doFakeWork() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSpeedtestSkipped(SKIP_REASON skip_reason) {


        Toast.makeText(SpeedTestActivity.this, "skipped", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSpeedtestDidStart(String s) {
        Toast.makeText(SpeedTestActivity.this, "didstatrted", Toast.LENGTH_LONG).show();
// the rotate animation operates from -90 to 0 degrees
        // we calculate the angle from 0 to 90, so reduce by 90 for the rotation

    }

    @Override
    public void onSpeedtestDidFinish(String s) {

        Toast.makeText(SpeedTestActivity.this, "Did finished", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSpeedtestDidCancel(String s) {

        Toast.makeText(SpeedTestActivity.this, "did cancel", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSpeedtestTaskDidStart(ROSpeedTest.ROSTType type) {

        if (ROSpeedTest.ROSTType.ROSTTypeDownload == type) {
            refreshSpeedometerScale();
            downloadStart();
        } else if (ROSpeedTest.ROSTType.ROSTTypeUpload == type) {
            refreshSpeedometerScale();
            uploadStart();
        }
    }

    @Override
    public void onSpeedtestTaskDidFinish(ROSpeedTest.ROSTType rostType, ROSTTaskResult rostTaskResult) {


        Toast.makeText(SpeedTestActivity.this, "task did finished", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSpeedtestTask(ROSpeedTest.ROSTType type, double progress, double value) {

        int progressPercent = (int) (progress * 100);

        if (ROSpeedTest.ROSTType.ROSTTypeDownload == type) {
            downloadUpdate(progressPercent, (int) value);
        } else if (ROSpeedTest.ROSTType.ROSTTypeUpload == type) {
            uploadUpdate(progressPercent, (int) value);
        } else {
            updateProgressBar(progressPercent, type);
        }

    }

    @Override
    public WebView getWebView() {
        return null;
    }

    @Override
    public void onSpeedtestServerRequestDidFinish() {

        Toast.makeText(SpeedTestActivity.this, "server request", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSpeedtestServerRequestProgress(int i) {
        updateSpeedo(mCurrSpeedoValue, i);
        mCurrSpeedoValue = i;
        updateProgressBarServerRequest();
    }


    @Override
    public boolean handleMessage(Message message) {
        return false;
    }


    private void uploadStart() {
        mCurrSpeedoValue = 0;
    }

    private void downloadStart() {

//        resetUI();
    }


    private void uploadUpdate(int step, int value) {
        updateProgressBar(step, ROSpeedTest.ROSTType.ROSTTypeUpload);
        updateProgressClock(mCurrSpeedoValue, value);
        mCurrSpeedoValue = value;
    }


    private void updateProgressBarServerRequest() {

        int progress_internal = (100 * mProgressServerRequest) / 40;
        //ProgressBar bar_wait_for_server = (ProgressBar) findViewById(R.id.bar_wait_for_server);

        if (progress_internal <= 100) {
            progressbar.setProgress(progress_internal);
        } else {
            progressbar.setProgress(100);
        }

        mProgressServerRequest++;

    }

    /**
     * Create an animation for the speedometer tacho indicating the current measured download and upload speed
     * <p>
     * F* @param oldValue The last download or upload measurement
     *
     * @param newValue The latest download or upload measurement
     */
    private void updateSpeedo(int oldValue, int newValue) {

        float dVal = ((float) newValue) / 1000;

        //mSpeedoValue.setText(mFormatter.format(dVal));
        speedometer.onSpeedChanged(dVal);

    }

    private void downloadUpdate(int step, int value) {
        updateProgressBar(step, ROSpeedTest.ROSTType.ROSTTypeDownload);
        updateProgressClock(mCurrSpeedoValue, value);
        mCurrSpeedoValue = value;
    }

    private void updateProgressBar(int step, ROSpeedTest.ROSTType type) {

        if (ROSpeedTest.ROSTType.ROSTTypeDownload == type) {

            progressbar.setProgress(step);
        } else if (ROSpeedTest.ROSTType.ROSTTypeUpload == type) {
            progressbar.setProgress(step);
        } else if (ROSpeedTest.ROSTType.ROSTTypePing == type || ROSpeedTest.ROSTType.ROSTTypePingHttp == type) {
            progressbar.setProgress(step);
        }
    }


    private void updateProgressClock(int oldValue, int newValue) {
        long ts = SystemClock.elapsedRealtime();
        if (ts - mLastSpeedoUpdate < 100) {
            return;
        }
        mLastSpeedoUpdate = ts;
        updateSpeedo(oldValue, newValue);
    }

    private void refreshSpeedometerScale() {
        boolean bScaleForFastConnection = true;
        try {
            Context ctx = this;
            ConnectivityManager connMgr = (ctx != null) ? (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE) : null;
            NetworkInfo ni = (connMgr != null) ? connMgr.getActiveNetworkInfo() : null;
            int type = (ni != null) ? ni.getType() : -1;
            if (type == ConnectivityManager.TYPE_WIFI) {
                bScaleForFastConnection = true;
            } else {
                TelephonyManager tm = (ctx != null) ? (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE) : null;

                if (tm != null) {

                    int iNwType = tm.getNetworkType();

                    // 13: LTE
                    // 14: eHRPD
                    // 15: HSPA+
                    if (iNwType < 13 || iNwType == 15)
                        bScaleForFastConnection = false;
                    else
                        bScaleForFastConnection = true;
                }
            }
        } catch (Exception e) {
        }

        if (bScaleForFastConnection) {
            SPEEDO_SCALE_SPEEDS = new float[]{0.0f, 2000.0f, 6000.0f, 20000.0f, 75000.0f, 300000.0f};
            SPEEDO_SCALE_ANGLES = new float[]{1.0f, 20.0f, 38.0f, 56.0f, 74.0f, 90.0f};
            SPEEDO_SCALE_LINEAR = new boolean[]{true, false, false, false, false};
            SPEEDO_SCALE_SEGEMNTS = SPEEDO_SCALE_SPEEDS.length - 1;

            mFormatter = new DecimalFormat("#000.00");

            speedometer.setCurrentSpeed(SPEEDO_SCALE_SPEEDS[2]);
//            if (mSpeedoScaleLabels != null)
//                mSpeedoScaleLabels.setImageResource(R.drawable.angle_values_4g_wifi);
        } else {
            SPEEDO_SCALE_SPEEDS = new float[]{0.0f, 2000.0f, 4000.0f, 10000.0f, 21000.0f, 42000.0f};
            SPEEDO_SCALE_ANGLES = new float[]{1.0f, 20.0f, 38.0f, 56.0f, 74.0f, 90.0f};
            SPEEDO_SCALE_LINEAR = new boolean[]{true, false, false, false, false};
            SPEEDO_SCALE_SEGEMNTS = SPEEDO_SCALE_SPEEDS.length - 1;

            mFormatter = new DecimalFormat("#00.000");

//            if (mSpeedoScaleLabels != null)
//                mSpeedoScaleLabels.setImageResource(R.drawable.angle_values_2g_3g);
        }
    }

}
