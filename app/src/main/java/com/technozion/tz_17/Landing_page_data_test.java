package com.technozion.tz_17;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by padma on 20-08-2017.
 */
public class Landing_page_data_test {

    // public static List<String> overview = new ArrayList<String>();
    static List <String > description = new ArrayList<String>();
    static List <String > rules = new ArrayList<String>();
    static List <String > contacts = new ArrayList<String>();

    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();
        expandableListDetail.put("Contact Details", contacts);
        expandableListDetail.put("Rules", rules);
        expandableListDetail.put("Overview", description);
        return expandableListDetail;
    }

    public static void setDescription(String d) {
        Landing_page_data_test.description.clear();
        Landing_page_data_test.description.add(d) ;
        Landing_page_data_test.rules.clear();
        Landing_page_data_test.rules.add(d) ;
    }

    public static void setContacts(String contacts) {
        Landing_page_data_test.contacts.clear();
        Landing_page_data_test.contacts.add(contacts);
    }


    // public void setOverview(String overview) {
    //     this.overview.add(overview) ;
    // }
}

