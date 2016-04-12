package edu.depaul.csc472.bouncingobjects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Xiaoping Jia on 10/24/14.
 */
public class MyView extends View {

  private int width, height;
  private boolean paused;

  private long frameCount = 0;
  private long timeStart = 0;

  class MyShape {
    ShapeDrawable drawable;
    int dx = 5, dy = 5;
    MyShape(Shape shape) {
      drawable = new ShapeDrawable(shape);
    }
    MyShape() {}

    void move() {
      Rect bounds = drawable.getBounds();
      if (bounds.right >= width && dx > 0 || bounds.left < 0 && dx < 0) dx = -dx;
      if (bounds.bottom >= height && dy > 0 || bounds.top < 0 && dy < 0) dy = -dy;
      bounds.left += dx;
      bounds.right += dx;
      bounds.top += dy;
      bounds.bottom += dy;
    }

    void setBounds(int left, int top, int right, int bottom) {
      drawable.setBounds(left, top, right, bottom);
    }

    void setVelocity(int dx, int dy) {
      this.dx = dx;
      this.dy = dy;
    }

    void setColor(int color) {
      if (drawable != null) drawable.getPaint().setColor(color);
    }

    void draw(Canvas canvas) {
      drawable.draw(canvas);
    }
  }

  class MyBitmap extends MyShape {
    Bitmap bitmap;
    int x, y;
    MyBitmap(int resId) {
      bitmap = BitmapFactory.decodeResource(getResources(), resId);

    }

    void setBounds(int left, int top, int right, int bottom) {
      x = left;
      y = top;
    }

    void move() {
      int w = bitmap.getWidth();
      int h = bitmap.getHeight();
      if (x + w  >= width && dx > 0 || x < 0 && dx < 0) dx = -dx;
      if (y + h >= height && dy > 0 || y < 0 && dy < 0) dy = -dy;
      x += dx;
      y += dy;
    }

    void draw(Canvas canvas) {
      canvas.drawBitmap(bitmap, x, y, null);
    }

  }

  private List<MyShape> shapes = new ArrayList<MyShape>();

  private Handler mHandler = new Handler();
  private Random random = new Random();

  private Paint paint = new Paint();

  public MyView(Context context) {
    super(context);
    initShapes(5);
  }

  public MyView(Context context, AttributeSet attrs) {
    super(context, attrs);
    initShapes(5);
  }

  private void initShapes(int n) {
    float[] outR = new float[] {6,6,6,6,6,6,6,6};

    shapes.clear();
    for (int i = 0; i < n; i++) {
      switch (i % 8) {
        case 0: shapes.add(new MyShape(new RectShape())); break;
        case 1: shapes.add(new MyShape(new OvalShape())); break;
        case 2: shapes.add(new MyShape(new RoundRectShape(outR, null, null))); break;
        case 3: shapes.add(new MyBitmap(R.drawable.soccer)); break;
        case 4: shapes.add(new MyBitmap(R.drawable.tennis)); break;
        case 5: shapes.add(new MyBitmap(R.drawable.basketball)); break;
        case 6: shapes.add(new MyBitmap(R.drawable.blueghost)); break;
        case 7: shapes.add(new MyBitmap(R.drawable.pumpkin)); break;
      }
    }
  }

  public void restart(int n) {
    if (width > 0 && height > 0) {
      stopAnimation();
      initShapes(n);
      positionShapes();
      startAnimation();
    }
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
  protected void onSizeChanged(int neww, int newh, int oldw, int oldh) {
    super.onSizeChanged(neww, newh, oldw, oldh);
    width = neww;
    height = newh;
    positionShapes();
  }

  private void positionShapes() {
    for (MyShape s : shapes) {
      int w = random.nextInt(50) + 30;
      int h = random.nextInt(50) + 30;
      int x = random.nextInt(width - 2 * w) + w;
      int y = random.nextInt(height - 2 * h) + h;
      s.setColor(getRandomColor());
      s.setBounds(x, y, x + w, y + h);
      s.setVelocity((random.nextBoolean() ? 1 : -1) * (random.nextInt(5) + 2),
          (random.nextBoolean() ? 1 : -1) * (random.nextInt(5) + 2));
    }
  }

  @Override
  protected void onDraw(Canvas canvas) {
    frameCount++;
    long timeNow = System.currentTimeMillis();
    long elapsedTime = timeNow - timeStart;
    float fps = (float) frameCount / elapsedTime * 1000L;

    canvas.drawColor(Color.WHITE);
    for (MyShape shape : shapes) {
      shape.move();
      shape.draw(canvas);
    }

    paint.setColor(Color.BLACK);
    paint.setTextSize(20);
    canvas.drawText("Frame count=" + frameCount + "    Elapsed time=" + elapsedTime +
        "    FPS=" + fps, 20, 40, paint);

    if (!paused) mHandler.postDelayed(update, 15);
  }

  private static int getRandomColor() {
    Random random = new Random();
    return Color.argb(random.nextInt(230) + 26, random.nextInt(256), random.nextInt(256), random.nextInt(256));
  }

}
