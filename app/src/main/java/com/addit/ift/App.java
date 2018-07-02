package com.addit.ift;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

/**
 * Created by user on 7/8/2016.
 */

public class App extends Application {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "B9NKj1EGsEJ4poqv2uG2jiaTP";
    private static final String TWITTER_SECRET = "QQSJ16IuoCyFxRk4PEAge9tVyQL6RdZQDEHmogo70sfpxCHL4P";
    static Logindetails logindetails = new Logindetails(null, null, false);

    public static class Logindetails {
      public static String Email = null;
      public static String USER_ID = null;
      public  static boolean LoggedIn = false;

        Logindetails(String Email, String USER_ID, boolean LoggedIn) {
            this.Email = Email;
            this.USER_ID = USER_ID;
            this.LoggedIn = LoggedIn;

        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }
}
