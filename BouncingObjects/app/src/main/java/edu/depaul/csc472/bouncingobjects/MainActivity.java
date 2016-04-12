package edu.depaul.csc472.bouncingobjects;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends Activity {

  MyView animView;
  Spinner spinner;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    spinner = (Spinner) findViewById(R.id.num);
    animView = (MyView) findViewById(R.id.v1);

    spinner.setAdapter(new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, NUMBERS));

    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        restart();
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {}
    });

  }

  static final Integer[] NUMBERS = {
      5, 10, 15, 20, 25, 30, 35, 40, 45, 50
  };

  public void restart() {
    animView.restart((Integer) spinner.getSelectedItem());
  }

  @Override
  protected void onResume() {
    super.onResume();
    animView.startAnimation();
  }

  @Override
  protected void onPause() {
    super.onPause();
    animView.stopAnimation();
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
