package edu.depaul.csc472.winelist_customizedfragment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.app.Activity;
import android.app.FragmentManager;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import edu.depaul.csc472.winelist_customizedfragment.wine.Wine;
import edu.depaul.csc472.winelist_customizedfragment.wine.WineList;

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

  public interface DetailCallbacks {
    /**
     * Callback for when an item has been selected.
     */
    public void onItemChanged();
  }

  private DetailCallbacks mCallbacks;

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
      TextView name = (TextView) rootView.findViewById(R.id.text1);
      TextView description = (TextView) rootView.findViewById(R.id.text2);
      ImageView icon = (ImageView) rootView.findViewById(R.id.image);
      RatingBar rating = (RatingBar) rootView.findViewById(R.id.rating);

      name.setText(wine.getName());
      description.setText(wine.getLongDescription());
      icon.setImageResource(Wine.getIconResource(wine.getType()));
      rating.setRating(wine.getRating());
      rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
        @Override
        public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
          wine.setRating(v);
          if (mCallbacks != null) {
            mCallbacks.onItemChanged();
          }
        }
      });
    }

    return rootView;
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);

    FragmentManager fragmentManager = activity.getFragmentManager();
    Fragment wineListFragment	= fragmentManager.findFragmentById(R.id.wine_list);
    if (wineListFragment instanceof DetailCallbacks) {
      mCallbacks = (DetailCallbacks) wineListFragment;
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();

    mCallbacks = null;
  }

}
