package edu.depual.csc472.sgao_assignment5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ConfigActivity extends Activity {
    private int currentChannel = 1;
    private List<Integer> keyPressed = new ArrayList<Integer>();

    // Handle all the button click events
    public void onButtonClicked(View view) {
        int id = view.getId();
        if (id == R.id.button_plus) {
            currentChannel++;
            if (currentChannel > 999)
                currentChannel = 1;
            keyPressed.clear();
        }
        else if (id == R.id.button_minus) {
            currentChannel--;
            if (currentChannel < 1)
                currentChannel = 999;
            keyPressed.clear();
        }
        else if (id == R.id.radio_left || id == R.id.radio_middle || id == R.id.radio_right) {
            keyPressed.clear();
        }
        else { // Number buttons
            keyPressed.add(Integer.parseInt(((Button)view).getText().toString()));
            if (keyPressed.size() == 3) {
                int n = keyPressed.get(0) * 100 + keyPressed.get(1) * 10 + keyPressed.get(2);
                if (n >= 1 && n <= 999)
                    currentChannel = n;
                keyPressed.clear();
            }
        }
        ((TextView) findViewById(R.id.channel)).setText(currentChannel + "");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        Button.OnClickListener listener = new Button.OnClickListener() {
            public void onClick(View view) {
                int id = view.getId();
                Intent data = new Intent();
                if (id == R.id.button_cancel) {
                    setResult(RESULT_CANCELED, data);
                    finish();
                }
                else {
                    String message = "";
                    int radioChecked = 0;
                    String label = "";
                    if (((RadioButton)findViewById(R.id.radio_left)).isChecked())
                        radioChecked = 1;
                    else if (((RadioButton)findViewById(R.id.radio_middle)).isChecked())
                        radioChecked = 2;
                    else if (((RadioButton)findViewById(R.id.radio_right)).isChecked())
                        radioChecked = 3;
                    if (radioChecked == 0) {
                        message = "You must select a Favorite Channel Button!";
                    }

                    if (message.equals("")) {
                        label = ((TextView) findViewById(R.id.label)).getText().toString();
                        int len = label.length();
                        if (len < 2 || len > 4)
                            message = "The label must be between 2-4 letters in length!";
                    }

                    if (message.equals("")) {
                        setResult(RESULT_OK, data);
                        data.putExtra("button", radioChecked);
                        data.putExtra("label", label);
                        data.putExtra("channel", currentChannel);
                        finish();
                    }
                    else {
                        Toast t = Toast.makeText(ConfigActivity.this, message, Toast.LENGTH_SHORT);
                        t.show();
                    }
                }
            }
        };
        Button cancel = (Button) findViewById(R.id.button_cancel);
        Button save = (Button) findViewById(R.id.button_save);
        cancel.setOnClickListener(listener);
        save.setOnClickListener(listener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_config, menu);
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
