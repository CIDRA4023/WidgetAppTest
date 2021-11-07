package com.example.widgetapptest

import android.content.Context
import android.os.SystemClock
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService

class RemoteViewFactory(private val mContext: Context) : RemoteViewsService.RemoteViewsFactory {

    private var widgetItem: ArrayList<WidgetItem> = ArrayList()


    override fun onCreate() {

        widgetItem = FirebaseService.getVideoItem()

        // RealtimeDatabaseからVideoItemを取得して表示させるために待機
        SystemClock.sleep(5000)

    }

    override fun onDataSetChanged() {


    }

    override fun onDestroy() {
        widgetItem.clear()

    }

    override fun getCount(): Int = widgetItem.size

    override fun getViewAt(position: Int): RemoteViews {
        val views = RemoteViews(mContext.packageName,
            R.layout.item_list_view
        ).apply {
            val title = widgetItem[position].title
            Log.i("getViewAt", title)
            setTextViewText(R.id.widget_item_text, widgetItem[position].title)
        }


        SystemClock.sleep(500)



        return views
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(p0: Int): Long = p0.toLong()

    override fun hasStableIds(): Boolean = true
}