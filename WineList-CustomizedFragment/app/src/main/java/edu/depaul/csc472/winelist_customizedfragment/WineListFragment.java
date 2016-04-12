package edu.depaul.csc472.winelist_customizedfragment;

import android.app.Activity;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import edu.depaul.csc472.winelist_customizedfragment.wine.Wine;
import edu.depaul.csc472.winelist_customizedfragment.wine.WineList;

/**
 * A list fragment representing a list of Wines. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link WineDetailFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class WineListFragment extends ListFragment
    implements WineDetailFragment.DetailCallbacks {

  /**
   * The serialization (saved instance state) Bundle key representing the
   * activated item position. Only used on tablets.
   */
  private static final String STATE_ACTIVATED_POSITION = "activated_position";

  /**
   * The fragment's current callback object, which is notified of list item
   * clicks.
   */
  private Callbacks mCallbacks = sDummyCallbacks;

  /**
   * The current activated item position. Only used on tablets.
   */
  private int mActivatedPosition = ListView.INVALID_POSITION;

  /**
   * A callback interface that all activities containing this fragment must
   * implement. This mechanism allows activities to be notified of item
   * selections.
   */
  public interface Callbacks {
    /**
     * Callback for when an item has been selected.
     */
    public void onItemSelected(String id);
  }

  /**
   * A dummy implementation of the {@link Callbacks} interface that does
   * nothing. Used only when this fragment is not attached to an activity.
   */
  private static Callbacks sDummyCallbacks = new Callbacks() {
    @Override
    public void onItemSelected(String id) {
    }
  };

  /**
   * Mandatory empty constructor for the fragment manager to instantiate the
   * fragment (e.g. upon screen orientation changes).
   */
  public WineListFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // TODO: replace with a real list adapter.
    setListAdapter(new WineAdapter(getActivity()));
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    // Restore the previously serialized activated item position.
    if (savedInstanceState != null
        && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
      setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
    }
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);

    // Activities containing this fragment must implement its callbacks.
    if (!(activity instanceof Callbacks)) {
      throw new IllegalStateException("Activity must implement fragment's callbacks.");
    }

    mCallbacks = (Callbacks) activity;
  }

  @Override
  public void onDetach() {
    super.onDetach();

    // Reset the active callbacks interface to the dummy implementation.
    mCallbacks = sDummyCallbacks;
  }

  @Override
  public void onListItemClick(ListView listView, View view, int position, long id) {
    super.onListItemClick(listView, view, position, id);

    // Notify the active callbacks interface (the activity, if the
    // fragment is attached to one) that an item has been selected.
    mCallbacks.onItemSelected(WineList.WINES.get(position).getName());
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    if (mActivatedPosition != ListView.INVALID_POSITION) {
      // Serialize and persist the activated item position.
      outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
    }
  }

  /**
   * Turns on activate-on-click mode. When this mode is on, list items will be
   * given the 'activated' state when touched.
   */
  public void setActivateOnItemClick(boolean activateOnItemClick) {
    // When setting CHOICE_MODE_SINGLE, ListView will automatically
    // give items the 'activated' state when touched.
    getListView().setChoiceMode(activateOnItemClick
        ? ListView.CHOICE_MODE_SINGLE
        : ListView.CHOICE_MODE_NONE);
  }

  private void setActivatedPosition(int position) {
    if (position == ListView.INVALID_POSITION) {
      getListView().setItemChecked(mActivatedPosition, false);
    } else {
      getListView().setItemChecked(position, true);
    }

    mActivatedPosition = position;
  }

  // For Handset

  @Override
  public void onResume() {
    super.onResume();
    ((WineAdapter) getListAdapter()).notifyDataSetChanged();
  }

  ///// Callback from WineDetailFragment. For two-pane layout

  @Override
  public void onItemChanged() {
    ((WineAdapter) getListAdapter()).notifyDataSetChanged();
  }


  ///// Wine Adapter

  static class WineAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Map<Wine.Type, Bitmap> icons;

    WineAdapter(Context context) {
      inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      icons = new HashMap<Wine.Type, Bitmap>();
      icons.put(Wine.Type.Red, BitmapFactory.decodeResource(context.getResources(), R.drawable.red));
      icons.put(Wine.Type.Rosé, BitmapFactory.decodeResource(context.getResources(), R.drawable.rose));
      icons.put(Wine.Type.Sparkling, BitmapFactory.decodeResource(context.getResources(), R.drawable.sparkling));
      icons.put(Wine.Type.White, BitmapFactory.decodeResource(context.getResources(), R.drawable.white));
    }

    @Override
    public int getCount() {
      return WineList.WINES.size();
    }

    @Override
    public Object getItem(int i) {
      return WineList.WINES.get(i);
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

      Wine wine = WineList.WINES.get(position);
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


}
