package txt.vodfone.com.speedtestapplication.customdigrams;

/**
 * Created by gazal on 11/8/16.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.widget.AbsoluteLayout;
import android.widget.LinearLayout;

import txt.vodfone.com.speedtestapplication.R;


@SuppressWarnings("deprecation")

public class Speedmeterview extends View implements SpeedChangeListener {
    private static final String TAG = Speedmeterview.class.getSimpleName();
    public static final float DEFAULT_MAX_SPEED = 245; // Assuming this is km/h and you drive a super-car

    // Speedometer internal state
    private float mMaxSpeed;
    private float mCurrentSpeed;
    private float morginalspeed;
    // Scale drawing tools
    private Paint onMarkPaint;
    private Paint offMarkPaint;
    private Paint scalePaint;
    private Paint readingPaint;
    private Path onPath;
    private Path offPath;
    final RectF oval = new RectF();
    final RectF oval1 = new RectF();
    final RectF ovalvalues = new RectF();
    // Drawing colors
    private int ON_COLOR = Color.argb(255, 0xff, 0xA5, 0x00);
    private int OFF_COLOR = Color.argb(255, 0x3e, 0x3e, 0x3e);
    private int SCALE_COLOR = Color.argb(255, 255, 255, 255);
    private float SCALE_SIZE = 12f;
    private float READING_SIZE = 60f;

    // Scale configuration
    private float centerX;
    private float centerY;
    private float radius;

    public Speedmeterview(Context context) {
        super(context);
    }

    public Speedmeterview(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.Speedometer,
                0, 0);
        try {
            mMaxSpeed = a.getFloat(R.styleable.Speedometer_maxSpeed, DEFAULT_MAX_SPEED);
            mCurrentSpeed = a.getFloat(R.styleable.Speedometer_currentSpeed, 0);
            ON_COLOR = a.getColor(R.styleable.Speedometer_onColor, ON_COLOR);
            OFF_COLOR = a.getColor(R.styleable.Speedometer_offColor, OFF_COLOR);
            SCALE_COLOR = a.getColor(R.styleable.Speedometer_scaleColor, SCALE_COLOR);
            SCALE_SIZE = a.getDimension(R.styleable.Speedometer_scaleTextSize, SCALE_SIZE);
            READING_SIZE = a.getDimension(R.styleable.Speedometer_readingTextSize, READING_SIZE);
        } finally {
            a.recycle();
        }
        initDrawingTools();
    }

    private void initDrawingTools() {
        onMarkPaint = new Paint();
        onMarkPaint.setStyle(Paint.Style.STROKE);
        onMarkPaint.setColor(getResources().getColor(R.color.color_light_grey_30));
        onMarkPaint.setStrokeWidth(5f);
        onMarkPaint.setShadowLayer(0f, 0f, 0f, ON_COLOR);
        onMarkPaint.setAntiAlias(true);

        offMarkPaint = new Paint(onMarkPaint);
        offMarkPaint.setColor(getResources().getColor(R.color.color_aqua_blue));
        offMarkPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        offMarkPaint.setShadowLayer(0f, 0f, 0f, OFF_COLOR);

        scalePaint = new Paint(offMarkPaint);
        scalePaint.setStrokeWidth(1f);
        scalePaint.setTextSize(SCALE_SIZE);
        scalePaint.setShadowLayer(5f, 0f, 0f, Color.RED);
        scalePaint.setColor(SCALE_COLOR);

        readingPaint = new Paint(scalePaint);
        readingPaint.setStyle(Paint.Style.STROKE);
        offMarkPaint.setShadowLayer(2f, 0f, 0f, getResources().getColor(R.color.color_aqua_blue));
        readingPaint.setTextSize(65f);
        readingPaint.setTypeface(Typeface.SANS_SERIF);
        readingPaint.setColor(Color.BLACK);

        onPath = new Path();
        offPath = new Path();
    }

    public float getCurrentSpeed() {
        return mCurrentSpeed;
    }

    public void setCurrentSpeed(float mCurrentSpeed) {
        if (mCurrentSpeed > 330) {
            this.mCurrentSpeed = 330;
            this.morginalspeed = 245;
//            if(mCurrentSpeed>(330/mMaxSpeed)*180-226)
//            {
//                this.mCurrentSpeed = (330/mMaxSpeed)*180-226;
//                this.morginalspeed=245;
//            }
        } else if (mCurrentSpeed <= 35) {
            this.mCurrentSpeed = 35;
            this.morginalspeed = 35;
        } else {
            this.mCurrentSpeed = mCurrentSpeed;

        }
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh) {

        // Setting up the oval area in which the arc will be drawn
        if (width > height) {
            radius = height / 3;
        } else {
            radius = width / 3;
        }
        oval.set(centerX - radius,
                centerY - radius,
                centerX + radius,
                centerY + radius);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		Log.d(TAG, "Width spec: " + MeasureSpec.toString(widthMeasureSpec));
//		Log.d(TAG, "Height spec: " + MeasureSpec.toString(heightMeasureSpec));

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int chosenWidth = chooseDimension(widthMode, widthSize);
        int chosenHeight = chooseDimension(heightMode, heightSize);

        int chosenDimension = Math.min(chosenWidth, chosenHeight);
        centerX = chosenDimension / 2;
        centerY = chosenDimension / 2;
        setMeasuredDimension(chosenDimension, chosenDimension);
    }

    private int chooseDimension(int mode, int size) {
        if (mode == MeasureSpec.AT_MOST || mode == MeasureSpec.EXACTLY) {
            return size;
        } else { // (mode == MeasureSpec.UNSPECIFIED)
            return getPreferredSize();
        }
    }

    // in case there is no size specified
    private int getPreferredSize() {
        return 300;
    }

    @Override
    public void onDraw(Canvas canvas) {
        drawScaleBackground(canvas);
        drawScale(canvas);
        drawScaleinside(canvas);
        drawmeterdies(canvas);
        drawLegend(canvas);
//        drawReading(canvas);
        drawNeedil(canvas);

    }

    /**
     * Draws the segments in their OFF state
     *
     * @param canvas
     */
    private void drawScaleBackground(Canvas canvas) {
        offPath.reset();
        for (int i = -220; i < 40; i += 1) {
            offPath.addArc(oval, i, 4f);
        }
        canvas.drawPath(offPath, offMarkPaint);
    }

    private void drawScale(Canvas canvas) {
        onPath.reset();
        oval1.set(centerX + 30 - radius,
                centerY + 30 - radius,
                centerX - 30 + radius,
                centerY - 30 + radius);
//        for(int i = -220; i < (mCurrentSpeed/mMaxSpeed)*180 - 180+40; i+=1){
//            onPath.addArc(oval1, i, 1f);
//        }

        for (int i = -220; i < 40; i += 1) {
            onPath.addArc(oval1, i, 3f);
            //onPath.addArc(oval1,j, 9f);

        }
//        canvas.drawPath(onPath, onMarkPaint);
//            for(int i = -220; i <40; i+=35){
//            onPath.addRect();oval1, i, 10f);
//            }
        canvas.drawPath(onPath, onMarkPaint);
        //onPath.reset();
    }


    private void drawNeedil(Canvas canvas) {
        Path path = new Path();
        ovalvalues.set(centerX + 30 - radius,
                centerY + 30 - radius,
                centerX - 30 + radius,
                centerY - 30 + radius);


        //path.addOval(ovalvalues,Path.Direction.CW);
        path.addArc(ovalvalues, (mCurrentSpeed / mMaxSpeed) * 180 - 226, 1f);

        Paint paintdies = new Paint();
        paintdies.setStyle(Paint.Style.FILL_AND_STROKE);

        paintdies.setColor(Color.RED);
        paintdies.setStrokeWidth(120f);
//        paintdies.setst
        //paintdies.setAntiAlias(true);
        canvas.drawPath(path, paintdies);
    }

    private void drawScaleinside(Canvas canvas) {
        onPath.reset();
        oval1.set(centerX + 80 - radius,
                centerY + 80 - radius,
                centerX - 80 + radius,
                centerY - 80 + radius);

        for (int i = -220; i < 40; i += 1) {
            onPath.addArc(oval1, i, 1f);
            //onPath.addArc(oval1,j, 9f);

        }
        Paint paintdies = new Paint();
        paintdies.setStyle(Paint.Style.STROKE);
        paintdies.setColor(getResources().getColor(R.color.color_light_grey_50));
        paintdies.setStrokeWidth(2f);
//        paintdies.setst
        paintdies.setAntiAlias(true);

        canvas.drawPath(onPath, paintdies);
        //onPath.reset();
    }

    private void drawmeterdies(Canvas canvas) {
        // onPath.reset();
        Path path = new Path();
        ovalvalues.set(centerX - 30 - radius,
                centerY - 30 - radius,
                centerX + 30 + radius,
                centerY + 30 + radius);
        for (int j = -200; j <= 40; j++) {
            path.addArc(ovalvalues, j, 1f);
            j = j + 35;
        }
        Paint paintdies = new Paint();
        paintdies.setStyle(Paint.Style.STROKE);
        paintdies.setColor(getResources().getColor(R.color.color_dark_grey_60));
        paintdies.setStrokeWidth(130f);
//        paintdies.setst
        paintdies.setAntiAlias(true);
        canvas.drawPath(path, paintdies);
    }

    private void drawLegend(Canvas canvas) {

        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        canvas.rotate(-240, centerX, centerY);
        Path circle = new Path();
        double halfCircumference = 820 + radius * Math.PI;
        double increments = 35;
        for (int i = 35; i < this.mMaxSpeed + 35; i += increments) {
            circle.addCircle(centerX, centerY, radius + 100, Path.Direction.CW);
            scalePaint.setTextSize(40);
            scalePaint.setColor(Color.BLACK);
            canvas.drawTextOnPath(String.format("%d", i), circle, (float) (8 + i * halfCircumference / this.mMaxSpeed), -20f, scalePaint);
        }

        canvas.restore();
    }

    private void drawReading(Canvas canvas) {

        Paint paintdies = new Paint();
        // paintdies.setColor(Color.BLACK);
//        paintdies.setst
        paintdies.setAntiAlias(true);


        Path path = new Path();
//        String message = String.format("%d", (int)this.mCurrentSpeed);
        String message = "Preparing your speed test 4G";
        float[] widths = new float[message.length() + 30];
        paintdies.getTextWidths(message, widths);
        float advance = 0;
        for (double width : widths)
            advance += width;
        path.moveTo(centerX - advance, centerY);
        path.lineTo(centerX + advance, centerY);
        paintdies.setTextSize(30);
        paintdies.setColor(Color.BLACK);
        canvas.drawTextOnPath(message, path, 0f, 0f, paintdies);
    }

    @Override
    public void onSpeedChanged(float newSpeedValue) {
        this.setCurrentSpeed(newSpeedValue);
        this.invalidate();
    }
}