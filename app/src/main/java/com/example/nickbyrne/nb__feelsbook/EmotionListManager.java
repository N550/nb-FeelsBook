/*
 *
 * The purpose of this class is creating an
 * emotionList manager to save user data.
 *
 * FUll CREDIT for this design rational is given to:
 * Abram Hindle, Student Picker for Android: 7 Persistence, SharedPreferences and Serializable and Equals
 * https://www.youtube.com/watch?v=gmNfc6u1qk0, Sep 14, 2014, retrieved sep 28, 2018.
 * The video teaches how to implement persistence
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

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class EmotionListManager {
    static final String prefFile = "EmotionList";
    static final String elkey = "EmotionList";


    Context context;

    static private EmotionListManager emotionListManager = null;

    public static void initManager(Context context ) {
        if (emotionListManager == null) {
            if (context == null) {
                throw new RuntimeException("missing context for emotionListManager");
            }
            emotionListManager = new EmotionListManager(context);
        }
    }

    public static EmotionListManager getManager(){

        if (emotionListManager == null) {
                throw new RuntimeException("Did not initialize manager ");

        }
        return emotionListManager;
    }


    public EmotionListManager(Context context){
        this.context = context;
    }

    EmotionList loadEmotionList() throws IOException, ClassNotFoundException {
        SharedPreferences settings = context.getSharedPreferences(prefFile, Context.MODE_PRIVATE);
        String emotionListData = settings.getString("EmotionList", "");
        if (emotionListData.equals("")){
            return new EmotionList();
        } else {
            return emotionListFromString(emotionListData);
        }

    }

    public EmotionList emotionListFromString(String emotionListData) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bi = new ByteArrayInputStream(Base64.decode(emotionListData, Base64.DEFAULT));
        ObjectInputStream oi = new ObjectInputStream(bi);
        return (EmotionList)oi.readObject();


    }

    private String emotionListToString(EmotionList em) throws IOException {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(em);
        oo.close();
        byte bytes[] = bo.toByteArray();
        return  Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    public void saveEmotionList(EmotionList el) throws IOException {
        SharedPreferences settings = context.getSharedPreferences(prefFile, Context.MODE_PRIVATE);
        Editor editor = settings.edit();
        editor.putString(elkey,emotionListToString(el));
        editor.commit();



    }
}
