package edu.depual.csc472.sgao_assignment6;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class DetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // The activity is about to become visible.
        Intent intent = getIntent();
        if (intent != null) {
            TextView name = (TextView) findViewById(R.id.car_name);
            TextView type = (TextView) findViewById(R.id.car_type);
            TextView size = (TextView) findViewById(R.id.car_size);
            ImageView image = (ImageView) findViewById(R.id.car_image);

            name.setText(intent.getCharSequenceExtra("CarName"));
            type.setText(intent.getCharSequenceExtra("CarType"));
            size.setText(intent.getCharSequenceExtra("CarSize"));
            image.setImageResource(intent.getIntExtra("CarImage", -1));
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
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
}
