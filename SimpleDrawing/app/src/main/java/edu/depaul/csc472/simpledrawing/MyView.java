package edu.depaul.csc472.simpledrawing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Xiaoping Jia on 10/24/14.
 */
public class MyView extends View {

  enum ShapeType {Trace, Line, Rectangle, Oval};

  private Path path = new Path();
  private Paint paint = new Paint();
  private int color = Color.BLUE;
  private ShapeType shapeType = ShapeType.Trace;

  public MyView(Context context) {
    super(context);
  }

  public MyView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public int getColor() {
    return color;
  }

  public void setColor(String name) {
    if (name.equals("Blue")) {
      color = Color.BLUE;
    } else if (name.equals("Cyan")) {
      color = Color.CYAN;
    } else if (name.equals("Gray")) {
      color = Color.GRAY;
    } else if (name.equals("Green")) {
      color = Color.GREEN;
    } else if (name.equals("Magenta")) {
      color = Color.MAGENTA;
    } else if (name.equals("Red")) {
      color = Color.RED;
    } else if (name.equals("Yellow")) {
      color = Color.YELLOW;
    } else {
      color = Color.BLACK;
    }
  }

  public void setColor(int color) {
    this.color = color;
  }

  public ShapeType getShapeType() {
    return shapeType;
  }

  public void setShapeType(ShapeType shapeType) {
    this.shapeType = shapeType;
  }

  public void setShapeType(String name) {
    if (name.equals("Trace")) {
      shapeType = ShapeType.Trace;
    } else if (name.equals("Line")) {
      shapeType = ShapeType.Line;
    } else if (name.equals("Rectangle")) {
      shapeType = ShapeType.Rectangle;
    } else if (name.equals("Oval")) {
      shapeType = ShapeType.Oval;
    } else {
      shapeType = ShapeType.Trace;
    }
  }

  @Override
  protected void onDraw(Canvas canvas) {
    canvas.drawColor(Color.WHITE);

    paint.setAntiAlias(true);
    paint.setColor(color);
    paint.setStyle(Paint.Style.STROKE);
    paint.setStrokeJoin(Paint.Join.ROUND);
    paint.setStrokeWidth(3);

    canvas.drawPath(path, paint);
  }


  float startX, startY;

  public boolean onTouchEvent(MotionEvent event) {
    int action = event.getAction();
    float curX = event.getX();
    float curY = event.getY();

    switch (action) {
      case MotionEvent.ACTION_DOWN:
        startX = curX;
        startY = curY;
        if (shapeType == ShapeType.Trace) {
          path.reset();
          path.moveTo(curX, curY);
        }
        break;

      case MotionEvent.ACTION_MOVE:
      case MotionEvent.ACTION_UP:
        if (shapeType == ShapeType.Trace) {
          final int historySize = event.getHistorySize();
          for (int h = 0; h < historySize; h++) {
            float histX = event.getHistoricalX(h);
            float histY = event.getHistoricalY(h);
            path.lineTo(histX, histY);
          }
          path.lineTo(curX, curY);
        } else  if (shapeType == ShapeType.Line) {
          path.reset();
          path.moveTo(startX, startY);
          path.lineTo(curX, curY);
        } else  if (shapeType == ShapeType.Rectangle) {
          path.reset();
          path.addRect(startX, startY, curX, curY, Path.Direction.CW);
        } else  if (shapeType == ShapeType.Oval) {
          path.reset();
          path.addOval(startX, startY, curX, curY, Path.Direction.CW);
        }
        break;
    }

    invalidate();

    return true;
  }


}
