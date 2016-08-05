package com.example.sgao.crashtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.Date;
import java.text.SimpleDateFormat;
import edu.depaul.cdm.trace.*;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ExceptionHandler.register(this, "http://10.0.2.2:8080/server.jsp");
        G.USER_NAME="Obama";

        //Or you can do like this
        //ExceptionHandler.register(this, "http://140.192.34.30/server.asp", "Obama");
    }

    public void trigger_crash (View view) {
        System.out.println("I am going to crash!!!!!!");
        try {Thread.sleep(1000);} catch (Exception E) {}
        System.out.println(s.equals("any string"));
    }

    public void generate_log (View view) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(df.format(new Date())+" - This is the log generated from CrashTest application");
    }

    public void active_post (View view) {
        LogSender.post();
    }

}
