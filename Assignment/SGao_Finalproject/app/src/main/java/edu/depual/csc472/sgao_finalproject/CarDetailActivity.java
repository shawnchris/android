package edu.depual.csc472.sgao_finalproject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CarDetailActivity extends Activity {

    String carName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // The activity is about to become visible.
        Intent intent = getIntent();
        if (intent != null) {
            carName = intent.getCharSequenceExtra("CarName").toString();
            TextView desc = (TextView) findViewById(R.id.car_desc);
            ImageView image = (ImageView) findViewById(R.id.car_image);
            desc.setText(carName + " Type: " + intent.getCharSequenceExtra("CarType") +
                    " Brand: " + intent.getCharSequenceExtra("CarBrand"));
            image.setImageResource(intent.getIntExtra("CarImage", -1));
            LinearLayout reviewContainer = (LinearLayout) findViewById(R.id.layout_reviews);
            float stars = 0;

            // Read reviews
            ArrayList<Review> list = Review.REVIEWS.get(carName);
            if (list != null && list.size() > 0) {
                float totalStars = 0;
                for (int i = 0; i < list.size(); i++) {
                    totalStars += list.get(i).stars;
                    TextView review = new TextView(this);
                    review.setText(
                            list.get(i).title + "  " + list.get(i).stars + " star\n" + list.get(i).content
                    );
                    review.setPadding(2, 2, 2, 2);
                    reviewContainer.addView(review, i);
                    Log.d("Car Reviews", "Added review comment " + i);
                }
                stars = totalStars / list.size();
            }
            RatingBar rb = (RatingBar) findViewById(R.id.car_rating);
            rb.setRating(stars);
            rb.setClickable(false);

            Button submit = (Button) findViewById(R.id.user_submit);
            submit.setOnClickListener(
                    new Button.OnClickListener() {
                        public void onClick(View view) {
                            String title = ((EditText) findViewById(R.id.user_name)).getText().toString();
                            Float stars = ((RatingBar) findViewById(R.id.user_rating)).getRating();
                            String content = ((EditText) findViewById(R.id.user_review)).getText().toString();
                            String message = null;
                            if (title == null || title.length() == 0)
                                message = "Please input your name.";
                            if (message == null && (stars == null || stars == 0))
                                message = "Please select your rating.";
                            if (message == null && (content == null || content.length() == 0))
                                message = "Please input your review.";
                            if (message != null) {
                                Toast t = Toast.makeText(CarDetailActivity.this, message, Toast.LENGTH_LONG);
                                t.show();
                            }
                            else {
                                // Add new review to memory hash map.
                                ArrayList<Review> list = Review.REVIEWS.get(carName);
                                if (list == null)
                                    list = new ArrayList<Review>();
                                DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                String date = df.format(Calendar.getInstance().getTime());
                                title += " on " + date;
                                Review review = new Review(title, stars, content);
                                list.add(review);
                                Review.REVIEWS.put(carName, list);

                                // Write new review to shared preference
                                int size = list.size();
                                SharedPreferences prefs = getSharedPreferences(carName, MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("name", carName);
                                editor.putInt("count", size);
                                editor.putString("title"+size, title);
                                editor.putFloat("stars"+size, stars);
                                editor.putString("content"+size, content);
                                editor.commit();

                                setResult(RESULT_OK);
                                finish();
                            }
                        }
                    }

            );
            image.requestFocus();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }
}
