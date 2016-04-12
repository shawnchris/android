package edu.depaul.csc472.scalegesturedemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xiaoping Jia on 10/24/14.
 */
public class MyView extends View {

  private static final String TAG = "MyView";

  private Paint paint = new Paint();

  private Handler handler = new Handler();

  private ScaleGestureDetector gestureDetector;
  private float scaleFactor = 1.f;


  public MyView(Context context) {
    super(context);
    gestureDetector = new ScaleGestureDetector(context, new ScaleListener());
  }

  public MyView(Context context, AttributeSet attrs) {
    super(context, attrs);
    gestureDetector = new ScaleGestureDetector(context, new ScaleListener());
  }


  @Override
  protected void onDraw(Canvas canvas) {

    canvas.drawColor(Color.WHITE);

    paint.setAntiAlias(true);
    paint.setColor(Color.BLACK);
    paint.setTypeface(Typeface.DEFAULT);
    paint.setTextSize(20);

    canvas.drawText("Touch event: " + eventMessage, 20, 40, paint);
    for (int p = 0; p < 5; p++) {
      paint.setColor(colors[p]);
      String msg = "Pointer " + p + ": ";
      if (pointers[p] != null) {
        int size = locations[p].size();
        for (Point point : locations[p]) {
          canvas.drawCircle(point.x, point.y, 50 + 10 * pointerCount, paint);
        }
        msg += pointers[p];
      }
      canvas.drawText(msg, 20, 60 + 20 * p, paint);
    }

    int size = (int) (30 * scaleFactor);
    paint.setColor(Color.BLACK);
    paint.setTypeface(Typeface.DEFAULT_BOLD);
    paint.setTextSize(size);
    canvas.drawText(gestureMessage, 20, 140 + size, paint);

    if (focus != null) {
      paint.setColor(Color.RED);
      paint.setStrokeWidth(20);
      paint.setStrokeCap(Paint.Cap.ROUND);
      canvas.drawCircle(focus.x, focus.y, 10, paint);
    }
  }


  float startX, startY;

  public boolean onTouchEvent(MotionEvent event) {
    this.gestureDetector.onTouchEvent(event);


    logEvent(event);
    invalidate();

    int action = event.getAction();
    if (action == MotionEvent.ACTION_UP ||
        action == MotionEvent.ACTION_CANCEL) {
      getHandler().postDelayed(new Runnable() {
        @Override
        public void run() {
          eventMessage = "No Event";
          gestureMessage = "";
          focus = null;
          for (int i = 0; i < 5; i++) {
            pointers[i] = null;
            locations[i] = null;
          }
          invalidate();
        }
      }, 1000);
    }


    return true;
  }

  String eventMessage = "No Event";
  int pointerCount;
  String[] pointers = new String[5];
  List<Point>[] locations = new List[5];
  int[] colors = { Color.BLUE, Color.GREEN, Color.MAGENTA, Color.CYAN, Color.YELLOW };

  String gestureMessage = "";
  Point focus;
  float spanX, spanY;


  void logEvent(MotionEvent event) {
    for (int i = 0; i < 5; i++) {
      pointers[i] = null;
      locations[i] = null;
    }

    int action = event.getActionMasked();
    float eventX = event.getX();
    float eventY = event.getY();
    int historySize = event.getHistorySize();
    pointerCount = event.getPointerCount();

    String actionName = MotionEvent.actionToString(action);
    if (action == MotionEvent.ACTION_POINTER_DOWN || action == MotionEvent.ACTION_POINTER_UP) {
      actionName += (" " + event.getActionIndex());
    }

    Log.d(TAG, actionName +
        String.format(" (%f,%f) pointerCount=%d historySize=%d", eventX, eventY, pointerCount, historySize));
    eventMessage = actionName + String.format(" (%d,%d) pointerCount=%d", (int) eventX, (int) eventY, pointerCount);
    for (int p = 0; p < pointerCount; p++) {
      int pointerId = event.getPointerId(p);
      int pointerX = (int) event.getX(p);
      int pointerY = (int) event.getY(p);
      Log.d(TAG, "=== pointerId: " + pointerId + "  " + actionName +
          String.format(" (%d,%d)",  pointerX, pointerY));
      if (pointerId >= 0 && pointerId < 5) {
        pointers[pointerId] = actionName + " (" + pointerX + ", " + pointerY + ") historySize=" + historySize;
        locations[pointerId] = new ArrayList<Point>();
        for (int h = 0; h < historySize; h++) {
          int pointerHX = (int) event.getHistoricalX(p, h);
          int pointerHY = (int) event.getHistoricalY(p, h);
          Log.d(TAG, "=== pointerId: " + pointerId + "  " + actionName +
              String.format(" history[%d] (%d,%d)", h, pointerHX, pointerHY));
          locations[pointerId].add(new Point(pointerHX, pointerHY));
        }
        locations[pointerId].add(new Point(pointerX, pointerY));
      }
    }
  }


  private class ScaleListener
      extends ScaleGestureDetector.SimpleOnScaleGestureListener {
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
      scaleFactor *= detector.getScaleFactor();

      // Don't let the object get too small or too large.
      scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 5.0f));

      gestureMessage = "scaleFactor=" + scaleFactor;
      focus = new Point((int) detector.getFocusX(), (int) detector.getFocusY());
      spanX = detector.getCurrentSpanX();
      spanY = detector.getCurrentSpanY();
      return true;
    }
  }

}
