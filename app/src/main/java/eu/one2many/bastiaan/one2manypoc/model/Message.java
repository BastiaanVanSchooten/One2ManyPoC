package eu.one2many.bastiaan.one2manypoc.model;

/**
 * Created by Gebruiker on 13-9-2017.
 */

public class Message {

    private String title, text, topic;
    private long date;

    public Message(String title, String text, long date, String topic){
        this.title = title;
        this.text = text;
        this.date = date;
        this.topic = topic;
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

    public String getTopic() {
        return topic;
    }
}
