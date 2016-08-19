package txt.vodfone.com.speedtestapplication.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * TextView that replaces AppName string by actual name of the App
 *
 * Net Perform 3 Sample View
 * Date: 2015-08-24
 *
 * Copyright (c) 2016 RadioOpt GmbH. All rights reserved.
 */
public class TextViewValidate extends TextView {
  public TextViewValidate(Context context) {
    super(context);
  }

  public TextViewValidate(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public TextViewValidate(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @Override
  public void setText (CharSequence text, BufferType type) {
    String newtext = Utils.replaceAppName(text.toString(), getContext());
    super.setText(newtext,type);
  }
}
