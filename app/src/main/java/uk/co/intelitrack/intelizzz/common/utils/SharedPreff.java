package uk.co.intelitrack.intelizzz.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


public class SharedPreff {


        private static final String USERID = "USERID";

        private static final String SESIONID = "sesionID";

        private static SharedPreferences getPreferences (Context c) {

            return c.getApplicationContext().getSharedPreferences("MySharedPreffsFile", Activity.MODE_PRIVATE);

        }
    public static void  addUser(String Name, Context c) {
        getPreferences(c).edit().putString("name",Name).apply();
    }
    public static String getUser( Context c) {
        return getPreferences(c).getString( "name","");
    }

        public static void addGroupID (String Email, Context c)  {

        getPreferences(c).edit().putString("GroupID", Email).apply();
    }

    public static  String getGroupID (Context c) {

        return getPreferences(c).getString("GroupID", "");
    }




}
