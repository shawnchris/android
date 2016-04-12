package edu.depaul.csc472.progressbardemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {

  private ProgressBar progressBar;
  private TextView textView;
  private int status = 0;

  private Handler mHandler = new Handler();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    progressBar = (ProgressBar) findViewById(R.id.progressbar);
    progressBar.setProgress(0);
    textView = (TextView) findViewById(R.id.loading);
  }

  public void start(View view) {
    status = 0;
    progressBar.setProgress(0);
    textView.setText("Loading ...");
    new Thread(new Runnable() {
      public void run() {
        while (status <= 100) {
          status = doWork();

          // Update the progress bar
          mHandler.post(new Runnable() {
            public void run() {
              progressBar.setProgress(status);
              if (status < 100) {
                textView.setText("Loading ... " + status + "%");
              } else {
                textView.setText("Done");
              }
            }
          });

        }
      }
    }).start();
  }


  // bad code
  /*
  public void start(View view) {
    status = 0;
    progressBar.setProgress(0);
    textView.setText("Loading ...");
    new Thread(new Runnable() {
      public void run() {
        while (status <= 100) {
          status = doWork();

          progressBar.setProgress(status);
          if (status < 100) {
            textView.setText("Loading ... " + status + "%");
          } else {
            textView.setText("Done");
          }
        }
      }
    }).start();
  }
  */

  private int doWork() {
    try {
      Thread.currentThread().sleep(100);
    } catch (InterruptedException e) {
    }
    return ++status;
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
}
