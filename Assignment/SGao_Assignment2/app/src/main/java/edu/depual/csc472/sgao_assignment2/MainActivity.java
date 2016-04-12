package edu.depual.csc472.sgao_assignment2;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    enum BUTTON {
        NUMBER,
        PLUS,
        EQUAL,
    }
    BUTTON lastPressed = BUTTON.NUMBER;
    int current = 0, memory = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tv = (TextView) findViewById(R.id.display);
        Button b1 = (Button) findViewById(R.id.button1);
        Button b2 = (Button) findViewById(R.id.button2);
        Button b3 = (Button) findViewById(R.id.button3);
        Button b4 = (Button) findViewById(R.id.button4);
        Button b5 = (Button) findViewById(R.id.button5);
        Button b6 = (Button) findViewById(R.id.button6);
        Button b7 = (Button) findViewById(R.id.button7);
        Button b8 = (Button) findViewById(R.id.button8);
        Button b9 = (Button) findViewById(R.id.button9);
        Button b0 = (Button) findViewById(R.id.button0);
        Button bA = (Button) findViewById(R.id.buttonAdd);
        Button bE = (Button) findViewById(R.id.buttonEqual);

        View.OnClickListener listener = new View.OnClickListener () {
            public void onClick (View v) {
                char ch = ((Button) v).getText().charAt(0);
                if (ch >= '0' && ch <= '9') { // 0 - 9 pressed
                    if (lastPressed == BUTTON.NUMBER) {
                        current = current * 10 + (int)(ch - '0');
                    }
                    else if (lastPressed == BUTTON.PLUS) {
                        memory = current;
                        current = (int)(ch - '0');
                    }
                    else if (lastPressed == BUTTON.EQUAL) {
                        memory = 0;
                        current = (int)(ch - '0');
                    }
                    lastPressed = BUTTON.NUMBER;
                }
                else if (ch == '+') { // + pressed
                    current = current + memory;
                    memory = 0;
                    lastPressed = BUTTON.PLUS;
                }
                else if (ch == '=') { // = pressed
                    current = current + memory;
                    memory = 0;
                    lastPressed = BUTTON.EQUAL;
                }
                tv.setText(current + "");
            }
        };
        b1.setOnClickListener(listener);
        b2.setOnClickListener(listener);
        b3.setOnClickListener(listener);
        b4.setOnClickListener(listener);
        b5.setOnClickListener(listener);
        b6.setOnClickListener(listener);
        b7.setOnClickListener(listener);
        b8.setOnClickListener(listener);
        b9.setOnClickListener(listener);
        b0.setOnClickListener(listener);
        bA.setOnClickListener(listener);
        bE.setOnClickListener(listener);
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
