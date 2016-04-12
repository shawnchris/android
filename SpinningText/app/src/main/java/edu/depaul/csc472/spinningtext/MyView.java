package edu.depaul.csc472.spinningtext;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

/**
 * Created by Xiaoping Jia on 10/24/14.
 */
public class MyView extends View {

  private int rotation = 0, dr = 5;
  private int width, height;
  private boolean paused;

  private Paint paint = new Paint();
  private int color = Color.BLUE;
  private Typeface typeface = Typeface.DEFAULT;

  private Handler mHandler = new Handler();

  private long frameCount = 0;
  private long timeStart = 0;

  public MyView(Context context) {
    super(context);
  }

  public MyView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public void startAnimation() {
    paused = false;
    frameCount = 0;
    timeStart = System.currentTimeMillis();
    mHandler.post(update);
  }

  public void stopAnimation() {
    paused = true;
  }

  private Runnable update = new Runnable() {
    @Override
    public void run() {
      invalidate();
    }
  };

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    width = w;
    height = h;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    frameCount++;
    long timeNow = System.currentTimeMillis();
    long elapsedTime = timeNow - timeStart;
    float fps = (float) frameCount / elapsedTime * 1000L;

    canvas.drawColor(Color.WHITE);

    paint.setAntiAlias(true);
    paint.setColor(Color.BLACK);
    paint.setTextSize(20);
    paint.setTypeface(Typeface.DEFAULT);
    paint.setTextAlign(Paint.Align.LEFT);
    canvas.drawText("Frame count=" + frameCount + "    Elapsed time=" + elapsedTime +
        "    FPS=" + fps, 20, 40, paint);

    rotation += dr;
    if (rotation >= 360) {
      rotation %= 360;
      color = getRandomColor();
      typeface = getRandomTypeface();
    }

    paint.setColor(color);
    paint.setTextSize(50);
    paint.setTypeface(typeface);
    paint.setTextAlign(Paint.Align.CENTER);

    canvas.translate(width/2, height/2);
    canvas.rotate(rotation);
    canvas.drawText("Android 6.0 Marshmallow", 0, 0, paint);

    if (!paused) mHandler.postDelayed(update, 15);
  }

  private static int getRandomColor() {
    Random random = new Random();
    return Color.rgb(random.nextInt(256),random.nextInt(256), random.nextInt(256));
  }

  static final Typeface[] TYPEFACE = {
      Typeface.DEFAULT, Typeface.MONOSPACE, Typeface.SERIF, Typeface.SANS_SERIF
  };

  static final int[] STYLE = {
      Typeface.NORMAL, Typeface.BOLD, Typeface.ITALIC, Typeface.BOLD_ITALIC
  };

  private static Typeface getRandomTypeface() {
    Random random = new Random();
    return Typeface.create(TYPEFACE[random.nextInt(4)], STYLE[random.nextInt(4)]);
  }

}
