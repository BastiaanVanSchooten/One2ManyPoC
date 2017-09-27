package eu.one2many.bastiaan.one2manypoc.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import eu.one2many.bastiaan.one2manypoc.R;
import eu.one2many.bastiaan.one2manypoc.model.Message;

public class MessageListAdapter extends ArrayAdapter<Message> {

    public MessageListAdapter(@NonNull Context context, @NonNull List<Message> objects) {
        super(context, -1, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listitem_message, parent, false);
        }

        //Bind and set views here
        Message message = getItem(position);

        TextView title = convertView.findViewById(R.id.tv_listitem_title);
        title.setText(message.getTitle());

        TextView date = convertView.findViewById(R.id.tv_listitem_date);

        Date sentDate = new Date(message.getDate());
        SimpleDateFormat fmt = new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm");

        date.setText(fmt.format(sentDate));

        return convertView;
    }
}
