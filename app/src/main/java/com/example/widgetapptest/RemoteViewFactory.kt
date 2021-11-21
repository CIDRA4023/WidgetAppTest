package com.example.widgetapptest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.bumptech.glide.Glide


class RemoteViewFactory(private val mContext: Context) : RemoteViewsService.RemoteViewsFactory {


    private var widgetItem = arrayListOf<WidgetItem>()


    override fun onCreate() {
        widgetItem.clear()
        widgetItem = FirebaseService.getVideoItem()
//        widgetItem = arrayListOf("a", "b")

        // RealtimeDatabaseからVideoItemを取得して表示させるために待機
        SystemClock.sleep(5000)

    }

    override fun onDataSetChanged() {
        widgetItem.clear()
        widgetItem = FirebaseService.getVideoItem()
//        widgetItem = arrayListOf("a", "b")

        // RealtimeDatabaseからVideoItemを取得して表示させるために待機
        SystemClock.sleep(5000)


    }

    override fun onDestroy() {
        widgetItem.clear()

    }

    override fun getCount(): Int = widgetItem.size

    override fun getViewAt(position: Int): RemoteViews {
        val views = RemoteViews(mContext.packageName, R.layout.widget_vf_item)
        try {
            val bitmap = Glide.with(mContext)
                .asBitmap()
                .load(widgetItem[position].thumbnail)
                .submit()
                .get()
            views.apply {
//                setTextViewText(R.id.vf_title, vfTitles[position])
//                setImageViewBitmap(R.id.vf_thumbnail, bitmap)
                setTextViewText(R.id.vf_title, widgetItem[position].title)
                setImageViewBitmap(R.id.vf_thumbnail, bitmap)
                setClickEvent(widgetItem[position].videoId)
//                Log.i("click", widgetItem[position])
            }

            SystemClock.sleep(500)

        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        return views
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(p0: Int): Long = p0.toLong()

    override fun hasStableIds(): Boolean = true

    private fun RemoteViews.setClickEvent(item: String) {
        setOnClickEvent(item)

    }


    private fun RemoteViews.setOnClickEvent(url: String) {
        // Actionの設定
        val bundle = Bundle().apply {
            putString(WidgetProvider.KEY_VIDEO_URL, url)
        }
        val intent = Intent().apply {
            action = WidgetProvider.ACTION_CLICK_ITEM
            putExtras(bundle)
        }


        setOnClickFillInIntent(R.id.vf_thumbnail, intent)
    }

}
