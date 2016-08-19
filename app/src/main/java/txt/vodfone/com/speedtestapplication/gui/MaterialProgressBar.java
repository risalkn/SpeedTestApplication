package txt.vodfone.com.speedtestapplication.gui;

/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.widget.ImageView;
import txt.vodfone.com.speedtestapplication.R;


public class MaterialProgressBar extends ImageView {

  private static final int KEY_SHADOW_COLOR = 0x1E000000;
  private static final int FILL_SHADOW_COLOR = 0x3D000000;
  // PX
  private static final float X_OFFSET = 0f;
  private static final float Y_OFFSET = 1.75f;
  private static final float SHADOW_RADIUS = 3.5f;
  private static final int SHADOW_ELEVATION = 4;


  private static final int DEFAULT_CIRCLE_BG_LIGHT = 0xFFFAFAFA;
  private static final int DEFAULT_CIRCLE_DIAMETER = 56;
  private static final int STROKE_WIDTH_LARGE = 3;
  public static final int DEFAULT_TEXT_SIZE = 9;

  private Animation.AnimationListener mListener;
  private int mProgressColor;
  private int mProgressStokeWidth;
  private int mProgress;
  private int mMax;
  private int mDiameter;
  private int mInnerRadius;
  private MaterialProgressDrawable mProgressDrawable;
  private int[] mColors = new int[]{Color.BLACK};

  public MaterialProgressBar(Context context) {
    super(context);
    init(context, null, 0);
  }

  public MaterialProgressBar(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs, 0);
  }

  public MaterialProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs, defStyleAttr);
  }

  private void init(Context context, AttributeSet attrs, int defStyleAttr) {
    final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MaterialProgressBar, defStyleAttr, 0);

    final float density = context.getResources().getDisplayMetrics().density;

    mProgressColor = a.getColor(R.styleable.MaterialProgressBar_progress_color, DEFAULT_CIRCLE_BG_LIGHT);
    mColors[0] = getResources().getColor(R.color.color_brand_bars);

    mInnerRadius = a.getDimensionPixelOffset(R.styleable.MaterialProgressBar_inner_radius, -1);

    mProgressStokeWidth = a.getDimensionPixelOffset(R.styleable.MaterialProgressBar_progress_stroke_width, (int) (STROKE_WIDTH_LARGE * density));

    mProgress = a.getInt(R.styleable.MaterialProgressBar_progress, 0);
    mMax = a.getInt(R.styleable.MaterialProgressBar_max, 100);
    a.recycle();

    mProgressDrawable = new MaterialProgressDrawable(getContext(), this);
    super.setImageDrawable(mProgressDrawable);
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    super.onLayout(changed, left, top, right, bottom);
    final float density = getContext().getResources().getDisplayMetrics().density;
    mDiameter = Math.min(getMeasuredWidth(), getMeasuredHeight());
    if (mDiameter <= 0) {
      mDiameter = (int) density * DEFAULT_CIRCLE_DIAMETER;
    }
    mProgressDrawable.setColorSchemeColors(mColors);
    mProgressDrawable.setSizeParameters(mDiameter, mDiameter,mInnerRadius <= 0 ? (mDiameter - mProgressStokeWidth * 2) / 4 : mInnerRadius, mProgressStokeWidth);
    super.setImageDrawable(null);
    super.setImageDrawable(mProgressDrawable);
    mProgressDrawable.setAlpha(255);
    mProgressDrawable.start();
  }

  @Override
  final public void setImageResource(int resId) {

  }

  @Override
  final public void setImageURI(Uri uri) {
    super.setImageURI(uri);
  }

  @Override
  final public void setImageDrawable(Drawable drawable) {}

  public void setAnimationListener(Animation.AnimationListener listener) {
    mListener = listener;
  }

  @Override
  public void onAnimationStart() {
    super.onAnimationStart();
    if (mListener != null) {
      mListener.onAnimationStart(getAnimation());
    }
  }

  @Override
  public void onAnimationEnd() {
    super.onAnimationEnd();
    if (mListener != null) {
      mListener.onAnimationEnd(getAnimation());
    }
  }

  public void setColorSchemeResources(int... colorResIds) {
    final Resources res = getResources();
    int[] colorRes = new int[colorResIds.length];
    for (int i = 0; i < colorResIds.length; i++) {
      colorRes[i] = res.getColor(colorResIds[i]);
    }
    setColorSchemeColors(colorRes);
  }

  public void setColorSchemeColors(int... colors) {
    mColors = colors;
    if (mProgressDrawable != null) {
      mProgressDrawable.setColorSchemeColors(colors);
    }
  }

  public void setBackgroundColor(int colorRes) {
    if (getBackground() instanceof ShapeDrawable) {
      final Resources res = getResources();
      ((ShapeDrawable) getBackground()).getPaint().setColor(res.getColor(colorRes));
    }
  }

  public int getMax() {
    return mMax;
  }

  public void setMax(int max) {
    mMax = max;
  }

  public int getProgress() {
    return mProgress;
  }

  public void setProgress(int progress) {
    if (getMax() > 0) {
      mProgress = progress;
    }
  }

  @Override
  public void setVisibility(int visibility) {
    super.setVisibility(visibility);
    if (mProgressDrawable != null) {
      if (visibility != VISIBLE) {
        mProgressDrawable.stop();
      }
      mProgressDrawable.setVisible(visibility == VISIBLE, false);
    }
  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    if (mProgressDrawable != null) {
      mProgressDrawable.stop();
      mProgressDrawable.setVisible(getVisibility() == VISIBLE, false);
    }
  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    if (mProgressDrawable != null) {
      mProgressDrawable.stop();
      mProgressDrawable.setVisible(false, false);
    }
  }

}