package com.codemobiles.scbauthen.utilities

import android.os.Handler
import android.os.HandlerThread

class CMWorkerThread(threadName: String) : HandlerThread(threadName) {

    // Room does not allow operations on the main thread as it can makes your UI laggy.
    //mWorkerHandler ตัวคุม
    private lateinit var mWorkerHandler: Handler
    //สร้าง Background thread
    override fun onLooperPrepared() {
        super.onLooperPrepared()

        mWorkerHandler = Handler(looper)
    }
    //สั่งรัน
    fun postTask(task: Runnable) {
        mWorkerHandler.post(task)
    }

}