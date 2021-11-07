package com.example.widgetapptest

import android.util.Log
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


object FirebaseService {
    private val database = Firebase.database
    private val ref = database.getReference("/video")

    /**
     * Realtime Databaseに格納されたVideoItemの取得
     */
    fun getVideoItem(): ArrayList<WidgetItem> {
        val liveItems = ref.orderByChild("eventType").equalTo("live").get()
        val videoItems = ArrayList<WidgetItem>()
        liveItems.addOnSuccessListener { dataSnapshot ->
            dataSnapshot.children.forEach {
                val videoItem = WidgetItem(title = it.child("title").value.toString())
                videoItems.add(videoItem)
                Log.i("widgetItemSnapshot", "${videoItems.size}")
            }
        }.addOnFailureListener {
            Log.e("getVideoItem", "${it.message}")
        }
        return videoItems
    }

}