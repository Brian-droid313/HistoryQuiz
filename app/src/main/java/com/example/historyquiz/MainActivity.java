package com.example.historyquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

// Brian
public class MainActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mBackButton;
    private Button mNextButton;
    private Button mShowAnswerButton;
    private TextView mQuestionTextView;

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_women_suffrage, true),
            new Question(R.string.question_first_republican_president, true),
            new Question(R.string.question_plessy_v_ferguson, false),
            new Question(R.string.question_little_rock_troops, true),
            new Question(R.string.question_current_president, true),
            new Question(R.string.question_current_speaker, false),
    };

    private boolean[] mAnswerButtonEnabled = new boolean[] {
            true,
            true,
            true,
            true,
            true,
            true,
    };


    private int mCurrentIndex = 0;
//    private boolean mWasAnswerShown = false;
    private int mNumQuestions = mQuestionBank.length;
    private int mNumCorrectAnswers = 0;
    private int mNumIncorrectAnswers = 0;

    private static final String TAG = "MainActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_ANSWER_SHOWN = "answer_shown";
    private static final String KEY_ANSWER_BUTTONS_ENABLED = "answer_buttons_enabled";
    private static final String KEY_CORRECT_ANSWERS = "correct_answers";
    private static final String KEY_INCORRECT_ANSWERS = "incorrect_answers";

    private static final int ANSWER_SHOWN_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(bundle) called");
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);
//            mWasAnswerShown =  savedInstanceState.getBoolean(KEY_ANSWER_SHOWN);
            mAnswerButtonEnabled = savedInstanceState.getBooleanArray(KEY_ANSWER_BUTTONS_ENABLED);
            mNumCorrectAnswers = savedInstanceState.getInt(KEY_CORRECT_ANSWERS);
            mNumIncorrectAnswers = savedInstanceState.getInt(KEY_INCORRECT_ANSWERS);
        }

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean answer = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(MainActivity.this, answer);
//                startActivity(intent);
                startActivityForResult(intent, ANSWER_SHOWN_CODE);
            }
        });

        mBackButton = (Button) findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mCurrentIndex != 0) {
                    mCurrentIndex--;
                    updateQuestion();
                }
                else {
                    Toast.makeText(MainActivity.this, R.string.back_error, Toast.LENGTH_SHORT).show();
                }
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                if (mCurrentIndex == mQuestionBank.length - 1 && (mNumQuestions != mNumCorrectAnswers + mNumIncorrectAnswers)) {
                    Toast.makeText(MainActivity.this, mNumQuestions + " " + mNumCorrectAnswers + " " + mNumIncorrectAnswers, Toast.LENGTH_SHORT).show();
                }
                else if(mCurrentIndex == mQuestionBank.length - 1 && (mNumQuestions == mNumCorrectAnswers + mNumIncorrectAnswers)) {
                    Intent intent = FinalScoreActivity.newIntent(MainActivity.this, mNumCorrectAnswers, mNumIncorrectAnswers);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "Start new activity", Toast.LENGTH_SHORT).show();
                }
                else {
                    mCurrentIndex++;
//                    mWasAnswerShown = false;
                    updateQuestion();
                }
            }
        });

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        updateQuestion();
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
//                updateQuestion();
            }
        });
    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        if (mAnswerButtonEnabled[mCurrentIndex] == true) {
            enableAnswerButtons();
        }
        else {
            disableAnswerButtons();
        }
    }

    private void disableAnswerButtons() {
        mTrueButton.setEnabled(false);
        mFalseButton.setEnabled(false);
    }

    private void enableAnswerButtons() {
        mTrueButton.setEnabled(true);
        mFalseButton.setEnabled(true);
    }

    private void checkAnswer(boolean userPressed) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;
//        if (mWasAnswerShown) {
//            messageResId = R.string.judgment_toast;
//            Toast.makeText(this, messageResId, Toast.LENGTH_LONG).show();
//        }
//        else {
            if (userPressed == answerIsTrue) {
                messageResId = R.string.correct_toast;
                mNumCorrectAnswers++;
            } else {
                messageResId = R.string.incorrect_toast;
                mNumIncorrectAnswers++;
            }

            Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
            mAnswerButtonEnabled[mCurrentIndex] = false;
            disableAnswerButtons();
//        }
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
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState called");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
//        savedInstanceState.putBoolean(KEY_ANSWER_SHOWN, mWasAnswerShown);
        savedInstanceState.putBooleanArray(KEY_ANSWER_BUTTONS_ENABLED, mAnswerButtonEnabled);
        savedInstanceState.putInt(KEY_CORRECT_ANSWERS, mNumCorrectAnswers);
        savedInstanceState.putInt(KEY_INCORRECT_ANSWERS, mNumIncorrectAnswers);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult called");
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == ANSWER_SHOWN_CODE) {
            if (data == null) {
                return;
            }
//            mWasAnswerShown = CheatActivity.wasAnswerShown(data);
            if (CheatActivity.wasAnswerShown(data)) {
                if(mAnswerButtonEnabled[mCurrentIndex]) {
                    mNumIncorrectAnswers++;
                    mAnswerButtonEnabled[mCurrentIndex] = false;
                    disableAnswerButtons();
                }
            }
        }
    }
}