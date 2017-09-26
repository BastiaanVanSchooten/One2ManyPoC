package eu.one2many.bastiaan.one2manypoc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import eu.one2many.bastiaan.one2manypoc.model.Message;


public class ReceiverTest extends BroadcastReceiver{
    private static ReceiverTest ourInstance = null;
    private ReceiverTestInterface listener;

    public static ReceiverTest getInstance(ReceiverTestInterface context) {
        if(ourInstance == null){
            ourInstance = new ReceiverTest(context);
        }
        return ourInstance;
    }

    private ReceiverTest(ReceiverTestInterface context) {
        this.listener = context;
    }

    public void updateListenerInstance(ReceiverTestInterface newListener){
        listener = newListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // Retrieve the data from the intent and do something with it
        // For now lets try out some fields like getNotification() and getData() and show them
        // in some TextViews for now.

        Log.e("BroadcastReceiver", "The BroadcastReceiver in the MainActivity caught the broadcast, should read intent and set views.");

        Bundle bundle = intent.getExtras();
        Message message = new Message(
                bundle.getString("title") + " [onReceive]",
                bundle.getString("message"),
                bundle.getLong("google.sent_time")
        );

        Log.e("SavingMessageToDatabase", "We're saving a new message from ONRECEIVE!");

        listener.saveMessageToDatabase(message);
        listener.setViews(message);
    }
}
