package com.example.widgetapptest

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.RemoteViews
import androidx.core.net.toUri

/**
 * Implementation of App Widget functionality.
 */
class WidgetProvider : AppWidgetProvider() {

    companion object {

        const val KEY_VIDEO_URL = "key_shot_url"

        const val ACTION_CLICK_ITEM = "com.example.widgetapptest.widget.CLICK_ITEM"

    }

    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {

        val intent = Intent(context, WidgetService::class.java)
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()

        RemoteViews(context.packageName, R.layout.widget_provider).apply {
            setRemoteAdapter(R.id.stack_view, Intent(context, WidgetService::class.java))
            setEmptyView(R.id.stack_view, R.id.empty_view)
        }.let {
            val clickIntent = Intent(context, WidgetProvider::class.java).apply {
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            }
            val clickPendingIntent = PendingIntent.getBroadcast(context, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            it.setPendingIntentTemplate(R.id.stack_view, clickPendingIntent)

            appWidgetManager.updateAppWidget(appWidgetId, it)
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.stack_view)
        }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action.equals(ACTION_CLICK_ITEM)) {

            val baseUrl = "https://www.youtube.com/watch?v="
            val videoUrl = intent.getStringExtra(KEY_VIDEO_URL)
            Log.i("click", "$videoUrl")
            val uri = Uri.parse(baseUrl + videoUrl)
            Intent(Intent.ACTION_VIEW, uri).let {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(it)
            }
        }
    }
}

