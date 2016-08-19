package txt.vodfone.com.speedtestapplication.Mainpage;

import android.Manifest;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tm.corelib.ROContext;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import txt.vodfone.com.speedtestapplication.Adapter.PageAdapter;
import txt.vodfone.com.speedtestapplication.R;
import txt.vodfone.com.speedtestapplication.Speedtest.SpeedTestActivity;
import txt.vodfone.com.speedtestapplication.fragments.Wifi_fragment;
import txt.vodfone.com.speedtestapplication.util.Dilogebox;
import txt.vodfone.com.speedtestapplication.util.Utils;


public class Speedchekerhome extends AppCompatActivity {

    private TabLayout tabLayout;
    private Button btn_start;
    private Dilogebox dilogebox = new Dilogebox();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speedchekerhome);

        Log.d("speedcheker", "" + ROContext.isServiceRunning());
        init();


    }

    private void init() {
        if (ROContext.isServiceRunning()) {
            tabLayout = (TabLayout) findViewById(R.id.tab_layout);
            tabLayout.addTab(tabLayout.newTab().setText(R.string.vodafone_network));
            tabLayout.addTab(tabLayout.newTab().setText(R.string.vodafone_wifi));
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            btn_start = (Button) findViewById(R.id.btn_start);
            btn_start.setOnClickListener(new View.OnClickListener() {

                                             @Override
                                             public void onClick(View view) {
//                                                 if (!Utils.isRadioOn())
//                                                     dilogebox.showWarningNoConnection(Speedchekerhome.this);
//                                                 else if (Utils.isCurrentConnectionMobile() && Utils.isCurrentConnectionRoaming())
//                                                     dilogebox.showWarningRoaming(Speedchekerhome.this);
//                                                 else if (Utils.isCurrentConnectionMobile() || !ROContext.isServiceRunning())
//                                                     dilogebox.showWarning(Speedchekerhome.this);
//                                                 else {
                                                     startActivity(new Intent(Speedchekerhome.this, SpeedTestActivity.class));
//                                                 }

                                             }
                                         }
            );
            final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
            final PageAdapter adapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), Speedchekerhome.this);
            viewPager.setAdapter(adapter);
            // Iterate over all tabs and set the custom view
            for (int i = 0; i < tabLayout.getTabCount(); i++) {
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                tab.setCustomView(adapter.getTabView(i));
            }
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        } else {


            dilogebox.showeactivate(Speedchekerhome.this);


        }


    }


}

