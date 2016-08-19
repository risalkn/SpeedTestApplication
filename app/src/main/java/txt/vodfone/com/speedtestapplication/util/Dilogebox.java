package txt.vodfone.com.speedtestapplication.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatDialog;
import android.widget.CheckBox;

import com.tm.corelib.ROContext;

import txt.vodfone.com.speedtestapplication.R;

/**
 * Created by gazal on 11/8/16.
 */
public class Dilogebox {



    public void showWarningNoConnection(Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("No data Connection");
        builder.setMessage(R.string.vodafone_error_dilog);

        String positiveText = "close";
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // positive button logic
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }

    /**
     * Show a dialog indicating that the device is currently in roaming state. A user action is required to start
     * the speed test.
     */
    public void showWarningRoaming(Context co) {

        AlertDialog.Builder builder = new AlertDialog.Builder(co);
        builder.setTitle("WarningRoaming");
        builder.setMessage(R.string.vodafone_error_dilog);

        String positiveText = "close";
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // positive button logic
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }

    /**
     * Show a dialog indicating if the background service is not running (the speed test will be aborted) and/or
     * the current active data connection is mobile (it may cause charges for cellular data traffic). A user action
     * is required to start the speed test.
     */
    public void showWarning(Context c) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("showWarning");
        builder.setMessage(R.string.vodafone_error_dilog);

        String positiveText = "close";
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // positive button logic
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();

    }

    public void showerror(Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.vodafone_tittle_error_dilog);
        builder.setMessage(R.string.vodafone_error_dilog);

        String positiveText = "START TEST";
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // positive button logic
                    }
                });

        String negativeText = "CANCEL";
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // negative button logic
                    }
                });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }


    public void showeactivate(Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.vodafone_tittle_error_dilog);
        builder.setMessage(R.string.vodafone_error_dilog);

        String positiveText = "START TEST";
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // positive button logic
                        if (!ROContext.isServiceRunning())
                            ROContext.startService();
                        dialog.dismiss();
                    }
                });

        String negativeText = "CANCEL";
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // negative button logic
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }

    public void showaddistionalpopup(Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.vodafone_tittle_permission_dilog);
        builder.setMessage(R.string.vodafone_permission_dilog);

        String positiveText = "OK,PLEASE PROCEED";
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // positive button logic
                    }
                });

        String negativeText = "NO THANKS";
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // negative button logic
                    }
                });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();

    }


}
