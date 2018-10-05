/*
*
* The purpose of this class is creating an
* emotion list object.
*
* The design rational is based off of
* Abram Hindle, Student Picker for Android: 3 Developing and Testing the Model
* https://www.youtube.com/watch?v=k9ZNbsc0Qgo , Sep 11, 2014, retrieved sep 23, 2018.
* * The video uses test driven development to implement object classes.
*
*and
*
* Abram Hindle, Student Picker for Android: 6 ListView, ArrayAdapter and Observer Pattern
* https://www.youtube.com/watch?v=7zKCuqScaRE, sep 13, 2014, retrieved sep 24, 2018.
* This video allows for the implementation of Listeners
*
*
* Issues:
* getCount is never really used but when I remove it I run
* into problems. Therefore I have kept it.
*
*/


package com.example.nickbyrne.nb__feelsbook;

import java.io.Serializable;
import java.util.ArrayList;


public class EmotionList implements Serializable {

    protected ArrayList<Emotion> emotionList = null; //
    protected transient ArrayList<Listener> listeners = null; //


    public EmotionList(){
       emotionList = new ArrayList<Emotion>();
       listeners = new ArrayList<Listener>(); //
    }

    private ArrayList<Listener> getListeners(){
        if (listeners == null){
            listeners = new ArrayList<Listener>();
            }
            return listeners; //
    }


    public ArrayList<Emotion> getEmotions() {
        return emotionList;
    }

    public void addEmotion(Emotion testEmotion) {
        emotionList.add(testEmotion);
        notifyListeners();
    }

    public void removeEmotion(Emotion testEmotion) {
        emotionList.remove(testEmotion);
        notifyListeners();
    }

    public Integer getCount(Emotion testEmotion) {
        int count = 0;
        for (int i = 0; i < emotionList.size(); i++){
            if (emotionList.get(i) == testEmotion){
                count++;
            }
        }
        return count; // return count of emotions named testEmotion
    }

    public void notifyListeners(){
        for (Listener listener: getListeners()) {
            listener.update();
        }
    }

    public void addListener(Listener l) {
        getListeners().add(l);
    }

    public void removeListener(Listener l) {
        getListeners().remove(l);
    }


}
