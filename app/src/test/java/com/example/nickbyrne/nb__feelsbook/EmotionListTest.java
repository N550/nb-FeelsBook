package com.example.nickbyrne.nb__feelsbook;

import junit.framework.TestCase;

import java.util.Collection;

public class EmotionListTest extends TestCase {
    public void testEmptyEmotionList() {
        EmotionList emotionList = new EmotionList();
        Collection<Emotion>  emotions = emotionList.getEmotions();
        assertTrue("Empty Emotion List", emotions.size() == 0);
    }
    public void testAddEmotion() {
        EmotionList emotionList = new EmotionList();
        String emotionName = "A Emotion";
        Emotion testEmotion = new Emotion(emotionName);
        emotionList.addEmotion(testEmotion);
        Collection<Emotion>  emotions = emotionList.getEmotions();
        assertTrue("Emotion List size ", emotions.size() == 1);
        assertTrue(emotions.contains(testEmotion));
    }

    public void testRemoveEmotion() {
        EmotionList emotionList = new EmotionList();
        String emotionName = "A Emotion";
        Emotion testEmotion = new Emotion(emotionName);
        emotionList.addEmotion(testEmotion);
        Collection<Emotion>  emotions = emotionList.getEmotions();
        emotionList.removeEmotion(testEmotion);
        emotions = emotionList.getEmotions();
        assertTrue("Emotion List size isn't small enough", emotions.size() == 0);
        assertFalse("Test Emotion Still Contained ", emotions.contains(testEmotion));
    }

    public void testCountEmotion() {
        EmotionList emotionList = new EmotionList();
        String emotionName = "A Emotion";
        Emotion testEmotion = new Emotion(emotionName);
        emotionList.addEmotion(testEmotion);
        emotionList.addEmotion(testEmotion);
        emotionList.addEmotion(testEmotion);
        Collection<Emotion>  emotions = emotionList.getEmotions();
        emotionList.getCount(testEmotion);
        assertTrue("Wrong count ", emotionList.getCount(testEmotion) == 3);

    }

    boolean updated = false;
    public void testNotifyListeners(){
        EmotionList emotionList = new EmotionList();
        updated = false;
        Listener l = new Listener() {
            public void update() {
                EmotionListTest.this.updated = true;


            }
        };
        emotionList.addListener(l);
        emotionList.addEmotion(new Emotion("Newbie"));
        assertTrue("EmotionList didnt fire an update off", this.updated);

    }

    public void testRemoveListeners(){
        EmotionList emotionList = new EmotionList();
        updated = false;
        Listener l = new Listener() {
            public void update() {
                EmotionListTest.this.updated = true;


            }
        };
        emotionList.addListener(l);
        emotionList.removeListener(l);
        emotionList.addEmotion(new Emotion("Newbie"));
        assertFalse("EmotionList didnt fire an update off", this.updated);
    }


}
