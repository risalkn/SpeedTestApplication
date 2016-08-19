package txt.vodfone.com.speedtestapplication.Mainpage;

import android.app.Application;
import android.content.pm.ApplicationInfo;

import com.tm.corelib.ROContext;
import com.tm.corelib.ROContextListener;


public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        try {

            // RO-CoreLib: Create ROContext with RO-CoreLib modules according to the configuration file.
            ROContext roContext = new ROContext(getApplicationContext(),
                    "rocore_android_2016071503_netperform_lib_331_TestEnvironment_DD.CFG",  (ROContextListener) ServiceListener.getInstance());

            // RO-CoreLib: Check whether all required permissions are registered in the Android application manifest file.
            // In case a permission is missing the CoreLib will not work properly.
            // In debug mode logcat output is provided.
            if (0 != (this.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE)) {
                ROContext.evaluateManifestPermissions();
            }

            // RO-CoreLib: Initialize and activate the CoreLib's modules.
            // If the user already opted in before, the measurement service is started automatically.
            roContext.init();

        } catch (Exception e) {
            e.printStackTrace();
            if (0 != (this.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE)) {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }
    }
}
