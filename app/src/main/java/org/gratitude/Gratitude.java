package org.gratitude;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.facebook.stetho.Stetho;

import org.gratitude.utils.FileLoggingTree;
import org.gratitude.utils.ReleaseCrashlyticsTree;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

public class Gratitude extends Application{

    public static Gratitude get(Context context) {
        return (Gratitude) context.getApplicationContext();
    }

    public Context getApplication() {
        return this.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //region Initialize
        CrashlyticsCore core = new CrashlyticsCore.Builder()
                .disabled(BuildConfig.DEBUG)
                .build();
        Fabric.with(this, new Crashlytics.Builder().core(core).build());

        if (BuildConfig.DEBUG) {
            Timber.plant(new FileLoggingTree(getApplicationContext()) {
                // Add the line number to the tag.
                @Override
                protected String createStackElementTag(StackTraceElement element) {
                    return super.createStackElementTag(element) + ':' + element.getLineNumber();
                }
            });
        } else {
            Timber.plant(new ReleaseCrashlyticsTree());
        }

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(this))
                        .build());
        //endregion
    }
}
