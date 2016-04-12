package edu.depaul.csc472.progressbarasync;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {

  private ProgressBar progressBar;
  private TextView textView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    progressBar = (ProgressBar) findViewById(R.id.progressbar);
    progressBar.setProgress(0);
    textView = (TextView) findViewById(R.id.loading);
  }

  public void start(View view) {
    new LongTask().execute();
  }

  private class LongTask extends AsyncTask<Void, Integer, Void> {
    @Override
    protected Void doInBackground(Void... params) {
      status = 0;
      while (status <= 100) {
        doWork();
        publishProgress(status);
      }
      return null;
    }

    private int status = 0;

    private void doWork() {
      try {
        Thread.currentThread().sleep(100);
      } catch (InterruptedException e) {
      }
      ++status;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
      progressBar.setProgress(progress[0]);
      textView.setText("Loading ... " + status + "%");
    }

    @Override
    protected void onPreExecute() {
      textView.setText("Loading ...");
    }

    @Override
    protected void onPostExecute(Void result) {
      textView.setText("Done");
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
}
