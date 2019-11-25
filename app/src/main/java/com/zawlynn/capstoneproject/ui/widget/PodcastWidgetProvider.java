

package com.zawlynn.capstoneproject.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;

import android.widget.RemoteViews;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.AppWidgetTarget;
import com.bumptech.glide.request.transition.Transition;
import com.zawlynn.capstoneproject.ui.main.HomeActivity;
import com.zawlynn.udacitycapstoneproject.R;

import static com.zawlynn.capstoneproject.utils.Constant.PREF_DEF_VALUE;
import static com.zawlynn.capstoneproject.utils.Constant.SIZE_BITMAP;
import static com.zawlynn.capstoneproject.utils.Constant.WIDGET_PENDING_INTENT_ID;


public class PodcastWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String episodeTitle = sharedPreferences.getString(
                context.getString(R.string.pref_episode_title_key), PREF_DEF_VALUE);
        String podcastTitle = sharedPreferences.getString(
                context.getString(R.string.pref_podcast_title_key), PREF_DEF_VALUE);
        String episodeImage = sharedPreferences.getString(
                context.getString(R.string.pref_episode_image_key), PREF_DEF_VALUE);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.podcast_widget);
        views.setTextViewText(R.id.widget_episode_title, episodeTitle);
        views.setTextViewText(R.id.widget_podcast_title, podcastTitle);
        loadImage(context, views, appWidgetId, episodeImage);

        Intent intent = new Intent(context, HomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, WIDGET_PENDING_INTENT_ID,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_artwork, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static void loadImage(Context context, RemoteViews remoteViews, int appWidgetId, String imageUrl) {
        AppWidgetTarget target = new AppWidgetTarget(context, R.id.widget_artwork, remoteViews, appWidgetId) {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, Transition<? super Bitmap> transition) {
                super.onResourceReady(resource, transition);
            }
        };

        RequestOptions options = new RequestOptions().
                override(SIZE_BITMAP, SIZE_BITMAP);

        Glide.with(context.getApplicationContext())
                .asBitmap()
                .load(imageUrl)
                .apply(options)
                .into(target);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        String action = intent.getAction();
        if (AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(action)) {
            int[] appWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
            for (int appWidgetId :appWidgetIds) {
                updateAppWidget(context, appWidgetManager, appWidgetId);
            }
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onEnabled(Context context) {

    }

    @Override
    public void onDisabled(Context context) {
        
    }
}

