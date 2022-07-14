package io.github.binishmanandhar23.differentscreensize.notification

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaMetadata
import android.media.session.MediaSession
import android.media.session.PlaybackState
import android.os.Build
import android.os.Looper
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import io.github.binishmanandhar23.differentscreensize.R
import io.github.binishmanandhar23.differentscreensize.data.BookDetailData
import io.github.binishmanandhar23.differentscreensize.enums.Action
import io.github.binishmanandhar23.differentscreensize.enums.PlayState
import io.github.binishmanandhar23.differentscreensize.receivers.NotificationActionReceiver

object PlayerNotificationCreation {
    const val AudioBookChannelId = "AudioBookChannelId"

    fun createNotification(
        context: Context,
        bookDetailData: BookDetailData,
        albumBitmap: Bitmap,
        playState: PlayState,
        position: Int,
        size: Int
    ) {
        val notificationManagerCompat = NotificationManagerCompat.from(context)

        val intentPrevious = Intent(context, NotificationActionReceiver::class.java).setAction(
            Action.ActionPrevious.action
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
            Action.ActionPlay.action
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
            Action.ActionNext.action
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

        val mediaSession = MediaSession(context, "AudioBookSession")
        mediaSession.setMetadata(
            MediaMetadata.Builder()
                .putString(
                    MediaMetadata.METADATA_KEY_TITLE,
                    bookDetailData.bookListPreviewData.bookTitle
                )
                .putBitmap(MediaMetadata.METADATA_KEY_ALBUM_ART, albumBitmap)
                .putString(
                    MediaMetadata.METADATA_KEY_ARTIST,
                    bookDetailData.bookListPreviewData.bookAuthor
                ).build()
        )
        mediaSession.setPlaybackState(
            PlaybackState.Builder().setActions(PlaybackState.ACTION_PLAY).build()
        )
        mediaSession.setCallback(object : MediaSession.Callback() {
            override fun onPlay() {
                pendingIntentPlay.send()
            }

            override fun onPause() {
                pendingIntentPlay.send()
            }
        })
        val mediaStyle = Notification.MediaStyle().setMediaSession(mediaSession.sessionToken)


        val notification =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Notification.Builder(context, AudioBookChannelId).let { notificationCompat ->
                    notificationCompat.setSmallIcon(R.drawable.ic_music_note)
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
                    notificationCompat.setLargeIcon(albumBitmap)
                    notificationCompat.setContentTitle(bookDetailData.bookListPreviewData.bookTitle)
                    notificationCompat.setContentText(bookDetailData.bookListPreviewData.bookAuthor)
                    notificationCompat.setOnlyAlertOnce(true)
                    notificationCompat.setShowWhen(false)
                    notificationCompat.addAction(
                        if (position == 0) 0 else R.drawable.ic_previous,
                        "Previous",
                        if (position == 0) null else pendingIntentPrevious
                    )
                    notificationCompat.addAction(
                        if (playState == PlayState.PAUSED) R.drawable.ic_play else R.drawable.ic_pause,
                        "Play",
                        pendingIntentPlay
                    )
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

    private fun getAvailableActions(playState: PlayState): Long {
        var actions: Long = PlaybackState.ACTION_PLAY_PAUSE or
                PlaybackState.ACTION_PLAY_FROM_MEDIA_ID or
                PlaybackState.ACTION_PLAY_FROM_SEARCH or
                PlaybackState.ACTION_SKIP_TO_PREVIOUS or
                PlaybackState.ACTION_SKIP_TO_NEXT
        actions = if (playState == PlayState.PLAYING)
            actions or PlaybackState.ACTION_PAUSE
        else
            actions or PlaybackState.ACTION_PLAY
        return actions
    }
}