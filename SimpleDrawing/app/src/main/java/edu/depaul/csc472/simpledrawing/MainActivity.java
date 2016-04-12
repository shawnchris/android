package edu.depaul.csc472.simpledrawing;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);


    final MyView v1 = (MyView) findViewById(R.id.v1);

    final Spinner s1 = (Spinner) findViewById(R.id.colors);
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, COLORS);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    s1.setAdapter(adapter);
    s1.setOnItemSelectedListener(
        new AdapterView.OnItemSelectedListener() {
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            v1.setColor(COLORS[position]);
          }
          public void onNothingSelected(AdapterView<?> parent) { }
        });

    final Spinner s2 = (Spinner) findViewById(R.id.shapes);
    adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SHAPES);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    s2.setAdapter(adapter);
    s2.setOnItemSelectedListener(
        new AdapterView.OnItemSelectedListener() {
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            v1.setShapeType(SHAPES[position]);
          }
          public void onNothingSelected(AdapterView<?> parent) { }
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

  static final String[] COLORS = {
      "Black",
      "Blue",
      "Cyan",
      "Gray",
      "Green",
      "Magenta",
      "Red",
      "Yellow"
  };

  static final String[] SHAPES = {
      "Trace",
      "Line",
      "Rectangle",
      "Oval",
  };

}
