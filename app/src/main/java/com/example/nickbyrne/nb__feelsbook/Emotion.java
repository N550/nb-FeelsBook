/*
*
* The purpose of this class is creating an
* emotion object.
*
* The design rational is based off of
* Abram Hindle, Student Picker for Android: 3 Developing and Testing the Model
* https://www.youtube.com/watch?v=k9ZNbsc0Qgo , Sep 11, 2014, retrieved sep 23, 2018.
*
* The video uses test driven development to implement object classes.
*
* A comparable function was added in order to sort an array of
* emotions later on.
*
* Issues:
* The main issue ran into was getting the comparable
* function to properly implement. This issue has been solved.
*
 */


package com.example.nickbyrne.nb__feelsbook;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

public class Emotion implements Serializable, Comparable<Emotion> {

    protected String emotionName;
    private Date date;
    protected String comment;

    public Emotion(String emotionName, String comment, Date date) {
        this.emotionName = emotionName; // emotion objects name
        this.date = date;   // emotion objects time
        this.comment = comment; // emotion objects comment
    }

    public String getName() {
        return this.emotionName; // return emotion name
    }

    public String getComment() {
        return this.comment; // return emotion comment
    }

    public Date getDate() {
        return this.date; // return emotion date
    }

    public String toString() {
        return this.emotionName + " " + this.date.toString() + "\n" + this.comment; // return a string of emotion name, date, comment
    }

    @Override
    public int compareTo(@NonNull Emotion o) {
        return date.compareTo(o.getDate()); // used for sorting two emotions based on date
    }
}
