package txt.vodfone.com.speedtestapplication.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import txt.vodfone.com.speedtestapplication.R;
import txt.vodfone.com.speedtestapplication.fragments.Network_fragment;
import txt.vodfone.com.speedtestapplication.fragments.Wifi_fragment;

/**
 * Created by gazal on 10/8/16.
 */
public class PageAdapter extends FragmentStatePagerAdapter {
   private  int mNumOfTabs;
   private int imageResId[]={R.drawable.earth,R.drawable.wifi};
    private String tabTitles[]={"NETWORK","WIFI"};
    private Context context;
    public PageAdapter(FragmentManager fm, int NumOfTabs,Context context) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.context=context;
    }

    public View getTabView(int position) {
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        View v = LayoutInflater.from(context).inflate(R.layout.cust_tab, null);
        TextView tv = (TextView) v.findViewById(R.id.txt_tittle);
        tv.setText(tabTitles[position]);
        ImageView img = (ImageView) v.findViewById(R.id.img_logo);
        img.setImageResource(imageResId[position]);
        return v;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Network_fragment tab1 = new Network_fragment();
                return tab1;
            case 1:
                Wifi_fragment tab2 = new Wifi_fragment();
                return tab2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Drawable image = context.getResources().getDrawable(imageResId[position]);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        // Replace blank spaces with image icon
        SpannableString sb = new SpannableString("   " + tabTitles[position]);
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }
}