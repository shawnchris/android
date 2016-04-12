package edu.depaul.csc472.motionsensors;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Xiaoping Jia on 10/24/14.
 */
public class SensorView extends View {

  private static final String TAG = "MyView";

  private Paint paint = new Paint();

  private Handler handler = new Handler();

  private SensorManager sensorManager;

  private int[] sensorTypes = {
      Sensor.TYPE_ACCELEROMETER,
      Sensor.TYPE_GRAVITY,
      Sensor.TYPE_LINEAR_ACCELERATION,
      Sensor.TYPE_GYROSCOPE,
      Sensor.TYPE_GYROSCOPE_UNCALIBRATED
  };
  private Sensor[] sensors = new Sensor[5];
  private SensorEventListener[] listeners = new SensorEventListener[5];
  private float[][] sensorData = new float[5][3];
  int[] colors = {Color.BLUE, Color.GREEN, Color.MAGENTA, Color.CYAN, Color.RED};

  private int[][] count = new int[5][1];

  private String[] labels = {
      "ACCELEROMETER",
      "GRAVITY",
      "LINEAR_ACCELERATION",
      "GYROSCOPE",
      "GYROSCOPE_UNCALIBRATED"
  };

  public SensorView(Context context) {
    super(context);
    initSensors();
  }

  public SensorView(Context context, AttributeSet attrs) {
    super(context, attrs);
    initSensors();
  }

  private void initSensors() {
    sensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
    for (int s = 0; s < 5; s++) {
      final float[] data = sensorData[s];
      final int[] data2 = count[s];
      sensors[s] = sensorManager.getDefaultSensor(sensorTypes[s]);
      if (sensors[s] != null) {
        Log.d(TAG, labels[s] + " is available");
        listeners[s] = new SensorEventListener() {
          @Override
          public void onSensorChanged(SensorEvent event) {
            data2[0]++;
            for (int i = 0; i < 3; i++)
              data[i] = event.values[i];
            invalidate();
          }

          @Override
          public void onAccuracyChanged(Sensor sensor, int accuracy) {
          }
        };
      } else {
        Log.d(TAG, labels[s] + " is not available");
      }
    }
  }

  void resume() {
    for (int s = 0; s < 5; s++) {
      if (listeners[s] != null)
        sensorManager.registerListener(listeners[s], sensors[s], SensorManager.SENSOR_DELAY_NORMAL);
    }
  }

  void pause() {
    for (int s = 0; s < 5; s++) {
      if (listeners[s] != null)
        sensorManager.unregisterListener(listeners[s]);
    }
  }

  @Override
  protected void onDraw(Canvas canvas) {
    canvas.drawColor(Color.WHITE);

    paint.setAntiAlias(true);
    paint.setColor(Color.BLACK);
    paint.setTypeface(Typeface.DEFAULT);
    paint.setTextSize(30);

    for (int s = 0; s < 5; s++) {
      String msg = String.format("x=%f y=%f z=%f", sensorData[s][0], sensorData[s][1], sensorData[s][2]);
      msg += ("  count=" + count[s][0]);
      paint.setColor(colors[s]);
      canvas.drawText(labels[s], 20, 50 + 60 * s, paint);
      paint.setColor(Color.BLACK);
      canvas.drawText(msg, 50, 80 + 60 * s, paint);
    }


    for (int s = 0; s < 5; s++) {
      for (int i = 0; i < 3; i++)
        sensorDataHistory[start][s][i] = sensorData[s][i];
    }
    start = ++start % 200;

    paint.setStyle(Paint.Style.STROKE);
    for (int s = 0; s < 5; s++) {
      int ybase = 450 + s * 100;
      paint.setColor(colors[s]);
      for (int axis = 0; axis < 3; axis++) {
        int xbase = 20 + axis * 220;
        Path path = new Path();
        path.moveTo(xbase, ybase - sensorDataHistory[start][s][axis] * 8);
        for (int i = 1; i < 200; i++) {
          int h = (start + i) % 200;
          path.lineTo(xbase + i, ybase - sensorDataHistory[h][s][axis] * 8);
        }
        canvas.drawPath(path, paint);
      }
    }
  }

  private float[][][] sensorDataHistory = new float[200][5][3];
  private int start = 0;
}
