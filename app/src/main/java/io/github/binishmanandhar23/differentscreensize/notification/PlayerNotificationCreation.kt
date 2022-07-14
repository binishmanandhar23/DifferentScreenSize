package io.github.binishmanandhar23.differentscreensize.notification

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaMetadata
import android.media.session.MediaSession
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import io.github.binishmanandhar23.differentscreensize.R
import io.github.binishmanandhar23.differentscreensize.data.BookDetailData
import io.github.binishmanandhar23.differentscreensize.enums.PlayState
import io.github.binishmanandhar23.differentscreensize.receivers.NotificationActionReceiver
import okhttp3.internal.notify
import java.io.IOException
import java.net.URL

class PlayerNotificationCreation {
    companion object {
        const val AudioBookChannelId = "AudioBookChannelId"
        const val ActionPrevious = "ActionPrevious"
        const val ActionPlay = "ActionPlay"
        const val ActionNext = "ActionNext"
    }

    fun createNotification(
        context: Context,
        bookDetailData: BookDetailData,
        playState: PlayState,
        position: Int,
        size: Int
    ) {
        val notificationManagerCompat = NotificationManagerCompat.from(context)

        val mediaSession = MediaSession(context, "AudioBookSession")
        mediaSession.setMetadata(
            MediaMetadata.Builder()
                .putString(
                    MediaMetadata.METADATA_KEY_TITLE,
                    bookDetailData.bookListPreviewData.bookTitle
                )
                .putString(
                    MediaMetadata.METADATA_KEY_ARTIST,
                    bookDetailData.bookListPreviewData.bookAuthor
                ).build()
        )
        val mediaStyle = Notification.MediaStyle().setMediaSession(mediaSession.sessionToken)

        val icon = try {
            URL(bookDetailData.bookListPreviewData.bookImageURL).let {
                BitmapFactory.decodeStream(it.openConnection().getInputStream())
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }

        val intentPrevious = Intent(context, NotificationActionReceiver::class.java).setAction(
            ActionPrevious
        )
        val pendingIntentPrevious = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            PendingIntent.getBroadcast(
                context,
                0,
                intentPrevious,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        else
            PendingIntent.getBroadcast(
                context,
                0,
                intentPrevious,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

        val intentPlay = Intent(context, NotificationActionReceiver::class.java).setAction(
            ActionPlay
        )
        val pendingIntentPlay = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            PendingIntent.getBroadcast(
                context,
                0,
                intentPlay,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        else
            PendingIntent.getBroadcast(context, 0, intentPlay, PendingIntent.FLAG_UPDATE_CURRENT)

        val intentNext = Intent(context, NotificationActionReceiver::class.java).setAction(
            ActionNext
        )
        val pendingIntentNext = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            PendingIntent.getBroadcast(
                context,
                0,
                intentNext,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        else
            PendingIntent.getBroadcast(context, 0, intentNext, PendingIntent.FLAG_UPDATE_CURRENT)


        val notification =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Notification.Builder(context, AudioBookChannelId).let { notificationCompat ->
                    notificationCompat.setSmallIcon(R.drawable.ic_music_note)
                    if (icon != null)
                        notificationCompat.setLargeIcon(icon)
                    notificationCompat.setOnlyAlertOnce(true)
                    notificationCompat.setShowWhen(false)
                    notificationCompat.addAction(
                        Notification.Action.Builder(
                            if (position == 0) 0 else R.drawable.ic_previous,
                            "Previous",
                            if (position == 0) null else pendingIntentPrevious
                        ).build()
                    )
                    notificationCompat.addAction(
                        Notification.Action.Builder(
                            R.drawable.ic_play,
                            "Play",
                            pendingIntentPlay
                        ).build()
                    )
                    notificationCompat.addAction(
                        Notification.Action.Builder(
                            if (position == size - 1) 0 else R.drawable.ic_next,
                            "Previous",
                            if (position == size - 1) null else pendingIntentNext
                        ).build()
                    )
                    notificationCompat.style = mediaStyle
                    notificationCompat.build()
                }
            } else {
                NotificationCompat.Builder(context, AudioBookChannelId).let { notificationCompat ->
                    notificationCompat.setSmallIcon(R.drawable.ic_music_note)
                    notificationCompat.setContentTitle(bookDetailData.bookListPreviewData.bookTitle)
                    notificationCompat.setContentText(bookDetailData.bookListPreviewData.bookAuthor)
                    if (icon != null)
                        notificationCompat.setLargeIcon(icon)
                    notificationCompat.setOnlyAlertOnce(true)
                    notificationCompat.setShowWhen(false)
                    notificationCompat.addAction(
                        if (position == 0) 0 else R.drawable.ic_previous,
                        "Previous",
                        if (position == 0) null else pendingIntentPrevious
                    )
                    notificationCompat.addAction(R.drawable.ic_play, "Play", pendingIntentPlay)
                    notificationCompat.addAction(
                        if (position == size - 1) 0 else R.drawable.ic_next,
                        "Previous",
                        if (position == size - 1) null else pendingIntentNext
                    )
                    notificationCompat.priority = NotificationCompat.PRIORITY_LOW
                    notificationCompat.build()
                }
            }
        notificationManagerCompat.notify(1, notification)
    }
}