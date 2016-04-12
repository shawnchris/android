package edu.depaul.csc472.bouncingball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Xiaoping Jia on 10/24/14.
 */
public class MyView extends View {

  private int x, y, dx = 5, dy = 5, r = 30;
  private int width, height;
  private boolean paused;

  private long frameCount = 0;
  private long timeStart = 0;

  private Paint paint = new Paint();

  private Handler mHandler = new Handler();

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
    x = width / 2;
    y = height / 2;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    frameCount++;
    long timeNow = System.currentTimeMillis();
    long elapsedTime = timeNow - timeStart;
    float fps = (float) frameCount / elapsedTime * 1000L;

    canvas.drawColor(Color.WHITE);
    paint.setColor(Color.BLUE);
    if (x + r >= width || x - r < 0) dx = -dx;
    if (y + r >= height || y - r < 0) dy = -dy;
    x += dx;
    y += dy;
    canvas.drawCircle(x, y, r, paint);

    paint.setColor(Color.BLACK);
    paint.setTextSize(20);
    canvas.drawText("Frame count=" + frameCount + "    Elapsed time=" + elapsedTime +
        "    FPS=" + fps, 20, 40, paint);

    if (!paused) mHandler.postDelayed(update, 15);
  }

}
