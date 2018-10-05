package com.example.nickbyrne.nb__feelsbook;


import junit.framework.TestCase;
import com.example.nickbyrne.nb__feelsbook.Emotion;
import java.util.Date;



public class EmotionTest extends TestCase {
    private Date date;
    public void testNameEmotion() {
        String emotionName = "A Emotion";
        Emotion emotion = new Emotion(emotionName);
        assertTrue("Emotion name is not equal", emotionName.equals(emotion.getName()));
    }


    public void testDateEmotion() {
        String emotionName = "A Emotion";
        Date emotionDate = new Date();
        Emotion emotion = new Emotion(emotionName);
        assertTrue("Date is not equal", emotionDate.equals(emotion.getDate()));
    }

    public void testCountEmotion() {
        String emotionName = "A Emotion";
        Date emotionDate = new Date();
        Integer Count = 0;
        Emotion emotion = new Emotion(emotionName);

        assertTrue("Count is not equal", Count.equals(emotion.Count()));
    }




}