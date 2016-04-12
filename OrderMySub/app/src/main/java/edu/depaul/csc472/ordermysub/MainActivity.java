package edu.depaul.csc472.ordermysub;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {

  private static final String TAG = "MainActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final Spinner s1 = (Spinner) findViewById(R.id.spinner1);
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SUBS);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    s1.setAdapter(adapter);
    s1.setOnItemSelectedListener(
        new AdapterView.OnItemSelectedListener() {
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Log.d(TAG, "Spinner1: " + SUBS[position] +
                " position=" + position + " id=" + id);
          }

          public void onNothingSelected(AdapterView<?> parent) {
            Log.d(TAG, "Spinner1: unselected");
          }
        });

    final Spinner s2 = (Spinner) findViewById(R.id.spinner2);
    adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SIZES);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    s2.setAdapter(adapter);
    s2.setOnItemSelectedListener(
        new AdapterView.OnItemSelectedListener() {
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Log.d(TAG, "Spinner2: " + SIZES[position] +
                " position=" + position + " id=" + id);
          }

          public void onNothingSelected(AdapterView<?> parent) {
            Log.d(TAG, "Spinner2: unselected");
          }
        });

    Button button = (Button) findViewById(R.id.button);
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Toast.makeText(MainActivity.this, "Your order: " + s2.getSelectedItem() + " " + s1.getSelectedItem(),
            Toast.LENGTH_SHORT).show();
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

  static final String[] SUBS = {
      "Blockbuster",
      "Roast Beef",
      "Italian Special",
      "Corned Beef",
      "Big \"Al\" Italian",
      "Tuna",
      "Wise Guy",
      "Chicken Salad ",
      "Caputo",
      "Veggie",
      "Prosciutto",
      "Turkey Breast",
      "American"
  };

  static final String[] SIZES = {
      "6 inch",
      "8 inch",
      "10 inch",
      "12 inch",
      "16 inch",
      "3 foot"
  };

}
