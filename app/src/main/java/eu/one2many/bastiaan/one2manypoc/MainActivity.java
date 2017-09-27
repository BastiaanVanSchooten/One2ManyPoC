package eu.one2many.bastiaan.one2manypoc;

import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

import eu.one2many.bastiaan.one2manypoc.database.MessageSaverContract;
import eu.one2many.bastiaan.one2manypoc.database.MessageSaverDbHelper;
import eu.one2many.bastiaan.one2manypoc.model.Message;
import eu.one2many.bastiaan.one2manypoc.view.MessageListAdapter;

public class MainActivity extends AppCompatActivity implements ReceiverTestInterface {

    TextView dataTV;
    ListView messageListView;
    ArrayList<Message> messages;
    MessageSaverDbHelper dbHelper;
    MessageListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MessageSaverDbHelper(this);

        dataTV = (TextView) findViewById(R.id.tv_data);

        messages = retrieveMessagesFromDatabase();

        messageListView = (ListView) findViewById(R.id.lv_messagelist);
        adapter = new MessageListAdapter(this, messages);
        messageListView.setAdapter(adapter);
        messageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Message message = (Message) adapterView.getItemAtPosition(i);
                dataTV.setText(message.getText());
            }
        });


//        ReceiverTest.getInstance(this).updateListenerInstance(this);

//        // Experimental:
//        if(savedInstanceState != null){
//            // We are recreated (for example after an orientation change), so we need to pass our
//            // 'new us' to the singleton broadcast receiver for the ui callback.
//            ReceiverTest.getInstance(this).updateListenerInstance(this);
//        }

        //        Below code can be used to make the token visible for debugging:
//        String token = FirebaseInstanceId.getInstance().getToken();
//        if(token != null) {
//            Log.d("ID-SERVICE", token);
//            tvTest.setText(token);
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(this).registerReceiver(ReceiverTest.getInstance(this), new IntentFilter("MessageContents"));
        ReceiverTest.getInstance(this).updateListenerInstance(this);

        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            // If there are extras in the bundle, the activity was resumed from a notification
            // The default bundle used on startup and initial creation does not include extras. (?)

            // Code below used to debug the getExtras() contents
//            Bundle bundle = intent.getExtras();
//            for (String key : bundle.keySet()) {
//                Object value = bundle.get(key);
//                Log.e("Intent test", String.format("%s %s (%s)", key,
//                        value.toString(), value.getClass().getName()));
//            }
            Bundle bundle = intent.getExtras();

            // We now know that there is a bundle with some data, but not with what
            // This could be the problem we are having with the onResume()
            // Checking for the google.sent_time should confirm there is a message in there.
            if(bundle.getLong("google.sent_time") != 0) {
                dataTV.setText(bundle.getString("message"));

                Message message = new Message(
                        bundle.getString("title") + " [onResume]",
                        bundle.getString("message"),
                        bundle.getLong("google.sent_time"),
                        bundle.getString("topic")
                );

                if(isNewMessage(message)) {
                    messages.add(message);
                    adapter.notifyDataSetChanged();
                    Log.e("SavingMessageToDatabase", "We're saving a new message from ONRESUME!");
                    saveMessageToDatabase(message);
                }
            }
            // We are done processing the intent, and it should not be processed and added to the
            // list and database a second time. For now, let's set the intent to -null- explicitly.
            // Admittedly, this looks like a pretty crude hack, and there should probably be a cleaner
            // way to check this, like Android.FLAGS thingies.
            this.setIntent(null);
        }

    }

    public void setViews(Message message){

        Log.w("SetViews", "THE SET VIEWS METHOD IS CALLED");
        dataTV.setText(message.getText());
        messages.add(message);
        adapter.notifyDataSetChanged();
    }

    public void saveMessageToDatabase(Message message) {

        Log.d("SaveMessageToDb", "THE SAVE MESSAGE TO DB METHOD IS CALLED");
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MessageSaverContract.MessageEntry.COLUMN_NAME_TITLE, message.getTitle());
        values.put(MessageSaverContract.MessageEntry.COLUMN_NAME_MESSAGE, message.getText());
        values.put(MessageSaverContract.MessageEntry.COLUMN_NAME_DATE, message.getDate());

        db.insert(MessageSaverContract.MessageEntry.TABLE_NAME, null, values);
    }

    private ArrayList<Message> retrieveMessagesFromDatabase() {

        ArrayList<Message> result = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM messages", null);

        while (cursor.moveToNext()) {
            result.add(new Message(
                    cursor.getString(cursor.getColumnIndexOrThrow(MessageSaverContract.MessageEntry.COLUMN_NAME_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(MessageSaverContract.MessageEntry.COLUMN_NAME_MESSAGE)),
                    cursor.getLong(cursor.getColumnIndexOrThrow(MessageSaverContract.MessageEntry.COLUMN_NAME_DATE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(MessageSaverContract.MessageEntry.COLUMN_NAME_TOPIC))));
        }
        cursor.close();

        for(Message message : result){
            Log.e("RetrieveMessages", "Title: " + message.getTitle());
        }

        return result;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(ReceiverTest.getInstance(this));
    }

    private boolean isNewMessage(Message message){
        for(Message mess : messages){
            if(mess.getDate() == message.getDate()){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.topics :
                // Start activity/fragment for managing subscriptions
                Intent intent = new Intent(this, SubscriptionActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
