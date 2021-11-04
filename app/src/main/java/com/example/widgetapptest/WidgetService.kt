package com.example.widgetapptest

import android.content.Intent
import android.widget.RemoteViewsService

class WidgetService : RemoteViewsService(){
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory =
        RemoteViewFactory(this.applicationContext)

}