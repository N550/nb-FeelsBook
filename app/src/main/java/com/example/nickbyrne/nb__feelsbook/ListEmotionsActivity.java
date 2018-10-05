/*
 *
 * The purpose of this class is creating
 * a history list of emotions.
 * When long clicking on an emotion you
 * get the option to either remove emotion,
 * edit emotion name, edit emotion comment,
 * or edit emotion Date.
 *
 *
 * The design rational is based off of:
 *
 * Abram Hindle, Student Picker for Android: 4 Navigation between Activities
 * https://www.youtube.com/watch?v=fxjIA4HIruU, Sept 11, 2014, retrieved sept, 24, 2018.
 *
 * and
 *
 * Abram Hindle, Student Picker for Android: 5 Controllers and adding students
 * https://www.youtube.com/watch?v=uLnoI7mbuEo, Sept 11, 2014, retrieved sept, 24, 2018.
 *
 * and
 *
 * Abram Hindle, Student Picker for Android: 6 ListView, ArrayAdapter and Observer Pattern
 * https://www.youtube.com/watch?v=7zKCuqScaRE, Sept 13, 2014, retrieved sept, 24, 2018.
 *
 * I added dialog boxes in order to edit date, name, and comment
 *
 * Editing date/time uses listeners to notify the change in date and time
 *
 * Issues:
 *
 */


package com.example.nickbyrne.nb__feelsbook;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;


public class ListEmotionsActivity extends AppCompatActivity{

    private DatePickerDialog.OnDateSetListener DateSetListener;     // listener to update date
    private TimePickerDialog.OnTimeSetListener TimeSetListener;     // listener to update time
    private int hour;
    private int min;
    private int MAX_CHAR = 100;     // max characters for a comment

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emotion_history);
        EmotionListManager.initManager(this.getApplicationContext());

        ListView listView = findViewById(R.id.emotionHistoryListView);
        final Collection<Emotion> emotions = EmotionListController.getEmotionList().getEmotions();
        final ArrayList<Emotion> emotionList = new ArrayList<Emotion>(emotions);
        final ArrayAdapter<Emotion> emotionAdapter = new ArrayAdapter<Emotion>(this, android.R.layout.simple_list_item_1, emotionList);
        listView.setAdapter(emotionAdapter);

        EmotionListController.getEmotionList().addListener(new Listener() {
            @Override
            public void update() {
                emotionList.clear();
                ArrayList<Emotion> emotions = EmotionListController.getEmotionList().getEmotions();
                Collections.sort(emotions);
                emotionList.addAll(emotions);
                emotionAdapter.notifyDataSetChanged();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()       // listener for clicking on emotions

    {
        @Override
        public boolean onItemLongClick (AdapterView < ? > parent, View view, final int position, long id){
        final AlertDialog.Builder editOptionBox = new AlertDialog.Builder(ListEmotionsActivity.this);
        editOptionBox.setMessage("Edit/Remove:" + " " + emotionList.get(position).getName() + " " + emotionList.get(position).getDate().toString() + "?");
        editOptionBox.setCancelable(true);
        final int finalPosition = position;

        TimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hour = hourOfDay;
                min = minute;
            }
        };

        DateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                    Calendar c = Calendar.getInstance();
                    c.set(year, month , dayOfMonth, hour, min);
                    Emotion emotion = emotionList.get(finalPosition);
                    String oldName = emotion.getName();
                    String oldComment = emotion.getComment();

                    EmotionListController.getEmotionList().addEmotion(new Emotion( oldName, oldComment, c.getTime()));
                    EmotionListController.getEmotionList().removeEmotion(emotion);
                }
            };


        editOptionBox.setPositiveButton("Edit", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder editEmotionBox = new AlertDialog.Builder(ListEmotionsActivity.this);
                        editEmotionBox.setMessage("Edit Emotion:" + " " + emotionList.get(position).getName() + " " + emotionList.get(position).getDate().toString() + "?");
                        editEmotionBox.setCancelable(true);
                        final int finalPosition = position;
                        editEmotionBox.setPositiveButton("Edit Emotion", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ListEmotionsActivity.this);
                                    builder.setTitle("Choose Emotion")
                                            .setItems(R.array.emotion_array, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    final String[] emotionArray = {"Love", "Joy", "Surprise" , "Anger",
                                                            "Sadness", "Fear"};
                                                    Emotion emotion = emotionList.get(finalPosition);
                                                    Date oldDate = emotion.getDate();
                                                    String oldComment = emotion.getComment();
                                                    EmotionListController.getEmotionList().addEmotion(new Emotion( emotionArray[which], oldComment, oldDate));
                                                    EmotionListController.getEmotionList().removeEmotion(emotion);
                                                }
                                            });

                                    builder.show();
                                }
                        });
                        editEmotionBox.setNegativeButton("Edit Date ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Emotion emotion = emotionList.get(finalPosition);
                                Date oldDate = emotion.getDate();
                                Calendar cal = Calendar.getInstance();
                                cal.setTime(oldDate);
                                int year = cal.get(Calendar.YEAR);
                                int month = cal.get(Calendar.MONTH);
                                int day = cal.get(Calendar.DAY_OF_MONTH);
                                int hour = cal.get(Calendar.HOUR_OF_DAY);
                                int minute = cal.get(Calendar.MINUTE);
                                int second = cal.get(Calendar.SECOND);

                                DatePickerDialog editDateDialog = new DatePickerDialog(ListEmotionsActivity.this, android.R.style.Theme, DateSetListener, year, month, day);
                                editDateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                editDateDialog.show();

                                TimePickerDialog editTimeDialog = new TimePickerDialog(ListEmotionsActivity.this, android.R.style.Theme, TimeSetListener, hour, minute, DateFormat.is24HourFormat(ListEmotionsActivity.this));
                                editTimeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                editTimeDialog.show();
                            }
                        });

                        editEmotionBox.setNeutralButton("Edit Comment", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final EditText commentEditText = new EditText(ListEmotionsActivity.this);
                                commentEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_CHAR)});
                                AlertDialog.Builder commentBox = new AlertDialog.Builder(ListEmotionsActivity.this);
                                commentBox.setMessage("Enter Your Comment:");
                                commentBox.setView(commentEditText);
                                commentBox.setPositiveButton("Add Comment", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String newComment = commentEditText.getText().toString();
                                        Emotion emotion = emotionList.get(finalPosition);
                                        String oldName = emotion.getName();
                                        Date olddate = emotion.getDate();

                                        EmotionListController.getEmotionList().addEmotion(new Emotion( oldName, newComment, olddate));
                                        EmotionListController.getEmotionList().removeEmotion(emotion);


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
                        editEmotionBox.show();
                    }

        });

        editOptionBox.setNegativeButton("Remove", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Emotion emotion = emotionList.get(finalPosition);
                EmotionListController.getEmotionList().removeEmotion(emotion);

            }
        });

        editOptionBox.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
            });

        editOptionBox.show();
        return false;
    }
    });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it it present.
        getMenuInflater().inflate(R.menu.emotion_menu, menu);
        return true;
    }
}

