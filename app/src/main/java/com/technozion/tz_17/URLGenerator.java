package com.technozion.tz_17;

public class URLGenerator {

    public static final String base = "http://www.technozion.org";
//    public static final String base = "http://192.168.175.136:8000";
//    public static final String base = "http://192.168.137.1:8000";
    public static final String eventdata = "/tz16/eventapp/";
    public static final String login = "/register/loginappuser/";
    public static final String register = "/register/appregister/";
    public static final String updateprofile = "/register/updateappprofile/";
    public static final String regcheck = "/register/regcheck/";
    public static final String addevent = "/register/addappevent/";
    public static final String allevents = "/register/appevents/";
    public static final String ref = "/register/appref/";
    public static final String checkteamuser = "/register/checkappuser";
    public static final String workshopreg = "/register/addappworkshop/";
    public static final String addapppreg = "/register/addappregistration/";
    public static final String addappphosp = "/register/addapphospitality/";
    public static final String addapppboth = "/register/addappboth/";
    public static final String getcollegelist = "/register/getappcolleges/";
    public static final String checkappuser = "/register/checkappuser/";
    public static final String validatetzid = "/register/validateapptzid/";
    public static final String addappevent = "/register/addappevent/";
    public static final String addappworkshop = "/register/addappworkshop/";
    public static final String validateindiid = "/register/validateindiid/";
    public static final String getAppLocation = "/register/getapplocation/";
    public static final String getappdatetime = "/register/getappdatetime/";
    public static final String addnewevents = "/tz16/refreshappevents/";

    static String getUrl(final String URLFor){
        return base+URLFor;
    }

}
