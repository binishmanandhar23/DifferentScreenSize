package io.github.binishmanandhar23.differentscreensize.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import io.github.binishmanandhar23.differentscreensize.enums.Action

class NotificationActionReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.sendBroadcast(Intent("TRACKS_TRACKS").putExtra(Action.ActionName.action, intent?.action))
    }
}