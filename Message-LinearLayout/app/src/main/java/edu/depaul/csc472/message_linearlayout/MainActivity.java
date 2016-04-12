package edu.depaul.csc472.message_linearlayout;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final EditText message = (EditText) findViewById(R.id.message);
    message.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        boolean handled = false;
        if (actionId == EditorInfo.IME_ACTION_SEND) {
          InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
          imm.hideSoftInputFromWindow(message.getWindowToken(), 0);

          sendMessage();
          handled = true;
        }
        return handled;
      }
    });

    Button send = (Button) findViewById(R.id.send);
    send.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        sendMessage();
      }
    });

  }

  // Uncomment this method if you use the onClick attribute of the Send button
  public void sendMessage(View v) {
    sendMessage();
  }

  protected void sendMessage() {
    EditText to = (EditText) findViewById(R.id.to);
    EditText subject = (EditText) findViewById(R.id.subject);
    EditText message = (EditText) findViewById(R.id.message);

    String messageText = "Sending a message to " + to.getText() + "\n" +
        "Subject: " + subject.getText() + "\n" +
        message.getText();
    int duration = Toast.LENGTH_SHORT;
    //int duration = Toast.LENGTH_LONG;
    Toast toast = Toast.makeText(this, messageText, duration);
    toast.show();
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
