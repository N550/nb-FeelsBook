/*
 *
 * The purpose of this class is keeping
 * track of the count of a emotion
 * in an emotion list.
 *
 *
 * The design rational is based off of:
 *
 * simply counting emotions in an emotionList
 * based off of the emotions name.
 *
 * Parts of this class are brought over from
 * "ListEmotionsActivity"--mainly adding a listener
 * for when emotions are added/removed.
 *
 *
 * Issues:
 * A possible issue of this class is that the more
 * emotions that get added to the list
 * the more time it will take for this class to execute.
 *
 */


package com.example.nickbyrne.nb__feelsbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Collection;


public class ListCountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        int loveCount = 0;      // how many love in emotionList
        int joyCount = 0;       // how many joy in emotionList
        int surpriseCount = 0;  // how many surprise in emotionList
        int angerCount = 0;     // how many anger in emotionList
        int sadnessCount = 0;   // how many sadness in emotionList
        int fearCount = 0;      // how many fear in emotionList


        super.onCreate(savedInstanceState);
        setContentView(R.layout.emotion_count);
        EmotionListManager.initManager(this.getApplicationContext());

        Collection<Emotion> emotions = EmotionListController.getEmotionList().getEmotions(); // get collection of emotions
        ArrayList<Emotion> listOne = new ArrayList<Emotion>(emotions);
        for (int i = 0; i < listOne.size(); i++) {              // traverse the emotionList and keep track of emotions
            if (listOne.get(i).getName().equals("Love")) {
                loveCount++;

            } else if (listOne.get(i).getName().equals("Joy")) {
                joyCount++;

            } else if (listOne.get(i).getName().equals("Surprise")) {
                surpriseCount++;

            } else if (listOne.get(i).getName().equals("Anger")) {
                angerCount++;

            } else if (listOne.get(i).getName().equals("Sadness")) {
                sadnessCount++;

            } else if (listOne.get(i).getName().equals("Fear")) {
                fearCount++;

            }
        }

        final ArrayList<String> emotionCountList = new ArrayList<String>();     // new array to store emotions + counts
        final String[] emotionArray = {"Love" + " " + loveCount, "Joy" + " "  + joyCount, "Surprise"  + " " + surpriseCount, "Anger"  + " " + angerCount,
                "Sadness"  + " " +  sadnessCount, "Fear"  + " " + fearCount};

        for (int i = 0; i < emotionArray.length; i++) {
            emotionCountList.add(emotionArray[i]);
        }

        ListView listView = findViewById(R.id.emotionCountListView);    // fill ListView with array of emotions + counts
        final ArrayAdapter<String> emotionAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, emotionCountList);
        listView.setAdapter(emotionAdapter);


        EmotionListController.getEmotionList().addListener(new Listener() {     // notify emotion count when emotion added/removed
            @Override
            public void update() {
                emotionCountList.clear();
                Collection<Emotion> emotions = EmotionListController.getEmotionList().getEmotions();
                emotionAdapter.notifyDataSetChanged();

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










