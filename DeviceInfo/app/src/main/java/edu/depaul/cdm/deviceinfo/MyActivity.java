package edu.depaul.cdm.deviceinfo;

import android.app.Activity;
import android.app.ActivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        TextView t=new TextView(this);

        t=(TextView)findViewById(R.id.tv01);
        t.setText("android.os.Build info:\n");
        t.append("BOARD=" + Build.BOARD + "\n");
        t.append("BOOTLOADER=" + Build.BOOTLOADER + "\n");
        t.append("BRAND=" + Build.BRAND + "\n");
        t.append("CPU_ABI=" + Build.CPU_ABI + "\n");
        t.append("CPU_ABI2=" + Build.CPU_ABI2 + "\n");
        t.append("DEVICE=" + Build.DEVICE + "\n");
        t.append("DISPLAY=" + Build.DISPLAY + "\n");
        t.append("FINGERPRINT=" + Build.FINGERPRINT + "\n");
        t.append("HARDWARE=" + Build.HARDWARE + "\n");
        t.append("HOST=" + Build.HOST + "\n");
        t.append("ID=" + Build.ID + "\n");
        t.append("MODEL=" + Build.MODEL + "\n");
        t.append("MANUFACTURER=" + Build.MANUFACTURER + "\n");
        t.append("PRODUCT=" + Build.PRODUCT + "\n");
        t.append("RADIO=" + Build.RADIO + "\n");
        t.append("TAGS=" + Build.TAGS + "\n");
        t.append("TIME=" + Build.TIME + "\n");
        t.append("TYPE=" + Build.TYPE + "\n");
        t.append("USER=" + Build.USER + "\n");
        t.append("VERSION.RELEASE=" + Build.VERSION.RELEASE + "\n");
        t.append("VERSION.CODENAME=" + Build.VERSION.CODENAME + "\n");
        t.append("VERSION.INCREMENTAL=" + Build.VERSION.INCREMENTAL + "\n");
        t.append("VERSION.SDK=" + Build.VERSION.SDK + "\n");
        t.append("VERSION.SDK_INT=" + Build.VERSION.SDK_INT + "\n");

        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        int memoryClass = am.getMemoryClass();
        t.append("\n\nMax memory limitation: " + Integer.toString(memoryClass) + "M\n");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
