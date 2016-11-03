package br.carlos.nupeds.hellonfc;

import android.app.Application;
import android.content.res.Configuration;

/**
 * Created by NUPEDS on 01/11/2016.
 */
public class HelloNFC extends Application {

    public static final String LOG = "LOG_NFC";

    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }
}
