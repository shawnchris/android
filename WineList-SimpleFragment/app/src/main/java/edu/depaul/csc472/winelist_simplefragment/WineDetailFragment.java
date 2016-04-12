package edu.depaul.csc472.winelist_simplefragment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.depaul.csc472.winelist_simplefragment.wine.Wine;
import edu.depaul.csc472.winelist_simplefragment.wine.WineList;

/**
 * A fragment representing a single Wine detail screen.
 * This fragment is either contained in a {@link WineListActivity}
 * in two-pane mode (on tablets) or a {@link WineDetailActivity}
 * on handsets.
 */
public class WineDetailFragment extends Fragment {
  /**
   * The fragment argument representing the item ID that this fragment
   * represents.
   */
  public static final String ARG_ITEM_ID = "item_id";

  /**
   * The dummy content this fragment is presenting.
   */
  private Wine wine;

  /**
   * Mandatory empty constructor for the fragment manager to instantiate the
   * fragment (e.g. upon screen orientation changes).
   */
  public WineDetailFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (getArguments().containsKey(ARG_ITEM_ID)) {
      // Load the dummy content specified by the fragment
      // arguments. In a real-world scenario, use a Loader
      // to load content from a content provider.
      wine = WineList.WINE_MAP.get(getArguments().getString(ARG_ITEM_ID));
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_wine_detail, container, false);

    // Show the dummy content as text in a TextView.
    if (wine != null) {
      ((TextView) rootView.findViewById(R.id.wine_detail)).setText(wine.getLongDescription());
    }

    return rootView;
  }
}
