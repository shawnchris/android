package edu.depaul.csc472.wineselection_multi;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
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
    listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    setListAdapter(new ArrayAdapter<String>(this,
        android.R.layout.simple_list_item_multiple_choice, WINES));

    final TextView textView = (TextView) findViewById(R.id.text);
    final Button select = (Button) findViewById(R.id.select);
    select.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        SparseBooleanArray selected = listView.getCheckedItemPositions();

        Log.d(TAG, "onCLick count=" + listView.getCheckedItemCount() + " " + selected);
        if (listView.getCheckedItemCount() > 0) {
          StringBuilder sb = new StringBuilder();
          for (int i = 0; i < selected.size(); i++) {
            if (selected.valueAt(i)) {
              if (sb.length() > 0) sb.append(", ");
              sb.append(WINES[selected.keyAt(i)]);
            }
          }
          textView.setText("Your selection: " + sb);
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
