package edu.depaul.csc472.intentdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ActivityB extends Activity {

  private static final String TAG = "ActivityB";
  private static int INSTANCE_COUNTER = 0;

  private int instanceID;
  private int counter = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_b);

    instanceID = ++INSTANCE_COUNTER;
    Log.d(TAG, "onCreate() instanceID=" + instanceID);

    Button button1 = (Button) findViewById(R.id.button1);
    button1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(ActivityB.this, ActivityA.class);
        startActivity(intent);
      }
    });

    Button button2 = (Button) findViewById(R.id.button2);
    button2.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        finish();
      }
    });

  }

  @Override
  protected void onStart() {
    super.onStart();
    // The activity is about to become visible.
    Log.d(TAG, "onStart() counter=" + ++counter);
    TextView title = (TextView) findViewById(R.id.title);
    title.setText("Activity B [" + instanceID + "-" + counter + "]");
  }

  @Override
  protected void onResume() {
    super.onResume();
    // The activity has become visible (it is now "resumed").
    Log.d(TAG, "onResume()");
  }

  @Override
  protected void onPause() {
    super.onPause();
    // Another activity is taking focus (this activity is about to be "paused").
    Log.d(TAG, "onPause()");
  }

  @Override
  protected void onStop() {
    super.onStop();
    // The activity is no longer visible (it is now "stopped")
    Log.d(TAG, "onStop()");
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    // The activity is about to be destroyed.
    Log.d(TAG, "onDestory()");
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.activity_b, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
