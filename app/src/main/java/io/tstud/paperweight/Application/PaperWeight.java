package io.tstud.paperweight.Application;

import android.app.Application;

import io.tstud.paperweight.Model.Local.Database;
import io.tstud.paperweight.Utils.NetworkMonitor;

/**
 * Created by etino7 on 05/08/2019.
 */
public class PaperWeight extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Database.setInstance(getApplicationContext());
        NetworkMonitor.createInstance(getApplicationContext());
    }
}
