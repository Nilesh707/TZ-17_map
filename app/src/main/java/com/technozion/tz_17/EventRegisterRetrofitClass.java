package com.technozion.tz_17;;

import java.util.ArrayList;

/**
 * Created by A on 3/8/2016.
 */
public class EventRegisterRetrofitClass {
    String eventid;
    ArrayList<String> userids;

    EventRegisterRetrofitClass(String eventId,ArrayList<String> userids)
    {
        this.eventid=eventId;
        this.userids=userids;
    }
}
