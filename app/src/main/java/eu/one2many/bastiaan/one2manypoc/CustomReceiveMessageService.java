package eu.one2many.bastiaan.one2manypoc;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class CustomReceiveMessageService extends FirebaseMessagingService {

    // Private broadcaster, used to communicate data to others, for instance the MainActivity.
    private LocalBroadcastManager broadcaster;

    @Override
    public void onCreate() {
        broadcaster = LocalBroadcastManager.getInstance(this);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.e("MessageReceiverService", "We made it to the receiving service!");

        // Prepare the intent object to be filled with the retrieved data.
        Intent intent = new Intent("MessageContents");

        // Check if there is a notification in the message.
        if(remoteMessage.getNotification() != null){
            RemoteMessage.Notification notification = remoteMessage.getNotification();
            intent.putExtra("notification_body", notification.getBody());
        }

        // Check if there is data in the message
        if(remoteMessage.getData().size() > 0){
            intent.putExtra("title", remoteMessage.getData().get("title"));
            intent.putExtra("message", remoteMessage.getData().get("message"));

            if(remoteMessage.getData().get("topic") != null) {
                intent.putExtra("topic", remoteMessage.getData().get("topic"));
            } else {
                intent.putExtra("topic", "General");
            }
        }

        // Add the date the message was sent to the bundle
        intent.putExtra("google.sent_time", remoteMessage.getSentTime());

        Log.e("MessageReceiverService", "Made it to the end, next call should be to broadcast the intent.");
        broadcaster.sendBroadcast(intent);
    }
}
