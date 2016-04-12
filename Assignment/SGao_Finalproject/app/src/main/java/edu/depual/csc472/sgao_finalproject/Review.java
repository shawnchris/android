package edu.depual.csc472.sgao_finalproject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Shawn on 11/8/2015.
 */
public class Review {
    public String title;
    public float stars;
    public String content;

    Review (String title, float stars, String content) {
        this.title = title;
        this.stars = stars;
        this.content = content;
    }

    public static HashMap<String, ArrayList<Review>> REVIEWS = new HashMap<String, ArrayList<Review>>();

    static {
        // Default reviews
        Review review;
        ArrayList<Review> list;

        review = new Review(
                "Shawn on 11/8/2014 18:00", 4.5f,
                "Reliable vehicle enjoy the performance and most reliable. very good and great price for what it has..I would very much recommend this car to all my friend."
        );
        list = new ArrayList<>();
        list.add(review);
        REVIEWS.put("Camry", list);

        review = new Review(
                "Brazz on 9/28/2015 18:00", 5.0f,
                "Have had a 2015 XLE AWD for about 3 weeks and just love it up to this point. So far it is very comfortable and has very nice interior. Love the power of the engine and has plenty of get up and go. Would definitely recommend this vehicle to others."
        );
        list = new ArrayList<>();
        list.add(review);
        REVIEWS.put("Highlander", list);
    }
}
