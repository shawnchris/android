package edu.depaul.csc472.winelist_parcel;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends ListActivity {

  private static final String TAG = "MyActivity";
  private static final int RATING = 100; // request code

  private Wine selectedWine;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //setContentView(R.layout.activity_main);

    setListAdapter(new WineAdapter(this));
  }

  @Override
  protected void onListItemClick(ListView l, View v, int position, long id) {
    Log.d(TAG, "onListItemClick position=" + position + " id=" + id + " " + WINES[position].getName());
    selectedWine = WINES[position];
    Intent intent = new Intent(MainActivity.this, RatingsActivity.class);
    /*
    intent.putExtra("WineName", selectedWine.getName());
    intent.putExtra("WineDescription", selectedWine.getLongDescription());
    intent.putExtra("WineIcon", getIconResource(selectedWine.getType()));
    intent.putExtra("WineRating", selectedWine.getRating());
    */
    intent.putExtra("Wine", selectedWine);
    startActivityForResult(intent, RATING);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    Log.d(TAG, "onActivityResult"); // + data.getFloatExtra("WineRating", 4));
    if (requestCode == RATING) {
      if (resultCode == RESULT_OK && data != null) {
        selectedWine.setRating(data.getFloatExtra("WineRating", 4));
        ((WineAdapter) getListAdapter()).notifyDataSetChanged();
      }
    }
  }

  // More efficient version of adapter
  static class WineAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Map<Wine.Type, Bitmap> icons;

    WineAdapter(Context context) {
      inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      icons = new HashMap<Wine.Type, Bitmap>();
      /*
      icons.put(Wine.Type.Red, BitmapFactory.decodeResource(context.getResources(), R.drawable.red));
      icons.put(Wine.Type.Rosé, BitmapFactory.decodeResource(context.getResources(), R.drawable.rose));
      icons.put(Wine.Type.Sparkling, BitmapFactory.decodeResource(context.getResources(), R.drawable.sparkling));
      icons.put(Wine.Type.White, BitmapFactory.decodeResource(context.getResources(), R.drawable.white));
      */
      for (Wine.Type type : Wine.Type.values()) {
        icons.put(type, BitmapFactory.decodeResource(context.getResources(),
            Wine.getIconResource(type)));
      }
    }

    @Override
    public int getCount() {
      return WINES.length;
    }

    @Override
    public Object getItem(int i) {
      return WINES[i];
    }

    @Override
    public long getItemId(int i) {
      return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      ViewHolder holder;
      View row = convertView;
      if (row == null) {
        row = inflater.inflate(R.layout.wine_list_item, parent, false);
        holder = new ViewHolder();
        holder.icon = (ImageView) row.findViewById(R.id.image);
        holder.name = (TextView) row.findViewById(R.id.text1);
        holder.description = (TextView) row.findViewById(R.id.text2);
        holder.rating = (TextView) row.findViewById(R.id.text3);
        row.setTag(holder);
      } else {
        holder = (ViewHolder) row.getTag();
      }

      Wine wine = WINES[position];
      holder.name.setText(wine.getName());
      holder.description.setText(wine.getShortDescription());
      holder.icon.setImageBitmap(icons.get(wine.getType()));
      holder.rating.setText(wine.getRating() + " stars");
      return row;
    }

    static class ViewHolder {
      TextView name;
      TextView description;
      TextView rating;
      ImageView icon;
    }
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

  private static final Wine[] WINES = {
      new Wine("Barbera",
          Wine.Type.Red,
          "Full-bodied Italian Red",
          "Barbera is a red wine grape found primarily in Italy's Piedmont region. It produces hearty red wines with deep ruby colors, full body and low tannin levels."),

      new Wine("Cabernet Sauvignon",
          Wine.Type.Red,
          "Full-bodied, intense Red",
          "Cabernet Sauvignon is a red wine known for its depth of flavor, aroma and ability to age (present by the wine’s noticeable tannins). It is full-bodied and intense, with cherry-currant and sometimes herbal flavors."),

      new Wine("Champagne",
          Wine.Type.Sparkling,
          "Sparkling White from Champagne region",
          "These wines are made effervescent by a secondary fermentation in the wine-making process. Champagnes and sparkling wines range in style from very dry (Natural), dry (brut) and slightly sweet (extra Dry) to sweet (sec and Demi-Sec). Many sparkling wines are also identified as Blanc de Blancs (wines made from white grapes) or Blanc de Noirs (wines produced from red grapes)."),

      new Wine("Chardonnay",
          Wine.Type.White,
          "Popular, complex White from Burgundy",
          "One of the world’s most popular wines, Chardonnay is a white wine originating from Burgundy.  Flavors range from clean and crisp with a hint of varietal flavor to rich and complex, vanilla, butter and oak-aged wines. Chardonnay typically balances fruit, acidity and texture."),

      new Wine("Chenin Blanc",
          Wine.Type.White,
          "Fresh, light White",
          "Chenin Blanc is a white wine with fresh, delicate floral characteristics. It grows well in warmer climates and produces light, well-balanced wines ranging from dry to off-dry (slightly sweet) styles."),

      new Wine("Dolcetto",
          Wine.Type.Red,
          "Light Italian Red",
          "This red wine grape is found almost exclusively in Italy's Piedmont region. It produces light and fruity wine."),

      new Wine("Fume Blanc",
          Wine.Type.White,
          "Dry White made from Sauvignon Blanc",
          "Invented by Robert Mondavi in 1970, Fume Blanc is a Sauvignon Blanc that has been fermented in oak."),

      new Wine("Gewürztraminer",
          Wine.Type.White,
          "Rich German White",
          "Gewürztraminer is a white German wine that produced distinctive wines rich in spicy aromas and full flavors, ranging from dry to sweet. This varietal is a popular choice for Asian dishes."),

      new Wine("Merlot",
          Wine.Type.Red,
          "Medium to full-bodied Red",
          "Merlot is a red wine with medium to full body with black cherry and herbal flavors. Merlot is typically smooth, soft and mellow."),

      new Wine("Mourvedre",
          Wine.Type.Red,
          "Rich Red from Rhone Valley",
          "This warm-weather, red wine grape is common in Southern France's Rhone Valley. Rich in color with early aromas, often blended with Syrah."),

      new Wine("Petite Sirah",
          Wine.Type.Red,
          "Robust tannic Red",
          "Petite Sirahs are inky red wines with firm, robust tannic tastes, often with peppery flavors. Petite Sirahs may complement meals with rich meats."),

      new Wine("Pinot Gris",
          Wine.Type.White,
          "Popular German and Italian White",
          "The low acidity of this white grape helps produce rich, lightly perfumed wines that are often more colorful than other whites."),

      new Wine("Pinot Noir",
          Wine.Type.Red,
          "Light to medium bodied Burgundy Red",
          "Pinot Noir is the world famous grape from Burgundy and more recently California and the Pacific Northwest.  A light to medium-body wine, pegged as one of the most difficult to grow and make. Delicate and smooth with rich complexity, Pinot Noir is a versatile dinner companion."),

      new Wine("Riesling",
          Wine.Type.White,
          "Full-bodied or sweet German White",
          "Riesling is the classic white wine grape from Germany and known for its floral perfume. Depending on where they're made, they can be crisp and bone-dry, full-bodied and spicy or luscious and sweet."),

      new Wine("Rosés",
          Wine.Type.Rosé,
          "Light, sweet, pink colored wine",
          "Rosés, also called blush wines, are light pink wines made from several red wine grapes. They get their color from a very short period of contact with the grape skins during the wine-making process. Rosés are light, usually somewhat sweet and best served well-chilled."),

      new Wine("Sangiovese",
          Wine.Type.Red,
          "Dry Italian Red",
          "Sangiovese is best known as the Italian red wine, Chianti. Hearty and dry, it often displays a distinctively smooth texture with spice, raspberry and licorice flavors."),

      new Wine("Sauvignon Blanc",
          Wine.Type.White,
          "Refreshing White",
          "Sauvignon Blanc is a white wine best known for its grassy, herbal flavors and is a popular choice for shellfish or as a refreshing alternative to Chardonnay."),

      new Wine("Syrah",
          Wine.Type.Red,
          "Complex Red",
          "Syrah (Shiraz) can produce giant red wines with strong tannins and complex combinations of flavors including berry, plum and smoke. It's known as Shiraz mainly in Australia and South Africa."),

      new Wine("Viognier",
          Wine.Type.White,
          "Aromatic White",
          "Viognier is a rare white grape growing in popularity for its uniqueness.  It is an aromatic variety typically displaying peach, apricot and sometimes spicy flavors."),

      new Wine("Zinfandel",
          Wine.Type.Red,
          "Medium to full-bodied Red",
          "Zinfandel is a medium to full-bodied red wine with berry or spicy, peppery flavors. Great with pizza and tangy barbecue sauce."),

  };

}
