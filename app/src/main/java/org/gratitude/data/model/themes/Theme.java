
package org.gratitude.data.model.themes;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;

import org.gratitude.data.api.ApiHandler;
import org.gratitude.data.api.ApiInterfaces;
import org.gratitude.data.model.response.AllThemes;
import org.gratitude.main.interfaces.ResponseInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class Theme {

    @Expose
    private String id;
    @Expose
    private String name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static class Builder {

        private String id;
        private String name;

        public Theme.Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Theme.Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Theme build() {
            Theme theme = new Theme();
            theme.id = id;
            theme.name = name;
            return theme;
        }

    }

    public static void getThemes(Context context, final ResponseInterface<Themes> responseInterface) {
        ApiInterfaces apiService = ApiHandler.getApiService(context, false);
        Call<AllThemes> responseThemes = apiService.getThemes();

        responseThemes.enqueue(new Callback<AllThemes>() {
            @Override
            public void onResponse(@NonNull Call<AllThemes> call, @NonNull Response<AllThemes> response) {
                Timber.d(response.toString());
                Themes themes = new AllThemes.Builder()
                        .withThemes(response.body().getThemes())
                        .build()
                        .getThemes();
                responseInterface.onResponseLoaded(themes);
            }

            @Override
            public void onFailure(@NonNull Call<AllThemes> call, @NonNull Throwable t) {
                Timber.e(t);
            }
        });
    }

}
