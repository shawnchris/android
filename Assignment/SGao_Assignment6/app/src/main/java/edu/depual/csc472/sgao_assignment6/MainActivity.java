package edu.depual.csc472.sgao_assignment6;

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
    private static final int DETAIL = 100; // request code

    private Car selectedCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        setListAdapter(new CarAdapter(this));
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Log.d(TAG, "onListItemClick position=" + position + " id=" + id + " " + CARS[position].getName());
        selectedCar = CARS[position];
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("CarName", "Name: " + selectedCar.getName());
        intent.putExtra("CarType", "Type: " + selectedCar.getType());
        intent.putExtra("CarSize", "Size: " + selectedCar.getSize());
        intent.putExtra("CarImage", Car.getImageResource(selectedCar.getName()));
        startActivityForResult(intent, DETAIL);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult"); // + data.getFloatExtra("WineRating", 4));
        /*
        if (requestCode == DETAIL) {
            if (resultCode == RESULT_OK && data != null) {
                selectedCar.setRating(data.getFloatExtra("WineRating", 4));
                ((WineAdapter) getListAdapter()).notifyDataSetChanged();
            }
        }
        */
    }

    // More efficient version of adapter
    static class CarAdapter extends BaseAdapter {

        private LayoutInflater inflater;
        private Map<Car.Type, Bitmap> icons;

        CarAdapter(Context context) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            icons = new HashMap<Car.Type, Bitmap>();
            icons.put(Car.Type.Sedan, BitmapFactory.decodeResource(context.getResources(), R.drawable.sedan));
            icons.put(Car.Type.SUV, BitmapFactory.decodeResource(context.getResources(), R.drawable.suv));
            icons.put(Car.Type.Wagon, BitmapFactory.decodeResource(context.getResources(), R.drawable.wagon));
            icons.put(Car.Type.Hatchback, BitmapFactory.decodeResource(context.getResources(), R.drawable.hatchback));
            icons.put(Car.Type.PickupTruck, BitmapFactory.decodeResource(context.getResources(), R.drawable.pickup));
            icons.put(Car.Type.Minivan, BitmapFactory.decodeResource(context.getResources(), R.drawable.minivan));
            icons.put(Car.Type.Convertible, BitmapFactory.decodeResource(context.getResources(), R.drawable.convertible));
            icons.put(Car.Type.Coupe, BitmapFactory.decodeResource(context.getResources(), R.drawable.coupe));
        }

        @Override
        public int getCount() {
            return CARS.length;
        }

        @Override
        public Object getItem(int i) {
            return CARS[i];
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

            Car car = CARS[position];
            holder.name.setText(car.getName());
            holder.description.setText(car.getType() + " " + car.getSize());
            holder.icon.setImageBitmap(icons.get(car.getType()));
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

    private static final Car[] CARS = {
            new Car("4 Runner", Car.Type.SUV, Car.Size.MidSize),
            new Car("Avalon", Car.Type.Sedan, Car.Size.FullSize),
            new Car("Camry", Car.Type.Sedan, Car.Size.MidSize),
            new Car("Corolla", Car.Type.Sedan, Car.Size.Compact),
            new Car("Highlander", Car.Type.SUV, Car.Size.MidSize),
            new Car("Land Cruiser", Car.Type.SUV, Car.Size.FullSize),
            new Car("Prius", Car.Type.Hatchback, Car.Size.Compact),
            new Car("Rav4", Car.Type.SUV, Car.Size.Compact),
            new Car("Sequoia", Car.Type.SUV, Car.Size.FullSize),
            new Car("Sienna", Car.Type.Minivan, Car.Size.MidSize),
            new Car("Tacoma", Car.Type.PickupTruck, Car.Size.Compact),
            new Car("Tundra", Car.Type.PickupTruck, Car.Size.FullSize),
            new Car("Yaris", Car.Type.Hatchback, Car.Size.Compact),
    };
}
