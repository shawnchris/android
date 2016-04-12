package edu.depual.csc472.sgao_assignment5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TvActivity extends Activity {
    // Global variables
    private int currentChannel = 1;
    // Favorite channel list
    private int[] favChannels = new int[] {111, 222, 333};
    private List<Integer> keyPressed = new ArrayList<Integer>();
    private List<View> viewList = new ArrayList<View>();

    private void init() {
        // Add all views into the viewList
        viewList.add(findViewById(R.id.text_power));
        viewList.add(findViewById(R.id.text_volume));
        viewList.add(findViewById(R.id.text_channel));
        viewList.add(findViewById(R.id.seek_bar));
        viewList.add(findViewById(R.id.button_0));
        viewList.add(findViewById(R.id.button_1));
        viewList.add(findViewById(R.id.button_2));
        viewList.add(findViewById(R.id.button_3));
        viewList.add(findViewById(R.id.button_4));
        viewList.add(findViewById(R.id.button_5));
        viewList.add(findViewById(R.id.button_6));
        viewList.add(findViewById(R.id.button_7));
        viewList.add(findViewById(R.id.button_8));
        viewList.add(findViewById(R.id.button_9));
        viewList.add(findViewById(R.id.button_plus));
        viewList.add(findViewById(R.id.button_minus));
        viewList.add(findViewById(R.id.button_fav1));
        viewList.add(findViewById(R.id.button_fav2));
        viewList.add(findViewById(R.id.button_fav3));
    }

    private void powerOff() {
        ((TextView)viewList.get(0)).setText("Off");
        ((TextView)viewList.get(1)).setText("--");
        ((TextView)viewList.get(2)).setText("--");
        disableAll();
    }

    private void powerOn() {
        ((TextView)viewList.get(0)).setText("On");
        ((TextView)viewList.get(1)).setText(((SeekBar)viewList.get(3)).getProgress() + "");
        ((TextView)viewList.get(2)).setText(currentChannel + "");
        enableAll();
    }

    private void disableAll() {
        for (View v : viewList) {
            v.setEnabled(false);
            v.setClickable(false);
        }
    }

    private void enableAll() {
        for (View v : viewList) {
            v.setEnabled(true);
            v.setClickable(true);
        }
    }

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
        else if (id == R.id.button_fav1) {
            currentChannel = favChannels[0];
            keyPressed.clear();
        }
        else if (id == R.id.button_fav2) {
            currentChannel = favChannels[1];
            keyPressed.clear();
        }
        else if (id == R.id.button_fav3) {
            currentChannel = favChannels[2];
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
        ((TextView)viewList.get(2)).setText(currentChannel + "");
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                int button = data.getIntExtra("button", -1);
                String label = data.getStringExtra("label");
                int channel = data.getIntExtra("channel", 0);

                if (button >= 1 && button <= 3 && label.length() >= 2 && label.length() <= 4 &&
                        channel >=1 && channel <= 999) {
                    Button b;
                    if (button == 1)
                        b = (Button) findViewById(R.id.button_fav1);
                    else if (button == 2)
                        b = (Button) findViewById(R.id.button_fav2);
                    else
                        b = (Button) findViewById(R.id.button_fav3);
                    b.setText(label);
                    favChannels[button - 1] = channel;
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv);
        init();
        powerOff();

        CompoundButton.OnCheckedChangeListener powerButtonListener = new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton button, boolean isChecked) {
                if (isChecked)
                    powerOn();
                else
                    powerOff();
                keyPressed.clear();
            }
        };
        Switch powerButton = (Switch) findViewById(R.id.power_button);
        powerButton.setOnCheckedChangeListener(powerButtonListener);

        SeekBar.OnSeekBarChangeListener seekBarListener =  new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ((TextView)viewList.get(1)).setText(progress + "");
            }
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            public void onStopTrackingTouch(SeekBar seekBar) {
                keyPressed.clear();
            }
        };
        SeekBar volumeBar = (SeekBar) findViewById(R.id.seek_bar);
        volumeBar.setOnSeekBarChangeListener(seekBarListener);

        Button toDVR = (Button) findViewById(R.id.button_switch_to_dvr);
        toDVR.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        Intent intent = new Intent(TvActivity.this, DvrActivity.class);
                        startActivity(intent);
                    }
                }
        );

        Button toConfig = (Button) findViewById(R.id.button_switch_to_config);
        toConfig.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        Intent intent = new Intent(TvActivity.this, ConfigActivity.class);
                        startActivityForResult(intent, 100);
                    }
                }
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tv, menu);
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
