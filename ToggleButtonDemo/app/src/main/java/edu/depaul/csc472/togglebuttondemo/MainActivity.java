package edu.depaul.csc472.togglebuttondemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity
    implements CompoundButton.OnCheckedChangeListener {

  private static final String TAG = "MainActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ToggleButton toggleButton1 = (ToggleButton) findViewById(R.id.toggle1);
    ToggleButton toggleButton2 = (ToggleButton) findViewById(R.id.toggle2);
    ToggleButton toggleButton3 = (ToggleButton) findViewById(R.id.toggle3);
    ToggleButton toggleButton4 = (ToggleButton) findViewById(R.id.toggle4);
    toggleButton1.setOnCheckedChangeListener(this);
    toggleButton2.setOnCheckedChangeListener(this);
    toggleButton3.setOnCheckedChangeListener(this);
    toggleButton4.setOnCheckedChangeListener(this);

    Switch switch1 = (Switch) findViewById(R.id.switch1);
    Switch switch2 = (Switch) findViewById(R.id.switch2);
    Switch switch3 = (Switch) findViewById(R.id.switch3);
    Switch switch4 = (Switch) findViewById(R.id.switch4);
    switch1.setOnCheckedChangeListener(this);
    switch2.setOnCheckedChangeListener(this);
    switch3.setOnCheckedChangeListener(this);
    switch4.setOnCheckedChangeListener(this);
  }

  @Override
  public void onCheckedChanged(CompoundButton button, boolean isChecked) {
    Toast.makeText(this, button.getTag() + " is " + (isChecked ? "on" : "off"),
        Toast.LENGTH_SHORT).show();
  }

  public void onToggleClicked(View view) {
    ToggleButton toggleButton = (ToggleButton) view;
    Log.d(TAG, "onToggleClicked() " + toggleButton.getTag() + " " + (toggleButton.isChecked() ? "on" : "off"));
  }

  public void onSwitchClicked(View view) {
    Switch sw = (Switch) view;
    Log.d(TAG, "onSwitchClicked() " + sw.getTag() + " " + (sw.isChecked() ? "on" : "off"));
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
