package io.github.binishmanandhar23.differentscreensize.services

import android.app.Service
import android.content.Intent
import android.os.IBinder

class OnClearFromRecent : Service() {
    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int =
        START_NOT_STICKY

    override fun onTaskRemoved(rootIntent: Intent?) {
        stopSelf()
    }
}