package eu.one2many.bastiaan.one2manypoc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import eu.one2many.bastiaan.one2manypoc.model.Message;


public class NotificationReceiver extends BroadcastReceiver{
    private static NotificationReceiver ourInstance = null;
    private NotificationReceiverCallbacks listener;

    public static NotificationReceiver getInstance(NotificationReceiverCallbacks context) {
        if(ourInstance == null){
            ourInstance = new NotificationReceiver(context);
        }
        return ourInstance;
    }

    private NotificationReceiver(NotificationReceiverCallbacks context) {
        this.listener = context;
    }

    public void updateListenerInstance(NotificationReceiverCallbacks newListener){
        listener = newListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        // Retrieve the data from the intent and do something with it
        Bundle bundle = intent.getExtras();
        Message message = new Message(
                bundle.getString("title"),
                bundle.getString("message"),
                bundle.getLong("google.sent_time"),
                bundle.getString("topic")
        );

        // Call the callback methods in the MainActivity
        listener.saveMessageToDatabase(message);
        listener.setViews(message);
    }
}
