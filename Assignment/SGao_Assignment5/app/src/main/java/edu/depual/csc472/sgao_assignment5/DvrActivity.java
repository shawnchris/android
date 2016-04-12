package edu.depual.csc472.sgao_assignment5;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class DvrActivity extends Activity {
    private String[] powerStr = {"Off", "On"};
    private String[] stateStr = {"Stopped", "Playing", "Paused",
            "Forwarding", "Rewinding", "Recording"};
    static int power = 0;
    static int state = 0;

    private void setButtonEnable (boolean flag) {
        ((Button) findViewById(R.id.button_play)).setEnabled(flag);
        ((Button) findViewById(R.id.button_stop)).setEnabled(flag);
        ((Button) findViewById(R.id.button_pause)).setEnabled(flag);
        ((Button) findViewById(R.id.button_fast_forward)).setEnabled(flag);
        ((Button) findViewById(R.id.button_fast_rewind)).setEnabled(flag);
        ((Button) findViewById(R.id.button_record)).setEnabled(flag);
    }

    private void refresh() {
        TextView tvPower = (TextView) findViewById(R.id.text_power);
        TextView tvState = (TextView) findViewById(R.id.text_state);
        Switch swPower = (Switch) findViewById(R.id.power_button);

        if (power == 0) {
            setButtonEnable(false);
            swPower.setChecked(false);
        }
        else {
            setButtonEnable(true);
            swPower.setChecked(true);
        }

        tvPower.setText(powerStr[power]);
        tvState.setText(stateStr[state]);
    }

    // Handle all the button click events
    public void onButtonClicked(View view) {
        int id = view.getId();
        String message = "";
        if (id == R.id.button_stop) {
            state = 0;
        }
        else if (id == R.id.button_play) {
            if (state == 5)
                message = "Can not Play while Recording";
            else
                state = 1;
        }
        else if (id == R.id.button_pause) {
            if (state == 1)
                state = 2;
            else
                message = "Can not Pause while " + stateStr[state];
        }
        else if (id == R.id.button_fast_forward) {
            if (state == 1)
                state = 3;
            else
                message = "Can not Forward while " + stateStr[state];
        }
        else if (id == R.id.button_fast_rewind) {
            if (state == 1)
                state = 4;
            else
                message = "Can not Rewind while " + stateStr[state];
        }
        else if (id == R.id.button_record) {
            if (state == 0)
                state = 5;
            else
                message = "Can not Record while " + stateStr[state];
        }

        if (message.equals("")) {
            refresh();
        }
        else {
            Toast t = Toast.makeText(this, message, Toast.LENGTH_SHORT);
            t.show();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dvr);
        refresh();

        CompoundButton.OnCheckedChangeListener powerButtonListener = new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton button, boolean isChecked) {
                if (isChecked)
                    power = 1;
                else {
                    power = 0;
                    state = 0;
                }
                refresh();
            }
        };
        Switch swPower = (Switch) findViewById(R.id.power_button);
        swPower.setOnCheckedChangeListener(powerButtonListener);

        Button toTV = (Button) findViewById(R.id.button_switch_to_tv);
        toTV.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        finish();
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dvr, menu);
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
