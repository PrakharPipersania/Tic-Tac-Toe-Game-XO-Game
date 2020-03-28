package com.miniapp.xogame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button[][] buttons = new Button[3][3];
    private boolean player1turn = true;
    private int roundcount;
    private int player1points;
    private int player2points;
    private TextView textviewp1;
    private TextView textviewp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        textviewp1 = findViewById(R.id.textview1);
        textviewp2 = findViewById(R.id.textview2);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonid = "button"+i+j;
                int resid = getResources().getIdentifier((buttonid),"id", getPackageName());
                buttons[i][j] = findViewById(resid);
                buttons[i][j].setOnClickListener(this);
            }
        }
        Button buttonreset = findViewById(R.id.buttonrst);
        buttonreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Button) v).setText("RESET");
                resetgame();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        if (player1turn) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }
        roundcount++;
        if(checkforwin()){
            if(player1turn){
                player1win();
            }else{
                player2win();
            }
        }else if(roundcount==9){
            draw();
        }else{
            player1turn=!player1turn;
        }
    }

    private boolean checkforwin() {
        String[][] feild = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                feild[i][j] = buttons[i][j].getText().toString();
            }
        }
        for(int i=0;i<3;i++)
        {
            if(feild[i][0].equals(feild[i][1])
            &&feild[i][0].equals(feild[i][2])
            &&!feild[i][0].equals(""))
            {
                return true;
            }
        }
        for(int i=0;i<3;i++)
        {
            if(feild[0][i].equals(feild[1][i])
                    &&feild[0][i].equals(feild[2][i])
                    &&!feild[0][i].equals("")) {
                return true;
            }
        }
        if(feild[0][0].equals(feild[1][1])&&feild[0][0].equals(feild[2][2])&&!feild[0][0].equals(""))
        {
            return true;
        }
        if(feild[2][0].equals(feild[1][1])&&feild[2][0].equals(feild[0][2])&&!feild[2][0].equals(""))
        {
            return true;
        }
        return false;
    }

    private void player1win(){
        player1points++;
        Toast.makeText(this,"Player 1 Wins!",Toast.LENGTH_LONG).show();
        textviewp1.setTextColor(Color.GREEN);
        textviewp2.setTextColor(Color.WHITE);
        updatepoints();
        resetboard();
    }

    private void player2win(){
        player2points++;
        Toast.makeText(this,"Player 2 Wins!",Toast.LENGTH_LONG).show();
        textviewp1.setTextColor(Color.WHITE);
        textviewp2.setTextColor(Color.GREEN);
        updatepoints();
        resetboard();
    }

    private void draw(){
        Toast.makeText(this,"Match Draw!",Toast.LENGTH_LONG).show();
        textviewp1.setTextColor(Color.WHITE);
        textviewp2.setTextColor(Color.WHITE);
        resetboard();
    }

    private void updatepoints(){
        textviewp1.setText("Player 1: " + player1points);
        textviewp2.setText("Player 2: " + player2points);
    }

    private void resetboard(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        roundcount=0;
        player1turn= true;
    }

    private void resetgame(){
        player1points=0;
        player2points=0;
        updatepoints();
        resetboard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundcount",roundcount);
        outState.putInt("player1points",player1points);
        outState.putInt("player2points",player2points);
        outState.putBoolean("player1turn",player1turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundcount=savedInstanceState.getInt("roundcount");
        player1points=savedInstanceState.getInt("player1points");
        player2points=savedInstanceState.getInt("player2points");
        player1turn=savedInstanceState.getBoolean("player1turn");
    }
}
