package edu.depaul.csc472.sliderdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends Activity {

  static private final String TAG = "MainActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final TextView value1 = (TextView) findViewById(R.id.value1);
    SeekBar seekBar1 = (SeekBar) findViewById(R.id.seekbar1);
    seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override
      public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        Log.d(TAG, "onProgressChanged");
        value1.setText("Value: " + i);
      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {
        Log.d(TAG, "onStartTrackingTouch");
      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {
        Log.d(TAG, "onStopTrackingTouch");
      }
    });

    final TextView value2 = (TextView) findViewById(R.id.value2);
    SeekBar seekBar2 = (SeekBar) findViewById(R.id.seekbar2);
    seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override
      public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        Log.d(TAG, "onProgressChanged");
        value2.setText("Value: " + i);
      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {
        Log.d(TAG, "onStartTrackingTouch");
      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {
        Log.d(TAG, "onStopTrackingTouch");
      }
    });

    final TextView value3 = (TextView) findViewById(R.id.value3);
    RatingBar ratingBar1 = (RatingBar) findViewById(R.id.ratingkbar1);
    ratingBar1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
      @Override
      public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
        Log.d(TAG, "onRatingChanged");
        value3.setText("Rating: " + v);
      }
    });

    final TextView value4 = (TextView) findViewById(R.id.value4);
    RatingBar ratingBar2 = (RatingBar) findViewById(R.id.ratingkbar2);
    ratingBar2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
      @Override
      public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
        Log.d(TAG, "onRatingChanged");
        value4.setText("Rating: " + v);
      }
    });

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
