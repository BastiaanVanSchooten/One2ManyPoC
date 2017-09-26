package eu.one2many.bastiaan.one2manypoc.model;

import java.util.Date;

/**
 * Created by Gebruiker on 13-9-2017.
 */

public class Message {

    private String title, text;
    private long date;

    public Message(String title, String text, long date){
        this.title = title;
        this.text = text;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public long getDate() {
        return date;
    }
}
