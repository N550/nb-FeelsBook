/*
 *
 * The purpose of this class is allowing
 * the user to navigate between the main
 * menu, the history list, and the count list.
 *
 * The class also allows the user to enter a new
 * emotion by clicking their current feeling.
 * The user has the option to add a comment
 * after choosing their emotion.
 *
 *
 * The design rational is based off of:
 *
 * Abram Hindle, Student Picker for Android: 4 Navigation between Activities
 * https://www.youtube.com/watch?v=fxjIA4HIruU, Sept 11, 2014, retrieved sept, 24, 2018.
 *
 *
 * I added dialog boxes in order to edit the comment.
 *
 * Issues:
 *
 * One issue that has occured is that when the user
 * first opens the app, sometimes the first
 * emotion that they add gets added twice.
 *
 */



package com.example.nickbyrne.nb__feelsbook;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Emotion noCommentEmotion;       // dummy emotion
    private int MAX_CHAR = 100;     // max characters for a comment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EmotionListManager.initManager(this.getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it it present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void viewHistory(MenuItem menu) {
        Intent intent = new Intent(MainActivity.this, ListEmotionsActivity.class);
        startActivity(intent);
    }

    public void viewEmotionCount(MenuItem menu) {
        Intent intent = new Intent(MainActivity.this, ListCountActivity.class);
        startActivity(intent);
    }

    public void recordAFeeling(View v) {

        EmotionListController em = new EmotionListController();
        Button emotionButton = (Button)v;       // get text from button clicked
        String buttonText = emotionButton.getText().toString();
        Date newDate = new Date();      // current date
        noCommentEmotion = new Emotion(buttonText, "", newDate);    // Add new emotion with no comment
        em.addEmotion(noCommentEmotion);
        AlertDialog.Builder emotionAddedDialog = new AlertDialog.Builder(MainActivity.this);
        emotionAddedDialog.setMessage("Emotion Added!  Would you like to add a comment?");
        emotionAddedDialog.setCancelable(true);

        emotionAddedDialog.setPositiveButton("Add Comment", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final EditText commentEditText = new EditText(MainActivity.this);
                        commentEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_CHAR)});
                        AlertDialog.Builder commentBox = new AlertDialog.Builder(MainActivity.this);
                        commentBox.setMessage("Enter Your Comment:");
                        commentBox.setView(commentEditText);
                        commentBox.setPositiveButton("Add Comment", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String comment = commentEditText.getText().toString();
                                noCommentEmotion.comment = comment;     // add comment to noCommentEmotion

                            }
                        });
                        commentBox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        commentBox.show();
                    }
                });

        emotionAddedDialog.setNegativeButton("No Thanks", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        emotionAddedDialog.show();
    }





}