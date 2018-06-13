package org.gratitude;

import android.app.Application;
import android.content.Context;

public class Gratitude extends Application{

    public static Gratitude get(Context context) {
        return (Gratitude) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
