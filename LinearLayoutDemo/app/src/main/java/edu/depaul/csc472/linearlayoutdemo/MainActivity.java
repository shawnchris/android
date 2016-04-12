package edu.depaul.csc472.linearlayoutdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //setContentView(R.layout.activity_main);

    setContentView(R.layout.activity_main_demo1);
    //setContentView(R.layout.activity_main_demo2);
    //setContentView(R.layout.activity_main_demo3);
    //setContentView(R.layout.activity_main_demo4);
    //setContentView(R.layout.activity_main_demo5);
    //setContentView(R.layout.activity_main_demo6);
    //setContentView(R.layout.activity_main_demo7);
    //setContentView(R.layout.activity_main_demo8);
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
