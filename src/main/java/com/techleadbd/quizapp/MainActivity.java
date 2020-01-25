package com.techleadbd.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class MainActivity extends AppCompatActivity {

    private final String SCORE_KEY = "SCORE";
    private final String INDEX_KEY = "INDEX";

    private TextView mTxtQuestion;

    private Button btnTrue;
    private Button btnWrong;



    private int mQuestionIndex;
    private int mQuizQuestion;

    private ProgressBar mProgressBar;
    private TextView mQuizStatsTextView;

    private int mUserScore;

    private QuizModel[] questionCollection = new QuizModel[]{

           new QuizModel(R.string.q1, true),
           new QuizModel(R.string.q2, true),
           new QuizModel(R.string.q3, false),
           new QuizModel(R.string.q4, false),
           new QuizModel(R.string.q5, false),
           new QuizModel(R.string.q6, true),
           new QuizModel(R.string.q7, false),
           new QuizModel(R.string.q8, true),
           new QuizModel(R.string.q9, false),
           new QuizModel(R.string.q10, true),
            new QuizModel(R.string.q11,true),
            new QuizModel(R.string.q12,false),
            new QuizModel(R.string.q13,false),
            new QuizModel(R.string.q14,true),
            new QuizModel(R.string.q15,false),
            new QuizModel(R.string.q16,true),
            new QuizModel(R.string.q17,true),
            new QuizModel(R.string.q18,false),
            new QuizModel(R.string.q19,false),
            new QuizModel(R.string.q20,true)

    };

    final int  USER_PROGRESS = (int) Math.ceil(100.0 / questionCollection.length);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null){
            mUserScore = savedInstanceState.getInt(SCORE_KEY);
            mQuestionIndex = savedInstanceState.getInt(INDEX_KEY);


        }
        else {
            mUserScore = 0;
            mQuestionIndex = 0;
        }

        mTxtQuestion = findViewById(R.id.txtQuestion);

        QuizModel q1 = questionCollection[mQuestionIndex];
        mQuizQuestion = q1.getQuestion();
        mTxtQuestion.setText(mQuizQuestion);
        mProgressBar = findViewById(R.id.quizPB);
        mQuizStatsTextView = findViewById(R.id.txtQuizeStates);
        mQuizStatsTextView.setText(mUserScore+ "");


        btnTrue = findViewById(R.id.btnTrue);

        btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                evaluateUsersAnswer(true);

                //Android Animation library
                YoYo.with(Techniques.FlipInX)
                        .duration(500)
                        .repeat(0)
                        .playOn(btnTrue);

                changeQuestionOnButtonClick();
            }
        });

        btnWrong = findViewById(R.id.btnWrong);

        btnWrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluateUsersAnswer(false);

                //Android Animation library
                YoYo.with(Techniques.FlipInY)
                        .duration(500)
                        .repeat(0)
                        .playOn(btnWrong);

                changeQuestionOnButtonClick();

            }
        });


    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SCORE_KEY ,mUserScore);
        outState.putInt(INDEX_KEY,mQuestionIndex);

    }

    public void changeQuestionOnButtonClick(){

        mQuestionIndex = (mQuestionIndex + 1) % 20;

        if (mQuestionIndex == 0){
            final AlertDialog.Builder quizAlert = new AlertDialog.Builder(this);
            quizAlert.setCancelable(false);
            quizAlert.setTitle("কুইজ শেষ");
            quizAlert.setMessage("আপনার স্কোর : "+ mUserScore);
            quizAlert.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            quizAlert.show();

        }


        mQuizQuestion = questionCollection[mQuestionIndex].getQuestion();
        mTxtQuestion.setText(mQuizQuestion);
        mProgressBar.incrementProgressBy(USER_PROGRESS);
        mQuizStatsTextView.setText(mUserScore+ "");

    }

    public void evaluateUsersAnswer(boolean userGuess){

        boolean correctQuestionAnswer = questionCollection[mQuestionIndex].isAnswer();

        if (correctQuestionAnswer == userGuess){
            Toast.makeText(getApplicationContext(),R.string.correct_toast_message, Toast.LENGTH_SHORT).show();
            mUserScore = mUserScore + 1;
        }

        else {
            Toast.makeText(getApplicationContext(),R.string.incorrect_toast_message, Toast.LENGTH_SHORT).show();
        }

    }

}
