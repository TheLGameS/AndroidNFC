package br.carlos.nupeds.hellonfc;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Configuration;

/**
 * Created by NUPEDS on 01/11/2016.
 */
public class ApplicationSingleton extends Application {

    public static final String LOG = "LOG_NFC";
    public static final  String CONFIG_KEY = "configuracao";
    private static final  String NO_SQL = "recomendacaoNoSqlDB";
    public static  SharedPreferences bancoNoSql;

    @Override
    public void onCreate() {
        super.onCreate();
        this.bancoNoSql = getSharedPreferences(NO_SQL, MODE_PRIVATE);
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
