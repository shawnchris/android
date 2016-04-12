package edu.depual.csc472.sgao_finalproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A fragment representing a single Car detail screen.
 * This fragment is either contained in a {@link MainActivity}
 * in two-pane mode (on tablets) or a {@link CarDetailActivity}
 * on handsets.
 */
public class CarListFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    private static final String TAG = "CarReviews";
    private static final int DETAIL = 100; // request code
    private static ArrayList<Car> carList;
    private Car selectedCar;
    private Context context;

    /**
     * The dummy content this fragment is presenting.
     */
    private CarType.Type mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CarListFragment() {
    }

    //public CarListFragment(Context context) {
    //    this.context = context;
    //}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.

            mItem = CarType.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
            carList = new ArrayList<Car>();
            for (Car car : Car.CARS) {
                if (car.getType().toString().equals(mItem.content))
                    carList.add(car);
            }
            Log.d(TAG, "CarListFragment onCreate, added " + carList.size() + " cars.");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_car_list, container, false);
        context = getActivity();
        if (mItem != null) {
            //((TextView) rootView.findViewById(R.id.car_detail)).setText(mItem.content);
            ListView lv = (ListView) rootView.findViewById(R.id.list_view);
            if (lv != null) {
                lv.setAdapter(new CarAdapter(context));
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d(TAG, "onListItemClick position=" + position + " id=" + id + " " + carList.get(position).getName());
                        selectedCar = carList.get(position);
                        Intent intent = new Intent(context, CarDetailActivity.class);
                        intent.putExtra("CarName", selectedCar.getName());
                        intent.putExtra("CarType", selectedCar.getType().toString());
                        intent.putExtra("CarBrand", selectedCar.getBrand().toString());
                        intent.putExtra("CarImage", Car.getImageResource(selectedCar.getName()));
                        startActivityForResult(intent, DETAIL);
                    }
                });
            }
        }

        return rootView;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DETAIL) {
            if (resultCode == Activity.RESULT_OK) {
                Toast t = Toast.makeText(getActivity(), "Thanks for your review!", Toast.LENGTH_LONG);
                t.show();
            }
        }
    }


    static class CarAdapter extends BaseAdapter {

        private LayoutInflater inflater;
        private Map<Car.Brand, Bitmap> icons;

        CarAdapter(Context context) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            icons = new HashMap<Car.Brand, Bitmap>();
            icons.put(Car.Brand.BMW, BitmapFactory.decodeResource(context.getResources(), R.drawable.bmw));
            icons.put(Car.Brand.Honda, BitmapFactory.decodeResource(context.getResources(), R.drawable.honda));
            icons.put(Car.Brand.Toyota, BitmapFactory.decodeResource(context.getResources(), R.drawable.toyota));
            icons.put(Car.Brand.VW, BitmapFactory.decodeResource(context.getResources(), R.drawable.vw));
        }

        @Override
        public int getCount() {
            return carList.size();
        }

        @Override
        public Object getItem(int i) {
            return carList.get(i);
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
                row = inflater.inflate(R.layout.car_list_item, parent, false);
                holder = new ViewHolder();
                holder.icon = (ImageView) row.findViewById(R.id.image);
                holder.name = (TextView) row.findViewById(R.id.text1);
                holder.description = (TextView) row.findViewById(R.id.text2);
                holder.rating = (TextView) row.findViewById(R.id.text3);
                row.setTag(holder);
            } else {
                holder = (ViewHolder) row.getTag();
            }

            Car car = carList.get(position);
            holder.name.setText(car.getName());
            holder.description.setText("Made by: " + car.getBrand());
            holder.icon.setImageBitmap(icons.get(car.getBrand()));
            //holder.rating.setText(wine.getRating() + " stars");
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
