package com.iamsimranjot.scarnesdice;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity{

    private int userOverallScore = 0;
    private int userTurnScore = 0;
    private int computerOverallScore = 0;
    private int computerTurnScore =0;

    TextView gameStatus , scoreCard;
    Button roll , hold , reset;
    ImageView diceFace;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setup();

    }

    public void  rolldice(View view){

        Random rand = new Random();

        int  r = rand.nextInt(6) + 1;

        DiceImage(r);

        if(r != 1){

            userTurnScore += r;
            gameStatus.setText("Your turn score : " + userTurnScore);
        }

        else{

            userTurnScore=0;
            gameStatus.setText("Your turn score : 0");
            computerturn();
        }
    }

    public void DiceImage(int i) {

        switch (i) {

            case 1:

                diceFace.setImageResource(R.drawable.dice1);
                break;

            case 2:

                diceFace.setImageResource(R.drawable.dice2);
                break;

            case 3:

                diceFace.setImageResource(R.drawable.dice3);
                break;

            case 4:

                diceFace.setImageResource(R.drawable.dice4);
                break;

            case 5:

                diceFace.setImageResource(R.drawable.dice5);
                break;

            default:

                diceFace.setImageResource(R.drawable.dice6);
        }
    }

    public void reset_button(View v){

        userOverallScore = 0;
        userTurnScore = 0;
        computerOverallScore = 0;
        computerOverallScore = 0;


        scoreCard.setText(R.string.score);
        gameStatus.setText(R.string.default_message);

        roll.setEnabled(true);
        hold.setEnabled(true);
    }

    public void hold_button(View v){

        userOverallScore += userTurnScore;
        userTurnScore = 0;


        scoreCard.setText("Your score : " + userOverallScore + " Computer score : " + computerOverallScore);
        gameStatus.setText("Your turn score : "+ userTurnScore);

        check_winner();

        if (gameStatus.getText()!="Congrats, You won!!")
            computerturn();
    }


    Handler handler = new Handler();
    Runnable runnable = new Runnable() {

        @Override
        public void run() {

            Random rand = new Random();

            int  r = rand.nextInt(6)+1;

            DiceImage(r);

            if(r != 1){

                if(computerTurnScore < 20) {

                    computerTurnScore += r;
                    gameStatus.setText("Computer turn score : " + computerTurnScore);
                    handler.postDelayed(runnable, 1000);

                }
                else{

                    computerOverallScore+=computerTurnScore;
                    update_status();
                    handler.removeCallbacks(runnable);
                }
            }


            else{

                update_status();
                handler.removeCallbacks(runnable);
            }

        }
    };

    public void computerturn() {

        roll.setEnabled(false);
        hold.setEnabled(false);

        handler.postDelayed(runnable,1000);

    }

    public void update_status(){

        computerTurnScore=0;

        scoreCard.setText("Your score : " + userOverallScore + " Computer score : " + computerOverallScore);
        gameStatus.setText("It's your Turn....  ");

        roll.setEnabled(true);
        hold.setEnabled(true);

        check_winner();
    }

    public void check_winner(){



        if(computerOverallScore >= 100) {

            gameStatus.setText("Computer Wins!!!");

            roll.setEnabled(false);
            hold.setEnabled(false);

        }
        else {

            if (userOverallScore >= 100) {

                gameStatus.setText("Congrats, You won!!");

                roll.setEnabled(false);
                hold.setEnabled(false);
            }
        }
    }

    public void setup(){

        gameStatus = (TextView) findViewById(R.id.game_status);
        scoreCard = (TextView) findViewById(R.id.score_card);
        roll = (Button) findViewById(R.id.rolldice);
        hold = (Button) findViewById(R.id.holddice);
        reset = (Button) findViewById(R.id.resetgame);
        diceFace = (ImageView) findViewById(R.id.dice);
    }
}


