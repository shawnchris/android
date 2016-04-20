package edu.purdue.cs180;

import android.content.Context;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Button;
import android.widget.Toast;

public class TicTacToeView implements TicTacToeViewInterface {
    Button[][] myButtons; //The 3x3 matrix of buttons
    Button newGameButton; //The "New Game" button
    Context context;
    /**
     * Constructor. Initializes the instance variables.
     */
    public TicTacToeView(Context context, Button[][] myButtons, Button newGameButton) {
        this.context = context;
        this.myButtons = myButtons;
        this.newGameButton = newGameButton;
    }
    @Override
    public void update(int x, int y, char player) {
        //Step 1: Update the text of button at index (x, y) to the player's symbol. In order to
        //convert a char to a String, you can do (charVariable + "")
        //Example usage : myButtons[x][y].setText("" + player);
        myButtons[x][y].setText("" + player);
        //Step 2: Disable the button at index (x, y) from being clicked further.
        //Example usage : myButtons[x][y].setEnabled(false);
        myButtons[x][y].setEnabled(false);
        //Step 3: Set text color of the button to BLUE for O or RED for X.
        //Example usage : myButtons[x][y].setTextColor(Color.BLUE);
        if (player == 'O') {
            myButtons[x][y].setTextColor(Color.BLUE);
        }
        else {
            myButtons[x][y].setTextColor(Color.RED);
        }
    }
    @Override
    public void showWinner(String winner) {
        disableButtons();
        //show toast
        CharSequence text = "Player " + winner + " wins!";
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        //play sound
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(context, notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void resetButtons() {
        //Step 1: Iterate through the myButton matrix.
        //Step 2: Reset the text of the current button.
        //Example usage : myButtons[i][j].setText("");
        //Step 3: Reset the color of text for the current button
        //Example usage : myButtons[x][y].setTextColor(Color.BLACK);
        //Step 4: Make the button clickable again
        //Example usage : myButtons[x][y].setEnabled(true);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                myButtons[i][j].setText("");
                myButtons[i][j].setTextColor(Color.BLACK);
                myButtons[i][j].setEnabled(true);
            }
        }
    }
    @Override
    public void disableButtons() {
        //Step 1: Iterate through the myButton matrix.
        //Step 2: Make the current button un-clickable
        //Example usage : myButtons[x][y].setEnabled(false);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                myButtons[i][j].setEnabled(false);
            }
        }
    }
    @Override
    public void gameOver() {
        disableButtons();
        //show toast
        CharSequence text = "Draw Game!";
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        //play sound
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(context, notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
