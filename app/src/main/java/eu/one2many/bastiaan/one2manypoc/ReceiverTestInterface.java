package eu.one2many.bastiaan.one2manypoc;

import eu.one2many.bastiaan.one2manypoc.model.Message;

/**
 * Created by Gebruiker on 25-9-2017.
 */

public interface ReceiverTestInterface {

    void saveMessageToDatabase(Message message);
    void setViews(Message message);
}
