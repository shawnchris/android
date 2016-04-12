package edu.depual.csc472.sgao_assignment3;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.*;

public class MainActivity extends Activity {
    // Class to store an item
    class Item {
        public String name;
        public int quantity;
        public double unitPrice;
        Item(String name, int quantity, double unitPrice) {
            this.name = name;
            this.quantity = quantity;
            this. unitPrice = unitPrice;
        }
    }
    // ArrayList to store the whole order list
    static List<Item> itemList = new ArrayList<Item>();
    // Menu list for auto-complete purpose
    static final String[] MENU = {
            "Big Mac",
            "McChicken",
            "Filet-O-Fish",
            "Chicken McNuggets",
            "Sausage Burrito",
            "French Fries",
    };
    // Unit Price list mapping to each menu item above
    static final double[] PRICE = {
            5.99,
            4.99,
            3.99,
            2.99,
            1.99,
            0.99,
    };

    // Clear order history and create a new order.
    private void newOrder() {
        itemList.clear();
        refreshSummary();
    }
    // Refresh order summary and total price.
    private void refreshSummary() {
        TextView summary = (TextView) findViewById(R.id.summary);
        TextView total = (TextView) findViewById(R.id.totalPrice);
        String text = "";
        double totalPrice = 0;
        for (Item i : itemList) {
            text += i.name + " x" + i.quantity + "\n";
            totalPrice += i.unitPrice * i.quantity;
        }
        summary.setText(text);
        total.setText("$" + String.format("%.2f", totalPrice));
    }
    // Create a new item
    private void newItem() {
        AutoCompleteTextView item = (AutoCompleteTextView) findViewById(R.id.item);
        EditText quantity = (EditText) findViewById(R.id.quantity);
        EditText unitPrice = (EditText) findViewById(R.id.unitPrice);
        item.setText("");
        quantity.setText("1");
        unitPrice.setText("0.00");
    }
    // Add current item to order list
    private void addCurrentItem() {
        AutoCompleteTextView item = (AutoCompleteTextView) findViewById(R.id.item);
        EditText quantity = (EditText) findViewById(R.id.quantity);
        EditText unitPrice = (EditText) findViewById(R.id.unitPrice);
        String itemStr = item.getText().toString();
        int quantityInt = Integer.parseInt(quantity.getText().toString());
        double unitPriceDbl = Double.parseDouble(unitPrice.getText().toString());
        String message = "";

        if (itemStr.length() == 0)
            message = "The item name is empty!";
        else if (quantityInt <= 0)
            message = "Quantity must be greater than 0!";
        else if (unitPriceDbl <= 0)
            message = "Unit Price must be greater than 0!";

        if (message.equals("")) { // Input is valid
            Item i = new Item(itemStr, quantityInt, unitPriceDbl);
            itemList.add(i);
            item.setText("");
            quantity.setText("1");
            unitPrice.setText("0.00");
            message = itemStr + " x" + quantityInt + " added to the order list!";
        }

        Toast t = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        t.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize
        newOrder();
        newItem();
        // Add OnClickListeners to 3 buttons
        final Button newItem = (Button) findViewById(R.id.newItem);
        final Button newOrder = (Button) findViewById(R.id.newOrder);
        Button total = (Button) findViewById(R.id.total);
        newItem.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        newItem();
                    }
                }
        );
        newOrder.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        newOrder();
                    }
                }
        );
        total.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        addCurrentItem();
                        refreshSummary();
                    }
                }
        );
        // Add auto-complete adapter to "item" View
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, MENU);
        final AutoCompleteTextView item = (AutoCompleteTextView) findViewById(R.id.item);
        item.setAdapter(adapter);
        // Add OnEditActionListener to "item" View
        final EditText unitPrice = (EditText) findViewById(R.id.unitPrice);
        final EditText quantity = (EditText) findViewById(R.id.quantity);
        item.setOnEditorActionListener(
                new AutoCompleteTextView.OnEditorActionListener() {
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        boolean handled = false;
                        if (actionId == EditorInfo.IME_ACTION_NEXT) {
                            // If item name is in array MENU, set corresponding unit price
                            for (int i = 0; i < MENU.length; i++) {
                                if (item.getText().toString().equalsIgnoreCase(MENU[i])) {
                                    unitPrice.setText(PRICE[i] + "");
                                    break;
                                }
                            }
                            // Move cursor to "quantity" EditText
                            quantity.requestFocus();
                            handled = true;
                        }
                        return handled;
                    }
                }
        );
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
