package io.magunghkm.github.notificationhistoryquicksetting

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService

class NotificationHistoryTileService : TileService() {

    override fun onStartListening() {
        super.onStartListening()

        qsTile?.let { tile ->
            tile.state = Tile.STATE_ACTIVE

            tile.subtitle = "Tap to open"

            tile.updateTile()
        }
    }

    @SuppressLint("StartActivityAndCollapseDeprecated")
    override fun onClick() {
        super.onClick()

        val intent = Intent("android.settings.NOTIFICATION_HISTORY").apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

        // Handle Android 14+ (API 34) requirements vs older versions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            val pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
            startActivityAndCollapse(pendingIntent)
        } else {
            @Suppress("Handle Android 11-13")
            startActivityAndCollapse(intent)
        }
    }
}