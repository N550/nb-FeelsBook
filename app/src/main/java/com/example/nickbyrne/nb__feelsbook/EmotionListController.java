/*
*
* The purpose of this class is creating an
* emotionList controller to process view user inputs.
*
* The design rational is based off of
* Abram Hindle, Student Picker for Android: 3 Developing and Testing the Model
* https://www.youtube.com/watch?v=k9ZNbsc0Qgo , Sep 11, 2014, retrieved sep 23, 2018.
* The video uses test driven development to implement object classes.
*
* and
*
* Abram Hindle, Student Picker for Android: 8 Serialization, Transients, Equals, etc.
* https://www.youtube.com/watch?v=uat-8Z6U_Co, sep 14, 2014, retrieved sep 28, 2018.
* This video allows for saving emotionList on the device
*
*
* Issues:
*
 */

package com.example.nickbyrne.nb__feelsbook;

import java.io.IOException;

public class EmotionListController {

    private static EmotionList emotionList = null;
    static public EmotionList getEmotionList() {
        if (emotionList == null){
            try {
                emotionList = EmotionListManager.getManager().loadEmotionList();
                emotionList.addListener(new Listener() {
                    @Override
                    public void update() {
                        saveEmotionList();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Could not deserialize emotionList from emotionListManager");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException("Could not deserialize emotionList from emotionListManager");
            }
        }
        return emotionList;
    }

    static public void saveEmotionList() {
        try {
            EmotionListManager.getManager().saveEmotionList(getEmotionList());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not deserialize emotionList from emotionListManager");
        }

    }


    public void addEmotion(Emotion emotion) {
        getEmotionList().addEmotion(emotion);
    }
}
