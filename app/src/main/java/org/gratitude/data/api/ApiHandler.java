package org.gratitude.data.api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.gratitude.utils.Params;
import org.gratitude.utils.Utility;

import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by paoloc on 26/01/17.
 */

public class ApiHandler {

    public static ApiInterfaces getApiService(Context context) {
        return getApiService(context, true, Params.ENDPOINT);
    }

    public static ApiInterfaces getApiService(Context context, boolean converter) {
        return getApiService(context, converter, Params.ENDPOINT);
    }

    private static ApiInterfaces getApiService(final Context context, boolean useConverter, String ENDPOINT) {

        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(new Cache(context.getCacheDir(), cacheSize))
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {

                        Response originalResponse = chain.proceed(chain.request());
                        if (Utility.isNetworkAvailable(context)) {
                            int maxAge = 60; // read from cache for 1 minute
                            return originalResponse.newBuilder()
                                    .header("Cache-Control", "public, max-age=" + maxAge)
                                    .build();
                        } else {
                            int maxStale = 60 * 60 * 24 * 28; // tolerate 2-weeks stale
                            return originalResponse.newBuilder()
                                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                                    .build();
                        }
                    }
                })
                .build();

        /*OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder();
                Request request = requestBuilder.build();
                Log.d(TAG, request.toString());
                return chain.proceed(request);

            }
        }).build();*/

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .client(okHttpClient);

        if (useConverter) {
            Gson gson = new GsonBuilder().registerTypeAdapterFactory(new WorkaroundGson()).create();
            retrofitBuilder.addConverterFactory(GsonConverterFactory.create(gson));
            retrofitBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        } else {
            retrofitBuilder.addConverterFactory(GsonConverterFactory.create());
        }

        return retrofitBuilder.build().create(ApiInterfaces.class);
    }
}
