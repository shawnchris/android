package edu.depaul.csc472.winelist;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {

  private static final String TAG = "MainActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //setContentView(R.layout.activity_main);

    // Use an existing ListAdapter that will map an array
    // of strings to TextViews
    setListAdapter(new ArrayAdapter<String>(this,
        android.R.layout.simple_list_item_1, WINES));

    /*
    final ListView listView = getListView();

    // single selection
    listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    setListAdapter(new ArrayAdapter<String>(this,
        android.R.layout.simple_list_item_single_choice, WINES));
    */

    /*
    // multi-selection
    listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    setListAdapter(new ArrayAdapter<String>(this,
        android.R.layout.simple_list_item_multiple_choice, WINES));
    */

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

  private static final String[] WINES = {
      "Barbera",
      "Cabernet Sauvignon",
      "Champagne",
      "Chardonnay",
      "Chenin Blanc",
      "Dolcetto",
      "Fume Blanc",
      "Gewürztraminer",
      "Merlot",
      "Mourvedre",
      "Petite Sirah",
      "Pinot Gris",
      "Pinot Noir",
      "Riesling",
      "Rosés",
      "Sangiovese",
      "Sauvignon Blanc",
      "Syrah",
      "Viognier",
      "Zinfandel"
  };

}
