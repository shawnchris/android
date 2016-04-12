package edu.depual.csc472.sgao_finalproject;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity
        implements TypeListFragment.Callbacks, ActionBar.OnNavigationListener {

    private static boolean inited = false;
    private boolean mTwoPane = false;

    private void init() {
        for (Car car : Car.CARS) {
            String prefName = car.getName();

            SharedPreferences prefs = getSharedPreferences(prefName, MODE_PRIVATE);
            String carName = prefs.getString("name", null);
            if (carName == null) {
            // The preference doesn't exist, need to create it and put in the default values
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("name", prefName);
                editor.putInt("count", 0);
                ArrayList<Review> list = Review.REVIEWS.get(prefName);
                if (list != null && list.size() != 0) {
                // Write default reviews to shared preference
                    for (int i = 1; i <= list.size(); i++) {
                        editor.putString("title"+i, list.get(i-1).title);
                        editor.putFloat("stars"+i, list.get(i-1).stars);
                        editor.putString("content"+i, list.get(i-1).content);
                    }
                    editor.putInt("count", list.size());
                }
                editor.commit();
            }
            else {
            // The preference exists, use it to overwrite the default reviews
                int count = prefs.getInt("count", 0);
                ArrayList<Review> list = Review.REVIEWS.get(prefName);
                if (list == null)
                    list = new ArrayList<Review>();
                list.clear();
                for (int i = 1; i <= count; i++) {
                    list.add(
                            new Review(
                                    prefs.getString("title"+i, ""),
                                    prefs.getFloat("stars"+i, 0.0f),
                                    prefs.getString("content"+i, "")
                            )
                    );
                }
                Review.REVIEWS.put(prefName, list);
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!inited) {
            init();
            inited = true;
        }

        setContentView(R.layout.activity_main);

        if (findViewById(R.id.type_list) != null) {
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((TypeListFragment) getFragmentManager()
                    .findFragmentById(R.id.type_list))
                    .setActivateOnItemClick(true);
        } else {
            // Set up the action bar to show a dropdown list.
            final ActionBar actionBar = getActionBar();
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle("Car Review  Type");
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

            // Set up the dropdown list navigation in the action bar.
            actionBar.setListNavigationCallbacks(
                    // Specify a SpinnerAdapter to populate the dropdown list.
                    //ArrayAdapter(Context context, int resource, int textViewResourceId, List<T> objects)
                    new ArrayAdapter<CarType.Type>(
                            actionBar.getThemedContext(),
                            android.R.layout.simple_list_item_1,
                            android.R.id.text1,
                            CarType.ITEMS), this
            );
        }
    }

    /**
     * Callback method from {@link TypeListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(CarListFragment.ARG_ITEM_ID, id);
            CarListFragment fragment = new CarListFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .replace(R.id.car_list_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            //Intent detailIntent = new Intent(this, CarDetailActivity.class);
            //detailIntent.putExtra(CarListFragment.ARG_ITEM_ID, id);
            //startActivity(detailIntent);
        }
    }

    @Override
    public boolean onNavigationItemSelected(int position, long id) {
        // When the given dropdown item is selected, show its contents in the
        // container view.
        //getFragmentManager().beginTransaction()
        //        .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
        //        .commit();
        Log.d("CarReviews", "onNavigationItemSelected position=" + position + " id=" + id + " " + CarType.ITEMS.get(position));
        Bundle arguments = new Bundle();
        arguments.putString(CarListFragment.ARG_ITEM_ID, position + "");
        CarListFragment fragment = new CarListFragment();
        fragment.setArguments(arguments);
        if (findViewById(R.id.container) == null)
            Log.d("CarReviews", "car_list_container is null");
        getFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
        return true;
    }
}
