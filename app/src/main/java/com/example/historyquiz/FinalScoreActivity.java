package com.example.historyquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FinalScoreActivity extends AppCompatActivity {

    private TextView mScoreTextView;
    private Button mExitButton;

    private int mNumCorrect = 0;
    private int mNumIncorrect = 0;

    private static final String EXTRA_NUM_CORRECT = "num_correct";
    private static final String EXTRA_NUM_INCORRECT = "num_incorrect";

    private static final String TAG = "FinalScoreActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_score);
        Log.d(TAG, "onCreate called");

        mNumCorrect = getIntent().getIntExtra(EXTRA_NUM_CORRECT, 0);
        mNumIncorrect = getIntent().getIntExtra(EXTRA_NUM_INCORRECT, 0);


        mScoreTextView = (TextView) findViewById(R.id.score_text_view);
        mScoreTextView.setText("You got " + mNumCorrect + " correct and " + mNumIncorrect + " incorrect");

        mExitButton = (Button) findViewById(R.id.exit_button);
        mExitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });

    }

    public static Intent newIntent(Context context, int numCorrect, int numIncorrect) {
        Intent intent = new Intent(context, FinalScoreActivity.class);
        intent.putExtra(EXTRA_NUM_CORRECT, numCorrect);
        intent.putExtra(EXTRA_NUM_INCORRECT, numIncorrect);
        return intent;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
}