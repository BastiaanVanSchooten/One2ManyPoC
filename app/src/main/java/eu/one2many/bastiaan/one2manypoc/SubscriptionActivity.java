package eu.one2many.bastiaan.one2manypoc;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;

import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Activity for managing subscriptions to Firebase topics.
 */
public class SubscriptionActivity extends AppCompatActivity {

    private boolean topic1, topic2;
    private SwitchCompat switch1, switch2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriptions);

        // Do smart stuff, like:
        // Get SharedPreferences handle
        // Read current settings
        //
        // Bind switch views to variables, and assign onChangeListeners.
        // When change detected, (un)sub to the topic and update SharedPreferences.

        // Save button maken, of niet? Alleen maar voor de illusie.

        switch1 = (SwitchCompat) findViewById(R.id.switch_subscriptions_topic_1);
        switch2 = (SwitchCompat) findViewById(R.id.switch_subscriptions_topic_2);

        final SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        topic1 = preferences.getBoolean("topic1", false);
        topic2 = preferences.getBoolean("topic2", false);

        switch1.setChecked(topic1);
        switch2.setChecked(topic2);

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton widget, boolean checked) {
                if(checked){
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("topic1", true);
                    editor.apply();
                    FirebaseMessaging.getInstance().subscribeToTopic("topic1");
                } else {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("topic1", false);
                    editor.apply();
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("topic1");
                }
            }
        });

        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton widget, boolean checked) {
                if(checked){
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("topic2", true);
                    editor.apply();
                    FirebaseMessaging.getInstance().subscribeToTopic("topic2");
                } else {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("topic2", false);
                    editor.apply();
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("topic2");
                }
            }
        });
    }
//
//    class switchListener implements CompoundButton.OnCheckedChangeListener{
//
//        @Override
//        public void onCheckedChanged(CompoundButton widget, boolean checked) {
//
//        }
//    }
}
