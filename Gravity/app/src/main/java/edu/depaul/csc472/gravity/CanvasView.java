package edu.depaul.csc472.gravity;

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
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Xiaoping Jia on 10/24/14.
 */
public class CanvasView extends SurfaceView implements SurfaceHolder.Callback {

  private int width, height;
  private boolean done;
  private boolean surfaceAvailble;

  private long frameCount = 0;
  private long timeStart = 0;

  private SurfaceHolder holder;

  private SensorManager sensorManager;
  private Sensor sensor;
  private SensorEventListener listener;
  private float[] sensorData = new float[3];

  private static final int MAX_V = 15;
  private static final float GRAVITY_F = 0.2f;

  class MyShape {
    ShapeDrawable drawable;
    float dx = 5, dy = 5;
    MyShape(Shape shape) {
      drawable = new ShapeDrawable(shape);
    }
    MyShape() {}

    void move() {
      float gx = sensorData[1]; // landscape mode
      float gy = sensorData[0]; // landscape mode
      dx += gx * GRAVITY_F;
      dy += gy * GRAVITY_F;
      dx = Math.min(Math.max(dx, -MAX_V), MAX_V);
      dy = Math.min(Math.max(dy, -MAX_V), MAX_V);
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

    void setVelocity(float dx, float dy) {
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
    float x, y;
    MyBitmap(int resId) {
      bitmap = BitmapFactory.decodeResource(getResources(), resId);

    }

    void setBounds(int left, int top, int right, int bottom) {
      x = left;
      y = top;
    }

    void move() {
      float gx = sensorData[1]; // landscape mode
      float gy = sensorData[0]; // landscape mode
      dx += gx * GRAVITY_F;
      dy += gy * GRAVITY_F;
      dx = Math.min(Math.max(dx, -MAX_V), MAX_V);
      dy = Math.min(Math.max(dy, -MAX_V), MAX_V);

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

  //// SurfaceHolder.Callback

  @Override
  public void surfaceCreated(SurfaceHolder surfaceHolder) {
    surfaceAvailble = true;
    startAnimation();
  }

  @Override
  public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
    this.width = width;
    this.height = height;
    stopAnimation();
    synchronized (holder) {
      positionShapes();
    }
    startAnimation();
  }

  @Override
  public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    surfaceAvailble = false;
    stopAnimation();
  }

  //////

  private List<MyShape> shapes = new ArrayList<MyShape>();

  private Random random = new Random();

  private Paint paint = new Paint();

  public CanvasView(Context context) {
    super(context);
    init();
  }

  public CanvasView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  private void init() {
    holder = getHolder();
    holder.addCallback(this);
    initShapes(5);
    sensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
    initSensor(Sensor.TYPE_GRAVITY);
  }

  private void initSensor(int type) {
    sensor = sensorManager.getDefaultSensor(type);
    if (sensor != null) {
      listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
          for (int i = 0; i < 3; i++)
            sensorData[i] = event.values[i];
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
      };
    }
  }

  public void initShapes(int n) {
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


  public void restart(int n, int sensorType) {
    if (width > 0 && height > 0) {
      stopAnimation();
      synchronized (holder) {
        initShapes(n);
        positionShapes();
      }
      if (sensor != null &&
          sensorType != sensor.getType()) {
        initSensor(sensorType);
      }
      startAnimation();
    }
  }

  public void startAnimation() {
    done = false;
    if (listener != null)
      sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_GAME);
    if (surfaceAvailble) startRenderingThread();
  }

  private void startRenderingThread() {
    frameCount = 0;
    timeStart = System.currentTimeMillis();
    new Thread(new Runnable() {
      public void run() {
        while (!done) {
          Canvas c = null;
          try {
            c = holder.lockCanvas();
            synchronized (holder) {
                doDraw(c);
            }
          } finally {
            if (c != null) {
              holder.unlockCanvasAndPost(c);
            }
          }
        }
      }
    }).start();

  }

  public void stopAnimation() {
    done = true;
    if (listener != null)
      sensorManager.unregisterListener(listener);
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

  protected void doDraw(Canvas canvas) {
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
    canvas.drawText(String.format("Frame count=%d    Elapsed time=%d    FPS=%f   sensorData[0]=%f sensorData[1]=%f sensorData[2]=%f",
        frameCount, elapsedTime, fps, sensorData[0], sensorData[1], sensorData[2]), 20, 40, paint);
  }

  private static int getRandomColor() {
    Random random = new Random();
    return Color.argb(random.nextInt(230) + 26, random.nextInt(256), random.nextInt(256), random.nextInt(256));
  }


}
