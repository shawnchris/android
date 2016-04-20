package edu.purdue.cs180;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    TicTacToeView view;
    TicTacToe myObj;
    Context context;
    //Consider onCreate the public static void main in Android. This
    //gets called when you run your application
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Default onCreate statement which is a MUST to be specified
        super.onCreate(savedInstanceState);
        //Sets the layout of the application as to the one specified in activity_main
        this.context = getApplicationContext();
        setContentView(R.layout.activity_main);
        //Declares and initializes buttons
        Button b00 = (Button) findViewById(R.id.button00);
        Button b01 = (Button) findViewById(R.id.button01);
        Button b02 = (Button) findViewById(R.id.button02);
        Button b10 = (Button) findViewById(R.id.button10);
        Button b11 = (Button) findViewById(R.id.button11);
        Button b12 = (Button) findViewById(R.id.button12);
        Button b20 = (Button) findViewById(R.id.button20);
        Button b21 = (Button) findViewById(R.id.button21);
        Button b22 = (Button) findViewById(R.id.button22);
        Button newGame = (Button) findViewById(R.id.newGame);
        //Creates a 2d array of buttons
        Button[][] myButtons = {{b00, b01, b02}, {b10, b11, b12}, {b20, b21, b22}};
        //Initializes the TicTacToeView object
        view = new TicTacToeView(context, myButtons, newGame);
        //Initializes the TicTacToe object
        myObj = new TicTacToe(view);
    }
    /**
     * Gets called whenever a button is clicked as we had specified in the activity_main layout file
     * @param v : The current view
     */
    public void buttonClicked(View v) {
        switch (v.getId()) { //Create a switch for all the IDs we have in the view
            case R.id.button00: //If the element clicked was button at (0, 0)
                myObj.updateGameBoard(0, 0); //Call updateGameBoard with (0, 0) as arguments
                break;
            case R.id.button01:
                myObj.updateGameBoard(0, 1);
                break;
            case R.id.button02:
                myObj.updateGameBoard(0, 2);
                break;
            case R.id.button10:
                myObj.updateGameBoard(1, 0);
                break;
            case R.id.button11:
                myObj.updateGameBoard(1, 1);
                break;
            case R.id.button12:
                myObj.updateGameBoard(1, 2);
                break;
            case R.id.button20:
                myObj.updateGameBoard(2, 0);
                break;
            case R.id.button21:
                myObj.updateGameBoard(2, 1);
                break;
            case R.id.button22:
                myObj.updateGameBoard(2, 2);
                break;
            case R.id.newGame:
                myObj.newGame();
                break;
        }
    }
}
