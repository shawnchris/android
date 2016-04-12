package edu.depaul.csc472.wineselection_single;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ListActivity {

  private static final String TAG = "MainActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final ListView listView = getListView();
    // must set the choice mode before set adapter
    listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    setListAdapter(new ArrayAdapter<String>(this,
        android.R.layout.simple_list_item_single_choice, WINES));

    final TextView textView = (TextView) findViewById(R.id.text);
    Button select = (Button) findViewById(R.id.select);
    select.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        int pos = listView.getCheckedItemPosition();
        Log.d(TAG, "onCLick pos=" + pos + "  count=" + listView.getCheckedItemCount());
        if (pos >= 0) {
          textView.setText("Your selection: " + WINES[pos]);
        } else {
          textView.setText("No selection");
        }
      }
    });
  }

  @Override
  protected void onListItemClick(ListView l, View v, int position, long id) {
    Log.d(TAG, "onListItemClick position=" + position + " id=" + id + " " + WINES[position]);
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
