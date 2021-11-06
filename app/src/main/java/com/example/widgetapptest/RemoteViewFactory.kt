package com.example.widgetapptest

import android.content.Context
import android.widget.RemoteViews
import android.widget.RemoteViewsService

class RemoteViewFactory(private val mContext: Context) : RemoteViewsService.RemoteViewsFactory {

    private val widgetItem = mutableListOf<WidgetItem>()

    override fun onCreate() {

    }

    override fun onDataSetChanged() {
        widgetItem.add(WidgetItem(title = "1"))
        widgetItem.add(WidgetItem(title = "2"))
        widgetItem.add(WidgetItem(title = "3"))
        widgetItem.add(WidgetItem(title = "4"))
    }

    override fun onDestroy() {

    }

    override fun getCount(): Int = widgetItem.size

    override fun getViewAt(position: Int): RemoteViews {
        val views = RemoteViews(mContext.packageName,
            R.layout.item_list_view
        ).apply {
            setTextViewText(R.id.widget_item_text, widgetItem[position].title)
        }

        return views
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(p0: Int): Long = 0

    override fun hasStableIds(): Boolean = false
}