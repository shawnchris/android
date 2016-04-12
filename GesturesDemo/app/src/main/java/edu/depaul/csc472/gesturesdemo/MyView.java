package edu.depaul.csc472.gesturesdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.shapes.Shape;
import android.os.Handler;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xiaoping Jia on 10/24/14.
 */
public class MyView extends View
    implements
    GestureDetector.OnGestureListener,
    GestureDetector.OnDoubleTapListener {

  private static final String TAG = "MyView";

  private Paint paint = new Paint();

  private Handler handler = new Handler();

  private GestureDetectorCompat gestureDetector;

  public MyView(Context context) {
    super(context);
    // Instantiate the gesture detector with the
    // application context and an implementation of
    // GestureDetector.OnGestureListener
    gestureDetector = new GestureDetectorCompat(context, this);

    // Set the gesture detector as the double tap
    // listener.
    gestureDetector.setOnDoubleTapListener(this);
  }

  public MyView(Context context, AttributeSet attrs) {
    super(context, attrs);
    // Instantiate the gesture detector with the
    // application context and an implementation of
    // GestureDetector.OnGestureListener
    //
    gestureDetector = new GestureDetectorCompat(context, this);
    // Set the gesture detector as the double tap
    // listener.
    gestureDetector.setOnDoubleTapListener(this);
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
        float f = 1f;
        for (Point point : locations[p]) {
          canvas.drawCircle(point.x, point.y, 50 + 10 * pointerCount, paint);
          f -= .5f / size;
        }
        msg += pointers[p];
      }
      canvas.drawText(msg, 20, 60 + 20 * p, paint);
    }

    paint.setColor(Color.BLACK);
    paint.setTypeface(Typeface.DEFAULT_BOLD);
    paint.setTextSize(30);
    canvas.drawText("Gesture: " + gestureMessage, 20, 170, paint);
    canvas.drawText("Parameters: " + gestureParam, 20, 200, paint);

    if (start != null && end != null) {
      paint.setColor(Color.RED);
      paint.setStrokeWidth(20);
      paint.setStrokeCap(Paint.Cap.ROUND);
      canvas.drawLine(start.x, start.y, end.x, end.y, paint);
    }
  }

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
          gestureParam = "";
          start = end = null;
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
  int[] colors = {Color.BLUE, Color.GREEN, Color.MAGENTA, Color.CYAN, Color.YELLOW};

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

  String gestureMessage = "";
  String gestureParam = "";
  Point start, end;

  private void logGesture(String msg, MotionEvent e) {
    Log.d(TAG, msg + ": " + e);
    start = end = null;
    gestureParam = "";
    int pointerCount = e.getPointerCount();
    gestureMessage = msg + String.format(": (%d,%d)x%d",
        (int) e.getX(), (int) e.getY(), pointerCount);
  }


  private void logGesture(String msg, String param,
                          MotionEvent e1, MotionEvent e2) {
    Log.d(TAG, msg + ": e1=" + e1 + " e2=" + e2);
    int pointerCount1 = e1.getPointerCount();
    int pointerCount2 = e2.getPointerCount();
    gestureMessage = msg + String.format(": (%d,%d)x%d (%d,%d)x%d",
        (int) e1.getX(), (int) e1.getY(), pointerCount1,
        (int) e2.getX(), (int) e2.getY(), pointerCount2);
    gestureParam = param;
    start = new Point((int) e1.getX(), (int) e1.getY());
    end = new Point((int) e2.getX(), (int) e2.getY());
  }

  /**
   * Notified when a single-tap occurs.
   * <p/>
   * Unlike {@link GestureDetector.OnGestureListener#onSingleTapUp(MotionEvent)}, this
   * will only be called after the detector is confident that the user's
   * first tap is not followed by a second tap leading to a double-tap
   * gesture.
   *
   * @param e The down motion event of the single-tap.
   * @return true if the event is consumed, else false
   */
  @Override
  public boolean onSingleTapConfirmed(MotionEvent e) {
    logGesture("onSingleTapConfirmed", e);
    return true;
  }

  /**
   * Notified when a double-tap occurs.
   *
   * @param e The down motion event of the first tap of the double-tap.
   * @return true if the event is consumed, else false
   */
  @Override
  public boolean onDoubleTap(MotionEvent e) {
    logGesture("onDoubleTap", e);
    return true;
  }

  /**
   * Notified when an event within a double-tap gesture occurs, including
   * the down, move, and up events.
   *
   * @param e The motion event that occurred during the double-tap gesture.
   * @return true if the event is consumed, else false
   */
  @Override
  public boolean onDoubleTapEvent(MotionEvent e) {
    logGesture("onDoubleTapEvent", e);
    return true;
  }

  /**
   * Notified when a tap occurs with the down {@link MotionEvent}
   * that triggered it. This will be triggered immediately for
   * every down event. All other events should be preceded by this.
   *
   * @param e The down motion event.
   */
  @Override
  public boolean onDown(MotionEvent e) {
    logGesture("onDown", e);
    return true;
  }

  /**
   * The user has performed a down {@link MotionEvent} and not performed
   * a move or up yet. This event is commonly used to provide visual
   * feedback to the user to let them know that their action has been
   * recognized i.e. highlight an element.
   *
   * @param e The down motion event
   */
  @Override
  public void onShowPress(MotionEvent e) {
    logGesture("onShowPress", e);
  }

  /**
   * Notified when a tap occurs with the up {@link MotionEvent}
   * that triggered it.
   *
   * @param e The up motion event that completed the first tap
   * @return true if the event is consumed, else false
   */
  @Override
  public boolean onSingleTapUp(MotionEvent e) {
    logGesture("onSingleTapUp", e);
    return true;
  }

  /**
   * Notified when a scroll occurs with the initial on down {@link MotionEvent} and the
   * current move {@link MotionEvent}. The distance in x and y is also supplied for
   * convenience.
   *
   * @param e1        The first down motion event that started the scrolling.
   * @param e2        The move motion event that triggered the current onScroll.
   * @param distanceX The distance along the X axis that has been scrolled since the last
   *                  call to onScroll. This is NOT the distance between {@code e1}
   *                  and {@code e2}.
   * @param distanceY The distance along the Y axis that has been scrolled since the last
   *                  call to onScroll. This is NOT the distance between {@code e1}
   *                  and {@code e2}.
   * @return true if the event is consumed, else false
   */
  @Override
  public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
    logGesture("onScroll", String.format("dx=%f dy=%f", distanceX, distanceY), e1, e2);
    return true;
  }

  /**
   * Notified when a long press occurs with the initial on down {@link MotionEvent}
   * that trigged it.
   *
   * @param e The initial on down motion event that started the longpress.
   */
  @Override
  public void onLongPress(MotionEvent e) {
    logGesture("onLongPress", e);
  }

  /**
   * Notified of a fling event when it occurs with the initial on down {@link MotionEvent}
   * and the matching up {@link MotionEvent}. The calculated velocity is supplied along
   * the x and y axis in pixels per second.
   *
   * @param e1        The first down motion event that started the fling.
   * @param e2        The move motion event that triggered the current onFling.
   * @param velocityX The velocity of this fling measured in pixels per second
   *                  along the x axis.
   * @param velocityY The velocity of this fling measured in pixels per second
   *                  along the y axis.
   * @return true if the event is consumed, else false
   */
  @Override
  public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
    logGesture("onFling", String.format("vx=%f vy=%f", velocityX, velocityY), e1, e2);
    return true;
  }
}
