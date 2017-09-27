package eu.one2many.bastiaan.one2manypoc;

import eu.one2many.bastiaan.one2manypoc.model.Message;

public interface NotificationReceiverCallbacks {

    void saveMessageToDatabase(Message message);
    void setViews(Message message);
}
