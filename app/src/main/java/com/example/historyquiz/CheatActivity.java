package com.example.historyquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CheatActivity extends AppCompatActivity {

    private TextView mAnswerTextView;
    private Button mShowAnswerButton;

    private boolean mIsAnswerShown = false;
    private boolean mAnswer;

    private static final String EXTRA_ANSWER= "answer";
    private static final String EXTRA_ANSWER_SHOWN = "answer_shown";

    private final String KEY_IS_ANSWER_SHOWN = "is_answer_shown";

    private static final String TAG = "CheatActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "OnCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        if (savedInstanceState != null) {
            mIsAnswerShown = savedInstanceState.getBoolean(KEY_IS_ANSWER_SHOWN);
        }

        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);
        mAnswer = getIntent().getBooleanExtra(EXTRA_ANSWER, false);

        if (mIsAnswerShown) {
            if(mAnswer) {
                mAnswerTextView.setText(R.string.correct_toast);
            }
            else {
                mAnswerTextView.setText(R.string.incorrect_toast);
            }
            setAnswerShown(true);
        }

        mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(mAnswer) {
                    mAnswerTextView.setText(R.string.correct_toast);
                }
                else {
                    mAnswerTextView.setText(R.string.incorrect_toast);
                }
                mIsAnswerShown = true;
                setAnswerShown(true);
            }
        });
    }

    public static Intent newIntent(Context context, boolean answer) {
        Intent intent = new Intent(context, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER, answer);
        return intent;
    }
    private void setAnswerShown(boolean isAnswerShown) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, intent);
    }

    public static boolean wasAnswerShown(Intent intent) {
        return intent.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
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
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.d(TAG, "onSaveInstanceState called");
        savedInstanceState.putBoolean(KEY_IS_ANSWER_SHOWN, mIsAnswerShown);
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