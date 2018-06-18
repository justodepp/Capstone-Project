package org.gratitude;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.facebook.stetho.Stetho;

import org.gratitude.data.api.ApiHandler;
import org.gratitude.data.api.ApiInterfaces;
import org.gratitude.data.model.response.FeaturedProjects;
import org.gratitude.utils.ReleaseCrashlyticsTree;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class Gratitude extends Application{

    private OnFinishedListener mCallback;

    public interface OnFinishedListener {
        void onFinished();
    }
    public static Gratitude get(Context context) {
        return (Gratitude) context.getApplicationContext();
    }

    public void setInterface(OnFinishedListener callback) {
        this.mCallback = callback;
    }
    @Override
    public void onCreate() {
        super.onCreate();

        //region Initialize Service
        CrashlyticsCore core = new CrashlyticsCore.Builder()
                .disabled(BuildConfig.DEBUG)
                .build();
        Fabric.with(this, new Crashlytics.Builder().core(core).build());

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree() {
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

        //region Calling API
        ApiInterfaces apiService = ApiHandler.getApiService(this, false);
        Call<FeaturedProjects> responseFeatured = apiService.getFeaturedProjects();

        responseFeatured.enqueue(new Callback<FeaturedProjects>() {
            @Override
            public void onResponse(@NonNull Call<FeaturedProjects> call, @NonNull Response<FeaturedProjects> response) {
                Timber.d(response.toString());
                //mCallback.onFinished();
            }

            @Override
            public void onFailure(@NonNull Call<FeaturedProjects> call, @NonNull Throwable t) {
                Timber.e(t);
            }
        });
        //endregion
    }
}
