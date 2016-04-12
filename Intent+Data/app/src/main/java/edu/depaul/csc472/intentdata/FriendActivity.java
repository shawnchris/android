package edu.depaul.csc472.intentdata;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FriendActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_friend);

    Button reply = (Button) findViewById(R.id.reply);
    reply.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        EditText answer = (EditText) findViewById(R.id.answer);
        Intent data = new Intent();
        data.putExtra("Answer", answer.getText());
        setResult(RESULT_OK, data);
        finish();
      }
    });
    Button dontKnow = (Button) findViewById(R.id.dontknow);
    dontKnow.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        setResult(RESULT_CANCELED);
        finish();
      }
    });
  }

  @Override
  protected void onStart() {
    super.onStart();
    // The activity is about to become visible.
    Intent intent = getIntent();
    if (intent != null) {
      TextView question = (TextView) findViewById(R.id.question);
      question.setText(intent.getCharSequenceExtra("Question"));
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_friend, menu);
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
