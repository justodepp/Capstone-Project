package org.gratitude.ui.widget;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.text.Html;
import android.widget.RemoteViews;

import com.bumptech.glide.request.target.AppWidgetTarget;

import org.gratitude.R;
import org.gratitude.data.db.GratitudeDatabase;
import org.gratitude.data.model.projects.Project;
import org.gratitude.main.MainActivity;
import org.gratitude.ui.ProjectsFragment;
import org.gratitude.utils.GlideApp;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Implementation of App Widget functionality.
 */
public class GratitudeWidget extends AppWidgetProvider {

    private GratitudeDatabase mDb;
    private ArrayList<Project> projects;
    private int randomIndex;

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {

        mDb = GratitudeDatabase.getInstance(context);

        // create new thread for querying database
        new AsyncTask<Void, Void, Integer>(){
            @Override
            protected Integer doInBackground(Void... voids) {
                projects = new ArrayList<>(mDb.projectDao().loadAllWidgetProjects());
                return projects.size();
            }

            @Override
            protected void onPostExecute(Integer integer) {

                // There may be multiple widgets active, so update all of them
                for (int appWidgetId : appWidgetIds) {

                    // for each widget, create a random index...
                    randomIndex = ThreadLocalRandom.current().nextInt(0, projects.size());
                    // ... and then a random building
                    Project project = projects.get(randomIndex);
                    // intent and click listener
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra(ProjectsFragment.PRJ_CLICKED, project);
                    // pending intent from https://developer.android.com/training/implementing-navigation/temporal.html
                    // The resulting PendingIntent specifies not only the activity to start,
                    // but also the back and up stack that should be inserted into the task
                    /*PendingIntent pendingIntent = TaskStackBuilder
                            .create(context)
                            .addParentStack(MainActivity.class)
                            .addNextIntent(intent)
                            .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);*/
                    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.gratitude_widget);
                    views.setOnClickPendingIntent(R.id.layout, pendingIntent);

                    AppWidgetTarget appWidgetTarget = new AppWidgetTarget( context, 96, 96, R.id.appwidget_image, views, appWidgetIds );

                    views.setTextViewText(R.id.appwidget_text, project.getTitle());

                    double result = ((project.getFunding() / project.getGoal()) * 100);
                    int progress;
                    if(result > 0 && result < 1) {
                        progress = 1;
                    } else {
                        progress = (int) result;
                    }

                    if (progress < 100) {
                        String text = String.format(context.getString(R.string.money_raised_text),
                                String.valueOf(project.getFunding()),
                                String.valueOf(project.getGoal()));
                        views.setTextViewText(R.id.money_raised, Html.fromHtml(text));
                    } else {
                        String text = String.format(context.getString(R.string.money_raised_text),
                                String.valueOf(project.getFunding()),
                                String.valueOf(project.getGoal()));
                        views.setTextViewText(R.id.money_raised, Html.fromHtml(text));
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        views.setProgressBar(R.id.money_progressBar, 100, progress,false);
                    } else {
                        views.setProgressBar(R.id.money_progressBar, 100, progress,false);
                    }

                    GlideApp.with(context)
                            .asBitmap()
                            .load(project.getImageLink())
                            .error(R.drawable.global_logo_color)
                            .into(appWidgetTarget);

                    appWidgetManager.updateAppWidget(appWidgetId, views);
                }

            }
        }.execute();
    }
}

